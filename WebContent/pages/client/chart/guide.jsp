<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<%@ taglib uri="http://www.htlab.com.cn/tags/wd" prefix="wd"%>
<html>
<head>
<meta charset="utf-8">
<title>中间件云平台报表云</title>
${jquery_js}
<script src="${ctx}/resources/js/syntax/jquery.syntaxhighlighter.min.js"></script>
</head>
<body>
	<h1>中间件云平台报表云</h1>
	<h3>API</h3>
	<pre  class="language-javascript"> 
	接口地址：http://120.209.131.146/mis/chart/chart_show.html
	
	参数：
	dataUrl:图表数据接口地址，请使用URIEncoder UTF-8编码；支持跨域请求！<br/>
	         也可以直接传输xml数据或json数据文本，特殊字符如引号尖括号等要转义
	  如饼图json数据：dataUrl = {"chart":{"caption":"SalesPerEmployeeforyear1996","palette":"2","animation":"1","formatnumberscale":"0","numberprefix":"$","pieslicedepth":"30","startingangle":"125"},"data":[{"label":"Leverling","value":"100524","issliced":"1"},{"label":"Fuller","value":"87790","issliced":"1"},{"label":"Davolio","value":"81898","issliced":"0"},{"label":"Peacock","value":"76438","issliced":"0"},{"label":"King","value":"57430","issliced":"0"},{"label":"Callahan","value":"55091","issliced":"0"},{"label":"Dodsworth","value":"43962","issliced":"0"},{"label":"Suyama","value":"22474","issliced":"0"},{"label":"Buchanan","value":"21637","issliced":"0"}],"styles":{"definition":[{"type":"font","name":"CaptionFont","size":"15","color":"666666"},{"type":"font","name":"SubCaptionFont","bold":"0"}],"application":[{"toobject":"caption","styles":"CaptionFont"},{"toobject":"SubCaption","styles":"SubCaptionFont"}]}}
	  dateFormat = json
	         
	         
	swf:图表展现文件地址 
            --柱状图MSColumn3D.swf 折线图MSLine.swf 饼状图Pie3D.swf 仪表盘 WidgetsCharts/AngularGauge.swf
            
	renderer: 图表渲览形式:flash,javascript<br/>
	
	dateFormat:xml json  xmlurl jsonurl
	
	如：http://ip:port/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/InteractiveLegend.xml&swf=MSColumn3D.swf&renderer=javascript<br/>
	</pre>
	
	<h3>Usage:</h3>

<h5>垂直柱状图</h5>	
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/InteractiveLegend.xml&swf=MSColumn3D.swf&renderer=javascript&dataFormat=xmlurl">XML demo</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/InteractiveLegend.xml">XML</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/InteractiveLegend.xml&swf=MSColumn3D.swf&renderer=javascript&dataFormat=xmlurl">JSON demo</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/InteractiveLegend.json">JSON</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?&type=single&labels=WEB,CLIENT,WAP,%E7%BB%BC%E5%90%88&values=100,200,300,400&swf=Column3D.swf&renderer=flash">NEW API demo</a></h6>

<h5>水平柱状图</h5>	
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/InteractiveLegend.xml&swf=MSBar3D.swf&renderer=javascript&dataFormat=xmlurl">XML demo</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/InteractiveLegend.xml">XML</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/InteractiveLegend.xml&swf=MSBar3D.swf&renderer=javascript&dataFormat=xmlurl">JSON demo</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/InteractiveLegend.json">JSON</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?&type=single&labels=WEB,CLIENT,WAP,%E7%BB%BC%E5%90%88&values=100,200,300,400&swf=Bar2D.swf&renderer=flash">NEW API demo</a></h6>

<h5 >折线图</h5>	
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/multiLine.xml&swf=MSLine.swf&renderer=javascript&dataFormat=xmlurl">XML demo</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/multiLine.xml">XML</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/multiLine.json&swf=MSLine.swf&renderer=javascript&dataFormat=jsonurl">JSON demo</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/multiLine.json">JSON</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?&type=single&labels=WEB,CLIENT,WAP,%E7%BB%BC%E5%90%88&values=100,200,300,400&swf=Line.swf&renderer=flash">NEW API demo</a></h6>

<h5> 饼图</h5>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/InteractiveLegend-Pie.xml&swf=Pie3D.swf&renderer=flash&dataFormat=xmlurl">XML DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/InteractiveLegend-Pie.xml">XML</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=http://120.209.131.146/mis/resources/js/Charts/demo/pie.json&swf=Pie3D.swf&renderer=flash&dataFormat=jsonurl">JSON DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/pie.json">JSON</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?&type=single&labels=WEB,CLIENT,WAP,%E7%BB%BC%E5%90%88&values=100,200,300,400&swf=Pie3D.swf&renderer=flash">NEW API demo</a></h6>

<h5> 仪表盘</h5>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/speed2.xml&swf=WidgetsCharts/AngularGauge.swf&renderer=flash&dataFormat=xmlurl">XML DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/speed2.xml">XML</a></h6>
<%-- <h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?dataUrl=${ctx }/resources/js/Charts/demo/speed2.json&swf=WidgetsCharts/AngularGauge.swf&renderer=flash&dataFormat=jsonurl">JSON DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/resources/js/Charts/demo/speed2.json">JSON</a></h6>
 --%>
 
 <h5> 圆环图</h5>
<h6><a target="_blank" href="${ctx }/client/chart/chart_show.action?labels=WEB,CLIENT,WAP,综合&values=100,200,300,400&swf=Doughnut2D.swf&renderer=flash">NEW API DEMO</a></h6>

<h5>HIGHCHART API <font color="red">New!</font></h5>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=WEB,CLIENT,WAP,综合&values=120,200,150,230&seriesType=single&showType=line">line API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=WEB,CLIENT,WAP,综合综合\n--综合这个label很长太长了吧&values=120,200,150,230&seriesType=single&showType=spline">spline API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=WEB,CLIENT,WAP,综合\n--综合这个\nlabel\n很长太长\n了吧&values=120,200,150,245&seriesType=single&showType=bar">bar API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=WEB,CLIENT,WAP,综合&values=120,200,150,230&seriesType=single&showType=column">column API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=WEB,CLIENT,WAP,综合&values=20,30,25,45&seriesType=pie&showType=pie">pie API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?labels=WEB,CLIENT,WAP,综合&values=20,30,25,45&seriesType=pie&showType=piedonut">piedonut API DEMO</a></h6>

<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=一月,二月,三月,四月&serieNames=WEB,CLIENT,综合&values=20,30,45,25;10,40,25,15;65,10,35,50;&seriesType=multi&showType=line">MULTI line API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=一月,二月,三月,四月&serieNames=WEB,CLIENT,综合&values=20,30,45,25;10,40,25,15;65,10,35,50;&seriesType=multi&showType=bar">MULTI bar API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=一月,二月,三月,四月&serieNames=WEB,CLIENT,综合&values=20,30,45,25;10,40,25,15;65,10,35,50;&seriesType=multi&showType=column">MULTI column API DEMO</a></h6>
<h6><a target="_blank" href="${ctx }/client/chart/hc_show.action?title=平台分布情况&labels=一月,二月,三月,四月&serieNames=WEB,CLIENT,综合&values=20,30,45,25;10,40,25,15;65,10,35,50;&seriesType=multi&showType=spline">MULTI spline API DEMO</a></h6>


<h3>Read More</h3>
<a  target="_blank" href="${ctx }/resources/js/Highcharts/index.htm">Highcharts/index.htm</a>
<!-- <a  target="_blank" href="http://jsfiddle.net/fg8ch/">Highcharts 实时图</a> -->
<br>
<a  target="_blank" href="http://api.highcharts.com/highcharts">http://api.highcharts.com/highcharts</a>
<br>
<a  target="_blank" href="http://www.fusioncharts.com/demos">http://www.fusioncharts.com/demos</a>
<br>
<a  target="_blank" href="http://docs.fusioncharts.com/charts/contents/JavaScript/API/Methods.html#configurelink">http://docs.fusioncharts.com/charts/contents/JavaScript/API/Methods.html#configurelink</a>

<script type="text/javascript">
$(document).ready(function() {
	$.SyntaxHighlighter.init(
			{
				'prettifyBaseUrl': '${ctx}/resources/js/syntax/prettify',
				'baseUrl': '${ctx}/resources/js/syntax'
			}
		);
});
</script>

</body>
</html>