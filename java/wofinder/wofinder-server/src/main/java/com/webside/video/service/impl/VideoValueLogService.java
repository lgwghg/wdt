/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.video.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserCacheService;
import com.webside.video.service.IVideoValueLogService;
import com.webside.video.mapper.IVideoValueLogMapper;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoValueLog;

/**
 * 视频标签操作日志服务实现类
 *
 * @author tianguifang
 * @date 2017-08-21 16:48:38
 */
@Service("videoValueLogService")
public class VideoValueLogService extends AbstractService<VideoValueLog, String> implements IVideoValueLogService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(videoValueLogMapper);
	}

	/**
	 * 视频标签操作日志 DAO定义
	 */
	@Autowired
	private IVideoValueLogMapper videoValueLogMapper;
	@Autowired
	private UserCacheService userCacheService;
	@Autowired
	private ValueService valueService;
	/**
	 * 查询用户对某视频的最新点赞或取消点赞记录
	 * @param id
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	@Override
	public List<VideoValueLog> queryUserUpVideoLastest(String videoValueId,
			String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("videoValueId", videoValueId);
		param.put("userId", userId);
		return videoValueLogMapper.queryUserUpVideoLastest(param);
	}
	
	/**
	 * 查询视频标签的操作记录 20条 ，操作时间倒序
	 * @param videoId
	 * @return
	 * @author tianguifang
	 */
	@Override
	public List<VideoValueLog> queryVideoValueLog(String videoId) {
		List<VideoValueLog> valueLogList = videoValueLogMapper.queryVideoValueLog(videoId);
		if (!CollectionUtils.isEmpty(valueLogList)) {
			for (VideoValueLog valueLog : valueLogList) {
				UserEntity createUser = new UserEntity();
				UserEntity user = userCacheService.getUserEntityByUserId(valueLog.getCreateId());
				if (user != null) {
					createUser.setId(user.getId());
					createUser.setNickName(user.getNickName());
					createUser.setPhoto(user.getPhoto());
					valueLog.setCreateUser(createUser);
				}
				ValueEntity valueEntity = valueService.findById(valueLog.getValueId());
				if(valueEntity!=null){
					valueLog.setValueName(valueEntity.getName());
				}
			}
			
		}
		return valueLogList;
	}
	
	/*
	 * 按条件查询视频标签操作日志
	 * @throws Exception
	 * @author tianguifang
	 */
	@Override
	public List<VideoValueLog> queryListByPage(Map<String, Object> parameter) {
		List<VideoValueLog> valueLogList = videoValueLogMapper.queryListByPage(parameter);
		if (!CollectionUtils.isEmpty(valueLogList)) {
			for (VideoValueLog valueLog : valueLogList) {
				UserEntity createUser = new UserEntity();
				UserEntity user = userCacheService.getUserEntityByUserId(valueLog.getCreateId());
				if (user != null) {
					createUser.setId(user.getId());
					createUser.setNickName(user.getNickName());
					createUser.setPhoto(user.getPhoto());
					valueLog.setCreateUser(createUser);
				}
				ValueEntity valueEntity = valueService.findById(valueLog.getValueId());
				if(valueEntity!=null){
					valueLog.setValueName(valueEntity.getName());
				}
				
			}
			
		}
		return null;
	}
}
