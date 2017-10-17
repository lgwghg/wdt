/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.model;

import java.io.Serializable;

/**
 * 系统帮助实体类
 *
 * @author zfei
 * @date 2017-06-30 13:51:40
 */
public class HelpEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	public HelpEntity() {
	}

	public HelpEntity(String id) {
		this.id = id;
	}
	
	private String id; // 系统帮助
	private String title; // 标题
	private String content; // 通知内容
	private String addTime; // 创建时间
	private String sysUserId; // 添加人员
	private String type; // 1.系统帮助；2.用户反馈
	private Integer sequence; // 排序
	private String status; // 状态 1：有效 0 无效 
	private String code; // 内码
	
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getAddTime(){
		return this.addTime;
	}
	
	public void setAddTime(String addTime){
		this.addTime = addTime;
	}
	
	public String getSysUserId(){
		return this.sysUserId;
	}
	
	public void setSysUserId(String sysUserId){
		this.sysUserId = sysUserId;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public Integer getSequence(){
		return this.sequence;
	}
	
	public void setSequence(Integer sequence){
		this.sequence = sequence;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
}