/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.rights.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色映射实体类
 *
 * @author zengxn
 * @date 2017-05-04 10:21:55
 */
public class UserRoleEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String id;
	
	/**
	 * 角色id
	 */
	private String roleId;
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}