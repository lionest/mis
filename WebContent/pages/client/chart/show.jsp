<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<%@ taglib uri="http://www.htlab.com.cn/tags/wd" prefix="wd"%>
<html>
<head>
<meta charset="utf-8">
<title>中间件云平台</title>
<meta http-equiv="Pragma" approle="no-cache">
<meta http-equiv="cache-control" approle="no-cache">
<!--浏览终端-->
<meta http-equiv="X-UA-Compatible" approle="IE=edge,chrome=1">
<!--移动设备特性-->
<meta name="viewport" approle="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" approle="yes" />
<meta name="apple-mobile-web-app-status-bar-style" approle="black" />
<meta name="format-detection" approle="telephone=no"/>
<script type="text/javascript" src="${ctx }/resources/js/Charts/FusionCharts.js"></script>
<script>
window.onload = function() {
var ismobile = null;
ismobile = navigator.userAgent.match(/(iPad)|(iPhone)|(iPod)|(android)|(webOS)/i);
if(ismobile) {
//document.getElementById('r2').checked = true;
FusionCharts.setCurrentRenderer('javascript'); //flash
}
if('${renderer}'.length>0){
FusionCharts.setCurrentRenderer('${renderer}'); //flash
}
showChart();
}
var myChart1 = '';
function showChart() {
 myChart1 = new FusionCharts("${ctx }/resources/js/Charts/${swf}", "chartID1", "99%", "99%", "0", "0");
 //myChart1.setDataURL("${dataUrl}");
 myChart1.setChartData( "${dataUrl}" , "${dataFormat}");
//myChart1.setChartData('{"data":[{"issliced":"1","value":"0","lable":"传输代维资质"},{"issliced":"1","value":"0","lable":"基站代维资质"},{"issliced":"1","value":"1","lable":"综合覆盖资质"}],"styles":{"application":[{"toobject":"caption","styles":"CaptionFont"},{"toobject":"SubCaption","styles":"SubCaptionFont"}],"definition":[{"color":"666666","name":"CaptionFont","type":"font","size":"15"},{"bold":"0","name":"SubCaptionFont","type":"font"}]},"chart":{"animation":"1","formatnumberscale":"0","palette":"2","numberprefix":"","caption":"全省人员资质证书统计报表","startingangle":"125","pieslicedepth":"30"}}',"${dataFormat}");
//myChart1.setChartData('{"chart":{"caption":"SalesPerEmployeeforyear1996","palette":"2","animation":"1","formatnumberscale":"0","numberprefix":"$","pieslicedepth":"30","startingangle":"125"},"data":[{"label":"Leverling","value":"100524","issliced":"1"},{"label":"Fuller","value":"87790","issliced":"1"},{"label":"Davolio","value":"81898","issliced":"0"},{"label":"Peacock","value":"76438","issliced":"0"},{"label":"King","value":"57430","issliced":"0"},{"label":"Callahan","value":"55091","issliced":"0"},{"label":"Dodsworth","value":"43962","issliced":"0"},{"label":"Suyama","value":"22474","issliced":"0"},{"label":"Buchanan","value":"21637","issliced":"0"}],"styles":{"definition":[{"type":"font","name":"CaptionFont","size":"15","color":"666666"},{"type":"font","name":"SubCaptionFont","bold":"0"}],"application":[{"toobject":"caption","styles":"CaptionFont"},{"toobject":"SubCaption","styles":"SubCaptionFont"}]}}',"${dataFormat}");
 //myChart1.setChartDataUrl("${dataUrl}", "${dataFormat}");
 myChart1.render("chartdiv1");
}
</script>
</head>
<body>
  <div id="chartdiv1" style="">Loading...</div>
</body>
</html>