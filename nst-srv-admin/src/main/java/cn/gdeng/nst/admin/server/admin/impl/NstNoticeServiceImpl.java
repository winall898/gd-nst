package cn.gdeng.nst.admin.server.admin.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.gdeng.nst.admin.dto.admin.AdminPageDTO;
import cn.gdeng.nst.admin.dto.admin.NstNoticeEntityDTO;
import cn.gdeng.nst.admin.service.admin.NstNoticeService;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.CommonConstant;

/**
 * 农速通公告服务实现类
 * 
 */
@Service
public class NstNoticeServiceImpl implements NstNoticeService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public ApiResult<Integer> insert(Map<String, Object> map)throws Exception{
		ApiResult<Integer> apiResult = new ApiResult<Integer>();
			int status = baseDao.execute("nstNotice.insert", map);
			return apiResult.setResult(status);
	}

	@Override
	public ApiResult<Integer> update(Map<String, Object> map)throws Exception{
		ApiResult<Integer> result = new ApiResult<Integer>();
			result.setResult(baseDao.execute("nstNotice.update", map));
			return result;
	}

    @SuppressWarnings("unchecked")
	public ApiResult<String> delete(String ids){
    	ApiResult<String> result = new ApiResult<String>();
    	try{
            String[] idAry = ids.split(",");
            int len = idAry.length;
            Map<String, Object>[] batchValues = new HashMap[len];
            for (int i = 0; i < len; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", StringUtils.trim(idAry[i]));
                batchValues[i] = map;
            }
            String isSuccess = baseDao.batchUpdate("nstNotice.delete", batchValues).length > 0 ? CommonConstant.COMMON_AJAX_SUCCESS : CommonConstant.COMMON_AJAX_ERROR;
            return result.setResult(isSuccess);
    	}catch(Exception e){
    		logger.error("", e);
    		result.withError(MsgCons.C_50000, MsgCons.M_50000);
    		return result;
    	}
    }

	@Override
	public ApiResult<NstNoticeEntityDTO> selectById(Map<String, Object> map){
		ApiResult<NstNoticeEntityDTO> result = new ApiResult<NstNoticeEntityDTO>();
		try{
			result.setResult(baseDao.queryForObject("nstNotice.selectById", map, NstNoticeEntityDTO.class));
			return result;
		}catch(Exception e){	
			logger.error("", e);
			return result.withError(MsgCons.C_50000, MsgCons.M_50000);
		}
	}

	@Override
	public ApiResult<AdminPageDTO> queryPage(Map<String, Object> map) {
		ApiResult<AdminPageDTO> apiResult = new ApiResult<AdminPageDTO>();
		try{
			int total = baseDao.queryForObject("nstNotice.queryNstNoticeListByPageCount", map, Integer.class);
			List<NstNoticeEntityDTO> list = baseDao.queryForList("nstNotice.queryNstNoticeListByPage", map, NstNoticeEntityDTO.class);
			AdminPageDTO page = new AdminPageDTO(total,list);
			apiResult.setResult(page);
			return apiResult;
		}catch(Exception e){
			logger.error("", e);
			return apiResult.withError(MsgCons.C_50000, MsgCons.M_50000);
		}
	}

	@Override
	public ApiResult<Integer> resetStatusById(Map<String, Object> paramMap) {
		ApiResult<Integer> result = new ApiResult<Integer>();
		try{
			result.setResult(baseDao.execute("nstNotice.updateOnOff", paramMap));
			return result;
		}catch(Exception e){
			logger.error("", e);
			return result.withError(MsgCons.C_50000, MsgCons.M_50000);
		}
	}
	
	@Override
	public ApiResult<Integer> queryTitleCount(Map<String, Object> map) {
		ApiResult<Integer> result = new ApiResult<Integer>();
		int total = baseDao.queryForObject("nstNotice.queryNstNoticeTitle",map,Integer.class);
		result.setResult(total);
		return result;
	}

@Override
public ApiResult<Integer> queryListCount(Map<String, Object> map) {
	ApiResult<Integer> result = new ApiResult<Integer>();
	int total = baseDao.queryForObject("nstNotice.queryNstNoticeListByPageCount",map,Integer.class);
	result.setResult(total);
	return result;
}

}
