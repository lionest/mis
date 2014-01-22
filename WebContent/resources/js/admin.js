//默认运行
$.ajaxSetup({cache: false}); 
$(function(){
	//首次启动时进行界面尺寸初始化
	index_resize();
	
});

//浏览器尺寸变化时运行
	$(window).resize(function(){
		index_resize();
	});

//重新计算工作界面尺寸
function index_resize(){
	
	var page_width;
	var page_height;

	page_width=$(window).width()-220;
	page_height=$(window).height()-118;

	
	//尺寸调整
	
	$('.left_nav').height(page_height);
		
	$('.pages').height(page_height);
	$('.pages').width(page_width);
	
}

function refreshTreeMenu(prjId){
	$(window.parent.document).contents().find("#prjId").val(prjId);
	$(window.parent.document).contents().find("#refreshMenuBtn").click();
}

function refreshMenuItem(menuName){
	$(window.parent.document).contents().find("#menuName").val(menuName);
	$(window.parent.document).contents().find("#findMenuItem").click();
}


