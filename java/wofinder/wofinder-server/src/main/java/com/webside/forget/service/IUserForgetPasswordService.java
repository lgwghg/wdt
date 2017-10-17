/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.forget.service;

import java.util.List;
import java.util.Map;
import com.webside.forget.model.UserForgetPassword;

/**
 * 用户忘记密码原因服务接口
 *
 * @author tianguifang
 * @date 2017-06-15 17:53:27
 */
public interface IUserForgetPasswordService 
{
	/*
	 * 按条件查询用户忘记密码原因
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserForgetPassword> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户忘记密码原因
	 * @param UserForgetPassword
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserForgetPassword entity);
	
	/*
	 * 修改用户忘记密码原因
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UserForgetPassword entity);
	
	/*
	 * 根据ID获取用户忘记密码原因
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserForgetPassword findById(String id);
	
	/*
	 * 根据对象删除用户忘记密码原因
	 * @param UserForgetPassword
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
}
