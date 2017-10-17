package com.crawler.vo;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;
import com.webside.video.model.VideoStationEntity;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://www.bilibili.com/video/av[0-9]\\d*")
@HelpUrl("http://www.bilibili.com/video/(videogame-1|esports-1|mobilegame-1|onlinegame-1|boardgame-1|gmv-1|music-game-1|game-mugen-1).html#!page=[0-9]\\d*")	//B站单机游戏
public class BilibiliVideoVo implements AfterExtractor{
	
	
	private String id;
	
	@ExtractBy("//div[@class='v-title']/h1/text()")
	private String title;	//视频标题
	@ExtractBy("//div[@class='v_desc']/text()")
	private String introduction;		//视频简介
	@ExtractBy("//img[@class='cover_image']/@src")
	private String cover;		//视频封面
	
	private String duration;		//视频时长
	
	private String score;		//评分
	
	private String game_id;		//所属游戏
	
	private String album_id;		//专辑id
	
	private Integer album_index;		//当前集数
	
	private DictEntity status;

	private UserEntity createUser;

	private String createTime;

	private UserEntity updateUser;

	private String updateTime;
	
	private VideoStationEntity videoStationEntity;
	
	
	
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



	public String getDuration() {
		return duration;
	}



	public void setDuration(String duration) {
		this.duration = duration;
	}



	public String getScore() {
		return score;
	}



	public void setScore(String score) {
		this.score = score;
	}



	public String getGame_id() {
		return game_id;
	}



	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}



	public String getAlbum_id() {
		return album_id;
	}



	public void setAlbum_id(String album_id) {
		this.album_id = album_id;
	}



	public Integer getAlbum_index() {
		return album_index;
	}



	public void setAlbum_index(Integer album_index) {
		this.album_index = album_index;
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



	public VideoStationEntity getVideoStationEntity() {
		return videoStationEntity;
	}



	public void setVideoStationEntity(VideoStationEntity videoStationEntity) {
		this.videoStationEntity = videoStationEntity;
	}



	public void afterProcess(Page arg0) {
		// TODO Auto-generated method stub
		
	}

}
