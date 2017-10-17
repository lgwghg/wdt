package com.webside.enums;

/**
 * 
 * @ClassName: EmailDescription
 * @Description: 邮件提示信息枚举
 * @author gaogang
 * @date 2016年7月12日 下午3:10:06
 *
 */
public enum EmailDescription {

	ADD_EMAIL("新建账户通知", "您好,您的账户已创建,账户名: %s ,密码: %s ,请尽快登录系统修改密码,谢谢."),
	UPDATE_EMAIL("密码重置通知", "您好，您的密码已重置，新密码是: %s "),
	SEND_CAPTCHA("WoDoTa验证码", "您好，您的验证码是: %s ,打死也不要告诉别人哦。");
	private String subject;
	
	private String message;
	
	private EmailDescription(String subject, String message)
	{
		this.subject = subject;
		this.message = message;
	}
	
	public String getSubject()
	{
		return this.subject;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	
	
}
