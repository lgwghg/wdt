/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.model;

import java.io.Serializable;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;

/**
 * 视频作者站点实体类
 * 
 * @author zengxn
 * @date 2017-04-15 18:29:58
 */
public class UpStationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public UpStationEntity() {
	}

	public UpStationEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 视频作者id
	 */
	private UpEntity up;

	/**
	 * 个人主页地址
	 */
	private String homeUrl;
	private String homeId;// 个人主页ID（非本站ID）

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 简介
	 */
	private String upIntroduction;

	/**
	 * 头像
	 */
	private String upAvatar;

	/**
	 * 视频数量
	 */
	private Long upVideoCount;

	/**
	 * 粉丝数量
	 */
	private Long upFansCount;

	/**
	 * 关注数量
	 */
	private Long upFriendCount;

	/**
	 * 播放数量
	 */
	private Long upPlayCount;

	/**
	 * 所属站点
	 */
	private DictEntity station;

	/**
	 * 所属第三方
	 */
	private DictEntity thirdParty;

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

	public UpEntity getUp() {
		return up;
	}

	public void setUp(UpEntity up) {
		this.up = up;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpIntroduction() {
		return upIntroduction;
	}

	public void setUpIntroduction(String upIntroduction) {
		this.upIntroduction = upIntroduction;
	}

	public String getUpAvatar() {
		return upAvatar;
	}

	public void setUpAvatar(String upAvatar) {
		this.upAvatar = upAvatar;
	}

	public Long getUpVideoCount() {
		return upVideoCount;
	}

	public void setUpVideoCount(Long upVideoCount) {
		this.upVideoCount = upVideoCount;
	}

	public Long getUpFansCount() {
		return upFansCount;
	}

	public void setUpFansCount(Long upFansCount) {
		this.upFansCount = upFansCount;
	}

	public Long getUpFriendCount() {
		return upFriendCount;
	}

	public void setUpFriendCount(Long upFriendCount) {
		this.upFriendCount = upFriendCount;
	}

	public Long getUpPlayCount() {
		return upPlayCount;
	}

	public void setUpPlayCount(Long upPlayCount) {
		this.upPlayCount = upPlayCount;
	}

	public DictEntity getStation() {
		return station;
	}

	public void setStation(DictEntity station) {
		this.station = station;
	}

	public DictEntity getThirdParty() {
		return thirdParty;
	}

	public void setThirdParty(DictEntity thirdParty) {
		this.thirdParty = thirdParty;
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

	public String getHomeId() {
		return homeId;
	}

	public void setHomeId(String homeId) {
		this.homeId = homeId;
	}

}