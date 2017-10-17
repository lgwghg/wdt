package com.webside.user.service;

import com.webside.user.model.UserEntity;

/**
 * 用户缓存服务接口
 * 
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
public interface UserCacheService {

	/**
	 * 根据用户id，查询用户缓存信息
	 * 
	 * @author zengxn
	 */
	UserEntity getUserEntityByUserId(String userId);

	/**
	 * 更新用户缓存信息
	 * 
	 * @author zengxn
	 */
	void updateUserToRedis(UserEntity userEntity);
}
