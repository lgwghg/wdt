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
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.task.mapper.TaskCommentMapper;
import com.webside.task.model.TaskCommentEntity;
import com.webside.task.model.TaskCommentLikeEntity;
import com.webside.task.model.TaskEntity;
import com.webside.task.service.TaskCommentLikeService;
import com.webside.task.service.TaskCommentService;
import com.webside.task.service.TaskService;
import com.webside.task.vo.TaskCommentVo;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 事件评论服务实现类
 *
 * @author aning
 * @date 2017-09-12 16:30:04
 */
@Service("taskCommentService")
public class TaskCommentServiceImpl extends AbstractService<TaskCommentEntity, String> implements TaskCommentService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(taskCommentMapper);
	}

	/**
	 * 事件评论 DAO定义
	 */
	@Autowired
	private TaskCommentMapper taskCommentMapper;

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
	 * 事件评论点赞管理 Service定义
	 */
	@Autowired
	private TaskCommentLikeService taskCommentLikeService;

	/**
	 * 新增事件评论
	 * 
	 * @author aning
	 */
	@Override
	public int insert(TaskCommentEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (entity.getStatus() == null || StringUtils.isBlank(entity.getStatus().getValue())) {
				entity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));// 默认有效
			}
			if (entity.getLikeNum() == null || entity.getLikeNum() < 0) {
				entity.setLikeNum(0l);
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增事件评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改事件评论
	 * 
	 * @author aning
	 */
	@Override
	public int updateById(TaskCommentEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改事件评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询事件评论
	 * 
	 * @author aning
	 */
	@Override
	public List<TaskCommentEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<TaskCommentEntity> list = super.queryListByPage(parameter);
			for (TaskCommentEntity entity : list) {
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
				if (entity.getParentComment() != null && StringUtils.isNotBlank(entity.getParentComment().getId())) {
					entity.setParentComment(this.findById(entity.getParentComment().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询事件评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除事件评论
	 * 
	 * @author aning
	 */
	@Override
	public int deleteById(String id) {
		try {
			// 删除事件评论点赞
			taskCommentLikeService.deleteByCommentId(id);
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据事件ID删除事件评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据事件ID删除事件评论
	 * 
	 * @author aning
	 */
	@Override
	public int deleteByTaskId(String taskId) {
		try {
			TaskCommentEntity entity = new TaskCommentEntity();
			entity.setTask(new TaskEntity(taskId));
			return taskCommentMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据事件ID删除事件评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID更新点赞量
	 * 
	 * @author aning
	 */
	@Override
	public int updateLikeNumById(String id, Integer likeNum) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("updateTime", System.currentTimeMillis());
			map.put("likeNum", likeNum);
			map.put("id", id);
			return taskCommentMapper.updateLikeNumById(map);
		} catch (Exception e) {
			logger.error("根据ID更新点赞量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询评论以及是否点赞
	 * 
	 * @author zengxn
	 */
	@Override
	public List<TaskCommentVo> queryListForLike(Map<String, Object> map) {
		List<TaskCommentEntity> list = taskCommentMapper.queryListByPage(map);
		if (CollectionUtils.isNotEmpty(list)) {
			String userId = MapUtils.getString(map, "userId");
			List<TaskCommentVo> result = new ArrayList<TaskCommentVo>(list.size());
			TaskCommentVo taskCommentVo = null;
			for (TaskCommentEntity entity : list) {
				taskCommentVo = new TaskCommentVo();
				taskCommentVo.setId(entity.getId());
				taskCommentVo.setContent(entity.getContent());
				taskCommentVo.setCreatetime(entity.getCreateTime());
				taskCommentVo.setTaskId(entity.getTask().getId());
				taskCommentVo.setLikeNum(entity.getLikeNum());
				if (StringUtils.isNotBlank(userId)) {
					TaskCommentLikeEntity commentLikeEntity = taskCommentLikeService.findByCommentAndUser(entity.getId(), userId);
					if (commentLikeEntity != null && StringUtils.isNotBlank(commentLikeEntity.getId())) {
						taskCommentVo.setLikeStatus(StringUtils.toInteger(commentLikeEntity.getStatus().getValue()));
					}
				}
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					UserEntity createUser = userService.findById(entity.getCreateUser().getId());
					taskCommentVo.setUserName(createUser.getNickName());
					taskCommentVo.setAvatar(createUser.getPhoto_40());
				}
				if (entity.getParentComment() != null && StringUtils.isNotBlank(entity.getParentComment().getId())) {
					TaskCommentEntity parentComment = findById(entity.getParentComment().getId());
					if (parentComment != null && parentComment.getCreateUser() != null && StringUtils.isNotBlank(parentComment.getCreateUser().getId())) {
						UserEntity parentCommentCreateUser = userService.findById(parentComment.getCreateUser().getId());
						if (parentCommentCreateUser != null && StringUtils.isNoneBlank(parentCommentCreateUser.getNickName())) {
							taskCommentVo.setParentUserName(parentCommentCreateUser.getNickName());
						}
					}
				}
				result.add(taskCommentVo);
			}
			return result;
		}
		return null;
	}

	/**
	 * 根据事件id查询事件评论数量
	 * 
	 * @author zengxn
	 */
	@Override
	public long countByTaskId(String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		return super.count(map);
	}

	/**
	 * 根据事件id查询最近一条事件评论
	 * 
	 * @author aning
	 */
	@Override
	public TaskCommentEntity findNewByTask(String taskId) {
		return taskCommentMapper.findNewByTask(taskId);
	}
}
