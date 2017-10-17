/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.sign.entities;

import java.io.Serializable;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2016-12-06 15:04:29
 */
public class UserSign implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserSign(){}
	

	/**
	 * 唯一标识
	 */
	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 用户ID
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 签到日期
	 */
	private Long addTime;
	
	public void setAddTime(Long addTime){
		this.addTime = addTime;
	}
	
	public Long getAddTime(){
		return this.addTime;
	}

	/**
	 * 赠送积分
	 */
	private Integer integral;

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	


}