<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="p"%>
<%@ taglib uri="tags-wd" prefix="wd"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--  system values--%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="addr" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}" />

<%-- css file define --%>
<c:set var="main_css" value='<link href="${ctx }/resources/css/admin/main.css" rel="stylesheet" type="text/css">'  />
<c:set var="admin_css" value='<link href="${ctx }/resources/css/admin.css" rel="stylesheet" type="text/css">'  />
<c:set var="history_css" value='<link href="${ctx }/resources/css/history.css" rel="stylesheet" type="text/css">'  />
<%-- js file define --%>
<c:set var="jquery_js" value="<script type='text/javascript' src='${ctx}/resources/js/jquery-1.5.min.js'></script>"  />
<c:set var="util_js" value="<script type='text/javascript' src='${ctx}/resources/js/util.js'></script>"  />
<c:set var="validator_js" value="<script type='text/javascript' src='${ctx}/resources/js/jquery-validate.js'></script>"  />
<c:set var="ke_js" value='<script charset="utf-8" src="${ctx }/resources/js/kindereditor/kindeditor.js"></script><script charset="utf-8" src="${ctx }/resources/js/kindereditor/lang/zh_CN.js"></script>'  />
<c:set var="WdatePicker_js" value="<script type='text/javascript' src='${ctx }/resources/js/my97/WdatePicker.js'></script>"  />
<c:set var="cookie_js" value="<script type='text/javascript' src='${ctx }/resources/js/jquery.cookie.js'></script>"  />
<c:set var="toastr_js" value="<link href='${ctx }/resources/js/toastr/toastr.css' rel='stylesheet' type='text/css' /><script src='${ctx }/resources/js/toastr/toastr.js'></script>"  />
<c:set var="tableSort_js" value="<link href='${ctx }/resources/js/tablesorter/blue/style.css' rel='stylesheet' type='text/css'><script src='${ctx }/resources/js/tablesorter/jquery.tablesorter.min.js'></script>"  />
<c:set var="jquery_form_js" value="<script type='text/javascript' src='${ctx}/resources/js/jquery.form.js'></script>"  />
<c:set var="blockui_js" value="<script type='text/javascript' src='${ctx}/resources/js/jquery.blockUI.js'></script>"  />
<c:set var="main_js" value="<script type='text/javascript' src='${ctx}/resources/js/main.js'></script>"  />
<c:set var="jquery7_js" value="<script type='text/javascript' src='${ctx}/resources/js/jquery-1.7.2.min.js'></script>"  />


