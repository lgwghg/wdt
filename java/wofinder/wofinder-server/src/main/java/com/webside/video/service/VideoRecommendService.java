/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.service;

import java.util.List;
import java.util.Map;

import com.webside.video.model.VideoRecommendEntity;
import com.webside.video.vo.VideoRecommendVo;

/**
 * 首页推荐视频服务接口
 *
 * @author zengxn
 * @date 2017-04-20 22:21:52
 */
public interface VideoRecommendService {
	/**
	 * 按条件查询首页推荐视频
	 * 
	 * @author zengxn
	 */
	List<VideoRecommendEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增首页推荐视频
	 * 
	 * @author zengxn
	 */
	int insert(VideoRecommendEntity entity);

	/**
	 * 根据ID修改首页推荐视频
	 * 
	 * @author zengxn
	 */
	int updateById(VideoRecommendEntity entity);

	/**
	 * 根据视频ids批量清空首页推荐视频的视频关联
	 * 
	 * @author zengxn
	 */
	int resetVideoIdBatchByVideoId(List<String> videoIds, String updateUserId, String updateTime);

	/**
	 * 根据ID获取首页推荐视频
	 * 
	 * @author zengxn
	 */
	VideoRecommendEntity findById(String id);

	/**
	 * 根据ID删除首页推荐视频
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据视频ID、ID获取首页推荐视频
	 * 
	 * @author zengxn
	 */
	VideoRecommendEntity findByVideoId(String videoId, String id);

	/**
	 * 根据视频ID删除首页推荐视频
	 * 
	 * @author zengxn
	 */
	int deleteByVideoId(String videoId);

	/**
	 * 查询首页推荐视频9个视频
	 * 
	 * @author zengxn
	 */
	List<VideoRecommendVo> queryListByParam(Map<String, Object> map);
}
