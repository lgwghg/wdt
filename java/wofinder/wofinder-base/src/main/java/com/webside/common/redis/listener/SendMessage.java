package com.webside.common.redis.listener;
import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class SendMessage 
{
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	
	public void sendMessage(String channel, Serializable message) 
	{
		redisTemplate.convertAndSend(channel, JSON.toJSONString(message));
    }
}