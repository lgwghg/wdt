/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.rights.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.rights.model.UserRoleEntity;

/**
 * 用户角色映射数据访问接口
 *
 * @author zengxn
 * @date 2017-05-04 10:25:40
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleEntity, String> {
	/**
	 * 根据entity查询用户角色映射
	 * 
	 * @param entity
	 * @return
	 */
	public List<UserRoleEntity> findByEntity(UserRoleEntity entity);

	/**
	 * 根据entity更新用户角色映射
	 * 
	 * @param entity
	 * @return
	 */
	public int updateByEntity(UserRoleEntity entity);

	/**
	 * 根据entity删除用户角色映射
	 * 
	 * @param entity
	 * @return
	 */
	public int deleteByEntity(UserRoleEntity entity);

	/**
	 * 根据角色id查询用户数量
	 * 
	 * @param roleId
	 * @return
	 */
	public int findCountByRoleId(String roleId);

	/**
	 * 根据角色Id查询用户Ids
	 * 
	 * @param roleId
	 * @return
	 */
	public List<String> findUserIdByRoleId(@Param(value = "roleId") String roleId);
}
