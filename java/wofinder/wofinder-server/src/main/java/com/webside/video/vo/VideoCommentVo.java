package com.webside.video.vo;

import java.io.Serializable;

public class VideoCommentVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String event;

	public VideoCommentVo() {
		this.event = "comment";
	}

	/**
	 * id
	 */
	private String id;

	/**
	 * 评论id
	 */
	private String commentId;

	/**
	 * 评论内容
	 */
	private String commentContent;

	/**
	 * 评论者昵称
	 */
	private String commentUserName;

	/**
	 * 评论时间
	 */
	private String commentCreatetime;

	/**
	 * 评论者头像
	 */
	private String avatar;

	/**
	 * 父评论昵称
	 */
	private String parentUserName;

	/**
	 * 视频id
	 */
	private String videoId;

	/**
	 * 站点value
	 */
	private String station;

	/**
	 * 站点class
	 */
	private String stationClass;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentUserName() {
		return commentUserName;
	}

	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}

	public String getCommentCreatetime() {
		return commentCreatetime;
	}

	public void setCommentCreatetime(String commentCreatetime) {
		this.commentCreatetime = commentCreatetime;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getParentUserName() {
		return parentUserName;
	}

	public void setParentUserName(String parentUserName) {
		this.parentUserName = parentUserName;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStationClass() {
		return stationClass;
	}

	public void setStationClass(String stationClass) {
		this.stationClass = stationClass;
	}

}
