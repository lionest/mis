package com.htlab.mis.util;

import java.util.Calendar;

/**
 * 根据密码的加密、解密工具类加密文件访问的时间+ID，解析后判断该文件uri是否在指定小时时间内有效，返回该文件的时间和文件ID
 * 
 */
public class FileUrlUtil {

	private static final String PASSWORD = "WONDERTEK_IPAIPAI";
	private static final String DATEPATTERN = "HH";
	private static final String SPLIT_FLAG = "_";
	
	public static String encrypt(String data) {
		if (data == null || data.length() == 0) {
			return "";
		}
		String date = DateUtil.getDateTime(DATEPATTERN, Calendar.getInstance().getTime());
		
		return Encrypt.encrypt(date, PASSWORD)+SPLIT_FLAG+data;
	}
	
	private static String decrypt(String data) {
		if (data == null|| data.length() == 0 || data.indexOf(SPLIT_FLAG) == -1) {
			return "";
		}
		
		String tmp = data.substring(0,data.lastIndexOf("_"));
		return Encrypt.decrypt(tmp, PASSWORD)+data.substring(data.lastIndexOf("_"));
	}

	/***
	 * hour = -1 表示永不失效
	 * @param encryptData
	 * @param hour
	 * @return
	 */
	public static boolean isValid(String encryptData,int hour){
		if(hour == -1){
			return true;
		}
		String tmp = decrypt(encryptData);
		String data[] = tmp.split(SPLIT_FLAG);
		
		if(data.length != 2){
			return false;
		}
		
		int currHour = Calendar.getInstance().getTime().getHours();
		
		if(currHour > (Integer.valueOf(data[0])+hour+23)%23){
			return false;
		}
		return true;
	}
	
	/***
	 * 返回该加密串的真实值
	 * 
	 * hour = -1 表示永不失效
	 * @param encryptData
	 * @param hour
	 * @return
	 */
	public static String getSource(String encryptData,int hour){
		
		return encryptData;
		
//		if(!isValid(encryptData,hour)){
//			return "";
//		}
//		String tmp = decrypt(encryptData);
//		String data[] = tmp.split(SPLIT_FLAG);
//		
//		return data[1];
	}
	
	public static String converUrl(String serverBasePath,String fileId){
//		return  serverBasePath+"client/base/show.action?fileName="+encrypt(fileId);
		return  serverBasePath+"client/base/show.action?fileName="+fileId;
	}
	
	/**
	 * 测试代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String str2 = FileUrlUtil.encrypt("4f4dac6eb52d85f0d55021a3.png");
		System.out.println("加密后：" + str2);
		System.out.println("解密后：" + FileUrlUtil.getSource(str2,-1));
		System.out.println("解密后：" + FileUrlUtil.getSource(str2,2));
	}
}