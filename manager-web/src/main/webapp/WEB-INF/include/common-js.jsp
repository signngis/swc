<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="${ctp}/static/jquery/jquery-3.2.1.min.js"></script>
<script src="${ctp}/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctp}/static/script/docs.min.js"></script>
<script src="${ctp}/static/script/back-to-top.js"></script>

<script
	src="${ctp}/static/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
<script
	src="${ctp}/static/jquery-validation-1.13.1/dist/localization/messages_zh.min.js"></script>

<script src="${ctp}/static/layer/layer.js"></script>
<script src="${ctp}/static/script/checkAll.js"></script>
<script src="${ctp}/static/zTree_v3-3.5.28/js/jquery.ztree.all.js"></script>
<script src="//cdn.datatables.net/1.10.18/js/jquery.dataTables.min.js"></script>


<script type="text/javascript">

	/* 菜单展开合并 */
	$(function() {
		$(".list-group-item").click(function() {
			if ($(this).find("ul")) {
				$(this).toggleClass("tree-closed");
				if ($(this).hasClass("tree-closed")) {
					$("ul", this).hide("fast");
				} else {
					$("ul", this).show("fast");
				}
			}
		});
	});
	

	$(function() {
		highLightA();
	});
	//当前是user.jsp；我们应该找用户维护
	function highLightA() {
		//把a改成红颜色
		$(".list-group-item a:contains('${title}')").css("color", "red");
		//找到这个a标签的父元素，给他显示出来
		$(".list-group-item a:contains('${title}')").parent().parent().show();
		//切换父元素的tree-closed状态
		$(".list-group-item a:contains('${title}')").parents(
				"li.list-group-item").toggleClass("tree-closed");
	}
</script>