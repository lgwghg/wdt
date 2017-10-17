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
 * 视频站点VO类
 * 
 * @author zengxn
 * @date 2017年6月10日 16:42:15
 */
public class VideoStationVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoStationVo() {
	}

	public VideoStationVo(String id, String stationClass, String flashUrl, String url, String introduction, String upName,String published) {
		this.id = id;
		this.stationClass = stationClass;
		this.flashUrl = flashUrl;
		this.introduction = introduction;
		this.url = url;
		this.upName = upName;
		this.published = published;
	}

	/**
	 * 视频站点id
	 */
	private String id;
	private String shortId;

	/**
	 * 视频播放url
	 */
	private String flashUrl;

	/**
	 * 视频源url
	 */
	private String url;
	
	/**
	 * 视频发布时间
	 */
	private String published;

	/**
	 * 视频简介
	 */
	private String introduction;

	/**
	 * 站点样式
	 */
	private String stationClass;

	/**
	 * 视频作者名称
	 */
	private String upName;
	private String videoId; // 所属视频
	private String cover; // 封面
	private String score; // 评分
	private String duration; // 时长
	private String category;// 类型
	private String station;// 类型

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlashUrl() {
		return flashUrl;
	}

	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}

	public String getStationClass() {
		return stationClass;
	}

	public void setStationClass(String stationClass) {
		this.stationClass = stationClass;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpName() {
		return upName;
	}

	public void setUpName(String upName) {
		this.upName = upName;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getShortId() {
		return shortId;
	}

	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

}
