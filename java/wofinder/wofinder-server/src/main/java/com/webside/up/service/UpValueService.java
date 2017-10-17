/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.service;

import java.util.List;
import java.util.Map;

import com.webside.sys.model.ValueEntity;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpValueEntity;

/**
 * 视频作者属性值关联服务接口
 *
 * @author zengxn
 * @date 2017-04-15 18:28:39
 */
public interface UpValueService {
	
	public static String BEANNAME = "upValueService";
	
	/**
	 * 按条件查询视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	List<UpValueEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	int insert(UpValueEntity entity);

	/**
	 * 根据ID修改视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	int updateById(UpValueEntity entity);

	/**
	 * 根据ID获取视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	UpValueEntity findById(String id);

	/**
	 * 根据ID删除视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID批量删除视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteBatchById(List<String> ids);

	/**
	 * 根据视频作者ID、属性值ID、ID获取视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	UpValueEntity findByUpValueId(UpEntity up, ValueEntity value, String id);

	/**
	 * 根据视频作者ID获取视频作者属性值关联列表
	 * 
	 * @author zengxn
	 */
	List<UpValueEntity> queryListByUpId(String upId);

	/**
	 * 根据视频作者ID删除视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByUpId(String upId);

	/**
	 * 根据属性值id删除视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByValueId(String valueId);

	/**
	 * 批量修改视频作者属性值关联的视频作者
	 * 
	 * @author zengxn
	 */
	int updateUpIdBatchById(List<String> ids, String upId, String updateUserId, String updateTime);
}
