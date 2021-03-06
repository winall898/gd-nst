package cn.gdeng.nst.admin.controller.admin;


import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gdeng.nst.admin.controller.right.AdminBaseController;
import cn.gdeng.nst.admin.dto.admin.AdminPageDTO;
import cn.gdeng.nst.admin.dto.admin.MemberCerAuditDTO;
import cn.gdeng.nst.admin.dto.admin.MemberCerDTO;
import cn.gdeng.nst.admin.service.member.MemberCerAuditService;
import cn.gdeng.nst.admin.service.member.MemberCerService;
import cn.gdeng.nst.entity.nst.SysRegisterUser;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.util.admin.web.DateUtil;
import cn.gdeng.nst.util.web.api.ApiResult;
import cn.gdeng.nst.util.web.api.BizException;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("memberCer")
public class MemberCerController extends AdminBaseController{
    private GdLogger logger = GdLoggerFactory.getLogger(this.getClass());
    @Reference
    private MemberCerService memberCerService;
    @Reference
    private MemberCerAuditService auditService;
    
    private static Map<Integer,Object> cerStatuMap = null;
    
    private static Map<Integer,Object> cerFromAppMap = null;
    
    static{
    	cerStatuMap = new LinkedHashMap<Integer,Object>();
    	cerStatuMap.put(0, "认证中");
    	cerStatuMap.put(1, "已认证");
    	cerStatuMap.put(2, "已驳回");
    	
    	cerFromAppMap = new LinkedHashMap<Integer,Object>();
    	cerFromAppMap.put(1,"农速通-货主");
    	cerFromAppMap.put(2,"农速通-车主");
    	cerFromAppMap.put(3,"农速通-物流公司");
    }
    
    
    //个人认证列表页
    @RequestMapping("/personal/list")
    public String personalList(){
        return "memberCer/personalCerList";
    }

    //个人认证查询结果
    @ResponseBody
    @RequestMapping("/personal/query")
    public Object personalQuery(HttpServletRequest request) throws BizException{
        Map<String, Object> paramMap = getParametersMap(request); 
        setCommParameters(request, paramMap);
        paramMap.put("userType", 1); 
        ApiResult<AdminPageDTO> apiResult = memberCerService.findByConditions(paramMap);
        if(apiResult != null){
            return JSONObject.toJSONString(apiResult.getResult(), SerializerFeature.WriteDateUseDateFormat);
        }
        return null;
    }
    
    //个人审核
    @RequestMapping("/personal/audit/{id}")
    public String personalAudit(ModelMap model,@PathVariable Integer id,HttpServletRequest request) throws BizException{
        String isShow = request.getParameter("isShow");
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("id", id);
        ApiResult<MemberCerDTO> apiResult = memberCerService.findOne(paramMap);
        if(apiResult != null){
            model.addAttribute("memberCerDTO", apiResult.getResult());
        }
        if(StringUtils.isNotEmpty(isShow)){
            model.addAttribute("isShow", isShow);
        }
        return "memberCer/personalAudit";
    }
    
    //保存审核记录
    @ResponseBody
    @RequestMapping("/personal/save")
    public Map<String,Object> savePersonalAudit(HttpServletRequest request,MemberCerAuditDTO dto){
        SysRegisterUser user = getUser(request);
        Number number = 0;
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        try{
            dto.setCreateUserId(user.getUserName());     
            dto.setCreateTime(new Date());
            number = auditService.save(dto).getResult();
        }catch(Exception e){
            logger.info("个人审核异常>>>>>>>>>",e);
        }       
        paramMap.put("number", number);
        return paramMap;
    }
    
    //个人审核记录
    @ResponseBody
    @RequestMapping("/personal/auditRecord/query/{cerId}")
    public Object personalAuditRecordQuery(HttpServletRequest request,@PathVariable String cerId) throws BizException{
        Map<String, Object> paramMap = getParametersMap(request); 
        setCommParameters(request, paramMap);
        paramMap.put("cerId", cerId); 
        ApiResult<AdminPageDTO> apiResult = auditService.findByConditions(paramMap);
        if(apiResult != null){
            return JSONObject.toJSONString(apiResult.getResult(), SerializerFeature.WriteDateUseDateFormat);
        }
        return null;
    }
    
    //导出个人认证EXCEL
    @ResponseBody
    @RequestMapping(value="/personal/exportCheck",produces="application/json;charset=utf-8")
    public String personalExportCheck(HttpServletRequest request) throws BizException{
        ApiResult<String> apiResult = new ApiResult<String>(null, MsgCons.C_10000, MsgCons.M_10000);    
        //查询参数
        Map<String, Object> paramMap = getParametersMap(request);
        ApiResult<Integer> remoteApiResult = memberCerService.countTotal(paramMap);
        if(remoteApiResult == null || remoteApiResult.getResult() == null){
            apiResult.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
            apiResult.setResult("对象为空！");
            return JSONObject.toJSONString(apiResult);
        }     
        //总记录数验证
        int total = remoteApiResult.getResult();
        if(total == 0){
            apiResult.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
            apiResult.setResult("导出的结果集无任何数据，请重新修改查询条件");
            return JSONObject.toJSONString(apiResult);
        }
        if(total > 50000){
            apiResult.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
            apiResult.setResult("查询结果集太大(>50000条), 请缩减日期范围, 或者修改其他查询条件！");
            return JSONObject.toJSONString(apiResult);
        }
        return JSONObject.toJSONString(apiResult);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/personal/exportData")
    public void personalExportData(HttpServletRequest request, HttpServletResponse response) throws BizException{
        //查询参数
        Map<String, Object> paramMap = getParametersMap(request);
        List<Map<String,Object>> mapList = null;
        paramMap.put("startRow", 0);
        paramMap.put("endRow", 50000);
        ApiResult<List<Map<String,Object>>> apiResult = (ApiResult<List<Map<String, Object>>>) memberCerService.findAll(paramMap);
        if(apiResult != null){
            mapList = apiResult.getResult();
            String[] cell = {"账号","姓名","手机","认证来源","认证申请时间","个人认证状态","审核人","审核时间"};
            writeExcel("个人认证",cell,mapList);
        }  
    }
    
    private void writeExcel(String title,String[] cell,List<Map<String,Object>> mapList){
        OutputStream ouputStream = null;
        WritableWorkbook wwb = null;
        try{
            String fileName = title + DateUtil.toString(new Date(),"yyyy-MM-dd");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1") + ".xls");
            ouputStream = response.getOutputStream();
            
            wwb = Workbook.createWorkbook(ouputStream);
            if(wwb != null){
                WritableSheet sheet = wwb.createSheet(title, 0);              
                for(int i = 0;i < cell.length;i++){
                    sheet.addCell(new Label(i, 0, cell[i]));
                }           
                if(mapList != null && mapList.size() > 0){
                    int j = 0;
                    int k = 1;
                    for(Map<String, Object> map : mapList){                        
                        for(Map.Entry<String, Object> entry : map.entrySet()){
                        	if(entry.getKey().equals("cerStatus") && entry.getValue() != null){
                        		Integer cerStatus = (Integer)entry.getValue();
                       			sheet.addCell(new Label(j, k, (String) cerStatuMap.get(cerStatus)));                      		                                
                        	}else if(entry.getKey().equals("certificFrom") && entry.getValue() != null){
                        		Integer cerFromApp = (Integer)entry.getValue();
                       			sheet.addCell(new Label(j, k, (String) cerFromAppMap.get(cerFromApp))); 
                        	}else{
                                sheet.addCell(new Label(j, k, entry.getValue()==null ? "" : String.valueOf(entry.getValue())));
                        	}
                            j++;                         
                        }
                        j=0;
                        k++;
                    }             
                }
                wwb.write();
                wwb.close();
                ouputStream.flush();
                ouputStream.close();
            }            
        }catch(Exception e){
            logger.info(title+"导出EXCEL异常>>>>>>>>",e);
        }finally {
            wwb = null;
            ouputStream = null;
        }
    }
    /////////////////////////////////////////////企业/////////////////////////////////////////////////
    //企业认证列表页
    @RequestMapping("/company/list")
    public String companyList(){
        return "memberCer/companyCerList";
    }

    //企业认证查询结果
    @ResponseBody
    @RequestMapping("/company/query")
    public Object companyQuery(HttpServletRequest request) throws BizException{
        Map<String, Object> paramMap = getParametersMap(request); 
        setCommParameters(request, paramMap);
        paramMap.put("userType",2);
        ApiResult<AdminPageDTO> apiResult = memberCerService.findByConditions(paramMap);
        if(apiResult != null){
            return JSONObject.toJSONString(apiResult.getResult(), SerializerFeature.WriteDateUseDateFormat);
        }
        return null;
    }
    
    //企业审核
    @RequestMapping("/company/audit/{id}")
    public String companyAudit(ModelMap model,@PathVariable Integer id,HttpServletRequest request) throws BizException{
        String isShow = request.getParameter("isShow");
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        paramMap.put("id", id);
        ApiResult<MemberCerDTO> apiResult = memberCerService.findOne(paramMap);
        if(apiResult != null){
            model.addAttribute("memberCerDTO", apiResult.getResult());
        }
        if(StringUtils.isNotEmpty(isShow)){
            model.addAttribute("isShow", isShow);
        }
        return "memberCer/companyAudit";
    }
    
    //保存审核记录
    @ResponseBody
    @RequestMapping("/company/save")
    public Map<String,Object> saveCompanyAudit(HttpServletRequest request,MemberCerAuditDTO dto){
        SysRegisterUser user = getUser(request);
        Number number = 0;
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        try{
            dto.setCreateUserId(user.getUserName());     
            dto.setCreateTime(new Date());
            number = auditService.save(dto).getResult();
        }catch(Exception e){
            logger.info("企业审核异常>>>>>>>>>",e);
        }       
        paramMap.put("number", number);
        return paramMap;
    }
    
    //企业审核记录
    @ResponseBody
    @RequestMapping("/company/auditRecord/query/{cerId}")
    public Object companyAuditRecordQuery(HttpServletRequest request,@PathVariable String cerId) throws BizException{
        Map<String, Object> paramMap = getParametersMap(request); 
        setCommParameters(request, paramMap);
        paramMap.put("cerId", cerId); 
        ApiResult<AdminPageDTO> apiResult = auditService.findByConditions(paramMap);
        if(apiResult != null){
            return JSONObject.toJSONString(apiResult.getResult(), SerializerFeature.WriteDateUseDateFormat);
        }
        return null;
    }
    
    //导出企业认证EXCEL
    @ResponseBody
    @RequestMapping(value="/company/exportCheck",produces="application/json;charset=utf-8")
    public String companyExportCheck(HttpServletRequest request) throws BizException{
        ApiResult<String> apiResult = new ApiResult<String>(null, MsgCons.C_10000, MsgCons.M_10000);     
        //查询参数
        Map<String, Object> paramMap = getParametersMap(request);
        ApiResult<Integer> remoteApiResult = memberCerService.countTotal(paramMap);
        if(remoteApiResult == null || remoteApiResult.getResult() == null){
            apiResult.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
            apiResult.setResult("对象为空！");
            return JSONObject.toJSONString(apiResult);
        }     
        //总记录数验证
        int total = remoteApiResult.getResult();
        if(total == 0){
            apiResult.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
            apiResult.setResult("导出的结果集无任何数据，请重新修改查询条件");
            return JSONObject.toJSONString(apiResult);
        }
        if(total > 50000){
            apiResult.setCodeMsg(MsgCons.C_20000, MsgCons.M_20000);
            apiResult.setResult("查询结果集太大(>50000条), 请缩减日期范围, 或者修改其他查询条件！");
            return JSONObject.toJSONString(apiResult);
        }
        return JSONObject.toJSONString(apiResult);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/company/exportData")
    public void companyExportData(HttpServletRequest request, HttpServletResponse response) throws BizException{
        //查询参数
        Map<String, Object> paramMap = getParametersMap(request);
        List<Map<String,Object>> mapList = null;
        paramMap.put("startRow", 0);
        paramMap.put("endRow", 50000);
        ApiResult<List<Map<String,Object>>> apiResult = (ApiResult<List<Map<String, Object>>>) memberCerService.findAll(paramMap);
        if(apiResult != null){
            mapList = apiResult.getResult();
            String[] cell = {"账号","企业名称","手机","认证来源","认证申请时间","企业认证状态","审核人","审核时间"};
            writeExcel("企业认证",cell,mapList);           
        }  
    }
}
