<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
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

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
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
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;"
							action="${ctp }/user/list.html">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text"
										name="condition" value="${condition }"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button type="submit" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button id="userBatchDeleteBtn" type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary"
							style="float: right;" onclick="window.location.href='${ctp}/user/add.html'">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="checkAllBtn" type="checkbox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th>创建时间</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${info.list }" var="user">
										<tr>
											<td>${user.id }</td>
											<td><input type="checkbox" u_id="${user.id }" class="singleCheckBox"></td>
											<td>${user.loginacct }</td>
											<td>${user.username }</td>
											<td>${user.email }</td>
											<td>${user.createtime }</td>
											<td>
												<!-- 点击去分配角色页面 -->
												<button type="button" class="btn btn-success btn-xs"
													onclick="location.href='${ctp}/user/role.html?id=${user.id}'">
													<i class=" glyphicon glyphicon-check"></i>
												</button>
												<!-- 点击去修改页 -->
												<button type="button" class="btn btn-primary btn-xs" onclick="location.href='${ctp}/user/edit.html?id=${user.id }'">
													<i class=" glyphicon glyphicon-pencil"></i>
												</button>
												
												<!-- 删除 -->
												<button type="button" class="btn btn-danger btn-xs" onclick="location.href='${ctp}/user/delete?ids=${user.id }'">
													<i class=" glyphicon glyphicon-remove"></i>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="7" align="center">
											<ul class="pagination">
												<li><a href="${ctp }/user/list.html?pn=1&condition=${condition}">首页</a></li>
												<c:if test="${info.hasPreviousPage }">
													<li><a href="${ctp }/user/list.html?pn=${info.prePage}&condition=${condition}">上一页</a></li>
												</c:if>
												
												<!--连续分页  -->
												<c:forEach items="${info.navigatepageNums }" var="nav">
													<c:if test="${info.pageNum ==  nav}">
														<li class="active"><a href="${ctp }/user/list.html?pn=${nav }&condition=${condition}">${nav }</a></li>
													</c:if>
													<c:if test="${info.pageNum !=  nav}">
														<li><a href="${ctp }/user/list.html?pn=${nav }&condition=${condition}">${nav }</a></li>
													</c:if>
												</c:forEach>
												
												
												<c:if test="${info.hasNextPage }">
													<li><a href="${ctp }/user/list.html?pn=${info.nextPage}&condition=${condition}">下一页</a></li>
												</c:if>
												<li><a href="${ctp }/user/list.html?pn=${info.pages}&condition=${condition}">末页</a></li>
												<li><a href="#">当前总 ${info.pages } 页，总  ${info.total } 记录</a></li>
											</ul>
										</td>
									</tr>
								</tfoot>

							</table>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<%@include file="/WEB-INF/include/common-js.jsp"%>
	<script type="text/javascript">
		$("#userBatchDeleteBtn").click(function(){
			//批量删除；
			var ids = "";
			$(".singleCheckBox:checked").each(function(){
				//获取当前选择的user的id
				ids+=$(this).attr("u_id")+",";
			});
			//参照  JavaScript String 对象
			// location.href 获取当前浏览器的地址
			location.href = "${ctp}/user/delete?ids="+ids;
		});
		
		//全选全不选功能
		checkAll("#checkAllBtn",".singleCheckBox");
		
	</script>
</body>
</html>
