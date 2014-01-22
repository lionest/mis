package com.htlab.mis.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class OSExecuteUtil
{
	/**
	 * <b>command。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param command
	 */
	public static String command(String command){
		try
		{
			Process process=new ProcessBuilder(Arrays.asList(command.split(" "))).start();
			//标准输入流
			BufferedReader result= new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s=result.readLine();
			while(s!=null){
				System.out.println(s);
				s=result.readLine();
			}
			//标准错误输入流
			BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			s=error.readLine();
			while(s!=null){
				System.err.println(s);
				s=error.readLine();
			}
			return s;
		} catch (Exception e)
		{
			//纠正
			if(!command.startsWith("CMD /C")){
				command("CMD /C "+command);
			}else{
				throw new RuntimeException(e.getMessage());
			}
			return "";
		}
	}
	public static void main(String[] args) {
		OSExecuteUtil.command("dir");
	}
}
