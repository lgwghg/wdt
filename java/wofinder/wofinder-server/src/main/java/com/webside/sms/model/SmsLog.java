package com.webside.sms.model;

import java.io.Serializable;

public class SmsLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2197931130017043148L;
	private String id;
	/**
	 * 接收短信的手机号
	 */
	private String mobile;
	/**
	 * 发送的短信内容
	 */
	private String content;
	/**
	 * 短信发送结果
	 */
	private String result;
	/**
	 * 短信发送时间
	 */
	private Long sendTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	
}
