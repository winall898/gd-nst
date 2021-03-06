package cn.gdeng.nst.web.controller.source.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import cn.gdeng.nst.util.server.HttpClientUtil;
import cn.gdeng.nst.util.web.api.Des3;
import cn.gdeng.nst.web.http.HttpHelper;
import junit.framework.TestCase;

public class GoodsQueryControllerTest extends TestCase {
	
	private final static String BASE_URL = "http://localhost:8880/nst-web-api";
	//private final static String BASE_URL = "http://localhost:8082/nst-web-api";
	//private final static String BASE_URL = "http://10.17.1.181:8089";
	//private final static String BASE_URL = "http://10.17.1.193:8281"; 

	
	
	public void test_queryDetailAndLogForShipper() throws Exception{
		String url ="/v1/goodsQuery/queryDetailAndLogForShipper";
		String data="{id:75}"; 
		String result = HttpHelper.doPost(BASE_URL + url, data);
		System.out.println(result);
	}
	
	
	// 查询待接受的货源
		public void queryMyUnconfirmed() throws Exception {
			long a = System.currentTimeMillis();
			String url = BASE_URL + "/v1/goodsAsiQuery/queryMyUnconfirmed";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("assignMemberId", "6508");
			Gson gson = new Gson();
			String requestData = Des3.encode(gson.toJson(map));
			Map<String, Object> map2 = new HashMap<>();
			map2.put("param", requestData);
			String reponseData = HttpClientUtil.doGet(url, map2);
			System.out.println(Des3.decode(reponseData) + "最终结果");
			long b = System.currentTimeMillis();
			System.out.println(b - a);
		}
		
		// 查询已关闭的货源
				public void queryAssiGoodsClose() throws Exception {
					long a = System.currentTimeMillis();
					String url = BASE_URL + "/v1/goodsAsiQuery/queryAssiGoodsClose";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("assignMemberId", "6508");
					Gson gson = new Gson();
					String requestData = Des3.encode(gson.toJson(map));
					Map<String, Object> map2 = new HashMap<>();
					map2.put("param", requestData);
					String reponseData = HttpClientUtil.doGet(url, map2);
					System.out.println(Des3.decode(reponseData) + "最终结果");
					long b = System.currentTimeMillis();
					System.out.println(b - a);
				}

				// 查询货源详情
				public void queryAssiGoodsDetail() throws Exception {
					long a = System.currentTimeMillis();
					String url = BASE_URL + "/v1/goodsAsiQuery/queryAssiGoodsDetail";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", "7");
					Gson gson = new Gson();
					String requestData = Des3.encode(gson.toJson(map));
					Map<String, Object> map2 = new HashMap<>();
					map2.put("param", requestData);
					String reponseData = HttpClientUtil.doGet(url, map2);
					System.out.println(Des3.decode(reponseData) + "最终结果");
					long b = System.currentTimeMillis();
					System.out.println(b - a);
				}
				
				
				// 接受货源
				public void acceptPlatformDistribution() throws Exception {
					long a = System.currentTimeMillis();
					String url = BASE_URL + "/v1/goodsAsiUpdate/acceptPlatformDistribution";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("driverMemberId", "4126");
					map.put("sourceShipperId", "1048666");
					map.put("updateUserId", "6508");
					map.put("sourceAssignHisId", "7");
					map.put("version", "2");
					Gson gson = new Gson();
					String requestData = Des3.encode(gson.toJson(map));
					Map<String, Object> map2 = new HashMap<>();
					map2.put("param", requestData);
					String reponseData = HttpClientUtil.doGet(url, map2);
					System.out.println(Des3.decode(reponseData) + "最终结果");
					long b = System.currentTimeMillis();
					System.out.println(b - a);
				}
	public static void main(String[] args) {
		System.out.println("start~~~~");
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					new ServerSocket(12345, 50);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("~~~thread1~~~");
				System.out.println("~~~thread2~~~");
				
			}
		},"StandaloneRegistry-Receiver-Launcher");
		thread.setDaemon(false);
		thread.start();
		System.out.println("end~~~~");
	}
	

}
