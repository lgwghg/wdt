/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.forget.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.forget.service.IUserForgetPasswordService;
import com.webside.forget.mapper.IUserForgetPasswordMapper;
import com.webside.forget.model.UserForgetPassword;

/**
 * 用户忘记密码原因服务实现类
 *
 * @author tianguifang
 * @date 2017-06-15 17:53:27
 */
@Service("userForgetPasswordService")
public class UserForgetPasswordService extends AbstractService<UserForgetPassword, String> implements IUserForgetPasswordService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userForgetPasswordMapper);
	}

	/**
	 * 用户忘记密码原因 DAO定义
	 */
	@Autowired
	private IUserForgetPasswordMapper userForgetPasswordMapper;
}
