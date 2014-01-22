<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<c:set var="ctx1" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${siteName}</title>
		${admin_css }${jquery_js } ${util_js } ${validator_js }  ${jquery_form_js} ${WdatePicker_js }${blockui_js }	
		
<script type='text/javascript' src='${ctx1 }/resources/js/jquery.bgiframe.min.js'></script>
<script type='text/javascript' src='${ctx1 }/resources/js/jquery.ajaxQueue.js'></script>
<script type='text/javascript' src='${ctx1 }/resources/js/thickbox-compressed.js'></script>
<script type='text/javascript' src='${ctx1 }/resources/js/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="${ctx1 }/resources/css/main.css" />
<link rel="stylesheet" type="text/css" href="${ctx1 }/resources/css/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="${ctx1 }/resources/css/thickbox.css" />

<script type="text/javascript">
$(document).ready(function(){
	var names=${jsonData};
	
	$("#lingjian").autocomplete(names, {
		multiple: false,
		dataType: "json",
		parse: function(data) {
			return $.map(data, function(row) {
				alert(row);
				return {
					data: row,
					value: row.name,
					result: row.name
				}
			});
		},
		formatItem: function(item) {
			return item.name;
		}
	}).result(function(e, item) {
		$("#linJianMingCheng").val(item.name);
		$("select[name='spId'] option[value='"+item.spId+"']").attr("selected","selected");
		$("#linJianHao").val(item.partNum);
	});
	
});	
</script>

</head>
<body>
  <form method="post" action="${ctx }/admin/pri/irr_add.action" id="addForm"  enctype="multipart/form-data"  class="ajax">
<div class="so_main">
  <div class="page_tit">拒收报告</div>
    <input type="hidden" name="pageIndex" value="${pageIndex}" />
    <input type="hidden" name="model.id" value="${model.id }"/>
    
	<div class="form2" >
    
    <dl class="lineD">
      <dt>IRR编号：</dt>
      <dd>
      	<c:if test="${empty model.code }">系统将自动生成</c:if>
      	<c:if test="${!empty model.code }">${model.code }</c:if>
	  </dd>
    </dl>
	<c:set var="showInput" value="${'QA' == session.SESSION_ADMIN_USER.role.code && model.state <= 2 }"></c:set>
    <dl class="lineD">
      <dt>零件名称：</dt>
      <dd>
      	<c:if test="${showInput }">
      	<input type="hidden" id="partnum" />
      <!--  	<input id="lingjian" name="lingjian"  placeholder="零件名称"   data-required="true"  data-message="请选择零件名称" value="${model.linJianMingCheng}"/>
      	<input type="hidden" name="model.linJianMingCheng" id="linJianMingCheng" value="${model.linJianMingCheng}"/>-->
      	
      	
      	 <select name="model.linJianMingCheng" id="linJianMingCheng" placeholder="零件名称"   data-required="true"  data-message="请选择零件名称">
      		<option value="">请选择</option>
      		<c:forEach items="${parts}" var="part">
      			<option value="${part.name}" id="${part.num }" title="${part.supplier.id}" ${model.linJianHao==part.num?'selected':'' }>${part.name }</option>
      		</c:forEach>
      	</select>
        </c:if>
        <c:if test="${!showInput }">
	        &nbsp;${model.linJianMingCheng }
        </c:if>
      </dd>
    </dl>
    <dl class="lineD">
      <dt>零件号：</dt>
      <dd>
      	<c:if test="${showInput }">
        <input name="model.linJianHao" id="linJianHao"  type="text" value="${model.linJianHao }"  placeholder="零件号"   data-required="true"  data-message="请输入零件号" maxlength="50" />
        </c:if>
        <c:if test="${!showInput }">
	        &nbsp;${model.linJianHao }
        </c:if>
      </dd>
    </dl>
    <dl class="lineD">
      <dt>供应商：</dt>
      <dd>
      	<c:if test="${showInput }">
      		<select id="spId"  name="spId"  >
	      		<option value="-1">请选择</option>
		        <c:forEach var="sp" items="${spList}">
		      		<option value="${sp.id }">${sp.name }</option>
	    	    </c:forEach>
	      	</select>
        </c:if>
        <c:if test="${!showInput }">
	        &nbsp;${model.sp.name }
        </c:if>
      </dd>
    </dl>
    <dl class="lineD">
      <dt>批次号：</dt>
      <dd>
      	<c:if test="${showInput }">
        <input name="model.piCiHao" id="piCiHao"  type="text" value="${model.piCiHao }"  placeholder="批次号"   data-required="true"  data-message="请输入批次号" maxlength="50" />
        </c:if>
        <c:if test="${!showInput }">
	        &nbsp;${model.piCiHao }
        </c:if>
      </dd>
    </dl>
    <dl class="lineD">
      <dt>数量：</dt>
      <dd>
      	<c:if test="${showInput }">
        <input name="model.shuLiang" id="shuLiang"  type="text" value="${model.shuLiang }"  placeholder="数量"   data-validator="digit"  data-required="true"  data-message="请输入数量" maxlength="9" />
        </c:if>
        <c:if test="${!showInput }">
	        &nbsp;${model.shuLiang }
        </c:if>
      </dd>
    </dl>
    <dl class="lineD">
      <dt>检验日期：</dt>
      <dd>
      	<c:if test="${showInput }">
        <input name="model.createTime" id="createTime"  value="<fmt:formatDate value='${model.createTime}' pattern='yyyy-MM-dd'/>" class="Wdate" onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
        </c:if>
        <c:if test="${!showInput }">
	        <fmt:formatDate value='${model.createTime}' pattern='yyyy-MM-dd'/>
        </c:if>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>审核QE：</dt>
      <dd>
      	<c:if test="${showInput }">
      		<select id="qeId"  name="qeId"  placeholder="审核QE" data-required="true" data-validator="int-range" data-param="[1,100]" data-message="请选择审核QE" >
	      		<option value="-1">请选择</option>
		        <c:forEach var="qe" items="${qeList }">
		      		<option value="${qe.id }">${qe.nickname }</option>
	    	    </c:forEach>
	      	</select>
        </c:if>
        <c:if test="${!showInput }">
	        &nbsp;${model.qe.nickname }
        </c:if>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>不合格类别：</dt>
      <dd>
      	<c:if test="${showInput }">
      	<input type="checkbox" style="width:15px;" name="model.category" value="1" ${fn:contains(model.category,'1')?'checked=checked':''}/> 外观
      	<input type="checkbox" style="width:15px;" name="model.category" value="2" ${fn:contains(model.category,'2')?'checked=checked':''}/> 尺寸
      	<input type="checkbox" style="width:15px;" name="model.category" value="3" ${fn:contains(model.category,'3')?'checked=checked':''}/> 材料
      	<input type="checkbox" style="width:15px;" name="model.category" value="4" ${fn:contains(model.category,'4')?'checked=checked':''}/> 性能
      	  	<input type="checkbox" style="width:15px;" name="model.category" value="5" ${fn:contains(model.category,'5')?'checked=checked':''}/>其他
        </c:if>
        <c:if test="${!showInput }">
	       <span width="200" >&nbsp;
		      			<c:if test="${fn:contains(model.category,'1')}">外观</c:if>	 
						<c:if test="${fn:contains(model.category,'2')}">尺寸</c:if>
						<c:if test="${fn:contains(model.category,'3') }">材料</c:if>
						<c:if test="${fn:contains(model.category,'4')}">性能</c:if>
						<c:if test="${fn:contains(model.category,'5')}">其他</c:if>	        
	        </span>
        </c:if>
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>不符合描述：</dt>
      <dd>
      <c:if test="${showInput }">
        <textarea  id="miaoShu" name="model.miaoShu" cols="50" rows="8"  data-required="true"  data-message="请输入不符合描述" >${model.miaoShu }</textarea>
		<p>最多500个字,还可以输入<span id="remain">500</span>个字</p>
		</c:if>
        <c:if test="${!showInput }">
	        <span width="200" >${wd:convertToHtml(model.miaoShu) }</span>
        </c:if>
      </dd>
    </dl>
    <dl class="lineD">
      <dt>图片：</dt>
      <dd>
      	<c:if test="${showInput }">
        	<input name="upload" id="upload" type="file"  />
		</c:if>
	        <span width="200" >&nbsp;
	        	<c:if test="${not empty model.img }">
	        		<br/><img src="${model.imgUrl }" width="300" height="300"></img>
	        	</c:if>
	        </span>
      </dd>
    </dl>
    
    <c:if test="${model.state >=2 }">

    <dl class="lineD">
      <dt>审核意见：</dt>
      <dd>
      <c:set var="showYiJian" value="${'QE' eq SESSION_ADMIN_USER.role.code && model.state == 2 }"/>
      <c:if test="${showYiJian }">
        <textarea  id="shenHeYiJian" name="model.shenHeYiJian" cols="50" rows="8"  data-required="true"  data-message="请输入审核意见" >${model.shenHeYiJian }</textarea>
		<p>最多500个字,还可以输入<span id="remain2">500</span>个字</p>
		</c:if>
        <c:if test="${!showYiJian }">
	        <span width="200" >&nbsp;${model.shenHeYiJian }</span>
        </c:if>
      </dd>
    </dl>
    </c:if>
    
    <c:if test="${model.mbrFile ==1 }">
    <dl class="lineD">
      <dt>评审单：</dt>
      <dd>
      	&nbsp;<div id="mbrFile"></div>
      </dd>
    </dl>
    </c:if>
    <c:if test="${model.sqeFile ==1 }">
    <dl class="lineD">
      <dt>处理单类型：</dt>
      <dd>
	      	<c:if test="${1 eq model.cldType }">偏差</c:if>
	      	<c:if test="${2 eq model.cldType }">筛选</c:if>
	      	<c:if test="${3 eq model.cldType }">返工</c:if>
	      	<c:if test="${4 eq model.cldType }">退货</c:if>
	      	<c:if test="${5 eq model.cldType }">报废</c:if>
	      	<c:if test="${6 eq model.cldType }">试装</c:if>
        <p>&nbsp;</p>
	  </dd>
    </dl>
    <dl class="lineD">
      <dt>处理单：</dt>
      <dd>
      	&nbsp;<div id="sqeFile"></div>
      </dd>
    </dl>
    </c:if>
    
    <c:if test="${model.state >1 }">
    <dl class="lineD">
      <dt>检验员：</dt>
      <dd>
	        &nbsp;${model.qa.nickname }
      </dd>
    </dl>
    <dl class="lineD">
      <dt>SQE：</dt>
      <dd>
	        &nbsp;${model.sp.sqe }
      </dd>
    </dl>
    <dl class="lineD">
      <dt>修改时间：</dt>
      <dd>
	        <fmt:formatDate value='${model.modifyTime}' pattern='yyyy-MM-dd HH:mm'/>
      </dd>
    </dl>
    </c:if>
    <c:if test="${model.id > 0 }">
     <dl class="lineD">
      <dt>状态：</dt>
      <dd>
	        <span width="200" >&nbsp;
						<c:if test="${model.state == 1 }">暂存</c:if>
						<c:if test="${model.state == 2 }">待审核</c:if>
						<c:if test="${model.state == 3 }">已审批</c:if>
						<c:if test="${model.state == 4 }">已撤销</c:if>	 
							<c:if test="${model.state == 5 }">已取消</c:if>	        
	        </span>
      </dd>
    </dl>
     <dl class="lineD">
      <dt>已关闭：</dt>
      <dd>
	        <span width="200" >&nbsp;
						<c:if test="${model.closed == 1 }">是</c:if>
						<c:if test="${model.closed == 0 }">否</c:if>
	        </span>
      </dd>
    </dl>
     <dl class="lineD">
      <dt>版本号：</dt>
      <dd>
	        <span width="200" >${model.version }</span>
      </dd>
    </dl>
      <c:if test="${model.delay > 0 }">
    <dl class="lineD">
      <dt>延迟：</dt>
      <dd>
	        <span width="200" >${model.delay} 小时</span>  申请延迟时间:  <fmt:formatDate value='${model.delaytime}' pattern='yyyy-MM-dd'/>
      </dd>
    </dl>
     <dl class="lineD">
      <dt>延迟原因：</dt>
      <dd>
	        <span width="200" >${model.delayreason}</span>  
      </dd>
    </dl>
    </c:if>
    </c:if>
    
</div>

</div>
	<div class="page_btm"  style="clear:both">
		<c:if test="${'QA' eq SESSION_ADMIN_USER.role.code }">
			<c:if test="${model.state <= 2 }">
				<input id="saveBtn"  type="button" class="btn_b ajax" value="暂存" />
				<input id="submitBtn"  type="button" class="btn_b  ajax" value="提交审核" />
			</c:if>
		</c:if>
		<c:if test="${'QE' eq SESSION_ADMIN_USER.role.code ||'QEZG' eq SESSION_ADMIN_USER.role.code}">
			<c:if test="${model.state == 2&&model.closed==0 }">
				<input id="agreeBtn"  type="button" class="btn_b ajax" value="同意发布" />
				<c:if test="${model.delay <=0 ||model.delay eq null}">
					<input  type="button" class="btn_b"  id="delay"  value="申请延时" />
					</c:if>
			</c:if>
		</c:if>
		<c:if test="${'QEZG' eq SESSION_ADMIN_USER.role.code||'QEJL' eq SESSION_ADMIN_USER.role.code  }">
				<input id="rejectBtn"  type="button" class="btn_b ajax" value="撤销" />
				</c:if>
				<c:if test="${'QEJL' eq SESSION_ADMIN_USER.role.code&&model.sqeFile>0&&model.closed==0}">
									<br />
										<input  type="button" class="btn_b close"  id="close"  value="关闭" />
								</c:if>
				<c:if test="${model.id >0}">
							<input id="${model.id }"   type="button" class="btn_b time" value="时间轴" />
							</c:if>
							
		<input id="back" onclick="returnList();" type="button" class="btn_b" value="返回" />

	</div>
	<div style="clear:both" />

<!-- 延迟申请 -->
<div id="delaydiv" style="display:none">
<span>延迟时间:</span><input type="text" name="delay" id="delayhour"  value="0"/> 小时
<br/>
<span>延迟原因:</span><input type="text" name="delayreason"  id="delayreason"  value="" /> 
<br/>
<input type="button"  id="confirmDelay"  value="确认"/>
<input type="button"  id="cancelDelay"  value="取消"/>
</div>
</form>

<script type="text/javascript">
$(function(){
	if('0' == '${resultMap.retcode}'){
		returnList();
	}else if('101' == '${resultMap.retcode}'){
		alert('${resultMap.retmsg}');
	}
	//$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	$("#miaoShu").calcWordNum({maxNumber:500,targetid:"remain"});
	$("#shenHeYiJian").calcWordNum({maxNumber:500,targetid:"remain2"});
	$.validate.init($("form")); //初始化表单验证工具
	//$("#rule").calcWordNum({maxNumber:500,targetid:"remain"});
	var idd="${model.id}";
	if(idd>0){
		$('#qeId').val('${model.qe.id}');
		$('#spId').val('${model.sp.id }');
		$('#sqe').val('${model.sp.sqe}');
		$('#category').val('${model.category}');
		$('#sqeEmail').val('${model.sp.sqeEmail}');
	}

	
	$('#saveBtn').click(function (){
		if(!check_form_result){return false;}
		var url = '';
		url= '${ctx}/admin/pri/irr_add.action';
		var idd="${model.id}";
		if(idd>0){
			url = "${ctx}/admin/pri/irr_update.action";
		}
		var options = { 
				beforeSend :function() { 
                    $.blockUI(); 
                } ,
				complete : function() { 
                    $.unblockUI(); 
                } ,
		        url:	   url,
		        success:   callback, 
		        type:      'post',      
		        dataType:  'json'
		    };
		
		$('#addForm').ajaxSubmit(options);
	});
	//显示延迟时间
	$('#delay').click(function (){
		$("#delaydiv").show();
		
	});
	
	$('#cancelDelay').click(function (){
		$("#delaydiv").hide();
	});
	//延迟
	$('#confirmDelay').click(function (){
		var url = '';
		var idd="${model.id}";
		url = "${ctx}/admin/pri/irr_delay.action";
		var delay=$("#delayhour").val();
		var reason=$("#delayreason").val();
		$.ajax({
			
			beforeSend :function() { 
                $.blockUI(); 
            } ,
			complete : function() { 
                $.unblockUI(); 
            } ,
				 url:	   url,
		        data:"model.id="+idd+"&model.delay="+delay+"&model.delayreason="+reason,
		        type:      'post',      
		        success:   callback, 
		        dataType:  'json'
		});
	});
	$('input.time').click(function (){
		var id = $(this).attr('id');
		window.location.href = "${ctx}/admin/pri/irr_time.action?model.id="
			+ id;
		return false;
	});
	
	$('#submitBtn').click(function (){
		if(!check_form_result){return false;}
		var url = '';
		url= '${ctx}/admin/pri/irr_submit.action';
		//alert(url);
		var options = { 
				beforeSend :function() { 
                    $.blockUI(); 
                } ,
				complete : function() { 
                    $.unblockUI(); 
                } ,
		        url:	   url,
		        success:   callback, 
		        type:      'post',      
		        dataType:  'json'
		    };
		$('#addForm').ajaxSubmit(options);
	});
	$('#agreeBtn').click(function (){
		if(!check_form_result){return false;}
		var url = '';
		url= '${ctx}/admin/pri/irr_agree.action';
		//alert(url);
		var options = { 
				beforeSend :function() { 
                    $.blockUI(); 
                } ,
				complete : function() { 
                    $.unblockUI(); 
                } ,
		        url:	   url,
		        success:   callback, 
		        type:      'post',      
		        dataType:  'json'
		    };
		$('#addForm').ajaxSubmit(options);
	});
	$('#rejectBtn').click(function (){
		if(!check_form_result){return false;}
		var url = '';
		url= '${ctx}/admin/pri/irr_reject.action';
		//alert(url);
		var options = { 
				beforeSend :function() { 
                    $.blockUI(); 
                } ,
				complete : function() { 
                    $.unblockUI(); 
                } ,
		        url:	   url,
		        success:   callback, 
		        type:      'post',      
		        dataType:  'json'
		    };
		$('#addForm').ajaxSubmit(options);
	});
	//回调函数
	function callback(data){
		alert(data.retmsg);
		if(data.retcode == "0"){				
			returnList();
		}
	}
	var mbrFile = '${model.mbrFile}';
	if(1== mbrFile){
		$.ajax({
			type:'POST',
			url:'${ctx}/admin/pri/irrfile_listByIrr.action',
			data:"model.id=${model.id}&type=mbr",
			success:function(data){
				if(data.list){
					var list = eval(data.list);
					$('#mbrFile').append("如果有多个评审单,请以时间最新的为准：<br/>");
					for(var i=0;i<list.length;i++){
						var irrFile = list[i];
						$('#mbrFile').append("<a href='"+irrFile.downUrl+"'>"+irrFile.filename+" </a> 上传于:"+irrFile.createTime+"<br/>");
					}
					$('#mbrFile').append("<b style='color:red'>为了避免误操作,请确认单据无误后，再做操作!</b>");
				}else{
					$('#sqeFile').append("暂无上传处理单");
				}
			}
		});
	}
	var sqeFile = '${model.sqeFile}';
	if(1== sqeFile){
		$.ajax({
			type:'POST',
			url:'${ctx}/admin/pri/irrfile_listByIrr.action',
			data:"model.id=${model.id}&type=sqe",
			success:function(data){
				if(data.list){
					var list = eval(data.list);
					$('#sqeFile').append("如果有多个处理单,请以时间最新的为准：<br/>");
					for(var i=0;i<list.length;i++){
						var irrFile = list[i];
						$('#sqeFile').append("<a  href='"+irrFile.downUrl+"'>"+irrFile.filename+"</a><br/>");
					}
					$('#sqeFile').append("<b style='color:red'>为了避免误操作,请确认单据无误后，再做操作!</b>");
				}else{
					$('#sqeFile').append("暂无上传处理单");
				}
			}
		});
	}
});


$("#close")
.click(
		function() {
			if (confirm("确定要关闭吗?")) {
				var id = $(this).attr('id');
				$
						.ajax({
							type : 'POST',
							url : '${ctx}/admin/pri/irr_close.action',
							data : "model.id="
									+ id,
							 dataType:  'json',
							success : function(
									data) {
								alert(data.retmsg);
								if (data.retcode == "0") {
									self.location
											.reload();
								}
							}
						});
			}
		});

function returnList(){
	if('true' == '${isShow}'){
		window.location.href = "${ctx}/admin/pri/irr_listReport.action?pageIndex=${pageIndex}&pageSize=${pageSize}";
	}else{
		window.location.href = "${ctx}/admin/pri/irr_list.action?pageIndex=${pageIndex}&pageSize=${pageSize}";
	}
}
		
</script> 
  
</body>
</html>