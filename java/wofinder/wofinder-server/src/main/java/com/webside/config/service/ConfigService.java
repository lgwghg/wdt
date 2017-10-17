package com.webside.config.service;

import java.util.List;
import java.util.Map;

import com.webside.config.model.Config;

public interface ConfigService {
	/**
	 * 根据配置key获取value
	 * @param key
	 * @return
	 * @author tianguifang
	 * @date 2016-08-29
	 */
	String findConfigValueByKey(String key);
	
	/**
	 * 修改配置
	 * @param config
	 * @return
	 * @author zengxn
	 */
	int updateByKeyValue(String key,String value);

	/**
	 * 按条件查询系统配置
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	List<Config> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增系统配置
	 * 
	 * @param Config
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	int insert(Config entity);

	/**
	 * 修改系统配置
	 * 
	 * @param entity
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	int updateById(Config entity);

	/**
	 * 根据ID获取系统配置
	 * 
	 * @param ID
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	Config findById(String id);

	/**
	 * 根据对象删除系统配置
	 * 
	 * @param Config
	 * 
	 * @throws Exception
	 * 
	 * @author tianguifang
	 */
	int deleteBatchById(List<String> ids);
}
