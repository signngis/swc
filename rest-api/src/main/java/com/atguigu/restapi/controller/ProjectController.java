package com.atguigu.restapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.TProject;
import com.atguigu.bean.TProjectResources;
import com.atguigu.bean.TReturn;
import com.atguigu.bean.TType;
import com.atguigu.constant.AppConstant;
import com.atguigu.enume.OrderByEnum;
import com.atguigu.enume.ProjectQueryEnum;
import com.atguigu.restapi.bean.AppResult;
import com.atguigu.restapi.vo.ProjectAppVO;
import com.atguigu.restapi.vo.ProjectPlusAppVO;
import com.atguigu.restapi.vo.ProjectResourceVo;
import com.atguigu.service.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="项目模块")
@RequestMapping("/proj")
@RestController
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@ApiOperation("获取项目支持的排序规则")
	@GetMapping("/query/order")
	public AppResult<List<Map<String, String>>> projectOrderCondition() {
		List<Map<String, String>> list = new ArrayList<>();
		OrderByEnum[] enums = OrderByEnum.values();
		
		for (OrderByEnum orderByEnum : enums) {
			Map<String, String> map = new HashMap<>();
			map.put("code", orderByEnum.getCode());
			map.put("msg", orderByEnum.getMsg());
			
			list.add(map);
		}
		
		return AppResult.success(list);
	}
	
	
	@ApiOperation("获取项目支持的查询状态")
	@GetMapping("/query/status")
	public AppResult<List<Map<String, String>>> projectQueryStatus() {
		
		
		List<Map<String, String>> status = projectService.getProjectQueryStatus();
		
		return AppResult.success(status);
	}
	
	
	@ApiOperation("获取项目的所有分类")
	@GetMapping("/types")
	public AppResult<List<TType>> projectTypes() {
		List<TType> types = projectService.getProjectTypes();
		return AppResult.success(types);
	}
	
	
	
	@ApiOperation("项目列表")
	@GetMapping("/query")
	public AppResult<PageInfo<ProjectPlusAppVO>>  projectsList(
			@ApiParam("项目分类id")
			@RequestParam(value="tid",required=false)Integer typeId,
			
			@ApiParam("项目状态标识 2-即将开始   U-众筹中  S-众筹成功")
			@RequestParam(value="pstatus",required=false)String projectStatus,
			
			@ApiParam("排序规则   1-最新上线   2-金额最多  3-支持最多")
			@RequestParam(value="orderBy",required=false)String orderBy,
			
			@RequestParam(value="pn",defaultValue="1")Integer pn){
		
		//系统变量的维护t_param
		PageHelper.startPage(pn, AppConstant.PROJECT_PAGE_SIZE);
		
		
		
		List<TProject> projects = projectService.getProjectsByPageAndCondtion(typeId,projectStatus,orderBy);
		
		//info封装了所有分页的信息
		
		List<ProjectPlusAppVO> plusVos = new ArrayList<>();
		//抽取其他VO；
		for (TProject p : projects) {
			ProjectResourceVo vo = new ProjectResourceVo();
			//1、查询每个项目的图片内容；
			
			TProjectResources resources = projectService.getProjectImages(p.getId());
			if(resources!=null)
				BeanUtils.copyProperties(resources, vo);
			
			ProjectPlusAppVO plusAppVO = new ProjectPlusAppVO();
			plusAppVO.setResources(vo);
			
			ProjectAppVO projectAppVO = new ProjectAppVO();
			if(p!=null)
				BeanUtils.copyProperties(p, projectAppVO);
			plusAppVO.setBaseInfo(projectAppVO);
			
			plusVos.add(plusAppVO);
		}
		
		PageInfo<ProjectPlusAppVO> info = new PageInfo<>(plusVos);
		
		return AppResult.success(info);
	}
	
	
	
	
	
	@ApiOperation("获取项目回报档位")
	@GetMapping("/ret")
	public AppResult<List<TReturn>> projectReturn(
			@ApiParam("项目的id")
			@RequestParam("pid")Integer pid) {
		
		List<TReturn> rets = projectService.getProjectReturn(pid);
		return AppResult.success(rets);
	}
	
	
	@ApiOperation("项目详细")
	@GetMapping("/infos")
	public AppResult<ProjectPlusAppVO> projectInfo(
			@ApiParam("项目的id")
			@RequestParam("pid")Integer pid) {
		//projectService.getProjectInfo();
		TProject project = projectService.getProjectBaseInfo(pid);
		TProjectResources resources = projectService.getProjectImages(pid);
		
		ProjectAppVO baseInfo = new ProjectAppVO();
		if(project!=null)
			BeanUtils.copyProperties(project, baseInfo);
		
		ProjectResourceVo resourceVo = new ProjectResourceVo();
		if(resources!=null)
			BeanUtils.copyProperties(resources, resourceVo);
		
		ProjectPlusAppVO plusAppVO = new ProjectPlusAppVO();
		plusAppVO.setBaseInfo(baseInfo);
		plusAppVO.setResources(resourceVo);
		
		return AppResult.success(plusAppVO);
	}

	@ApiOperation("获取热门推荐项目，全局热门&分类热门二合一")
	@GetMapping("/hots")
	public AppResult<List<ProjectAppVO>> getHots(
			@ApiParam("状态码： 3-全网热门  4-分类热门 ")
			@RequestParam("hotscode")String hots,
			
			@ApiParam("项目的分类id")
			@RequestParam(value="tid",required=false)Integer typeId) {
		
		List<ProjectAppVO> vos = new ArrayList<>();
		//1、获取推荐项目
		List<TProject> projects = projectService.getHotsProject(hots,typeId);
		if(projects == null) {
			return AppResult.success(null);
		}
		for (TProject tProject : projects) {
			ProjectAppVO vo = new ProjectAppVO();
			BeanUtils.copyProperties(tProject, vo);
			vos.add(vo);
		}
		
		
		return AppResult.success(vos);
	}
}
