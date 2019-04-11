package com.atguigu.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Test;

import com.atguigu.front.bean.HttpUtils;

public class WeatherTest {

	@Test
	public void weatheTest() {
		String host = "http://jisutianqi.market.alicloudapi.com";
		String path = "/weather/query";
		String method = "GET";
		String appcode = "93b7e19861a24c519a7548b17dc16d75";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("city", "深圳");
//		querys.put("citycode", "citycode");
//		querys.put("cityid", "cityid");
//		querys.put("ip", "ip");
//		querys.put("location", "location");

		try {
			String string = HttpUtils.doGet(host, path, method, headers, querys, String.class);
			// 获取response的body
			System.out.println(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
