package com.atguigu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.TProject;
import com.atguigu.bean.TProjectExample;
import com.atguigu.bean.TProjectExample.Criteria;
import com.atguigu.bean.TProjectResources;
import com.atguigu.bean.TReturn;
import com.atguigu.bean.TReturnExample;
import com.atguigu.bean.TType;
import com.atguigu.enume.ProjectQueryEnum;
import com.atguigu.enume.ProjectStatusEnum;
import com.atguigu.mapper.TProjectMapper;
import com.atguigu.mapper.TProjectResourcesMapper;
import com.atguigu.mapper.TReturnMapper;
import com.atguigu.mapper.TTypeMapper;
import com.atguigu.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	TProjectMapper projectMapper;

	@Autowired
	TProjectResourcesMapper projectResourcesMapper;
	@Autowired
	TReturnMapper returnMapper;
	
	@Autowired
	TTypeMapper typeMapper;

	@Override
	public List<TProject> getHotsProject(String hots, Integer typeId) {
		// TODO Auto-generated method stub
		if (ProjectStatusEnum.HOT.getCode().equals(hots)) {
			// 只是查全网热门 3 or7
			// 3 4 7
			// SELECT * FROM `t_project` WHERE STATUS='3' OR STATUS='7'
			TProjectExample example = new TProjectExample();
			// 判断是否是3
			example.createCriteria().andStatusEqualTo(hots);
			example.or(example.createCriteria().andStatusEqualTo(ProjectStatusEnum.ALLHOTS.getCode()));
			return projectMapper.selectByExample(example);

		} else if (typeId != null && ProjectStatusEnum.GROUPHOT.getCode().equals(hots)) {
			// 查分类任务 4 or 7
			/**
			 * SELECT p.* FROM `t_project` p LEFT JOIN `t_project_type` pt ON
			 * pt.`projectid`=p.`id` AND pt.`typeid`=1 WHERE p.`status`='4' OR
			 * p.`status`='7'
			 */

			return projectMapper.selectHotsProject(hots, typeId);
		}
		return null;
	}

	@Override
	public TProject getProjectBaseInfo(Integer pid) {
		// TODO Auto-generated method stub
		return projectMapper.selectByPrimaryKey(pid);
	}

	@Override
	public TProjectResources getProjectImages(Integer pid) {
		// TODO Auto-generated method stub
		return projectResourcesMapper.selectByPrimaryKey(pid);
	}

	@Override
	public List<TReturn> getProjectReturn(Integer pid) {
		// TODO Auto-generated method stub
		TReturnExample example = new TReturnExample();
		example.createCriteria().andProjectidEqualTo(pid);
		return returnMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public List<TProject> getProjectsByPageAndCondtion(Integer typeId, String projectStatus, String orderBy) {
		// TODO Auto-generated method stub
		TProjectExample example = new TProjectExample();
		
		
		return projectMapper.selectProjects(typeId,projectStatus,orderBy);
	}

	@Override
	public List<TType> getProjectTypes() {
		// TODO Auto-generated method stub
		return typeMapper.selectByExample(null);
	}

	@Override
	public List<Map<String, String>> getProjectQueryStatus() {
		// TODO Auto-generated method stub
		List<Map<String, String>> results = new ArrayList<>();
		ProjectQueryEnum[] enums = ProjectQueryEnum.values();
		for (ProjectQueryEnum projectQueryEnum : enums) {
			Map<String, String> obj = new HashMap<>();
			String code = projectQueryEnum.getCode();
			String msg = projectQueryEnum.getMsg();
			obj.put("code", code);
			obj.put("msg", msg);
			
			results.add(obj);
		}
		return results;
	}

}
