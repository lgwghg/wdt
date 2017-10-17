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

import com.webside.task.model.TaskCommentEntity;
import com.webside.task.vo.TaskCommentVo;

/**
 * 事件评论服务接口
 *
 * @author aning
 * @date 2017-09-12 16:30:04
 */
public interface TaskCommentService {
	/**
	 * 按条件查询事件评论
	 * 
	 * @author aning
	 */
	List<TaskCommentEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增事件评论
	 * 
	 * @author aning
	 */
	int insert(final TaskCommentEntity entity);

	/**
	 * 根据ID更新修改事件评论
	 * 
	 * @author aning
	 */
	int updateById(final TaskCommentEntity entity);

	/**
	 * 根据ID获取事件评论
	 * 
	 * @author aning
	 */
	TaskCommentEntity findById(String id);

	/**
	 * 根据ID删除事件评论
	 * 
	 * @author aning
	 */
	int deleteById(String id);

	/**
	 * 根据事件ID删除事件评论
	 * 
	 * @author aning
	 */
	int deleteByTaskId(String taskId);

	/**
	 * 根据ID更新点赞量
	 * 
	 * @author aning
	 */
	int updateLikeNumById(String id, Integer likeNum);

	/**
	 * 查询评论以及是否点赞
	 * 
	 * @author zengxn
	 */
	List<TaskCommentVo> queryListForLike(Map<String, Object> map);

	/**
	 * 根据事件id查询事件评论数量
	 * 
	 * @author zengxn
	 */
	long countByTaskId(String taskId);

	/**
	 * 根据事件id查询最近一条事件评论
	 * 
	 * @author aning
	 */
	TaskCommentEntity findNewByTask(String taskId);
}
