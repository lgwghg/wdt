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
 * @date 2017-06-06 11:42:38
 */
public class UpPhoto implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UpPhoto(){}
	

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
	 * 人物二级id
	 */
	private String upSecondId;
	
	public void setUpSecondId(String upSecondId){
		this.upSecondId = upSecondId;
	}
	
	public String getUpSecondId(){
		return this.upSecondId;
	}

	/**
	 * 相册名称
	 */
	private String photoName;
	
	public void setPhotoName(String photoName){
		this.photoName = photoName;
	}
	
	public String getPhotoName(){
		return this.photoName;
	}

	/**
	 * 图片地址
	 */
	private String photo;
	
	public void setPhoto(String photo){
		this.photo = photo;
	}
	
	public String getPhoto(){
		return this.photo;
	}

	/**
	 * 是否是主图，默认0   0：非主图  1：主图
	 */
	private Integer isMain;
	
	public void setIsMain(Integer isMain){
		this.isMain = isMain;
	}
	
	public Integer getIsMain(){
		return this.isMain;
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
        if (!(other instanceof UpPhoto)){
			return false;
		}
        UpPhoto castOther = (UpPhoto) other;
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