<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${siteName}</title>
		${admin_css }${jquery_js } ${util_js }
</head>
<body>
<div class="so_main">
 <div class="page_tit">系统配置</div>
  <div class="form2">
  	<c:forEach items="${list}" var="config" varStatus="status">
    <dl class="lineD">
      <dt>${config.dataName }:</dt>
      <dd>
        <input id="${config.id }_val" type="text" value="${config.dataValue }" maxlength="100" size="50">
		<input type="button" id="${config.id }" href="javascript:void(0);" class="btn_b" style="width:60px;" value="更新" onclick="return update(this);">
        <p>${config.dataDesc }</p>
        <input id="${config.id }_code" type="hidden" value="${config.dataCode }"></input>
       </dd>
    </dl>
    </c:forEach>
	<br/>
	<br/>
	<br/>
  </div>
</div>

<script>
			function update(obj){
				if(confirm("确定要更新吗?")){
					var url = '${ctx}/admin/pri/config_update.action';				
					var options = { 
					        url:	   url,
					        success:   callback, 
					        type:      'post',      
					        dataType:  'json',
					        data:{
					        	"globalConfig.dataCode":$("#"+$(obj).attr('id')+"_code").val(),
					        	"globalConfig.dataValue":$("#"+$(obj).attr('id')+"_val").val()
					        }
					    };
					$.ajax(options);
				}
			}
			
			//回调函数
			function callback(data){
				alert(data.retmsg);
				if(data.retcode == "0"){				
					self.location.reload();
				}
			}
			
</script>
</body>
</html>