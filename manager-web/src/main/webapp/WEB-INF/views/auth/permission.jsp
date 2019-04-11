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
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <%
		pageContext.setAttribute("title", "许可维护");
	%>
	<!--顶部导航  -->
	<%@include file="/WEB-INF/include/manager-nav.jsp"%>

    <div class="container-fluid">
      <div class="row">
       <!-- 侧边导航 -->
		<%@include file="/WEB-INF/include/manager-sidebar.jsp"%>
        
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="panel panel-default">
              <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
                  <ul id="permissionTree" class="ztree"></ul>
			  </div>
			</div>
        </div>
      </div>
    </div>
    
    <!-- 权限添加模态框  -->
    <div class="modal fade" id="permissionAddModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增权限</h4>
				</div>
				<div class="modal-body">
					<form>
						<!-- 父菜单  -->
						<input type="hidden" name="pid"/>
						<div class="form-group">
							<label>父菜单</label> 
							<input type="text" disabled="disabled" class="form-control">
						</div>
						<div class="form-group">
							<label>权限名</label> 
							<input type="text" class="form-control" name="name">
						</div>
						<div class="form-group">
							<label>图标</label> 
							<input type="text" class="form-control" name="icon">
						</div>
						<div class="form-group">
							<label>URL</label> 
							<input type="text" class="form-control" name="url">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="permissionSaveSubmitBtn" type="button" class="btn btn-primary">新增</button>
				</div>
			</div>
		</div>
	</div>
	
	 <!-- 权限修改模态框  -->
    <div class="modal fade" id="permissionEditModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">修改权限</h4>
				</div>
				<div class="modal-body">
					<form>
						<!-- 当前菜单的id -->
						<input type="hidden" name="id"/>
						<!-- 父菜单  -->
						<div class="form-group">
							<label>父菜单</label> 
							<select name="pid" class="form-control">
								
							</select>
						</div>
						<div class="form-group">
							<label>权限名</label> 
							<input type="text" class="form-control" name="name">
						</div>
						<div class="form-group">
							<label>图标</label> 
							<input type="text" class="form-control" name="icon">
						</div>
						<div class="form-group">
							<label>URL</label> 
							<input type="text" class="form-control" name="url">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="permissionEditSubmitBtn" type="button" class="btn btn-primary">修改</button>
				</div>
			</div>
		</div>
	</div>
    
    
    <%@include file="/WEB-INF/include/common-js.jsp"%>
    <script type="text/javascript">
	
    	//绑定事件完成CRUD
    	//添加
    	/* $("#permissionTree").on("click",".addPerBtn",function(){
    		alert("点了添加");
    	});
		$("#permissionTree").on("click",".delPerBtn",function(){
			alert("点了删除");
    	}); */
		
    	//回调里面的一个参数封装事件对象，封装了事件所有信息的
		$("#permissionTree").on("click",".btnGroup",function(e){
			//代表当前点击的按钮是哪个菜单的
			var mid = $(e.target).parent("#btnGroup").attr("mid");
			var zObj = $.fn.zTree.getZTreeObj("permissionTree");
			//当前菜单的节点数据
			var node = zObj.getNodeByParam("id",mid,null);
			
			
			//e.target 表示当前点击的是哪个元素
			if($(e.target).is(".delPerBtn")){
				layer.confirm("确认删除【"+node.name+"】吗？",{btn:["确认","取消"]},function(){
					$.get("${ctp}/per/delete",{ids:node.id},function(){
						layer.msg("删除成功..");
						//刷新菜单
						initPermissionTree();
					})
				},function(){});
			}
			if($(e.target).is(".addPerBtn")){
				$("#permissionAddModal").modal({
					show:true,
					backdrop:'static'
				});
				//回显父菜单的信息；
				$("#permissionAddModal input[name='pid']").val(node.id);
				//按照当前菜单的id查出ztree中这个菜单的treeNode；
				$("#permissionAddModal input:disabled").val(node.name);
				
			}
			if($(e.target).is(".editPerBtn")){
				//1、显示模态框
				showEditPermissionModal();
				//回显自己的id
				$("#permissionEditModal input[name='id']").val(node.id);
				//回显自己的其他属性
				$("#permissionEditModal input[name='name']").val(node.name);
				$("#permissionEditModal input[name='icon']").val(node.icon);
				$("#permissionEditModal input[name='url']").val(node.url);
				
				
				//2、回显（选中）自己的父菜单
				$("#permissionEditModal option").each(function(){
					console.log(this);
				});
				$("#permissionEditModal select").val([node.pid]);
			}
		})
		
		//角色修改完成
		$("#permissionEditSubmitBtn").click(function(){
			var formData = $("#permissionEditModal form").serialize();
			$.post("${ctp}/per/update",formData,function(){
				$("#permissionEditModal").modal('hide');
				layer.msg("修改成功...")
				//刷新列表
				initPermissionTree();
			});
		});
		
		//角色修改信息回显
		function showEditPermissionModal(){
    		$("#permissionEditModal").modal({
				show:true,
				backdrop:'static'
			});
    		//查出所有的父菜单
    		$.ajax({
    			url:"${ctp}/per/parents.json",
    			type:"get",
    			//必须变为同步方法，否则有可能数据还没获取过来就要进行选中操作，导致失败；
    			async:false,
    			success:function(data){
    				console.log(data);
    				$("#permissionEditModal select").empty();
    				$.each(data,function(){
    					var option = "<option value='"+this.id+"'>"+this.name+"</option>";
    					$("#permissionEditModal select").append(option);
    				})
    			}
    		})
			
    	}
		
		
    
		
		//角色新增
		$("#permissionSaveSubmitBtn").click(function(){
			var formData = $("#permissionAddModal form").serialize();
			$.post("${ctp}/per/save",formData,function(data){
				layer.msg("权限新增完成");
				//关闭模态框
				$("#permissionAddModal").modal('hide');
				//刷新列表
				initPermissionTree();
			})
		});
		
		
		function showBtnGroup(treeId,treeNode){
			//$(".btnGroup").remove();
			//把当前菜单的id获取，放在自定义属性上
			var btnGroup = $("<span mid='"+treeNode.id+"' id='btnGroup' class='btnGroup'></span>");
			var addBtn = "<a class='addPerBtn btn btn-primary btn-xs glyphicon glyphicon-plus'></a>";
			var removeBtn = "<a class='delPerBtn btn btn-danger btn-xs glyphicon glyphicon-remove'></a>";
			var editBtn = "<a class='editPerBtn btn btn-info btn-xs glyphicon glyphicon-pencil'></a>";
			//2、如果是父菜单，没有子元素；显示添加、删除、修改按钮
			if(treeNode.pid == 0 && treeNode.children==null){
				btnGroup.append(addBtn).append(editBtn).append(removeBtn);
				//放之前看一下有没有
				if($("#"+treeNode.tId+"_a .btnGroup").length<=0){
					$("#"+treeNode.tId+"_a").append(btnGroup);
				}
				return;
			}
			
			//1、如果是父菜单，并且有子元素；显示添加和修改按钮
			if(treeNode.pid == 0 && treeNode.children.length>0){
				btnGroup.append(addBtn).append(editBtn);
			}
			
			//3、如果是子元素，显示删除、修改按钮；
			if(treeNode.pid != 0){
				btnGroup.append(editBtn).append(removeBtn);
			}
			
			if($("#"+treeNode.tId+"_a .btnGroup").length<=0){
				$("#"+treeNode.tId+"_a").append(btnGroup);
			}
			
			
		}
		
		//鼠标移除以后要删除
		function removeBtnGroup(treeId,treeNode){
			$(".btnGroup").remove();
		}
    	
    	$(function(){
    		initPermissionTree();
    		
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
    					addDiyDom: showMyIcon,
    					//添加按钮组
    					addHoverDom: showBtnGroup,
    					//移除按钮组
    					removeHoverDom: removeBtnGroup
    				}
    			};
    		$.get("${ctp}/per/list.json",function(data){
    			//data[data.length]={id:0,name:"系统菜单",pid:null,icon:"glyphicon glyphicon-gift"};
    			//给这个data放一个系统默认的父菜单
    			data.push({id:0,name:"系统菜单",pid:null,icon:"glyphicon glyphicon-gift"});
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
