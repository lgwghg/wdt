/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.video.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.webside.user.model.UserEntity;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-08-21 16:48:27
 */
public class VideoValueLog implements Serializable
{
	private static final long serialVersionUID = 1L;

	public VideoValueLog(){}
	

	/**
	 * 主键
	 */
	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 视频id
	 */
	private String videoValueId;
	/**
	 * 属性id
	 */
	private String valueId;
	
	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}


	/**
	 * 属性名称
	 */
	private String valueName;
	
	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public void setVideoValueId(String videoValueId){
		this.videoValueId = videoValueId;
	}
	
	public String getVideoValueId(){
		return this.videoValueId;
	}

	/**
	 * 操作类型：0:添加  1:点赞 4:删除
	 */
	private Integer operateType;
	
	public void setOperateType(Integer operateType){
		this.operateType = operateType;
	}
	
	public Integer getOperateType(){
		return this.operateType;
	}

	/**
	 * 状态  1有效  0无效
	 */
	private Integer status;
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}

	/**
	 * 举报状态 ：0：正常  1：已举报  2：已处理
	 */
	private Integer informStatus;
	
	public void setInformStatus(Integer informStatus){
		this.informStatus = informStatus;
	}
	
	public Integer getInformStatus(){
		return this.informStatus;
	}

	/**
	 * 创建者
	 */
	private String createId;
	private UserEntity createUser;
	
	public UserEntity getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}

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
        if (!(other instanceof VideoValueLog)){
			return false;
		}
        VideoValueLog castOther = (VideoValueLog) other;
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