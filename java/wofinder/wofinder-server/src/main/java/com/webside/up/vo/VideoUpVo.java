/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.vo;

import java.io.Serializable;
import java.util.List;

import com.webside.sys.vo.ValueVo;

/**
 * 视频作者VO类
 *
 * @author zengxn
 * @date 2017年6月10日 16:42:15
 */
public class VideoUpVo implements Serializable {

	private static final long serialVersionUID = 1L;

	public VideoUpVo() {
	}

	public VideoUpVo(String id) {
		this.id = id;
	}

	/**
	 * 视频作者id
	 */
	private String id;

	/**
	 * 视频作者名称
	 */
	private String name;

	/**
	 * 简介
	 */
	private String introduction;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 综合评分
	 */
	private double score;

	/**
	 * Wo排名
	 */
	private double rank;

	/**
	 * 视频作者站点集合
	 */
	private List<VideoUpStationVo> upStationList;

	/**
	 * 视频作者属性集合
	 */
	private List<ValueVo> upValueList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public List<VideoUpStationVo> getUpStationList() {
		return upStationList;
	}

	public void setUpStationList(List<VideoUpStationVo> upStationList) {
		this.upStationList = upStationList;
	}

	public List<ValueVo> getUpValueList() {
		return upValueList;
	}

	public void setUpValueList(List<ValueVo> upValueList) {
		this.upValueList = upValueList;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
