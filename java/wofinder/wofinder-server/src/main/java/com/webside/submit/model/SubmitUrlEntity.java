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

/**
 * 提交搜索关键字url实体类
 *
 * @author zengxn
 * @date 2017-06-13 17:49:15
 */
public class SubmitUrlEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public SubmitUrlEntity() {
	}

	public SubmitUrlEntity(String id) {
		this.id = id;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 提交搜索关键字id
	 */
	private SubmitKeywordEntity submitKeyword;

	/**
	 * url
	 */
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SubmitKeywordEntity getSubmitKeyword() {
		return submitKeyword;
	}

	public void setSubmitKeyword(SubmitKeywordEntity submitKeyword) {
		this.submitKeyword = submitKeyword;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}