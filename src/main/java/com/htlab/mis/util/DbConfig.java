package com.htlab.mis.util;

import java.util.Date;

import com.htlab.mis.common.BaseObject;

public class DbConfig extends BaseObject{
	private String host;
	private String dbname;
	private String dbuser;
	private String dbpwd;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getDbuser() {
		return dbuser;
	}
	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}
	public String getDbpwd() {
		return dbpwd;
	}
	public void setDbpwd(String dbpwd) {
		this.dbpwd = dbpwd;
	}
	public String getWindowsBackCmd() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append("cmd.exe /c mysqldump  -h ").append(host).append(" -u ")
				.append(dbuser).append(" --default-character-set=utf8  ")
				.append(" -p").append(dbpwd).append("  ").append(dbname)
				.append(" > ")
				.append(PropertiesLoader.getInstance().getProperty("db.back.path","/"))
				.append(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"))
				.append(".sql");
		
		return sb.toString();
	}
	public String getUnixBackShell() {
		StringBuffer sb = new StringBuffer();
		sb = sb.append("mysqldump  -h ").append(host).append(" -u ")
				.append(dbuser).append(" --default-character-set=utf8  ")
				.append(" -p").append(dbpwd).append("  ").append(dbname)
				.append(" > ")
				.append(PropertiesLoader.getInstance().getProperty("db.back.path","/"))
				.append(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"))
				.append(".sql");
		
		return sb.toString();
	}
	
	
	
}
