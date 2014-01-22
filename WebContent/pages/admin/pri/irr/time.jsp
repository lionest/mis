<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${siteName}</title> ${jquery7_js } ${main_js } ${history_css }

</head>
<body>
	<div class="">
	<div class="head-warp">
  <div class="head">
        <div class="nav-box">
          <ul>
              <li class="cur" style="text-align:center; font-size:40px; font-family:'微软雅黑', '宋体';">IRR 报告时间轴</li>
          </ul>
        </div>
  </div>
</div>
			<div class="main" >
			<div class="history">
				<div class="history-date">
					<ul>
						<h2 class="first">
							<a href="#nogo"><fmt:formatDate value='${model.createTime}'
									pattern='yyyy' />年</a>
						</h2>

						<c:if test="${ not empty model.createTime }">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.createTime}'  pattern='yyyy-MM-dd HH:mm:dd' /></span>
								</h3>
								<dl>
									<dt>
										${model.linJianMingCheng } 零件在这天被 ${model.qa.username } 检查出来有问题
										<span>供销商:${model.sp.name }</span>
									</dt>
								</dl>
							</li>
						</c:if>
						<c:if test="${not empty model.modifyTime}">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.modifyTime}'  pattern='yyyy-MM-dd HH:mm:dd'  /></span>
								</h3>
								<dl>
									<dt>${model.qa.username } 提交了此报告</dt>
								</dl>
							</li>
						</c:if>

						<c:if test="${not empty model.cancelTime}">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.cancelTime}' pattern='yyyy-MM-dd HH:mm:dd'  /></span>
								</h3>
								<dl>
									<dt>${model.qa.username } 取消了此报告</dt>
								</dl>
							</li>
						</c:if>

						<c:if test="${not  empty model.delaytime}">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.delaytime}'  pattern='yyyy-MM-dd HH:mm:dd' /></span>
								</h3>
								<dl>
									<dt>${model.qe.username } 申请了延迟处理此IRR，延迟时间为 ${model.delay
										} 小时</dt>
								</dl>
							</li>
						</c:if>

						<c:if test="${not empty model.agreeTime}">
									<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.agreeTime}' pattern='yyyy-MM-dd HH:mm:dd'  /></span>
								</h3>
								<dl>
									<dt>${model.qe.username } 批准了同意发布了IRR</dt>
								</dl>
							</li>
						</c:if>

						<c:if test="${not empty model.rejectTime }">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.rejectTime}'  pattern='yyyy-MM-dd HH:mm:dd' /></span>
								</h3>
								<dl>
									<dt>此IRR被${model.operator.username } 撤销</dt>
								</dl>
							</li>
						</c:if>
						
						<c:if test="${not empty model.mbrTime }">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.mbrTime}' pattern='yyyy-MM-dd HH:mm:dd' /></span>
								</h3>
								<dl>
									<dt>MBR小组上传了评审报告</dt>
								</dl>
							</li>
						</c:if>
						
					
					<c:if test="${not empty model.handleTime }">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.handleTime}'  pattern='yyyy-MM-dd HH:mm:dd' /></span>
								</h3>
								<dl>
									<dt>SQE上传了
									<c:choose>
										<c:when test="${model.cldType==1 }">
										 偏差 
										</c:when>
												<c:when test="${model.cldType==2 }">
												筛选
										</c:when>
												<c:when test="${model.cldType==3 }">
												返工
										</c:when>
												<c:when test="${model.cldType==4 }">
												 退货
										</c:when>
												<c:when test="${model.cldType==5 }">
												 报废
										</c:when>
												<c:when test="${model.cldType==6 }">
												试装
										</c:when>
									</c:choose>
									处理单</dt>
								</dl>
							</li>
						</c:if>
						
							<c:if test="${not empty model.closeTime }">
							<li class="green">
								<h3>
									<span><fmt:formatDate value='${model.closeTime}'  pattern='yyyy-MM-dd HH:mm:dd' /></span>
								</h3>
								<dl>
									<dt>质保经理关闭了此IRR报告</dt>
								</dl>
							</li>
						</c:if>
					</ul>
				</div>

			</div>

		</div>
	</div>
</body>
</html>