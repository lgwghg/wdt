/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.model;

import java.io.Serializable;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;

/**
 * 事件点赞实体类
 *
 * @author aning
 * @date 2017-06-12 16:03:14
 */
public class TaskLikeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public TaskLikeEntity() {
	}

	public TaskLikeEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 点赞人
	 */
	private UserEntity createUser;

	/**
	 * 事件对象
	 */
	private TaskEntity task;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 状态
	 */
	private DictEntity status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserEntity getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}

	public TaskEntity getTask() {
		return task;
	}

	public void setTask(TaskEntity task) {
		this.task = task;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public DictEntity getStatus() {
		return status;
	}

	public void setStatus(DictEntity status) {
		this.status = status;
	}

}