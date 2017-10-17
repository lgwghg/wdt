/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.interest.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.interest.model.UserInterest;

/**
 * 用户兴趣数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-23 15:26:40
 */
@Repository
public interface IUserInterestMapper extends BaseMapper<UserInterest, String> 
{
	/**
	 * 根据用户id查询用户兴趣
	 * @param userId
	 * @return
	 */
	List<UserInterest> queryUserInterestByUserId(String userId);
	
}
