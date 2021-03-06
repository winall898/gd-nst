package cn.gdeng.nst.api.dto.source;

import java.io.Serializable;

/**
 * 货源规则分配所需DTO
 * 
 * @author xiaojun
 */
public class RuleDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1337200301490989926L;
	/**
	 * 货源id
	 */
	private Integer sourceId;
	/**
	 * 货主id
	 */
	private Integer shipperMemberId;

	/**
	 * 货源类别: 1干线 2同城
	 */
	private Integer sourceType;
	/**
	 * 1 自由 2 分配 3 指派
	 */
	private Integer sourceGoodsType;

	/**
	 * 货源分配的次数
	 */
	private Integer assignCount;
	/**
	 * 货源分配的状态1 货主直发 2 代发 3分配中 4物流公司直发
	 */
	private Integer nstRule;
	/**
	 * 2 农速通 货主 3 农速通 物流公司 4 农商友 5 农商友 - 农批商 6 农商友-供应商
	 */
	private Integer clients;
	/**
	 * 0 非平台配送 1 平台配送
	 */
	private Integer platform;
	/**
	 * 分配memberId
	 */
	private Integer assignMemberId;

	/**
	 * 出发地省会Id
	 */
	private Integer s_provinceId;
	/**
	 * 出发地城市Id
	 */
	private Integer s_cityId;
	/**
	 * 出发地区域Id
	 */
	private Integer s_areaId;
	/**
	 * 目的地省会Id
	 */
	private Integer e_provinceId;
	/**
	 * 目的地城市Id
	 */
	private Integer e_cityId;
	/**
	 * 目的地区域Id
	 */
	private Integer e_areaId;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 规则类型：0 货主指派 1 物流规则
	 */
	private Integer ruleType;
	/**
	 * 规则id
	 */
	private Integer ruleInfoId;
	/**
	 * 刷选物流公司订阅线路使用
	 */
	private Integer memberId;
	/**
	 * 会员类型：2 车主 3 物流公司
	 */
	private Integer memberType;
	/**
	 * 货源状态: 1 已发布 2 待确认 3 已接受 4 已过期 5 已关闭(平台配送)
	 */
	private Integer sourceStatus;

	/**
	 * 2 农速通 货主 3 农速通 物流公司 4 农商友 5 农商友 - 农批商 6 农商友-供应商
	 */
	public Integer getClients() {
		return clients;
	}

	public void setClients(Integer clients) {
		this.clients = clients;
	}

	/**
	 * 货源状态: 1 已发布 2 待确认 3 已接受 4 已过期 5 已关闭(平台配送)
	 */
	public Integer getSourceStatus() {
		return sourceStatus;
	}

	/**
	 * 货源状态: 1 已发布 2 待确认 3 已接受 4 已过期 5 已关闭(平台配送)
	 */
	public void setSourceStatus(Integer sourceStatus) {
		this.sourceStatus = sourceStatus;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getRuleInfoId() {
		return ruleInfoId;
	}

	public void setRuleInfoId(Integer ruleInfoId) {
		this.ruleInfoId = ruleInfoId;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getAssignCount() {
		return assignCount;
	}

	public void setAssignCount(Integer assignCount) {
		this.assignCount = assignCount;
	}

	public Integer getNstRule() {
		return nstRule;
	}

	public void setNstRule(Integer nstRule) {
		this.nstRule = nstRule;
	}

	/**
	 * 0 非平台配送 1 平台配送
	 * 
	 * @return
	 */
	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getAssignMemberId() {
		return assignMemberId;
	}

	public void setAssignMemberId(Integer assignMemberId) {
		this.assignMemberId = assignMemberId;
	}

	public Integer getShipperMemberId() {
		return shipperMemberId;
	}

	public void setShipperMemberId(Integer shipperMemberId) {
		this.shipperMemberId = shipperMemberId;
	}

	/**
	 * 货源类别: 1干线 2同城
	 */
	public Integer getSourceType() {
		return sourceType;
	}

	/**
	 * 货源类别: 1干线 2同城
	 */
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getS_provinceId() {
		return s_provinceId;
	}

	public void setS_provinceId(Integer s_provinceId) {
		this.s_provinceId = s_provinceId;
	}

	public Integer getS_cityId() {
		return s_cityId;
	}

	public void setS_cityId(Integer s_cityId) {
		this.s_cityId = s_cityId;
	}

	public Integer getS_areaId() {
		return s_areaId;
	}

	public void setS_areaId(Integer s_areaId) {
		this.s_areaId = s_areaId;
	}

	public Integer getE_provinceId() {
		return e_provinceId;
	}

	public void setE_provinceId(Integer e_provinceId) {
		this.e_provinceId = e_provinceId;
	}

	public Integer getE_cityId() {
		return e_cityId;
	}

	public void setE_cityId(Integer e_cityId) {
		this.e_cityId = e_cityId;
	}

	public Integer getE_areaId() {
		return e_areaId;
	}

	public void setE_areaId(Integer e_areaId) {
		this.e_areaId = e_areaId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSourceGoodsType() {
		return sourceGoodsType;
	}

	public void setSourceGoodsType(Integer sourceGoodsType) {
		this.sourceGoodsType = sourceGoodsType;
	}

}
