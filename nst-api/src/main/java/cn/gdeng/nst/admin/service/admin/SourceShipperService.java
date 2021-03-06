package cn.gdeng.nst.admin.service.admin;

import java.util.List;
import java.util.Map;

import cn.gdeng.nst.admin.dto.admin.AdminPageDTO;
import cn.gdeng.nst.admin.dto.admin.AdminSourceShipperDTO;
import cn.gdeng.nst.util.web.api.ApiResult;

/**
 * 货源管理service
 * @author jfdeng
 *
 */
public interface SourceShipperService {

	/**
	 * 分页查询：包含总记录数，分页列表数据
	 * @param paramMap
	 * @return
	 */
	ApiResult<AdminPageDTO> queryPage(Map<String, Object> paramMap);
	
	/**
	 * 根据id获取详情
	 * @param id
	 * @return
	 */
	ApiResult<AdminSourceShipperDTO> getById(Integer id);
	
	/**
	 * 修改
	 * @param sourceShipperDTO
	 * @return
	 */
	ApiResult<Integer> update(AdminSourceShipperDTO sourceShipperDTO);

	/**
	 * 批量删除（逻辑删除，修改isDeleted字段）
	 * @param ids
	 * @param loginUserId
	 * @return
	 */
	ApiResult<int[]> batchDelete(String[] ids, String loginUserId);
	
	/**
	 * 获取总记录数
	 * @param paramMap
	 * @return
	 */
	ApiResult<Integer> countTotal(Map<String, Object> paramMap);
	
	/**
	 * 分页获取列表数据
	 * @param paramMap
	 * @return
	 */
	ApiResult<List<AdminSourceShipperDTO>> queryListByPage(Map<String, Object> paramMap);
}
