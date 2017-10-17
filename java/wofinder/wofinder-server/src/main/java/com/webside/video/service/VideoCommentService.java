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

import com.webside.video.model.VideoCommentEntity;

/**
 * 视频评论服务接口
 *
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */
public interface VideoCommentService {

	public static String BEANNAME = "videoCommentService";

	/**
	 * 按条件查询视频评论
	 * 
	 * @author zengxn
	 */
	List<VideoCommentEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频评论
	 * 
	 * @author zengxn
	 */
	int insert(VideoCommentEntity entity);

	/**
	 * 根据ID修改视频评论
	 * 
	 * @author zengxn
	 */
	int updateById(VideoCommentEntity entity);

	/**
	 * 根据ID获取视频评论
	 * 
	 * @author zengxn
	 */
	VideoCommentEntity findById(String id);

	/**
	 * 根据ID删除视频评论
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID批量删除视频评论
	 * 
	 * @author zengxn
	 */
	int deleteBatchById(List<String> ids);

	/**
	 * 根据父评论ID获取视频评论
	 * 
	 * @author zengxn
	 */
	VideoCommentEntity findByParentId(String videoId,String parentId);

	/**
	 * 根据视频ID获取视频评论
	 * 
	 * @author zengxn
	 */
	List<VideoCommentEntity> queryListByVideoId(String videoId);

	/**
	 * 根据视频ID删除视频评论
	 * 
	 * @author zengxn
	 */
	int deleteByVideoId(String videoId);

	/**
	 * 批量修改视频评论的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoIdByIds(List<String> ids, String videoId, String updateUserId, String updateTime);

	/**
	 * 根据视频id查询视频评论数量
	 * 
	 * @author zengxn
	 */
	long countByVideoId(String videoId);

	/**
	 * 查询评论以及是否点赞
	 * 
	 * @author zengxn
	 */
	List<VideoCommentEntity> queryListForLike(Map<String, Object> map);

	/**
	 * 批量将视频评论移动到其他视频下
	 * 
	 * @author lwh
	 */
	int updateVideoIdByVid(String videoId, String oldVideoId);

	/**
	 * 根据站点以及站点评论ID获取评论
	 * 
	 * @author lwh
	 */
	VideoCommentEntity findByStationCid(String videoId,String station, String cid);

	/**
	 * 根据参数查询评论数量
	 * 
	 */
	long queryCountByStationVid(String station, String vid, String pid, String isempty,String status);
	
	/**
	 * 根据参数查询除去无效状态的评论
	 * 
	 */
	long queryCountByReInvalid(String station, String vid, String pid, String isempty);

	/**
	 * 根据用户id查询今天用户发表评论的数量
	 * 
	 * @param paramMap
	 * @return
	 * @author tianguifang
	 */
	Integer queryUserTodayCommentNumByUserId(Map<String, Object> paramMap);
}
