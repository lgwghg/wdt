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

import com.webside.video.model.VideoAlbumValueEntity;

/**
 * 视频专辑属性值关联服务接口
 *
 * @author zengxn
 * @date 2017-04-20 21:18:02
 */
public interface VideoAlbumValueService {
	
	public static String BEANNAME = "videoAlbumValueService";
	/**
	 * 按条件查询视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	List<VideoAlbumValueEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	int insert(VideoAlbumValueEntity entity);

	/**
	 * 根据ID修改视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	int updateById(VideoAlbumValueEntity entity);

	/**
	 * 根据ID获取视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	VideoAlbumValueEntity findById(String id);

	/**
	 * 根据ID删除视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID批量删除视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteBatchById(List<String> ids);

	/**
	 * 根据视频专辑ID、属性值ID、ID获取视频属性值关联
	 * 
	 * @author zengxn
	 */
	VideoAlbumValueEntity findByVideoAlbumIdValueId(String videoAlbumId, String valueId, String id);

	/**
	 * 根据视频专辑ID获取视频专辑属性值关联集合
	 * 
	 * @author zengxn
	 */
	List<VideoAlbumValueEntity> queryListByVideoAlbumId(String videoAlbumId);

	/**
	 * 根据视频ID删除视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByVideoAlbumId(String videoAlbumId);

	/**
	 * 根据属性值ID删除视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByValueId(String valueId);

	/**
	 * 批量修改视频专辑属性值关联的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoAlbumIdBatchById(List<String> ids, String videoAlbumId, String updateUserId, String updateTime);
}
