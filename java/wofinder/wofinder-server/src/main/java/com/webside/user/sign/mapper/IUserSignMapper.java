/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.sign.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.sign.entities.UserSign;

/**
 * 用户签到数据访问接口
 *
 * @author tianguifang
 * @date 2016-12-06 15:04:37
 */
@Repository
public interface IUserSignMapper extends BaseMapper<UserSign, String> 
{

	/**
	 * 查询当天签到记录
	 * 
	 * @param param
	 * @return
	 */
	UserSign querySignInToday(Map<String, Object> param);
	
}
