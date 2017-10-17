/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.interest.service;

import java.util.List;
import java.util.Map;

import com.webside.user.interest.model.UserInterest;

/**
 * 用户兴趣服务接口
 *
 * @author tianguifang
 * @date 2017-06-23 15:26:40
 */
public interface IUserInterestService 
{
	/*
	 * 按条件查询用户兴趣
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserInterest> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户兴趣
	 * @param UserInterest
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserInterest entity);
	
	/*
	 * 修改用户兴趣
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UserInterest entity);
	
	/*
	 * 根据ID获取用户兴趣
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserInterest findById(String id);
	
	/*
	 * 根据对象删除用户兴趣
	 * @param UserInterest
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 根据用户id查询用户兴趣
	 * @param userId
	 * @return
	 */
	public List<UserInterest> queryUserInterestByUserId(String userId);
}
