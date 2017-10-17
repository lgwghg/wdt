/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.rights.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.rights.mapper.UserRoleMapper;
import com.webside.rights.model.UserRoleEntity;
import com.webside.rights.service.UserRoleService;
import com.webside.util.IdGen;

/**
 * 用户角色映射服务实现类
 *
 * @author zengxn
 * @date 2017-05-04 10:25:40
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends AbstractService<UserRoleEntity, String> implements UserRoleService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userRoleMapper);
	}

	/**
	 * 用户角色映射 DAO定义
	 */
	@Autowired
	private UserRoleMapper userRoleMapper;

	/**
	 * 新增用户角色映射
	 * 
	 * @param UserRoleEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int insert(UserRoleEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增用户角色映射出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改用户角色映射
	 * 
	 * @param UserRoleEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int updateById(UserRoleEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改用户角色映射出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据userId查询用户角色映射
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public UserRoleEntity findByUserId(String userId) {
		try {
			UserRoleEntity entity = new UserRoleEntity();
			entity.setUserId(userId);
			List<UserRoleEntity> list = userRoleMapper.findByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据userId查询用户角色映射出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据entity更新用户角色映射
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public int updateByEntity(UserRoleEntity entity) {
		try {
			return userRoleMapper.updateByEntity(entity);
		} catch (Exception e) {
			logger.error("根据entity更新用户角色映射出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据userId删除用户角色映射
	 * 
	 * @param userId
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int deleteByUserId(String userId) {
		try {
			UserRoleEntity entity = new UserRoleEntity();
			entity.setUserId(userId);
			return userRoleMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据userId删除用户角色映射出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据角色id查询用户数量
	 * 
	 * @param roleId
	 *            角色id
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int findCountByRoleId(String roleId) {
		try {
			return userRoleMapper.findCountByRoleId(roleId);
		} catch (Exception e) {
			logger.error("根据角色id查询用户数量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据角色Id查询用户Ids
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public List<String> findUserIdByRoleId(String roleId) {
		try {
			return userRoleMapper.findUserIdByRoleId(roleId);
		} catch (Exception e) {
			logger.error("根据角色Id查询用户Ids出错：", e);
			throw new ServiceException(e);
		}
	}
}
