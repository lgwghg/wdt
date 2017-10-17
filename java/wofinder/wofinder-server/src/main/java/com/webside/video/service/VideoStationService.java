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

import com.webside.video.model.VideoStationEntity;
import com.webside.video.vo.VideoStationVo;

/**
 * 视频站点服务接口
 *
 * @author zengxn
 * @date 2017-04-20 21:15:55
 */
public interface VideoStationService {
	
	public static String BEANNAME = "videoStationService";
	
	/**
	 * 按条件查询视频站点
	 * 
	 * @author zengxn
	 */
	List<VideoStationEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频站点
	 * 
	 * @author zengxn
	 */
	int insert(VideoStationEntity entity);

	/**
	 * 根据ID修改视频站点
	 * 
	 * @author zengxn
	 */
	int updateById(VideoStationEntity entity);

	/**
	 * 根据ID获取视频站点
	 * 
	 * @author zengxn
	 */
	VideoStationEntity findById(String id);

	/**
	 * 根据ID删除视频站点
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据视频ID、站点值、ID获取视频站点
	 * 
	 * @author zengxn
	 */
	VideoStationEntity findByVideoStationId(String videoId, String stationValue, String id,String vid);

	/**
	 * 根据视频ID删除视频站点
	 * 
	 * @author zengxn
	 */
	int deleteByVideoId(String videoId);

	/**
	 * 重置符合要求的视频站点视频作者字段
	 * 
	 * @author zengxn
	 */
	int resetUpIdByUpId(String upId, String updateUserId, String updateTime);

	/**
	 * 根据视频作者ids批量修改视频站点的所属作者
	 * 
	 * @author zengxn
	 */
	int updateUpIdBatchByUpId(List<String> upIds, String upId, String updateUserId, String updateTime);

	/**
	 * 批量修改视频站点的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoIdBatchById(List<String> ids, String videoId, String updateUserId, String updateTime);

	/**
	 * 批量修改视频站点状态
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(List<String> ids, String statusValue, String updateUserId, String updateTime);

	/**
	 * 根据videoIds查询视频站点
	 * 
	 * @author zengxn
	 */
	List<VideoStationEntity> queryListByVideoIds(List<String> videoIds, String stationValue);

	/**
	 * 根据视频ID获取视频站点列表
	 * 
	 * @author zengxn
	 */
	List<VideoStationEntity> queryListByVideoId(String videoId);

	/**
	 * 依据主键id查找站点相关视频
	 * 
	 * @param id
	 * @return
	 */
	List<VideoStationVo> queryListByIdForRelated(Map<String, Object> map);
	
	/**
	 * 根据视频类型以及视频站和状态查询数量
	 * @param station
	 * @param category
	 * @param status
	 * @return
	 */
	long queryCountByStationValue(String station,String category,String status);
	
	/**
	 * 根据视频类型以及视频站除去无效状态查询数量
	 * @param station
	 * @param category
	 * @return
	 */
	long queryCountByReInvalid(String station,String category);
	
	/**
	 * 
	 * @param title
	 * @return
	 */
	public VideoStationEntity findByVideoStationTitle(String title);
	
	/**
	 * 通过up主ID查询总播放数量
	 * @param upId up主ID
	 * @return
	 */
	public Long queryTotalViewCountByUpId(String upId);
	
	/**
	 * 通过视频ID查询播放量
	 * @param videoId
	 * @return
	 */
	public Long queryTotalViewCountByVideoId(String videoId);
}
