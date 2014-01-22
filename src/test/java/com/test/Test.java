package com.test;

import java.text.ParseException;
import java.util.Date;

import com.htlab.mis.util.DateUtil;


public class Test {
	public static void main(String[] args) throws ParseException {
		Date d1 = DateUtil.getDateByString("20130426");
		System.out.println(d1.getTime());
		Date d = DateUtil.getDateByString("20131230");
		System.out.println(d.getTime());
	} 
}
