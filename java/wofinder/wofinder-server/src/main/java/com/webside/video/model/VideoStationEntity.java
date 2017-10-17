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
import com.webside.up.model.UpEntity;
import com.webside.user.model.UserEntity;

/**
 * 视频站点实体类
 *
 * @author zengxn
 * @date 2017-04-20 21:30:19
 */
public class VideoStationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoStationEntity() {
	}

	public VideoStationEntity(String id) {
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
	 * 视频源id
	 */
	private String vid;

	/**
	 * 视频源url
	 */
	private String url;

	/**
	 * 视频播放url
	 */
	private String flashUrl;

	/**
	 * 视频标题
	 */
	private String title;

	/**
	 * 视频简介
	 */
	private String introduction;

	/**
	 * 视频封面
	 */
	private String cover;

	/**
	 * 视频时长
	 */
	private Double duration;

	/**
	 * 视频类别
	 */
	private String category;

	/**
	 * 视频发布时间
	 */
	private String published;

	/**
	 * 视频播放量
	 */
	private Long viewCount;

	/**
	 * 视频评论量
	 */
	private Long commentCount;

	/**
	 * 视频下载量
	 */
	private Long downCount;

	/**
	 * 视频收藏量
	 */
	private Long favoriteCount;

	/**
	 * 视频作者id
	 */
	private UpEntity up;

	/**
	 * 所属站点
	 */
	private DictEntity station;

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

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFlashUrl() {
		return flashUrl;
	}

	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getDownCount() {
		return downCount;
	}

	public void setDownCount(Long downCount) {
		this.downCount = downCount;
	}

	public Long getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public UpEntity getUp() {
		return up;
	}

	public void setUp(UpEntity up) {
		this.up = up;
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

}