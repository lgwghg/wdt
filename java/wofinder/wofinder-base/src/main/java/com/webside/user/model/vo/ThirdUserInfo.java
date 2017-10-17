package com.webside.user.model.vo;

import java.io.Serializable;

/**
 * 第三方用户信息
 * 
 * @author tianguifang
 * 
 */
public class ThirdUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 第三方用户key
	 */
	private String thirdKey;

	/**
	 * 第三方用户昵称
	 */
	private String thirdNick;

	/**
	 * 第三方用户类型 包括：'steam','qq','weibo','wechat'
	 */
	private String thirdType;

	public String getThirdKey() {
		return thirdKey;
	}

	public void setThirdKey(String thirdKey) {
		this.thirdKey = thirdKey;
	}

	public String getThirdNick() {
		return thirdNick;
	}

	public void setThirdNick(String thirdNick) {
		this.thirdNick = thirdNick;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

}
