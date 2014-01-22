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
	Highcharts.setOptions({
	       colors: ['#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4','#008ED6','#BBDC00','#D64646','#AFD8F8','#F6BD0F','#8BBA00','#FF8E46','#008E8E','#8E468E','#588526','#B3AA00','#9D080D','#A186BE','#CC6600','#FDC689','#ABA000','#F26D7D','#FFF200','#0054A6','#F7941C','#CC3300','#006600','#663300','#6DCFF6','#386520','#452871','#525698','#625863','#745632','#846100','#110066','#226633','#889966','#558833','#CFEFDD','#A67EF3','#DD44CC','#EE2211','#3FEF4A','#9CCFAA','#4AEECC']
	      });
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
                plotBackgroundColor: null,
                plotBorderWidth: null,
                spacingTop: 2,
                spacingRight: 0,
                spacingBottom: 2,
                spacingLeft: 0,
                reflow: false,
                plotShadow: false
            },
            title: {
                text: '${title} '
            },
            tooltip: {
        	    pointFormat: '{series.name}: <b>{point.percentage}%</b>',
            	percentageDecimals: 1
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        distance: -10,
                        formatter: function() {
                            return  this.y;
                        }
                    },
                    showInLegend: true
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 70,
                borderWidth: 0
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