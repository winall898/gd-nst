package cn.gdeng.nst.admin.controller.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.nst.admin.controller.right.AdminBaseController;
import cn.gdeng.nst.admin.dto.admin.AdminOrderInfoDTO;
import cn.gdeng.nst.admin.dto.admin.AdminPageDTO;
import cn.gdeng.nst.admin.service.admin.OrderInfoService;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.util.admin.web.CommonUtil;
import cn.gdeng.nst.util.admin.web.DateUtil;
import cn.gdeng.nst.util.web.api.ApiResult;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 货运订单
 * @author dengjianfeng
 *
 */
@Controller
@RequestMapping("orderInfo")
public class OrderInfoController extends AdminBaseController{

	@Reference
	private OrderInfoService orderInfoService;
	
	/**
	 * 进入货运订单列表页
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return "orderInfo/list";
	}
	
	/**
	 * 货运订单分页查询
	 * @param request
	 * @return
	 */
	@RequestMapping("queryPage")
	@ResponseBody
	public String queryPage(HttpServletRequest request){
		Map<String, Object> paramMap = getParametersMap(request); 
		setCommParameters(request, paramMap);
		formatParamTime(paramMap);
		
		ApiResult<AdminPageDTO> apiResult = orderInfoService.queryPage(paramMap);
		if(apiResult != null){
			return JSONObject.toJSONString(apiResult.getResult(), SerializerFeature.WriteDateUseDateFormat);
		}
		return null;
	}
	
	/**
	 * 查看货运订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping("detail/{id}")
	public String detail(@PathVariable("id")Integer id){
		ApiResult<AdminOrderInfoDTO> apiResult = orderInfoService.getById(id);
		if(apiResult != null){
			putModel("orderInfoDTO", apiResult.getResult());
		}
		return "orderInfo/detail";
	}
	
	/**
	 * 导出检测
	 * @param request
	 * @return
	 */
	@RequestMapping(value="exportCheck",produces="application/json;charset=utf-8")
	@ResponseBody
	public String exportCheck(HttpServletRequest request){
		//查询参数
		Map<String, Object> paramMap = getParametersMap(request);
		formatParamTime(paramMap);
		
		ApiResult<Integer> apiResult = orderInfoService.countTotal(paramMap);
		if(apiResult != null && apiResult.getResult() != null){
			int total = apiResult.getResult();
			if(total <= 0){
				return JSONObject.toJSONString(new ApiResult<String>().withError(MsgCons.C_29005, MsgCons.M_29005));
			}
			else if(total > EXPORT_MAX_SIZE){
				return JSONObject.toJSONString(new ApiResult<String>().withError(MsgCons.C_29006, MsgCons.M_29006));
			}
		} else {
			return JSONObject.toJSONString(new ApiResult<String>().withError(MsgCons.C_50000, MsgCons.M_50000));
		}
		return JSONObject.toJSONString(new ApiResult<String>());
	}
	
	/**
	 * 导出数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("export")
	public void export(HttpServletRequest request, HttpServletResponse response){
		//查询参数
		Map<String, Object> paramMap = getParametersMap(request);
		formatParamTime(paramMap);
		paramMap.put(END_ROW, EXPORT_PAGE_SIZE);

		OutputStream ouputStream = null;
		try{
			String fileName = "货运订单列表"+DateUtil.toString(new Date(), "yyyy-MM-dd_HHmmss");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xlsx");
            ouputStream = response.getOutputStream();
            
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            String[] headers = {"货运订单号", "订单生成时间", "发货地", "目的地", "货物类型", "发布人", "发布人手机",
            		"车主", "车主手机", "货运订单类型", "意向运费", "发货方式", "物流状态", "订单状态", "支付状态"};
        
			Sheet sheet = createSheet(workbook, headers);
            
			// 查询导出数据总数
			ApiResult<Integer> countApiResult = orderInfoService.countTotal(paramMap);
			int totalCount = 0;
            if(countApiResult != null){
            	totalCount = countApiResult.getResult();
            }
            // 计算分几次查询导出数据
            int exportCount = (totalCount / EXPORT_PAGE_SIZE) + 1;
            
            int rowNum = 1;
            for(int i = 0; i < exportCount; i++){
            	// 查询分页数据
            	paramMap.put(START_ROW, (i * EXPORT_PAGE_SIZE));
        		ApiResult<List<AdminOrderInfoDTO>> apiResult = orderInfoService.queryListByPage(paramMap);
        		List<AdminOrderInfoDTO> list = null;
        		if(apiResult != null){
        			list = apiResult.getResult();
        		}
        		
        		if(CollectionUtils.isEmpty(list)){
        			break;
        		}
        		
        		for(int j = 0, len = list.size(); j < len; j++){
                   	AdminOrderInfoDTO dto = list.get(j);
                   	writeRowData(sheet, rowNum, dto);
                    rowNum++;
               	}
            }
            workbook.write(ouputStream);   
		}catch(Exception e){
			e.printStackTrace();
		}finally {
            try {
               ouputStream.flush();
               ouputStream.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
	}
	
	/**
	 * 创建一个sheet，并在第一行写入表头
	 * @param workbook
	 * @param sheetNum
	 * @param headers
	 * @return
	 */
	private Sheet createSheet(SXSSFWorkbook workbook, String[] headers){
		String sheetName = "货运订单数据";
		Sheet sheet = workbook.createSheet(sheetName);
		Row row = sheet.createRow(0);
        for(int i = 0, len = headers.length; i < len; i++){
        	row.createCell(i).setCellValue(headers[i]);
        }
        return sheet;
	}
	
	/**
	 * 填充dto字段到row
	 * @param sheet
	 * @param rowNum
	 * @param dto
	 */
	private void writeRowData(Sheet sheet, int rowNum, AdminOrderInfoDTO dto){
		Row row = sheet.createRow(rowNum);
		
		String orderNo = dto.getOrderNo() == null ? "" : dto.getOrderNo();
		String createTime = dto.getCreateTime() == null ? "" : DateUtil.toString(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATETIME);
		String sDetail = dto.getSDetailStr() == null ? "" : dto.getSDetailStr();
		String eDetail = dto.getEDetailStr() == null ? "" : dto.getEDetailStr();
		String goodsType = dto.getGoodsTypeStr() == null ? "" : dto.getGoodsTypeStr();
		String sourceMemberName = dto.getShipperName() == null ? "" : dto.getShipperName();
		String sourceMemberMobile = dto.getShipperMobile() == null ? "" : dto.getShipperMobile();
		String driverName = dto.getDriverName() == null ? "" : dto.getDriverName();
		String driverMobile = dto.getDriverMobile() == null ? "" : dto.getDriverMobile();
		String sourceType = dto.getSourceTypeStr() == null ? "" : dto.getSourceTypeStr();
		String freight = null;
		if (dto.getFreight() == null || dto.getFreight().doubleValue() == 0) {
			freight = "面议";
		} else {
			freight = dto.getFreight().toString();
		}
		String sendGoodsType = dto.getSendGoodsTypeStr() == null ? "" : dto.getSendGoodsTypeStr();
		String transStatus = "";
		byte transStatus$Int = dto.getTransStatus() == null ? 0 : dto.getTransStatus();
		if (transStatus$Int == 1) {
			transStatus = "待验货";
		} else if (transStatus$Int == 2) {
			transStatus = "已发货";
		} else if (transStatus$Int == 3) {
			transStatus = "已送达";
		} else if (transStatus$Int == 4) {
			transStatus = "验货不通过";
		} else if (transStatus$Int == 5) {
			transStatus = "已拒收";
		}
		String orderStatus = dto.getOrderStatusStr() == null ? "" : dto.getOrderStatusStr();
		String payStatus = dto.getPayStatusStr() == null ? "" : dto.getPayStatusStr();
		
		String[] data = {orderNo, createTime, sDetail, eDetail, goodsType, sourceMemberName,
				sourceMemberMobile, driverName, driverMobile, sourceType, freight, sendGoodsType,
				transStatus, orderStatus, payStatus};
		
		for (int i = 0, len = data.length; i < len; i++) {
			row.createCell(i).setCellValue(data[i]);
		}
      
	}
	
	/**
	 * 查询时间参数格式化：开始时间为yyyy-MM-dd 00:00:00;结束时间为yyyy-MM-dd 23:59:59
	 * @param paramMap
	 */
	private void formatParamTime(Map<String, Object> paramMap) {
		String startDate = (String) paramMap.get("startDate");
		String endDate = (String) paramMap.get("endDate");
		if(StringUtils.isNotBlank(startDate)){
			String startTime = CommonUtil.getStartOfDay(DateUtil.formateDate(startDate));
			paramMap.put("startTime", startTime);
		}
		if(StringUtils.isNotBlank(endDate)){
			String endTime = CommonUtil.getEndOfDay(DateUtil.formateDate(endDate));
			paramMap.put("endTime", endTime);
		}
	}
}
