package cn.gdeng.nst.server.member.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

import cn.gdeng.nst.api.server.member.MemberInfoApiService;
import cn.gdeng.nst.api.vo.member.MemberInfoVo;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.entity.nst.MemberInfoEntity;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;
import cn.gdeng.nst.util.web.api.GdProperties;

/**
 * 会员信息service实现类
 * @author Administrator
 *
 */
@Service
public class MemberInfoApiServiceImpl implements MemberInfoApiService {
    @Autowired
    private GdProperties gdProperties;
    @Resource
    private BaseDao<?> baseDao;
    
    @Override
    public ApiResult<MemberInfoVo> findMember(Map<String,Object> map) throws BizException{
        MemberInfoVo vo = baseDao.queryForObject("MemberInfo.findMember", map, MemberInfoVo.class);
        ApiResult<MemberInfoVo> apiResult = new ApiResult<MemberInfoVo>(vo,MsgCons.C_10000,MsgCons.M_10000);
        return apiResult;
    }

    @Override
    @Transactional
    public ApiResult<Integer> updateByMap(Map<String, Object> map) throws BizException{
        Integer total = baseDao.execute("MemberInfo.updateByMap", map);
        ApiResult<Integer> apiResult = new ApiResult<Integer>(total,MsgCons.C_10000,MsgCons.M_10000);
        return apiResult;
    }

	@Override
	public ApiResult<MemberInfoEntity> getById(Integer id) throws BizException{
		MemberInfoEntity paramEntity = new MemberInfoEntity();
		paramEntity.setId(id);
		MemberInfoEntity resultEntity = baseDao.find(MemberInfoEntity.class, paramEntity);
		ApiResult<MemberInfoEntity> apiResult = new ApiResult<MemberInfoEntity>();
		apiResult.setResult(resultEntity);
		return apiResult;
	}

	@Override
	public ApiResult<Map<String,Object>> checkMemberOrCar(Map<String, Object> map) throws BizException{
		Map<String,Object> resultMap = baseDao.queryForMap("MemberInfo.checkMemberOrCar", map);
		ApiResult<Map<String,Object>> apiResult = new ApiResult<Map<String,Object>>(resultMap,MsgCons.C_10000,MsgCons.M_10000);
	    return apiResult;
	}

	@Override
	public ApiResult<Map<String, Object>> findCarMember(Map<String, Object> map) throws BizException{
		Map<String,Object> resultMap = baseDao.queryForMap("MemberInfo.findCarMember", map);
		ApiResult<Map<String,Object>> apiResult = new ApiResult<Map<String,Object>>(resultMap,MsgCons.C_10000,MsgCons.M_10000);
	    return apiResult;
	}

}
