/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.submit.service;

import java.util.List;
import java.util.Map;
import com.webside.submit.model.SubmitUrlEntity;

/**
 * 提交搜索关键字url服务接口
 *
 * @author zengxn
 * @date 2017-06-13 17:49:15
 */
public interface SubmitUrlService 
{
	/**
	 * 按条件查询提交搜索关键字url
	 * @author zengxn
	 */
	List<SubmitUrlEntity> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增提交搜索关键字url
	 * @author zengxn
	 */
	int insert(final SubmitUrlEntity entity);
	
	/**
	 * 根据ID更新修改提交搜索关键字url
	 * @author zengxn
	 */
	int updateById(final SubmitUrlEntity entity);
	
	/**
	 * 根据ID获取提交搜索关键字url
	 * @author zengxn
	 */
	SubmitUrlEntity findById(String id);
	
	/**
	 * 根据ID删除提交搜索关键字url
	 * @author zengxn
	 */
	int deleteById(String id);
}
