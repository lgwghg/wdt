package com.webside.user.model.vo;

import java.io.Serializable;

/**
 * 用户缓存VO类
 * 
 * @author zengxn
 *
 */
public class UserCacheVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private String id;

	/**
	 * 用户昵称
	 */
	private String n;

	/**
	 * 手机号码
	 */
	private String m;

	/**
	 * 头像
	 */
	private String p;

	/**
	 * 邮箱
	 */
	private String e;

	/**
	 * 签名
	 */
	private String s;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

}
