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
import com.webside.video.model.VideoValueEntity;

/**
 * 视频属性值关联数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 21:18:02
 */
@Repository
public interface VideoValueMapper extends BaseMapper<VideoValueEntity, String> {
	/**
	 * 批量修改视频属性值关联的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoIdBatchById(Map<String, Object> map);
	
	/**
	 * 查询videoId 和 targetId 之间的差异
	 * @param map
	 * @return
	 */
	public List<String> findIdByVideoForDif(Map<String, Object> map);
	
	VideoValueEntity findAllStatusById(String id);
}
