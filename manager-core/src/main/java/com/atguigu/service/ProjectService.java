package com.atguigu.service;

import java.util.List;
import java.util.Map;

import com.atguigu.bean.TProject;
import com.atguigu.bean.TProjectResources;
import com.atguigu.bean.TReturn;
import com.atguigu.bean.TType;

public interface ProjectService {

	List<TProject> getHotsProject(String hots, Integer typeId);

	TProject getProjectBaseInfo(Integer pid);

	TProjectResources getProjectImages(Integer pid);

	List<TReturn> getProjectReturn(Integer pid);

	//分页按条件带排序的来查询项目
	List<TProject> getProjectsByPageAndCondtion(Integer typeId, String projectStatus, String orderBy);

	List<TType> getProjectTypes();

	List<Map<String, String>> getProjectQueryStatus();


}
