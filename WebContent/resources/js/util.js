function loadJs(src){
    var oHead = document.getElementsByTagName("HEAD").item(0);

    var oScript= document.createElement("script");

    oScript.type = "text/javascript";

    oScript.src= src;

    oHead.appendChild( oScript);

}

function isNumber(oNum) { 
  if(!oNum) return false; 
  var strP=/^\d+(\.\d+)?$/; 
  if(!strP.test(oNum)) return false; 
  try{ 
  	if(parseFloat(oNum)!=oNum) return false; 
  } 
  catch(ex) 
  { 
   return false; 
  } 
  return true; 
}

function isEmpty(str) {
	if (str == null || str == "undefined" || str.length == 0) {
		return true;
	}
	return false;
}

function checkPortalPwd(pwd) {
	return /^\d{4,20}$/.test(pwd);
}

function checkAll(obj)
{
	if(obj.attr('checked')== "checked")
	{
		$(':checkbox').attr('checked',"checked")
	} else {
		$(':checkbox').attr('checked',false)
	}
}

Date.prototype.format = function(format) {
	/*
	 * eg:format="YYYY-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

function pageHeight() {
	if ($.browser.msie) {
		return document.compatMode == "CSS1Compat" ? document.documentElement.clientHeight
				: document.body.clientHeight;
	} else {
		return self.innerHeight;
	}
};

function pageSkip(value) {
    $("#currentPage").val(value);
    $("#pageSize").val($("#pageSizeSel").val());
    $("#queryForList").click();
}

function jumpTo() {
    var jumpPage = $("#jumpPage").val();

    if (isEmpty(jumpPage)) {
        alert('请输入跳转到的页码');
        return;
    }
    
    var bool = /^\d+$/.test(jumpPage);
    if(!bool){
        alert('请输入有效的页码');
        return;
    }
    
    var totalPage = parseInt($("#totalPage").val());
    if (parseInt(jumpPage) < 1) {
    	jumpPage = 1;
    }else if (parseInt(jumpPage) > totalPage) {
    	jumpPage = totalPage;
    }
    
    pageSkip(jumpPage);
}

/**
 * 显示loading画面
 * @param desc
 * @return
 */
function showLoading(desc) {
	//document.write("<script type='text/javascript' src='${ctx}/js/jquery.jmpopups.js'></script>");
	//alert("${pageContext.request.contextPath}");
	//loadJs(ctx+'/js/jquery.jmpopups.js');
	$("body").append("<div id=\"processingdiv\" style=\"display:none;\"><div class=\"popup\"> <div class=\"popup-body\"><div class=\"loading\"><span>"+desc+"</span></div></div></div></div>");

	//alert($("head").html());
	
	$.openPopupLayer({
		name: "processing",
		width: 300,
		target: "processingdiv"
	});
}

/**
 * 关闭loading画面
 * @param desc
 * @return
 */
function hideLoading() {
$.closePopupLayer('processing');
$("#processingdiv").remove();
}



/**
 * jQuery插件
 * 计算input和textarea的剩余可输入的字数
 * 输入参数：
 * 	maxNumber:字数最大值
 *  targetid:用来回显剩余字数的元素控件，目前只支持span/font/p
 */
(function($) {
   $.fn.calcWordNum = function(options) {
        var defaults = { maxNumber: 512 , targetid: null };        
        var opts = $.extend({}, defaults, options);
        var target = $("#"+opts.targetid);
        if(target==null||(!target.is("span")&&!target.is("font")&&!target.is("p"))){
        	return false;
        }
        var obj = this;
        if (opts.maxNumber>0) {
        	//当编辑框已有内容时，进行初始化
        	var init_num=0;
        	if(opts.maxNumber > obj.val().length){
        		init_num = opts.maxNumber-obj.val().length
        	}
        	target.html(init_num);
            
             obj.bind("keyup", function(event){
 				var maxLength = opts.maxNumber;
 				if(event.which == 8){// backspace, skip content length check.
 					target.html(maxLength-obj.val().length);
 					return true;
 				}				
 				if(obj.val().length > maxLength){
 					obj.val(obj.val().substring(0,maxLength));
 					target.html(0);
 					// scroll to bottom
 					if(obj.is("textarea")){
	 					obj.scrollTop(99999) 
	 					obj.scrollTop(obj.scrollTop()*12)
 					}
 					return false;
 				}else{
 					target.html(maxLength - obj.val().length);
 				}
 			});
             
        }else{
        	alert("calcWordNum:输入的maxNumber参数必须是正整数!");
        	return 0;
        }
    };
})(jQuery);



//鼠标移动表格效果
$(document).ready(function(){
	$("tr[overstyle='on']").hover(
	  function () {
	    $(this).addClass("bg_hover");
	  },
	  function () {
	    $(this).removeClass("bg_hover");
	  }
	);
});


function checkon(o){
		if( o.checked == true ){
			$(o).parents('tr').addClass('bg_on') ;
		}else{
			$(o).parents('tr').removeClass('bg_on') ;
		}
	}
	
function checkAll(o){
	if( o.checked == true ){
		$('input[name="checkbox"]').attr('checked','true');
		$('tr[overstyle="on"]').addClass("bg_on");
	}else{
		$('input[name="checkbox"]').removeAttr('checked');
		$('tr[overstyle="on"]').removeClass("bg_on");
	}
}

//获取已选择用户的ID数组
function getChecked() {
	var uids = new Array();
	$.each($('table input:checked'), function(i, n){
		uids.push( $(n).val() );
	});
	return uids;
}


function Clock() {
	var date = new Date();
	this.year = date.getFullYear();
	this.month = date.getMonth() + 1;
	this.date = date.getDate();
	this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
	this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	this.toString = function() {
		return "" + this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day; 
	};
	
	this.toSimpleDate = function() {
		return this.year + "-" + this.month + "-" + this.date;
	};
	
	this.toDetailDate = function() {
		return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
	};
	
	this.display = function(ele) {
		var clock = new Clock();
		ele.innerHTML = clock.toString();
		window.setTimeout(function() {clock.display(ele);}, 1000);
	};
}