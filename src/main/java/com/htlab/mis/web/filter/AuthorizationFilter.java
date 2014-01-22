package com.htlab.mis.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htlab.mis.common.Constants;
import com.htlab.mis.entity.User;
import com.htlab.mis.util.IPRequest;
import com.htlab.mis.util.StringUtil;

public class AuthorizationFilter implements Filter {

	public Logger log = LoggerFactory.getLogger(this.getClass());

	private FilterConfig filterConfig;

	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setFilterConfig(filterConfig);
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rsp = (HttpServletResponse) response;

		final String uri = req.getRequestURI();
		log.debug("\n\n[request.url===> " + req.getRequestURL() + "]");

		// 访问私有页面时，检查用户是否登录。
		if (StringUtils.contains(uri, "/pri")) {
			final User user = (User) req.getSession().getAttribute(Constants.SESSION_ADMIN_USER);
			if (user == null) {   //没有登录 或者回话失效
				rsp.setContentType("text/html");
				rsp.setHeader("Cache-Control", "no-cache");
				rsp.setHeader("Pragma", "no-cache");
				rsp.setDateHeader("Expires", -1);
				PrintWriter out = rsp.getWriter();
				out.print("<html><script>window.open ('");
				out.print(req.getContextPath());
				out.print("/pages/admin/login.jsp?flag=1','_top');</script></html>");
				out.flush();
				out.close();
				return;
			}
		}

		rsp.setHeader("sessionid", req.getSession().getId());

		// 判断请求的查询字符串是否包含特殊字符，如果包含就跳转到登录页
		String queryString = req.getQueryString();
		if (StringUtil.isNotEmpty(queryString) && StringUtil.invalidQueryString(queryString)) {
			log.error("非法的请求参数:"+queryString);
			req.getSession().invalidate();
			rsp.sendRedirect("/mis/pages/admin/login.jsp");
			return;
		}

		// 打印请求参数集合，方便调试
		@SuppressWarnings("unchecked")
		Enumeration<String> enu = req.getParameterNames();
		StringBuffer params = new StringBuffer();
		if (enu != null) {
			while (enu.hasMoreElements()) {
				String paraName = enu.nextElement();
				if (paraName.toLowerCase().contains("password")) {
					continue;
				}
				String paraValue = request.getParameter(paraName);
				if (paraValue != null && paraValue.length() > 5000) {
					continue;
				}
				params.append(paraName).append(" = ").append(paraValue).append(" , ");
			}
		}

		// 打印请求头信息
		StringBuffer headers = new StringBuffer();
		@SuppressWarnings("unchecked")
		Enumeration<String> enames = req.getHeaderNames();
		while (enames.hasMoreElements()) {
			String name = enames.nextElement();
			String value = req.getHeader(name);
			headers.append(name).append(" = ").append(value).append(" , ");
		}

		log.debug("Access ip : [{}]", IPRequest.getIpAddress(req));
		log.debug("uri is :{}", uri);
		log.debug("header is :{}", headers.toString());
		log.debug("params is :{}", params.toString());

		chain.doFilter(req, rsp);
		}catch(Exception e){
			log.error("authorization filter error",e);
		}
		return;
	}

	
	public void destroy() {
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

}
