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
import com.webside.video.model.VideoCommentEntity;

/**
 * 视频评论数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */
@Repository
public interface VideoCommentMapper extends BaseMapper<VideoCommentEntity, String> {
	/**
	 * 批量修改视频评论的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoIdByIds(Map<String, Object> map);

	/**
	 * 查询评论以及是否点赞
	 * 
	 * @param map
	 * @return
	 */
	List<VideoCommentEntity> queryListForLike(Map<String, Object> map);
	
	/**
	 * 批量修改视频评论的视频
	 * 
	 * @author lwh
	 */
	public int updateVideoIdByVid(Map<String, Object> map);
	
	/**
	 * 
	 * @author lwh
	 */
	public long queryCountByStationVid(Map<String, Object> map);
	/**
	 * 根据用户id查询今天用户发表评论的数量
	 * @param paramMap
	 * @return
	 * @author tianguifang
	 */
	Integer queryUserTodayCommentNumByUserId(Map<String, Object> paramMap);
}
