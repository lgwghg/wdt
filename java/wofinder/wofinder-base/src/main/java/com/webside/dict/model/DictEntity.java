/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.dict.model;

import java.io.Serializable;

import com.webside.user.model.UserEntity;

/**
 * 字典实体类
 *
 * @author zengxn
 * @date 2017-05-16 15:05:05
 */
public class DictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DictEntity() {
	}

	public DictEntity(String value) {
		this.value = value;
	}
	
	public DictEntity(String id, String value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 名称
	 */
	private String label;

	/**
	 * 值
	 */
	private String value;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 文字列表样式
	 */
	private String labelClass;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 状态
	 */
	private DictEntity status;

	/**
	 * 创建者
	 */
	private UserEntity createUser;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改者
	 */
	private UserEntity updateUser;

	/**
	 * 修改时间
	 */
	private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLabelClass() {
		return labelClass;
	}

	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DictEntity getStatus() {
		return status;
	}

	public void setStatus(DictEntity status) {
		this.status = status;
	}

	public UserEntity getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public UserEntity getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserEntity updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}