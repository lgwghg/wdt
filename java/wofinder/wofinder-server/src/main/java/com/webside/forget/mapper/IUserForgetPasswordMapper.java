/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.forget.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.forget.model.UserForgetPassword;

/**
 * 用户忘记密码原因数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-15 17:53:27
 */
@Repository
public interface IUserForgetPasswordMapper extends BaseMapper<UserForgetPassword, String> 
{
	
}
