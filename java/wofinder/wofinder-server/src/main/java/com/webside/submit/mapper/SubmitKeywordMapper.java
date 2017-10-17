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
import com.webside.submit.model.SubmitKeywordEntity;

/**
 * 提交搜索关键字数据访问接口
 *
 * @author zengxn
 * @date 2017-06-13 17:49:37
 */
@Repository
public interface SubmitKeywordMapper extends BaseMapper<SubmitKeywordEntity, String> 
{
	
}
