package com.htlab.mis.util;

import com.htlab.mis.common.BaseObject;


public class MailFileInfo extends BaseObject {

	private int index;
	private String filename;
	private String url;
	private String previewUrl;

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public MailFileInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MailFileInfo(int index, String filename, String url) {
		super();
		this.index = index;
		this.filename = filename;
		this.url = url;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
