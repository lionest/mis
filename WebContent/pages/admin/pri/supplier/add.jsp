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

  <div class="page_tit">${model.id > 0 ?'更新':'添加' }供应商</div>
  <form method="post" action="${ctx }/admin/pri/supplier_add.action" id="addForm">
    <input type="hidden" name="pageIndex" value="${pageIndex}" />
    <input type="hidden" name="model.id" value="${model.id }"/>
	<div class="form2">
    
    <dl class="lineD">
      <dt>供应商名称：</dt>
      <dd>
        <input name="model.name" id="suppliername" type="text" value="${model.name }" placeholder="供应商名称" data-required="true"  data-message="请输入供应商名称"  maxlength="100"/>
	  </dd>
    </dl>

    <dl class="lineD">
      <dt>供应商代码：</dt>
      <dd>
        <input name="model.code" id="code"  type="text" value="${model.code }"  placeholder="供应商代码"   data-required="true"  data-message="请输入供应商代码" maxlength="50" />
      </dd>
    </dl>
    <dl class="lineD">
      <dt>供应商类别：</dt>
      <dd>
        <select id="type" name="model.type">
        	<option value="国产件">国产件</option>
        	<option value="型钢件">型钢件</option>
        	<option value="进口件">进口件</option>
        </select>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>Email：</dt>
      <dd>
        <textarea  id="email" name="model.email" cols="40" rows="6" tabindex="11"  >${model.email }</textarea>
		<p>多个邮箱用英文分号;分隔最多200个字,还可以输入<span id="remain">200</span>个字</p>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>SQE：</dt>
      <dd>
        <input name="model.sqe" id="sqe"  type="text" value="${model.sqe }"  data-required="true" data-message="请输入SQE"  maxlength="20"/>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>SQE邮箱：</dt>
      <dd>
        <input name="model.sqeEmail" id="sqeemail"  type="text" value="${model.sqeEmail }"  data-required="true"   data-message="请输入正确格式的邮箱" maxlength="100"/>
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
	
	$("#email").calcWordNum({maxNumber:200,targetid:"remain"});
	$.validate.init($("form")); //初始化表单验证工具
	//$("#rule").calcWordNum({maxNumber:500,targetid:"remain"});
	var idd="${model.id}";
	if(idd>0){
		$('#type').val('${model.type}');
		$("form").attr("action","${ctx}/admin/pri/supplier_update.action");
	}
});

function returnList(){
	window.location.href = "${ctx}/admin/pri/supplier_list.action?pageIndex=${pageIndex}&pageSize=${pageSize}";
}
		
</script> 
  
</body>
</html>