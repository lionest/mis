package com.htlab.mis.common;

import java.util.ResourceBundle;

/**
 * 定义全局常量类
 * 
 * 
 */
public class Constants {
	private static ResourceBundle resBundle = ResourceBundle.getBundle("sys-config");

	public static int PAGE_SIZE = Integer.parseInt(resBundle.getString("record.page.size"));

	public static final String SESSION_USER = "SESSION_USER";

	public static final String SESSION_ADMIN_USER = "SESSION_ADMIN_USER";

	public static final String CHECK_CODE = "CHECKCODE";


}
