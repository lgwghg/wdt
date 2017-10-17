/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.integral.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-06-15 17:46:54
 */
public class UserIntegral implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserIntegral(){}
	

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 积分类型
	 */
	private Integer type;
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
	 * 积分
	 */
	private Integer integral;
	
	public void setIntegral(Integer integral){
		this.integral = integral;
	}
	
	public Integer getIntegral(){
		return this.integral;
	}

	private String integralSource;
	
	public void setIntegralSource(String integralSource){
		this.integralSource = integralSource;
	}
	
	public String getIntegralSource(){
		return this.integralSource;
	}

	/**
	 * 状态
	 */
	private Integer status;
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}

	/**
	 * 创建者
	 */
	private String createId;
	
	public void setCreateId(String createId){
		this.createId = createId;
	}
	
	public String getCreateId(){
		return this.createId;
	}

	/**
	 * 创建时间
	 */
	private Long createTime;
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String toString() {
        return new ToStringBuilder(this)
        			.append("Id", getId())
		            .toString();
	}

	public boolean equals(Object other) {
        if (!(other instanceof UserIntegral)){
			return false;
		}
        UserIntegral castOther = (UserIntegral) other;
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