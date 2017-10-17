/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.service;

import java.util.List;
import java.util.Map;

import com.webside.sys.model.ValueEntity;

/**
 * 属性值服务接口
 *
 * @author zengxn
 * @date 2017-04-16 14:38:03
 */
public interface ValueService {
	
	public static String BEANNAME = "valueService";
	
	/**
	 * 按条件查询属性值
	 * 
	 * @author zengxn
	 */
	List<ValueEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增属性值
	 * 
	 * @author zengxn
	 */
	int insert(ValueEntity entity);

	/**
	 * 根据ID修改属性值
	 * 
	 * @author zengxn
	 */
	int updateById(ValueEntity entity);

	/**
	 * 根据ID获取属性值
	 * 
	 * @author zengxn
	 */
	ValueEntity findById(String id);

	/**
	 * 根据ID删除属性值
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID删除属性值，并且删除属性值关联记录
	 * 
	 * @author zengxn
	 */
	int deleteRelevantById(String id);

	/**
	 * 根据名称、ID获取属性值
	 * 
	 * @author zengxn
	 */
	ValueEntity findByNameId(String name, String id);
}
