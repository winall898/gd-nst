package cn.gdeng.nst.server.pub.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.gdeng.nst.api.vo.pub.AssignVO;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.enums.NsyAssignEnum;
import cn.gdeng.nst.pub.service.RuleLogisticAssignService;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 农商友校验是否分配物流公司
 * 
 * @author wj
 */
@Service
public class RuleLogisticAssignServiceImpl implements RuleLogisticAssignService{

	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public ApiResult<AssignVO> validateAssign(Map<String, Object> paraMap) throws BizException{
		ApiResult<AssignVO> result = new ApiResult<AssignVO>();
		AssignVO vo = new AssignVO();
		int count = baseDao.queryForObject("RuleLogisticAssign.validateAssign", paraMap, Integer.class);
		if(count>0){
			vo.setIsAssign(NsyAssignEnum.ASSIGNED.getCode().toString());
		}else{
			vo.setIsAssign(NsyAssignEnum.NOASSIGN.getCode().toString());
		}
		return result.setResult(vo);
	}

}
