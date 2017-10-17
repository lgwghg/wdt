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
 * 视频VO类
 *
 * @author zengxn
 * @date 2017年6月10日 16:42:15
 */
public class VideoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	public VideoVo() {
	}

	public VideoVo(String id) {
		this.id = id;
	}

	/**
	 * 视频id
	 */
	private String id;

	/**
	 * 视频短ID
	 */
	private String shortId;

	/**
	 * 视频标题
	 */
	private String title;

	/**
	 * 搜索匹配后视频标题
	 */
	private String searchTitle;

	/**
	 * 视频封面
	 */
	private String cover;

	/**
	 * 播放量总数
	 */
	private Long viewCount;

	/**
	 * 视频评论总数
	 */
	private Long videoCommentCount;

	/**
	 * 视频时长
	 */
	private Double duration;

	/**
	 * 综合评分
	 */
	private Double score;

	/**
	 * 个人评分
	 */
	private Double myScore;
	
	/**
	 * 当前集数
	 */
	private Integer albumIndex;

	/**
	 * 视频站点集合
	 */
	private List<VideoStationVo> videoStationList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortId() {
		return shortId;
	}

	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getVideoCommentCount() {
		return videoCommentCount;
	}

	public void setVideoCommentCount(Long videoCommentCount) {
		this.videoCommentCount = videoCommentCount;
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

	public Double getMyScore() {
		return myScore;
	}

	public void setMyScore(Double myScore) {
		this.myScore = myScore;
	}

	public List<VideoStationVo> getVideoStationList() {
		return videoStationList;
	}

	public void setVideoStationList(List<VideoStationVo> videoStationList) {
		this.videoStationList = videoStationList;
	}

	public Integer getAlbumIndex() {
		return albumIndex;
	}

	public void setAlbumIndex(Integer albumIndex) {
		this.albumIndex = albumIndex;
	}

	@Override
	public String toString() {
		return "VideoVo [id=" + id + ", shortId=" + shortId + ", title=" + title + ", searchTitle=" + searchTitle + ", cover=" + cover + ", viewCount=" + viewCount + ", videoCommentCount=" + videoCommentCount + ", duration=" + duration + ", score=" + score + ", myScore=" + myScore + ", videoStationList=" + videoStationList + "]";
	}

}
