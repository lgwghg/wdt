/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoGradeMapper;
import com.webside.video.model.VideoGradeEntity;
import com.webside.video.service.VideoGradeService;
import com.webside.video.service.VideoService;

/**
 * 视频评分服务实现类
 * 
 * @author zfei
 * @date 2017-06-12 16:04:07
 */
@Service("videoGradeService")
public class VideoGradeServiceImpl extends AbstractService<VideoGradeEntity, String> implements VideoGradeService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoGradeMapper);
	}

	/**
	 * 视频评分 DAO定义
	 */
	@Autowired
	private VideoGradeMapper videoGradeMapper;
	@Autowired
	private VideoService videoService;

	/**
	 * 新增视频评分
	 * 
	 * @param VideoGradeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	@Override
	public int insert(VideoGradeEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增视频评分出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改视频评分
	 * 
	 * @param VideoGradeEntity
	 * @throws ServiceException
	 * @author zfei
	 */
	@Override
	public int update(VideoGradeEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改视频评分出错：", e);
			throw new ServiceException(e);
		}
	}
	
	public void addOrUpdate(String videoId, double score) {
		String userId = ShiroAuthenticationManager.getUserId();
		VideoGradeEntity entity = findByVideoAndUser(videoId, userId);
		boolean isUpdate = true;// 是否更新
		if (entity == null || StringUtils.isBlank(entity.getId())) {
			entity = new VideoGradeEntity();
			entity.setId(IdGen.uuid());
			entity.setVideoId(videoId);
			entity.setUserId(userId);
			entity.setCreateTime(System.currentTimeMillis());
			entity.setStatus(StringUtils.toInteger(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));// 默认有效
			isUpdate = false;
		}
		
		entity.setScore(score);
		if (isUpdate) {
			super.updateById(entity);
		} else {
			super.insert(entity);
		}
		
		videoService.updateScoreById(videoId);
		
	}
	
	public VideoGradeEntity findByVideoAndUser(String videoId, String userId) {
		if (StringUtils.isBlank(videoId)) {
			throw new ServiceException("视频videoId不能为空");
		}
		if (StringUtils.isBlank(userId)) {
			userId = ShiroAuthenticationManager.getUserId();
		}
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("videoId", videoId);
		map.put("userId", userId);
		List<VideoGradeEntity> list = super.queryListByPage(map);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}
	/**
	 * 根据用户id查询今天用户评分数量
	 * @param paramMap
	 * @return
	 * @author tianguifang
	 */
	@Override
	public Integer queryUserTodayGradeNumByUserId(Map<String, Object> paramMap) {
		return videoGradeMapper.queryUserTodayGradeNumByUserId(paramMap);
	}
	
}
