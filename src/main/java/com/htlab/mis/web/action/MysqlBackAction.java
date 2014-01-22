package com.htlab.mis.web.action;

import com.htlab.mis.util.DbConfig;
import com.htlab.mis.util.MysqlBackupUtil;
import com.htlab.mis.util.OSExecuteUtil;



//mysql 数据备份还原脚本
public class MysqlBackAction extends BaseAction{
	
	
	public String backUp(){
		DbConfig dc = MysqlBackupUtil.getConfig();
		log.debug("dbconfig: "+dc);
		String cmd = dc.getWindowsBackCmd();
		log.debug(cmd);
		String s = OSExecuteUtil.command(dc.getWindowsBackCmd());
		return JSON;
	}
	
}
