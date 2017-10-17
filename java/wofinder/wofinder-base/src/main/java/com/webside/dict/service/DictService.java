/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.dict.service;

import java.util.List;
import java.util.Map;

import com.webside.dict.model.DictEntity;
import com.webside.exception.ServiceException;

/**
 * 字典服务接口
 *
 * @author zengxn
 * @date 2017-05-16 15:05:05
 */
public interface DictService {
	
	public static String BEANNAME = "dictService";
	
	/**
	 * 按条件查询字典
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<DictEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增字典
	 * 
	 * @param DictEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int insert(DictEntity entity);

	/**
	 * 修改字典
	 * 
	 * @param DictEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	int updateById(DictEntity entity);

	/**
	 * 根据ID获取字典
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	DictEntity findById(String id);

	/**
	 * 根据ID删除字典
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据字典分类查询字典
	 * 
	 * @param type
	 * @throws ServiceException
	 * @author zengxn
	 */
	List<DictEntity> queryListByType(String type);

	/**
	 * 根据type 和 value 查询字典对象
	 * 
	 * @param type
	 * @param value
	 * @throws ServiceException
	 * @author zengxn
	 */
	DictEntity findByTypeValue(String type, String value);
	
	/**
	 * 根据type 和 value 查询字典对象(不读取redis)
	 * @param type
	 * @param value
	 * @return
	 */
	public DictEntity findByTypeValueNoRedis(String type, String value);
}
