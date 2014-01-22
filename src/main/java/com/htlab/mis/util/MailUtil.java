package com.htlab.mis.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {

	public Logger log = LoggerFactory.getLogger(this.getClass());

	// 连接pop3服务器的主机名、协议、用户名、密码
	private String mailServer = "";
	private String protocol = "pop3";
	private String user = "";
	private String pwd = "";

	private boolean isDebug = false;

	public MailUtil(String mailServer, String user, String pwd) {
		this.mailServer = mailServer;
		this.user = user;
		this.pwd = pwd;
	}

	/** 获取最新的邮件列表 */
	public Map getMailList(int currentPage, int pageSize) {
		Session session = getSession();
		Store store = null;
		Folder folder = null;
		HashMap resultMap = new HashMap();
		resultMap.put("mailCount", 0);
		resultMap.put("mailList", new ArrayList());
		try {
			// 利用Session对象获得Store对象，并连接pop3服务器
			store = session.getStore();
			store.connect(mailServer, user, pwd);

			// 获得邮箱内的邮件夹Folder对象，以"读-写"打开
			folder = store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);
			int total = folder.getMessageCount();
			resultMap.put("mailCount", total);
			
			int start = total - (currentPage * pageSize) + 1;
			boolean isOutArray = false;
			if (start <= 0) {
				if((0-start) < pageSize){
					start = 1;
					isOutArray = true;
				}else{
					return resultMap; 
				}
			}
			int end = start + pageSize - 1;
			if(isOutArray){
				end = total - ((currentPage-1) * pageSize) ;
			}
			if (end > total) {
				end = total;
			}
			log.debug("start={},end={}", start, end);
			// 指定邮件范围
			Message[] messages = folder.getMessages(start, end);
			
			resultMap.put("mailList", convertMessages(messages));
			return resultMap;
		} catch (Exception e) {
			log.error("get mail list error " + e);
		} finally {
			try {
				// 释放资源
				if (folder != null)
					folder.close(true);
				if (store != null)
					store.close();
			} catch (Exception e) {
				log.error("get mail list release resource error " + e);
			}
		}
		return resultMap;
	}

	private List<MailInfo> convertMessages(Message[] messages) throws Exception {
		List<MailInfo> mailList = new ArrayList<MailInfo>();
		int mailCounts = messages.length;
		log.debug("getMailList size=" + mailCounts);
		for (int i = mailCounts-1; i >= 0; i--) {
			mailList.add(new MailInfo(messages[i]));
		}
		return mailList;
	}

	private Session getSession() {
		// 创建一个有具体连接信息的Properties对象
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", protocol);
		props.setProperty("mail.pop3.host", mailServer);
		props.setProperty("mail.mime.parameters.strict", "false");
		props.setProperty("mail.pop3.disabletop", "true");
		// 使用Properties对象获得Session对象
		Session session = Session.getInstance(props);
		session.setDebug(isDebug);
		return session;
	}

	/** 解析邮件内容 */
	public MailInfo getMessage(int messageNumber, String filedownUrl,String previewUrl){
		Session session = getSession();
		Store store = null;
		Folder folder = null;
		try {
			// 利用Session对象获得Store对象，并连接pop3服务器
			store = session.getStore();
			store.connect(mailServer, user, pwd);

			// 获得邮箱内的邮件夹Folder对象，以"读-写"打开
			folder = store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);

			Message message = folder.getMessage(messageNumber);
			Object content = message.getContent();
			StringBuffer contentSb = new StringBuffer();
			List<MailFileInfo> fileList = new ArrayList<MailFileInfo>();
			if (content instanceof MimeMultipart) {
				MimeMultipart multipart = (MimeMultipart) content;
				parseMultipart(contentSb, fileList,multipart, filedownUrl,previewUrl,messageNumber);
			}

			return new MailInfo(message, contentSb.toString(), fileList);

		} catch (Exception e) {
			log.error("get mail info error " + e);
		} finally {
			try {
				// 释放资源
				if (folder != null)
					folder.close(true);
				if (store != null)
					store.close();
			} catch (Exception e) {
				log.error("get mail info release recourserror " + e);
			}
		}
		return new MailInfo();
	}

	/** 查询邮件未读和已读条数 */
	public Map getMessageCount() {
		Session session = getSession();
		Store store = null;
		Folder folder = null;
		HashMap resultMap = new HashMap();
		try {
			// 利用Session对象获得Store对象，并连接pop3服务器
			store = session.getStore();
			store.connect(mailServer, user, pwd);

			// 获得邮箱内的邮件夹Folder对象，以"读-写"打开
			folder = store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);
			resultMap.put("messageCount", folder.getMessageCount());
//			resultMap.put("newMessageCount", folder.getNewMessageCount());
		} catch (Exception e) {
			log.error("get mail count error " + e);
		} finally {
			try {
				// 释放资源
				if (folder != null)
					folder.close(true);
				if (store != null)
					store.close();
			} catch (MessagingException e) {
				log.error("get mail count error " + e);
			}
		}
		return resultMap;
	}

	/**
	 * 对复杂邮件的解析
	 * 
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void parseMultipart(StringBuffer contentSb,List<MailFileInfo> fileList, Multipart multipart,
			String filedownUrl,String previewUrl,int messageNumber) throws MessagingException, IOException {
		int count = multipart.getCount();
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			String disposition = bodyPart.getDisposition();
			//System.out.println("bodyPart.getContentType() : " + bodyPart.getContentType());
			if (bodyPart.isMimeType("text/plain")) {
				contentSb.append(bodyPart.getContent().toString());
			} else if (bodyPart.isMimeType("text/html")) {
				contentSb.append(bodyPart.getContent().toString());
			} else if (bodyPart.isMimeType("multipart/*")) {
				Multipart mpart = (Multipart) bodyPart.getContent();
				parseMultipart(contentSb, fileList,mpart, filedownUrl,previewUrl,messageNumber);
			} else if (((disposition != null) && ((disposition.equalsIgnoreCase(bodyPart.ATTACHMENT)) || (disposition
					.equalsIgnoreCase(bodyPart.INLINE)))) || bodyPart.isMimeType("MESSAGE/RFC822 ")) {

				String fileName = bodyPart.getFileName();
				// contentSb.append("<br/>附件：<a href='" + filedownUrl +"?messageNumber="+messageNumber+"&fileIndex="+
				// idx
				// + "'>"+fileName+"</a>");

				MailFileInfo file = new MailFileInfo();
				if (fileName.indexOf("=?x-unknown?") >= 0) {

					fileName = fileName.replaceAll("x-unknown", "gbk"); // 将编码方式的信息由x-unkown改为gbk
				}
				fileName = MimeUtility.decodeText(fileName); // 再重新解码
				file.setFilename(fileName);
				file.setIndex(idx);
				file.setUrl(filedownUrl + "?messageNumber=" + messageNumber + "&fileIndex=" + idx + "&fileName="
						+ URLEncoder.encode(fileName,"UTF-8"));
				file.setPreviewUrl(previewUrl+"?messageNumber="+messageNumber+"&fileIndex="+idx+"&fileName="+URLEncoder.encode(fileName,"UTF-8"));
				fileList.add(file);
			}
		}
	}

	/**
	 * 将附件写到输出流
	 * 
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void handleMultipart(OutputStream os, Multipart multipart, int fileIndex) throws MessagingException,
			IOException {
		int count = multipart.getCount();
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			String disposition = bodyPart.getDisposition();
			// System.out.println("bodyPart.getContentType() : "+bodyPart.getContentType());
			if (bodyPart.isMimeType("multipart/*")) {
				Multipart mpart = (Multipart) bodyPart.getContent();
				handleMultipart(os, mpart, fileIndex);
			} else if (((disposition != null) && ((disposition.equalsIgnoreCase(bodyPart.ATTACHMENT)) || (disposition
					.equalsIgnoreCase(bodyPart.INLINE)))) || bodyPart.isMimeType("MESSAGE/RFC822 ")) {
				if (fileIndex == idx) {
					InputStream is = bodyPart.getInputStream();
					copy(is, os);
				}
			}
		}
	}

	public void handleAttachment(OutputStream os, int messageNumber, int fileIndex) {
		Session session = getSession();
		Store store = null;
		Folder folder = null;
		try {
			// 利用Session对象获得Store对象，并连接pop3服务器
			store = session.getStore();
			store.connect(mailServer, user, pwd);

			// 获得邮箱内的邮件夹Folder对象，以"读-写"打开
			folder = store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);

			Message message = folder.getMessage(messageNumber);
			log.debug("邮件内容" + message.getContent() + "\nContentType=" + message.getContentType());
			Object content = message.getContent();
			if (content instanceof MimeMultipart) {
				MimeMultipart multipart = (MimeMultipart) content;
				handleMultipart(os, multipart, fileIndex);
			}

		} catch (Exception e) {
			log.error("get mail file error ", e);
		} finally {
			try {
				// 释放资源
				if (folder != null)
					folder.close(true);
				if (store != null)
					store.close();
			} catch (MessagingException e) {
				log.error("get mail file error " + e);
			}
		}
	}

	/**
	 * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	private void copy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = is.read(bytes)) != -1) {
			os.write(bytes, 0, len);
		}
		if (os != null)
			os.close();
		if (is != null)
			is.close();
	}

	String messageContentMimeType = "text/html; charset=gb2312";

	/**
	 * 
	 * 发送e_mail，返回类型为int
	 * 
	 * 当返回值为0时，说明邮件发送成功
	 * 
	 * 当返回值为3时，说明邮件发送失败
	 */

	public int sendMail(String mailTo, String mailFrom, String subject, String msgContent, Vector attachedFilePathList,
			String mailbccTo, String mailccTo) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", mailServer);
		props.put("mail.smtp.auth", "true");
		MailAuthenticator auth = new MailAuthenticator(user, pwd);
		Session session = Session.getInstance(props, auth);

		session.setDebug(false);
		MimeMessage msg = new MimeMessage(session);
		Transport trans = null;
		try {
			Multipart mPart = new MimeMultipart();
			if (mailFrom != null&& mailFrom.length() > 0) {
				msg.setFrom(new InternetAddress(mailFrom));
			} else {
				log.debug("没有指定发送人邮件地址！");
				return 3;
			}

			if (mailTo != null&& mailTo.length() > 0) {
				InternetAddress[] address = InternetAddress.parse(mailTo);
				msg.setRecipients(Message.RecipientType.TO, address);
			} else {
				log.debug("没有指定收件人邮件地址！");
				return 3;
			}

			if (mailccTo != null && mailccTo.length() > 0) {
				InternetAddress[] ccaddress = InternetAddress.parse(mailccTo);
				msg.setRecipients(Message.RecipientType.CC, ccaddress);
			}

			if (mailbccTo != null && mailbccTo.length() > 0) {
				InternetAddress[] bccaddress = InternetAddress.parse(mailbccTo);
				msg.setRecipients(Message.RecipientType.BCC, bccaddress);
			}

			msg.setSubject(subject);
			InternetAddress[] replyAddress = { new InternetAddress(mailFrom) };
			msg.setReplyTo(replyAddress);

			// create and fill the first message part
			MimeBodyPart mBodyContent = new MimeBodyPart();
			if (msgContent != null)
				mBodyContent.setContent(msgContent, messageContentMimeType);
			else
				mBodyContent.setContent("", messageContentMimeType);

			mPart.addBodyPart(mBodyContent);

			// attach the file to the message

			if (attachedFilePathList != null) {
				for (Enumeration fileList = attachedFilePathList.elements(); fileList.hasMoreElements();) {
					String fileName = null;
					fileName = (String) fileList.nextElement();
					log.debug("fileName=" + fileName);
					MimeBodyPart mBodyPart = new MimeBodyPart();
					// attach the file to the message
					FileDataSource fds = new FileDataSource(fileName);
					mBodyPart.setDataHandler(new DataHandler(fds));
					
					fileName = fileName.substring(fileName.lastIndexOf("/")+1);
					mBodyPart.setFileName(MimeUtility.encodeText(fileName));
					mPart.addBodyPart(mBodyPart);
				}
			}

			msg.setContent(mPart);
			msg.setSentDate(new Date());

			// send the message
			trans = session.getTransport("smtp");
			try {
				trans.connect(mailServer, user, pwd);
			} catch (Exception e) {
				log.error("连接邮件服务器错误：", e);
				return 3;
			}
			trans.send(msg);
		} catch (Exception mex) {
			log.error("发送邮件误：", mex);
			return 3;
		} finally {

			try {
				if (trans != null && trans.isConnected())
					trans.close();
			} catch (Exception e) {
				log.error("发送邮件误：", e);
			}

		}
		return 0;
	}

	/**使用系统设置的账号发送邮件*/
	public static int sendSystemMail(String mailTo,String title,String message){
		String mailUser = PropertiesLoader.getInstance().getProperty("mail.user");
		String mailPassword = PropertiesLoader.getInstance().getProperty("mail.password");
		String mailServer = PropertiesLoader.getInstance().getProperty("mail.server");
		
		MailUtil mu = new MailUtil(mailServer, mailUser, mailPassword);
		return mu.sendMail(mailTo, mailUser, title, message,null,null,null);
	}
	
	public static void main(String[] argv) throws Exception {

		// sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		// System.out.println((new
		// String(dec.decodeBuffer("=?gb2312?B?MjIy0MK9qCBNaWNyb3NvZnQgT2ZmaWNlIFdvcmQgzsS1tS5kb2N4?="),"utf-8")));
		// String str = "=?gb2312?B?MjIy0MK9qCBNaWNyb3NvZnQgT2ZmaWNlIFdvcmQgzsS1tS5kb2N4?=";
		// if ( str.indexOf("=?x-unknown?") >=0 ){
		//
		// str = str.replaceAll("x-unknown","gbk" ); // 将编码方式的信息由x-unkown改为gbk
		// }
		// try{
		//
		// str = MimeUtility.decodeText( str ); //再重新解码
		// System.out.println(str);
		//
		// }catch( Exception e1){
		//
		//
		// }
		long start = System.currentTimeMillis();
		MailUtil sm = new MailUtil("smtp.139.com", "15755120841@139.com", "77889997788999");

		// 发邮件
		String mailFrom = MimeUtility.encodeText("李四")+"<15755120841@139.com>";
//		String mailTo = "\"123\"<tangjun@wondertek.com.cn.com.cn>,\"458\"<hitangjun@qq.com>";
		String mailTo = MimeUtility.encodeText("汤军")+"<tangjun@wondertek.com.cn>"
					+","+MimeUtility.encodeText("汤军QQ邮箱")+"<hitangjun@qq.com>";
		String mailccTo = MimeUtility.encodeText("CM126邮箱")+"<cm_ums@126.com>";
		String mailbccTo = "tangjun@mediahouse.com.cn";
		for(int i=0;i<1;i++){
		String msgContent = "139测试word邮件";
		String subject = "139测试word邮件内容";
		Vector attachedFilePathList = new Vector();
//		attachedFilePathList.add("d:/新建 Microsoft Office Excel 工作表.xlsx");
		sm.sendMail(mailTo, "", subject, msgContent, attachedFilePathList, mailbccTo, mailccTo);
		}
		System.out.println(System.currentTimeMillis() - start);
		// 邮件数目
		// System.out.println(sm.getMessageCount());

//		int i=1;
//		// 收邮件
//		 List<MailInfo> list = (List<MailInfo>)sm.getMailList(2,15).get("mailList");
//		 for(MailInfo m:list){
//		 System.out.println(i+++" : "+m);
//		 }
		// 邮件详情
		// System.out.println(sm.getMessage(17, "http://baidu.com"));

	}

}
