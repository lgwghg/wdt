/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.rights.service;

import java.util.List;
import java.util.Map;

import com.webside.exception.ServiceException;
import com.webside.rights.model.UserRoleEntity;

/**
 * 用户角色映射服务接口
 *
 * @author zengxn
 * @date 2017-05-04 10:25:40
 */
public interface UserRoleService {
	/**
	 * 按条件查询用户角色映射
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<UserRoleEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增用户角色映射
	 * 
	 * @param UserRoleEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int insert(UserRoleEntity entity);

	/**
	 * 修改用户角色映射
	 * 
	 * @param UserRoleEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updateById(UserRoleEntity entity);

	/**
	 * 根据ID获取用户角色映射
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	UserRoleEntity findById(String id);

	/**
	 * 根据ID删除用户角色映射
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据userId查询用户角色映射
	 * 
	 * @param userId
	 * @return
	 */
	UserRoleEntity findByUserId(String userId);

	/**
	 * 根据entity更新用户角色映射
	 * 
	 * @param entity
	 * @return
	 */
	int updateByEntity(UserRoleEntity entity);

	/**
	 * 根据userId删除用户角色映射
	 * 
	 * @param userId
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteByUserId(String userId);

	/**
	 * 根据角色id查询用户数量
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	int findCountByRoleId(String roleId);

	/**
	 * 根据角色Id查询用户Ids
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<String> findUserIdByRoleId(String roleId);
}
