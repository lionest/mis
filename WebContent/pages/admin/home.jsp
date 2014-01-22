<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${siteName}</title>
		${admin_css }${jquery_js }
	</head>
<body>
<script>
function fold(index) {
  	$('#app_'+index).slideToggle('fast');
}


</script>

<div id="container" class="so_main">
    <div class="page_tit">欢迎使用</div>
			<div class="form2">
				<!--<h4>
					提示：点击标题可以折叠栏目
				</h4>
				<h3 onclick="fold('1');">
					用户信息
				</h3>
				<div id="app_1">
					<dl>
						<dt>
							<strong>当前在线：</strong>
						</dt>
						<dd>
							10000
						</dd>
					</dl>
					<dl>
						<dt>
							<strong>全部用户：</strong>
						</dt>
						<dd>
							67776677778
						</dd>
					</dl>
					<dl>
						<dt>
							<strong>有效用户：</strong>
						</dt>
						<dd>
							898989981
						</dd>
					</dl>
				</div>
				<h3 onclick="fold('2');">
					相册
				</h3>
				<div id="app_2">
					<dl>
						<dt>
							<strong>非空相册总数：</strong>
						</dt>
						<dd>
							0
						</dd>
					</dl>
					<dl>
						<dt>
							<strong>图片数量：</strong>
						</dt>
						<dd>
							0
						</dd>
					</dl>
					<dl>
						<dt>
							<strong>占用空间：</strong>
						</dt>
						<dd>
							0 B
						</dd>
					</dl>
				</div>
				<h3 onclick="fold('3');">
					活动
				</h3>

				<div id="app_3">
					<dl>
						<dt>
							<strong>活动总数：</strong>
						</dt>
						<dd>
							8880
						</dd>
					</dl>
					<dl>
						<dt>
							<strong>平均参与人次：</strong>
						</dt>
						<dd>
							9898.0
						</dd>
					</dl>
					<dl>
						<dt>
							<strong>当前进行的活动数：</strong>
						</dt>
						<dd>
							776
						</dd>
					</dl>
					<dl>
						<dt>
							<strong>当前平均参与人次：</strong>
						</dt>
						<dd>
							0.0
						</dd>
					</dl>
				</div>
				<h3 onclick="fold('4');">
					开发团队
				</h3>
				<div id="app_4">

					<dl>
						<dt>
							<strong>版权所有：</strong>
						</dt>
						<dd>
							<a href="http://www.wondertek.com.cn" target="_blank">网达软件</a>
						</dd>
					</dl>
				</div>
			--></div>

		</div>
</body>
</html>