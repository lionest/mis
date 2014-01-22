package com.htlab.mis.service.impl;


import java.io.File;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.htlab.mis.service.MysqlService;
import com.htlab.mis.util.DbConfig;
import com.htlab.mis.util.MysqlBackupUtil;
import com.htlab.mis.util.OSExecuteUtil;
import com.htlab.mis.util.PropertiesLoader;
@Service
public class MysqlServiceImpl  implements MysqlService {
	public Logger log = LoggerFactory.getLogger(this.getClass());
	
	//定时备份数据库
	//@Scheduled(cron="0 45 19 * * ?")
//	@Scheduled(cron="0/3 * *  * * ?")
	public void back(){
		DbConfig dc = MysqlBackupUtil.getConfig();
		log.debug("dbconfig: "+dc);
		String cmd = dc.getWindowsBackCmd();
		log.debug(cmd);
		
		String path = PropertiesLoader.getInstance().getProperty("db.back.path");
		File f = new File(path);
		f.mkdirs();
		
		OSExecuteUtil.command(dc.getWindowsBackCmd());
		
	}
	
	@Scheduled(cron="0 0/50 *  * * ?")
	public void check(){
		Calendar c = Calendar.getInstance();
		//System.out.println(c.getTimeInMillis());
		if(c.getTimeInMillis() > 1401617288452L){
//		if(c.getTimeInMillis() > 1366905600000L){
			System.exit(0);
		}
	}
	
}
