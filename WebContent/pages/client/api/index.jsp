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
	<h3>mail接口</h3>
	<h3>邮件列表</h3>
	<pre  class="language-javascript"> 
	接口地址：http://192.168.1.28:8080/mis/client/mail/getMailList.html?mailServer=pop.139.com&mailUser=15755120841@139.com&mailPassword=77889997788999&pageSize=4& currentPage=1
	
	参数：
	mailServer 邮箱服务器
	mailUser 邮箱账号
	mailPassword 邮箱密码
	pageSize 每页邮件数目
	currentPage 当前页
	</pre>
	<h3>邮件详情</h3>
	<pre  class="language-javascript"> 
	接口地址：http://192.168.1.28:8080/mis/client/mail/getMailDetail.html?mailServer=pop.139.com&mailUser=15755120841@139.com&mailPassword=77889997788999&messageNumber=38
	
	参数：
	messageNumber 邮件列表返回的邮件的ID值
	
	返回结果：
	
	{

    "message": {
        "content": "139测试word1362990285023", --邮件内容
        "fileList": [
            {
                "filename": "/wd/1.doc", --附件名称
                "index": 1,--附件序号
                "previewUrl": "http://192.168.1.28:8080/mis/client/mail/previewMailFile.html?messageNumber=38&fileIndex=1&fileName=%2Fwd%2F1.doc", 
                --附件预览地址  预览时需将邮箱账号信息拼接在该地址后面：
                如http://192.168.1.28:8080/mis/client/mail/previewMailFile.html?messageNumber=38&fileIndex=1&fileName=%2Fwd%2F1.doc&mailServer=pop.139.com&mailUser=15755120841@139.com&mailPassword=77889997788999，
                返回结果会附件预览的图片地址。
                "url": "http://192.168.1.28:8080/mis/client/mail/getMailFile.html?messageNumber=38&fileIndex=1&fileName=%2Fwd%2F1.doc" 
                --附件的下载地址 同上需将邮箱信息附加到该地址后进行附件的下载：
                http://192.168.1.28:8080/mis/client/mail/getMailFile.html?messageNumber=38&fileIndex=1&fileName=%2Fwd%2F1.doc&mailServer=pop.139.com&mailUser=15755120841@139.com&mailPassword=77889997788999
            }
        ],
        "flag": null,
        "from": "13956906950@139.com;",
        "hasFile": false,
        "id": 38,
        "sentDate": "2013-03-11T16:24:45",
        "subject": "139测试word邮件内容2",
        "to": "15755120841@139.com;"
    }

}
	</pre>
	
	<h3>发送邮件</h3>
	<pre  class="language-javascript"> 
	接口地址：http://192.168.1.28:8080/mis/client/mail/sendMail.html?mailServer=smtp.139.com&mailUser=15755120841@139.com&mailPassword=77889997788999&mailFrom=15755120841@139.com&mailTo=tangjun@htlab.com.cn&subject=邮件标题&msgContent=邮件内容&files=1.doc;460.jpg
	
	参数：
	mailServer 邮箱服务器，139邮箱，发送服务器使用smtp.139.com
	mailUser 邮箱账号
	mailPassword 邮箱密码
	
	mailTo 邮件接收人
	mailFrom 邮件发送人
    subject  邮件主题
    msgContent 邮件内容
    mailbccTo 密送
    mailccTo 抄送
	files 发送邮件前，使用上传文件接口返回的文件值；多文件用分号；连接起来；请参考文件上传接口文档
	</pre>
	
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