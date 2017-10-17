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

import com.webside.video.model.VideoEntity;
import com.webside.video.vo.VideoVo;

/**
 * 视频服务接口
 *
 * @author zengxn
 * @date 2017-04-20 18:58:30
 */
public interface VideoService {
	
	public static String BEANNAME = "videoService";
	
	/**
	 * 按条件查询视频
	 * 
	 * @author zengxn
	 */
	List<VideoEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频
	 * 
	 * @author zengxn
	 */
	int insert(VideoEntity entity);

	/**
	 * 根据ID修改视频
	 * 
	 * @author zengxn
	 */
	int updateById(VideoEntity entity);

	/**
	 * 批量修改视频状态
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(List<String> ids, String statusValue, String updateUserId, String updateTime);
	
	/**
	 * 修改评分
	 * @param id
	 * @return
	 */
	public int updateScoreById(String id);

	/**
	 * 根据ID获取视频
	 * 
	 * @author zengxn
	 */
	VideoEntity findById(String id);

	/**
	 * 根据ID批量查询视频
	 * 
	 * @author zengxn
	 */
	List<VideoEntity> queryListBatchById(List<String> ids);

	/**
	 * 根据ID删除视频
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID删除视频，并且删除视频相关评论、站点、属性、首页推荐视频
	 * 
	 * @author zengxn
	 */
	int deleteRelevantById(String id);

	/**
	 * 重置符合要求的视频专辑字段
	 * 
	 * @author zengxn
	 */
	int resetAlbumIdByAlbumId(String albumId, String updateUserId, String updateTime);

	/**
	 * 重置符合要求的视频游戏字段
	 * 
	 * @author zengxn
	 */
	int resetGameIdByGameId(String gameId, String updateUserId, String updateTime);

	/**
	 * 根据专辑ID获取视频集合
	 * 
	 * @author zengxn
	 */
	List<VideoEntity> queryListByAlbumId(String albumId, Integer index);

	/**
	 * 批量归纳视频集合到一个专辑中与从专辑移除视频
	 * 
	 * @author zengxn
	 */
	int doBatchAlbum(List<VideoEntity> videoList);

	/**
	 * 合并视频
	 * 
	 * @author zengxn
	 */
	Map<String, Object> doMerge(String updateUserId, List<String> videoIdList, List<String> checkStationIdList);
	
	/**
	 * 根据标题获取视频
	 * 
	 * @author zengxn
	 */
	VideoEntity findByTitle(String title);

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
	 * 根据视频id查询视频VO数据
	 * 
	 * @author zengxn
	 */
	VideoVo findVideoVOByVideoId(String videoId);

	/**
	 * 根据视频id批量查询视频VO数据集合
	 * 
	 * @author zengxn
	 */
	List<VideoVo> queryListVideoVOBathchByVideoId(List<String> videoIds);
	/**
	 * 查询搜索页右侧热门视频
	 * @param object
	 * @return
	 * @author tianguifang
	 */
	List<VideoEntity> queryHotVideo(int count);
	
	/**
	 * 通过短ID查询视频
	 * @param videoId
	 * @return
	 */
	VideoEntity findByShortId(String videoId);
	
}
