package com.webside.common.redis.listener;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component(value = "messageDelegateListener")
public class ListenMessage
{
	public void handleMessage(Serializable message) 
	{
		System.out.println(message);
	}
}