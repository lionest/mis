package com.htlab.mis.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
	private String userAccount = "";
	private String pass = "";

	public MailAuthenticator(String userAccount, String pass) {
		this.userAccount = userAccount;
		this.pass = pass;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userAccount, pass);
	}

}
