<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${siteName}</title>
		${admin_css }${jquery_js } ${util_js } ${validator_js }
</head>
<body>
<div class="so_main">

  <div class="page_tit">${model.id > 0 ?'更新':'添加' }帐号</div>
  <form method="post" action="${ctx }/admin/pri/user_add.action" id="addForm">
    <input type="hidden" name="pageIndex" value="${pageIndex}" />
    <input type="hidden" name="model.id" value="${model.id }"/>
	<div class="form2">
    
    <dl class="lineD">
      <dt>帐号名：</dt>
      <dd>
        <input name="model.username" id="username" type="text" value="${model.username }" placeholder="帐号登录账号" data-required="true" data-validator="regex" data-param="^[0-9a-zA-Z_]{4,18}$" data-message="登录账号必须是4到18位的字母或数字或下划线"  maxlength="20"/>
        <p>帐号进行登录的帐号</p>
	  </dd>
    </dl>

    <dl class="lineD">
      <dt>密码：</dt>
      <dd>
        <input name="model.password" id="password"  type="password" value=""  placeholder="登录密码"   data-validator="regex" data-param="^[0-9a-zA-Z_]{6,18}$" data-message="密码必须是6到18位的字母或数字或下划线" maxlength="20" />
        <p>帐号进行登录的密码</p>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>姓名：</dt>
      <dd>
        <input name="model.nickname" id="nickname"  type="text" value="${model.nickname }"  data-required="true"  maxlength="20"/>
        <p>2-11位个中英文、数字、下划线和中划线组成</p>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>联系方式：</dt>
      <dd>
        <input name="model.mobile" id="mobile"  type="text" value="${model.mobile }"  data-required="true"  maxlength="20"/>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>个人邮箱：</dt>
      <dd>
        <input name="model.email" id="email"  type="text" value="${model.email }" id="normalemail" data-required="true"  data-message="请输入正确格式的邮箱" maxlength="100"/>
         <p>英文、数字、下划线组成，请填写争取的格式，如果一个部门有多个人同时需要处理业务，请添加多个邮箱，并用 分号隔开！</p>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>角色：</dt>
      <dd>
      	<select id="role"  name="roleId"  placeholder="帐号角色" data-required="true" data-validator="int-range" data-param="[1,100]" data-message="请选择帐号角色" >
      		<option value="-1">请选择</option>
	        <c:forEach var="role" items="${roleList }">
	      		<option value="${role.id }">${role.name }</option>
    	    </c:forEach>
      	</select>
      </dd>
    </dl>
    
</div>
	<div class="page_btm">
			<input id="submitBtn"  type="submit" class="btn_b" value="保存" />
		<input id="back" onclick="returnList();" type="button" class="btn_b" value="返回" />
	</div>
	<div style="clear:both" />
</form>
</div>


<script type="text/javascript">
$(function(){
	if('0' == '${resultMap.retcode}'){
		returnList();
	}else if('101' == '${resultMap.retcode}'){
		alert('${resultMap.retmsg}');
	}
	
	
	$.validate.init($("form")); //初始化表单验证工具
	//$("#rule").calcWordNum({maxNumber:500,targetid:"remain"});
	var idd="${model.id}";
	if(idd>0){
		$('#role').val('${model.role.id }');
		$("form").attr("action","${ctx}/admin/pri/user_update.action");
	}
});

function returnList(){
	window.location.href = "${ctx}/admin/pri/user_list.action?pageIndex=${pageIndex}&pageSize=${pageSize}";
}
		
</script> 
  
</body>
</html>