/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.video.service;

import java.util.List;
import java.util.Map;

import com.webside.video.model.VideoValueLog;

/**
 * 视频标签操作日志服务接口
 *
 * @author tianguifang
 * @date 2017-08-21 16:48:38
 */
public interface IVideoValueLogService 
{
	/*
	 * 按条件查询视频标签操作日志
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<VideoValueLog> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增视频标签操作日志
	 * @param VideoValueLog
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final VideoValueLog entity);
	
	/*
	 * 修改视频标签操作日志
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final VideoValueLog entity);
	
	/*
	 * 根据ID获取视频标签操作日志
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public VideoValueLog findById(String id);
	
	/*
	 * 根据对象删除视频标签操作日志
	 * @param VideoValueLog
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 查询用户对某视频的最新点赞或取消点赞记录
	 * @param id
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	public List<VideoValueLog> queryUserUpVideoLastest(String videoValueId, String userId);
	
	/**
	 * 查询视频标签的操作记录 20条 ，操作时间倒序
	 * @param videoId
	 * @return
	 * @author tianguifang
	 */
	public List<VideoValueLog> queryVideoValueLog(String videoId);
}
