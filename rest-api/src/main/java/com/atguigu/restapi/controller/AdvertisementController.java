package com.atguigu.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.TAdvertisement;
import com.atguigu.restapi.bean.AppResult;
import com.atguigu.service.AdvertisementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="广告模块")
@RequestMapping("/adv")
@RestController
public class AdvertisementController {
	
	@Autowired
	AdvertisementService advertisementService;
	
	@ApiOperation("获取首页推荐广告")
	@GetMapping("/index")
	public AppResult<List<TAdvertisement>> indexAdvs() {
		
		//1、获取首页推荐广告
		List<TAdvertisement> advs = advertisementService.getIndexAds();
		
		AppResult<List<TAdvertisement>> success = AppResult.success(advs);
		return success;
	}
	
	
	
	
	
	
	

}
