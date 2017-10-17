package com.webside.rights.service;

import java.util.List;
import java.util.Map;

import com.webside.rights.model.ResourceEntity;

public interface ResourceService{

	/**
	 * 自定义方法
	 * 获取用户ID对应的资源信息
	 * @param userId
	 * @return
	 */
	public List<ResourceEntity> findResourcesByUserId(String userId);

	/**
	 * 自定义方法
	 * 获取用户ID对应的资源菜单信息
	 * @param userId
	 * @return
	 */
	public List<ResourceEntity> findResourcesMenuByUserId(String userId);
	
	public List<ResourceEntity> queryListByPage(Map<String, Object> parameter);
	
	public List<ResourceEntity> queryTreeGridListByPage(Map<String, Object> parameter);
	
	public ResourceEntity findByName(String name);
	
	public ResourceEntity findById(String id);

	public int updateById(ResourceEntity resourceEntity);
    
    public int deleteBatchById(List<String> resourceIds);
    
    public List<ResourceEntity> queryResourceList(Map<String, Object> parameter);
    
    public int insert(ResourceEntity resourceEntity);
    
    public int count(Map<String, Object> parameter);
    
    public boolean deleteRoleAndResource(List<String> resourceIds);
}
