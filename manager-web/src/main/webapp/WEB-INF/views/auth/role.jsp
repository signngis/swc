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
		pageContext.setAttribute("title", "角色维护");
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
									<input id="queryStr" class="form-control has-success"
										type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button id="conditionQueryBtn" type="button"
								class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" id="roleDeleteAllBtn" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button id="roleAddBtn" type="button" class="btn btn-primary"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table id="roleTable" class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox" id="bigCheckBtn"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>

								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul id="pageBarUl" class="pagination">

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

	<!-- 角色新增模态框 -->
	<div class="modal fade" id="roleAddModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">角色新增</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label>角色名</label> 
							<input type="text" class="form-control" name="name">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="roleSaveSubmitBtn" type="button" class="btn btn-primary">新增</button>
				</div>
			</div>
		</div>
	</div>


	<!-- 角色修改模态框 -->
	<div class="modal fade" id="roleEditModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">角色修改</h4>
				</div>
				<div class="modal-body">
					<form>
						<input id="roleIdInput" type="hidden" class="form-control" name="id">
						<div class="form-group">
							<label>角色名</label> 
							<input id="roleNameInput" type="text" class="form-control" name="name">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="roleUpdateSubmitBtn" type="button" class="btn btn-primary">修改</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!--权限分配模态框  -->
	<div class="modal fade" id="rolePermissionModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">分配权限</h4>
				</div>
				<div class="modal-body">
					 <ul id="permissionTree" class="ztree"></ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="rolePermissionSubmitBtn" type="button" class="btn btn-primary">分配权限</button>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/include/common-js.jsp"%>
	<script type="text/javascript">
		$(function(){
			//页面加载完成先查出权限树
			initPermissionTree();
		})
		var globalRid = null;
		//设置全局ajax错误
		$.ajaxSetup({
			//请求发送之前的回调
			beforeSend:function(){
				layer.load(0, {shade: false});
			},
			//complete代表请求完成（无论成功失败）
			complete:function(){
				layer.close(layer.index);
			},
			//请求失败回调
			error:function(){
				layer.msg("网络炸了...");
			},
			
		});
		
		
	
		//调用全选全不选功能
		checkAll("#bigCheckBtn",".smallCheckBtn");
	
		//全局保存页码和分页条件，方便当前页面任何时候任何方法来获取调用；
		var pn = 1;
		var condition = null;
		
		//================以下是删除=============
		//1、单个删除
		$("tbody").on("click",".roleSingleDeleteBtn",function(){
				var rid = $(this).attr("rid")
				layer.confirm('确认删除当前角色吗？', {
					  btn: ['确认','取消'] //按钮
					},function(){
						/* $.get("${ctp}/role/delete",{ids:rid},function(){
							layer.msg("删除完成");
							gotoPage(pn, condition);
						}) */
						$.ajax({
							url:"${ctp}/role/delete",
							data:{ids:rid},
							success:function(data){
								layer.msg("删除完成");
								gotoPage(pn, condition);
							}
						});
					},function(){
						
				});
				
		});
		//2、批量删除
		$("#roleDeleteAllBtn").click(function(){
			//
			var rids = "";
			$(".smallCheckBtn:checked").each(function(){
				rids+=$(this).attr("rid")+",";
			})
			
			layer.confirm('确认删除这些角色吗？', {
				  btn: ['确认','取消'] //按钮
				},function(){
					/* $.get("${ctp}/role/delete",{ids:rids},function(){
						layer.msg("删除完成");
						gotoPage(pn, condition);
					}).fail(function(){
						layer.msg("网络有异常了...")
					}) */
					//使用延迟函数捕获一个普通的ajax请求的成功失败回调
					$.get("${ctp}/role/delete",{ids:rids})
						.done(function(data){
							layer.msg("删除完成"+data);
							gotoPage(pn, condition);
						})
						/* .fail(function(){
							layer.msg("网络有异常了...")
						}) */
				},function(){
					
			});
		});
		
		
		//=================以下是修改===============
		//0、点击分配按钮弹出权限分配模态框
		$("tbody").on('click','.rolePermissionBtn',function(){
			globalRid = $(this).attr("rid");
			//1、弹出权限分配模态框
			$("#rolePermissionModal").modal({
				show:true,
				backdrop:'static'
			});
			//2、回显权限树内容
			showHasPermission();
			
			
		})
		function showHasPermission(){
			//1、发送ajax请求获取到数据库中当前角色已有的权限
			//alert(globalRid);
			//回显之前去掉之前的状态
			$.get("${ctp}/per/roleper.json",{rid:globalRid},function(data){
				var zObj = $.fn.zTree.getZTreeObj("permissionTree");
				zObj.checkAllNodes(false);
				$.each(data,function(){
					var id = this.id;
					//1、在ztree中找到当前id为指定值的节点
					var node = zObj.getNodeByParam("id",id,null);
					//2、选中节点
					zObj.checkNode(node,true,false);
				})
			});
			//2、勾选这些权限
		}
		
		//点击分配权限按钮将权限提交出去
		$("#rolePermissionSubmitBtn").click(function(){
			var zObj = $.fn.zTree.getZTreeObj("permissionTree");
			var pids = new Array();
			$.each(zObj.getCheckedNodes(true),function(){
				pids.push(this.id);
			});
			var pidStr = pids.toString();
			//alert("角色id："+globalRid+"  权限id："+pidStr);
			$.post("${ctp}/per/assign",{rid:globalRid,pids:pidStr},function(){
				layer.closeAll('loading');
				$("#rolePermissionModal").modal('hide');
				layer.msg("分配成功");
			});
		});
			
			
		//1、点击修改按钮弹出修改模态框；并回显数据
		$("tbody").on("click",".roleSingleEditBtn",function(){
			//获取当前按钮自定义属性rid保存的角色id的值
			var rid = $(this).attr("rid");
			//1、弹出修改模态框
			$("#roleEditModal").modal({
				backdrop : 'static',
				show : true
			})
			//2、发送ajax请求，回显数据；
			$.get("${ctp}/role/getById",{id:rid},function(data){
				//console.log(data);
				//回显id
				$("#roleIdInput").val(data.id);
				//回显name
				$("#roleNameInput").val(data.name);
			})
		})
		//2、点击修改发送请求修改数据
		$("#roleUpdateSubmitBtn").click(function(){
			//1、获取到表单的数据
			var rId = $("#roleIdInput").val();
			var rName = $("#roleNameInput").val();
			//2、发送请求将数据提交过去
			$.post("${ctp}/role/update",{id:rId,name:rName},function(data){
				layer.msg("修改成功!");
				$("#roleEditModal").modal('hide');
				gotoPage(pn, condition);
			})
		});
		
		
		//================以下是添加==================
		//角色添加模态框打开
		$("#roleAddBtn").click(function() {
			//
			$("#roleAddModal").modal({
				backdrop : 'static',
				show : true
			});
		});
		//角色添加功能
		$("#roleSaveSubmitBtn").click(function(){
			$.post("${ctp}/role/save",{name:$("#roleAddModal input[name='name']").val()},function(data){
				//保存成功的回调
				layer.msg("角色添加完成");
				//来到最后一页
				gotoPage(999999, null);
				$("#roleAddModal").modal('hide');
			});
		});

		//=========================================================
		// var condition;
		$(function() {
			gotoPage(1, null);
		})

		//带条件的翻页查询
		$("#conditionQueryBtn").click(function() {
			//获取查询条件
			condition = $("#queryStr").val();
			// 新查询肯定从第一页开始
			gotoPage(1, condition);
		});

		//翻页；页码和条件
		function gotoPage(pn, condition) {
			$.get("${ctp}/role/list.json", {
				pn : pn,
				condition : condition
			}, function(data) {
				//console.log(data);
				//显示表格数据
				buildTableContent(data);
				//构建分页条
				buildPageBar(data);
			});
		}

		//构建分页条数据
		function buildPageBar(data) {
			//清空分页条
			$("#pageBarUl").empty();

			var startPage = "<li pn='1'><a href='#'>首页</a></li>";
			var prePage = "<li pn='"+data.prePage+"'><a href='#'>上一页</a></li>";
			$("#pageBarUl").append(startPage).append(prePage);

			var nums = data.navigatepageNums;
			$.each(nums,function() {
				if (this == data.pageNum) {
					var currPage = "<li pn='"+this+"' class='active'><a href='#'>"+ this + "</a></li>";
					$("#pageBarUl").append(currPage);
				} else {
					var currPage = "<li pn='"+this+"'><a href='#'>"+ this + "</a></li>";
					$("#pageBarUl").append(currPage);
				}

			})

			var nextPage = "<li pn='"+data.nextPage+"'><a href='#'>下一页</a></li>";
			var endPage = "<li pn='"+data.pages+"'><a href='#'>末页</a></li>";
			$("#pageBarUl").append(nextPage).append(endPage);

		}

		//构建表格里面的数据
		function buildTableContent(data) {
			//清空表格里面的内容
			$("#roleTable tbody").empty();
			//获取到内容
			var content = data.list;
			$.each(content,function() {
								//alert(this.id+"==>"+this.name);
								//
				var trEle = $("<tr></tr>");
					trEle.append("<td>" + this.id + "</td>")
						.append("<td><input rid='"+this.id+"' type='checkbox' class='smallCheckBtn'></td>")
						.append("<td>" + this.name + "</td>")
						.append("<td>"
									+ "<button type='button' rid='"+this.id+"'  class='btn btn-success btn-xs rolePermissionBtn'><i class='glyphicon glyphicon-check'></i></button>"
									+ "<button type='button' rid='"+this.id+"' class='roleSingleEditBtn btn btn-primary btn-xs'><i class='glyphicon glyphicon-pencil'></i></button>"
									+ "<button type='button' rid='"+this.id+"' class='roleSingleDeleteBtn btn btn-danger btn-xs'><i class='glyphicon glyphicon-remove'></i></button>"
								+ "</td>")
						.appendTo($("#roleTable tbody"));
			});
		}

		//点击分页功能；
		//普通的.click()只能给已有的【页面上用代码直接写上】元素绑事件，不能给未来的【后来利用DOM增删改动态添加】绑；

		//父元素.on("事件","子元素过滤器",回调)
		$("#pageBarUl").on("click", "li", function() {
			pn = $(this).attr("pn");
			//获取条件
			condition = $("#queryStr").val();
			gotoPage(pn, condition);
		})
		
		
		//初始化权限树
    	function initPermissionTree(){
    		var setting = {
    				data: {
    					simpleData: {
    						//使用简单数据
    						enable: true,
    						pIdKey: "pid",
    						rootPId:0
    					},
    					key:{
    						url:"hahah"
    					}
    				},
    				view: {
    					//指定回调方法名
    					addDiyDom: showMyIcon
    				},
    				check: {
    					enable: true
    				}
    			};
    		$.get("${ctp}/per/list.json",function(data){
    			//data[data.length]={id:0,name:"系统菜单",pid:null,icon:"glyphicon glyphicon-gift"};
    			//给这个data放一个系统默认的父菜单
    			var zObj = $.fn.zTree.init($("#permissionTree"), setting, data);
    			//zObj.addNodes(null,{id:0,name:"系统菜单",icon:"glyphicon glyphicon-gift"});
    			//展开所有
    			zObj.expandAll(true);
    		})
    		 
    	}
		function showMyIcon(treeId,treeNode){
    		//console.log("treeId："+treeId);
    		//console.log(treeNode);
    		
    		//permissionTree_1_ico
    		$("#"+treeNode.tId+"_ico").removeClass();
    		$("#"+treeNode.tId+"_ico").after("<span class='"+treeNode.icon+"'></span>");
    	}
	</script>
</body>
</html>
