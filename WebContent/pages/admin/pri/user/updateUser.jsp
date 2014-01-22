<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${siteName}</title>
		${admin_css }${jquery_js } ${util_js } ${validator_js } ${jquery_form_js}
</head>
<body>
<div class="so_main">

  <div class="page_tit">帐号设置</div>
  <c:set var="model" value="${SESSION_ADMIN_USER}"></c:set>
  <form method="post" action="#" id="addForm" >
    <input type="hidden" name="model.id" value="${model.id }"/>
	<div class="form2">
    
    <dl class="lineD">
      <dt>帐号名：</dt>
      <dd>
        <input name="model.username" id="username" type="text" value="${model.username }" readonly="readonly" maxlength="20"/>
        <p>帐号进行登录的帐号</p>
	  </dd>
    </dl>

    <dl class="lineD">
      <dt><font color="red">* </font>原密码：</dt>
      <dd>
        <input name="model.password" id="password" type="password"  value="" placeholder="原密码"   data-validator="regex" data-param="^[0-9a-zA-Z_]{6,18}$" data-message="密码必须是6到18位的字母或数字或下划线" maxlength="20"  />
        <p>请输入6到20位长度原始密码。<c:if test="${adminUser.id > 0}">留空则不修改密码。</c:if></p>
       </dd>
    </dl>
	<dl class="lineD">
      <dt><font color="red">* </font>新密码：</dt>
      <dd>
        <input name="newPwd" id="newPwd" type="password"  value="" placeholder="新密码"   data-validator="regex" data-param="^[0-9a-zA-Z_]{6,18}$" data-message="密码必须是6到18位的字母或数字或下划线" maxlength="20"  />
        <p>请输入6到20位长度新密码。</p>
       </dd>
    </dl>
	<dl class="lineD">
      <dt><font color="red">* </font>确认密码：</dt>
      <dd>
        <input  id="reptPwd" type="password"  value="" placeholder="确认密码"   data-validator="regex" data-param="^[0-9a-zA-Z_]{6,18}$" data-message="密码必须是6到18位的字母或数字或下划线" maxlength="20"   />
        <p>请输入6到20位长度确认密码。</p>
       </dd>
    </dl>
    
    <dl class="lineD">
      <dt>姓名：</dt>
      <dd>
        <input name="model.nickname" id="nickname"  type="text" value="${model.nickname }"  data-required="true"  maxlength="20"/>
        <p>2-11位个中英文、数字、下划线和中划线组成</p>
      </dd>
    </dl>
    
    <fieldset>
	  <legend>其他信息</legend>
    <dl class="lineD">
      <dt>联系方式：</dt>
      <dd>
        <input name="model.mobile" id="mobile"  type="text" value="${model.mobile }"  data-required="true"  maxlength="20"/>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>个人邮箱：</dt>
      <dd>
        <input name="model.email" id="email"  type="text" value="${model.email }"  data-required="true"  data-validator="email" data-message="请输入正确格式的邮箱" maxlength="100"/>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>角色：</dt>
      <dd>
      	${model.role.name }
      </dd>
    </dl>
	</fieldset>        
    <div class="page_btm">
      <input id="back" onclick="returnList();" type="button" class="btn_b" value="返回" />
      <input id="submitBtn" onclick="save();" type="button" class="btn_b" value="保存" />
    </div>
  </div>
  </form>
</div>

<script type="text/javascript">
$.validate.init($("form")); //初始化表单验证工具
		function validate()
		{    
		    
     	    var password=$.trim($("#password").val());
     	    
		    if(password.length > 0){
		     	var result = /^.{6,20}$/.test(password);
		        if (result == false) {
		        	alert("请输入6到20位长度原始密码。");
		            return false;
		        }
     	    	var newPwd=$.trim($("#newPwd").val());
     	    	result = /^.{6,20}$/.test(newPwd);
     	    	if (result == false) {
		        	alert("请输入6到20位长度新密码。");
		            return false;
		        }
     	    	
     	    	
     	    	var reptPwd=$.trim($("#reptPwd").val());
     	    	if(newPwd != reptPwd){
     	    		alert("新密码和确认密码不一致。");
		            return false;
     	    	}
	        }
		    
		     var namea=$.trim($("#nickname").val());
		     if(namea.length == 0){
		     	alert("请输入姓名。");
		     	return false;
		     }
		     var namea=$.trim($("#email").val());
		     if(namea.length == 0){
		     	alert("请输入邮箱。");
		     	return false;
		     }
		    
		    
		     return true;
		}

		function save(){
			if(validate()){
				submit();
				//document.getElementById("submitBtn").disabled = "disabled";
			}
		}

		function submit(){
			var url = '';
			url= '${ctx}/admin/pri/user_updateUser.action';
			//alert(url);
			var options = { 
			        url:	   url,
			        success:   callback, 
			        type:      'post',      
			        dataType:  'json'
			    };
			$('#addForm').ajaxSubmit(options);
		}
		//回调函数
		function callback(data){
			alert(data.retmsg);
			if(data.retcode == "0"){				
				returnList();
			}
			//document.getElementById("submitBtn").disabled = "enabled";
		}
		
		function returnList(){
			history.back(-1);
		}

</script>
</body>

</html>