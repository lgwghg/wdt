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

import com.webside.submit.model.SubmitKeywordEntity;

/**
 * 提交搜索关键字服务接口
 *
 * @author zengxn
 * @date 2017-06-13 17:49:37
 */
public interface SubmitKeywordService {
	/**
	 * 按条件查询提交搜索关键字
	 * 
	 * @author zengxn
	 */
	List<SubmitKeywordEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增提交搜索关键字
	 * 
	 * @author zengxn
	 */
	int insert(final SubmitKeywordEntity entity);

	/**
	 * 根据ID更新修改提交搜索关键字
	 * 
	 * @author zengxn
	 */
	int updateById(final SubmitKeywordEntity entity);

	/**
	 * 根据ID获取提交搜索关键字
	 * 
	 * @author zengxn
	 */
	SubmitKeywordEntity findById(String id);

	/**
	 * 根据ID删除提交搜索关键字
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);
}
