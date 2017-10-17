/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.question.service;

import java.util.List;
import java.util.Map;

import com.webside.user.question.model.UserSecurityQuestion;

/**
 * 用户安全问题服务接口
 *
 * @author tianguifang
 * @date 2017-06-27 16:48:09
 */
public interface IUserSecurityQuestionService 
{
	/*
	 * 按条件查询用户安全问题
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserSecurityQuestion> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户安全问题
	 * @param UserSecurityQuestion
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserSecurityQuestion entity);
	
	/*
	 * 修改用户安全问题
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UserSecurityQuestion entity);
	
	/*
	 * 根据ID获取用户安全问题
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserSecurityQuestion findById(String id);
	
	/*
	 * 根据对象删除用户安全问题
	 * @param UserSecurityQuestion
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/*
	 * 根据用户id查询用户安全问题
	 * @param UserSecurityQuestion
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserSecurityQuestion> queryQuestionByUserId(String userId);
}
