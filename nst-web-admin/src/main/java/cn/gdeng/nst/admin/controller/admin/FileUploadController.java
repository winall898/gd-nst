package cn.gdeng.nst.admin.controller.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.gdeng.nst.admin.controller.right.AdminBaseController;
import cn.gdeng.nst.enums.MsgCons;
import cn.gdeng.nst.util.admin.web.CommonUtil;
import cn.gdeng.nst.util.server.FileUploadUtil;
import cn.gdeng.nst.util.web.api.GdProperties;

/**
 * 文件上传
 * @author dengjianfeng
 *
 */
@Controller
public class FileUploadController extends AdminBaseController{
	
	@Autowired
	private GdProperties gdProperties;

	@RequestMapping(value="uploadFile",produces="application/json;charset=utf-8")
	@ResponseBody
	public String uploadFile(@RequestParam(value = "file", required = true) MultipartFile file){
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 上传文件大小校验（最大限制20M）
		if (file != null && file.getSize() > 20 * 1024 * 1024) {
			map.put("status", 0);
			map.put("message", MsgCons.M_2001);
			return JSONObject.toJSONString(map);
		}
		
		String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
		Properties properties = gdProperties.getProperties();
		String endpoint = properties.getProperty("gd.upload.endpoint");
		String accessKeyId = properties.getProperty("gd.upload.accessKeyId");
		String accessKeySecret = properties.getProperty("gd.upload.accessKeySecret");
		String bucketName = properties.getProperty("gd.upload.bucketName");
		String path = properties.getProperty("gd.uplaod.path")+".admin";
		String host = properties.getProperty("gd.uplaod.host");
		try{
			String masterPicPath = FileUploadUtil.uploadFile(endpoint, accessKeyId, accessKeySecret, bucketName, path, fileName, file.getBytes());
			map.put("status", 1);
			map.put("message", masterPicPath);
			map.put("url", host+masterPicPath);
		}catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("message", MsgCons.M_2002);
		}
		return JSONObject.toJSONString(map);
	}
}
