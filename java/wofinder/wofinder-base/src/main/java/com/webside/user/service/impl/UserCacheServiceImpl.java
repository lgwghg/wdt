package com.webside.user.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.common.CacheConstant;
import com.webside.redis.RedisManager;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrementEntity;
import com.webside.user.model.vo.UserCacheVo;
import com.webside.user.service.UserCacheService;
import com.webside.user.service.UserService;

/**
 * 用户缓存服务接口实现类
 * 
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
@Service("userCacheService")
public class UserCacheServiceImpl implements UserCacheService {

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * redis 定义
	 */
	@Autowired
	private RedisManager redisManager;

	/**
	 * 线程池定义
	 */
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();// 启动线程池

	/**
	 * 缓存中的用户信息，转换成userEntity 对象
	 * 
	 * @author zengxn
	 */
	private UserEntity exchangeUserCacheVoToUser(UserCacheVo vo) {
		// 用户
		UserEntity user = new UserEntity();
		user.setId(vo.getId());
		user.setNickName(vo.getN());
		user.setMobile(vo.getM());
		user.setPhoto(vo.getP());
		user.setEmail(vo.getE());
		user.setSign(vo.getS());
		// 用户增量
		UserIncrementEntity userIncrement = new UserIncrementEntity();
		userIncrement.setUser(user);
		user.setUserIncrement(userIncrement);

		return user;
	}

	/**
	 * 根据用户id，查询用户缓存信息
	 * 
	 * @author zengxn
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserEntity getUserEntityByUserId(String userId) {
		UserCacheVo vo = redisManager.get(CacheConstant.UserConstant.USER_REDIS_CACHE_KEY + userId, UserCacheVo.class);
		if (vo != null) {
			UserEntity user = exchangeUserCacheVoToUser(vo);
			return user;
		} else {
			UserEntity userEntity = userService.findById(userId);// 用户信息部分返回
			if (userEntity != null) {
				UserEntity user = new UserEntity();
				user.setId(userEntity.getId());
				user.setNickName(userEntity.getNickName());
				user.setMobile(userEntity.getMobile());
				user.setPhoto(userEntity.getPhoto());
				user.setEmail(userEntity.getEmail());
				user.setSign(userEntity.getSign());
				user.setUserIncrement(userEntity.getUserIncrement());
				updateUserToRedis(user);
				return user;
			}
		}
		return null;
	}

	/**
	 * 更新用户缓存信息
	 * 
	 * @author zengxn
	 */
	@Override
	public void updateUserToRedis(UserEntity userEntity) {
		if (userEntity != null && StringUtils.isNotBlank(userEntity.getId())) {
			cachedThreadPool.execute(new UpdateRedisUserRunner(userEntity));
		}
	}

	/**
	 * 更新redis中的user信息
	 * 
	 * @author zengxn
	 */
	class UpdateRedisUserRunner implements Runnable {
		private UserEntity userEntity;

		public UpdateRedisUserRunner(UserEntity userEntity) {
			this.userEntity = userEntity;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			UserCacheVo vo = redisManager.get(CacheConstant.UserConstant.USER_REDIS_CACHE_KEY + userEntity.getId(), UserCacheVo.class);
			if (vo == null) {
				vo = new UserCacheVo();
			}
			vo = exchangeUserEntity(vo, userEntity);
			// 存缓存
			redisManager.set(CacheConstant.UserConstant.USER_REDIS_CACHE_KEY + userEntity.getId(), vo);
		}

		/**
		 * userEntity对象转换成缓存中的userCacheVo对象
		 * 
		 * @author zengxn
		 */
		private UserCacheVo exchangeUserEntity(UserCacheVo vo, UserEntity userEntity) {
			// 用户信息
			if (StringUtils.isNotBlank(userEntity.getId())) {
				vo.setId(userEntity.getId());
			}
			if (StringUtils.isNotBlank(userEntity.getNickName())) {
				vo.setN(userEntity.getNickName());
			}
			if (StringUtils.isNotBlank(userEntity.getMobile())) {
				vo.setM(userEntity.getMobile());
			}
			if (StringUtils.isNotBlank(userEntity.getPhoto())) {
				vo.setP(userEntity.getPhoto());
			}
			if (StringUtils.isNotBlank(userEntity.getEmail())) {
				vo.setE(userEntity.getEmail());
			}
			if (StringUtils.isNotBlank(userEntity.getSign())) {
				vo.setS(userEntity.getSign());
			}
			// 用户增量信息
			if (userEntity.getUserIncrement() != null) {

			}
			return vo;
		}
	}

}
