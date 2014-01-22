/**
 * 通过扩展jQuery的方式来定义统一的验证处理机制
 * 在失去焦点或提交时触发验证，当验证失败时，可以自定义消息显示方式
 * 如果没有定义消息显示回调方法，则系统自动显示验证失败信息
 * zhangguojng at 2011/12/7
 * zouxiaoming at 2012/10/21
 */
var check_form_result;
$.extend({
	validate: {
		//定义验证器配置参数
		options: {
			stopValidateOnError: false, //如碰到错误要不要停止验证
			showPrompt: true, //是否在获得焦点时显示提示信息
			validateOnBlur: true, //是否在失去焦点时进行验证
			showMessage: null //定义显示验证消息的方法
		}, 
		
		//定义预置的验证器
		validators: {
			none: { //空验证器
				
			},
			digit:	{ //全数字验证器
				rule: /^\d+$/,
				message: "输入的不是0-9的字符组合"
			},
			int:	{ //整数验证器
				rule: /^[\-\+]?\d+$/,
				message: "输入的不是一个有效的整数"
			},
			"int-range":	{ //整数验证器
				rule: undefined,
				message: "输入的不是一个从{0}到{1}之间的整数"
			},
			double:	{ //整数或小数验证器
				rule: /^[\-\+]?\d+(\.\d+)?$/,
				message: "输入的不是一个有效的数字"
			},
			letter:	{ //英文字母验证器
				rule: /^[A-Za-z]+$/,
				message: "输入的不是一个英文字母组合"
			},
			digitOrLetter:	{ //数字或英文字母验证器
				rule: /^[0-9A-Za-z]+$/,
				message: "输入的不是一个英文字母组合"
			},
			chinese:	{ //中文验证器
				rule: /^[\u4e00-\u9fa5]+$/,
				message: "输入的不是一个有效的中文字符组合"
			},
			word:	{ //有效字符码验证器，即数字或英文字母或中文字符
				rule: /^[\-_\#0-9a-zA-Z\u4e00-\u9fa5]+$/,
				message: "输入的不是一个有效的字符组合"
			},
			sentence:	{ //有效字符码验证器，即数字或英文字母或中文字符
				rule: /^[\-_\#0-9a-zA-Z\[\] \( \)\<\>\{\}.,\u4e00-\u9fa5]+$/,
				message: "输入的信息中包含有非法字符"
			},
			zip:	{ //邮政编码验证器
				rule: /^\d{6}$/,
				message: "输入的不是一个有效的邮政编码"
			},
			phone:	{ //电话号码验证器
				rule: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
				message: "输入的不是一个有效的电话号码"
			},
			mobile:	{ //手机号码验证器
				rule: /^((\(\d{2,3}\))|(\d{3}\-))?((13\d{9})|(15\d{9})|(18\d{9})|(14\d{9}))$/,
				message: "输入的不是一个有效的手机号码"
			},
			tel: { //电话或手机号码验证器
				rule: /^((\(\d{2,3}\))|(\d{3}\-))?((13\d{9})|(15\d{9})|(18\d{9}))$|^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
				message: "输入的不是一个有效的电话号码"
			},
			email:	{ //email验证器
				rule: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
				message: "输入的不是一个有效的Email地址"
			},
			url:	{ //URL验证器
				rule: /^(http|https):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
				message: "输入的不是一个有效的URL地址"
			},
			ip:	{ //IP验证器
				rule: /^(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5])$/,
				message: "输入的不是一个有效的IP地址"
			},
			date:	{ //日期
				rule: /^((((((0[48])|([13579][26])|([2468][048]))00)|([0-9][0-9]((0[48])|([13579][26])|([2468][048]))))-02-29)|(((000[1-9])|(00[1-9][0-9])|(0[1-9][0-9][0-9])|([1-9][0-9][0-9][0-9]))-((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30))|(((0[1-9])|(1[0-2]))-((0[1-9])|(1[0-9])|(2[0-8]))))))$/,
				message: "输入的不是一个有效的日期类型"
			},
			idcard:	{ //中国身份证号码验证器
				rule: /^\d{17}[0-9Xx]$|^\d{15}$/,
				message: "输入的不是一个有效的身份证号码"
			},
			licence:	{ //车牌号码验证器，不包括军车
				rule: /^(([\u4e00-\u9fa5]{1}[A-Za-z]{1})|(LS)|(ls))[A-Za-z0-9]{5}$/,
				message: "输入的不是一个有效的车牌号码"
			},
			vin:	{ //VIN验证器
				rule: /^[0-9a-zA-Z]{17}$/,
				message: "输入的不是一个有效的车架号/VIN号码"
			},
			sameas:	{ //与指定的值相同的验证器
				rule: undefined,
				message: "两次输入的值不一致"
			},
			regex: 	{ //正则表达式验证器，表达式需自定义并做为参数传入，呵呵，它可是一个强大的万能验证器啊，就看你的正则表达式写得给不给力了
				rule: undefined, //该规则要求外部传入
				message: "输入的不是一个有效的数据"
			}
		},
		
		/**
		 * 在指定的元素后面创建span并加入到dom中,返回该jquery元素
		 */
		getMessageSpan: function(element) {
			var failSpan;
			var msg_panel_id = $.trim(element.attr("data-msgpanel"));
			
			if(msg_panel_id != "") {
				failSpan = $("#" + msg_panel_id); //使用指定的消息显示容器
			} else {
				var id = $.trim(element.attr("id"));
				if(id == "") {
					id = new Date().getTime();
					element.attr("id", id);
				}
				
				msg_panel_id = "fail_span_id_" + id;
				var span = document.getElementById(msg_panel_id);
				
				if(! span) {
					element.after("<span id='" + msg_panel_id + "' style='margin-left:10px;padding-left:20px;'></span>");
					span = document.getElementById(msg_panel_id);
				}
				failSpan = $(span);
			}
			//failSpan.html("&nbsp;&nbsp;");
			//failSpan.hide();
			$.validate.setBorder(element, "clear"); //清除边框信息
			return failSpan;
		},
	
		/**
		 * 根据type来设置样式表
		 *type: 创建的span的类型：text:正常的文字提示span，fail:验证失败的span，succ:验证成功的span
		 */
		setStyle: function(ele, type) {
			//先移除所有的样式
			ele.removeClass();
			ele.addClass("help-inline");
			
			if(type == "text"){
				ele.parents(".control-group").removeClass().addClass("control-group info");
				ele.addClass("validator_info");
			}
			else if(type == "fail"){
				ele.parents(".control-group").removeClass().addClass("control-group error");
				ele.addClass("validator_fail");
			}
			else if(type == "succ"){
				ele.parents(".control-group").removeClass().addClass("control-group success");
				ele.addClass("validator_succ");
			}
		},
	
		/**
		 * 设置表单元素的边框效果
		 * type: focus, fail
		 */
		setBorder: function(ele, type) {
			
		},
		
		/**
		 * 显示提示消息
		 * @param domEle
		 * @returns {Boolean}
		 */
		showMessage: function(failSpan, message, ele) {
			if($.validate.options.showMessage) {
				$.validate.options.showMessage(message);
			} else {
				failSpan.html(message);
				failSpan.show();
				$.validate.setStyle(failSpan, "text");
				$.validate.setBorder(ele, "focus");
			}
		},
		
		/**
		 * 处理验证失败
		 * @param domEle
		 * @returns {Boolean}
		 */
		validateFail: function(failSpan, message, ele) {
			$.validate.setStyle(failSpan, "fail");
			$.validate.setBorder(ele, "fail");
			
			if($.validate.options.showMessage) {
				$.validate.options.showMessage(message);
			} else {
				failSpan.html(message);
				failSpan.show();
			}
		},
		
		/**
		 * 处理验证成功
		 * @param domEle
		 * @returns {Boolean}
		 */
		validateSucc: function(failSpan) {
			if(! $.validate.options.showMessage) {
				$.validate.setStyle(failSpan, "succ");
				failSpan.html("&nbsp;&nbsp;"); //放置空白字符以让背景可以显示出来
				failSpan.show();
			}
		},
		
		//定义验证方法，如果有一项验证失败，则停止进行其他项目的验证
		checkForm: function(container, callType) {
			if (!container){
				container = $(document);
			}
			
			var elementArray = $("[data-validator]", container);
			var elementArray2 = $("[data-required]", container);
			$.merge(elementArray, elementArray2);
			var validateSuccess = true; //是否成功通过验证
			for(var i = 0; i < elementArray.length; i++) {
				var element = $(elementArray[i]);
				var ret = $.validate.checkItem(element);
				if(! ret) {
					validateSuccess = false;
				}
				
				if(! ret && $.validate.options.stopValidateOnError) {
					return ret;
				}
			}
			
			return validateSuccess;
		},
		
		//验证一个表单项
		checkItem: function(element) {
			var validateSuccess = true; //是否成功通过验证
			var val = element.val();
			var required = $.trim(element.attr("data-required"));
			var message = $.trim(element.attr("data-message"));
			var itemName = $.trim(element.attr("data-name"));
			if (itemName == ""){
				itemName = "该输入项";
			}
			
			var failSpan = $.validate.getMessageSpan(element);
			//如果输入值为空，先判断是否为必须输入，如果是再提交验证失败，否则不进行验证
			if(val == "") {
				if(required == "true") {
					if(message == "") {
						message = itemName + "不能为空";
					}
					$.validate.validateFail(failSpan, message, element);
					return false;
				}
				return true;
			} else {
				if(required == "true") {
					$.validate.validateSucc(failSpan); //验证通过
				}
			}
			
			var validatorName = $.trim(element.attr("data-validator"));
			var validator = $.validate.validators[validatorName];
			var param = $.trim(element.attr("data-param"));
			if(!validator || validatorName == "none") { //验证器无效
				return true;
			}
			
			//对需要参数的验证器进行特殊处理，其他的则统一处理
			switch(validatorName) {
				case "sameas":
					if(param == "") { //没有传入正则表达式参数
						break;
					}
					var v1 = $("#" + param).val();
					validateSuccess = (v1 == val);
					break;
					
				case "regex":
					if(param == "") { //没有传入正则表达式参数
						break;
					}
					
					var reg = new RegExp(param);
					validateSuccess = reg.test(val);
					break;
					
				case "int-range":
					if(param == "") { //没有传入参数
						break;
					}
					
					param = param.substring(1, param.length-1);
					var param1 = param.split(",")[0];
					var param2 = param.split(",")[1];
					if(message == "") {
						message = validator.message.replace("{0}", param1);
						message = message.replace("{1}", param2);
					}
					
					//首先检查是不是全部数字
					if(! /^\d+$/.test(val)) {
						validateSuccess = false;
						break;
					}
					
					validateSuccess = parseInt(val) >= parseInt(param1) && parseInt(val) <= parseInt(param2);
					break;
					
				default:
					validateSuccess = validator.rule.test(val);
			}
			
			//如果有一项验证失败，则提示出错信息
			if(! validateSuccess) {
				if(message == "") {
					message = itemName + validator.message;
				}
				$.validate.validateFail(failSpan, message, element);
			} else {
				$.validate.validateSucc(failSpan); //验证通过
			}
			
			return validateSuccess;
		},
		
		/**
		 * 当表单项失去焦点时进行校验
		 */
		validateOnBlur: function(container) {
			if (!container){
				container = $(document);
			}
			
			var elementArray = $("[data-validator]", container);
			var elementArray2 = $("[data-required]", container);
			$.merge(elementArray, elementArray2);
			for(var i = 0; i < elementArray.length; i++) {
				var element = $(elementArray[i]);
				
				//增加foucs事件处理,用来显示提示信息
				element.focus(function(evt) { 
					var ele = $(this);
					var prompt = $.trim(ele.attr("data-prompt"));
					var val = $.trim(ele.val());
					
					//显示获得焦点样式
					$.validate.setBorder(ele, "focus");
					
					//查检有没有指定的消息显示容器
					var failSpan = $.validate.getMessageSpan(ele);
					
					//failSpan.html("");
					//failSpan.hide();
					
					//如果没有提示信息，则直接跳过
					if(prompt == "") {
						return;
					}
					
					//已经输入值则不显示默认的点击提示信息
					if(val != "") {
						return;
					}
					
					if($.validate.options.showPrompt) {
						$.validate.showMessage(failSpan, prompt, ele);
					}
				}); 
				
				if($.validate.options.validateOnBlur) {
					element.blur(function(evt) {
						$.validate.setBorder($(this), "clear"); //清除边框
						return $.validate.checkItem($(this));
					});
					
					element.change(function(evt) {
						$.validate.setBorder($(this), "clear"); //清除边框
						return $.validate.checkItem($(this));
					});
				}
			}
		},
		
		/**
		 * 初始化需要验证的表单，可以选择传入验证项所在父容器
		 */
		init: function(container, options) {
			if (!container){
				container = $(document);
			}
			$.validate.options = $.extend($.validate.options, options);
			
			$.validate.validateOnBlur(container);
			//如果容器是FORM，则加入submit事件处理函数
			if(container[0].nodeName == "FORM") {
				container.submit(function() {
					return $.validate.checkForm(container);
				});
			}
			if(container.hasClass("ajax")) {
				container.find(".ajax").click(function() {
					check_form_result= $.validate.checkForm(container);
					return check_form_result;
				});
			}
		}
	}
});
