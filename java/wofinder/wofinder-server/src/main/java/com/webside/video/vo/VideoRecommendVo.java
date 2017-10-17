package com.webside.video.vo;

/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
import java.io.Serializable;

/**
 * 首页推荐视频VO类
 *
 * @author zengxn
 * @date 2017年6月10日 16:42:15
 */
public class VideoRecommendVo implements Serializable {

	private static final long serialVersionUID = 1L;

	public VideoRecommendVo() {
	}

	public VideoRecommendVo(String id) {
		this.id = id;
	}

	/**
	 * 视频id
	 */
	private String id;

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
	 * 播放量总数
	 */
	private long viewCount;

	/**
	 * 视频评论总数
	 */
	private long videoCommentCount;

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

	public long getViewCount() {
		return viewCount;
	}

	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}

	public long getVideoCommentCount() {
		return videoCommentCount;
	}

	public void setVideoCommentCount(long videoCommentCount) {
		this.videoCommentCount = videoCommentCount;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
