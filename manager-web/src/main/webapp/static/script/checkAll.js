
function checkAll(checkAllSelector,singleCheckSelector){
	//全选全不选功能
	
	$("body").on("click",checkAllSelector,function(){
		$(singleCheckSelector).prop("checked", $(this).prop("checked"));
	})
//	$(checkAllSelector).click(function(){
//		// $(this).attr("checked") 获取元素上面写好的属性的
//		//如果获取html自己的属性，但是没有写上，想要正确获取，使用prop；prop不能获取到自定义属性的值
//		//自定义属性，和在HTML标签上写好的推荐用attr获取；
//		//attr不要去来获取由于点击事件等会动态变化的值；（事件属性）
//		//var flag = $(this).prop("checked");
//		//".singleCheckBox"
//		
//	});

	$("body").on("click",singleCheckSelector,function(){
		$(checkAllSelector).prop("checked",$(singleCheckSelector).length == $(singleCheckSelector+":checked").length);
	})
//	$(singleCheckSelector).click(function(){
//		//var flag = $(".singleCheckBox").length == $(".singleCheckBox:checked").length;
//		
//	});
}
