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

/**
 *  entity
 *
 * @author tianguifang
 * @date 2017-08-21 16:50:12
 */
public class VideoValueInform implements Serializable
{
	private static final long serialVersionUID = 1L;

	public VideoValueInform(){}
	

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
	 * 视频标签id
	 */
	private String videoValueId;
	/** 视频id */
	private String videoId;
	private String videoShortId;
	/** 属性标签id */
	private String valueId;
	/** 视频标题 */
	private String videoTitle;
	/** 标签名称 */
	private String valueName;
	/**
	 * 举报人
	 */
	private String createName;
	/**
	 * 举报状态
	 */
	private Integer informStatus;
	
	public String getVideoShortId() {
		return videoShortId;
	}

	public void setVideoShortId(String videoShortId) {
		this.videoShortId = videoShortId;
	}

	public Integer getInformStatus() {
		return informStatus;
	}

	public void setInformStatus(Integer informStatus) {
		this.informStatus = informStatus;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

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
	 * 举报原因  0:内容不相关 1：敏感信息 2：恶意攻击 3：剧透内容 4：恶意删除
	 */
	private Integer informReason;
	
	public void setInformReason(Integer informReason){
		this.informReason = informReason;
	}
	
	public Integer getInformReason(){
		return this.informReason;
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
                    .toString();
	}

	public boolean equals(Object other) {
        if (!(other instanceof VideoValueInform)){
			return false;
		}
        VideoValueInform castOther = (VideoValueInform) other;
        return new EqualsBuilder()
                   .isEquals();
	}

	public int hashCode() {
        return new HashCodeBuilder()
                    .toHashCode();
	}

}