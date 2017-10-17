/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning
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
import com.webside.task.mapper.TaskCommentLikeMapper;
import com.webside.task.model.TaskCommentEntity;
import com.webside.task.model.TaskCommentLikeEntity;
import com.webside.task.service.TaskCommentLikeService;
import com.webside.task.service.TaskCommentService;
import com.webside.user.model.UserEntity;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 事件评论点赞服务实现类
 * 
 * @author aning
 * @date 2017-06-12 16:03:14
 */
@Service("taskCommentLikeService")
public class TaskCommentLikeServiceImpl extends AbstractService<TaskCommentLikeEntity, String> implements TaskCommentLikeService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(taskCommentLikeMapper);
	}

	/**
	 * 评论点赞 DAO定义
	 */
	@Autowired
	private TaskCommentLikeMapper taskCommentLikeMapper;

	/**
	 * 事件评论 Service定义
	 */
	@Autowired
	private TaskCommentService taskCommentService;

	/**
	 * 新增事件评论点赞
	 * 
	 * @author aning
	 */
	@Override
	public int insert(TaskCommentLikeEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增事件评论点赞出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改事件评论点赞
	 * 
	 * @author aning
	 */
	@Override
	public int update(TaskCommentLikeEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改事件评论点赞出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新或者新增事件点赞
	 * 
	 * @author aning
	 */
	@Override
	public void addOrUpdate(String commentId, String userId, Integer status) {
		TaskCommentLikeEntity entity = findByCommentAndUser(commentId, userId);
		boolean isUpdate = true;// 是否更新
		if (entity == null || StringUtils.isBlank(entity.getId())) {
			entity = new TaskCommentLikeEntity();
			entity.setComment(new TaskCommentEntity(commentId));
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
		taskCommentService.updateLikeNumById(commentId, likeCount);

	}

	/**
	 * 通过评论ID 和 用户ID 查找事件评论点赞
	 * 
	 * @author aning
	 */
	@Override
	public TaskCommentLikeEntity findByCommentAndUser(String commentId, String userId) {
		if (StringUtils.isBlank(commentId)) {
			throw new ServiceException("评论id不能为空");
		}
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentId", commentId);
		map.put("userId", userId);
		List<TaskCommentLikeEntity> list = super.queryListByPage(map);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 根据事件评论ID删除事件评论点赞
	 * 
	 * @author aning
	 */
	@Override
	public int deleteByCommentId(String commentId) {
		try {
			TaskCommentLikeEntity entity = new TaskCommentLikeEntity();
			entity.setComment(new TaskCommentEntity(commentId));
			return taskCommentLikeMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据事件评论ID删除事件评论点赞出错：", e);
			throw new ServiceException(e);
		}
	}

}
