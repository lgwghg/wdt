/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.rights.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.rights.mapper.RoleResourceMapper;
import com.webside.rights.model.ResourceEntity;
import com.webside.rights.model.RoleEntity;
import com.webside.rights.model.RoleResourceEntity;
import com.webside.rights.service.ResourceService;
import com.webside.rights.service.RoleResourceService;
import com.webside.rights.service.RoleService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.util.IdGen;

/**
 * 角色权限映射服务实现类
 *
 * @author zengxn
 * @date 2017-05-05 14:13:38
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl extends AbstractService<RoleResourceEntity, String> implements RoleResourceService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(roleResourceMapper);
	}

	/**
	 * 角色权限映射 DAO定义
	 */
	@Autowired
	private RoleResourceMapper roleResourceMapper;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	/**
	 * 新增角色权限映射
	 * 
	 * @param RoleResourceEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int insert(RoleResourceEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			entity.setCreateTime(new Date());
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增角色权限映射出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改角色权限映射
	 * 
	 * @param RoleResourceEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int updateById(RoleResourceEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改角色权限映射出错：", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean insertRoleAndResource(ResourceEntity resourceEntity) {
		try {
			// 1、添加资源
			resourceService.insert(resourceEntity);
			// 2、超级管理员直接赋予该权限
			RoleEntity role = roleService.findByName("超级管理员");
			RoleResourceEntity roleResource = new RoleResourceEntity();
			roleResource.setResourceId(resourceEntity.getId());
			roleResource.setRoleId(role.getId());
			insert(roleResource);
			// 清空所有用户权限,重新加载权限
			ShiroAuthenticationManager.clearAllUserAuth();
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据角色id删除角色的权限信息
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int deleteByRoleId(String roleId) {
		try {
			return roleResourceMapper.deleteByRoleId(roleId);
		} catch (Exception e) {
			logger.error("根据角色id删除角色的权限信息出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据资源id删除角色的权限信息
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int deleteByResourceId(String resourceId) {
		try {
			return roleResourceMapper.deleteByResourceId(resourceId);
		} catch (Exception e) {
			logger.error("根据资源id删除角色的权限信息出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据roleId获取角色权限映射数量
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int findCountByRoleId(String roleId) {
		try {
			return roleResourceMapper.findCountByRoleId(roleId);
		} catch (Exception e) {
			logger.error("根据roleId获取角色权限映射数量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量添加角色和权限映射信息
	 * 
	 * @param parameter
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int insertByBatch(List<RoleResourceEntity> parameter) {
		try {
			return roleResourceMapper.insertByBatch(parameter);
		} catch (Exception e) {
			logger.error("批量添加角色和权限映射信息出错：", e);
			throw new ServiceException(e);
		}
	}
}
