/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.service;

import java.util.List;
import java.util.Map;

import com.webside.exception.ServiceException;
import com.webside.video.model.VideoCommentLikeEntity;

/**
 * 评论点赞服务接口
 *
 * @author zfei
 * @date 2017-06-12 16:03:14
 */
public interface VideoCommentLikeService {
	/**
	 * 按条件查询评论点赞
	 * 
	 * @throws ServiceException
	 * @author zfei
	 */
	public List<VideoCommentLikeEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增评论点赞
	 * 
	 * @param VideoCommentLikeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	public int insert(final VideoCommentLikeEntity entity);

	/**
	 * 修改评论点赞
	 * 
	 * @param VideoCommentLikeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	public int update(final VideoCommentLikeEntity entity);

	/**
	 * 根据ID获取评论点赞
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zfei
	 */
	public VideoCommentLikeEntity findById(String id);

	/**
	 * 根据ID删除评论点赞
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zfei
	 */
	public int deleteById(String id);

	/**
	 * 更新或者新增点赞
	 * 
	 * @param commentId
	 * @param userId
	 * @param status
	 */
	public void addOrUpdate(String commentId, String userId, Integer status);

	/**
	 * 通过评论ID 和 用户ID 查找点赞评论
	 * 
	 * @param commentId
	 * @param userId
	 * @return
	 */
	public VideoCommentLikeEntity findByCommentAndUser(String commentId, String userId);
}
