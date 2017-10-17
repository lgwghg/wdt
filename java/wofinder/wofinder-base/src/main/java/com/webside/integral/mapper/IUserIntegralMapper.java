/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.integral.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.integral.model.UserIntegral;

/**
 * 用户积分数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-15 17:46:59
 */
@Repository
public interface IUserIntegralMapper extends BaseMapper<UserIntegral, String> 
{

	List<UserIntegral> findByUserIdAndType(Map<String, Object> param);
	
}
