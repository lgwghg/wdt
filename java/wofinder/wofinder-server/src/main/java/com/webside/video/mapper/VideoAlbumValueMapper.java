/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.video.model.VideoAlbumValueEntity;

/**
 * 视频专辑属性值关联数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 21:18:02
 */
@Repository
public interface VideoAlbumValueMapper extends BaseMapper<VideoAlbumValueEntity, String> {
	/**
	 * 批量修改视频专辑属性值关联的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoAlbumIdBatchById(Map<String, Object> map);
}
