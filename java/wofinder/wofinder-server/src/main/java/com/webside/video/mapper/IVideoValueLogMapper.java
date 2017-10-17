/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.video.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.video.model.VideoValueLog;

/**
 * 视频标签操作日志数据访问接口
 *
 * @author tianguifang
 * @date 2017-08-21 16:48:38
 */
@Repository
public interface IVideoValueLogMapper extends BaseMapper<VideoValueLog, String> 
{
	/**
	 * 查询用户对某视频的最新点赞或取消点赞记录
	 * @param id
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	List<VideoValueLog> queryUserUpVideoLastest(Map<String, Object> param);
	/**
	 * 查询视频标签的操作记录 20条 ，操作时间倒序
	 * @param videoId
	 * @return
	 * @author tianguifang
	 */
	List<VideoValueLog> queryVideoValueLog(String videoId);
	
}
