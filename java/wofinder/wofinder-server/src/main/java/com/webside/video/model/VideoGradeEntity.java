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

/**
 * 视频评分实体类
 *
 * @author zengxn
 * @date 2017-06-12 16:04:07
 */
public class VideoGradeEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	public VideoGradeEntity() {
	}

	public VideoGradeEntity(String id) {
		this.id = id;
	}
	
	private String id; // 评分
	
	private String userId; // 评分人
	
	private String videoId; // 视频 t_video
	
	private VideoEntity video;// 视频 t_video
	
	private Double score; // 评分
	
	private Long createTime; // 创建时间
	
	private Integer status; // 状态，1有效，0无效
	
	public VideoEntity getVideo() {
		return video;
	}

	public void setVideo(VideoEntity video) {
		this.video = video;
	}

	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getVideoId(){
		return this.videoId;
	}
	
	public void setVideoId(String videoId){
		this.videoId = videoId;
	}
	
	public Double getScore(){
		return this.score;
	}
	
	public void setScore(Double score){
		this.score = score;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Integer getStatus(){
		return this.status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
}