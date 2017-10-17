package com.webside.task.vo;

import java.io.Serializable;

/**
 * 人物事件VO类
 *
 * @author zengxn
 * @date 2017年9月28日15:21:50
 */
public class TaskVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public TaskVo() {
	}

	public TaskVo(String id) {
		this.id = id;
	}

	/**
	 * 事件id
	 */
	private String id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 阅读量
	 */
	private Long viewCount;

	/**
	 * 点赞量
	 */
	private Long likeCount;

	/**
	 * 创建时间
	 */
	private String createTime;

	// 本人点赞状态
	private Integer likeStatus;

	/**
	 * 评论量
	 */
	private Long commentCount;

	/**
	 * 最新评论内容
	 */
	private String commentContent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(Integer likeStatus) {
		this.likeStatus = likeStatus;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}
