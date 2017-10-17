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
import com.webside.sys.model.GameEntity;
import com.webside.user.model.UserEntity;

/**
 * 视频实体类
 *
 * @author zengxn
 * @date 2017-04-20 21:27:55
 */
public class VideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoEntity() {
	}

	public VideoEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;
	private String shortId;// 短ID

	/**
	 * 视频标题
	 */
	private String title;

	/**
	 * 视频封面
	 */
	private String cover;

	/**
	 * 视频时长
	 */
	private Double duration;

	/**
	 * 评分
	 */
	private Double score;

	/**
	 * 所属游戏
	 */
	private GameEntity game;

	/**
	 * 专辑id
	 */
	private VideoAlbumEntity album;

	/**
	 * 当前集数
	 */
	private Integer albumIndex;

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

	// 评论总数（非数据库字段）
	private Long videoCommentCount;

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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public VideoAlbumEntity getAlbum() {
		return album;
	}

	public void setAlbum(VideoAlbumEntity album) {
		this.album = album;
	}

	public Integer getAlbumIndex() {
		return albumIndex;
	}

	public void setAlbumIndex(Integer albumIndex) {
		this.albumIndex = albumIndex;
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

	public Long getVideoCommentCount() {
		return videoCommentCount;
	}

	public void setVideoCommentCount(Long videoCommentCount) {
		this.videoCommentCount = videoCommentCount;
	}

	public String getShortId() {
		return shortId;
	}

	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

}