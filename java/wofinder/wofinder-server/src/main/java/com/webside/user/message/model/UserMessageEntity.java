/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.message.model;

import java.io.Serializable;

/**
 * 用户消息实体类
 *
 * @author zfei
 * @date 2017-06-23 18:02:58
 */
public class UserMessageEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserMessageEntity() {
	}

	public UserMessageEntity(String id) {
		this.id = id;
	}
	
	private String id; // 唯一标识
	
	private String userId; // 用户ID
	
	private String businessType; // 业务类型 1:评论
	
	private String businessId; // 业务ID
	
	private String content; // 消息描述
	
	private String state; // 阅读状态 1：已读，0：未读
	
	private String isDeleted; // 默认0     1：已删除   0：正常
	
	private String createTime; // 消息创建时间
	
	private String updateTime; // 更新已读状态时，更新时间
	
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
	
	public String getBusinessType(){
		return this.businessType;
	}
	
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}
	
	public String getBusinessId(){
		return this.businessId;
	}
	
	public void setBusinessId(String businessId){
		this.businessId = businessId;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getState(){
		return this.state;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public String getIsDeleted(){
		return this.isDeleted;
	}
	
	public void setIsDeleted(String isDeleted){
		this.isDeleted = isDeleted;
	}
	
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	
}