/**
 * 利用JavaMail API来发送Email的工具类
 * 支持单发,群发及无限制数量的附件功能,并同时支持简单邮件和HTML两种格式的邮件内容
 * 支持抄送,密送功能
 * 必须的支持库: activation.jar, mail.jar
 * EMail邮件发送类
 * @author ZOUXIAOMING
 * @date: 2008-12-12
 */

package com.htlab.mis.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class JavaMailSender {
	private static final int SIMPLE_MAIL = 1;
	private static final int HTML_MAIL = 2;
	
	private String host; //邮件服务器地址
	private String username; //邮件发送服务器上的有效帐号
	private String password; //邮件发送服务器上的有效密码
	private boolean authenticated; //登录邮件发送服务器是否需要进行验证
	private String protocol = "smtp"; //邮件发送服务器的所有协议，默认为SMPT
	
	private Authenticator authenticator;
	private Transport transport;
	private Session session;
	private List<File> attachments = new ArrayList<File>(); //对应的邮件附件
	
	private String[] to; //邮件接收人数组
	private String[] cc; //邮件抄送人
	private String[] bcc; //邮件密件抄送人
	private String from; //邮件发送人
	private String subject; //邮件主题
	
	/**
	 * 构造一个邮件发送器
	 * @param host 邮件服务器地址 
	 * @param username 邮件服务器上有效的帐号
	 * @param password 邮件服务器上有效的密码
	 * @param authenticated 邮件服务器是否需要验证，一般为true
	 * @throws Exception
	 */
	public JavaMailSender(
			String host, 
			String username, 
			String password, 
			boolean authenticated) throws Exception {
		this.host = host;
		this.username = username;
		this.password = password;
		this.authenticated = authenticated;
		
		if(this.authenticated) {
			this.authenticator = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(getUsername(), getPassword());
				}
			};
		}
		
		Properties props = new Properties();
		props.put("mail.smtp.host", this.host);
		String authod = "true";
		if(! this.authenticated) {
			authod = "false";
		}
		props.put("mail.smtp.auth", authod);
		
		session = Session.getDefaultInstance(props, authenticator);
		transport = session.getTransport(this.protocol);
		transport.connect(host, username, password);
	}
	
	public void close() {
		try {
			this.transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private MimeMessage createMessage(String content, String contentType, int mailType) throws Exception {
		if(this.from == null) {
			throw new Exception("not set from field.");
		}
		
		if(this.to == null) {
			throw new Exception("not set to field.");
		}
		
		MimeMessage message = new MimeMessage(session);
		
		if(this.from != null) {
			message.setFrom(new InternetAddress(from));
		}
		
		InternetAddress[] icc = new InternetAddress[to.length];
		if(this.to != null) {
			for(int i = 0; i < to.length; i++) {
				icc[i] = new InternetAddress(to[i]);
			}
			message.setRecipients(Message.RecipientType.TO, icc);
		}
		
		if(this.cc != null) {
			icc = new InternetAddress[cc.length];
			for(int i = 0; i < cc.length; i++) {
				icc[i] = new InternetAddress(cc[i]);
			}
			message.setRecipients(Message.RecipientType.CC, icc);
		}
		
		if(this.bcc != null) {
			icc = new InternetAddress[bcc.length];
			for(int i = 0; i < bcc.length; i++) {
				icc[i] = new InternetAddress(bcc[i]);
			}
			message.setRecipients(Message.RecipientType.BCC, icc);
		}
		
		if(this.subject != null) {
			message.setSubject(this.subject);
		}
		
		Multipart multipart = new MimeMultipart();
		
		//set message body part
		BodyPart bodyPart = new MimeBodyPart();
		if(mailType == SIMPLE_MAIL) {
			bodyPart.setText(content); //发送简单文本邮件
		} else if(mailType == HTML_MAIL) {
			bodyPart.setContent(content, contentType);
		}
		multipart.addBodyPart(bodyPart); //add message body part
		
		//set attchment part
		for(File attachment : attachments) {
			if(attachment != null && attachment.exists()) {
				BodyPart attachmentPart = new MimeBodyPart();
				DataSource ds = new FileDataSource(attachment);
				attachmentPart.setDataHandler(new DataHandler(ds));
				String filename = attachment.getName();
				if(filename != null) {
					filename = new String(filename.getBytes(), "iso8859-1");
				}
				attachmentPart.setFileName(filename);
				multipart.addBodyPart(attachmentPart);
			}
		}
		
		message.setContent(multipart); //put all parts into message
		return message;
	}
	
	@SuppressWarnings("static-access")
	public void sendSimpleMail(String content) throws Exception {
		this.transport.send(this.createMessage(content, null, SIMPLE_MAIL));
	}
	
	@SuppressWarnings("static-access")
	public void sendHtmlMail(String content, String contentType) throws Exception {
		this.transport.send(this.createMessage(content, contentType, HTML_MAIL));
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Transport getTransport() {
		return transport;
	}

	public Session getSession() {
		return session;
	}
	
	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}
	
	public void setTo(String to) {
		this.to = new String[] {to};
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}
	
	public void setCc(String cc) {
		this.cc = new String[] {cc};
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	
	public void setBcc(String bcc) {
		this.bcc = new String[] {bcc};
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public List<File> getAttachments() {
		return attachments;
	}

	public void addAttachment(File attachment) {
		this.attachments.add(attachment);
	}

	/**
	 * 主方法,用于测试该类
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		JavaMailSender sender = new JavaMailSender("ourchr.com", 
					"user",
					"chr.123",
					true);
		sender.setTo(new String[] {"361969382@qq.com"});
		//sender.setCc("shingoo@21cn.com");
		//sender.setBcc("15945144@qq.com");
		sender.setFrom(new String("a在222".getBytes(), "iso8859-1"));
		sender.setSubject("javamail test");

		//File attachment = new File("c:\\exam.log"); //附件1
		//sender.addAttachment(attachment);
		
//		File attachment2 = new File("c:\\test");//附件2
//		sender.addAttachment(attachment2);
		String content = "<h1>Hello world!<br>华罗庚,带1个附件</h1>";
//		sender.sendSimpleMail(content);//sendSimpleMail方法用于发送简单纯文本邮件
		sender.sendHtmlMail(content, "text/html; charset=gbk");//sendHtmlMail方法用于发送html邮件,必须指定MIME信息,如"text/html; charset=gbk"
		sender.close();
	}

}
