﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

  <div class="page_tit">导入供应商</div>
  <form method="post" action="${ctx }/admin/pri/supplier_importData.action"  enctype="multipart/form-data"  id="addForm">
    <input type="hidden" name="pageIndex" value="${pageIndex}" />
    <input type="hidden" name="model.id" value="${model.id }"/>
	<div class="form2">
    
    <dl class="lineD">
      <dt>供应商文件：</dt>
      <dd>
        <input name="upload" id="upload" type="file" data-required="true"  data-message="请选择文件" />
        <p><a href="${ctx }/resources/file/gys.xls">查看导入数据模版，导入的EXCEL必须和此模版格式一致</a></p>
	  </dd>
    </dl>

</div>
	<div class="page_btm">
			<input id="submitBtn"  type="submit" class="btn_b" value="导入" />
		<input id="back" onclick="returnList();" type="button" class="btn_b" value="返回" />
	</div>
	<div style="clear:both" />
</form>
</div>


<script type="text/javascript">
$(function(){
	if('' != '${retMsg}'){
		alert('${retMsg}');
		returnList();
	}
	
	$.validate.init($("form")); //初始化表单验证工具
});

function returnList(){
	window.location.href = "${ctx}/admin/pri/supplier_list.action?pageIndex=${pageIndex}&pageSize=${pageSize}";
}
		
</script> 
  
</body>
</html>