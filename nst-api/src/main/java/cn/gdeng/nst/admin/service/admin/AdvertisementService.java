package cn.gdeng.nst.admin.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.nst.admin.dto.admin.AdminPageDTO;
import cn.gdeng.nst.admin.dto.admin.AdvertisementDTO;
import cn.gdeng.nst.admin.dto.admin.TreeNode;
import cn.gdeng.nst.entity.nst.AdvertisementEntity;
import cn.gdeng.nst.util.web.api.ApiResult;

/**
 * 广告管理
 * @author jfdeng
 *
 */
public interface AdvertisementService {
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	ApiResult<Long> add(AdvertisementEntity entity);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	ApiResult<AdminPageDTO> queryPage(Map<String, Object> map);
	
	/**
	 * 修改状态（上下架操作）
	 * @param advertiseDTO
	 * @return
	 */
	ApiResult<Integer> updateStatus(AdvertisementDTO advertiseDTO);
	
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	ApiResult<AdvertisementDTO> getById(Integer id);
	
	/**
	 * 修改
	 * @param advertiseDTO
	 * @return
	 */
	ApiResult<Integer> update(AdvertisementDTO advertiseDTO);
	
	/**
	 * 初始化广告选择省市树
	 * @return
	 */
	ApiResult<List<TreeNode>> queryForInitTree();

	/**
	 * 根据父级节点获取广告选择树孩子节点
	 * @param pid
	 * @return
	 */
	ApiResult<List<TreeNode>> queryAreaNodeByPid(String pid);
	
	/**
	 * 列表查询
	 * @return
	 */
	ApiResult<List<AdvertisementDTO>> listByConditions(Map<String, Object> map);
	
	/**
	 * 同步广告
	 * @param cityId
	 * @param adIds
	 * @param loginUserId
	 * @return
	 */
	ApiResult<String> syncAd(Integer cityId, Integer[] adIds, String loginUserId);
}
