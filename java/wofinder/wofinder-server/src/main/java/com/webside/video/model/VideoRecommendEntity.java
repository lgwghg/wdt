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
 * 首页推荐视频实体类
 *
 * @author zengxn
 * @date 2017-04-20 22:21:52
 */
public class VideoRecommendEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoRecommendEntity() {
	}

	public VideoRecommendEntity(String id) {
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
	 * 展示视频地址
	 */
	private String url;

	/**
	 * 展示时间开始
	 */
	private String startTime;

	/**
	 * 展示时间结束
	 */
	private String endTime;

	/**
	 * 状态
	 */
	private DictEntity status;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

}