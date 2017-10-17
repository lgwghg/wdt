/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.exception.ServiceException;
import com.webside.task.mapper.TaskLikeMapper;
import com.webside.task.model.TaskEntity;
import com.webside.task.model.TaskLikeEntity;
import com.webside.task.service.TaskLikeService;
import com.webside.task.service.TaskService;
import com.webside.user.model.UserEntity;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 评论点赞服务实现类
 * 
 * @author aning
 * @date 2017-06-12 16:03:14
 */
@Service("taskLikeService")
public class TaskLikeServiceImpl extends AbstractService<TaskLikeEntity, String> implements TaskLikeService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(taskLikeMapper);
	}

	/**
	 * 评论点赞 DAO定义
	 */
	@Autowired
	private TaskLikeMapper taskLikeMapper;

	/**
	 * 事件Service定义
	 */
	@Autowired
	private TaskService taskService;

	/**
	 * 新增事件点赞
	 * 
	 * @author aning
	 */
	@Override
	public int insert(TaskLikeEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增事件点赞出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改事件点赞
	 * 
	 * @author aning
	 */
	@Override
	public int update(TaskLikeEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改事件点赞出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新或者新增点赞
	 * 
	 * @author aning
	 */
	@Override
	public void addOrUpdate(String taskId, String userId, Integer status) {
		TaskLikeEntity entity = findByTaskAndUser(taskId, userId);
		boolean isUpdate = true;// 是否更新
		if (entity == null || StringUtils.isBlank(entity.getId())) {
			entity = new TaskLikeEntity();
			entity.setTask(new TaskEntity(taskId));
			entity.setCreateUser(new UserEntity(userId));
			isUpdate = false;
		}

		if (status == null) {// 默认有效
			status = StringUtils.toInteger(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
		}
		entity.setStatus(new DictEntity(StringUtils.toString(status)));
		if (isUpdate) {
			updateById(entity);
		} else {
			insert(entity);
		}

		Integer likeCount = 0;
		if (GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1.equals(StringUtils.toString(status))) {
			likeCount += 1;
		} else {
			likeCount -= 1;
		}
		taskService.updateLikeCountById(taskId, likeCount);
	}

	/**
	 * 通过事件ID 和 用户ID 查找事件点赞
	 * 
	 * @author aning
	 */
	@Override
	public TaskLikeEntity findByTaskAndUser(String taskId, String userId) {
		if (StringUtils.isBlank(taskId)) {
			throw new ServiceException("事件id不能为空");
		}
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		map.put("userId", userId);
		List<TaskLikeEntity> list = super.queryListByPage(map);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据事件ID删除事件点赞
	 * 
	 * @author aning
	 */
	@Override
	public int deleteByTaskId(String taskId) {
		try {
			TaskLikeEntity entity = new TaskLikeEntity();
			entity.setTask(new TaskEntity(taskId));
			return taskLikeMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据事件ID删除事件点赞出错：", e);
			throw new ServiceException(e);
		}
	}

}
