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

import com.webside.sys.model.AttributeEntity;

/**
 * 属性服务接口
 *
 * @author zengxn
 * @date 2017-04-16 14:36:14
 */
public interface AttributeService {
	
	public static String BEANNAME = "attributeService";
	
	/**
	 * 按条件查询属性
	 * 
	 * @author zengxn
	 */
	List<AttributeEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增属性
	 * 
	 * @author zengxn
	 */
	int insert(AttributeEntity entity);

	/**
	 * 根据ID修改属性
	 * 
	 * @author zengxn
	 */
	int updateById(AttributeEntity entity);

	/**
	 * 根据ID获取属性
	 * 
	 * @author zengxn
	 */
	AttributeEntity findById(String id);

	/**
	 * 根据ID删除属性
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID删除属性，并且删除属性值关联记录
	 * 
	 * @author zengxn
	 */
	int deleteRelevantById(String id);

	/**
	 * 根据name与id获取属性
	 * 
	 * @author zengxn
	 */
	AttributeEntity findByNameId(String name, String id);
}
