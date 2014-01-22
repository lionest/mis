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

  <div class="page_tit">帐号管理</div>
  
  <!-------- 搜索 -------->
  <div id="search_div" style="display:none;">
  	<div class="page_tit">搜索帐号 [ <a href="javascript:void(0);" onclick="switchShowSearch();">隐藏</a> ]</div>
	<div class="form2">
	<form method="post" action="${ctx}/admin/pri/user_list.action">
	 <dl class="lineD">
      <dt>帐号：</dt>
      <dd>
        <input name="username" id="username" type="text" value="${username }" />
      </dd>
    </dl>
	
    <dl class="lineD">
      <dt>姓名：</dt>
      <dd>
        <input name="nickname" id="nickname" type="text" value="${nickname }" />
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
			<span class="search_action">搜索帐号</span>
		</a>
		<a id="add" href="javascript:void(0);" class="btn_a"><span>添加帐号</span></a>
  </div>
  <div class="list"><input id="flag"  type="hidden"  value="1"/>
  <table id="listTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="tablesorter">
		  <thead>
		  	<tr >
		  	 <th class="line_l"  width="30">选择</th>
		    <th class="line_l" width="30">ID</th>
		    <th class="line_l">帐号名</th>
		    <th class="line_l">姓名</th>
		    <th class="line_l">邮箱</th>
		    <th class="line_l">联系方式</th>
		    <th class="line_l">角色</th>
		    <th class="line_l" width="50">操作</th>
		    </tr>
		  </thead>
		  <tbody>
		<c:forEach items="${pageInfo.pageData }" var="user" varStatus="status">
			<c:if test="${user.id > 1 }">
				<tr overstyle='on' id="user_1">
					<td><input type="checkbox" name="uids"  class="uids"  value="${user.id }"/></td>
					<td>${user.id }</td>
					<td>
						<div style="float:left">
								<a href='javascript:void(0);' class='fn edit' id="${user.id }">${user.username }</a>
						</div>
					</td>
					<td>${user.nickname }</td>
					<td>${user.email }</td>
					<td>${user.mobile }</td>
					<td>${user.role.name }</td>
					<td>
						<a href='javascript:void(0);' class='fn edit' id="${user.id }">编辑</a>
						 <a href='javascript:void(0);' class='fn delete' id="${user.id }">删除</a> 
					</td>
				</tr>
			</c:if>	
		</c:forEach>
		</tbody>
	</table>
	<input type="button"  value="全选" name="uids_check"  id="selectAll"/><input type="button" id="deletes" onclick="deleteUsers();" value="删除"/>
  </div>
<script>
$("#selectAll").click(function(){
	if($("#flag").val()==1){
		$(".uids").attr("checked","checked");
		$("#flag").val("0");
	}else{
		$(".uids").removeAttr("checked");
		$("#flag").val("1");
	}
	
})	;

function deleteUsers(){
	var fids="";
	$('input[name="uids"]:checked').each(function(){
		 fids+=$(this).val()+",";
	 });
	 if($('input[name="uids"]:checked').size()==0){
		 alert("请选择一个条目!");
		 return; 
	 }
	 if(!confirm('您确认删除吗?')){
		 	return;
		 }
	 
				var id = $(this).attr('id');
				$.post(
						"${ctx}/admin/pri/user_deletes.action",
						{"uids" :　fids	},
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
	<p:page url="${ctx}/admin/pri/user_list.action" pageInfo="${pageInfo }" params="username=${username }&nickname=${nickname }"/>
  </div>
</div>

<script>

$(document).ready(function(){
	$("#listTable").tablesorter({headers:{4:{sorter:false},5:{sorter:false},6:{sorter:false}}}); 
	$("#add").click(function() {
		window.location.href = "${ctx}/admin/pri/user_gotoAdd.action?pageIndex=${pageInfo.pageIndex}";
		return false;
	});

	$("a.edit").click(
		function() {
			var id = $(this).attr('id');
			window.location.href = "${ctx}/admin/pri/user_edit.action?pageIndex=${pageInfo.pageIndex}&model.id="+id;
			return false;
		}
	);
	
	$("a.delete").click(
			function() {
				if(confirm("确定要删除该条信息吗?")){
					var id = $(this).attr('id');
					$.post(
							"${ctx}/admin/pri/user_delete.action",
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

	//获取已选择帐号的ID数组
	function getChecked() {
		var uids = new Array();
		$.each($('table input:checked'), function(i, n){
			uids.push( $(n).val() );
		});
		return uids;
	}

	//删除帐号
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
			$(".search_action").html("搜索帐号");
			isSearchHidden = 1;
		}
	}
	
</script>

</body>
</html>