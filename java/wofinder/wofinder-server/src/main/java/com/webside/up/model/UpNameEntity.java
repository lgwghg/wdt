/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.model;

import java.io.Serializable;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;

/**
 * 视频作者名称实体类
 *
 * @author zengxn
 * @date 2017-04-15 18:29:36
 */
public class UpNameEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public UpNameEntity() {
	}

	public UpNameEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 视频作者id
	 */
	private UpEntity up;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类别
	 */
	private DictEntity type;

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

	public UpEntity getUp() {
		return up;
	}

	public void setUp(UpEntity up) {
		this.up = up;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DictEntity getType() {
		return type;
	}

	public void setType(DictEntity type) {
		this.type = type;
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