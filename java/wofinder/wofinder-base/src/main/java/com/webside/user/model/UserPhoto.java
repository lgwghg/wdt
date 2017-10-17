/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-06-23 10:17:42
 */
public class UserPhoto implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserPhoto(){}
	

	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 用户
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 头像
	 */
	private String photo;
	
	public void setPhoto(String photo){
		this.photo = photo;
	}
	
	public String getPhoto(){
		return this.photo;
	}
	private Integer status;
	private Integer isCurrent;
	
	public void setIsCurrent(Integer isCurrent){
		this.isCurrent = isCurrent;
	}
	
	public Integer getIsCurrent(){
		return this.isCurrent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	/**
	 * 上传时间
	 */
	private Long createTime;
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}

	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	public void setUpdateTime(Long updateTime){
		this.updateTime = updateTime;
	}
	
	public Long getUpdateTime(){
		return this.updateTime;
	}

	public String toString() {
        return new ToStringBuilder(this)
        			.append("Id", getId())
		            .toString();
	}

	public boolean equals(Object other) {
        if (!(other instanceof UserPhoto)){
			return false;
		}
        UserPhoto castOther = (UserPhoto) other;
        return new EqualsBuilder()
        			.append(this.getId(), castOther.getId())
		           .isEquals();
	}

	public int hashCode() {
        return new HashCodeBuilder()
        			.append(this.getId())
		            .toHashCode();
	}

}