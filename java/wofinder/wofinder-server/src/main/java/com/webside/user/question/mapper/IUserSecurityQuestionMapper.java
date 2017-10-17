/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.question.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.question.model.UserSecurityQuestion;

/**
 * 用户安全问题数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-27 16:48:09
 */
@Repository
public interface IUserSecurityQuestionMapper extends BaseMapper<UserSecurityQuestion, String> 
{

	List<UserSecurityQuestion> queryQuestionByUserId(String userId);
	
}
