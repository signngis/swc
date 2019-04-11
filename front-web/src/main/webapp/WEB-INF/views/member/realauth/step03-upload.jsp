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
	<link rel="stylesheet" href="${ctp}/static/css/theme.css">
	<style>
#footer {
    padding: 15px 0;
    background: #fff;
    border-top: 1px solid #ddd;
    text-align: center;
}
	</style>
  </head>
  <body>
 <div class="navbar-wrapper">
      <div class="container">
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			  <div class="container">
				<div class="navbar-header">
				  <a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a>
				</div>
            <div id="navbar" class="navbar-collapse collapse" style="float:right;">
              <ul class="nav navbar-nav">
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> 张三<span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="member.html"><i class="glyphicon glyphicon-scale"></i> 会员中心</a></li>
                    <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                    <li class="divider"></li>
                    <li><a href="index.html"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                  </ul>
                </li>
              </ul>
            </div>
			  </div>
			</nav>

      </div>
    </div>

    <div class="container theme-showcase" role="main">
      <div class="page-header">
        <h1>实名认证 - 申请</h1>
      </div>

		<ul class="nav nav-tabs" role="tablist">
		  <li role="presentation" ><a href="#"><span class="badge">1</span> 基本信息</a></li>
		  <li role="presentation" class="active"><a href="#"><span class="badge">2</span> 资质文件上传</a></li>
		  <li role="presentation"><a href="#"><span class="badge">3</span> 邮箱确认</a></li>
		  <li role="presentation"><a href="#"><span class="badge">4</span> 申请确认</a></li>
		</ul>
        
		<form id="showForm" role="form" style="margin-top:20px;">
		  <div id="divEle"></div>
          <button type="button" onclick="window.location.href='${ctp}/auth/step02.html'" class="btn btn-default">上一步</button>
		  <button type="button" id="nextBtn"  class="btn btn-success">下一步</button>
		</form>
		<hr>
    </div> <!-- /container -->
        <div class="container" style="margin-top:20px;">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div id="footer">
                        <div class="footerNav">
                             <a rel="nofollow" href="http://www.atguigu.com">关于我们</a> | <a rel="nofollow" href="http://www.atguigu.com">服务条款</a> | <a rel="nofollow" href="http://www.atguigu.com">免责声明</a> | <a rel="nofollow" href="http://www.atguigu.com">网站地图</a> | <a rel="nofollow" href="http://www.atguigu.com">联系我们</a>
                        </div>
                        <div class="copyRight">
                            Copyright ?2017-2017 atguigu.com 版权所有
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
   <%@include file="/WEB-INF/include/common-js.jsp" %>
	<script>
		/* onclick="'" */
		$("#nextBtn").click(function(){
			var pd = new Array();
			//1、组装数据
			$("#divEle .form-group").each(function(){
				console.log(this);
				var cid = $(this).find("input[name='certid']").val();
				var ipath = $(this).find("input[name='iconpath']").val();
				var token = $(this).find("input[name='token']").val();
				//alert(cid+"--->"+ipath+"===>"+token);
				var obj = {certid:cid,iconpath:ipath,token:token};
				pd.push(obj);
			});
			//console.log(pd);
			//将对象转为json数据
			var json = JSON.stringify(pd);
			//2、发送请求提交
			//1、JSON数据提交：  1）、把对象转为json字符串    2）、ajax请求设置contentType:"application/json"，processData:false
			$.ajax({
				url:"http://localhost:8082/rest-api/member/center/certs",
				type:"post",
				data:json,
				contentType:"application/json",
				processData:false, //不处理原生json字符串，直接提交
				success:function(data){
					location.href='${ctp}/auth/step04.html';
				}
			})
		});
	
		/* 自动上传并回显  */
		$("#showForm").on("change","input[type='file']",function(){
			//alert("1");
			//文件上传;
			//console.log();
			var fd = new FormData();
			fd.append("file",this.files[0])
			var iconInput = $(this).nextAll("input[name='iconpath']");
			$.ajax({
				url:"http://localhost:8082/rest-api/sys/upload",
				type:"post",
				data:fd,
				contentType:false,
				processData:false,
				success:function(data){
					var imgPath = "http://localhost:8082/rest-api/"+data.content;
					//1、将文件路径保存在iconpath中
					iconInput.val(imgPath);
					var imgEle = "<img src='"+imgPath+"' width='280px' class='img-rounded'>";
					iconInput.nextAll("img").remove();
					iconInput.after(imgEle);
				}
			});
			
		});
		
	
		$(function(){
			//1、查询当前页面需要提交的证件
			var ud = JSON.parse(localStorage.userData);
  			var pd = {token:ud.content.token};
			$.get("http://localhost:8082/rest-api/member/center/certs",pd,function(data){
				//console.log(data);
				$.each(data.content,function(){
					var div = $("<div class='form-group'></div>")
						.append("<label>"+this.name+"</label>")
						.append("<input type='file' name='file' class='form-control' >")
						.append("<input type='hidden' name='certid' value='"+this.id+"'/>")
						.append("<input type='hidden' name='iconpath'/>")
						.append("<input type='hidden' name='token' value='"+ud.content.token+"'>")
						;
					//更新登陆信息
					$("#showForm #divEle").append(div);	
				})
			});
		})
	
        $('#myTab a').click(function (e) {
          e.preventDefault()
          $(this).tab('show')
        });        
	</script>
  </body>
</html>