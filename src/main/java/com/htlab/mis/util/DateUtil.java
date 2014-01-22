package com.htlab.mis.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {

	static Logger log = LoggerFactory.getLogger(DateUtil.class);
	public static final String TIME_PATTERN = "HH:mm:ss";
	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	private final static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
	public static SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	public static SimpleDateFormat yyyyMMddHHmmssStr = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat PLAYBILL_TIME_PATTERN = new SimpleDateFormat("yyyyMMdd HH:mm");
	public static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	public static String LAST_SECOND = "lastsecond";
	public static String FIRST_SECOND = "firstsecond";
	public static String PRECISION_MIN_LAST_SECOND = "PRECISION_MIN_LAST_SECOND";
	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("ParseException: " + pe);
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static String getFullDateTime(Date aDate) {
		return DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINESE).format(aDate);
	}

	public static java.sql.Date convertDateToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static java.sql.Timestamp convertDateToTimestamp(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	public static String getNowTime(Date date) {
		if (date == null) {
			return "";
		}
		return timeFormat.format(date);
	}

	public static String getDateTime(String sdate) {
		try {
			java.sql.Timestamp date = stringToTimestamp(sdate);
			return dateFormat.format(date);
		} catch (Exception e) {
			return sdate;
		}
	}

	/**
	 * 将14位数字类型yyyyMMddHHmmss时间转换为日期类型时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String stringDate = format.format(date);

		return stringDate;
	}
	
	/**
	 * 按指定的格式来格式化日期类型
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date, String format) {

		DateFormat sdf = new SimpleDateFormat(format);
		String stringDate = sdf.format(date);
		return stringDate;
	}

	public static java.sql.Timestamp stringToTimestamp(String timestampStr) {
		if (timestampStr == null || timestampStr.length() < 1)
			return null;
		return java.sql.Timestamp.valueOf(timestampStr);
	}

	/**
	 * 根据日期计算出所在周的日期，并返回大小为7的数组
	 * 
	 * @param date
	 * @return
	 */
	public static String[] getWholeWeekByDate(Date date) {
		String[] ss = new String[7];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0, j = 2; i < 6 && j < 8; i++, j++) {
			calendar.setTime(date);
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.set(Calendar.DAY_OF_WEEK, j);
			ss[i] = getFormatDate(calendar.getTime());
		}
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
		ss[6] = getFormatDate(calendar.getTime());
		return ss;
	}

	/**
	 * 返回格式 yyyyMMdd的日期格式
	 * 
	 * @param d
	 * @return
	 */
	public static String getFormatDate(Date d) {
		if (d == null)
			return null;
		return yyyyMMdd.format(d);
	}

	
	public static String formatLong2DateString(Long time) {
		if (time == null)
			return "";
		return yyyyMMddHHmmssStr.format(time);
	}

	/**
	 * 返回pattern规定的日期格式
	 * 
	 * @param d
	 * @param pattern
	 * @return
	 */
	public static String getFormatDate(Date d, String pattern) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * 返回yyyy的日期格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return yyyy.format(Calendar.getInstance().getTime());
	}

	public static Date getDateByString(String pattern) throws ParseException {
		return yyyyMMdd.parse(pattern);
	}

	public static Date getPlayBillTimeByPattern(String date) throws ParseException {
		return PLAYBILL_TIME_PATTERN.parse(date);
	}

	/**
	 * 返回格式 HH:MM
	 * 
	 * @param d
	 * @return
	 */
	public static String getDateTime(Date d) {
		return HHmm.format(d);
	}

	public static Date parseDate(String strDate, String pattern) {
		if (strDate == null || pattern == null) {
			return null;
		}

		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date parseDate(Date srcdate, String flag) {
		if (DateUtil.FIRST_SECOND.equals(flag)) {
			String dateStr = getFormatDate(srcdate) + "000000";
			return parseDate(dateStr, "yyyyMMddHHmmss");
		} else if (DateUtil.LAST_SECOND.equals(flag)) {
			String dateStr = getFormatDate(srcdate) + "235959";
			return parseDate(dateStr, "yyyyMMddHHmmss");
		} else if (DateUtil.PRECISION_MIN_LAST_SECOND.equals(flag)) {
			log.debug("before fomat dateStr=========={}"+srcdate);
			String dateStr = getFormatDate(srcdate,"yyyyMMddHHmm") + "59";
			log.debug("dateStr=========={}"+dateStr);
			return parseDate(dateStr, "yyyyMMddHHmmss");
		} else
			return srcdate;
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String convertDateFormat(final String dateStr,String formatStr) {
		
		if(dateStr == null || dateStr.length() == 0) return "";
		return DateUtil.getFullDateTime(DateUtil.convertStringToDate(formatStr, dateStr));
	}
	
	public static SimpleDateFormat JOURNEY_TIME_PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static Date str2Date(String time){
		if(time == null || time.length() < 16)
			return null;
		try {
			return JOURNEY_TIME_PATTERN.parse(time);
		} catch (Exception e) {
			return null;
		}
	}
	
	/*
	 * 
	 * 得到本日的开始时间
	 * 
	 */
	public static Date getDayBeginTime(Date date)
	{
		try {
			return dateFormat.parse(yyyy_MM_dd.format(date)+" 00:00:00");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * 
	 * 得到本日的结束时间
	 * 
	 */
	public static Date getDayEndTime(Date date)
	{
		try {
			return dateFormat.parse(yyyy_MM_dd.format(date)+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * 
	 * 得到本周的开始时间和结束时间
	 * 传入参数Calendar
	 * weekDate[0] 开始时间
	 * weekDate[1] 结束时间
	 * 周一是第一天，周日是最后一天
	 */
	public static Date[] getWeekBeginAndEndTime(Calendar cal) 
	{
		Date[] weekDate = new Date[2];
		try {
			Date firstDay;
			Date lastDay;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			int day_of_week = 0;// 当日是本周的第几天
			
			// 根据当前日期计算出本期的起始时间firstDay、lastDay
			if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
				day_of_week = 7;
			} else {
				day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
			}
			
			cal.add(Calendar.DATE, -day_of_week + 1);
			firstDay = sdfDate.parse(sdf.format(cal.getTime()) + " 00:00:00");// 本周第一天
			
			cal.add(Calendar.DATE, 6);
			lastDay = sdfDate.parse(sdf.format(cal.getTime()) + " 23:59:59");// 本周最后一天
			
			weekDate[0] = firstDay;
			weekDate[1] = lastDay;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return weekDate;
	}
	
	/*
	 * 
	 * 得到本月的开始时间和结束时间
	 * 传入参数Calendar
	 * weekDate[0] 开始时间
	 * weekDate[1] 结束时间
	 * 
	 */
	public static Date[] getMonthBeginAndEndTime(Calendar cal)
	{
		Date[] dateArray = new Date[2];
		try {
			Date firstDay;
			Date lastDay;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			
			int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int min = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
			
			cal.set(Calendar.DAY_OF_MONTH,min);
			firstDay = sdfDate.parse(sdf.format(cal.getTime()) + " 00:00:00");// 本月第一天
			
			cal.set(Calendar.DAY_OF_MONTH,max);
			lastDay = sdfDate.parse(sdf.format(cal.getTime()) + " 23:59:59");// 本月最后一天
			
			dateArray[0] = firstDay;
			dateArray[1] = lastDay;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateArray;
		
	}
	
	/*
	 * 
	 * 得到本个季度的开始时间和结束时间
	 * 传入参数Calendar 1月 4月 7月 10月
	 * weekDate[0] 开始时间 1月
	 * weekDate[1] 结束时间
	 * 
	 */
	public static Date[] getSeasonBeginAndEndTime(Calendar cal)
	{
		Date[] dateArray = new Date[2];
		try {
			Date firstDay;
			Date lastDay;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			int min = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH,min);
			firstDay = sdfDate.parse(sdf.format(cal.getTime()) + " 00:00:00");// 本季度第一天
			
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+2);
			int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH,max);
			lastDay = sdfDate.parse(sdf.format(cal.getTime()) + " 23:59:59");// 本季度最后一天
			
			dateArray[0] = firstDay;
			dateArray[1] = lastDay;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateArray;
		
	}
	
	
	/*
	 * 
	 * 得到本年的开始时间和结束时间
	 * 传入参数Calendar
	 * weekDate[0] 开始时间
	 * weekDate[1] 结束时间
	 * 
	 */
	public static Date[] getYearBeginAndEndTime(Calendar cal)
	{
		Date[] dateArray = new Date[2];
		try {
			Date firstDay;
			Date lastDay;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			
			int max = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
			int min = cal.getActualMinimum(Calendar.DAY_OF_YEAR);
			
			cal.set(Calendar.DAY_OF_YEAR,min);
			firstDay = sdfDate.parse(sdf.format(cal.getTime()) + " 00:00:00");// 本月第一天
			
			cal.set(Calendar.DAY_OF_YEAR,max);
			lastDay = sdfDate.parse(sdf.format(cal.getTime()) + " 23:59:59");// 本月最后一天
			
			dateArray[0] = firstDay;
			dateArray[1] = lastDay;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateArray;
		
	}
	
	/***
	 * 计算两个日期包含的月份，返回月份按升序排列
	 * @param date1 < date2
	 * @return
	 */
	public static List<Date> getContainsMonths(Date date1, Date date2){   
		List<Date> list = new ArrayList<Date>();
		try{
			
			Calendar objCalendarDate1 = Calendar.getInstance();   
			objCalendarDate1.setTime(date1);   
			
			Calendar objCalendarDate2 = Calendar.getInstance();   
			objCalendarDate2.setTime(date2);
			
			
			if (objCalendarDate1.after(objCalendarDate2)){   
				Calendar temp = objCalendarDate1;   
				objCalendarDate1 = objCalendarDate2;   
				objCalendarDate2 = temp; 
			}
			
			int ca1Year = objCalendarDate1.get(Calendar.YEAR);
			int ca1Month = objCalendarDate1.get(Calendar.MONTH);
			
			int ca2Year = objCalendarDate2.get(Calendar.YEAR);
			int ca2Month = objCalendarDate2.get(Calendar.MONTH);
			int countMonth = 0;//这个是用来计算得到有多少个月时间的一个整数
			if(ca1Year==ca2Year){
				countMonth = ca2Month - ca1Month;
			}else{
				countMonth = (ca2Year - ca1Year)*12 + (ca2Month - ca1Month);
			}
			Calendar tmpCal = objCalendarDate1;
			list.add(getMonthBeginAndEndTime(tmpCal)[0]);
			for(int count = 1;count<=countMonth && (tmpCal.before(objCalendarDate2) || tmpCal.equals(objCalendarDate2));count++){
				tmpCal.set(Calendar.MONTH, tmpCal.get(Calendar.MONTH)+1);
				tmpCal.set(Calendar.DAY_OF_MONTH, 1);
				list.add(getMonthBeginAndEndTime(tmpCal)[0]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;   
	} 
	
	/***
	 * 计算指定日期包含的前几个月份月份，返回月份按升序排列
	 * @param date1 < date2
	 * @return
	 */
	public static List<Date> getBeforeMonths(Date date, int month){   
        List<Date> list = new ArrayList<Date>();
        try{
        	
            Calendar objCalendarDate1 = Calendar.getInstance();   
            objCalendarDate1.setTime(date);
            objCalendarDate1.set(Calendar.MONTH, objCalendarDate1.get(Calendar.MONTH)-month+1);
   
            Calendar objCalendarDate2 = Calendar.getInstance();   
            objCalendarDate2.setTime(date);   
   
            list = getContainsMonths(objCalendarDate1.getTime(),objCalendarDate2.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;   
	}
	
	/**返回指定日期的前多少天的日期数组*/
	public static List<Date> getBeforeDays(Date date, int days){
		List<Date> list = new ArrayList<Date>();
		try{
        	
            Calendar objCalendarDate1 = Calendar.getInstance();   
            for(int i=days;i>0;i--){
            	objCalendarDate1.setTime(date);
            	objCalendarDate1.set(Calendar.DATE, objCalendarDate1.get(Calendar.DATE)-i+1);
            	list.add(objCalendarDate1.getTime());
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;   
	}
	
	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + " 天 " + hours + " 时 " + minutes + " 分 "
				+ seconds + " 秒 ";
	}
	/**
	 * 
	 * @param begin 时间段的开始
	 * @param end	时间段的结束
	 * @return	输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author fy.zhang
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}
	
	public static void main(String[] args) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		/*cal.add(Calendar.MONTH, 1);
		Date[] arys=DateUtil.getMonthBeginAndEndTime(cal);
		System.out.println(DateUtil.formatDate(arys[0], "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateUtil.formatDate(arys[1], "yyyy-MM-dd HH:mm:ss"));*/
		/*List<Date> list=DateUtil.getBeforeDays(new Date(), 7);
		for(Date d:list){
			System.out.println(DateUtil.formatDate(d));
		}*/
		
		cal.set(2014,5, 1);
//		cal.setTimeInMillis(1388332800000L);
		System.out.println(DateUtil.formatDate(cal.getTime()));
		System.out.println(cal.getTimeInMillis());
	//	System.out.println(DateUtil.formatDate(DateUtil.getSeasonBeginAndEndTime(cal)[1]));
		System.out.println(DateUtil.formatDate(DateUtil.getMonthBeginAndEndTime(cal)[1]));
		
	}

}
