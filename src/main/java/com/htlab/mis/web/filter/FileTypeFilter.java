/**
 * 
 */
package com.htlab.mis.web.filter;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.lang.StringUtils;

/**
 * @author rain
 * 
 */
public class FileTypeFilter implements FilenameFilter {
	private String suffix;

	/**
	 * 
	 */
	public FileTypeFilter(final String suffix) {
		this.suffix = suffix;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	
	public boolean accept(File arg0, String arg1) {
		return StringUtils.endsWith(arg1, suffix);
	}
}
