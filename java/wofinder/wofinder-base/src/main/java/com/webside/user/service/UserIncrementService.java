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
import com.webside.user.model.UserIncrementEntity;

/**
 * 用户增量服务接口
 *
 * @author zengxn
 * @date 2017-05-05 11:09:24
 */
public interface UserIncrementService {

	/**
	 * 按条件查询用户增量
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<UserIncrementEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增用户增量
	 * 
	 * @param UserIncrementEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int insert(final UserIncrementEntity entity);

	/**
	 * 修改用户增量
	 * 
	 * @param UserIncrementEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updateById(final UserIncrementEntity entity);

	/**
	 * 根据ID获取用户增量
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserIncrementEntity findById(String id);

	/**
	 * 根据ID删除用户增量
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据userId获取用户增量
	 * 
	 * @param userId
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserIncrementEntity findByUserId(String userId);

	int updateByUserId(UserIncrementEntity userIncrement);
}
