<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${siteName}</title>
		${jquery_js }
		<style type="text/css">
<!--
* {
	margin: 0;
	padding: 0
}

body {
	font-family: arial;
	font-size: 12px;
	background: #EFF3F6;
	margin: 0px;
}

li {
	list-style-type: none;
}

ul,form,input {
	font-size: 12px;
	padding: 0;
	margin: 0;
}

a:link {
	color: #084F63;
}

a:visited {
	color: #084F63;
}

a:hover {
	color: #cc3300;
}

a img {
	border: none;
}

img {
	border: 0px;
}

.fl {
	float: left;
}

.wrap_login {
	width: 532px;
	height: 380px;
	margin: 0 auto;
	margin-top: 150px;
	background: url(${ctx}/resources/images/login_box_bg2.png) no-repeat top;
	position: relative
}

#box_logo {
	position: absolute;
	top: 0px;
	padding: 15px 0 0 120px;
	font-size: 32px;
	font-weight: bold;
	color: #719BB2;
}

#left_logo {
	position: absolute;
	height: 90px;
	top: 100px;
	padding: 0px 0 0 215px;
	background: url(${ctx}/resources/images/logo.png) no-repeat top;
}

.wrap_login .lb {
	width: 176px;
	height: 215px;
	background: url(${ctx}/resources/images/gm_l_f.gif) no-repeat center center;
}

.wrap_login .box_login {
	width: 257px;
	color: #fff;
	position: absolute;
	right: 40px;
	top: 80px;
	padding: 20px 0 0 30px;
	font-size: 14px
}

.wrap_login .box_login dd {
	padding: 0 0 15px
}

.wrap_login .box_login dd label {
	width: 60px;
	display: block;
	float: left;
	padding: 5px 0
}

.wrap_login .box_login .c1 {
	margin-left: 60px
}

.wrap_login .box_login .txt {
	padding: 5px;
	background-color: #C0E3F1;
	border: #C0E3F1 solid 1px;
	vertical-align: middle
}

.wrap_login .box_login .txt2 {
	padding: 5px;
	background-color: #fff;
	border: #fff solid 1px;
	vertical-align: middle
}

.wrap_login .lc ul {
	padding-left: 20px;
}

.wrap_login .lc li {
	float: left;
	width: 237px;
	line-height: 22px;
}

.wrap_login .lx {
	margin-left: 24px;
}

.ldinput {
	border: 1px solid #c3c6cb;
	padding: 2px;
}

.lf {
	margin-bottom: 13px;
}

.footer_login {
	height: 39px;
	line-height: 22px;
	color: #084F63;
	text-align: center;
	padding-top: 15px;
	position: absolute;
	bottom: 10px;
	width: 532px
}
-->
</style>
	</head>
	<body>

		<div class="wrap_login">
				<div id="box_logo" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录系统</div>
				<div id="left_logo" ></div>
			<div class="la fl">
				<div class="box_login">
						<dl>
							<dd>
								<label>
									帐 号：
								</label>
								<div class="c1">
									<input class="txt" onfocus="this.className='txt2'" id="username"
										onblur="this.className='txt'" type="text" name="adminUser.username"
										value="${adminUser.username }" style="width: 180px">
								</div>
							</dd>

							<dd>
								<label>
									密 码：
								</label>
								<div class="c1">
									<input class="txt" onfocus="this.className='txt2'" id="password"
										onblur="this.className='txt'" type="password" name="adminUser.password"
										value="${adminUser.password }" style="width: 180px">
								</div>
							</dd>
							<dd>
								<label>
									验证码：
								</label>
								<div class="c1">
									<input class="txt" onfocus="this.className='txt2'" id="checkCode" maxlength="4"
										onblur="this.className='txt'" name="checkCode" value="${checkCode }"
										style="width: 58px">
									<img id="img_checkCode" style="cursor:pointer" align="top"  border="1"/>
								</div>
							</dd>
							<dd>
								<div ><font id="error_tip" style="display: none;" color="red"></font></div>
							</dd>
							<dd>
								<label>
									&nbsp;
								</label>
								<input type="image" id="login_btn"
									src="${ctx}/resources/images/btn_login.png"
									style="height: 32px; width: 102px;" />
							</dd>
						</dl>
				</div>
			</div>
			<div class="footer_login">
				Copyright &copy; 2013
				<a href="#" >安徽爱德夏汽车零部件有限公司</a> 版权所有
			</div>
		</div>
	${cookie_js }
	${util_js }
	<script type="text/javascript">
    // 登录页面校验
    function validateLogin() {
        var username = $("#username").val();
        var pwd = $("#password").val();

        if (isEmpty(username)) {
            $("#error_tip").html("请输入帐号。");
            $("#error_tip").show();
            return false;
        }

        if (isEmpty(pwd)) {
            $("#error_tip").html("请输入密码。");
            $("#error_tip").show();
            return false;
        }

        var result = /^.{6,20}$/.test(pwd);
        if (result == false) {
            $("#error_tip").html("请输入6到20位长度密码。");
            $("#error_tip").show();
            return false;
        }
        
        var checkCode = $("#checkCode").val();

        if (isEmpty(checkCode)) {
            $("#error_tip").html("请输入验证码。");
            $("#error_tip").show();
            return false;
        }

        return true;
    }

    function doLogin() {
        $("#error_tip").hide();
        var tmp_bool = validateLogin();
        if (tmp_bool != true) {
            return false;
        }

        $.post(
                "${ctx}/admin/base/login.action",
                {
                    "model.username":$("#username").val(),
                    "model.password":$("#password").val(),
                    "checkCode":$("#checkCode").val()
                },
                function (data, textStatus) {
                    if (data.retcode === '0') {
                        $("#error_tip").html(data.retmsg);
                        $("#error_tip").show();
                       	$.cookie('cookie_username', $("#username").val(), { expires: 365 });
                        window.location.href = "${ctx}/pages/admin/admin.jsp";
                    } else {
                        $("#error_tip").html(data.retmsg);
                        $("#error_tip").show();
                        $("#img_checkCode").attr("src", "${ctx}/admin/base/checkCode.action?time="+Math.random());
                    }
                },
                "json"
    	);
    }
        
    $(document).ready(function(){
    	
    	$("#username").val($.cookie("cookie_username"));
    	
    	var ran = Math.random();
    	var queryStr = "${ctx}/admin/base/checkCode.action?a=" + ran; 
    	$("#img_checkCode").attr("src", queryStr);
    	$("#img_checkCode").attr("border", 1);
    	
    	//表单提交，并作循环验证
        $("#login_btn").click(
        	function(){
         		doLogin();	
        	}
        );
    	
  		$("#img_checkCode").click(function() {
  			var time = new Date().getTime();
  			$(this).attr("src", "${ctx}/admin/base/checkCode.action?time=" + time);
  		});
        
      	$(function(){
      		document.onkeydown = function(e){
       		var ev = document.all ? window.event : e; 
       		if(ev.keyCode==13) {
       			doLogin();
       		}
      		}
      	}); 	
    });
</script>
	</body>
</html>