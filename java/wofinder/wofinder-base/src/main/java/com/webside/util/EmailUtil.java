package com.webside.util;

import java.io.UnsupportedEncodingException;

import javax.mail.AuthenticationFailedException;
import javax.mail.internet.MimeUtility;

import jodd.mail.Email;
import jodd.mail.MailException;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: EmailUtil
 * @Description: 邮件发送工具类,封装了jodd的mail工具类
 * @author gaogang
 * @date 2016年7月12日 下午4:22:12
 *
 */
@Component
public class EmailUtil {
	

	@Value("${mail.username}")
	private String EMAIL_USER;

	@Value("${mail.password}")
	private String EMAIL_PASSWORD;

	public static final String EMAIL_CAPTCHA_TEMPLATE = "email_captcha_template";
	/**
	 * 发送126邮箱
	 * 
	 * @param toMail
	 * @param subject
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	public boolean send126Mail(String toMail, String fromName, String subject, String text) throws AuthenticationFailedException, MailException, UnsupportedEncodingException
	{
		//Email email = Email.create().from(USER_126).to(toMail).subject(Encoder.encodeStr(subject, "GB2312")).addText(text);
		Email email = Email.create().from(fromName, EMAIL_USER).to(toMail).subject(MimeUtility.encodeText(subject, "UTF-8", "B")).addHtml(text);// .addText(text);
		SmtpServer smtpServer = SmtpServer.create("smtp.exmail.qq.com").authenticateWith(EMAIL_USER, EMAIL_PASSWORD);
		SendMailSession session = smtpServer.createSession();
		session.open();
		session.sendMail(email);
		session.close();
		return true;
	}
}
