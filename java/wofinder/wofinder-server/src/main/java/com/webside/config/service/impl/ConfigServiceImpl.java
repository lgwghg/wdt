package com.webside.config.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.config.mapper.ConfigMapper;
import com.webside.config.model.Config;
import com.webside.config.service.ConfigService;
import com.webside.redis.RedisManager;

@Service("configService")
public class ConfigServiceImpl extends AbstractService<Config, String> implements ConfigService {
	private static final Log LOG = LogFactory.getLog(ConfigServiceImpl.class);
	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private RedisManager redisManager;
	
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(configMapper);
	}
	
	/**
	 * 根据配置key获取value
	 * @param key
	 * @return
	 * @author tianguifang
	 * @date 2016-08-29
	 */
	@Override
	public String findConfigValueByKey(String key) {
		String redisKey = "wodota:config:";
		redisKey = redisKey + key;
		// 先从缓存获取
		String configValue = redisManager.get(redisKey);
		if (configValue == null) {
			// 缓存获取不到从数据库获取
			configValue = configMapper.findConfigValueByKey(key);
			if (configValue != null) {
				redisManager.setex(redisKey, configValue, CacheConstant.TEN_MINITES);// 缓存10分钟
			} else {
				// TODO 发邮件告知开发者配置
				LOG.error("获取配置未找到，请尽快处理，配置key=" + key);
			}
		}
		return configValue;
	}
	
	@Override
	public int updateByKeyValue(String key, String value) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", key);
		param.put("value", value);
		param.put("date", System.currentTimeMillis());
		return configMapper.updateByKeyValue(param);
	}
	
}
