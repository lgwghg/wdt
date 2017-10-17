/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.service;

import java.util.List;
import java.util.Map;

import com.webside.dict.model.DictEntity;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpStationEntity;

/**
 * 视频作者站点服务接口
 *
 * @author zengxn
 * @date 2017-04-15 18:29:58
 */
public interface UpStationService {
	
	public static String BEANNAME = "upStationService";
	
	/**
	 * 按条件查询视频作者站点
	 * 
	 * @author zengxn
	 */
	List<UpStationEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频作者站点
	 * 
	 * @author zengxn
	 */
	int insert(UpStationEntity entity);

	/**
	 * 根据ID修改视频作者站点
	 * 
	 * @author zengxn
	 */
	int updateById(UpStationEntity entity);

	/**
	 * 批量修改视频作者站点的状态
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(List<String> ids, String statusValue, String updateUserId, String updateTime);

	/**
	 * 根据ID获取视频作者站点
	 * 
	 * @author zengxn
	 */
	UpStationEntity findById(String id);

	/**
	 * 根据视频作者id、站点值、第三方值、id获取视频作者站点
	 * 
	 * @author zengxn
	 */
	UpStationEntity findByUpStationThirdPartyId(UpEntity up, DictEntity station, DictEntity thirdParty, String id);

	/**
	 * 通过站点 和 用户ID（非本站ID） 查找
	 * @param station
	 * @param homeId
	 * @return
	 */
	public UpStationEntity findByStationAndHomeId(String stationValue, String homeId);
	
	/**
	 * 根据ID删除视频作者站点
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据视频作者ID删除视频作者站点
	 * 
	 * @author zengxn
	 */
	int deleteByUpId(String upId);

	/**
	 * 根据ID批量删除视频作者站点
	 * 
	 * @author zengxn
	 */
	int deleteBatchById(List<String> ids);

	/**
	 * 根据upIds查询视频作者站点
	 * 
	 * @author zengxn
	 */
	List<UpStationEntity> queryListByUpIds(List<String> upIds, String stationValue, String thirdPartyValue);

	/**
	 * 批量修改视频作者站点的视频作者
	 * 
	 * @author zengxn
	 */
	int updateUpIdBatchById(List<String> ids, String upId, String updateUserId, String updateTime);

	/**
	 * 根据upId查询视频作者站点
	 * 
	 * @author zengxn
	 */
	List<UpStationEntity> queryListByUpId(String upId);
}
