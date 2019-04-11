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

.tree-closed {
	height: 40px;
}

.tree-expanded {
	height: auto;
}
</style>
</head>

<body>
	<%
		pageContext.setAttribute("title", "控制面板");
	%>
	<!-- 顶部导航 -->
	<%@include file="/WEB-INF/include/manager-nav.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<!-- 侧边导航 -->
			<%@include file="/WEB-INF/include/manager-sidebar.jsp"%>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">控制面板sss</h1>

				<div class="row placeholders">
					<div class="col-xs-6 col-sm-3 placeholder">
						<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
							alt="Generic placeholder thumbnail">
						<h4>Label</h4>
						<span class="text-muted">Something else</span>
					</div>
					<div class="col-xs-6 col-sm-3 placeholder">
						<img data-src="holder.js/200x200/auto/vine" class="img-responsive"
							alt="Generic placeholder thumbnail">
						<h4>Label</h4>
						<span class="text-muted">Something else</span>
					</div>
					<div class="col-xs-6 col-sm-3 placeholder">
						<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
							alt="Generic placeholder thumbnail">
						<h4>Label</h4>
						<span class="text-muted">Something else</span>
					</div>
					<div class="col-xs-6 col-sm-3 placeholder">
						<img data-src="holder.js/200x200/auto/vine" class="img-responsive"
							alt="Generic placeholder thumbnail">
						<h4>Label</h4>
						<span class="text-muted">Something else</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/include/common-js.jsp"%>
	<script type="text/javascript">
		//显示每一个父菜单的子菜单个数
		/**
		java:Spring；this；
		
		JQuery集合或元素遍历：
			//如果对象不是jquery对象，我们就用$.each
			$.each(obj【遍历的集合】,function(i【index索引从0开始】,n【当前的元素】){}【每次遍历的回调】);
			
			//如果对象是jquery集合或者数据，我们直接用 obj.each();
			//只有用jquery的选择器找到的元素才算jquery对象。$(dom)这也是jquery对象；
					this永远都是js对象
		 */
		$(".list-group-item").each(function() {
			//当前的原生dom对象
			var num = $(this).find("li").length;
			$(this).find("span.badge").text(num);
		});
	</script>
</body>
</html>
