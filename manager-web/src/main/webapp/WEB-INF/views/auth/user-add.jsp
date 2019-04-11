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
</style>
</head>

<body>
	<%
		pageContext.setAttribute("title", "用户维护");
	%>

	<%@include file="/WEB-INF/include/manager-nav.jsp"%>

	
	
	<div class="container-fluid">
		<div class="row">
		<!-- 侧边导航 -->
			<%@include file="/WEB-INF/include/manager-sidebar.jsp"%>
			
			
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
					<li><a href="#">数据列表</a></li>
					<li class="active">新增</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-heading">
						表单数据
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<span style="color:red">${msg }</span>
						<form role="form" 
							action="${ctp }/user/add" method="post">
							<div class="form-group">
								<label>登陆账号</label> 
								<input type="text" class="form-control" 
								name="loginacct" value="${TUser.loginacct }"
									placeholder="请输入登陆账号">
							</div>
							<div class="form-group">
								<label>用户名称</label> <input
									type="text" class="form-control" 
									name="username" value="${TUser.username }"
									placeholder="请输入用户名称">
							</div>
							<div class="form-group">
								<label>邮箱地址</label> <input type="email"
									class="form-control" name="email"
									value="${TUser.email }"
									placeholder="请输入邮箱地址">
							</div>
							<button type="submit" class="btn btn-success">
								<i class="glyphicon glyphicon-plus"></i> 新增
							</button>
							<button type="button" class="btn btn-danger">
								<i class="glyphicon glyphicon-refresh"></i> 重置
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="/WEB-INF/include/common-js.jsp"%>
	
</body>
</html>
