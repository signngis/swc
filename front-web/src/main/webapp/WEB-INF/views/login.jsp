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
	<%@include file="/WEB-INF/include/common-css.jsp" %>
	<link rel="stylesheet" href="${ctp}/static/css/login.css">
	<style>
		.error{
			color:red;
		}
	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="${ctp}/static/index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form id="loginForm" class="form-signin" action="http://localhost:8082/rest-api/member/login" 
      	role="form" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 
        	用户登录</h2>
        	<span class="error">${msg }</span>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" 
			 placeholder="请输入登录账号" name="loginacct" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" 
				name="pwd" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
           		<a href="${ctp }/user/forget">忘记密码</a> 
          </label>
          <label style="float:right">
            <a href="${ctp}/reg.html">我要注册</a>
          </label>
        </div>
        <input id="loginBtn" type="submit" class="btn btn-lg btn-success btn-block" value="登陆" />
      </form>
    </div>
    <!-- 引入公共的JS -->
    <%@include file="/WEB-INF/include/common-js.jsp" %>
    <script type="text/javascript">
    	var baseurl = "http://localhost:8082/rest-api"; 
    	//登陆功能
    	$("#loginBtn").click(function(){
    		$.post(baseurl+"/member/login",$("#loginForm").serialize(),function(data){
    			//alert(data);
    			localStorage.userData = JSON.stringify(data);
    			//去主页
    			location.href="${ctp}/";
    		}).fail(function(data){
    			layer.msg("出错了");
    		})
    		return false;
    	});
    
    </script>
  </body>
</html>