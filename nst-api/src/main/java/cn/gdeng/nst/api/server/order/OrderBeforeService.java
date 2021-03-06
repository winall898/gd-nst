package cn.gdeng.nst.api.server.order;

import java.util.Map;

import cn.gdeng.nst.api.dto.order.OrderBeforeDetailDTO;
import cn.gdeng.nst.entity.nst.OrderBeforeEntity;
import cn.gdeng.nst.util.web.api.ApiPage;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;

/**
 * 预运单Service
 * 
 * @author huangjianhua  2016年8月1日  上午11:16:41
 * @version 1.0
 */
public interface OrderBeforeService {
	
	/**
	 * 创建预运单信息(司机接单)
	 * @param beforeDTO
	 * @return
	 */
	public ApiResult<Integer> createOrderBefore(OrderBeforeEntity orderBeforeEntity) throws BizException;
	
	
	/**
	 * 司机取消接单
	 * @param beforeDTO
	 * @return
	 */
	public ApiResult<Integer> cancleOrderBefore(Map<String, Object> map)throws BizException;
	
	/**
	 * 查询预运单详情
	 * @param id
	 * @return
	 */
	public ApiResult<OrderBeforeDetailDTO> queryOrderBefore(Map<String, Object> map)throws BizException;
	
	
	
	
	
	/**
	 * 分页查询预运单
	 * @param page
	 * @return
	 */
	public ApiResult<ApiPage> queryOrderBeforePage(ApiPage page)throws BizException;
	
	/**
	 * 分页查询预运单
	 * @param page
	 * @return
	 */
	public ApiResult<ApiPage> queryOrderBeforePageV2(ApiPage page)throws BizException;
	

	/**拒接预订单
	 * @param param
	 * @return 结果集对象，数据为影响的行数
	 * @throws BizException
	 */
	public ApiResult<Map<String, Object>> rejectOrderBefore(Map<String, Object> param) throws BizException;
	
	
	/**
	 * 
	 * @Description:   判断司机是否还可以继续接单  
	 * @param param
	 * @return
	 * @throws BizException
	 *
	 */
	ApiResult<Boolean> queryOrderQuantity(Map<String, Object> param) throws BizException;
	
	/**
	 * 
	 * @Description: 判断用户是否被禁用
	 * @param memberId
	 * @throws BizException
	 *
	 */
	void checkoutMember(String memberId) throws BizException;
	
	/**
	 * 
	 * @Description: 车主确认送达
	 * @param param
	 * @return
	 * @throws BizException
	 *
	 */
	ApiResult<Boolean> deliveryConfirmation(Map<String, Object> param) throws BizException;
	
	/**
	 * 定时任务   车主 验货超时 3 天
	 */
	void examineCargoTimeOut();
	
}
