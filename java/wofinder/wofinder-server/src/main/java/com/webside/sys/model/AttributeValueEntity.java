/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.model;

import java.io.Serializable;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;

/**
 * 属性值关联实体类
 *
 * @author zengxn
 * @date 2017-04-15 18:26:08
 */
public class AttributeValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public AttributeValueEntity() {
	}

	public AttributeValueEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 属性id
	 */
	private AttributeEntity attribute;

	/**
	 * 属性值id
	 */
	private ValueEntity value;

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

	public AttributeEntity getAttribute() {
		return attribute;
	}

	public void setAttribute(AttributeEntity attribute) {
		this.attribute = attribute;
	}

	public ValueEntity getValue() {
		return value;
	}

	public void setValue(ValueEntity value) {
		this.value = value;
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