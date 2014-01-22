<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
${admin_css }${jquery_js }${util_js }${tableSort_js }${WdatePicker_js }
</head>
<body>
	<div class="so_main">

		<div class="page_tit">我的报告</div>

		<!-------- 搜索 -------->
		<div id="search_div" style="display: none;">
			<div class="page_tit">
				搜索报告 [ <a href="javascript:void(0);" onclick="switchShowSearch();">隐藏</a>
				]
			</div>
			<div class="form2">
				<form method="post" action="${ctx}/admin/pri/irr_list.action"  id="searchForm">
					<dl class="lineD">
						<dt>IRR编号：</dt>
						<dd>
							<input name="model.code" id="model.code" type="text"
								value="${model.code }" />
						</dd>
					</dl>

					<dl class="lineD">
						<dt>零件名称：</dt>
						<dd>
							<input name="model.linJianMingCheng" id="model.linJianMingCheng"
								type="text" value="${model.linJianMingCheng }" />
						</dd>
					</dl>

					<dl class="lineD">
						<dt>零件号：</dt>
						<dd>
							<input name="model.linJianHao" id="model.linJianHao" type="text"
								value="${model.linJianHao }" />
						</dd>
					</dl>
					<dl class="lineD">
						<dt>供应商：</dt>
						<dd>
							<select name="spId" id="spId" width="300">
								<option value="-1">全部</option>
								<c:forEach var="spp" items="${spList}">
									<option value="${spp.id }">${spp.name }</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl class="lineD">
						<dt>批次号：</dt>
						<dd>
							<input name="model.piCiHao" id="model.piCiHao" type="text"
								value="${model.piCiHao }" />
						</dd>
					</dl>
					<dl class="lineD">
						<dt>时间段：</dt>
						<dd>
							<input name="startTime" type="text" id="beginTime"
								value="${startTime}" class="Wdate"
								onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm'})"
								readonly="readonly" /> 至<input name="endTime" type="text"
								id="endTime" value="${endTime}" class="Wdate"
								onfocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm'})"
								readonly="readonly" />
						</dd>
					</dl>

					<dl class="lineD">
						<dt>状态：</dt>
						<dd>
							<select name="model.state" id="state">
								<option value="-1">全部</option>
								<option value="1">待提交</option>
								<option value="2">待审核</option>
								<option value="3">已审批</option>
								<option value="4">已撤销</option>
								<option value="5">已取消</option>
							</select>
						</dd>
					</dl>

					<div class="page_btm">
						<input type="submit" class="btn_b" value="确定" />  <input type="button" class="btn_b"  id="clear"  value="清空" />
					</div>
				</form>
			</div>
		</div>

		<!-------- 列表 -------->
		<div class="Toolbar_inbox">
			<a href="javascript:void(0);" class="btn_a"
				onclick="switchShowSearch();"> <span class="search_action">搜索报告</span>
			</a>
			<c:if test="${SESSION_ADMIN_USER.role.code eq 'QA' }">
				<a id="add" href="javascript:void(0);" class="btn_a"><span>添加报告</span></a>
			</c:if>
		</div>
		<div class="list">
			<table id="listTable" width="100%" border="0" cellspacing="0"
				cellpadding="0" class="tablesorter">
				<thead>
					<tr>
						<th class="line_l" width="30">ID</th>
						<th class="line_l">发布时间</th>
						<th class="line_l">IRR编号</th>
						<th class="line_l">零件名称</th>
						<th class="line_l">供应商名称</th>
						<th class="line_l">批次号</th>
						<!--th class="line_l">数量</th>
		    <th class="line_l">检验日期</th>
		    <th class="line_l">检验员</th-->
						<th class="line_l">审核QE</th>
						<th class="line_l">SQE</th>
						<th class="line_l">状态</th>
						<!--th class="line_l">评审单</th>
		    <th class="line_l">处理单</th-->
						<th class="line_l">已关闭</th>
							<th class="line_l">是否延迟</th>
						<th class="line_l">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageInfo.pageData }" var="entity"
						varStatus="status">
						<tr overstyle='on' id="supplier_1">
							<td>${entity.id }</td>
							<td><fmt:formatDate value='${entity.createTime}'  pattern='yyyy-MM-dd HH:mm' /></td>
							<td>
								<div style="float: left" title="${entity.miaoShu }">
									<a href='javascript:void(0);' class='fn edit'
										id="${entity.id }">${entity.code }</a>
								</div>
							</td>
							<td>${wd:limit(entity.linJianMingCheng,10) }</td>
							<td>${wd:limit(entity.sp.name,10) }</td>
							<td>${wd:limit(entity.piCiHao,10) }</td>
							<!--td>${wd:limit(entity.shuLiang,10) }</td>
					<td>${entity.createTimeStr }</td>
					<td>${entity.qa.nickname }</td-->
							<td>${entity.qe.nickname }</td>
							<td>${entity.sp.sqe }</td>
							<td><c:if test="${entity.state == 1 }">待提交</c:if> <c:if
									test="${entity.state == 2 }">
									<span style="background-color: yellow;">待审核</span>
								</c:if> <c:if test="${entity.state == 3 }">
									<span style="background-color: green; color: white;">已审批</span>
								</c:if> <c:if test="${entity.state == 4 }">
									<span style="background-color: red; color: white;">已撤销</span>
								</c:if></td>
							<!--td>${entity.mbrFile eq 1 ? '已提交':'无' }</td>
					<td>
						${entity.sqeFile eq 1 ? '':'无' }
						<c:if test="${1 eq entity.cldType }">偏差</c:if>
				      	<c:if test="${2 eq entity.cldType }">筛选</c:if>
				      	<c:if test="${3 eq entity.cldType }">返工</c:if>
				      	<c:if test="${4 eq entity.cldType }">退货</c:if>
				      	<c:if test="${5 eq entity.cldType }">报废</c:if>
					</td-->
							<td><c:if test="${entity.closed == 0 }">
									<span style="background-color: yellow;">否</span>
								</c:if> <c:if test="${entity.closed == 1 }">
									<span style="background-color: green; color: white;">是</span>
								</c:if></td>
								<td>
								<c:if test="${entity.delay<=0||entity.delay eq null }">
									未延迟
								</c:if>
									<c:if test="${entity.delay>0 }">
									延迟${entity.delay}小时
									</c:if>
								</td>
							<td>
							<c:if
									test="${entity.state eq 3 && entity.closed eq 0}">
									<c:if
										test="${('OD' eq SESSION_ADMIN_USER.role.code && '国产件' eq entity.sp.type) || ('GM' eq SESSION_ADMIN_USER.role.code && '进口件' eq entity.sp.type)|| ('CG' eq SESSION_ADMIN_USER.role.code && '型钢件' eq entity.sp.type)}">
										<c:choose>
											<c:when test="${entity.mbrFile eq 1}">
												<a href="javascript" class='fn mbrUpload' id="${entity.id }">修改评审单</a>
											</c:when>
											<c:otherwise>
												<a href="javascript" class='fn mbrUpload' id="${entity.id }">上传评审单</a>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if
										test="${entity.mbrFile eq 1&&'SQE' eq SESSION_ADMIN_USER.role.code}">
										<br />
										<c:choose>
											<c:when test="${entity.sqeFile eq 1}">
												<a href="javascript" class='fn sqeUpload' id="${entity.id }">修改处理单</a>
											</c:when>
											<c:otherwise>
											<a href="javascript" class='fn sqeUpload' id="${entity.id }">上传处理单</a>
											</c:otherwise>
										</c:choose>
												
									</c:if>

								</c:if> 
							<c:if test="${'QA' eq SESSION_ADMIN_USER.role.code }">
							<c:if test="${entity.state <= 2 }">
							
									<a href='javascript:void(0);' class='fn edit'
										id="${entity.id }">编辑</a>
										</c:if>
										
							<c:if test="${entity.state == 1 }">
										<a href='javascript:void(0);' class='fn remove'
										id="${entity.id }">删除</a>
								</c:if> 
								</c:if>
								<c:if test="${entity.state == 2 }">
									<c:if test="${'QA' eq SESSION_ADMIN_USER.role.code }">
										<a href='javascript:void(0);' class='fn edit'
											id="${entity.id }">查看详细</a>
									</c:if>
									
									<c:if test="${'QA' eq SESSION_ADMIN_USER.role.code }">
										<a href='javascript:void(0);' class='fn cancel'
											id="${entity.id }">取消</a>
									</c:if>
									<c:if test="${'QE' eq SESSION_ADMIN_USER.role.code }">
										<a href='javascript:void(0);' class='fn edit'
											id="${entity.id }">审核</a>
									</c:if>
								</c:if> 
								
								<c:if test="${entity.state > 2 }"><a href='javascript:void(0);' class='fn edit'
											id="${entity.id }">查看详细</a></c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="Toolbar_inbox">
			<p:page url="${ctx}/admin/pri/irr_list.action"
				pageInfo="${pageInfo }"
				params="model.code=${model.code }&model.linJianHao=${model.linJianHao }&model.piCiHao=${model.piCiHao }&spId=${spId }&model.state=${model.state }&startTime=${startTime }&endTime=${endTime }" />
		</div>
	</div>

	<script>
		$(document)
				.ready(
						function() {
							$('#spId').val('${spId }');
							$('#state').val('${model.state }');
							$("#listTable").tablesorter({
								headers : {
									2 : {
										sorter : false
									},
									3 : {
										sorter : false
									},
									4 : {
										sorter : false
									},
									5 : {
										sorter : false
									},
									6 : {
										sorter : false
									},
									8 : {
										sorter : false
									},
									9 : {
										sorter : false
									},
									10 : {
										sorter : false
									},
									12 : {
										sorter : false
									},
									13 : {
										sorter : false
									},
									14 : {
										sorter : false
									},
									15 : {
										sorter : false
									}
								}
							});
							
							
							
							//清空表单值
							$("#clear").click(function(){
								$(':input','#searchForm')  
								 .not(':button, :submit, :reset, :hidden')  
								 .val('')  
								 .removeAttr('checked')  
								 .removeAttr('selected');
							});
							$("#add")
									.click(
											function() {
												window.location.href = "${ctx}/admin/pri/irr_gotoAdd.action?pageIndex=${pageInfo.pageIndex}";
												return false;
											});

							$("a.edit")
									.click(
											function() {
												var id = $(this).attr('id');
												window.location.href = "${ctx}/admin/pri/irr_edit.action?pageIndex=${pageInfo.pageIndex}&model.id="
														+ id;
												return false;
											});

							$("a.remove")
									.click(
											function() {
												var id = $(this).attr('id');
												if(confirm("确认删除此报告吗?"))
												window.location.href = "${ctx}/admin/pri/irr_delete.action?pageIndex=${pageInfo.pageIndex}&model.id="
														+ id;
												return false;
											});
							
							$("a.cancel")
							.click(
									function() {
										var id = $(this).attr('id');
										if(confirm("确认取消此报告吗?"))
										window.location.href = "${ctx}/admin/pri/irr_cancel.action?pageIndex=${pageInfo.pageIndex}&model.id="
												+ id;
										return false;
									});
							$("a.mbrUpload")
							.click(
									function() {
										var id = $(this).attr('id');
										window.location.href = "${ctx}/admin/pri/irrfile_gotoUpload.action?type=mbr&pageIndex=${pageInfo.pageIndex}&model.id="
												+ id;
										return false;
									});
					$("a.sqeUpload")
							.click(
									function() {
										var id = $(this).attr('id');
										window.location.href = "${ctx}/admin/pri/irrfile_gotoUpload.action?type=sqe&pageIndex=${pageInfo.pageIndex}&model.id="
												+ id;
										return false;
									});

						});

		//鼠标移动表格效果
		$(document).ready(function() {
			$("tr[overstyle='on']").hover(function() {
				$(this).addClass("bg_hover");
			}, function() {
				$(this).removeClass("bg_hover");
			});
		});

		//搜索
		var isSearchHidden = 1;
		function switchShowSearch() {
			if (isSearchHidden == 1) {
				$("#search_div").slideDown("fast");
				$(".search_action").html("搜索完毕");
				isSearchHidden = 0;
			} else {
				$("#search_div").slideUp("fast");
				$(".search_action").html("搜索报告");
				isSearchHidden = 1;
			}
		}
	</script>

</body>
</html>