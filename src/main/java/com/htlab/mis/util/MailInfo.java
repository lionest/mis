package com.htlab.mis.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htlab.mis.common.BaseObject;

public class MailInfo extends BaseObject {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = -5957479472951203781L;
	private int id;
	private String subject;
	private String from;
	private String sentDate;
	private String flag; // 0 未读 1已读
	private boolean hasFile; // 是否有附件
	private String content;
	private String to;

	private List<MailFileInfo> fileList;

	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		String stringDate = format.format(date);
		return stringDate;
	}

	public MailInfo() {
	}

	public MailInfo(Message message) {
		try {
			this.id = message.getMessageNumber();
			this.sentDate = formatDate(message.getSentDate());
			this.from = convertAddress(message.getFrom());
			this.to = convertAddress(message.getAllRecipients());
			this.subject = message.getSubject();
			Flags flags = message.getFlags();
			if (flags.contains(Flags.Flag.SEEN))
				this.flag = "1";
			else {
				this.flag = "0";
			}

			this.hasFile = isPart(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MailInfo(Message message, String content, List<MailFileInfo> fileList) {
		try {
			this.id = message.getMessageNumber();
			this.sentDate = formatDate(message.getSentDate());
			this.from = convertAddress(message.getFrom());
			this.to = convertAddress(message.getAllRecipients());
			this.subject = message.getSubject();
			this.content = HtmlRegexpUtil.filterHtml(content);
			this.fileList = fileList;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String convertAddress(Address[] adrs) {
		if (adrs == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (Address adr : adrs) {
			String tmpAdr = adr.toString();
			if (tmpAdr.contains("<")) {
				tmpAdr = tmpAdr.substring(tmpAdr.indexOf("<") + 1, tmpAdr.indexOf(">"));
			}
			sb.append(tmpAdr).append(";");
		}
		return sb.toString();
	}

	public boolean isPart(Message message) {
		// 获取附件
		try {
			Object out_content = message.getContent();
			if (!(out_content instanceof Multipart)) {// 不是复合邮件体，既是不带附件。
				return false;
			}
			Multipart mp = (Multipart) out_content;
			int m = mp.getCount();
			for (int j = 0; j < m; j++) {
				Part part = mp.getBodyPart(j);
				String disposition = part.getDisposition();
				if (((disposition != null) && ((disposition.equalsIgnoreCase(part.ATTACHMENT)) || (disposition
						.equalsIgnoreCase(part.INLINE))))) {
					return true;
				} else if (part.isMimeType("MESSAGE/RFC822 ")) {
					return true;
				}
			}
		} catch (Exception e) {
			log.error("取得附件状态时异常： " + e.toString());
			return false;
		}
		return false;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<MailFileInfo> getFileList() {
		return fileList;
	}

	public void setFileList(List<MailFileInfo> fileList) {
		this.fileList = fileList;
	}

	public static void main(String[] args) {
		String adr = "\"tangjun@htlab.com.cn\"<tangjun@htlab.com.cn>";
		System.out.println(adr);
		if (adr.contains("<")) {
			adr = adr.substring(adr.indexOf("<") + 1, adr.indexOf(">"));
		}
		System.out.println(adr);

	}
}
