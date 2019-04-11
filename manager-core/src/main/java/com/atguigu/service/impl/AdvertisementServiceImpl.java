package com.atguigu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.TAdvertisement;
import com.atguigu.bean.TAdvertisementExample;
import com.atguigu.enume.AdverStatusEnum;
import com.atguigu.mapper.TAdvertisementMapper;
import com.atguigu.service.AdvertisementService;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired
	TAdvertisementMapper advertisementMapper;
	
	@Override
	public List<TAdvertisement> getIndexAds() {
		// TODO Auto-generated method stub
		TAdvertisementExample example = new TAdvertisementExample();
		example.createCriteria().andStatusEqualTo(AdverStatusEnum.UP.getCode());
		
		List<TAdvertisement> list = advertisementMapper.selectByExample(example);
		
		return list;
	}

}
