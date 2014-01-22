/**
 * 
 */
package com.htlab.mis.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author rain
 * 
 */
public class FilePreviewNameComparator implements Comparator<String> {

	public int compare(String arg0, String arg1) {
		final int index0 = Integer.parseInt(extractString(arg0));
		final int index1 = Integer.parseInt(extractString(arg1));

		if (index0 == index1) {
			return 0;
		} else if (index0 > index1) {
			return 1;
		}
		return -1;
	}

	private String extractString(final String input) {
		// a-28306126b55432ec6148335656f1ad79-6.jpg
		final String regEx = "-([0-9]*).jpg";
		final List<String> results = new ArrayList<String>();
		if (StringUtils.isEmpty(input)) {
			return StringUtils.EMPTY;
		}
		final Pattern p = Pattern.compile(regEx);
		final Matcher m = p.matcher(input);
		while (m.find()) {
			results.add(m.group(1));
		}

		if (results.size() > 0) {
			return results.get(0);
		}
		return StringUtils.EMPTY;
	}
}
