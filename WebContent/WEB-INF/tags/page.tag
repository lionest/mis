<%@ tag body-content="empty" import="java.util.*, com.htlab.mis.util.PageInfo" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ attribute name="pageInfo" required="true" rtexprvalue="true" type="com.htlab.mis.util.PageInfo"%>
<%@ attribute name="url" required="true" rtexprvalue="true"%>
<%@ attribute name="params" required="false" rtexprvalue="true"%>
<%@ attribute name="msg" required="false" rtexprvalue="true"%>

<style type="text/css">
	/* 翻页 */
	.manu {  text-align:right; color:#333; clear:both; display:block;}
	.manu a { border:1px solid #ccc; background:#fff; padding: 2px 3px; margin:1px; color:#666; text-decoration:none;}
	.manu a:hover { border:1px solid #999; color:#333; text-decoration:none;}
	.manu .current { border: #0f6d9e 1px solid; background-color: #1aabf7; padding: 1px 3px; margin:1px; color:#fff;}
	.manu .disabled { border:1px solid #ccc; padding:2px 3px 2px 3px; color:#ccc; background:#eee;}
	.manu .disabled:hover { border:1px solid #ccc; color:#ccc; cursor:default;}
	.manu .invalid_page { border:1px solid #ccc; padding:2px 3px 2px 3px; color:#ccc; background:#eee;}
	.manu .invalid_page:hover { border:1px solid #ccc; color:#ccc; cursor:default;}
</style>

<!-- 分页控件 刷新分页 -->
<script type="text/javascript">
<!--
var url = "${url}";
var params = "${params}";

function gotoPage(page) {
	var tmp = "";
	if(url.indexOf("?") >= 0) {
		tmp = url + "&pageIndex=" + page;
	} else {
		tmp = url + "?pageIndex=" + page;
	}
	if(params != null && params.length > 0) {
		tmp = tmp + "&" + encodeURI(params);
	}
	//tmp = tmp + "&t=" + new Date().getTime();

	window.location.href = tmp;
}

function page_stopEvent(evt) {
	if(window.addEventListener) {
		evt.stopPropagation();
		evt.preventDefault();
	} else {
		evt.cancelBubble = true;
		evt.returnValue = false;
	}
}
//-->
</script>
 <s:if test="pageInfo.recordeCount >0">
 		<s:if test="pageInfo.pageCount>1"><!-- 当多于1页时候显示分页控件 -->
 		<ul class="breadcrumb" style="margin-top: 10px;">
		 	<div class="manu">
 			<span style="font-size:12px;float:left;">共&nbsp;${pageInfo.recordeCount}&nbsp;条&nbsp;&nbsp;当前显示&nbsp;${pageInfo.pageSize}&nbsp;条/共&nbsp;${pageInfo.pageCount}&nbsp;页</span>
	 		<a href="javascript: gotoPage(1)" class="number" title="首页">首页</a>
	 		 
		 	<% 
				Object obj = this.getJspContext().getAttribute("pageInfo");
				PageInfo map = (PageInfo)obj;
				
				int max = map.getPageCount(); //共有多少页
				int c = map.getPageIndex();//当前页码
				
				int i = 1; //循环起始编号
				int k = max; //循环结束编号
				
				if(max<=6){//每页显示6个
					
				}else if(c<=3){
					k = 6;
				}else if(c+5>=max){
					i = max-5;
				}else{
					i = c - 2;
					k = c + 3;
				}
				
				if(c <= 1) {
					out.print("<span class=\"invalid_page\">上一页</span>");
				} else {
					out.print("<a href='javascript: gotoPage(" + (c-1) + ")' class=\"number\" title=\"上一页\">上一页</a>");
				}
				
				if(i > 1) {
					out.print("<a href='javascript: gotoPage(1)' class=\"number\" title=\"1\">1</a>");
					out.print("<span class='dot'>...</span>");
				}
				
				for(; i<=k; i++){
					this.getJspContext().setAttribute("i", new Integer(i));
					
					if(i == c) {
						out.print("<a href='javascript: gotoPage(" + i + ")' class=\"number current\" title=\"" + i + "\">" + i + "</a>");
					} else {
						out.print("<a href='javascript: gotoPage(" + i + ")' class=\"number\" title=\"" + i + "\">" + i + "</a>");
					}
				}
				
				if(k < max) {
					out.print("<span class='dot'>...</span>");
					out.print("<a href='javascript: gotoPage(" + max + ")' class=\"number\" title=\"" + max + "\">" + max + "</a>");
				}
				
				if(c >= max) {
					out.print("<span class=\"invalid_page\">下一页</span>");
				} else {
					out.print("<a href='javascript: gotoPage(" + (c+1) + ")' class=\"number\" title=\"下一页\">下一页</a>");
				}
			%>
			
			<a href="javascript: gotoPage(${pageInfo.pageCount })" class="number" title="末页">末页</a>
			</div>
		</ul>
 		</s:if>
 		<s:else>
 			&nbsp;
 		</s:else>
</s:if>
<s:else>
	<div style="text-align:center; font-size:12px;">
		 ${empty msg?'没有符合条件的记录！':msg}
	</div>
</s:else>
