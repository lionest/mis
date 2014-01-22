package com.htlab.mis.util;

/**
 * MD5加密工具
 * @author ZOUXIAOMING
 *
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class MD5Generator {

	private static InputStream createInputStream(File file) {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

	private static String generateMD5ForFile(File file) {
		InputStream is = createInputStream(file);
		byte[] buf = new byte[4096];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			int count = 0;
			while ((count = is.read(buf)) > 0) {
				md.update(buf, 0, count);
			}
			byte[] md5 = md.digest();
			is.close();
			buf = null;
			return md5HashToString(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String generateMD5(byte[] data, int offset, int len) {
		if (data.length == 0) {
			return "";
		}

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data, offset, len);
			byte[] md5 = md.digest();
			return md5HashToString(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String md5HashToString(byte[] input) {
		StringBuffer strBuf = new StringBuffer();
		int value;
		for (int i = 0; i < input.length; i++) {
			value = input[i];
			value = value > 0 ? value : value + 256;
			String str = Integer.toHexString(value);
			if (str.length() < 2)
				str = "0" + str;
			strBuf.append(str);
		}
		return strBuf.toString();
	}

	public static String getMD5Value(File file) {
		return generateMD5ForFile(file);
	}

	public static String getMD5Value(String src) {
		if (src == null) {
			return null;
		}
		if (src == "") {
			return "";
		}

		byte[] data = src.getBytes();
		return generateMD5(data, 0, data.length);
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public static String getFileCheckSum(String file) {
		MessageDigest md;
		InputStream is = null;
		DigestInputStream dis = null;
		try {
			md = MessageDigest.getInstance("MD5");
			is = new FileInputStream(file);
			dis = new DigestInputStream(is, md);
			while (dis.read() != -1)
				;
			byte[] digest = md.digest();
			return byteArray2Hex(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (dis != null) {
					dis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public static void main(String[] args) {
		/*
		 * String testFileMD5="bc391a736116fafce5b804d53129e169" ; String file = "e:\\test.txt"; long tmOrg =
		 * System.currentTimeMillis(); String md5 = getMD5Value(new File(file)); System.out.println("MD5: " + md5);
		 * System.out.println("the result of MD5 verify: " + md5.equals(testFileMD5)); long tmLast =
		 * System.currentTimeMillis() - tmOrg; System.out.println("Use time :" + tmLast + " ms");
		 */

		/*
		 * String str = "show.123456"; System.out.println(str + ":" + getMD5Value(str)); str = "show.pass";
		 * System.out.println(str + ":" + getMD5Value(str)); str = "chr.pass"; System.out.println(str + ":" +
		 * getMD5Value(str)); str = "pass_br"; System.out.println(str + ":" + getMD5Value(str));
		 */

		// 454a638ca3a49433b16af3a8b55dac48

//		String file = "d:\\project_xsgj.apk";
//		System.out.println(getFileCheckSum(file));
//		System.out.println(getMD5Value(new File(file)));
		System.out.println(getMD5Value("adx2013"));

	}
}
