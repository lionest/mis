<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>管理系统</title>
		${admin_css }${jquery_js }
		<script type="text/javascript">
/* 按下F5时仅刷新iframe页面 */
function inactiveF5(e) {
	return ;
	e=window.event||e;
	var key = e.keyCode;
	if (key == 116){
		parent.MainIframe.location.reload();
		if(document.all) {
			e.keyCode = 0;
			e.returnValue = false;
		}else {
			e.cancelBubble = true;
			e.preventDefault();
		}
	}
}

function nof5() {
    return ;
	if(window.frames&&window.frames[0]) {
		window.frames[0].focus();
		for (var i_tem = 0; i_tem < window.frames.length; i_tem++) {
			if (document.all) {
				window.frames[i_tem].document.onkeydown = new Function("var e=window.frames[" + i_tem + "].event; if(e.keyCode==116){parent.MainIframe.location.reload();e.keyCode = 0;e.returnValue = false;};");
			}else {
				window.frames[i_tem].onkeypress = new Function("e", "if(e.keyCode==116){parent.MainIframe.location.reload();e.cancelBubble = true;e.preventDefault();}");
			}
		} //END for()
	} //END if()
}

function refresh() {
	parent.MainIframe.location.reload();
}

function updateUser(){
	parent.MainIframe.location = "${ctx}/pages/admin/pri/user/updateUser.jsp";
}

document.onkeydown=inactiveF5;
</script>
	</head>

	<body scroll="no" style="margin: 0; padding: 0;" onload="nof5()">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td colspan="3">

					<div class="header">
						<!-- 头部 begin -->
						<div class="logo">
							<a href="${ctx}/pages/admin/admin.jsp" style="font-family:'微软雅黑', '宋体';">管理系统&nbsp;</a>
						</div>
						<div class="nav_sub">
							欢迎,${SESSION_ADMIN_USER.nickname } &nbsp;【${ SESSION_ADMIN_USER.role.name}】
							<a href="javascript:void(0);" onclick="updateUser();">帐号设置</a> |
							<a href="javascript:void(0);" onclick="refresh();">刷新</a> |
							<a href="${ctx }/admin/base/logout.action">退出</a>
							<br />

							<div id="TopTime"></div>
						</div>
						<div class="main_nav">
								<a id="channel_index" class="on" href="javascript:void(0)"
									onclick="switchChannel('index');" hidefocus="true"
									style="outline: none;">控制台</a>
							<c:if test="${SESSION_ADMIN_USER.role.code eq 'admin' }">
								<a id="channel_sys" href="javascript:void(0)"
									onclick="switchChannel('sys');" hidefocus="true"
									style="outline: none;">系统</a>
							</c:if>
						</div>
					</div>
					<div class="header_line">
						<span>&nbsp;</span>
					</div>

				</td>
			</tr>
			<tr>
				<td width="200px" height="100%" valign="top" id="FrameTitle"
					background="${ctx }/resources/images/left_bg.gif">
					<div class="LeftMenu">
						<!-- 第一级菜单，即大频道 -->
						<ul class="MenuList" id="root_index">
							<!-- 第二级菜单 -->
							<li class="treemenu">
								<a id="root_1" class="actuator" href="javascript:void(0)"
									onclick="switch_root_menu('1');" hidefocus="true"
									style="outline: none;">控制台</a>
								<ul id="tree_1" class="submenu">
									<!-- 第三级菜单 -->
										<li>
											<a id="menu_10" href="javascript:void(0)"
												onClick="switch_sub_menu('10', '${ctx }/admin/pri/chart_show.action');" 
												class="submenuA" hidefocus="true" style="outline: none;">质量报表</a>
										</li>
										<li>
											<a id="menu_11" href="javascript:void(0)"
												onClick="switch_sub_menu('11', '${ctx }/admin/pri/irr_listReport.action');" 
												class="submenuA" hidefocus="true" style="outline: none;">报告一览</a>
										</li>
										<c:if test="${'QA' == SESSION_ADMIN_USER.role.code||'GM' == SESSION_ADMIN_USER.role.code||'QE' == SESSION_ADMIN_USER.role.code||'OD' == SESSION_ADMIN_USER.role.code||'CG' == SESSION_ADMIN_USER.role.code||'SQE' == SESSION_ADMIN_USER.role.code }">
											<li>
												<a id="menu_12" href="javascript:void(0)"
													onClick="switch_sub_menu('12', '${ctx }/admin/pri/irr_list.action');" 
													class="submenuA" hidefocus="true" style="outline: none;">我的报告</a>
											</li>
										</c:if>
								</ul>
							</li>
						</ul>

						<ul class="MenuList" id="root_sys" style="display: none;">
							<!-- 第二级菜单 -->
							<li class="treemenu">
								<a id="root_4" class="actuator" href="#"
									onclick="switch_root_menu('4');" hidefocus="true"
									style="outline: none;">系统信息</a>
								<ul id="tree_4" class="submenu">
									<!-- 第三级菜单 -->
										<li>
											<a id="menu_40" href="#"
												onClick="switch_sub_menu('40', '${ctx }/admin/pri/user_list.do');"
												class="submenuA" hidefocus="true" style="outline: none;">帐号管理</a>
										</li>
										<li>
											<a id="menu_41" href="javascript:void(0)"
												onClick="switch_sub_menu('41', '${ctx }/admin/pri/supplier_list.do');"
												class="submenuA" hidefocus="true" style="outline: none;">供应商管理</a>
										</li>
										<li>
											<a id="menu_44" href="javascript:void(0)"
												onClick="switch_sub_menu('44', '${ctx }/admin/pri/part_list.do');"
												class="submenuA" hidefocus="true" style="outline: none;">零件管理</a>
										</li>
										<li>
											<a id="menu_43" href="javascript:void(0)"
												onClick="switch_sub_menu('43', '${ctx }/admin/pri/role_list.do');"
												class="submenuA" hidefocus="true" style="outline: none;">角色管理</a>
										</li>
										<li>
											<a id="menu_42" href="javascript:void(0)"
												onClick="switch_sub_menu('42', '${ctx }/admin/pri/clean.do');"
												class="submenuA" hidefocus="true" style="outline: none;">清除数据</a>
										</li>
								</ul>
							</li>
						</ul>
						
					</div>
				</td>
				<td>
					<iframe onload="nof5()" id="MainIframe" name="MainIframe"
						scrolling="yes" src="home.jsp" width="100%" height="100%"
						frameborder="0" noresize>
					</iframe>
				</td>
			</tr>
		</table>
	</body>

	<script type="text/javascript">
	var current_channel   = null;
	var current_menu_root = null;
	var current_menu_sub  = null;
	var viewed_channel	  = new Array();
	
	$(document).ready(function(){
		switchChannel('index');
	});
	
	//切换频道（即头部的tab）
	function switchChannel(channel) {
		if(current_channel == channel) return false;
		
		$('#channel_'+current_channel).removeClass('on');
		$('#channel_'+channel).addClass('on');
		
		$('#root_'+current_channel).css('display', 'none');
		$('#root_'+channel).css('display', 'block');
		
		var tmp_menulist = $('#root_'+channel).find('a');
		tmp_menulist.each(function(i, n) {
			// 防止重复点击ROOT菜单
			if( i == 0 && $.inArray($(n).attr('id'), viewed_channel) == -1 ) {
				$(n).click();
				viewed_channel.push($(n).attr('id'));
			}
			if ( i == 1 ) {
				$(n).click();
			}
		});

		current_channel = channel;
	}
	
	function switch_root_menu(root) {
		root = $('#tree_'+root);
		if (root.css('display') == 'block') {
			root.css('display', 'none');
			root.parent().css('backgroundImage', 'url(${ctx}/resources/images/ArrOn.png)');
		}else {
			root.css('display', 'block');
			root.parent().css('backgroundImage', 'url(${ctx}/resources/images/ArrOff.png)');
		}
	}
	
	function switch_sub_menu(sub, url) {
		if(sub==42){
			if(!confirm('确认清除系统数据吗？系统的零件,供应商,IRR报告数据将会被清空!')){
				return false;
			}
		}
		if(current_menu_sub) {
			$('#menu_'+current_menu_sub).attr('class', 'submenuA');
		}
		current_menu_sub = sub;
		$('#menu_'+sub).attr('class', 'submenuB');
		parent.MainIframe.location = url;
		return false;
	}
</script>
</html>