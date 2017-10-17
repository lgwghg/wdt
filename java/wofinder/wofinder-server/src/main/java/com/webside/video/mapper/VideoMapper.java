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
import com.webside.video.model.VideoEntity;

/**
 * 视频数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 18:58:30
 */
@Repository
public interface VideoMapper extends BaseMapper<VideoEntity, String> {
	/**
	 * 重置符合要求的视频游戏字段
	 * 
	 * @author zengxn
	 */
	int resetGameIdByGameId(VideoEntity entity);

	/**
	 * 重置符合要求的视频专辑字段
	 * 
	 * @author zengxn
	 */
	int resetAlbumIdByAlbumId(VideoEntity entity);

	/**
	 * 根据视频id修改视频的所属专辑以及集数
	 * 
	 * @author zengxn
	 */
	int updateAlbumById(VideoEntity entity);

	/**
	 * 批量修改视频
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(Map<String, Object> map);

	/**
	 * 修改评分
	 * 
	 * @param id
	 * @return
	 */
	int updateScoreById(Map<String, String> map);

	/**
	 * 查询人物的相关视频
	 * 
	 * @param up
	 * @return
	 * @author tianguifang
	 * @date 2017-06-08
	 */
	List<VideoEntity> queryUpRelateVideoPgByUp(Map<String, Object> paramMap);
	/**
	 * 查询搜索页右侧热门视频
	 * @param object
	 * @return
	 * @author tianguifang
	 */
	List<VideoEntity> queryHotVideo(int count);
}
