package cn.gdeng.nst.server.member.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.gdeng.nst.api.dto.member.RuleLogisticDTO;
import cn.gdeng.nst.api.dto.member.RuleOnoffDTO;
import cn.gdeng.nst.api.dto.redis.AreaDTO;
import cn.gdeng.nst.api.server.member.RuleOnoffApiService;
import cn.gdeng.nst.api.vo.member.MemberCarPageVo;
import cn.gdeng.nst.api.vo.member.RuleOnoffDetailVo;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.entity.nst.RuleOnoffEntity;
import cn.gdeng.nst.enums.AppTypeEnum;
import cn.gdeng.nst.enums.IsSelectEnum;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.enums.NstRuleOnoffEnum;
import cn.gdeng.nst.util.server.bo.CacheBo;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class RuleOnoffApiServiceImpl implements RuleOnoffApiService{
    
    @Resource
    private BaseDao<?> baseDao;
    @Autowired
	private CacheBo cacheBo;

    /**
     * 修改订阅地址
     * @param  id
     * @return 影响条数
     */
	@Override
    @Transactional
	public ApiResult<Integer>  updateRuleOnoffAddressById(RuleOnoffDTO ruleOnoffDTO) throws BizException {
		if(null==ruleOnoffDTO.getMemberId()){
			throw new BizException(MsgCons.C_20001, MsgCons.M_20001);
		}
		if(null==ruleOnoffDTO.getCityName()&&null==ruleOnoffDTO.getCityId()){
			throw new BizException(MsgCons.C_20001, MsgCons.M_20001);
		}
		if(null!=ruleOnoffDTO.getCityName()&&null==ruleOnoffDTO.getCityId()){//是否为定位地址数据
			AreaDTO areaDto=cacheBo.getAreaByName(ruleOnoffDTO.getCityName(),false, baseDao);//不是省
			if(null==areaDto){
				throw new BizException(MsgCons.C_20001, MsgCons.M_20001);
			}
			ruleOnoffDTO.setCityId(Integer.parseInt(areaDto.getAreaID()));
			if(null==areaDto.getFather()){//是否为直辖市
				ruleOnoffDTO.setProvinceId(Integer.parseInt(areaDto.getAreaID()));
			}else{
				ruleOnoffDTO.setProvinceId(Integer.parseInt(areaDto.getFather()));
			}
			String sDetail = ruleOnoffDTO.getDetail();
			if(null==sDetail){
				throw new BizException(MsgCons.C_20001, MsgCons.M_20001);
			}
			if (ruleOnoffDTO.getDetail().startsWith("/")) {
				Integer id = ruleOnoffDTO.getProvinceId();
				AreaDTO dto=cacheBo.getAreaByName(id.toString(), baseDao);
				sDetail = dto.getArea() + sDetail;
				ruleOnoffDTO.setDetail(sDetail);
			}
		}
		 ApiResult<Integer> apiResult =new  ApiResult<Integer>();
		int re=baseDao.execute("RuleOnoffApi.updateRuleOnoffAddressById",ruleOnoffDTO);
		apiResult.setResult(re);
	     return apiResult;
	}
	/**
     * 修改订阅状态
     * @param  id
     * @return 影响条数
     */
	@Override
    @Transactional
	public ApiResult<Integer>  updateRuleOnoffById(RuleOnoffDTO ruleOnoffDTO) throws BizException {
		if(null==ruleOnoffDTO.getMemberId()){
			throw new BizException(MsgCons.C_20001, MsgCons.M_20001);
		}
		int RuleCount=baseDao.queryForObject("RuleOnoffApi.RuleOnoffCountByMemberId", ruleOnoffDTO, Integer.class);
		if(ruleOnoffDTO.getOnOff()==NstRuleOnoffEnum.on.getCode()){//开启需判断 是否有对应物流规则
			Map<String, Object> param=new HashMap<String, Object> ();
			//1.是否有货主直接指派
			param.put("memberIdLogistic",ruleOnoffDTO.getMemberId());
			long ruleLogisticAssignCount = baseDao.queryForObject("RuleLogisticAssign.countValidByMemberIdLogistic", param, Long.class);
			//没有指派，则需要进一步判断是否有配置物流规则
			if (ruleLogisticAssignCount <= 0) {
				int count=baseDao.queryForObject("RuleOnoffApi.ruleLogisticCountByMemberId", ruleOnoffDTO, Integer.class);
				if(count==0){
					throw new BizException(MsgCons.C_22100, MsgCons.M_22100);
				}
			}
			//2017-2-22新需求  车主开启收货模式  引导用户去设置承运车辆    AppTypeEnum枚举  车主货主定义反
			if(Integer.parseInt(ruleOnoffDTO.getDeviceApp())==2){
				Map<String, Object> map =new HashMap<>();
				map.put("memberId", ruleOnoffDTO.getMemberId());
				map.put("isCarriage",IsSelectEnum.SELECT.getCode());
				List<MemberCarPageVo> carList=baseDao.queryForList("MemberCarApi.queryCarrierCar", map,MemberCarPageVo.class);
				if(CollectionUtils.isEmpty(carList)){
					throw new BizException(MsgCons.C_21028, MsgCons.M_21028);
				}
			}
		}
	    if(RuleCount==0){
	         RuleOnoffEntity re=new RuleOnoffEntity();
			 re.setMemberId(ruleOnoffDTO.getMemberId());
			 re.setOnOff(ruleOnoffDTO.getOnOff());
			 baseDao.persist(re,Long.class);
	       }
		int re=baseDao.execute("RuleOnoffApi.updateRuleOnoffById",ruleOnoffDTO);
		 ApiResult<Integer> apiResult =new ApiResult<Integer>();
		 apiResult.setResult(re);
	     return apiResult;
	}

    /**
     * 根据ID 查订阅开关详情
     * @param RuleOnoffDTO
     */
	@Override
	public ApiResult<RuleOnoffDetailVo> queryRuleOnoffById(
			RuleOnoffDTO ruleOnoffDTO) throws BizException {
		if(null==ruleOnoffDTO.getMemberId()){
			throw new BizException(MsgCons.C_20001, MsgCons.M_20001);
		}
		//用户是否有对应分配规则
		int count=baseDao.queryForObject("RuleOnoffApi.ruleLogisticCountByMemberId", ruleOnoffDTO, Integer.class);
		RuleOnoffDetailVo vo = baseDao.queryForObject("RuleOnoffApi.queryRuleOnoffById", ruleOnoffDTO, RuleOnoffDetailVo.class);  
		if(null==vo){
			vo=new RuleOnoffDetailVo();
		}
		if(count>0){
			vo.setFlag(1);
		}else{
			vo.setFlag(0);
		}
        ApiResult<RuleOnoffDetailVo> apiResult = new ApiResult<RuleOnoffDetailVo>(vo,MsgCons.C_10000,MsgCons.M_10000);
        return apiResult;
	}

	
	@Override
	public ApiResult<?> IsCanAcceptedGoods(Map<String, Object> param) throws BizException {
		//有接受分配权限的两种情况：1、货主直接指派；2、后台有为当前会员id配置物流规则。
		
		ApiResult<Object> result = new ApiResult<Object>();
		//制造不同的参数key以适应其他sql查询参数。
		param.put("memberIdLogistic", param.get("memberId"));
		
		//1.是否有货主直接指派
		long ruleLogisticAssignCount = baseDao.queryForObject("RuleLogisticAssign.countValidByMemberIdLogistic", param, Long.class);
		//没有指派，则需要进一步判断是否有配置物流规则
		if (ruleLogisticAssignCount <= 0) {
			//2.查询后台是否有为当前会员id配置物流规则。
			RuleLogisticDTO ruleLogisticDTO = baseDao.queryForObject("RuleLogistic.queryValidByMemberId", param, RuleLogisticDTO.class);
			if (ruleLogisticDTO ==  null) {
				return result.withError(MsgCons.C_22100, MsgCons.M_22100);
			}
		}
		
		//开关状态1:关闭;2:开启
		RuleOnoffDTO ruleOnoffDTO = baseDao.queryForObject("RuleOnoffApi.queryRuleOnoffById", param, RuleOnoffDTO.class);     		
		if (ruleOnoffDTO ==  null || (ruleOnoffDTO != null && ruleOnoffDTO.getOnOff() != 2)) {
			result.setResult(1);
		}else{
			result.setResult(2);
		}
		return result;
	}
}
