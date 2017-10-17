/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.up.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-06-06 11:45:21
 */
public class UpRelation implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UpRelation(){}
	

	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 人物id
	 */
	private String upId;
	
	public void setUpId(String upId){
		this.upId = upId;
	}
	
	public String getUpId(){
		return this.upId;
	}

	/**
	 * 相关人物id
	 */
	private String relationUpId;
	/**
	 * 相关人物信息
	 */
	private UpEntity relationUp;
	
	public UpEntity getRelationUp() {
		return relationUp;
	}

	public void setRelationUp(UpEntity relationUp) {
		this.relationUp = relationUp;
	}

	public void setRelationUpId(String relationUpId){
		this.relationUpId = relationUpId;
	}
	
	public String getRelationUpId(){
		return this.relationUpId;
	}

	/**
	 * 关系描述
	 */
	private String relationDesc;
	
	public void setRelationDesc(String relationDesc){
		this.relationDesc = relationDesc;
	}
	
	public String getRelationDesc(){
		return this.relationDesc;
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
	private String createTime;
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	
	public String getCreateTime(){
		return this.createTime;
	}

	/**
	 * 修改者
	 */
	private String updateId;
	
	public void setUpdateId(String updateId){
		this.updateId = updateId;
	}
	
	public String getUpdateId(){
		return this.updateId;
	}

	/**
	 * 修改时间
	 */
	private String updateTime;
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	
	public String getUpdateTime(){
		return this.updateTime;
	}

	public String toString() {
        return new ToStringBuilder(this)
        			.append("Id", getId())
		            .toString();
	}

	public boolean equals(Object other) {
        if (!(other instanceof UpRelation)){
			return false;
		}
        UpRelation castOther = (UpRelation) other;
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