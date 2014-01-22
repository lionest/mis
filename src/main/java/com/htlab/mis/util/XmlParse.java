package com.htlab.mis.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
* @ClassName: XmlParse 
* @Description: 解析XML字符串，转换成Map对象
* @author zouxiaoming
* @date Feb 28, 2012 10:40:37 AM 
*
 */
public class XmlParse {

	/**
	 * 解析一个XML字符串
	 * @param xml
	 * @return  解析字符串后形成的Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> Dom2Map(String xml) {

		if (StringUtil.isEmpty(xml)) {
			return new HashMap<String, Object>();
		}
		Document doc = null;
		Element element = null;
		try {
			doc = DocumentHelper.parseText(xml);
			element = doc.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return Dom2Map(element);
	}
	
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	public static void main(String[] args) {

		// 下面是需要解析的xml字符串例子
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF+8\" ?> "
				+ "<allresult version=\"1.0\">"
				+ "<vtype><![CDATA[ xxxxx]]></vtype>"
				+ "<ptype><![CDATA[ 1]]></ptype>" + " <searchresults>"
				+ "<weatherinfo>" + "<status><![CDATA[ 0]]></status>"
				+ "<total><![CDATA[ 3]]></total>"
				+ "<count><![CDATA[ 3]]></count>" + "<resultlist>" + "<result>"
				+ "<seqnum><![CDATA[ 1]]></seqnum>"
				+ "<provid><![CDATA[ 10000000]]></provid>"
				+ "<cityid><![CDATA[ 10000000]]></cityid>"
				+ "<cname><![CDATA[ 北京市]]></cname>"
				+ "<weatdate><![CDATA[ 2010+07+12]]></weatdate>"
				+ "<cdate><![CDATA[ 07月12日周一]]></cdate>"
				+ "<weather><![CDATA[ 雷阵雨]]></weather>"
				+ "<weathervane><![CDATA[ 无持续风向]]></weathervane>"
				+ "<windpower><![CDATA[ 微风]]></windpower>"
				+ "<temperature><![CDATA[ 最低气温23度,最高气温29度]]></temperature>"
				+ "<addtime><![CDATA[ 2010+07+12 09:10:23]]></addtime>"
				+ "</result>" + "<result>" + "<seqnum><![CDATA[ 2]]></seqnum>"
				+ "<provid><![CDATA[ 10000000]]></provid>"
				+ "<cityid><![CDATA[ 10000000]]></cityid>"
				+ "<cname><![CDATA[ 北京市]]></cname>"
				+ "<weatdate><![CDATA[ 2010+07+13]]></weatdate>"
				+ "<cdate><![CDATA[ 07月13日周二]]></cdate>"
				+ "<weather><![CDATA[ 阵雨]]></weather>"
				+ "<weathervane><![CDATA[ 无持续风向]]></weathervane>"
				+ "<windpower><![CDATA[ 微风]]></windpower>"
				+ "<temperature><![CDATA[ 最低气温24度,最高气温31度]]></temperature>"
				+ "<addtime><![CDATA[ 2010+07+12 09:10:23]]></addtime>"
				+ "</result>" + "</resultlist>" + "</weatherinfo>"
				+ "</searchresults>" + "</allresult>";

		Map<String, Object> result = Dom2Map(xmlString);
		System.out.println(result.get("vtype"));
	}

}