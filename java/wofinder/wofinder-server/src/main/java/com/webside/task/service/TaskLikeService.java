/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.service;

import java.util.List;
import java.util.Map;

import com.webside.task.model.TaskLikeEntity;

/**
 * 事件点赞服务接口
 *
 * @author aning
 * @date 2017-06-12 16:03:14
 */
public interface TaskLikeService {
	/**
	 * 按条件查询事件点赞
	 * 
	 * @author aning
	 */
	List<TaskLikeEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增事件点赞
	 * 
	 * @author aning
	 */
	int insert(final TaskLikeEntity entity);

	/**
	 * 修改事件点赞
	 * 
	 * @author aning
	 */
	int update(final TaskLikeEntity entity);

	/**
	 * 根据ID获取事件点赞
	 * 
	 * @author aning
	 */
	TaskLikeEntity findById(String id);

	/**
	 * 根据ID删除事件点赞
	 * 
	 * @author aning
	 */
	int deleteById(String id);

	/**
	 * 根据事件ID删除事件点赞
	 * 
	 * @author aning
	 */
	int deleteByTaskId(String taskId);

	/**
	 * 更新或者新增点赞
	 * 
	 * @author aning
	 */
	void addOrUpdate(String taskId, String userId, Integer status);

	/**
	 * 通过事件ID 和 用户ID 查找事件点赞
	 * 
	 * @author aning
	 */
	TaskLikeEntity findByTaskAndUser(String taskId, String userId);
}
