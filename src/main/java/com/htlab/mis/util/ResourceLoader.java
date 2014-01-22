
package com.htlab.mis.util;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ResourceLoader {
	private String filename = "system_config"; //default config file
	private Locale locale = new Locale("en", "US");
	private ResourceBundle bundle = null;
	 
	private ResourceLoader(String filename, Locale locale) {
		if(filename != null) {
			this.filename = filename;
		}
		if(locale != null) {
			this.locale = locale;
		}

		bundle = ResourceBundle.getBundle(this.filename, this.locale);
	}
	
	public static synchronized ResourceLoader getInstance() {
		return new ResourceLoader(null, null);
	}
	
	public static synchronized ResourceLoader getInstance(String filename) {
		return new ResourceLoader(filename, null);
	}
	
	public static synchronized ResourceLoader getInstance(String filename, Locale locale) {
		return new ResourceLoader(filename, locale);
	}
	
	public synchronized void setLocale(Locale locale) {
		if(locale == null || this.locale.equals(locale))
			return;
		
		bundle = ResourceBundle.getBundle(filename, locale);
	}
	
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	public String getGBKString(String key) {
		return ISO2GBK(getString(key));
	}
	
	public Enumeration<String> getKeys() {
		return bundle.getKeys();
	}
	
	public Map<String, String> getKeyValueMap() {
		Enumeration<String> enu = this.getKeys();
		Map<String, String> map = new TreeMap<String,String>();
		while(enu.hasMoreElements()) {
			String key = enu.nextElement();
			map.put(key, this.getString(key));
		}
		
		return map;
	}
	
	public static String ISO2GBK(String str) {
		if(str == null)
			return null;
		try {
			str = new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String GBK2ISO(String str) {
		if(str == null)
			return null;
		try {
			str = new String(str.getBytes("GBK"), "ISO-8859-1");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}