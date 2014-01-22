package com.htlab.mis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHExec {

	String hostname = "192.168.1.6";
	int    port     = 22;
	String username = "root";
	String password = "";
	String lang     = "UTF-8";
	
	
	
	public String getHostname() {
		return hostname;
	}



	public void setHostname(String hostname) {
		this.hostname = hostname;
	}



	public String getLang() {
		return lang;
	}



	public void setLang(String lang) {
		this.lang = lang;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}

	public  boolean ping(){
		Connection conn=null;
		try
		{
			conn = new Connection(hostname,port);		
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (!isAuthenticated ){
				return false;
			}
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(conn!=null)
			  conn.close();
		}
	}

	public String exec(String command){
		Connection conn=null;
		Session sess = null;
		try
		{
			
			conn = new Connection(hostname,port);		
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (!isAuthenticated )
				return "验证失败";

			 sess = conn.openSession();
			sess.execCommand(command);			
			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout,lang));
            StringBuffer buffer=new StringBuffer();
            
			while (true){
				String line = br.readLine();
				if (line == null)
					break;
				buffer.append(line).append("\r\n");
			}

			Integer status=sess.getExitStatus();
			if(status!=null && status!=0){
				return null;
			}
			if(buffer.indexOf("没有那个文件或目录")!=-1){
				return null;
			}
			return buffer.toString();
		}
		catch (IOException e){
            return "执行命令出现异常:"+e.getMessage();
		}finally{
			if(sess!=null)
			  sess.close();
			if(conn!=null)
			  conn.close();
		}
	}
	

}
