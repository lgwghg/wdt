/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.up.model.UpStationEntity;

/**
 * 视频作者站点数据访问接口
 *
 * @author zengxn
 * @date 2017-04-15 18:29:58
 */
@Repository
public interface UpStationMapper extends BaseMapper<UpStationEntity, String> {
	/**
	 * 根据upIds查询视频作者站点
	 * 
	 * @author zengxn
	 */
	List<UpStationEntity> findByUpIds(Map<String, Object> map);

	/**
	 * 批量修改视频作者站点的视频作者
	 * 
	 * @author zengxn
	 */
	int updateUpIdBatchById(Map<String, Object> map);

	/**
	 * 批量修改视频作者站点的状态
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(Map<String, Object> map);
}
