/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.up.model.UpNameEntity;

/**
 * 视频作者名称数据访问接口
 *
 * @author zengxn
 * @date 2017-04-15 18:29:36
 */
@Repository
public interface UpNameMapper extends BaseMapper<UpNameEntity, String> {
	/**
	 * 批量修改视频作者名称的视频作者
	 * 
	 * @author zengxn
	 */
	int updateUpIdBatchById(Map<String, Object> map);
}
