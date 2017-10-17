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
import com.webside.rights.model.ResourceEntity;
import com.webside.rights.model.RoleResourceEntity;

/**
 * 角色权限映射服务接口
 *
 * @author zengxn
 * @date 2017-05-05 14:13:38
 */
public interface RoleResourceService {
	/**
	 * 按条件查询角色权限映射
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<RoleResourceEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增角色权限映射
	 * 
	 * @param RoleResourceEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int insert(RoleResourceEntity entity);

	/**
	 * 修改角色权限映射
	 * 
	 * @param RoleResourceEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updateById(RoleResourceEntity entity);

	/**
	 * 根据ID获取角色权限映射
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	RoleResourceEntity findById(String id);

	/**
	 * 根据ID删除角色权限映射
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 添加资源(权限)时同步给超级管理员赋予该权限
	 * 
	 * @param resourceEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	boolean insertRoleAndResource(ResourceEntity resourceEntity);

	/**
	 * 根据角色id删除角色的权限信息
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteByRoleId(String roleId);

	/**
	 * 根据资源id删除角色的权限信息
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteByResourceId(String resourceId);

	/**
	 * 根据roleId获取角色权限映射数量
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	int findCountByRoleId(String roleId);

	/**
	 * 批量添加角色和权限映射信息
	 * 
	 * @param parameter
	 * @throws ServiceException
	 * @author zengxn
	 */
	int insertByBatch(List<RoleResourceEntity> parameter);
}
