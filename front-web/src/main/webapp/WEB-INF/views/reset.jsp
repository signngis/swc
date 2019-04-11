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

      <form class="form-signin" role="form" action="${ctp }/member/sendEmail" method="get">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 
        	重置密码邮件发送</h2>
        	<span class="error">${msg }</span>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" 
			 placeholder="请输入注册时的邮箱" name="email" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
        <input class="btn btn-lg btn-success btn-block" type="submit" value="确认" />
      </form>
    </div>
    <!-- 引入公共的JS -->
    <%@include file="/WEB-INF/include/common-js.jsp" %>
    
  </body>
</html>