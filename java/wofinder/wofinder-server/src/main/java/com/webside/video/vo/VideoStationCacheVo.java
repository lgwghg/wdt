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

import com.webside.dict.model.DictEntity;
import com.webside.up.model.UpEntity;

/**
 * 视频站点缓存VO类
 * 
 * @author zengxn
 *
 */
public class VideoStationCacheVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 视频站点id
	 */
	private String id;

	/**
	 * 视频id
	 */
	private String vid;

	/**
	 * 视频源url
	 */
	private String u;

	/**
	 * 视频播放url
	 */
	private String fu;

	/**
	 * 视频简介
	 */
	private String i;

	/**
	 * 视频播放量
	 */
	private Long vc;

	/**
	 * 视频评论量
	 */
	private Long cc;

	/**
	 * 视频作者id
	 */
	private UpEntity up;

	/**
	 * 所属站点
	 */
	private DictEntity s;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getFu() {
		return fu;
	}

	public void setFu(String fu) {
		this.fu = fu;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public Long getVc() {
		return vc;
	}

	public void setVc(Long vc) {
		this.vc = vc;
	}

	public Long getCc() {
		return cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}

	public UpEntity getUp() {
		return up;
	}

	public void setUp(UpEntity up) {
		this.up = up;
	}

	public DictEntity getS() {
		return s;
	}

	public void setS(DictEntity s) {
		this.s = s;
	}

}
