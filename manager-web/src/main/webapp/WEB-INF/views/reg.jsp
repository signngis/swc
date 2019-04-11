<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<!-- 引入公共CSS依赖 -->
<%@include file="/WEB-INF/include/common-css.jsp"%>
<link rel="stylesheet" href="${ctp }/static/css/login.css">
<style>
span.error {
	color: red;
}
</style>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" href="${ctp}/static/index.html"
						style="font-size: 32px;">尚筹网-创意产品众筹平台</a>
				</div>
			</div>
		</div>
	</nav>

	<div class="container">

		<form id="userRegistForm" class="form-signin" role="form"
			action="${ctp }/user/regist" method="post">
			<h2 class="form-signin-heading">
				<i class="glyphicon glyphicon-log-in"></i> 用户注册
			</h2>
			<div class="form-group has-success has-feedback">
				<input type="text" class="form-control" placeholder="请输入登录账号"
					name="loginacct" value="${TUser.loginacct }" autofocus> <span
					class="glyphicon glyphicon-user form-control-feedback"></span> <span
					class="error">${excMsg.loginAcct }</span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="password" class="form-control" name="userpswd"
					placeholder="请输入登录密码" style="margin-top: 10px;"> <span
					class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="text" class="form-control" name="email"
					value="${TUser.email }" placeholder="请输入邮箱地址"
					style="margin-top: 10px;"> 
				<span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
				
				<span class="error">${excMsg.email }</span>
			</div>
			<div class="form-group has-success has-feedback">
				<select class="form-control">
					<option>会员</option>
					<option>管理</option>
				</select>
			</div>
			<div class="checkbox">
				<label> 忘记密码 </label> <label style="float: right"> <a
					href="${ctp}/login.html">我有账号</a>
				</label>
			</div>
			<!-- <a > 注册</a> -->
			<input class="btn btn-lg btn-success btn-block" type="submit"
				value="注册">
		</form>
	</div>
	<%@include file="/WEB-INF/include/common-js.jsp"%>
	<script type="text/javascript">
		$("#userRegistForm").validate(
				{
					rules : {
						loginacct : {
							minlength : 5,
							required : true
						},
						userpswd : {
							minlength : 5,
							required : true
						},
						email : {
							minlength : 5,
							required : true,
							email : true
						}
					},
					//定义错误放在哪个位置，自定义错误显示逻辑
					errorPlacement : function(error, element) {
						//console.log(error);//错误的框架自动生成的span标签
						//console.log(element);//发生错误的input框
						$(element).nextAll("span.glyphicon").after(error);
					},
					//定义错误的标签是用什么包装
					errorElement : "span",
					//如何自定义校验方法
					messages : {
						loginacct : {
							minlength : "登陆账号最小5位",
							required : "登陆账号必须填写"
						},
						userpswd : {
							minlength : "密码最小5位",
							required : "密码必须填写"
						},
						email : {
							required : "邮箱必须填写",
							email : "邮箱必须合法"
						}
					}
				});
	</script>
</body>
</html>