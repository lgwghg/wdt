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
import com.webside.video.model.VideoStationEntity;
import com.webside.video.vo.VideoStationVo;

/**
 * 视频站点数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 21:15:55
 */
@Repository
public interface VideoStationMapper extends BaseMapper<VideoStationEntity, String> {
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
	int resetUpIdByUpId(Map<String, Object> map);

	/**
	 * 根据视频作者ids批量修改视频站点的所属作者
	 * 
	 * @author zengxn
	 */
	int updateUpIdBatchByUpId(Map<String, Object> map);

	/**
	 * 根据videoIds查询视频站点
	 * 
	 * @author zengxn
	 */
	List<VideoStationEntity> queryListByVideoIds(Map<String, Object> map);

	/**
	 * 依据主键id查找站点相关视频
	 * 
	 * @param id
	 * @return
	 */
	List<VideoStationVo> queryListByIdForRelated(Map<String, Object> map);

	/**
	 * 批量修改视频站点的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoIdBatchById(Map<String, Object> map);

	/**
	 * 批量修改视频站点状态
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(Map<String, Object> map);

	
	/**
	 * 根据站点类型以及属性值查询视频数量
	 * @param station
	 * @param value
	 * @return
	 */
	public long queryCountByStationValue(Map<String, Object> map);
	
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
