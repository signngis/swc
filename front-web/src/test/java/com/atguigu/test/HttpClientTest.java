package com.atguigu.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class HttpClientTest {
	
	//RestTemplate
	
	@Test
	public void restTemplateTest() {
		String url = "http://localhost:8082/rest-api/member/forget?email=leifengyang@atguigu.com";
		//1、创键对象
		RestTemplate restTemplate = new RestTemplate();
		//2、发送请求 uriVariables：路径变量
		// 
		Map map = restTemplate.getForObject(url, Map.class);
		System.out.println(map.get("msg"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void httpClientTest() throws ClientProtocolException, IOException {
		//1、创建出一个能发请求的HTTP客户端
		CloseableHttpClient client = HttpClients.createDefault();
		String url = "http://localhost:8082/rest-api/member/forget?email=leifengyang@atguigu.com";
		//2、创建一个请求
		HttpGet get = new HttpGet(url);
		//3、执行请求，得到响应
		CloseableHttpResponse response = client.execute(get);
		
		//4、获取响应数据
		//Header[] headers = response.getAllHeaders();//获取所有响应头
		
		StatusLine line = response.getStatusLine();//获取响应状态行
		System.out.println(line.getStatusCode()+line.getReasonPhrase());
		
		HttpEntity entity = response.getEntity();//获取响应内容
		
		InputStream stream = entity.getContent();//拿到内容
	
		String string = IOUtils.toString(stream, "utf-8");
		System.out.println(string);
		
		//5、关响应
		response.close();
		
	}

}
