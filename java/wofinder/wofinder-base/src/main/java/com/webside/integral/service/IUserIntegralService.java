/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.integral.service;

import java.util.List;
import java.util.Map;

import com.webside.integral.model.UserIntegral;

/**
 * 用户积分服务接口
 *
 * @author tianguifang
 * @date 2017-06-15 17:46:59
 */
public interface IUserIntegralService 
{
	/*
	 * 按条件查询用户积分
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserIntegral> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户积分
	 * @param UserIntegral
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserIntegral entity);
	
	/*
	 * 修改用户积分
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UserIntegral entity);
	
	/*
	 * 根据ID获取用户积分
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserIntegral findById(String id);
	
	/*
	 * 根据对象删除用户积分
	 * @param UserIntegral
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 插入用户积分数据
	 * @param userId
	 * @param integral
	 * @param remark
	 * @return
	 * @author tianguifang
	 */
	public String insertIntegral(UserIntegral entity);
	/**
	 * 根据用户和积分类型，查询积分
	 * @param id
	 * @param i
	 * @return
	 * @author tianguifang
	 */
	public List<UserIntegral> findByUserIdAndType(String userId, int integralType);
}
