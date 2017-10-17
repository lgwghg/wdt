/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.service;

import java.util.List;
import java.util.Map;

import com.webside.task.model.TaskEntity;
import com.webside.task.vo.TaskVo;

/**
 * 事件服务接口
 *
 * @author aning
 * @date 2017-09-11 11:46:36
 */
public interface TaskService {
	/**
	 * 按条件查询事件
	 * 
	 * @author aning
	 */
	List<TaskEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增事件
	 * 
	 * @author aning
	 */
	int insert(final TaskEntity entity);

	/**
	 * 根据ID更新修改事件
	 * 
	 * @author aning
	 */
	int updateById(final TaskEntity entity);

	/**
	 * 根据ID获取事件
	 * 
	 * @author aning
	 */
	TaskEntity findById(String id);

	/**
	 * 根据ID删除事件
	 * 
	 * @author aning
	 */
	int deleteById(String id);

	/**
	 * 根据ID更新阅读量
	 * 
	 * @author aning
	 */
	int updateViewCountById(String id);

	/**
	 * 根据ID更新点赞量
	 * 
	 * @author aning
	 */
	int updateLikeCountById(String id, Integer likeCount);
	
	/**
	 * 查询相关事件
	 * 
	 * @author aning
	 */
	List<TaskVo> queryListByIdForRelated(Map<String, Object> map);
}
