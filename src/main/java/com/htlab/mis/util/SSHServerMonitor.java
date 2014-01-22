package com.htlab.mis.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SSHServerMonitor {
	private static final String checkCpuCmd = "vmstat 2 3 |sed 1,3d | awk '{total+=$15} END {print 100-total/2}'";
	private static final String checkMemCmd = "grep Mem /proc/meminfo | awk '{print $2}'";
	private static final String checkDiskCmd = "df -kh | sed -e '/0K/d' |sed 1d";
	private static final String checkNetSendCmd="ifconfig eth0 | grep bytes | awk '{print $6}' | awk -F : '{print $2}'";
	private static final String checkNetRecvCmd="ifconfig eth0 | grep bytes | awk '{print $2}' | awk -F : '{print $2}'";
	
	public static String checkCpu(SSHExec ssh) {
		String content = ssh.exec(checkCpuCmd);
		float rate = 0;
		if (content == null || content.trim().length() == 0)
			return "-1";
		try{
			rate = Float.parseFloat(content.trim());
		}catch(Exception e)
		{
			return "-1";
		}
		return String.valueOf(rate);
	}

	public  static String checkMemory(SSHExec ssh) {
		String content = ssh.exec(checkMemCmd);
		if (content == null || content.trim().length() == 0)
			return "-1";
		String[] mems = content.split("\r\n");
		if (mems.length < 2)
			return "-1";
		float total = Float.parseFloat(mems[0].trim());
		float free = Float.parseFloat(mems[1].trim());
		float rate = (total-free)*100/total;
		return String.valueOf((int)rate);
	}

	public static String checkDisk( SSHExec ssh) {
		String content = ssh.exec(checkDiskCmd);
		if (content == null || content.trim().length() == 0)
			return "-1";
		List<String> tokens = Arrays.asList(StringUtils.split(content));
		String capacity = tokens.get(4);
		return capacity.replaceAll("%", "");
		
//			info.setFileSystem(tokens.get(i * 6));
//			info.setTotal(tokens.get(i * 6 + 1));
//			info.setUsed(tokens.get(i * 6 + 2));
//			info.setAvail(tokens.get(i * 6 + 3));
//			info.setCapacity(tokens.get(i * 6 + 4));
//			info.setMountOn(tokens.get(i * 6 + 5));
//			info.setUpdateTime(now);
	}
	
	public static String checkNet(SSHExec ssh) {
		String content = ssh.exec(checkNetSendCmd);
		if (content == null || content.trim().length() == 0)
			return "-1";
		Long rate = Long.parseLong(content.trim());
		
		content = ssh.exec(checkNetRecvCmd);
		if (content == null || content.trim().length() == 0)
			return "-1";
		rate += Long.parseLong(content.trim());
		return String.valueOf(rate);
	}
	
	
	public static void main(String[] args) {
		System.out.println(Long.parseLong("3295536214"));
	}
	
}
