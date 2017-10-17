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

import com.webside.dict.model.DictEntity;
import com.webside.up.model.UpNameEntity;

/**
 * 视频作者名称服务接口
 *
 * @author zengxn
 * @date 2017-04-15 18:29:36
 */
public interface UpNameService {
	
	public static String BEANNAME = "upNameService";
	
	/**
	 * 按条件查询视频作者名称
	 * 
	 * @author zengxn
	 */
	List<UpNameEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频作者名称
	 * 
	 * @author zengxn
	 */
	int insert(UpNameEntity entity);

	/**
	 * 根据ID修改视频作者名称
	 * 
	 * @author zengxn
	 */
	int updateById(UpNameEntity entity);

	/**
	 * 根据ID获取视频作者名称
	 * 
	 * @author zengxn
	 */
	UpNameEntity findById(String id);

	/**
	 * 根据ID批量查询视频作者名称
	 * 
	 * @author zengxn
	 */
	List<UpNameEntity> queryListBatchById(List<String> ids);

	/**
	 * 根据ID删除视频作者名称
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID批量删除视频作者名称
	 * 
	 * @author zengxn
	 */
	int deleteBatchById(List<String> ids);

	/**
	 * 根据名称、类型、ID获取视频作者名称
	 * 
	 * @author zengxn
	 */
	UpNameEntity findByNameTypeId(String name, DictEntity type, String id);

	/**
	 * 根据视频作者id获取视频作者名称列表
	 * 
	 * @author zengxn
	 */
	List<UpNameEntity> queryListByUpId(String upId);

	/**
	 * 根据视频作者ID删除视频作者名称
	 * 
	 * @author zengxn
	 */
	int deleteByUpId(String upId);

	/**
	 * 根据视频作者ID、类型修改视频作者名称
	 * 
	 * @author zengxn
	 */
	int updateNameByUpIdType(String name, String upId, String typeValue, String updateUserId, String updateTime);

	/**
	 * 批量修改视频作者名称的视频作者并且类别为次名
	 * 
	 * @author zengxn
	 */
	int updateUpIdBatchById(List<String> ids, String upId, String updateUserId, String updateTime);

}
