package com.htlab.mis.util;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MysqlBackupUtil {
	public static Log log = LogFactory.getLog(MysqlBackupUtil.class);
	
	public static DbConfig getConfig(){
		DbConfig dc = new DbConfig();
		Properties prop = PropertiesLoader.getInstance();
		String url = prop.getProperty("jdbc.url");
		dc.setHost(url.substring(url.indexOf("jdbc:mysql://")+13, url.indexOf(":3306")));
		dc.setDbname(url.substring(url.lastIndexOf("/")+1,url.indexOf("?")));
		dc.setDbuser(prop.getProperty("jdbc.username"));
		dc.setDbpwd(prop.getProperty("jdbc.password"));
		return dc;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getConfig());
	}

}
