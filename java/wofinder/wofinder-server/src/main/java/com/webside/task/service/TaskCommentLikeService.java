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

import com.webside.task.model.TaskCommentLikeEntity;

/**
 * 事件评论点赞服务接口
 *
 * @author aning
 * @date 2017-06-12 16:03:14
 */
public interface TaskCommentLikeService {
	/**
	 * 按条件查询事件评论点赞
	 * 
	 * @author aning
	 */
	List<TaskCommentLikeEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增事件评论点赞
	 * 
	 * @author aning
	 */
	int insert(final TaskCommentLikeEntity entity);

	/**
	 * 修改事件评论点赞
	 * 
	 * @author aning
	 */
	int update(final TaskCommentLikeEntity entity);

	/**
	 * 根据ID获取事件评论点赞
	 * 
	 * @author aning
	 */
	TaskCommentLikeEntity findById(String id);

	/**
	 * 根据ID删除事件评论点赞
	 * 
	 * @author aning
	 */
	int deleteById(String id);

	/**
	 * 根据事件评论ID删除事件评论点赞
	 * 
	 * @author aning
	 */
	int deleteByCommentId(String commentId);

	/**
	 * 更新或者新增事件点赞
	 * 
	 * @author aning
	 */
	void addOrUpdate(String commentId, String userId, Integer status);

	/**
	 * 通过评论ID 和 用户ID 查找事件评论点赞
	 * 
	 * @author aning
	 */
	TaskCommentLikeEntity findByCommentAndUser(String commentId, String userId);
}
