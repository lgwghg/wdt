/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.submit.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.submit.model.SubmitUrlEntity;

/**
 * 提交搜索关键字url数据访问接口
 *
 * @author zengxn
 * @date 2017-06-13 17:49:15
 */
@Repository
public interface SubmitUrlMapper extends BaseMapper<SubmitUrlEntity, String> 
{
	
}
