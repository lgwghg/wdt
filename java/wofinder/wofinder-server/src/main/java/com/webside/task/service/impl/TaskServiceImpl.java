/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.task.mapper.TaskMapper;
import com.webside.task.model.TaskCommentEntity;
import com.webside.task.model.TaskEntity;
import com.webside.task.model.TaskLikeEntity;
import com.webside.task.service.TaskCommentService;
import com.webside.task.service.TaskLikeService;
import com.webside.task.service.TaskService;
import com.webside.task.service.TaskValueService;
import com.webside.task.vo.TaskVo;
import com.webside.up.service.UpService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 事件服务实现类
 *
 * @author aning
 * @date 2017-09-11 11:46:36
 */
@Service("taskService")
public class TaskServiceImpl extends AbstractService<TaskEntity, String> implements TaskService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(taskMapper);
	}

	/**
	 * 事件 DAO定义
	 */
	@Autowired
	private TaskMapper taskMapper;

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
	 * 人物管理 Service定义
	 */
	@Autowired
	private UpService upService;

	/**
	 * 事件评论管理 Service定义
	 */
	@Autowired
	private TaskCommentService taskCommentService;

	/**
	 * 事件属性值关联管理 Service定义
	 */
	@Autowired
	private TaskValueService taskValueService;

	/**
	 * 事件点赞管理 Service定义
	 */
	@Autowired
	private TaskLikeService taskLikeService;

	/**
	 * 新增事件
	 * 
	 * @author aning
	 */
	@Override
	public int insert(TaskEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (entity.getLikeCount() == null || entity.getLikeCount() < 0) {
				entity.setLikeCount(0l);
			}
			if (entity.getViewCount() == null || entity.getViewCount() < 0) {
				entity.setViewCount(0l);
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增事件出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改事件
	 * 
	 * @author aning
	 */
	@Override
	public int updateById(TaskEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改事件出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除事件
	 * 
	 * @author aning
	 */
	@Override
	public int deleteById(String id) {
		try {
			// 删除评论
			taskCommentService.deleteByTaskId(id);
			// 删除属性值
			taskValueService.deleteByTaskId(id);
			// 删除事件点赞
			taskLikeService.deleteByTaskId(id);
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据ID删除事件出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询事件
	 * 
	 * @author aning
	 */
	@Override
	public List<TaskEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<TaskEntity> list = super.queryListByPage(parameter);
			for (TaskEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getUp() != null && StringUtils.isNotBlank(entity.getUp().getId())) {
					entity.setUp((upService.findById(entity.getUp().getId())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID更新阅读量
	 * 
	 * @author aning
	 */
	@Override
	public int updateViewCountById(String id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("updateTime", System.currentTimeMillis());
			map.put("id", id);
			return taskMapper.updateViewCountById(map);
		} catch (Exception e) {
			logger.error("根据ID更新阅读量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID更新点赞量
	 * 
	 * @author aning
	 */
	@Override
	public int updateLikeCountById(String id, Integer likeCount) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("updateTime", System.currentTimeMillis());
			map.put("likeCount", likeCount);
			map.put("id", id);
			return taskMapper.updateLikeCountById(map);
		} catch (Exception e) {
			logger.error("根据ID更新点赞量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询相关事件
	 * 
	 * @author aning
	 */
	@Override
	public List<TaskVo> queryListByIdForRelated(Map<String, Object> map) {
		try {
			List<TaskEntity> list = taskMapper.queryListByIdForRelated(map);
			if (CollectionUtils.isNotEmpty(list)) {
				String userId = MapUtils.getString(map, "userId");
				String taskId = MapUtils.getString(map, "id");
				List<TaskVo> result = new ArrayList<TaskVo>(list.size());
				TaskVo taskVo = null;
				for (TaskEntity entity : list) {
					taskVo = new TaskVo(entity.getId());
					taskVo.setContent(entity.getContent());
					taskVo.setCreateTime(entity.getCreateTime());
					taskVo.setLikeCount(entity.getLikeCount());
					taskVo.setTitle(entity.getTitle());
					taskVo.setViewCount(entity.getViewCount());
					taskVo.setCommentCount(taskCommentService.countByTaskId(entity.getId()));
					TaskCommentEntity taskCommentEntity = taskCommentService.findNewByTask(entity.getId());
					if (taskCommentEntity != null) {
						taskVo.setCommentContent(taskCommentEntity.getContent());
					}
					if (StringUtils.isBlank(userId)) {
						TaskLikeEntity taskLikeEntity = taskLikeService.findByTaskAndUser(taskId, userId);
						if (taskLikeEntity != null && StringUtils.isNotBlank(taskLikeEntity.getId())) {
							taskVo.setLikeStatus(StringUtils.toInteger(taskLikeEntity.getStatus().getValue()));
						}
					}
					result.add(taskVo);
				}
				return result;
			}
			return null;
		} catch (Exception e) {
			logger.error("查询相关事件出错：", e);
			throw new ServiceException(e);
		}
	}

}
