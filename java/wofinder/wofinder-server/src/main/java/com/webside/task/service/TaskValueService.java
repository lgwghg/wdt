
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

import com.webside.sys.model.ValueEntity;
import com.webside.task.model.TaskEntity;
import com.webside.task.model.TaskValueEntity;

/**
 * 事件属性值关联服务接口
 *
 * @author aning
 * @date 2017-04-20 21:18:02
 */
public interface TaskValueService {

	/**
	 * 按条件查询事件属性值关联
	 * 
	 * @author aning
	 */
	List<TaskValueEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增事件属性值关联
	 * 
	 * @author aning
	 */
	int insert(TaskValueEntity entity);

	/**
	 * 根据ID修改事件属性值关联
	 * 
	 * @author aning
	 */
	int updateById(TaskValueEntity entity);

	/**
	 * 根据ID获取事件属性值关联
	 * 
	 * @author aning
	 */
	TaskValueEntity findById(String id);

	/**
	 * 根据ID删除视频属性值关联
	 * 
	 * @author aning
	 */
	int deleteById(String id);

	/**
	 * 根据事件ID、属性值ID、ID获取事件属性值关联
	 * 
	 * @author aning
	 */
	TaskValueEntity findByTaskValueId(TaskEntity taskEntity, ValueEntity valueEntity, String id);

	/**
	 * 根据事件ID获取事件属性值关联集合
	 * 
	 * @author aning
	 */
	List<TaskValueEntity> queryListByTaskId(String taskId);

	/**
	 * 根据事件ID删除事件属性值关联
	 * 
	 * @author aning
	 */
	int deleteByTaskId(String taskId);

	/**
	 * 根据属性值ID删除事件属性值关联
	 * 
	 * @author aning
	 */
	int deleteByValueId(String valueId);

}
