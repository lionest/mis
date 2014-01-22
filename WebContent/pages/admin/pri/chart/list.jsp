<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/pages/admin/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		${admin_css }${jquery_js }${util_js }
		<script type="text/javascript" src="${ctx }/resources/js/Highcharts/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/Highcharts/js/highcharts-more.js"></script>
</head>
<body>
<div class="so_main">
  <div id="container" style="height:200px">Loading...</div>
  <div id="container2" style="height:300px">Loading...</div>
</div>

<script>

$(function () {
	
	$.post("${ctx}/admin/pri/chart_showIrrChart.action",{},function(data){
		renderChart('container','每月拒收报告数趋势图','line',eval(data.categories),eval(data.series));
	});
	
	$.post("${ctx}/admin/pri/chart_showSupplierChart.action",{},function(data){
		renderChart('container2','供应商拒收报告数TOP10','bar',eval(data.categories),eval(data.series));
	});
	
	function renderChart(container,title,showType,categories,series){
		new Highcharts.Chart({
	        chart: {
	            renderTo: container,
	            type: showType,
	            spacingTop: 2,
	            spacingBottom: 2,
	            spacingLeft: 0,
	            reflow: false
	        },
	        title: {
	            text: title
	            
	        },
	        xAxis: {
	            categories: categories
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
	        	showType: {
	                dataLabels: {
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
	       
	        series: series
	    });
	}
});
</script>

</body>
</html>