/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.uswitch.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.uswitch.model.UserSwitch;

/**
 * 用户开关数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-27 15:04:58
 */
@Repository
public interface IUserSwitchMapper extends BaseMapper<UserSwitch, String> 
{

	List<UserSwitch> queryUserSwitchByUserId(String userId);
	
}
