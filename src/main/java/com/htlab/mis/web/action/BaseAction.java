package com.htlab.mis.web.action;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.support.http.StatViewServlet;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.htlab.mis.common.Constants;
import com.htlab.mis.entity.User;
import com.htlab.mis.util.PageInfo;
import com.htlab.mis.util.PropertiesLoader;

/**
 * 所有Action的基类，用来做一个通用的动作
 * 
 */
public  class BaseAction extends ActionSupport implements ServletResponseAware, ServletRequestAware,
		SessionAware {
	private static final long serialVersionUID = 6426614401279137513L;
	public  Map<String, Object> resultMap = new HashMap<String, Object>(); // 用于返回JSON数据的Map
	protected Integer pageIndex = 1; // 用于标识当前请求的数据页数，从1开始
	protected Integer pageSize = Constants.PAGE_SIZE; // 分页数据默认大小 10
	protected PageInfo pageInfo; // 用于保存分页查询数据
	protected String message;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;

	/** 通用返回的结果页面 */
	protected String JSON = "json";
	protected String LIST = "list";
	protected String ADD = "add";
	protected String TOADD = "gotoAdd";
	protected String UPDATE = "update";
	protected String EDIT = "edit";
	protected String DOLIST = "doList";
	protected String NOPRIVILEGE = "noPrivilege";
	public Logger log = LoggerFactory.getLogger(this.getClass());

	
	/**
	 * 图片文件后缀
	 */
	protected static Set<String> imgFileExtSet = new HashSet<String>();
	static {
		imgFileExtSet.add("png");
		imgFileExtSet.add("jpg");
		imgFileExtSet.add("jpeg");
		imgFileExtSet.add("bmp");
		imgFileExtSet.add("gif");
	}
	
	/**
	 * 返回当前action所处的ActionContext
	 * 
	 * @return
	 */
	public ActionContext getContext() {
		return ActionContext.getContext();
	}

	/**
	 * 把数据保存到ActionContext对象中
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	public void putToSession(String key, Object value) {
		ActionContext.getContext().getSession().put(key, value);
	}

	/**
	 * 返回action所对应的request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 返回action所对应的response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 返回action所对应的session
	 * 
	 * @return
	 */
	public Map<String, Object> getSession() {
		return this.getContext().getSession();
	}

	/**
	 * 返回所请求的客户端IP
	 * 
	 * @return
	 */
	public String getClientIP() {
		return ServletActionContext.getRequest().getRemoteAddr();
	}

	/**
	 * 返回web资源所对应的绝对文件路径
	 * 
	 * @param file
	 * @return
	 */
	public String getApplicationContextRealPath(String file) {
		String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath(file);
		if (!path.endsWith(java.io.File.separator)) {
			path += java.io.File.separator;
		}
		return path;
	}

	/**
	 * 把字符串写处response
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void writeToResponse(String msg) throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(msg);
		out.flush();
	}

	/**
	 * 把登录的用户信息放入session中
	 * 
	 * @param user
	 */
	public void storeUserIntoSession(User user) {
		getRequest().getSession().setAttribute(Constants.SESSION_USER, user);
		getRequest().getSession().setAttribute(StatViewServlet.SESSION_USER_KEY, user);
	}

	public Integer getUserIdFromSession() {
		User user = this.getUserFromSession();
		if (user != null) {
			return user.getId();
		}

		return null;
	}

	/**
	 * 获取前端登陆用户对应产险用户信息
	 * 
	 * @return 产险用户信息
	 */
	public User getUserFromSession() {
		return (User) getRequest().getSession().getAttribute(Constants.SESSION_USER);
	}

	/**
	 * 返回当前请求的页数
	 * 
	 * @return
	 */
	public Integer getPageIndex() {
		return pageIndex == null || pageIndex < 1 ? 1 : pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @Description
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	public String json2Resp(Object obj) {
		PrintWriter out = null;
		getResponse().setContentType("application/json;charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			out = getResponse().getWriter();
			// String result = JSON.toJSONString(obj);
			// String result = JsonUtil.object2json(obj);
			String result = null;
			if (obj instanceof List) {
				JSONArray ja = JSONArray.fromObject(obj);
				result = ja.toString();
			} else {
				JSONObject jb = JSONObject.fromObject(obj);
				result = jb.toString();
			}

			log.debug(result);
			out.write(result);
		} catch (Exception e) {
			log.error("json2Resp error ", e);
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}

		return null;
	}

	public String str2Resp(String str) {
		PrintWriter out = null;
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		try {
			out = getResponse().getWriter();
			out.write(str);
		} catch (Exception e) {
			log.error("str2Resp error ", e);
		} finally {
			if (null != out) {
				out.close();
			}
		}
		
		return null;
	}
	
	/**直接跳转*/
	public String forward(){
		return SUCCESS;
	}

	/**
	 * 获取服务器地址
	 * 
	 * @return
	 */
	public String getBasePath() {
		String path = getRequest().getContextPath();
		String bases = getRequest().getHeader("X-FORWARDED-HOST");
		if (bases == null || bases.length() < 1) {
			bases = getRequest().getHeader("Host");
		}

		if (bases == null || bases.length() < 1) {
			bases = getRequest().getServerName() + ":" + getRequest().getServerPort();
		}
		String basePath = getRequest().getScheme() + "://" + bases + path;
		return basePath;
	}

	/**
	 * Convenience method to get the parameter
	 * 
	 * @return current request
	 */
	protected String getParameter(String key) {
		return getRequest().getParameter(key);
	}

	@SuppressWarnings("unchecked")
	protected Map<String, String> getParametersMap() {
		Enumeration<String> enu = getRequest().getParameterNames();
		Map<String, String> paraMap = new HashMap<String, String>();
		if (enu != null) {
			while (enu.hasMoreElements()) {
				String paraName = enu.nextElement();
				String paraValue = request.getParameter(paraName);
				paraMap.put(paraName, paraValue);
			}
		}
		return paraMap;
	}

	public String getDocumentRoot() {
		return PropertiesLoader.getInstance().getProperty("document.root", "/wd/htdocs/upload/");
	}

	public String getServerUrl() {
		return PropertiesLoader.getInstance().getProperty("server.docurl");
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
