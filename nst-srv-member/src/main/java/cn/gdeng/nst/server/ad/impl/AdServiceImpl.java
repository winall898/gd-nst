package cn.gdeng.nst.server.ad.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import cn.gdeng.nst.api.dto.ad.AdAllDto;
import cn.gdeng.nst.api.dto.ad.AdTaskDTO;
import cn.gdeng.nst.api.dto.ad.AdvertisementDto;
import cn.gdeng.nst.api.server.ad.AdService;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.enums.AdStatusEnum;
import cn.gdeng.nst.util.server.DateUtil;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.util.web.api.ParamProcessUtil;

/**
 * @author DJB
 * @version 创建时间：2016年8月1日 上午11:21:14 广告服务实现类
 */

@Service
public class AdServiceImpl implements AdService {

	// private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private BaseDao<?> baseDao;

	/**
	 * 
	 * @Description: 查询新广告和公告
	 * @param paraMap
	 *        省市 类型
	 * @return
	 * @throws BizException
	 *
	 */
	@Override
	public ApiResult<AdAllDto> queryAdAllNew(Map<String, Object> paraMap) throws BizException {
		ApiResult<AdAllDto> result = new ApiResult<AdAllDto>();
		AdAllDto adAllDto = new AdAllDto();
		paraMap.put("nowTime", new Date());
		// 默认查询排序前五个，不需要返回总数
		List<AdvertisementDto> adBannerDtos = baseDao.queryForList("Advertisement.queryAdvertisement", paraMap, AdvertisementDto.class);

		if ( adBannerDtos == null || adBannerDtos.isEmpty()) {
			adBannerDtos = baseDao.queryForList("Advertisement.queryAdvertisementDefault", paraMap, AdvertisementDto.class);
		}
		List<Map<String, Object>> list = baseDao.queryForList("AdNotice.getAdNoticeNewest", paraMap);
		if ( list.size() == 0) {
			paraMap.put("cityName", "默认");
			list = baseDao.queryForList("AdNotice.getAdNoticeNewest", paraMap);
		}
		adAllDto.setAdBannerDtos(adBannerDtos);
		StringBuilder notice = new StringBuilder();
		for (int i = 0, j = list.size(); i < j; i++) {
			//将有效的 广告 通过空格连接起来
			if ( ParamProcessUtil.mapKeyIsEmpty(list.get(i), "content")) {
				notice.append(list.get(i).get("content").toString());
				if ( i != list.size() - 1) {
					notice.append("    ");
				}
			}
		}
		adAllDto.setNotice(notice.toString());
		return result.withResult(adAllDto);
	}

	/**
	 * 列出当前时间定时任务自动上架的广告
	 * @return
	 * @throws BizException
	 */
	private List<AdTaskDTO> listToBePutOnAds() throws BizException {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 5);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("afterFiveMinTime", DateUtil.toString(calendar.getTime(), DateUtil.DATE_FORMAT_DATETIME));
		paramMap.put("nowTime", DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
		List<AdTaskDTO> adList = baseDao.queryForList("Advertisement.listToBePutOnAds", paramMap, AdTaskDTO.class);
		return adList;
	}

	/**
	 * 相同客户端、相同城市、上架状态的条件下,广告数量，用于判断自动上架广告是否可以上架
	 * @param cityId
	 * @param channel
	 * @param sort
	 * @return
	 * @throws BizException
	 */
	private Integer countPutOnAd(Integer cityId, Byte channel, Integer sort) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityId", cityId);
		paramMap.put("channel", channel);
		paramMap.put("sort", sort);
		paramMap.put("nowTime", DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
		Integer count = baseDao.queryForObject("Advertisement.countPutOnAd", paramMap, Integer.class);
		return count;
	}

	/**
	 * 修改状态
	 * @param id
	 * @param status
	 * @throws BizException
	 */
	private void updateStatus(Integer id, Byte status) throws BizException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("status", status);
		paramMap.put("updateUserId", "system");
		baseDao.execute("Advertisement.updateStatus", paramMap);
	}

	@Override
	public void putOnAdvertisement() throws BizException {
		List<AdTaskDTO> adList = listToBePutOnAds();
		if (adList == null) {
			return;
		}
		
		for (AdTaskDTO dto : adList) {
			// 相同客户端、相同城市、上架状态的条件下,广告数量
			Integer adCount = countPutOnAd(dto.getCityId(), dto.getChannel(), dto.getSort());
			if (adCount != null && adCount > 0) {
				continue;
			}
			// 进行上架操作
			updateStatus(dto.getId(), AdStatusEnum.ON.getCode());
		}
	}
}
