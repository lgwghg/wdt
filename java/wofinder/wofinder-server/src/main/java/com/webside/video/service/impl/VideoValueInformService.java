/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.video.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.sys.mapper.ValueMapper;
import com.webside.sys.model.ValueEntity;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserCacheService;
import com.webside.util.IdGen;
import com.webside.video.service.IVideoValueInformService;
import com.webside.video.mapper.IVideoValueInformMapper;
import com.webside.video.mapper.VideoMapper;
import com.webside.video.mapper.VideoValueMapper;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoValueEntity;
import com.webside.video.model.VideoValueInform;

/**
 * 视频标签操作日志服务实现类
 *
 * @author tianguifang
 * @date 2017-08-21 16:50:17
 */
@Service("videoValueInformService")
public class VideoValueInformService extends AbstractService<VideoValueInform, String> implements IVideoValueInformService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(videoValueInformMapper);
	}

	/**
	 * 视频标签操作日志 DAO定义
	 */
	@Autowired
	private IVideoValueInformMapper videoValueInformMapper;
	@Autowired
	private VideoValueMapper videoValueMapper;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private ValueMapper valueMapper;
	@Autowired
	private UserCacheService userCacheService;
	/*
	 * 按条件查询视频标签操作日志
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<VideoValueInform> queryListByPage(Map<String, Object> parameter) {
		List<VideoValueInform> informList = videoValueInformMapper.queryListByPage(parameter);
		if (!CollectionUtils.isEmpty(informList)) {
			for (VideoValueInform inform : informList) {
				VideoEntity video = videoMapper.findById(inform.getVideoId());
				if (video != null) {
					inform.setVideoTitle(video.getTitle());
					inform.setVideoShortId(video.getShortId());
				}
				ValueEntity value = valueMapper.findById(inform.getValueId());
				if (value != null) {
					inform.setValueName(value.getName());
				}
				
				UserEntity user = userCacheService.getUserEntityByUserId(inform.getCreateId());
				if (user != null) {
					inform.setCreateName(user.getNickName());
				}
			}
		}
		return informList;
	}
	/**
	 * 处理举报问题
	 * @param id
	 * @param isDeal
	 * @return
	 * @author tianguifang
	 */
	@Override
	public int updateDealWithInform(String id, String isDeal) {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(isDeal) || (!"1".equals(isDeal) && !"0".equals(isDeal))) {
			return 0;
		}
		VideoValueInform inform = videoValueInformMapper.findById(id);
		if (inform == null) {
			return 0;
		}
		String videoValueId= inform.getVideoValueId();
		VideoValueEntity vValue = new VideoValueEntity();
		vValue.setId(videoValueId);
		DictEntity status = new DictEntity();
		if ("1".equals(isDeal)) { // 处理
			if (inform.getInformReason() == GlobalConstant.VIDEO_TAG_INFORM_TYPE_4) {// 恶意删除，需要恢复
				status.setValue("1");
				vValue.setInformStatus(GlobalConstant.VIDEO_TAG_INFORM_STATUS_0);
			} else {
				status.setValue("0");
				vValue.setInformStatus(GlobalConstant.VIDEO_TAG_INFORM_STATUS_2);
			}
			
		} else { // 不处理
			status.setValue("1");
			vValue.setInformStatus(GlobalConstant.VIDEO_TAG_INFORM_STATUS_0);
			
		}
		vValue.setStatus(status);
		vValue.setUpdateTime(System.currentTimeMillis() + "");
		vValue.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
		int result = videoValueMapper.updateById(vValue);
		return result;
	}
	
	/**
	 * 新增举报
	 */
	@Override
	public int insert(VideoValueInform inform) {
		if (inform == null || StringUtils.isBlank(inform.getVideoValueId()) || inform.getInformReason() == null) {
			return 0;
		}
		VideoValueEntity value = videoValueMapper.findAllStatusById(inform.getVideoValueId());
		if (value == null || (value.getInformStatus() != null && (value.getInformStatus() == 1 || value.getInformStatus() == 2))) {
			return 0;
		}
		inform.setId(IdGen.uuid());
		inform.setCreateTime(System.currentTimeMillis());
		inform.setStatus(1);
		inform.setCreateId(ShiroAuthenticationManager.getUserId());
		int result = videoValueInformMapper.insert(inform);
		if (result > 0) {//成功后更新t_video_value表的举报状态
			VideoValueEntity videoValue = new VideoValueEntity();
			videoValue.setId(inform.getVideoValueId());
			videoValue.setInformStatus(GlobalConstant.VIDEO_TAG_INFORM_STATUS_1);
			videoValue.setUpdateTime(System.currentTimeMillis() + "");
			videoValue.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
			videoValueMapper.updateById(videoValue);
		}
		return result;
	}
}
