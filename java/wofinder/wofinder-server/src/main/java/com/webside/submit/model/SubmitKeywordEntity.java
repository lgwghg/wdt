/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.submit.model;

import java.io.Serializable;
import java.util.List;

import com.webside.dict.model.DictEntity;
import com.webside.user.model.UserEntity;

/**
 * 提交搜索关键字实体类
 *
 * @author zengxn
 * @date 2017-06-13 17:49:37
 */
public class SubmitKeywordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public SubmitKeywordEntity() {
	}

	public SubmitKeywordEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 关键字
	 */
	private String keyword;

	/**
	 * 状态，1有效，0无效
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
	 * 提交搜索关键字url集合
	 */
	private List<SubmitUrlEntity> submitUrlList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public List<SubmitUrlEntity> getSubmitUrlList() {
		return submitUrlList;
	}

	public void setSubmitUrlList(List<SubmitUrlEntity> submitUrlList) {
		this.submitUrlList = submitUrlList;
	}
}