<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<%@include file="/WEB-INF/include/common-css.jsp"%>
<link rel="stylesheet" href="${ctp}/static/css/main.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}
</style>
</head>

<body>

	<%
		pageContext.setAttribute("title", "用户维护");
	%>
	<!--顶部导航  -->
	<%@include file="/WEB-INF/include/manager-nav.jsp"%>


	<div class="container-fluid">
		<div class="row">
			<!-- 侧边导航 -->
			<%@include file="/WEB-INF/include/manager-sidebar.jsp"%>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">数据列表</a></li>
					<li class="active">分配角色</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-body">
					<c:if test="${!empty sessionScope.msg }">
						<div class="alert alert-success" role="alert">${sessionScope.msg }</div>
						<c:remove var="msg" scope="session"/>
					</c:if>
						<form role="form" class="form-inline">
							<div class="form-group">
								<label for="exampleInputPassword1">未分配角色列表</label><br> 
									<!-- 
									外层*内容
									
									for(TRole role:allRoles){
										var flag = false;
										for(TRole r:hasRoles){
											if(r.id == role.id){
												flag == true;
											}
										}
										
										//flag 说明 在里面
										if(!flag){
											//输出
										}
									}
									
									 -->
								<select id="addUserRoleSelect" name="pids" class="form-control" multiple size="10"
									style="width: 250px; overflow-y: auto;">
										<c:forEach items="${allRoles }" var="allRole">
											<!-- 默认没有 -->
											<c:set var="flag" value="false"></c:set>
											<c:forEach items="${hasRoles }" var="hasRole">
												<c:if test="${allRole.id == hasRole.id }">
													<c:set var="flag" value="true"></c:set>
												</c:if>
											</c:forEach>
											<!-- 一次性判断 -->
											<!-- 当前菜单不是已有的集合里面的元素 -->
											<c:if test="${!flag }">
												<option value="${allRole.id }">${allRole.name }</option>
											</c:if>
										</c:forEach>
									</select>
							</div>
							<div class="form-group">
								<ul>
									<li id="addUserRoleSubmitBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
									<br>
									<li id="deleteUserRoleSubmitBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
										style="margin-top: 20px;"></li>
								</ul>
							</div>
							<div class="form-group" style="margin-left: 40px;">
								<label for="exampleInputPassword1">已分配角色列表</label><br> 
								<select id="deleteUserRoleSelect"
									class="form-control" multiple size="10"
									style="width: 250px; overflow-y: auto;">
									<c:forEach items="${hasRoles }" var="hasRole">
										<option value="${hasRole.id }">${hasRole.name }</option>
									</c:forEach>
								</select>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="/WEB-INF/include/common-js.jsp"%>
	<script type="text/javascript">
	
		//用户角色删除
		$("#deleteUserRoleSubmitBtn").click(function(){
			var rids = $("#deleteUserRoleSelect").val();
			if(rids == null){
				layer.alert("请先选择角色");
				return;
			}
			
			var ridStr = rids.toString();
			var uid = "${param.id}";
			
			$.post("${ctp}/user/deleterole",{rids:ridStr,uid:uid},function(data){
				//刷新本页
				location.reload(true);
			});
			
		});
	
		//角色添加
		$("#addUserRoleSubmitBtn").click(function(){
			//要提交的所有rid；
			var rids = $("#addUserRoleSelect").val();
			if(rids == null){
				layer.alert("请先选择角色");
				return;
			}
			var ridStr = rids.toString();
			var uid = "${param.id}";
			$.post("${ctp}/user/addrole",{rids:ridStr,uid:uid},function(data){
				//刷新本页
				location.reload(true);
			});
			
		});
	</script>
</body>
</html>
