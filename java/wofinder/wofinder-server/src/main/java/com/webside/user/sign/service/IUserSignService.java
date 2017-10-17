/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.sign.service;

import java.util.List;
import java.util.Map;

import com.webside.user.sign.entities.UserSign;

/**
 * 用户签到服务接口
 *
 * @author tianguifang
 * @date 2016-12-06 15:04:37
 */
public interface IUserSignService 
{
	/*
	 * 按条件查询用户签到
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserSign> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户签到
	 * @param UserSign
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserSign entity);
	
	/*
	 * 修改用户签到
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UserSign entity);
	
	/*
	 * 根据ID获取用户签到
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserSign findById(String id);
	
	/*
	 * 根据对象删除用户签到
	 * @param UserSign
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);

	/**
	 * 查询签到记录
	 * 
	 * @param userId
	 * @return
	 */
	UserSign querySignInToday(String userId);
}
