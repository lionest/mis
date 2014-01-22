<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		${admin_css }${jquery_js }${util_js }${tableSort_js }
</head>
<body>
<div class="so_main">

  <div class="page_tit">供应商管理</div>
  
  <!-------- 搜索 -------->
  <div id="search_div" style="display:none;">
  	<div class="page_tit">搜索供应商 [ <a href="javascript:void(0);" onclick="switchShowSearch();">隐藏</a> ]</div>
	<div class="form2">
	<form method="post" action="${ctx}/admin/pri/supplier_list.action">
	 <dl class="lineD">
      <dt>供应商名称：</dt>
      <dd>
        <input name="model.name" id="model.name" type="text" value="${model.name }" />
      </dd>
    </dl>
	
    <dl class="lineD">
      <dt>供应商代码：</dt>
      <dd>
        <input name="model.code" id="model.code" type="text" value="${model.code }" />
      </dd>
    </dl>
    
    <dl class="lineD">
      <dt>SQE：</dt>
      <dd>
        <input name="model.sqe" id="model.sqe" type="text" value="${model.sqe }" />
      </dd>
    </dl>
	 
    <div class="page_btm">
      <input type="submit" class="btn_b" value="确定" />
    </div>
	</form>
  </div>
  </div>
  
  <!-------- 列表 -------->
  <div class="Toolbar_inbox">
		<a href="javascript:void(0);" class="btn_a" onclick="switchShowSearch();">
			<span class="search_action">搜索供应商</span>
		</a>
		<a id="add" href="javascript:void(0);" class="btn_a"><span>添加供应商</span></a>
		<a id="import" href="javascript:void(0);" class="btn_a"><span>导入供应商</span></a>
  </div>
  <div class="list"><input id="flag"  type="hidden"  value="1"/>
  <table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tablesorter">
		  <thead>
		  	<tr >
		  	<th class="line_l"  width="30">选择</th>
		    <th class="line_l" width="30">ID</th>
		    <th class="line_l">供应商名称</th>
		    <th class="line_l" width="100">供应商代码</th>
		    <th class="line_l" >类别</th>
		    <th class="line_l" >邮箱</th>
		    <th class="line_l">SQE</th>
		    <th class="line_l">修改时间</th>
		    <th class="line_l" width="50">操作</th>
		    </tr>
		  </thead>
		  <tbody> 
		<c:forEach items="${pageInfo.pageData }" var="entity" varStatus="status">
				<tr overstyle='on' id="supplier_1">
					<td><input type="checkbox" name="sids"  class="sids"  value="${entity.id }"/></td>
					<td>${entity.id }</td>
					<td>
						<div style="float:left">
								<a href='javascript:void(0);' class='fn edit' id="${entity.id }" title="${entity.name }">${wd:limit(entity.name,15) }</a>
						</div>
					</td>
					<td>${entity.code }</td>
					<td>${entity.type }</td>
					<td title="${entity.email }">${wd:limit(entity.email,20) }</td>
					<td title="${entity.sqeEmail }">${entity.sqe }</td>
					<td><fmt:formatDate value='${entity.createTime }' pattern='yyyy-MM-dd HH:mm:ss'/></td>
					<td>
						<a href='javascript:void(0);' class='fn edit' id="${entity.id }">编辑</a>
						<a href='javascript:void(0);' class='fn delete' id="${entity.id }">删除</a> 
					</td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	<input type="button"  value="全选" name="sids_check"  id="selectAll"/><input type="button" id="deletes" onclick="deleteSups();" value="删除"/>
  </div>
<script>
$("#selectAll").click(function(){
	if($("#flag").val()==1){
		$(".sids").attr("checked","checked");
		$("#flag").val("0");
	}else{
		$(".sids").removeAttr("checked");
		$("#flag").val("1");
	}
	
})	;

function deleteSups(){
	var sids="";
	$('input[name="sids"]:checked').each(function(){
		sids+=$(this).val()+",";
	 });
	 if($('input[name="sids"]:checked').size()==0){
		 alert("请选择一个条目!");
		 return; 
	 }
	 if(!confirm('您确认删除吗?删除后所选供应商下的零件将会被删除！')){
		 	return;
		 }
				var id = $(this).attr('id');
				$.post(
						"${ctx}/admin/pri/supplier_deletes.action",
						{"sids" :　sids	},
						function(data) {
							alert(data.retmsg);
							if(data.retcode == "0"){
								location.reload();
							}
						}, 
						"json"
				);
			
			return false;
		
 }

</script>
  <div class="Toolbar_inbox" >
	<p:page url="${ctx}/admin/pri/supplier_list.action" pageInfo="${pageInfo }" params="model.name=${model.name }&model.code=${model.code }&model.sqe=${model.sqe }"/>
  </div>
</div>

<script>

$(document).ready(function(){
	$("#listTable").tablesorter({headers:{3:{sorter:false},4:{sorter:false},6:{sorter:false}}}); 
	$("#add").click(function() {
		window.location.href = "${ctx}/admin/pri/supplier_gotoAdd.action?pageIndex=${pageInfo.pageIndex}";
		return false;
	});
	$("#import").click(function() {
		window.location.href = "${ctx}/admin/pri/supplier_gotoImport.action?pageIndex=${pageInfo.pageIndex}";
		return false;
	});

	$("a.edit").click(
		function() {
			var id = $(this).attr('id');
			window.location.href = "${ctx}/admin/pri/supplier_edit.action?pageIndex=${pageInfo.pageIndex}&model.id="+id;
			return false;
		}
	);
	$("a.delete").click(
			function() {
				if(confirm("确定要删除该条信息吗?删除后所选供应商下的零件将会被删除！")){
					var id = $(this).attr('id');
					$.post(
							"${ctx}/admin/pri/supplier_delete.action",
							{"model.id" :　id	},
							function(data) {
								alert(data.retmsg);
								if(data.retcode == "0"){
									location.reload();
								}
							}, 
							"json"
					);
				}
				return false;
			}
		);
});

	//鼠标移动表格效果
	$(document).ready(function(){
		$("tr[overstyle='on']").hover(
		  function () {
		    $(this).addClass("bg_hover");
		  },
		  function () {
		    $(this).removeClass("bg_hover");
		  }
		);
	});
	
	//搜索
	var isSearchHidden = 1;
	function switchShowSearch() {
		if(isSearchHidden == 1) {
			$("#search_div").slideDown("fast");
			$(".search_action").html("搜索完毕");
			isSearchHidden = 0;
		}else {
			$("#search_div").slideUp("fast");
			$(".search_action").html("搜索供应商");
			isSearchHidden = 1;
		}
	}
	
</script>

</body>
</html>