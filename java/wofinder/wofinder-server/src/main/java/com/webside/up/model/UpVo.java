package com.webside.up.model;

import java.io.Serializable;
import java.util.List;

import com.webside.dict.model.DictEntity;
import com.webside.sys.model.GameEntity;

public class UpVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3609151831260116700L;

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
	 * 状态
	 */
	private DictEntity status;
	/**
	 * 相关人物
	 */
	private List<UpRelation> upRelationList;
	/**
	 * 人物二级信息
	 */
	private List<UpSecondLevel> upSecondLevelList;
	/**
	 * 相册主图
	 */
	private String mainPhoto;
	/**
	 * 人物相册
	 */
	private List<UpPhoto> upPhotoList;
	
	public String getMainPhoto() {
		return mainPhoto;
	}
	public void setMainPhoto(String mainPhoto) {
		this.mainPhoto = mainPhoto;
	}
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
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public DictEntity getStatus() {
		return status;
	}
	public void setStatus(DictEntity status) {
		this.status = status;
	}
	public List<UpRelation> getUpRelationList() {
		return upRelationList;
	}
	public void setUpRelationList(List<UpRelation> upRelationList) {
		this.upRelationList = upRelationList;
	}
	public List<UpSecondLevel> getUpSecondLevelList() {
		return upSecondLevelList;
	}
	public void setUpSecondLevelList(List<UpSecondLevel> upSecondLevelList) {
		this.upSecondLevelList = upSecondLevelList;
	}
	public List<UpPhoto> getUpPhotoList() {
		return upPhotoList;
	}
	public void setUpPhotoList(List<UpPhoto> upPhotoList) {
		this.upPhotoList = upPhotoList;
	}
	
	
}
