package cn.gdeng.nst.api.server.member;

import java.util.List;
import java.util.Map;

import cn.gdeng.nst.api.dto.member.MemberCarApiDTO;
import cn.gdeng.nst.api.vo.member.MemberCarDetailVo;
import cn.gdeng.nst.api.vo.member.MemberCarPageVo;
import cn.gdeng.nst.entity.nst.MemberCarEntity;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;

public interface MemberCarApiService {
	   /**
     * 用户车辆列表
     * @param page对象
     */
	public ApiResult<ApiPage>  queryPage(ApiPage page) throws BizException;
	/**
     * 删除车辆（逻辑删除）
     * @param  id
     * @return 影响条数
     */
	public ApiResult<Integer>  deleteMemberCarById(MemberCarApiDTO memberCarApiDTO) throws BizException;
	/**
	 * 添加车辆
	 * @param 用户车辆实体
	 */
	public ApiResult<Long>  saveMemberCar(MemberCarEntity memberCarEntity) throws BizException;
	/**
	 * 修改车辆
	 * @param 用户车辆DTO 
	 */
	public ApiResult<Integer>  updateMemberCar(MemberCarApiDTO memberCarApiDTO) throws BizException;
	/**
	 * 车辆详情
	 * @param 用户车辆DTO
	 */
	public ApiResult<MemberCarDetailVo>  queryMemberCarDetailById(MemberCarApiDTO memberCarApiDTO) throws BizException;
	/**
	 * 重复车牌号数
	 * @param 用户车辆DTO
	 */
	public ApiResult<Integer> queryMemberCarNumberNumber(MemberCarApiDTO memberCarApiDTO) throws BizException;
	/**
	 * 车辆总数（暂没使用）
	 * @param map
	 */
	public ApiResult<Integer> getTotal(Map<String,Object> map);
	
	/**
	 * 查询用户承运车辆
	 * @return
	 * @throws BizException
	 */
	public ApiResult<List<MemberCarPageVo>> queryMemberCarrierCarList(Map<String, Object> map)throws BizException;
	
	/**
	 * 更新用户承运车辆
	 * @param map
	 */
	public ApiResult<Integer> updateMemberCarrierCar(Map<String,Object> map)throws BizException;
}
