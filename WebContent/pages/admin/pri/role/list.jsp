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

  <div class="page_tit">角色管理</div>
  
  
  <!-------- 列表 -------->
  <div class="Toolbar_inbox">
		<a id="add" href="javascript:void(0);" class="btn_a"><span>添加角色</span></a>
  </div>
  <div class="list">
  <table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tablesorter">
		  <thead>
		  	<tr >
		    <th class="line_l" width="30">ID</th>
		    <th class="line_l">角色名</th>
		    <th class="line_l">代码</th>
		    <th class="line_l" width="50">操作</th>
		    </tr>
		  </thead>
		  <tbody> 
		<c:forEach items="${pageInfo.pageData }" var="role" varStatus="status">
			<c:if test="${role.id > 1 }">
				<tr overstyle='on' id="role_1">
					<td>${role.id }</td>
					<td>
						<div style="float:left">
								<a href='javascript:void(0);' class='fn edit' id="${role.id }">${role.name }</a>
						</div>
					</td>
					<td>${role.code }</td>
					<td>
						<a href='javascript:void(0);' class='fn edit' id="${role.id }">编辑</a>
						<%-- <a href='javascript:void(0);' class='fn delete' id="${user.id }">删除</a> --%>
					</td>
				</tr>
			</c:if>	
		</c:forEach>
		</tbody>
	</table>
  </div>

  <div class="Toolbar_inbox" >
	<p:page url="${ctx}/admin/pri/role_list.action" pageInfo="${pageInfo }" params="name=${name}&code=${code}"/>
  </div>
</div>

<script>

$(document).ready(function(){
	$("#listTable").tablesorter({headers:{4:{sorter:false},5:{sorter:false},6:{sorter:false}}}); 
	$("#add").click(function() {
		window.location.href = "${ctx}/admin/pri/role_gotoAdd.action?pageIndex=${pageInfo.pageIndex}";
		return false;
	});

	$("a.edit").click(
		function() {
			var id = $(this).attr('id');
			window.location.href = "${ctx}/admin/pri/role_edit.action?pageIndex=${pageInfo.pageIndex}&model.id="+id;
			return false;
		}
	);
	
	$("a.delete").click(
			function() {
				if(confirm("确定要删除该条信息吗?")){
					var id = $(this).attr('id');
					$.post(
							"${ctx}/admin/pri/user_delete.action",
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

	//获取已选择角色的ID数组
	function getChecked() {
		var uids = new Array();
		$.each($('table input:checked'), function(i, n){
			uids.push( $(n).val() );
		});
		return uids;
	}

	//删除角色
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
	
	
</script>

</body>
</html>