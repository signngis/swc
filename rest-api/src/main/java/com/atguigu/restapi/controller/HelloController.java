package com.atguigu.restapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableXXX开启xx功能
@EnableSwagger2

@Api(tags="测试哈哈")
@RestController
public class HelloController {
	
	/**
	 * 1、导入Swagger相关依赖
	 * <dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.8.0</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.8.0</version>
		</dependency>
	 * 2、开启Swagger功能
	 * 	@EnableSwagger2 只需要标注一次
	 * 
	 * @return
	 */
	@ApiOperation(value="这是hello测试")
	@GetMapping("/hello")
	public String hello(String callback) {
		
		//只要服务器返回的是一段js即可
		return callback+"({\"age\":18,\"id\":1})";
	}
	
	
	@ApiOperation(value="这是hello2测试")
	@GetMapping("/hello2")
	public String hello2() {
		
		return "{\"age\":18,\"id\":1}";
	}

}
