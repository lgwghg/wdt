/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.task.model.TaskCommentEntity;

/**
 * 事件评论数据访问接口
 *
 * @author aning
 * @date 2017-09-12 16:30:04
 */
@Repository
public interface TaskCommentMapper extends BaseMapper<TaskCommentEntity, String> {

	/**
	 * 根据ID更新点赞量
	 * 
	 * @author aning
	 */
	int updateLikeNumById(Map<String, Object> map);

	/**
	 * 根据事件id查询最近一条事件评论
	 * 
	 * @author aning
	 */
	TaskCommentEntity findNewByTask(String taskId);
}
