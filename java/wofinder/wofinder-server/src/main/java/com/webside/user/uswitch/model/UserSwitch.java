/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.user.uswitch.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-06-27 15:04:54
 */
public class UserSwitch implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UserSwitch(){}
	

	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	private String switchType;
	private String switchDesc;
	
	public String getSwitchDesc() {
		return switchDesc;
	}

	public void setSwitchDesc(String switchDesc) {
		this.switchDesc = switchDesc;
	}

	public void setSwitchType(String switchType){
		this.switchType = switchType;
	}
	
	public String getSwitchType(){
		return this.switchType;
	}

	private Integer value;
	
	public void setValue(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return this.value;
	}

	private Long createTime;
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	
	public Long getCreateTime(){
		return this.createTime;
	}

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
        if (!(other instanceof UserSwitch)){
			return false;
		}
        UserSwitch castOther = (UserSwitch) other;
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