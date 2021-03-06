package cn.gdeng.nst.server.source.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.nst.api.dto.source.SourceLogSimpleDetailDTO;
import cn.gdeng.nst.api.dto.source.SourceShipperFullDetailDTO;
import cn.gdeng.nst.api.dto.source.SourceShipperListDTO;
import cn.gdeng.nst.api.dto.source.SourceShipperSimpleDetailDTO;
import cn.gdeng.nst.api.server.source.GoodsService;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.entity.nst.SourceLogEntity;
import cn.gdeng.nst.entity.nst.SourceMerchantEntity;
import cn.gdeng.nst.entity.nst.SourceShipperEntity;
import cn.gdeng.nst.entity.nst.SourceShipperExtEntity;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.enums.OperateEnum;
import cn.gdeng.nst.enums.SourceStatusEnum;
import cn.gdeng.nst.server.source.mq.GoodsProvidereMQServie;
import cn.gdeng.nst.util.server.AddrUtils;
import cn.gdeng.nst.util.server.ReflectionUtils;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;

@Service
public class GoodsServiceImpl implements GoodsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private BaseDao<?> baseDao;
	@Resource
	private GoodsProvidereMQServie goodsProvidereMQServie;

	@Override
	@Transactional
	public ApiResult<Map<String, Object>> save(SourceShipperEntity entity) throws BizException {

		// ===保存实体====
		Long id = baseDao.persist(entity, Long.class);
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", id);
		apiResult.setResult(resultMap);
		logger.debug("保存SourceShipperEntity实体成功!");
		// ===保存货源日志=====
		saveLog(id.intValue(), OperateEnum.GOODSPUBLISHED.getName(), entity.getCreateUserId());
		return apiResult;
	}

	@Override
	@Transactional
	public ApiResult<Map<String, Object>> save(SourceShipperEntity shipper, SourceMerchantEntity merchant)
			throws BizException {
		// 先保存货源
		ApiResult<Map<String, Object>> originalResult = this.save(shipper);
		// 再保存货源对应的商家信息。
		Long sourceId = (Long) originalResult.getResult().get("id");
		// 关联货源id
		merchant.setSourceId(sourceId.intValue());
		baseDao.persist(merchant, Long.class);

		return originalResult;
	}

	/**
	 * 保存货源日志。
	 * 
	 * @param sourceId
	 * @param desc
	 * @param createUserId
	 * @return
	 */
	private int saveLog(Integer sourceId, String desc, String createUserId) {
		SourceLogEntity entity = new SourceLogEntity();
		entity.setSourceId(sourceId);
		entity.setDescription(desc);
		entity.setCreateUserId(createUserId);
		Long id = baseDao.persist(entity, Long.class);
		logger.debug("保存货源日志成功，日志内容：" + desc);
		return id.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApiResult<ApiPage> queryMyPublishedByPageForShipper(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		// 获取结果集总数
		long total = baseDao.queryForObject("SourceShipper.countMyPublishedForShipper", page.getParaMap(), Long.class);
		// 获取结果集
		List<SourceShipperListDTO> list;
		if (total > 0) {
			list = baseDao.queryForList("SourceShipper.queryMyPublishedByPageForShipper", page.getParaMap(),
					SourceShipperListDTO.class);

			try {
				// 生成详细的全地址
				generalFullAddrAndSet(list);
			} catch (Exception e) {
				logger.error("", e);
				throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
			}

		} else {
			list = Collections.EMPTY_LIST;
		}
		// 将结果封装到分页对象中
		page.setRecordList(list).setRecordCount(total);
		logger.debug("queryMyPublishedByPageForShipper查询成功！");
		return apiResult.setResult(page);
	}

	/**
	 * 生成详细的全地址，并set到list的元素中。<br/>
	 * NOTE:如果地址包含省份，去除该省份。
	 * 
	 * @param list
	 * @throws Exception
	 */
	private void generalFullAddrAndSet(List<SourceShipperListDTO> list) throws Exception {
		for (SourceShipperListDTO srcShi : list) {
			generalFullAddrAndSet(srcShi);
		}
	}

	/**
	 * 生成详细的全地址，并set到参数的元素中。<br/>
	 * NOTE:如果地址包含省份，去除该省份。
	 * 
	 * @param list
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private void generalFullAddrAndSet(Object obj) throws Exception {
		if (obj == null) {
			return;
		}
		// =====设置起始地=====
		String srcFullAddr = "";
		String sDetailVal = (String) ReflectionUtils.invokeMethod(obj, "getS_detail");
		if (StringUtils.isNotBlank(sDetailVal)) {
			srcFullAddr = AddrUtils.ridProvinceAndSpliceAddr(sDetailVal, "/");

		}
		String sDetailAddrVal = (String) ReflectionUtils.invokeMethod(obj, "getS_detailed_address");
		if (StringUtils.isNotBlank(sDetailAddrVal)) {
			srcFullAddr += sDetailAddrVal;
		}
		// set到对象属性中
		ReflectionUtils.invokeMethod(obj, "setSrcFullAddr", srcFullAddr);

		// =====设置目的地====
		String desFullAddr = "";
		String eDetailVal = (String) ReflectionUtils.invokeMethod(obj, "getE_detail");
		if (StringUtils.isNotBlank(eDetailVal)) {
			desFullAddr = AddrUtils.ridProvinceAndSpliceAddr(eDetailVal, "/");
		}
		String eDetailAddrVal = (String) ReflectionUtils.invokeMethod(obj, "getE_detailed_address");
		if (StringUtils.isNotBlank(eDetailAddrVal)) {
			desFullAddr += eDetailAddrVal;
		}
		// set到对象属性中
		ReflectionUtils.invokeMethod(obj, "setDesFullAddr", desFullAddr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApiResult<ApiPage> queryMyUnconfirmedByPageForShipper(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		// 获取结果集总数
		// 获取结果集总数
		long total = baseDao.queryForObject("SourceShipper.countMyUnconfirmedForShipper", page.getParaMap(),
				Long.class);
		// 获取结果集
		List<SourceShipperListDTO> list;
		if (total > 0) {
			list = baseDao.queryForList("SourceShipper.queryMyUnconfirmedByPageForShipper", page.getParaMap(),
					SourceShipperListDTO.class);

			try {
				// 生成详细的全地址
				generalFullAddrAndSet(list);
			} catch (Exception e) {
				logger.error("", e);
				throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
			}

		} else {
			list = Collections.EMPTY_LIST;
		}
		// 将结果封装到分页对象中
		page.setRecordList(list).setRecordCount(total);
		logger.debug("queryMyUnconfirmedByPageForShipper查询成功！");
		return apiResult.setResult(page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApiResult<ApiPage> queryMyAllByPageForShipper(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		// 获取结果集总数
		long total = baseDao.queryForObject("SourceShipper.countMyAllForShipper", page.getParaMap(), Long.class);
		// 获取结果集
		List<SourceShipperListDTO> list;
		if (total > 0) {
			list = baseDao.queryForList("SourceShipper.queryMyAllByPageForShipper", page.getParaMap(),
					SourceShipperListDTO.class);

			try {
				// 生成详细的全地址
				generalFullAddrAndSet(list);
			} catch (Exception e) {
				logger.error("", e);
				throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
			}

		} else {
			list = Collections.EMPTY_LIST;
		}
		// 将结果封装到分页对象中
		page.setRecordList(list).setRecordCount(total);
		logger.debug("queryMyAllByPageForShipper查询成功！");
		return apiResult.setResult(page);
	}

	@Override
	@Transactional
	public ApiResult<Map<String, Object>> deleteSafelyById(Map<String, Object> param) throws BizException {
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String, Object>>();

		int deleteRecord = baseDao.execute("SourceShipper.deleteSafelyById", param);
		// 判断删除结果
		if (deleteRecord == 0) {
			throw new BizException(MsgCons.C_20000, "删除失败，请刷新后再尝试！");
		}
		logger.debug("deleteSafelyById删除货源成功！");
		Map<String, Object> resultMap = new HashMap<String, Object>(1);
		resultMap.put("record", deleteRecord);
		return apiResult.setResult(resultMap);
	}

	@Override
	public ApiResult<SourceShipperFullDetailDTO> queryGoodsDetailForShipper(Map<String, Object> param)
			throws BizException {
		ApiResult<SourceShipperFullDetailDTO> apiResult = new ApiResult<SourceShipperFullDetailDTO>();
		SourceShipperFullDetailDTO dto = baseDao.queryForObject("SourceShipper.queryGoodsDetailForShipper", param,
				SourceShipperFullDetailDTO.class);

		if (dto == null) {
			throw new BizException(MsgCons.C_24020, MsgCons.M_24020);
		}

		try {
			// 生成详细的全地址
			generalFullAddrAndSet(dto);
		} catch (Exception e) {
			logger.error("", e);
			throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
		}

		logger.debug("queryGoodsDetailForShipper查询成功！");
		return apiResult.setResult(dto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApiResult<ApiPage> queryMyGoodsByPageForLogistics(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		// 获取结果集总数
		long total = baseDao.queryForObject("SourceShipper.countMyGoodsByPageForLogistics", page.getParaMap(),
				Long.class);
		// 获取结果集
		List<SourceShipperListDTO> list;
		if (total > 0) {
			list = baseDao.queryForList("SourceShipper.queryMyGoodsByPageForLogistics", page.getParaMap(),
					SourceShipperListDTO.class);

			try {
				// 生成详细的全地址
				generalFullAddrAndSet(list);
			} catch (Exception e) {
				logger.error("", e);
				throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
			}

		} else {
			list = Collections.EMPTY_LIST;
		}
		// 将结果封装到分页对象中
		page.setRecordList(list).setRecordCount(total);
		logger.debug("queryMyGoodsByPageForLogistics查询成功！");
		return apiResult.setResult(page);
	}

	@Override
	public ApiResult<SourceShipperSimpleDetailDTO> queryMyGoodsDetailForLogistics(Map<String, Object> param)
			throws BizException {
		ApiResult<SourceShipperSimpleDetailDTO> apiResult = new ApiResult<SourceShipperSimpleDetailDTO>();
		SourceShipperSimpleDetailDTO dto = baseDao.queryForObject("SourceShipper.queryMyGoodsDetailForLogistics", param,
				SourceShipperSimpleDetailDTO.class);

		if (dto == null) {
			throw new BizException(MsgCons.C_24020, MsgCons.M_24020);
		}

		try {
			// 生成详细的全地址
			generalFullAddrAndSet(dto);
		} catch (Exception e) {
			logger.error("", e);
			throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
		}

		logger.debug("queryMyGoodsDetailForLogistics查询成功！");
		return apiResult.setResult(dto);
	}

	@Override
	@Transactional
	public ApiResult<Map<String, Object>> refreshGoodsCreateTimeAndTransToPub(Map<String, Object> param)
			throws BizException {
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String, Object>>();
		SourceShipperEntity paramEntity = new SourceShipperEntity();
		paramEntity.setId(Integer.valueOf(param.get("id").toString()));
		SourceShipperEntity resultEntity = baseDao.find(SourceShipperEntity.class, paramEntity);

		// 刷新时间
		Date createTime = (Date) param.get("createTime");

		// 版本号比较
		if (resultEntity.getVersion() != Integer.parseInt(param.get("version").toString())) {
			throw new BizException(MsgCons.C_24008, MsgCons.M_24008);
		}

		// 状态判断，只有已发布和已过期的才可以刷新发货时间
		Byte sourceStatus = resultEntity.getSourceStatus();
		if (!SourceStatusEnum.RELEASED.getCode().equals(sourceStatus)
				&& !SourceStatusEnum.EXPIRE.getCode().equals(sourceStatus)) {
			throw new BizException(MsgCons.C_24031, MsgCons.M_24031);
		}

		// 货源状态均改为以发布。
		param.put("sourceStatus", SourceStatusEnum.RELEASED.getCode());

		int record = baseDao.execute("SourceShipper.updateCreateTimeById", param);
		if (record == 0) {
			throw new BizException(MsgCons.C_24008, MsgCons.M_24008);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("record", record);
		resultMap.put("refreshDate", createTime);

		logger.debug("refreshGoodsCreateTimeAndTransToPub执行成功！");
		return apiResult.setResult(resultMap);
	}

	@Override
	@Transactional
	public ApiResult<Map<String, Object>> changeSendDateAndTransToPub(Map<String, Object> param) throws BizException {
		ApiResult<Map<String, Object>> apiResult = new ApiResult<Map<String, Object>>();

		SourceShipperEntity paramEntity = new SourceShipperEntity();
		paramEntity.setId(Integer.valueOf(param.get("id").toString()));
		SourceShipperEntity resultEntity = baseDao.find(SourceShipperEntity.class, paramEntity);

		// 状态判断，只有已发布和已过期的才可以刷新发货和装货时间
		Byte sourceStatus = resultEntity.getSourceStatus();
		if (!SourceStatusEnum.RELEASED.getCode().equals(sourceStatus)
				&& !SourceStatusEnum.EXPIRE.getCode().equals(sourceStatus)) {
			throw new BizException(MsgCons.C_24032, MsgCons.M_24032);
		}

		// 货源状态均改为以发布。
		param.put("sourceStatus", SourceStatusEnum.RELEASED.getCode());

		int record = baseDao.execute("SourceShipper.updateSendDateById", param);
		if (record == 0) {
			throw new BizException(MsgCons.C_24009, MsgCons.M_24009);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("record", record);
		logger.debug("changeSendDateAndTransToPub执行成功！");
		return apiResult.setResult(resultMap);
	}

	@Override
	public ApiResult<SourceShipperEntity> getEntityById(Map<String, Object> param) throws BizException {
		ApiResult<SourceShipperEntity> apiResult = new ApiResult<SourceShipperEntity>();
		SourceShipperEntity paramEntity = new SourceShipperEntity();
		paramEntity.setId(Integer.valueOf(param.get("id").toString()));
		SourceShipperEntity resultEntity = baseDao.find(SourceShipperEntity.class, paramEntity);
		if (resultEntity == null) {
			throw new BizException(MsgCons.C_24010, MsgCons.M_24010);
		}
		logger.debug("getEntityById查询成功！");
		return apiResult.setResult(resultEntity);

	}

	@SuppressWarnings("unchecked")
	@Override
	public ApiResult<ApiPage> queryMyOverdueByPageForShipper(ApiPage page) throws BizException {
		ApiResult<ApiPage> apiResult = new ApiResult<ApiPage>();
		// 获取结果集总数
		long total = baseDao.queryForObject("SourceShipper.countMyOverdueForShipper", page.getParaMap(), Long.class);
		// 获取结果集
		List<SourceShipperListDTO> list;
		if (total > 0) {
			list = baseDao.queryForList("SourceShipper.queryMyOverdueByPageForShipper", page.getParaMap(),
					SourceShipperListDTO.class);

			try {
				// 生成详细的全地址
				generalFullAddrAndSet(list);
			} catch (Exception e) {
				logger.error("", e);
				throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
			}

		} else {
			list = Collections.EMPTY_LIST;
		}
		// 将结果封装到分页对象中
		page.setRecordList(list).setRecordCount(total);
		logger.debug("queryMyPublishedByPageForShipper查询成功！");
		return apiResult.setResult(page);
	}

	@Override
	public ApiResult<SourceShipperFullDetailDTO> queryGoodsDetailAndLogForShipper(Map<String, Object> paraMap)
			throws BizException {
		// ====查询出货源详情====
		ApiResult<SourceShipperFullDetailDTO> apiResult = new ApiResult<SourceShipperFullDetailDTO>();
		SourceShipperFullDetailDTO sourceShipper = baseDao.queryForObject("SourceShipper.queryGoodsDetailForShipper",
				paraMap, SourceShipperFullDetailDTO.class);

		if (sourceShipper == null) {
			throw new BizException(MsgCons.C_24020, MsgCons.M_24020);
		}

		try {
			// 生成详细的全地址
			generalFullAddrAndSet(sourceShipper);
		} catch (Exception e) {
			logger.error("", e);
			throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
		}

		// ====查询出关联的日志=====
		paraMap.put("sourceId", paraMap.get("id"));
		List<SourceLogSimpleDetailDTO> sourceLogs = baseDao.queryForList("SourceLog.simpleQueryBySourceId", paraMap,
				SourceLogSimpleDetailDTO.class);
		sourceShipper.setSourceLogs(sourceLogs);

		logger.debug("queryGoodsDetailAndLogForShipper查询成功！");
		return apiResult.setResult(sourceShipper);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ApiResult<Map<String, Object>> scanOverdueAndUpdate() throws BizException {
		ApiResult<Map<String, Object>> apiReult = new ApiResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		apiReult.setResult(resultMap);

		List<Integer> ids = baseDao.queryForList("SourceShipper.queryIdOverdueButNotUpdateStatus", null, Integer.class);
		int idCount = ids.size();
		resultMap.put("record", idCount);
		if (idCount == 0) {
			return apiReult;
		}

		// 更新状态
		Map<String, Object> updateParam = new HashMap<String, Object>();
		updateParam.put("ids", ids);
		updateParam.put("updateUserId", "system");
		int record = baseDao.execute("SourceShipper.updateOverdueStatusByIds", updateParam);

		// 如果更新的数量和查询的id数量不一致，则抛出错误信息，不进行更新。
		if (idCount != record) {
			throw new BizException(MsgCons.C_24014, MsgCons.M_24014);
		}

		// 保存日志。
		Map<String, Object>[] saveLogParams = new Map[idCount];
		for (int i = 0, len = idCount; i < len; i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceId", ids.get(i));
			param.put("description", OperateEnum.GOODSOVERDUE.getName());
			param.put("createUserId", "system");
			saveLogParams[i] = param;
		}
		baseDao.batchUpdate("SourceLog.insert", saveLogParams);

		return apiReult;
	}

	@Override
	@Transactional
	public ApiResult<Integer> refreshGoods(Map<String, Object> paramMap) throws BizException {
		ApiResult<Integer> result = new ApiResult<>();
		SourceShipperExtEntity paramEntity = new SourceShipperExtEntity();
		paramEntity.setId(Integer.valueOf(paramMap.get("id").toString()));
		SourceShipperExtEntity resultEntity = baseDao.find(SourceShipperExtEntity.class, paramEntity);
		// 状态判断，只有已发布和已过期的才可以刷新发货和装货时间
		Byte sourceStatus = resultEntity.getSourceStatus();
		if (!SourceStatusEnum.RELEASED.getCode().equals(sourceStatus)
				&& !SourceStatusEnum.EXPIRE.getCode().equals(sourceStatus)) {
			throw new BizException(MsgCons.C_24032, MsgCons.M_24032);
		}
		// 装货时间与当前时间比较 (装货时间为null,不需要比较)
		if (resultEntity.getSendDate()!=null) {
			checkSendDate(resultEntity);
		}
		// 删除
		int count = baseDao.execute("SourceShipper.refreshDeleteGoodsById", resultEntity);
		if (count <= 0) {
			logger.debug("删除失败");
			throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
		}
		// 组装entity
		handleEntity(resultEntity);
		// 新增
		Long id = baseDao.persist(resultEntity, Long.class);
		result.setResult(Integer.parseInt(id.toString()));
		return result;
	}

	/**
	 * 装货时间和当前时间比较
	 * 
	 * @param resultEntity
	 * @throws BizException
	 */
	private void checkSendDate(SourceShipperExtEntity resultEntity) throws BizException {
		// 装货时间
		long sendTimeMillis = resultEntity.getSendDate().getTime();
		// 当前时间
		long currentTimeMillis = System.currentTimeMillis();
		// 装货时间小于当前刷新时间，返回24007
		if (sendTimeMillis < currentTimeMillis) {
			throw new BizException(MsgCons.C_24007, MsgCons.M_24007);
		}
	}

	/**
	 * 组装entity
	 * 
	 * @param resultEntity
	 */
	private void handleEntity(SourceShipperExtEntity resultEntity) {
		// 清除主键
		resultEntity.setId(null);
		// 设置货源为已发布
		resultEntity.setSourceStatus(SourceStatusEnum.RELEASED.getCode());
		// 设置创建时间
		resultEntity.setCreateTime(new Date());
		// 设置刷新时间
		resultEntity.setRealCreateTime(new Date());
		// 设置修改时间
		resultEntity.setUpdateTime(new Date());
		// 设置版本为0
		resultEntity.setVersion(0);

	}

	@Override
	public ApiResult<Integer> deleteGoodsById(Integer updateId) throws BizException {
		ApiResult<Integer> result = new ApiResult<>();
		SourceShipperExtEntity resultEntity = new SourceShipperExtEntity();
		resultEntity.setId(updateId);
		// 查询实体(主要查询版本号和会员id)
		resultEntity = baseDao.find(SourceShipperExtEntity.class, resultEntity);
		// 删除
		int count = baseDao.execute("SourceShipper.refreshDeleteGoodsById", resultEntity);
		if (count <= 0) {
			logger.debug("删除失败");
			throw new BizException(MsgCons.C_20000, MsgCons.M_20000);
		}
		return result;
	}
}
