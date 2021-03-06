package cn.gdeng.nst.web.controller.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.nst.api.dto.member.MemberCarApiDTO;
import cn.gdeng.nst.api.server.member.MemberCarApiService;
import cn.gdeng.nst.api.vo.member.MemberCarDetailVo;
import cn.gdeng.nst.entity.nst.MemberCarEntity;
import cn.gdeng.nst.enums.IsSelectEnum;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.web.controller.base.BaseController;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * 用户车辆controller
 * @author kwang
 *
 */
@Controller
@RequestMapping("memberCarApi")
public class MemberCarApiController  extends BaseController {

	@Reference
	private MemberCarApiService memberCarApiService;
	
	/**
	 * 分页查询用户车辆
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryMemberCarPage")
	@ResponseBody
	public Object queryMemberCarPage(HttpServletRequest request) throws Exception {
		ApiPage page = getPageInfoEncript(request);
		return memberCarApiService.queryPage(page);
	}
	
	/**
	 * 添加车辆
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("saveMemberCar")
	public Object saveMemberCar(HttpServletRequest request) throws Exception {
		MemberCarEntity memberCarEntity = getDecodeDto(request,MemberCarEntity.class);        
        ApiResult<Long> result = memberCarApiService.saveMemberCar(memberCarEntity);
        return result; 
	 }
	
	/**
	 * 删除用户车辆（逻辑删除）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("deleteMemberCarById")
	public Object deleteMemberCarById(HttpServletRequest request) throws Exception {
		MemberCarApiDTO memberCarApiDTO = getDecodeDto(request,MemberCarApiDTO.class);        
        ApiResult<Integer> result = memberCarApiService.deleteMemberCarById(memberCarApiDTO);
        return result; 
	 }
	
	/**
	 * 查询用户车辆详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("queryMemberCarDetailById")
	public Object queryMemberCarDetailById(HttpServletRequest request) throws Exception {
		MemberCarApiDTO memberCarApiDTO = getDecodeDto(request,MemberCarApiDTO.class);        
        ApiResult<MemberCarDetailVo> result = memberCarApiService.queryMemberCarDetailById(memberCarApiDTO);
        return result; 
	 }
	/**
	 * 查询用户车辆车牌号码是否重复
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("queryMemberCarNumber")
	public Object queryMemberCarNumber(HttpServletRequest request) throws Exception {
		MemberCarApiDTO memberCarApiDTO = getDecodeDto(request,MemberCarApiDTO.class);        
        ApiResult<Integer> result = memberCarApiService.queryMemberCarNumberNumber(memberCarApiDTO);
        return result; 
	 }
	
	/**
	 * 查询用户承运车辆
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryMemberCarrierCarList")
	@ResponseBody
	public Object queryMemberCarrierCarList(HttpServletRequest request) throws Exception {
		Map<String, Object> map= getDecodeMap(request);
		return memberCarApiService.queryMemberCarrierCarList(map);
	}
	
	/**
	 * 修改用户车辆为承运车辆
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMemberCarrierCar")
	@ResponseBody
	public Object updateMemberCarrierCar(HttpServletRequest request) throws Exception {
		Map<String, Object> map= getDecodeMap(request);
		return memberCarApiService.updateMemberCarrierCar(map);
	}
	
}
