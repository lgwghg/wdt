/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.vo;

import java.io.Serializable;

/**
 * 视频缓存VO类
 * 
 * @author zengxn
 *
 */
public class VideoCacheVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 视频id
	 */
	private String id;

	/**
	 * 视频短id
	 */
	private String sid;

	/**
	 * 视频标题
	 */
	private String t;

	/**
	 * 视频封面
	 */
	private String c;

	/**
	 * 视频时长
	 */
	private Double d;

	/**
	 * 评分
	 */
	private Double s;

	/**
	 * 所属游戏id
	 */
	private String gid;

	/**
	 * 评论数量
	 */
	private Long cc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public Double getD() {
		return d;
	}

	public void setD(Double d) {
		this.d = d;
	}

	public Double getS() {
		return s;
	}

	public void setS(Double s) {
		this.s = s;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public Long getCc() {
		return cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
