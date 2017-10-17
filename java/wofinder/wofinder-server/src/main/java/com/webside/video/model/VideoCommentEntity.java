/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.model;

import java.io.Serializable;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;

/**
 * 视频评论实体类
 *
 * @author zengxn
 * @date 2017-04-20 21:29:15
 */
public class VideoCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoCommentEntity() {
	}

	public VideoCommentEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 视频id
	 */
	private VideoEntity video;

	/**
	 * 评论id
	 */
	private String commentId;

	/**
	 * 评论内容
	 */
	private String commentContent;

	/**
	 * 评论时间
	 */
	private String commentCreatetime;

	/**
	 * 评论者id
	 */
	private String commentUserId;

	/**
	 * 评论者昵称
	 */
	private String commentUserName;

	/**
	 * 父级评论id
	 */
	private VideoCommentEntity commentParent;

	/**
	 * 所属站点
	 */
	private DictEntity station;

	/**
	 * 状态
	 */
	private DictEntity status;

	/**
	 * 点赞数量
	 */
	private Long likeNum;
	private Integer likeStatus;// 本人点赞状态

	/**
	 * 创建者
	 */
	private UserEntity createUser;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改者
	 */
	private UserEntity updateUser;

	/**
	 * 修改时间
	 */
	private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public VideoEntity getVideo() {
		return video;
	}

	public void setVideo(VideoEntity video) {
		this.video = video;
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

	public String getCommentCreatetime() {
		return commentCreatetime;
	}

	public void setCommentCreatetime(String commentCreatetime) {
		this.commentCreatetime = commentCreatetime;
	}

	public String getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}

	public String getCommentUserName() {
		return commentUserName;
	}

	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}

	public VideoCommentEntity getCommentParent() {
		return commentParent;
	}

	public void setCommentParent(VideoCommentEntity commentParent) {
		this.commentParent = commentParent;
	}

	public DictEntity getStation() {
		return station;
	}

	public void setStation(DictEntity station) {
		this.station = station;
	}

	public DictEntity getStatus() {
		return status;
	}

	public void setStatus(DictEntity status) {
		this.status = status;
	}

	public UserEntity getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public UserEntity getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserEntity updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Long getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Long likeNum) {
		this.likeNum = likeNum;
	}

	public Integer getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(Integer likeStatus) {
		this.likeStatus = likeStatus;
	}

}