/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.video.service;

import java.util.List;
import java.util.Map;

import com.webside.video.model.VideoValueInform;

/**
 * 视频标签操作日志服务接口
 *
 * @author tianguifang
 * @date 2017-08-21 16:50:17
 */
public interface IVideoValueInformService 
{
	/*
	 * 按条件查询视频标签操作日志
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<VideoValueInform> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增视频标签操作日志
	 * @param VideoValueInform
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final VideoValueInform entity);
	
	/*
	 * 修改视频标签操作日志
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final VideoValueInform entity);
	
	/*
	 * 根据ID获取视频标签操作日志
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public VideoValueInform findById(String id);
	
	/*
	 * 根据对象删除视频标签操作日志
	 * @param VideoValueInform
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	
	/**
	 * 处理举报问题
	 * @param id
	 * @param isDeal
	 * @return
	 * @author tianguifang
	 */
	public int updateDealWithInform(String id, String isDeal);
}
