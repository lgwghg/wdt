/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoCommentLikeMapper;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoCommentLikeEntity;
import com.webside.video.service.VideoCommentLikeService;
import com.webside.video.service.VideoCommentService;

/**
 * 评论点赞服务实现类
 * 
 * @author zfei
 * @date 2017-06-12 16:03:14
 */
@Service("videoCommentLikeService")
public class VideoCommentLikeServiceImpl extends AbstractService<VideoCommentLikeEntity, String> implements VideoCommentLikeService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoCommentLikeMapper);
	}

	/**
	 * 评论点赞 DAO定义
	 */
	@Autowired
	private VideoCommentLikeMapper videoCommentLikeMapper;
	@Autowired
	private VideoCommentService videoCommentService;

	/**
	 * 新增评论点赞
	 * 
	 * @param VideoCommentLikeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	public int insert(VideoCommentLikeEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			entity.setCreateTime(System.currentTimeMillis());
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增评论点赞出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改评论点赞
	 * 
	 * @param VideoCommentLikeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	public int update(VideoCommentLikeEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改评论点赞出错：", e);
			throw new ServiceException(e);
		}
	}

	public void addOrUpdate(String commentId, String userId, Integer status) {
		VideoCommentLikeEntity entity = findByCommentAndUser(commentId, userId);
		boolean isUpdate = true;// 是否更新
		if (entity == null || StringUtils.isBlank(entity.getId())) {
			entity = new VideoCommentLikeEntity();
			entity.setCommentId(commentId);
			entity.setUserId(userId);
			isUpdate = false;
		}

		if (status == null) {// 默认有效
			status = StringUtils.toInteger(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
		}
		entity.setStatus(status);
		if (isUpdate) {
			updateById(entity);
		} else {
			insert(entity);
		}

		VideoCommentEntity videoComment = videoCommentService.findById(commentId);
		if (videoComment != null && StringUtils.isNotBlank(videoComment.getId())) {
			Long likeNum = videoComment.getLikeNum();
			if (likeNum == null) {
				likeNum = 0L;
			}
			if (status == 1) {
				likeNum = likeNum + 1;
			} else {
				likeNum = likeNum - 1;
			}
			videoComment.setLikeNum(likeNum);
			videoCommentService.updateById(videoComment);
		}

	}

	public VideoCommentLikeEntity findByCommentAndUser(String commentId, String userId) {
		if (StringUtils.isBlank(commentId)) {
			throw new ServiceException("评论commentId不能为空");
		}
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentId", commentId);
		map.put("userId", userId);
		List<VideoCommentLikeEntity> list = super.queryListByPage(map);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

}
