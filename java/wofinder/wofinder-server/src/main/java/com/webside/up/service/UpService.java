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

import com.webside.up.model.UpEntity;
import com.webside.up.model.UpVo;
import com.webside.up.vo.VideoUpVo;

/**
 * 视频作者服务接口
 *
 * @author zengxn
 * @date 2017-04-15 18:28:19
 */
public interface UpService {
	
	public static String BEANNAME = "upService";
	
	/**
	 * 按条件查询视频作者
	 * 
	 * @author zengxn
	 */
	List<UpEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增视频作者
	 * 
	 * @author zengxn
	 */
	int insert(UpEntity entity);

	/**
	 * 根据ID修改视频作者
	 * 
	 * @author zengxn
	 */
	int updateById(UpEntity entity);

	/**
	 * 批量修改视频作者状态
	 * 
	 * @author zengxn
	 */
	int updateStatusBatchById(List<String> upIds, String statusValue, String updateUserId, String updateTime);

	/**
	 * 根据ID获取视频作者
	 * 
	 * @author zengxn
	 */
	UpEntity findById(String id);

	/**
	 * 根据ID批量查询视频作者
	 * 
	 * @author zengxn
	 */
	List<UpEntity> queryListBatchById(List<String> ids);

	/**
	 * 根据ID删除视频作者
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID删除视频作者，并且删除相关子表记录
	 * 
	 * @author zengxn
	 */
	int deleteRelevantById(String id, String updateUserId);

	/**
	 * 根据ID批量删除视频作者
	 * 
	 * @author zengxn
	 */
	int deleteBatchById(List<String> ids);

	/**
	 * 根据ID修改视频作者名称
	 * 
	 * @author zengxn
	 */
	int updateNameById(String id, String name, String updateUserId, String updateTime);

	/**
	 * 根据ID修改视频作者人气指数
	 * 
	 * @author zengxn
	 */
	int updatePopularityIndexById(String id, Long popularityIndex, String updateUserId, String updateTime);

	/**
	 * 重置符合要求的视频作者游戏字段
	 * 
	 * @author zengxn
	 */
	int resetGameIdByGameId(String gameId, String updateUserId, String updateTime);

	/**
	 * 合并视频作者
	 * 
	 * @author zengxn
	 */
	Map<String, Object> doMerge(String updateUserId, List<String> upIdList, List<String> checkStationIdList);

	/**
	 * 根据upId,查询人物详情页信息
	 * 
	 * @param id
	 * @return
	 */
	UpVo queryUpDetailByUpId(String id);

	/**
	 * 根据人物名称id批量查询视频作者VO数据集合
	 * 
	 * @author zengxn
	 */
	List<VideoUpVo> queryListVideoUpVOBathchByUpNameId(List<String> upNameIds);
}
