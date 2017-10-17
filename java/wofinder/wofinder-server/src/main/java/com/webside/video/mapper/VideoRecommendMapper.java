/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.video.model.VideoRecommendEntity;

/**
 * 首页推荐视频数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 22:21:52
 */
@Repository
public interface VideoRecommendMapper extends BaseMapper<VideoRecommendEntity, String> {
	/**
	 * 根据视频ids批量清空首页推荐视频的视频关联
	 * 
	 * @author zengxn
	 */
	int resetVideoIdBatchByVideoId(Map<String, Object> map);

	/**
	 * 按条件查询首页推荐视频
	 * 
	 * @author zengxn
	 */
	List<VideoRecommendEntity> queryListByParam(Map<String, Object> parameter);
}
