/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.uswitch.service;

import java.util.List;
import java.util.Map;

import com.webside.user.uswitch.model.UserSwitch;

/**
 * 用户开关服务接口
 *
 * @author tianguifang
 * @date 2017-06-27 15:04:58
 */
public interface IUserSwitchService 
{
	/*
	 * 按条件查询用户开关
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserSwitch> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户开关
	 * @param UserSwitch
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserSwitch entity);
	
	/*
	 * 修改用户开关
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UserSwitch entity);
	
	/*
	 * 根据ID获取用户开关
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserSwitch findById(String id);
	
	/*
	 * 根据对象删除用户开关
	 * @param UserSwitch
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 根据用户id查询用户的开关设置
	 * @param userId
	 * @return
	 */
	public List<UserSwitch> queryUserSwitchByUserId(String userId);
}
