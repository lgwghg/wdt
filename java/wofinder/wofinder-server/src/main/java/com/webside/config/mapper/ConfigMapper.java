package com.webside.config.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.config.model.Config;

@Repository
public interface ConfigMapper extends BaseMapper<Config, String>{
	/**
	 * 根据配置key获取value
	 * @param key
	 * @return
	 * @author tianguifang
	 * @date 2016-08-29
	 */
	public String findConfigValueByKey(String key);
	
	/**
	 * 修改配置
	 * @param param
	 * @return
	 */
	public int updateByKeyValue(Map<String, Object> param);
}
