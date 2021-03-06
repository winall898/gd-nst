package cn.gdeng.nst.pub.service;

import java.util.Map;

import cn.gdeng.nst.util.web.api.ApiResult;

/**
 * 农速通会员
 * 
 * @author xiaojun
 */
public interface MemberPublicService {
	
	/**
	 * 获取手机验证码
	 */
	ApiResult<?> getVerifyCode(Map<String, Object> map) throws Exception;

	/**
	 * 农速通注册下一步
	 * 
	 * @return
	 */
	ApiResult<?> registerNext(Map<String, Object> param) throws Exception;

	/**
	 * 农速通完成注册
	 * 
	 * @return
	 */
	ApiResult<?> register(Map<String, Object> param) throws Exception;

	/**
	 * 农速通登录
	 */
	ApiResult<?> login(Map<String, Object> param,String deviceType,String deviceTokens,String deviceApp,String appVersion) throws Exception;

	/**
	 * 找回密码下一步
	 * @return
	 */
	ApiResult<?> findPasswordNext(Map<String, Object> param) throws Exception;
	
	/**
	 * 更新业务类型
	 * @param serviceType
	 * @return
	 * @throws Exception
	 */
	ApiResult<?> selectServiceType(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 修改手机号码
	 * @return
	 */
	ApiResult<?> updateMobile(Map<String, Object> param) throws Exception;
	/**
	 * 修改联系人
	 * @return
	 * @throws Exception 
	 */
	ApiResult<?> updateUserName(Map<String, Object> paramMap) throws Exception;
	/**
     * 银行卡类型
     */
    ApiResult<?> bankCardType(Map<String, Object> paramMap) throws Exception;
	/**
	 * 绑定的银行卡信息
	 */
	ApiResult<?> bankCardInfo(Map<String, Object> paramMap) throws Exception;
	/**
	 * 保存绑定银行卡信息
	 */
	ApiResult<?> saveBankCardInfo(Map<String, Object> paramMap) throws Exception;
	/**
     * 修改绑定银行卡信息
     */
	ApiResult<?> updateBankCardInfo(Map<String, Object> paramMap) throws Exception;
	/**
     * app登陆统计
     */
	ApiResult<?> appStart(Map<String, Object> param,Object appVersion, String token) throws Exception;
}
