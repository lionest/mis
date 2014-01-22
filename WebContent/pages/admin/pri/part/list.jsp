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

  <div class="page_tit">零件管理</div>
  <!-------- 搜索 -------->
  <div id="search_div" style="display:none;">
  	<div class="page_tit">搜索零件 [ <a href="javascript:void(0);" onclick="switchShowSearch();">隐藏</a> ]</div>
	<div class="form2">
	<form method="post" action="${ctx}/admin/pri/part_list.action">
	 <dl class="lineD">
      <dt>零件名称：</dt>
      <dd>
        <input name="model.name" id="model.name" type="text" value="${model.name }" />
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
			<span class="search_action">搜索零件</span>
		</a>
		<a id="add" href="javascript:void(0);" class="btn_a"><span>添加零件</span></a>
			<a id="import" href="javascript:void(0);" class="btn_a"><span>导入零件</span></a>
  </div>
  <div class="list"><input id="flag"  type="hidden"  value="1"/>
  <table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tablesorter">
		  <thead>
		  	<tr >
		  	<th class="line_l"  width="30">选择</th>
		    <th class="line_l" width="30">ID</th>
		    <th class="line_l">零件名</th>
		    <th class="line_l">零件号</th>
		     <th class="line_l">供应商</th>
		    <th class="line_l" width="50">操作</th>
		    </tr>
		  </thead>
		  <tbody> 
		<c:forEach items="${pageInfo.pageData }" var="part" varStatus="status">
			<c:if test="${part.id > 1 }">
				<tr overstyle='on' id="part_1">
				<td><input type="checkbox" name="pids"  class="pids"  value="${part.id }"/></td>
					<td>${part.id }</td>
					<td>
						<div style="float:left">
								<a href='javascript:void(0);' class='fn edit' id="${part.id }">${part.name }</a>
						</div>
					</td>
					<td>${part.num }</td>
					<td>
							${part.supplier.name }
					</td>
					<td>
						<a href='javascript:void(0);' class='fn edit' id="${part.id }">编辑</a>
						<a href='javascript:void(0);' class='fn delete' id="${part.id }">删除</a> 
					</td>
				</tr>
			</c:if>	
		</c:forEach>
		</tbody>
	</table><input type="button"  value="全选" name="pids_check"  id="selectAll"/><input type="button" id="deletes" onclick="deleteParts();" value="删除"/>
  </div>
<script>
$("#selectAll").click(function(){
	if($("#flag").val()==1){
		$(".pids").attr("checked","checked");
		$("#flag").val("0");
	}else{
		$(".pids").removeAttr("checked");
		$("#flag").val("1");
	}
	
})	;

function deleteParts(){
	var pids="";
	$('input[name="pids"]:checked').each(function(){
		pids+=$(this).val()+",";
	 });
	 if($('input[name="pids"]:checked').size()==0){
		 alert("请选择一个条目!");
		 return; 
	 }
	 if(!confirm('您确认删除吗?')){
		 	return;
		 }
				var id = $(this).attr('id');
				$.post(
						"${ctx}/admin/pri/part_deletes.action",
						{"pids" :　pids	},
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
	<p:page url="${ctx}/admin/pri/part_list.action" pageInfo="${pageInfo }" params="model.name=${model.name }"/>
  </div>
</div>

<script>

$(document).ready(function(){
	$("#listTable").tablesorter({headers:{4:{sorter:false},5:{sorter:false},6:{sorter:false}}}); 
	$("#add").click(function() {
		window.location.href = "${ctx}/admin/pri/part_gotoAdd.action?pageIndex=${pageInfo.pageIndex}";
		return false;
	});

	$("#import").click(function() {
		window.location.href = "${ctx}/admin/pri/part_gotoImport.action?pageIndex=${pageInfo.pageIndex}";
		return false;
	});
	$("a.edit").click(
		function() {
			var id = $(this).attr('id');
			window.location.href = "${ctx}/admin/pri/part_edit.action?pageIndex=${pageInfo.pageIndex}&model.id="+id;
			return false;
		}
	);
	
	$("a.delete").click(
			function() {
				if(confirm("确定要删除该条信息吗?")){
					var id = $(this).attr('id');
					$.post(
							"${ctx}/admin/pri/part_delete.action",
							{"model.id" :id	},
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
	
	function checkon(o){
		if( o.checked == true ){
			$(o).parents('tr').addClass('bg_on') ;
		}else{
			$(o).parents('tr').removeClass('bg_on') ;
		}
	}
	
	function checkAll(o){
		if( o.checked == true ){
			$('input[name="checkbox"]').attr('checked','true');
			$('tr[overstyle="on"]').addClass("bg_on");
		}else{
			$('input[name="checkbox"]').removeAttr('checked');
			$('tr[overstyle="on"]').removeClass("bg_on");
		}
	}

	//获取已选择零件的ID数组
	function getChecked() {
		var uids = new Array();
		$.each($('table input:checked'), function(i, n){
			uids.push( $(n).val() );
		});
		return uids;
	}

	//删除零件
	function deleteUser(uid) {
		uid = uid ? uid : getChecked();
		uid = uid.toString();
		if(uid == '' || !confirm('删除成功后将无法恢复，确认继续？')) return false;
		
		$.post("http://www.baidu.com", {uid:uid}, function(res){
			if(res == '1') {
				uid = uid.split(',');
				for(i = 0; i < uid.length; i++) {
					$('#user_'+uid[i]).remove();
				}
				ui.success('操作成功');
			}else {
				ui.error('操作失败');
			}
		});
	}
	//搜索
	var isSearchHidden = 1;
	function switchShowSearch() {
		if(isSearchHidden == 1) {
			$("#search_div").slideDown("fast");
			$(".search_action").html("搜索完毕");
			isSearchHidden = 0;
		}else {
			$("#search_div").slideUp("fast");
			$(".search_action").html("搜索零件");
			isSearchHidden = 1;
		}
	}
	
</script>

</body>
</html>