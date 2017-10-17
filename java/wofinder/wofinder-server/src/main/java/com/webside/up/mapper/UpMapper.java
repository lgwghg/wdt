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
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpVo;

/**
 * 视频作者数据访问接口
 *
 * @author zengxn
 * @date 2017-04-15 18:28:19
 */
@Repository
public interface UpMapper extends BaseMapper<UpEntity, String> {

	/**
	 * 重置符合要求的视频作者游戏字段
	 * 
	 * @author zengxn
	 */
	int resetGameIdByGameId(Map<String, Object> map);

	/**
	 * 批量修改视频作者状态
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(Map<String, Object> map);

	/**
	 * 根据id查询up人物信息
	 * 
	 * @param id
	 * @return
	 * @author tianguifang
	 */
	UpVo findUpDetailById(String id);
}
