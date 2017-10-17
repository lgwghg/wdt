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
import com.webside.sys.model.AttributeValueEntity;
import com.webside.sys.model.ValueEntity;

/**
 * 属性值关联服务接口
 *
 * @author zengxn
 * @date 2017-04-16 14:36:57
 */
public interface AttributeValueService {
	
	public static String BEANNAME = "attributeValueService";
	
	/**
	 * 按条件查询属性值关联
	 * 
	 * @author zengxn
	 */
	List<AttributeValueEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增属性值关联
	 * 
	 * @author zengxn
	 */
	int insert(AttributeValueEntity entity);

	/**
	 * 根据ID修改属性值关联
	 * 
	 * @author zengxn
	 */
	int updateById(AttributeValueEntity entity);

	/**
	 * 根据ID获取属性值关联
	 * 
	 * @author zengxn
	 */
	AttributeValueEntity findById(String id);

	/**
	 * 根据ID删除属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据属性ID删除属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByAttributeId(String attributeId);

	/**
	 * 根据属性值ID删除属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByValueId(String valueId);

	/**
	 * 根据属性id、属性值id、id获取属性值关联
	 * 
	 * @author zengxn
	 */
	AttributeValueEntity findByAttributeValueId(AttributeEntity attribute, ValueEntity value, String id);
}
