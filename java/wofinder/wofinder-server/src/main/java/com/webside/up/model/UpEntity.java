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
import java.util.List;

import com.webside.dict.model.DictEntity;
import com.webside.sys.model.GameEntity;
import com.webside.user.model.UserEntity;

/**
 * 视频作者实体类
 *
 * @author zengxn
 * @date 2017-04-15 18:28:19
 */
public class UpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public UpEntity() {
	}

	public UpEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 名称
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
	 * 所属游戏
	 */
	private GameEntity game;

	/**
	 * 人气指数
	 */
	private Long popularityIndex;
	/**
	 * 排名
	 */
	private Long rank;
	
	/**
	 * 是否可以搜索
	 */
	private DictEntity isSearch;

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
	 * 人物站点集合
	 */
	private List<UpStationEntity> upStationList;

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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public Long getPopularityIndex() {
		return popularityIndex;
	}

	public void setPopularityIndex(Long popularityIndex) {
		this.popularityIndex = popularityIndex;
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

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public List<UpStationEntity> getUpStationList() {
		return upStationList;
	}

	public void setUpStationList(List<UpStationEntity> upStationList) {
		this.upStationList = upStationList;
	}

	public DictEntity getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(DictEntity isSearch) {
		this.isSearch = isSearch;
	}

}