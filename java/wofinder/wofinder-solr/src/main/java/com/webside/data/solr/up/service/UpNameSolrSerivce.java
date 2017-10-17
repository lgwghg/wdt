/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.data.solr.up.service;

import com.webside.data.solr.common.Page;
import com.webside.data.solr.up.model.UpName;

/**
 * 视频作者名称 solr操作类
 *
 * @author zengxn
 * @date 2017-05-25 14:17:39
 */
public interface UpNameSolrSerivce {
	/**
	 * 添加或修改视频作者名称
	 * 
	 * @author zengxn
	 */
	void insertOrUpdate(String id, String name, String type);

	/**
	 * 根据ID删除视频作者名称
	 * 
	 * @author zengxn
	 */
	void deleteById(String id);

	/**
	 * 根据条件分页查询视频作者名称
	 * 
	 * @author zengxn
	 */
	void queryListByPage(Page<UpName> page);
}
