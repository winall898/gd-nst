package cn.gdeng.nst.server.source.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;

import cn.gdeng.nst.api.server.source.PlatformService;
import cn.gdeng.nst.dao.BaseDao;
import cn.gdeng.nst.util.web.api.ApiResult;
/**
 * 平台配送相关Service 实现
 * @author xiaojun
 *
 */
@Service
public class PlatformServiceImpl implements PlatformService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private BaseDao<?> baseDao;
	@Override
	public ApiResult<?> goodsClose(Map<String, Object> map) {
		ApiResult<?> apiResult =new ApiResult<>();
		baseDao.execute("SourceShipper.platformGoodsClose", map);
		baseDao.execute("SourceShipper.updateSourceAssignHisClose", map);
		baseDao.execute("OrderInfo.updateOrderInfoClose", map);
		logger.info("平台配送采购单取消，货源关闭id："+map.get("sourceId"));
		return apiResult;
	}

}
