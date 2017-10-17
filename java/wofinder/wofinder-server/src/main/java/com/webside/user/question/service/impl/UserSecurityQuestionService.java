/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.question.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.user.question.service.IUserSecurityQuestionService;
import com.webside.user.question.mapper.IUserSecurityQuestionMapper;
import com.webside.user.question.model.UserSecurityQuestion;

/**
 * 用户安全问题服务实现类
 *
 * @author tianguifang
 * @date 2017-06-27 16:48:09
 */
@Service("userSecurityQuestionService")
public class UserSecurityQuestionService extends AbstractService<UserSecurityQuestion, String> implements IUserSecurityQuestionService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userSecurityQuestionMapper);
	}

	/**
	 * 用户安全问题 DAO定义
	 */
	@Autowired
	private IUserSecurityQuestionMapper userSecurityQuestionMapper;

	@Override
	public List<UserSecurityQuestion> queryQuestionByUserId(String userId) {
		return userSecurityQuestionMapper.queryQuestionByUserId(userId);
	}
}
