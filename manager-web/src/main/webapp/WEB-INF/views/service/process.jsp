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
		pageContext.setAttribute("title", "流程管理");
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
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>

						<button id="openUploadModalBtn" type="button" class="btn btn-primary"
							style="float: right;">
							<i class="glyphicon glyphicon-upload"></i> 上传流程定义文件
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table id="processTable" class="table  table-bordered">
								<thead>
									<tr>
										<th>#</th>
										<th>category</th>
										<th>name</th>
										<th>key</th>
										<th>version</th>
										<th>resourceName</th>
										<th>deploymentId</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 文件上传模态框 -->
	<div class="modal fade" id="uploadFileModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">上传流程</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label>文件</label> 
							<input id="fileItemInput" type="file" class="form-control" name="file">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="uploadFileBtn" type="button" class="btn btn-primary">上传</button>
				</div>
			</div>
		</div>
	</div>

	<%@include file="/WEB-INF/include/common-js.jsp"%>
	
	<script type="text/javascript">
	var dataTable = null;
	$("#processTable").on("click",".resourceBtn",function(){
		//alert(this);
		
		//1、获取当前这一行的 id内容
		var pid = dataTable.row($(this).parent()[0]).data().id;
		//2、发请求要图片显示
		//var image = $("<img src='${ctp}/proc/images?pid="+pid+"&t="+Math.random()+"'/>");

		layer.open({
				type : 1,
				content : "<img src='${ctp}/proc/images?pid="+pid+"&t="+Math.random()+"'/>"
		});
	})

		//
		$(function() {
			showTable();
		})
		//	http://localhost:8081/manager-web/proc/images?pid=xxx
		function showTable() {
			dataTable = $('#processTable')
					.DataTable(
							{
								destroy : true, //先删除之前的表格显示再刷新
								ajax : {
									url : '${ctp}/proc/all.json',
									dataSrc : ""
								},
								columns : [
										{
											data : "id"
										},
										{
											data : "category"
										},
										{
											data : "name"
										},
										{
											data : "key"
										},
										{
											data : "version"
										},
										{
											data : "resourceName",
											className : "resourceBtn"
										},
										{
											data : "deploymentId"
										},
										{
											defaultContent : "<button type='button' class='btn btn-success btn-xs'>"
													+ "<i class='glyphicon glyphicon-eye-open'></i></button>"
													+ " <button type='button' class='btn btn-danger btn-xs'>"
													+ "<i class='glyphicon glyphicon-remove'></i></button>",
											orderable : false
										} ],
								language : {
									search : "搜索"
								}
							});
		}

		//打开模态框
		$("#openUploadModalBtn").click(function() {
			$("#uploadFileModal").modal({
				show : true,
				backdrop : 'static'
			})
		});

		//上传流程
		$("#uploadFileBtn").click(function() {
			//1、上传流程文件
			//   1）、new FormData：传入表单的dom对象，直接会将表单的所有值都封装好
			//   2）、手动给fd里面添加值
			var file = $("#fileItemInput")[0].files[0];
			var fd = new FormData();
			fd.append("file", file);
			//alert(file.name);
			//文件名必须以 .bpmn结尾才合法
			//var regx = /^[](\.bpmn)/;
			//alert(file.name.match(regx));
			//  xxxxx  bpmn
			var s = file.name.split(".");
			if (s[s.length - 1] != "bpmn") {
				layer.alert("文件必须是.bpmn格式");
				return;
			}

			//$("#form").submit();

			//AJAX文件上传-====POST
			$.ajax({
				url : "${ctp}/proc/deploy",
				type : "POST",
				data : fd,
				contentType : false, //内容类型不用默认
				processData : false, //不去处理数据
				success : function(data) {
					layer.msg(data.msg);
					showTable();
				},
				error : function(data) {
					layer.msg(data)
				}
			})

			//2、关闭模态框
			$("#uploadFileModal").modal('hide');
		})
	</script>
</body>
</html>
