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

  <div class="page_tit">上传文件</div>
  <form method="post" action="${ctx }/admin/pri/irrfile_upload.action"  enctype="multipart/form-data"  id="addForm">
    <input type="hidden" name="pageIndex" value="${pageIndex}" />
    <input type="hidden" name="model.id" value="${model.id }"/>
    <input type="hidden" name="type" value="${type }"/>
	<div class="form2">
    
    <c:if test="${type eq 'sqe' }">
    <dl class="lineD">
      <dt>处理单类型：</dt>
      <dd>
		<select id="cldType"  name="cldType"  placeholder="处理单类型" data-required="true" data-validator="int-range" data-param="[1,100]" data-message="请选择处理单类型" >
      		<option value="-1">请选择</option>
	      	<option value="1">偏差</option>
	      	<option value="2">筛选</option>
	      	<option value="3">返工</option>
	      	<option value="4">退货</option>
	      	<option value="5">报废</option>
	      	<option value="6">试装</option>
      	</select>
        <p>&nbsp;</p>
	  </dd>
    </dl>
    </c:if>
    <dl class="lineD">
      <dt>&nbsp;</dt>
      <dd>
        <input  id="addBtn" type="button" class="btn_b"  style="width:60px;" value="添加文件" />
	  </dd>
    </dl>
    <dl class="lineD">
      <dt>文件：</dt>
      <dd>
    <div id="files">
        <input name="uploads" id="upload" type="file"  /><br />
	</div>
        <p>&nbsp;
        <b style='color:red'>为了避免误操作,请确认单据无误后，再上传!</b></p>
	  </dd>
    </dl>
</div>
	<div class="page_btm">
			<input id="submitBtn"  type="submit" class="btn_b" value="上传" />
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
	var count = 0;
	$('#addBtn').click(function(){
		if(count > 10){
			alert('最多10个文件');
			return false;
		}
		count++;
		var upHtml = "<input name='uploads' id='upload"+count+"' type='file' /><br />";
		$('#files').append(upHtml);
		
	});
});

function returnList(){
	window.location.href = "${ctx}/admin/pri/irr_listReport.action?pageIndex=${pageIndex}&pageSize=${pageSize}";
}
		
</script> 
  
</body>
</html>