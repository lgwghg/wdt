package com.webside.loginfo.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 
 * @ClassName: LogInfoEntity
 * @Description: 用户操作日志信息
 * @author gaogang
 * @date 2016年7月12日 下午2:44:55
 *
 */
@Alias("logInfoEntity")
public class LogInfoEntity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * id
	 */
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * 管理员id
	 */
	private String userId;
	/*
	 * 用户账户
	 */
	private String accountName;
	/*
	 * 日期
	 */
	private Date createTime;
	/*
	 * 日志内容
	 */
	private String content; 
	/*
	 * 操作(主要是"添加"、"修改"、"删除"、"授权")等
	 */
	private String operation;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
