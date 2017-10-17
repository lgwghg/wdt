package com.webside.rights.service;

import java.util.List;
import java.util.Map;

import com.webside.rights.model.RoleEntity;

/**
 * 角色服务接口
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
public interface RoleService {

	public List<RoleEntity> queryListByPage(Map<String, Object> parameter);

	public RoleEntity findByName(String name);
	
	public int insert(RoleEntity roleEntity);
	
	public RoleEntity findById(String roleId);

	public int updateById(RoleEntity roleEntity);
    
	public boolean deleteRoleById(String roleId);
    
	public boolean addRolePermBatch(String roleId, List<String> ids);
    
	/**
	 * 根据用户id查询用户角色
	 * 
	 * @param userId
	 * @return
	 */
	public RoleEntity findByUserId(String userId);

}