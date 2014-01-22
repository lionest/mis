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

  <div class="page_tit">${model.id > 0 ?'更新':'添加' }零件</div>
  <form method="post" action="${ctx }/admin/pri/part_add.action" id="addForm">
    <input type="hidden" name="pageIndex" value="${pageIndex}" />
    <input type="hidden" name="model.id" value="${model.id }"/>
	<div class="form2">
    
    <dl class="lineD">
      <dt>零件名：</dt>
      <dd>
        <input name="model.name" id="name" type="text" value="${model.name }" placeholder="零件名称" data-required="true"  maxlength="50"/>
        <p>零件名称</p>
	  </dd>
    </dl>

    <dl class="lineD">
      <dt>零件代码：</dt>
      <dd>
        <input name="model.num" id="code"  type="text" value="${model.num }"  placeholder="零件代码"   data-message="添加后不得更改" maxlength="100" />
        <p>零件代码</p>
      </dd>
    </dl>
      <dl class="lineD">
      <dt>供应商：</dt>
      <dd>
        <select id="role"  name="supId"  placeholder="供应商" data-required="true" data-validator="int-range" data-param="[1,1000]" data-message="请选择供应商" >
      		<option value="-1">请选择</option>
	        <c:forEach var="sup" items="${spList }">
	      		<option value="${sup.id }" ${model.supplier.id==sup.id?"selected":'' }>${sup.name }</option>
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
		$("form").attr("action","${ctx}/admin/pri/part_update.action");
	}
});

function returnList(){
	window.location.href = "${ctx}/admin/pri/part_list.action?pageIndex=${pageIndex}&pageSize=${pageSize}";
}
		
</script> 
  
</body>
</html>