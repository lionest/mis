package com.htlab.mis.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ApiUtil {
	public static Log log = LogFactory.getLog(ApiUtil.class);
	
/**
 *  * google 地理位置API
 * http://maps.googleapis.com/maps/api/geocode/json?latlng=31.28,107.45&sensor=true
 * 
 * 基站信息查询
 * http://apis.juhe.cn/cell/get?cell=3021&lac=54531&key=e08885cac8f1160822a89c96a1e88ed0
 * 
 * http://developer.baidu.com/map/apply-key.htm
 * baidukey = 436a13419a49773f6bc15fb5e057b61d
 * 
    地址解析：根据地址获取坐标

    http://api.map.baidu.com/geocoder?address=地址&output=输出格式类型&key=用户密钥&city=城市名
    逆地址解析：根据坐标获取地址

    http://api.map.baidu.com/geocoder?location=纬度,经度&output=输出格式类型&key=用户密钥
http://api.map.baidu.com/geocoder?output=xml&location=39.983424,%20116.322987&key=37492c0ee6f924cb5e934fa08c6b1676

 */
	public static final String BAIDU_KEY = "436a13419a49773f6bc15fb5e057b61d";
	public static String[] getAddressBylatlng(String lat,String lng){
		try {
			String result= HttpClientUtil.requestGet("http://api.map.baidu.com/geocoder?location="+lat+","+lng+"&output=xml&key="+BAIDU_KEY,30000 );
			log.debug("query address result : "+result);
			Map<String,Object> xmlResultMap = XmlParse.Dom2Map(result);
			Map<String,Object> searchResults = (Map<String, Object>) xmlResultMap.get("result");
			String location = (String) searchResults.get("formatted_address");
			Map<String,Object> addressComponent = (Map<String, Object>) searchResults.get("addressComponent");
			String province = (String) addressComponent.get("province");
			String city = (String) addressComponent.get("city");

			String[] rets = new String[]{province,city,location};
			
			return rets;
		} catch (Exception e) {
			log.error(" error：", e);
			return new String[]{"","",""};
		}
	}
	
	/**
	 * http://www.enjoyphp.com/2010/news/software/youdao-ip-mobile-api/
	 * 网易数据查询API，支持IP地址，手机号，身份证信息
	 * @param mobile
	 * @return
	 */
	public static String[] queryLocation(String mobile){
		try {
			String result= HttpClientUtil.requestGet("http://www.yodao.com/smartresult-xml/search.s?type=mobile&q="+mobile,30000 );
			log.debug("query mobile result : "+result);
			Map<String,Object> xmlResultMap = XmlParse.Dom2Map(result);
			Map<String,Object> searchResults = (Map<String, Object>) xmlResultMap.get("product");
			String location = (String) searchResults.get("location");
			return location.split(" ");
		} catch (Exception e) {
			log.error(" error：", e);
			return new String[]{"",""};
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String longitude = "117.24564562401324";
//		String latitude = "31.85213410944495";
//		String[] rets = getAddressBylatlng(latitude,longitude);
//
//		for(int i = 0;i<rets.length;i++){
//			System.out.println(rets[i]);
//		}
		
		long l = 411370708979L;
		System.out.println(l);
		
	}

}
