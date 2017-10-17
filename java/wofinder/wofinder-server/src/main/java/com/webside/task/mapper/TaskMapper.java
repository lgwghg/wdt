/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.task.model.TaskEntity;

/**
 * 事件数据访问接口
 *
 * @author aning
 * @date 2017-09-11 11:46:36
 */
@Repository
public interface TaskMapper extends BaseMapper<TaskEntity, String> {
	/**
	 * 根据ID更新阅读量
	 * 
	 * @author aning
	 */
	int updateViewCountById(Map<String, Object> parameter);

	/**
	 * 根据ID更新点赞量
	 * 
	 * @author aning
	 */
	int updateLikeCountById(Map<String, Object> parameter);

	/**
	 * 查询相关事件
	 * 
	 * @author aning
	 */
	List<TaskEntity> queryListByIdForRelated(Map<String, Object> parameter);
}
