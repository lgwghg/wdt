/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.data.solr.video.service;

import com.webside.data.solr.common.Page;
import com.webside.data.solr.video.model.VideoAlbum;

/**
 * 视频专辑 solr操作类
 *
 * @author zengxn
 * @date 2017-05-25 14:17:39
 */
public interface VideoAlbumSolrSerivce {
	/**
	 * 添加或修改视频专辑
	 * 
	 * @author zengxn
	 */
	void insertOrUpdate(String id, String title);

	/**
	 * 根据ID删除视频专辑
	 * 
	 * @author zengxn
	 */
	void deleteById(String id);

	/**
	 * 根据条件分页查询视频专辑
	 * 
	 * @author zengxn
	 */
	void queryListByPage(Page<VideoAlbum> page);
}
