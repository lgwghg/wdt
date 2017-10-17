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
 * 视频合集缓存VO类
 * 
 * @author zengxn
 *
 */
public class VideoAlbumCacheVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 合集id
	 */
	private String id;

	/**
	 * 合集名称
	 */
	private String n;

	/**
	 * 合集封面
	 */
	private String c;

	/**
	 * 合集作者
	 */
	private String a;

	/**
	 * 合集描述
	 */
	private String i;

	/**
	 * 合集网络更新时间
	 */
	private String u;
	
	/**
	 * 当前集数
	 */
	private Integer inde;

	/**
	 * 父级id
	 */
	private String pid;

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

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getInde() {
		return inde;
	}

	public void setInde(Integer inde) {
		this.inde = inde;
	}

}
