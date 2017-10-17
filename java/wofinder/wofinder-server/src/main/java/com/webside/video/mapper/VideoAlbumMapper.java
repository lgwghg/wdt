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

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.video.model.VideoAlbumEntity;

/**
 * 视频专辑数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
@Repository
public interface VideoAlbumMapper extends BaseMapper<VideoAlbumEntity, String> {
	/**
	 * 根据父级id查询视频合集集合
	 * 
	 * @author zengxn
	 */
	List<VideoAlbumEntity> queryListByParentId(String parentId);
}
