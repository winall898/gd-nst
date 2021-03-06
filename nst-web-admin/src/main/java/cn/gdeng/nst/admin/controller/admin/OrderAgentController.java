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

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.gdeng.nst.admin.controller.right.AdminBaseController;
import cn.gdeng.nst.admin.dto.admin.AdminOrderAgentDTO;
import cn.gdeng.nst.admin.dto.admin.AdminPageDTO;
import cn.gdeng.nst.admin.service.admin.OrderAgentService;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.util.admin.web.CommonUtil;
import cn.gdeng.nst.util.admin.web.DateUtil;
import cn.gdeng.nst.util.web.api.ApiResult;

/**
 * 信息订单
 * @author dengjianfeng
 *
 */
@Controller
@RequestMapping("orderAgent")
public class OrderAgentController extends AdminBaseController{

	@Reference
	private OrderAgentService orderAgentService;
	
	/**
	 * 进入信息订单列表页
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return "orderAgent/list";
	}
	
	/**
	 * 信息订单分页查询
	 * @param request
	 * @return
	 */
	@RequestMapping("queryPage")
	@ResponseBody
	public String queryPage(HttpServletRequest request){
		Map<String, Object> paramMap = getParametersMap(request);
		setCommParameters(request, paramMap);
		formatParamTime(paramMap);
		
		ApiResult<AdminPageDTO> apiResult = orderAgentService.queryPage(paramMap);
		if(apiResult != null){
			return JSONObject.toJSONString(apiResult.getResult(), SerializerFeature.WriteDateUseDateFormat);
		}
		return null;
	}
	
	/**
	 * 查看信息订单详情
	 * @param id
	 * @return
	 */
	@RequestMapping("detail/{id}")
	public String detail(@PathVariable("id") Integer id){
		ApiResult<AdminOrderAgentDTO> apiResult = orderAgentService.getById(id);
		if(apiResult != null){
			putModel("orderAgentDTO", apiResult.getResult());
		}
		return "orderAgent/detail";
	}
	
	/**
	 * 导出检测
	 * @param request
	 * @return
	 */
	@RequestMapping(value="exportCheck",produces="application/json;charset=utf-8")
	@ResponseBody
	public String exportCheck(HttpServletRequest request){
		Map<String, Object> paramMap = getParametersMap(request);
		formatParamTime(paramMap);
		
		ApiResult<Integer> apiResult = orderAgentService.countTotal(paramMap);
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
			String fileName = "信息订单列表"+DateUtil.toString(new Date(), "yyyy-MM-dd_HHmmss");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xlsx");
            ouputStream = response.getOutputStream();
            
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			String[] headers = {"订单号", "发布人姓名", "发布人手机", "发货地", "目的地", "货物重量", "物流公司名称", "物流公司电话", 
					"车主姓名", "车主电话", "车主接单时间","接单处理时间", "订单类型", "订单状态", "支付状态", "订单金额"};
            
			Sheet sheet = createSheet(workbook, headers);
            
			// 查询导出数据总数
			ApiResult<Integer> countApiResult = orderAgentService.countTotal(paramMap);
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
            	ApiResult<List<AdminOrderAgentDTO>> apiResult = orderAgentService.queryListByPage(paramMap);
            	List<AdminOrderAgentDTO> list = null;
            	if(apiResult != null){
	    			list = apiResult.getResult();
	    		}
	   
	            if(CollectionUtils.isEmpty(list)){
	            	break;
	            }
	            for(int j = 0, len = list.size(); j < len; j++){
                   	AdminOrderAgentDTO dto = list.get(j);
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
		String sheetName = "信息订单数据";
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
	private void writeRowData(Sheet sheet, int rowNum, AdminOrderAgentDTO dto){
		Row row = sheet.createRow(rowNum);
		
		String orderNo = dto.getOrderNo() == null ? "" : dto.getOrderNo();
		String sourceMemberName = dto.getSourceMemberName() == null ? "" : dto.getSourceMemberName();
		String sourceMemberMobile = dto.getSourceMemberMobile() == null ? "" : dto.getSourceMemberMobile();
		String sDetail = dto.getSDetailStr() == null ? "" : dto.getSDetailStr();
		String eDetail = dto.getEDetailStr() == null ? "" : dto.getEDetailStr();
		String totalWeight = dto.getTotalWeight() == null ? "" : (dto.getTotalWeight().toString() + "吨");
		String logisticCompanyName = dto.getLogisticCompanyName() == null ? "" : dto.getLogisticCompanyName();
		String logisticMobile = dto.getLogisticMobile() == null ? "" : dto.getLogisticMobile();
		String driverName = dto.getDriverName() == null ? "" : dto.getDriverName();
		String driverMobile = dto.getDriverMobile() == null ? "" : dto.getDriverMobile();
		String confirmTime = dto.getConfirmTime() == null ? "" : DateUtil.toString(dto.getConfirmTime(), DateUtil.DATE_FORMAT_DATETIME);
		String logisticTime = dto.getLogisticTime() == null ? "" : DateUtil.toString(dto.getLogisticTime(), DateUtil.DATE_FORMAT_DATETIME);
		String sourceType = dto.getSourceTypeStr() == null ? "" : dto.getSourceTypeStr();
		String orderStatus = dto.getOrderStatusStr() == null ? "" : dto.getOrderStatusStr();
		String payStatus = dto.getPayStatusStr() == null ? "" : dto.getPayStatusStr();
		String infoAmt = dto.getInfoAmt() == null ? "" : dto.getInfoAmt().toString();
		
		String[] data = {orderNo, sourceMemberName, sourceMemberMobile, sDetail, eDetail, totalWeight,
				logisticCompanyName, logisticMobile, driverName, driverMobile, confirmTime, logisticTime,
				sourceType, orderStatus, payStatus, infoAmt};
		for (int i = 0, len = data.length; i < len; i++) {
			row.createCell(i).setCellValue(data[i]);
		}
	}
	
	/**
	 * 查询时间参数格式化：开始时间为yyyy-MM-dd 00:00:00;结束时间为yyyy-MM-dd 23:59:59
	 * @param paramMap
	 */
	private void formatParamTime(Map<String, Object> paramMap) {
		String acceptStartDate = (String) paramMap.get("acceptStartDate");
		String acceptEndDate = (String) paramMap.get("acceptEndDate");
		if(StringUtils.isNotBlank(acceptStartDate)){
			String acceptStartTime = CommonUtil.getStartOfDay(DateUtil.formateDate(acceptStartDate));
			paramMap.put("acceptStartTime", acceptStartTime);
		}
		if(StringUtils.isNotBlank(acceptEndDate)){
			String acceptEndTime = CommonUtil.getEndOfDay(DateUtil.formateDate(acceptEndDate));
			paramMap.put("acceptEndTime", acceptEndTime);
		}
		
		String confirmStartDate = (String) paramMap.get("confirmStartDate");
		String confirmEndDate = (String) paramMap.get("confirmEndDate");
		if(StringUtils.isNotBlank(confirmStartDate)){
			String confirmStartTime = CommonUtil.getStartOfDay(DateUtil.formateDate(confirmStartDate));
			paramMap.put("confirmStartTime", confirmStartTime);
		}
		if(StringUtils.isNotBlank(confirmEndDate)){
			String confirmEndTime = CommonUtil.getEndOfDay(DateUtil.formateDate(confirmEndDate));
			paramMap.put("confirmEndTime", confirmEndTime);
		}
	}
}
