package com.atguigu.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {
	
	
	@Test
	public void test02() {
		RestTemplate template = new RestTemplate();
		
		//template.
		//1、如果要使用自定义的请求头，必须使用底层的exchange；
		//2、除此之外都可以使用对应的get、post快速发送请求；
	}
	
	
	@Test
	public void test01() {
		//1、测试获取天气；   自定义请求头；
		
		RestTemplate template = new RestTemplate();
		//2、创建URL
		//url后面如何带数据
		String url = "http://jisutianqi.market.alicloudapi.com/weather/query?city={cityName}";
		
		//3、构建headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "APPCODE 93b7e19861a24c519a7548b17dc16d75");
		
		//4、构建请求体数据
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("city", "深圳");
		
		HttpEntity requestEntity = new HttpEntity<>(null, headers);
		
		
		//路径变量的map
		Map<String, String> map = new HashMap<>();
		map.put("cityName", "南京");
		//5、发送请求接受数据
		ResponseEntity<String> entity = template.exchange(url, HttpMethod.GET, requestEntity, String.class,map);
		
		
		String string = entity.getBody();
		System.out.println(string);
		
		
	}

}
