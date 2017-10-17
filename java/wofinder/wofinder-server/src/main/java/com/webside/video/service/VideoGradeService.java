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

import com.webside.video.model.VideoGradeEntity;

/**
 * 视频评分服务接口
 * 
 * @author zfei
 * @date 2017-06-12 16:04:07
 */
public interface VideoGradeService {
	/**
	 * 按条件查询视频评分
	 * 
	 * @throws ServiceException
	 * @author zfei
	 */
	public List<VideoGradeEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频评分
	 * 
	 * @param VideoGradeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	public int insert(final VideoGradeEntity entity);

	/**
	 * 修改视频评分
	 * 
	 * @param VideoGradeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	public int update(final VideoGradeEntity entity);

	/**
	 * 根据ID获取视频评分
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zfei
	 */
	public VideoGradeEntity findById(String id);

	/**
	 * 根据ID删除视频评分
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zfei
	 */
	public int deleteById(String id);
	
	/**
	 * 新增或修改
	 * @param videoId 视频ID
	 * @param score   个人评分
	 */
	public void addOrUpdate(String videoId, double score);
	
	/**
	 * 通过视频 和 用户 查找个人评分
	 * @param videoId
	 * @param userId
	 * @return
	 */
	public VideoGradeEntity findByVideoAndUser(String videoId, String userId);
	/**
	 * 根据用户id查询今天用户评分数量
	 * @param paramMap
	 * @return
	 * @author tianguifang
	 */
	public Integer queryUserTodayGradeNumByUserId(Map<String, Object> paramMap);
}
