package com.webside.config.model;

import java.io.Serializable;
/**
 * 配置model
 * @author tianguifang
 * @date 2016-08-29
 */
public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5356117841560439740L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 配置组
	 */
	private String configGroup;
	/**
	 * 配置键
	 */
	private String configKey;
	/**
	 * 配置值
	 */
	private String configValue;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 更新时间
	 */
	private Long updateTime;
	/**
	 * 创建人Id
	 */
	private String createOperatorId;
	/**
	 * 更新人id
	 */
	private String updateOperatorId;
	
	public String getConfigGroup() {
		return configGroup;
	}
	public void setConfigGroup(String configGroup) {
		this.configGroup = configGroup;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateOperatorId() {
		return createOperatorId;
	}
	public void setCreateOperatorId(String createOperatorId) {
		this.createOperatorId = createOperatorId;
	}
	public String getUpdateOperatorId() {
		return updateOperatorId;
	}
	public void setUpdateOperatorId(String updateOperatorId) {
		this.updateOperatorId = updateOperatorId;
	}
	
	
}
