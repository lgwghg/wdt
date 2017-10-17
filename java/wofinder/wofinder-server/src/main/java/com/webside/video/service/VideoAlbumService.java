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

import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.vo.VideoAlbumVo;

/**
 * 视频专辑服务接口
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
public interface VideoAlbumService {
	
	public static String BEANNAME = "videoAlbumService";
	/**
	 * 按条件查询视频专辑
	 * 
	 * @author zengxn
	 */
	List<VideoAlbumEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频专辑
	 * 
	 * @author zengxn
	 */
	int insert(VideoAlbumEntity entity);

	/**
	 * 根据ID修改视频专辑
	 * 
	 * @author zengxn
	 */
	int updateById(VideoAlbumEntity entity);

	/**
	 * 根据ID获取视频专辑
	 * 
	 * @author zengxn
	 */
	VideoAlbumEntity findById(String id);

	/**
	 * 根据ID批量查询视频专辑
	 * 
	 * @author zengxn
	 */
	List<VideoAlbumEntity> queryListBatchById(List<String> ids);

	/**
	 * 根据ID删除视频专辑
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID删除视频专辑，并且清空相关的视频专辑字段
	 * 
	 * @author zengxn
	 */
	int deleteRelevantById(String updateUserId, String id);
	
	/**
	 * 通过第三方链接查找
	 * @param homeUrl
	 * @return
	 */
	VideoAlbumEntity findByHome(String homeUrl, String homeId);

	/**
	 * 根据视频合集id批量查询视频合集VO数据集合
	 * 
	 * @author zengxn
	 */
	List<VideoAlbumVo> queryListVideoAlbumVoBatchById(List<String> videoAlbumIds);
	
	/**
	 * 根据父级id查询视频合集集合
	 * 
	 * @author zengxn
	 */
	List<VideoAlbumEntity> queryListByParentId(String parentId);
	
	/**
	 * 通过当前合辑，查找一系列关联的合辑集合
	 * @param albumId
	 * @return
	 */
	List<VideoAlbumEntity> queryListByIdForAll(String albumId);
}
