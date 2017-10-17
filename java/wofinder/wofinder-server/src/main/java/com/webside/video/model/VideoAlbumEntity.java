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
import java.util.List;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;

/**
 * 视频专辑实体类
 * 
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
public class VideoAlbumEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoAlbumEntity() {
	}

	public VideoAlbumEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 专辑名称
	 */
	private String name;

	/**
	 * 父级合集
	 */
	private VideoAlbumEntity parentAlbum;
	
	/**
	 * 排序集数
	 */
	private Integer inde;
	
	/**
	 * 合集封面
	 */
	private String cover;
	
	/**
	 * 合集作者名称
	 */
	private String author;
	
	/**
	 * 更新时间备注
	 */
	private String updateRemarks;

	/**
	 * ID（非本站ID）
	 */
	private String homeId;

	/**
	 * 地址
	 */
	private String homeUrl;


	/**
	 * 专辑简介
	 */
	private String introduction;

	/**
	 * 评分
	 */
	private Double score;

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

	/**
	 * 视频集合
	 */
	private List<VideoEntity> videoList;

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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
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

	public List<VideoEntity> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<VideoEntity> videoList) {
		this.videoList = videoList;
	}

	public VideoAlbumEntity getParentAlbum() {
		return parentAlbum;
	}

	public void setParentAlbum(VideoAlbumEntity parentAlbum) {
		this.parentAlbum = parentAlbum;
	}

	public String getHomeId() {
		return homeId;
	}

	public void setHomeId(String homeId) {
		this.homeId = homeId;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public Integer getInde() {
		return inde;
	}

	public void setInde(Integer inde) {
		this.inde = inde;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUpdateRemarks() {
		return updateRemarks;
	}

	public void setUpdateRemarks(String updateRemarks) {
		this.updateRemarks = updateRemarks;
	}
}