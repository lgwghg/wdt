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
import com.webside.rights.model.RoleResourceEntity;

/**
 * 角色权限映射数据访问接口
 *
 * @author zengxn
 * @date 2017-05-05 14:13:38
 */
@Repository
public interface RoleResourceMapper extends BaseMapper<RoleResourceEntity, String> {
	/**
	 * 根据角色id删除角色的权限信息
	 * 
	 * @param roleId
	 */
	public int deleteByRoleId(@Param(value = "roleId") String roleId);

	/**
	 * 根据资源id删除角色的权限信息
	 * 
	 * @param roleId
	 */
	public int deleteByResourceId(@Param(value = "resourceId") String resourceId);

	/**
	 * 根据roleId获取角色权限映射数量
	 * 
	 * @param roleId
	 * @author zengxn
	 */
	public int findCountByRoleId(@Param(value = "roleId") String roleId);

	/**
	 * 批量添加角色和权限映射信息
	 * 
	 * @param parameter
	 * @return
	 */
	public int insertByBatch(List<RoleResourceEntity> parameter);
}
