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
<script type="text/javascript" src="${ctx }/resources/js/Highcharts/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/Highcharts/js/highcharts.js"></script>

<script type="text/javascript">
$(function () {
	
    var chart;
    $(window).resize(function() 
    		{    
    		    chart.setSize(
    		       $(document).width()*0.9, 
    		       $(document).height()*0.9,
    		       false
    		    );   
    		});
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: '${showType}',
                spacingTop: 2,
                spacingBottom: 2,
                spacingLeft: 0,
                reflow: false
            },
            title: {
                text: '${title} '
                
            },
            xAxis: {
                categories: ${categories}
            },
            yAxis: {
                title: {
                    text: ' '
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            plotOptions: {
            	${showType}: {
                    dataLabels: {
                    	<c:if test="${'bar' eq showType}">
                    	align: 'right',
                        x: -10,
                        color: '#FFFFFF',
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif'
                        },
                        </c:if>
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                formatter: function() {
                        return this.x +': '+ this.y ;
                }
            },
            credits: {
                enabled: false
            },
           
            series: ${series}
        });
        
        chart.setSize(
 		       $(document).width()*0.9, 
 		       $(document).height()*0.9,
 		       false
 		    );  
    });
    
});
		</script>
		
</head>
<body>
  <div id="container" style="margin: 0 auto">Loading...</div>
</body>
</html>