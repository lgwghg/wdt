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

import com.alibaba.fastjson.JSONObject;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoValueEntity;

/**
 * 视频属性值关联服务接口
 *
 * @author zengxn
 * @date 2017-04-20 21:18:02
 */
public interface VideoValueService {
	
	public static String BEANNAME = "videoValueService";
	
	/**
	 * 按条件查询视频属性值关联
	 * 
	 * @author zengxn
	 */
	List<VideoValueEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频属性值关联
	 * 
	 * @author zengxn
	 */
	int insert(VideoValueEntity entity);

	/**
	 * 根据ID修改视频属性值关联
	 * 
	 * @author zengxn
	 */
	int updateById(VideoValueEntity entity);

	/**
	 * 根据ID获取视频属性值关联
	 * 
	 * @author zengxn
	 */
	VideoValueEntity findById(String id);

	/**
	 * 根据ID删除视频属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID批量删除视频属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteBatchById(List<String> ids);

	/**
	 * 根据视频ID、属性值ID、ID获取视频属性值关联
	 * 
	 * @author zengxn
	 */
	VideoValueEntity findByVideoValueId(String videoId, String valueId, String id);

	/**
	 * 根据视频ID获取视频属性值关联集合
	 * 
	 * @author zengxn
	 */
	List<VideoValueEntity> queryListByVideoId(String videoId);

	/**
	 * 根据视频ID删除视频属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByVideoId(String videoId);

	/**
	 * 根据属性值ID删除视频属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByValueId(String valueId);

	/**
	 * 批量修改视频属性值关联的视频
	 * 
	 * @author zengxn
	 */
	int updateVideoIdBatchById(List<String> ids, String videoId, String updateUserId, String updateTime);
	
	/**
	 * 查询videoId 和 targetId 之间的差异
	 * @param map
	 * @return
	 */
	public int updateVideoIdByTargetForDif(String videoId, String targetId);
	/**
	 * 视频标签点赞
	 * @param id
	 * @return
	 * @author tianguifang
	 */
	Map<String, Object> updateUpVideoValue(String id);
	/**
	 * 新增视频标签
	 * @param userId
	 * @param videoId
	 * @param tagName
	 * @return
	 * @author tianguifang
	 */
	Map<String, Object> insertVideoValue(String userId, String videoId,
			String tagName);
	/**
	 * 删除视频标签---关联关系
	 * @param videoValueId
	 * @return
	 * @author tianguifang
	 */
	public Integer deleteVideoValue(String videoValueId);
}
