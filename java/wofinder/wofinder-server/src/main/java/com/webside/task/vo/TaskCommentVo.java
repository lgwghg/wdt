package com.webside.task.vo;

import java.io.Serializable;

/**
 * 人物事件评论VO类
 *
 * @author zengxn
 * @date 2017年9月28日15:21:50
 */
public class TaskCommentVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public TaskCommentVo() {
		this.event = "taskComment";
	}

	private String event;

	/**
	 * 评论id
	 */
	private String id;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 评论者昵称
	 */
	private String userName;

	/**
	 * 评论时间
	 */
	private String createtime;

	/**
	 * 评论者头像
	 */
	private String avatar;

	/**
	 * 父评论昵称
	 */
	private String parentUserName;
	
	/**
	 * 点赞数量
	 */
	private Long likeNum;

	/**
	 * 事件id
	 */
	private String taskId;

	/**
	 * 本人点赞状态
	 */
	private Integer likeStatus;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(Integer likeStatus) {
		this.likeStatus = likeStatus;
	}

	public Long getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Long likeNum) {
		this.likeNum = likeNum;
	}
}
