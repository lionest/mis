package com.htlab.mis.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.CharacterIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author ZOUXIAOMING
 * 
 */
public class StringUtil {
	public static HashMap<String, String> escapeCharMap = new HashMap<String, String>();
	static {
		escapeCharMap.put("<", "&lt;");
		escapeCharMap.put(">", "&gt;");
		escapeCharMap.put("&", "&amp;");
		escapeCharMap.put("\"", "&quot;");
		escapeCharMap.put("'", "&apos;");
	}

	public static boolean contains(List<String> list,String str){
    	if(list == null || list.size() ==0 )return false;
    	if(list.contains("*")) return true;
    	return list.contains(str);
    }
	
	/**
	 * 判断是否为合法的日期时间字符串
	 * 
	 * @param str_input
	 * @return boolean;符合为true,不符合为false
	 */
	public static boolean isDate(String str_input, String rDateFormat) {
		if (!isNull(str_input)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 判断对象是否为NULL
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(Object str) {
		if (str == null)
			return true;
		else
			return false;
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为NULL或者空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 将NULL转换成空字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String null2Str(Object value) {
		return value == null || "null".equals(value.toString()) ? "" : value.toString();
	}

	/**
	 * 将NULL转换成空字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String null2Str(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	/**
	 * String转换成Long
	 * 
	 * @param value
	 * @return
	 */
	public static Long stringToLong(String value) {
		Long l;
		value = null2Str(value);
		if ("".equals(value)) {
			l = 0L;
		} else {
			try {
				l = Long.valueOf(value);

			} catch (Exception e) {
				l = 0L;
			}
		}

		return l;
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是long类型整数
	 */
	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static int parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	/** 判断是否为时间 * */
	public static boolean isDate(String value) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String getText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append(" ");
			} else if (ch == '\n') {
				buf.append(" ");
			} else if (ch == '\t') {
				buf.append("    ");
			} else if (ch == ' ') {
				buf.append(" ");
			} else if (ch == '\'') {
				buf.append("\\'");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	// 清除特殊字符
	public static String getescapeText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("");
			} else if (ch == '\n') {
				buf.append("");
			} else if (ch == '\t') {
				buf.append("");
			} else if (ch == ' ') {
				buf.append("");
			} else if (ch == '\'') {
				buf.append("");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 
	 * 根据转义列表对字符串进行转义(escape)。
	 * 
	 * @param source
	 *            待转义的字符串
	 * 
	 * @param escapeCharMap
	 *            转义列表
	 * 
	 * @return 转义后的字符串
	 */

	public static String escapeCharacter(String source) {

		if (source == null || source.length() == 0) {

			return source;

		}

		if (escapeCharMap.size() == 0) {

			return source;

		}

		StringBuffer sb = new StringBuffer(source.length() + 100);

		StringCharacterIterator sci = new StringCharacterIterator(source);

		for (char c = sci.first();

		c != CharacterIterator.DONE;

		c = sci.next()) {

			String character = String.valueOf(c);

			if (escapeCharMap.containsKey(character)) {

				character = escapeCharMap.get(character);

			}

			sb.append(character);

		}

		return sb.toString();

	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String changeEnter(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("|");
			} else if (ch == '\n') {
				buf.append("|");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	// 截掉url左边的一级目录名,如/wap/news/index.xml -> /news/index.xml
	public static String trimLeftNode(String str) {
		if (str == null)
			return "";

		if (str.startsWith("/")) {
			int ind = str.indexOf("/", 1);
			if (ind > 0)
				return str.substring(ind);
		}
		return str;
	}

	public static String generatedUrl(int pageType, List<String> sourceList, String nodestr, int maxint) {
		List<String> nodeList = new ArrayList<String>();
		Random rmd = new Random();
		String rstr = "";
		Set<String> cpSet = new HashSet<String>();
		Set<Integer> distNum = new HashSet<Integer>();
		Set<String> distCp = new HashSet<String>();
		for (int i = 0; i < sourceList.size(); i++) {
			String tmpstr = sourceList.get(i);
			if (getSpstr(tmpstr, 1).equals(nodestr)) {
				nodeList.add(tmpstr);
				cpSet.add(getSpstr(tmpstr, 3));
			}
		}
		if (nodeList.size() > maxint) {
			for (int i = 0; i < maxint;) {
				int tmpint = rmd.nextInt(nodeList.size());
				String tmpstr = nodeList.get(tmpint);
				if ((distCp.add(getSpstr(tmpstr, 3)) || distCp.size() >= cpSet.size()) && distNum.add(tmpint)) {
					rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>" + getSpstr(tmpstr, 2) + "</a><br/>";
					i++;
				}
			}
		} else {
			for (int i = 0; i < nodeList.size(); i++) {
				String tmpstr = nodeList.get(i);
				rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>" + getSpstr(tmpstr, 2) + "</a><br/>";
			}
		}
		return rstr;
	}

	public static String getSpstr(String spstr, int level) {
		String rstr = "";
		for (int i = 0; i < level; i++) {
			if (spstr.indexOf("|*") == -1) {
				rstr = spstr;
				return rstr;
			} else {
				rstr = spstr.substring(0, spstr.indexOf("|*"));
			}
			spstr = spstr.substring(spstr.indexOf("|*") + 2, spstr.length());
		}
		return rstr;
	}

	/**
	 * put string array into map with a key, this method will return the detail information of array This method can be
	 * used in servlet for log many parameter array, and you can not worry about NULL POINT EXCEPTION see main method
	 * 
	 * @param map
	 * @return
	 */
	public static String ArrayToString(Map<String, String[]> map) {
		String res = "";
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			if (values != null) {
				res = res + key + "=[";
				for (String value : values) {
					res = res + value + ",";
				}
				if (res.length() - res.lastIndexOf(",") == 1) {
					res = res.substring(0, res.lastIndexOf(","));
				}
				res = res + "],";
			} else {
				res = res + key + "=NULL,";
			}

		}
		if (res.length() - res.lastIndexOf(",") == 1) {
			res = res.substring(0, res.lastIndexOf(","));
		}
		return res;
	}

	/**
	 * 对关键词过滤SQL特殊字符
	 * 
	 * @param name
	 * @return
	 */
	public static String parese(String name) {
		if (name == null || "".equals(name))
			return "";
		name = StringUtils.replace(name, ">", "");
		name = StringUtils.replace(name, "<", "");
		name = StringUtils.replace(name, "\"", ""); // '双引号
		name = StringUtils.replace(name, "'", "");// '单引号
		name = StringUtils.replace(name, " ", "");// '空格
		name = StringUtils.replace(name, "	", "");// 'tab键值
		name = StringUtils.replace(name, "\r", "");// '换行
		name = StringUtils.replace(name, "\n", "");// '回车
		name = StringUtils.replace(name, "?", "");// '回车
		return name;
	}

	// 版本规范是0~9999.0~9999.0~9999
	public static boolean checkVersion(String version) {
		if (version == null || "".equals(version) || version.split("\\.").length != 3) {
			return false;
		}
		try {
			String[] numbers = version.split("\\.");
			for (int i = 0; i < numbers.length; i++) {
				Long num = Long.parseLong(numbers[i]);
				if (num > 9999L || num < 0L)
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 返回限制字节长度的字符串
	 * 
	 * @param str
	 *            内容
	 * @param count
	 *            字节数
	 * @return 限制后的字符串
	 */
	public static String limitStringByByte(String str, int count) {
		if (str == null || count <= 0) {
			return "";
		}

		StringBuffer strBuf = new StringBuffer(count * 2);
		int len = 0;
		char ch = ' ';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			len += ch > 255 ? 2 : 1;
			if (len > count) {
				break;
			}
			strBuf.append(ch);
		}
		return strBuf.toString();
	}

	public static String filterBr(String content) {
		content = StringUtils.replace(content, "<br>", "");
		content = StringUtils.replace(content, "\n", "");
		return content;
	}

	public static String filterQuot(String str) {
		if (str == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer(str.length() + 20);
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch == '\"') {
				sb.append("&quot;");
			} else {
				sb.append(ch);
			}
		}

		return sb.toString();
	}

	public static String pureString(String content) {

		content = StringUtils.replace(content, "\n", "");
		content = StringUtils.replace(content, "&nbsp;", "");
		int right = 1;
		while (right > -1) {
			int left = 0;
			right = 0;
			int mid = 0;
			int lenth = content.length();
			right = content.indexOf(">", left);
			if (right > -1) {
				mid = content.lastIndexOf("<", right);
				if (mid > -1) {
					if ((mid == 0) & (right == lenth - 1)) {
						content = "";
					} else if (mid == 0) {
						content = content.substring(right + 1, lenth - 1);
					} else if (right == lenth - 1) {
						content = content.substring(0, mid - 1);
					} else {
						content = content.substring(0, mid - 1) + content.substring(right + 1, lenth - 1);
					}
				}
			}

		}

		return content;
	}

	public static String filterBr2(String content) {
		content = StringUtils.replace(content, "&nbsp;", "");
		return content;
	}

	public static String filterSpace(String content) {
		content = StringUtils.replace(content, "\t", " ");
		return content;
	}

	public static String convertToHtml(String content) {
		content = StringUtils.replace(content, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");// 替换跳格
		content = StringUtils.replace(content, " ", "&nbsp;");// 替换空格
		content = StringUtils.replace(content, "\n", "<br>");// 替换换行
		return content;
	}

	public static String convertToShow(String content) {
		content = StringUtils.replace(content, "<br>", "\n");// 替换换行
		return content;
	}

	public static final String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String replaceFirst(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;

			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 解析字符串 exp：str=zhangsan,lisi,wangwu; split(str,",")-->[zhangsan,lisi,wangwu]
	 * 
	 * @param string
	 * @param delim
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final String[] split(String string, String delim) {
		StringTokenizer token = new StringTokenizer(string, delim);
		String[] result = new String[token.countTokens()];
		List tmp = new ArrayList();
		while (token.hasMoreTokens()) {
			tmp.add(token.nextToken());
		}
		tmp.toArray(result);
		return result;
	}

	/**
	 * 返回值定为String[2]
	 */
	private static final String[] NullStringArray2 = new String[2];

	public static final String[] splitStringForOracle(String s) {
		if (s == null)
			return NullStringArray2;
		int length = s.length();
		if (length <= 650)
			return new String[] { s, null };
		String a = s.substring(0, 650);
		String b = s.substring(650, length);
		return new String[] { a, b };
	}

	public static Date getDateAfter(Date today, int days) {

		long todayT = today.getTime();
		long createTimeT = 0;
		long ldays = days;
		createTimeT = todayT + 24 * 60 * 60 * 1000 * ldays;
		Date ct = new Date();
		ct.setTime(createTimeT);
		return ct;
	}

	public static String getFilenameS(String input) {
		String f1 = input;
		int index1 = f1.lastIndexOf("/"); // /linux&winxp
		f1 = "s" + f1.substring(index1 + 1, f1.length());
		return f1;

	}

	public static String getFilenameB(String input) {
		String f1 = input;
		int index1 = f1.lastIndexOf("/"); // /linux&winxp
		f1 = f1.substring(index1 + 1, f1.length());
		return f1;

	}

	public static String getFilenameHead(String input) {
		String f1 = input;
		int index1 = f1.lastIndexOf("/"); // /linux&winxp
		int index2 = f1.lastIndexOf(".");
		f1 = f1.substring(index1 + 1, index2);
		return f1;

	}

	public static String getFileUrl(String input) {
		String f1 = input;
		int index1 = f1.lastIndexOf("/");// /linux&winxp

		f1 = f1.substring(0, index1 + 1);
		return f1;

	}

	/**
	 * 根据指定的参数limit来限制传入的文本信息，超出的部分以"..."代替
	 * 
	 * @param text
	 * @param limit
	 * @return
	 */
	public static String limitText(String text, int limit) {
		if (text == null) {
			return "";
		}
		if (text.length() > limit) {
			return text.substring(0, limit) + "...";
		}
		return text;
	}

	/**
	 * 按字节数来截取字符串，中文字母算2个字节，英文字节算1个字节。
	 * 
	 * @param str
	 *            要截取的源字符串
	 * @param byteCount
	 *            要取的字节数
	 * @return 返回截取的子串,如：substr("我ABC", 4)返回"我AB"，而 substr("我ABC汉DEF", 6)则返回"我ABC"
	 */
	public static String substr(String str, int byteCount) {
		if (str == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(str.length());
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			int codePoint = str.codePointAt(i);
			char ch = str.charAt(i);
			if (codePoint > 0xFF) { // 此为中文字符
				count += 2;
			} else {
				count++;
			}

			if (count > byteCount) {
				break;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 返回一个字符串所占用的字节个数
	 * 
	 * @param str
	 *            源字符串
	 * @return 字符串所占用的字节数，如"我ABC"占用5个字节
	 */
	public static int getByteCount(String str) {
		int len = 0;
		if (str == null) {
			return len;
		}

		for (int i = 0; i < str.length(); i++) {
			int codePoint = str.codePointAt(i);
			if (codePoint > 0xFF) { // 中文字符
				len += 2;
			} else {
				len++;
			}
		}
		return len;
	}

	/**
	 * 下载文件
	 * 
	 * @param file
	 * @param response
	 */
	public static void downloadFile(String file, HttpServletResponse response) {
		String filename = file.substring(file.lastIndexOf(File.separator) + 1);
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename); // no
																						// file
																						// path.

		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[4096];
			while (bis.available() > 0) {
				int len = bis.read(buf);
				bos.write(buf, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断url请求的查询字符串是不是包括的非法的字符
	public static boolean invalidQueryString(String url) {
		if (StringUtil.isEmpty(url)) {
			return true;
		}
		if (url.indexOf("%3E") > 0 || url.indexOf("%3C") > 0 || url.indexOf("&lt;") > 0 || url.indexOf("&gt;") > 0
				|| url.indexOf(">") > 0 || url.indexOf("<") > 0 || url.indexOf("||") > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是Email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String check = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Matcher matcher = Pattern.compile(check).matcher(email);
		return matcher.matches();
	}

	/**
	 * 格式化日期
	 * 
	 * @author zouxiaoming
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String str = null;
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			return null;
		}
		return str;
	}

	/**
	 * 解析字符串日期
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public Date parseDateStr(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	private static String zhPattern = "[\u4e00-\u9fa5]+";

	/**
	 * 用正则对中文字符串进行编码
	 * 
	 * @param str
	 *            被替换的字符串
	 * @param charset
	 *            字符集
	 * @return 替换好的
	 * @throws UnsupportedEncodingException
	 *             不支持的字符集
	 */
	public static String encode(String str, String charset) {
		Pattern p = Pattern.compile(zhPattern);
		Matcher m = p.matcher(str);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			try {
				m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		m.appendTail(b);
		return b.toString();
	}


	/**
	 * 格式化输出指定进制的数字和单位值 1.02时 = formatTimeScale("3660000")
	 * 
	 * @param srcValue
	 * @param scaleValue
	 * @param scaleUnit
	 * @return
	 */
	public static String formatTimeScale(String srcValue) {
		if (!StringUtil.isNumber(srcValue))
			return "N/A";
		return formatNumberScale(srcValue, new String[] { "1000", "60", "60","24" }, new String[] { "秒", "分", "时","天" });
	}

	/**
	 * 格式化输出指定进制的数字和单位值 1.02时 = formatNumberScale("3660000",new String[]{"1000","60","60"}, new String[]{"秒","分","时"})
	 * 
	 * @param srcValue
	 * @param scaleValue
	 * @param scaleUnit
	 * @return
	 */
	public static String formatNumberScale(String srcValue, String[] scaleValue, String[] scaleUnit) {
		try {
			if (!StringUtil.isNumber(srcValue))
				return "N/A";
			
			BigDecimal number = new BigDecimal(srcValue);
			int i = 0;
			BigDecimal divalValue = new BigDecimal(scaleValue[i]);
			number = average(number, divalValue);
			divalValue = new BigDecimal(scaleValue[i + 1]);
			while (number.compareTo(divalValue) >= 0 && i<scaleValue.length-1) {
				number = average(number, divalValue);
				divalValue = new BigDecimal(scaleValue[++i]);
			}
			return number.toString() + scaleUnit[i];

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * BigDecimal 求均,处理了null的情况
	 * 
	 * @param value
	 * @param count
	 * @return
	 */
	protected static BigDecimal average(BigDecimal value, BigDecimal count) {
		if (null == value || BigDecimal.ZERO.equals(value)) {
			return BigDecimal.ZERO;
		}
		return value.divide(count, 4, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/** * 生成一个随机数 六位需求,如果长度不够 就伪生成 * * @return String */
	public static String getRandomCode() {
		Random random = new Random();
		// 默认一个值 不至于程序崩溃
		String reslt = "585888";
		int ran = random.nextInt(999999);
		// 临时变量tempRan储存
		String tempRan = String.valueOf(ran);
		if (tempRan.length() == 6) {
			reslt = tempRan;
		}
		return reslt;
	}

	public static Long getVersionNumber(final String version) {
		String numberStr = "";
		if (StringUtils.contains(version, ".")) {
			final String[] numberArray = StringUtils.split(version, ".");
			for (String number : numberArray) {
				numberStr += StringUtils.leftPad(number, 4, "0");
			}
		} else {
			numberStr = StringUtils.leftPad(version, 4, "0");
		}
		return Long.valueOf(StringUtils.rightPad(numberStr, 12, "0"));
	}

	public static boolean compareVersion(final String current, final String max) {
		if (getVersionNumber(current) < getVersionNumber(max)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	public static String byteToHexString(byte[] tmp) {
		if (tmp == null) {
			throw new NullPointerException();
		}
		int len = tmp.length;
		char str[] = new char[len * 2];
		int i = 0;
		for (byte b : tmp) {
			str[i * 2] = hexDigits[b >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			str[i * 2 + 1] = hexDigits[b & 0xf]; // 取字节中低 4 位的数字转换
			i++;
		}
		return new String(str);
	}

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 得到一个Long值的指定长度的字符串形式
	 * 
	 * @param l
	 * @param len
	 * @return
	 */
	public static String getStringByAppointLen(Long l, int len) {
		return getStringByAppointLen(l.toString(), len, true);
	}

	/**
	 * 得到一个Integer值的指定长度的字符串形式 NOTE: 不足的前面添'0'
	 * 
	 * @param i
	 * @param len
	 * @return
	 */
	public static String getStringByAppointLen(Integer i, int len) {
		return getStringByAppointLen(i.toString(), len, true);
	}

	/**
	 * 得到一个String值的指定长度的字符串形式 NOTE: 不足的前面添'0'
	 * 
	 * @param s
	 * @param len
	 * @param cutHead
	 *            当s的长度大于len时，截取方式：true,截掉头部；否则从截掉尾部 例如getStringByAppointLen("12345",3,true) ---> "345"
	 * @return
	 */
	public static String getStringByAppointLen(String s, int len, boolean cutHead) {
		if (s == null || len <= 0) {
			s = "";
		}
		if (len > s.length()) {
			int size = len - s.length();
			StringBuffer sb = new StringBuffer();
			while (size-- > 0) {
				sb.append("0");
			}
			sb.append(s);
			return sb.toString();
		} else if (len == s.length()) {
			return s;
		} else {
			if (cutHead) {
				return s.substring(s.length() - len, s.length());
			} else {
				return s.substring(0, len);
			}
		}
	}

	/**
	 * 通过ID生成存储路径
	 * 
	 * @param id
	 * @return
	 */
	public static String getFileDirPathById(Long id) {
		String s = StringUtil.getStringByAppointLen(id, 12);
		StringBuffer sb = new StringBuffer();
		sb.append(s.substring(0, 3)).append("/").append(s.substring(3, 6)).append("/").append(s.substring(6, 9))
				.append("/").append(s.substring(9, 12)).append("/");
		return sb.toString();
	}
	
	/*** /wd/(38400)省佛子岭水库.doc(38400) == > _wd_(38400)省佛子岭水库.doc**/
	public static String replaceDocname(String filename){
		String str = "\\((\\d+)\\)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(filename);
		return m.replaceAll("").replaceAll("/", "_");
	}
	
	/**替换中文字符和空格字符*/
	public static String replaceSpecialChar(String str){
		if(isEmpty(str)){
			return "";
		}
		Pattern p = Pattern.compile("\r|\n");
		Matcher m= p.matcher(str);
		str = m.replaceAll(";");
		return str.trim().replaceAll("，", ",").replaceAll("（", "(").replaceAll("）", ")").replaceAll(";;", ";");
	}
	
	public static void main(String[] args) {
		System.out.println(formatTimeScale("411370305844"));
	}
}