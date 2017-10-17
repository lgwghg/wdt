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
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-06-06 11:43:57
 */
public class UpSecondLevel implements Serializable
{
	private static final long serialVersionUID = 1L;

	public UpSecondLevel(){}
	

	/**
	 * 主键
	 */
	private String id;
	private String mainPhoto;
	/**
	 * 人物二级信息图册
	 */
	private List<UpPhoto> upPhotoList;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 父id
	 */
	private String parentId;
	
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	
	public String getParentId(){
		return this.parentId;
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
	 * 标题类型，字典存储
	 */
	private Integer titleType;
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleType(Integer titleType){
		this.titleType = titleType;
	}
	
	public Integer getTitleType(){
		return this.titleType;
	}

	/**
	 * 内容
	 */
	private String content;
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
	}

	/**
	 * 排序
	 */
	private Integer sort;
	
	public void setSort(Integer sort){
		this.sort = sort;
	}
	
	public Integer getSort(){
		return this.sort;
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

	public List<UpPhoto> getUpPhotoList() {
		return upPhotoList;
	}

	public void setUpPhotoList(List<UpPhoto> upPhotoList) {
		this.upPhotoList = upPhotoList;
	}

	public String getMainPhoto() {
		return mainPhoto;
	}

	public void setMainPhoto(String mainPhoto) {
		this.mainPhoto = mainPhoto;
	}

	public String toString() {
        return new ToStringBuilder(this)
        			.append("Id", getId())
		            .toString();
	}

	public boolean equals(Object other) {
        if (!(other instanceof UpSecondLevel)){
			return false;
		}
        UpSecondLevel castOther = (UpSecondLevel) other;
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