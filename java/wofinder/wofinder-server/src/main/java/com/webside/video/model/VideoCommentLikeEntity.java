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
 * 评论点赞实体类
 *
 * @author zengxn
 * @date 2017-06-12 16:03:14
 */
public class VideoCommentLikeEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	public VideoCommentLikeEntity() {
	}

	public VideoCommentLikeEntity(String id) {
		this.id = id;
	}
	
	private String id; // 点赞
	
	private String userId; // 点赞人
	
	private String commentId; // 评论 t_video_comment
	
	private Long createTime; // 创建时间
	
	private Integer status; // 状态，1有效，0无效
	
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
	
	public String getCommentId(){
		return this.commentId;
	}
	
	public void setCommentId(String commentId){
		this.commentId = commentId;
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