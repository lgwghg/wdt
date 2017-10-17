/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.service;

import java.util.List;
import java.util.Map;

import com.webside.exception.ServiceException;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserPhoto;

/**
 * 用户服务接口
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
public interface UserService {

	/**
	 * 按条件查询用户
	 * 
	 * @param parameter
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<UserEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 根据ID获取用户
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserEntity findById(String id);

	/**
	 * 根据手机获取用戶信息
	 * 
	 * @param mobile
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserEntity findByMobile(String mobile);

	/**
	 * 根据用户昵称获取用户
	 * 
	 * @param nickName
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserEntity findByNickName(String nickName);

	/**
	 * 根据邮箱获取用户信息
	 * 
	 * @param email
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserEntity findByEmail(String email);

	/**
	 * 根据角色id 查询出该角色下的所有用户信息
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<UserEntity> findRoleUserByRoleId(String roleId);

	/**
	 * 新增用户
	 * 
	 * @param UserEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int insert(UserEntity userEntity);

	/**
	 * 修改用户
	 * 
	 * @param UserEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updateById(UserEntity userEntity, boolean isUpdateRedis);

	/**
	 * 修改密码，并且发送邮箱通知
	 * 
	 * @param userEntity
	 * @param password
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updatePassword(UserEntity userEntity, String password);

	/**
	 * 更新用户表数据（数据库+缓存）
	 * 
	 * @param userEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updateUserOnly(UserEntity userEntity, boolean isUpdateRedis);

	/**
	 * 取消第三方绑定
	 * 
	 * @param userId
	 * @param thirdType
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updateCancelBind(String userId, String thirdType);

	/**
	 * 根据ID删除用户
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 该方法只用来登录获取用户信息
	 * 
	 * @param entity
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserEntity login(UserEntity entity);
	
	/**
	 * 根据用户id获取用户信息和用户增量信息
	 * @param entity
	 * @return
	 */
	public UserEntity findUserAndIncrementById(String userId);
	
	
	/**
	 * 保存用户头像
	 * @param photo
	 * @return
	 */
	int insertUserPhoto(UserPhoto photo);
}