/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 视频合集VO类
 *
 * @author zengxn
 * @date 2017年8月23日 16:09:36
 */
public class VideoAlbumVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoAlbumVo() {
	}
	
	public VideoAlbumVo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public VideoAlbumVo(String id, String name, String introduction, String cover, String author, String updateRemarks) {
		this.id = id;
		this.name = name;
		this.introduction = introduction;
		this.cover = cover;
		this.author = author;
		this.updateRemarks = updateRemarks;
	}

	public VideoAlbumVo(String id) {
		this.id = id;
	}

	/**
	 * 视频专辑id
	 */
	private String id;

	/**
	 * 视频专辑名称
	 */
	private String name;
	
	/**
	 * 第几季
	 */
	private int inde;

	/**
	 * 视频专辑简介
	 */
	private String introduction;

	/**
	 * 搜索匹配后视频标题
	 */
	private String searchName;

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
	 * 视频集合
	 */
	private List<VideoVo> videoList;

	/**
	 * 视频集合集合
	 */
	private List<VideoAlbumVo> videoAlbumList;

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

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public List<VideoVo> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<VideoVo> videoList) {
		this.videoList = videoList;
	}

	public List<VideoAlbumVo> getVideoAlbumList() {
		return videoAlbumList;
	}

	public void setVideoAlbumList(List<VideoAlbumVo> videoAlbumList) {
		this.videoAlbumList = videoAlbumList;
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

	public int getInde() {
		return inde;
	}

	public void setInde(int inde) {
		this.inde = inde;
	}

}
