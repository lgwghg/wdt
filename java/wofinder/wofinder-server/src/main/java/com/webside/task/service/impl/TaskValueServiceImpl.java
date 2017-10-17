/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning		
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.task.mapper.TaskValueMapper;
import com.webside.task.model.TaskEntity;
import com.webside.task.model.TaskValueEntity;
import com.webside.task.service.TaskService;
import com.webside.task.service.TaskValueService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 事件属性值关联服务实现类
 *
 * @author aning
 * @date 2017-09-12 16:30:04
 */
@Service("taskValueService")
public class TaskValueServiceImpl extends AbstractService<TaskValueEntity, String> implements TaskValueService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(taskValueMapper);
	}

	/**
	 * 事件属性值关联 DAO定义
	 */
	@Autowired
	private TaskValueMapper taskValueMapper;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 事件管理 Service定义
	 */
	@Autowired
	private TaskService taskService;

	/**
	 * 属性值Service定义
	 */
	@Autowired
	private ValueService valueService;

	/**
	 * 新增事件属性值关联
	 * 
	 * @author aning
	 */
	@Override
	public int insert(TaskValueEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增事件属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改事件属性值关联
	 * 
	 * @author aning
	 */
	@Override
	public int updateById(TaskValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改事件属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询事件属性值关联
	 * 
	 * @author aning
	 */
	@Override
	public List<TaskValueEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<TaskValueEntity> list = super.queryListByPage(parameter);
			for (TaskValueEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getTask() != null && StringUtils.isNotBlank(entity.getTask().getId())) {
					entity.setTask((taskService.findById(entity.getTask().getId())));
				}
				if (entity.getValue() != null && StringUtils.isNotBlank(entity.getValue().getId())) {
					entity.setValue(valueService.findById(entity.getValue().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询事件属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据事件ID、属性值ID、ID获取事件属性值关联
	 * 
	 * @author aning
	 */
	@Override
	public TaskValueEntity findByTaskValueId(TaskEntity taskEntity, ValueEntity valueEntity, String id) {
		try {
			TaskValueEntity entity = new TaskValueEntity(id);
			entity.setTask(taskEntity);
			entity.setValue(valueEntity);
			List<TaskValueEntity> list = taskValueMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据事件ID、属性值ID、ID获取事件属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据事件ID获取事件属性值关联集合
	 * 
	 * @author aning
	 */
	@Override
	public List<TaskValueEntity> queryListByTaskId(String taskId) {
		try {
			TaskValueEntity entity = new TaskValueEntity();
			entity.setTask(new TaskEntity(taskId));
			return taskValueMapper.queryListByEntity(entity);
		} catch (Exception e) {
			logger.error("根据事件ID获取事件属性值关联集合出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据事件ID删除事件属性值关联
	 * 
	 * @author aning
	 */
	@Override
	public int deleteByTaskId(String taskId) {
		try {
			TaskValueEntity entity = new TaskValueEntity();
			entity.setTask(new TaskEntity(taskId));
			return taskValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据事件ID删除事件属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性值ID删除事件属性值关联
	 * 
	 * @author aning
	 */
	@Override
	public int deleteByValueId(String valueId) {
		try {
			TaskValueEntity entity = new TaskValueEntity();
			entity.setValue(new ValueEntity(valueId));
			return taskValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据属性值ID删除事件属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}
}
