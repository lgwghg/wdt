/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
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
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.sys.model.AttributeEntity;
import com.webside.sys.model.AttributeValueEntity;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.AttributeValueService;
import com.webside.sys.service.ValueService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoValueMapper;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoValueEntity;
import com.webside.video.model.VideoValueLog;
import com.webside.video.service.IVideoValueLogService;
import com.webside.video.service.VideoValueService;

/**
 * 视频属性值关联服务实现类
 * 
 * @author zengxn
 * @date 2017-04-20 21:18:02
 */
@Service("videoValueService")
public class VideoValueServiceImpl extends AbstractService<VideoValueEntity, String> implements VideoValueService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoValueMapper);
	}

	/**
	 * 视频属性值关联 DAO定义
	 */
	@Autowired
	private VideoValueMapper videoValueMapper;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 属性值 Service定义
	 */
	@Autowired
	private ValueService valueService;
	
	@Autowired
	private IVideoValueLogService videoValueLogService;
	@Autowired
	private AttributeValueService attributeValueService;
	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
	// 视频标签，在数据库对应的key - 游戏视频
	private AttributeEntity attributeEntity = new AttributeEntity("1d3268c804ae42b88de004732d88b5b0");
	/**
	 * 新增视频属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(VideoValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getId())) {
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增视频属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(VideoValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}
	/**
	 * 根据t_video_value id 删除视频标签关联关系
	 */
	@Override
	public Integer deleteVideoValue(String videoValueId) {
		VideoValueEntity vv = new VideoValueEntity();
		vv.setId(videoValueId);
		vv.setStatus(new DictEntity("0"));
		int re = this.updateById(vv);
		if (re > 0) {
			VideoValueLog valueLog = new VideoValueLog();
			valueLog.setId(IdGen.uuid());
			valueLog.setVideoValueId(videoValueId);
			valueLog.setCreateId(ShiroAuthenticationManager.getUserId());
			valueLog.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			valueLog.setCreateTime(System.currentTimeMillis());
			valueLog.setOperateType(GlobalConstant.VIDEO_TAG_OPERATE_TYPE_4);
			valueLog.setStatus(1);
			videoValueLogService.insert(valueLog);
			return 1;
		}
		
		return 0;
	}
	/**
	 * 根据视频ID获取视频属性值关联集合
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoValueEntity> queryListByVideoId(String videoId) {
		try {
			VideoValueEntity entity = new VideoValueEntity();
			entity.setVideo(new VideoEntity(videoId));
			return videoValueMapper.queryListByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频ID获取视频属性值关联集合出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoValueEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<VideoValueEntity> list = super.queryListByPage(parameter);
			for (VideoValueEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getValue() != null && StringUtils.isNotBlank(entity.getValue().getId())) {
					entity.setValue(valueService.findById(entity.getValue().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID删除视频属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByVideoId(String videoId) {
		try {
			VideoValueEntity entity = new VideoValueEntity();
			entity.setVideo(new VideoEntity(videoId));
			return videoValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频ID删除视频属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性值ID删除视频属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByValueId(String valueId) {
		try {
			VideoValueEntity entity = new VideoValueEntity();
			entity.setValue(new ValueEntity(valueId));
			return videoValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据属性值ID删除视频属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频属性值关联的视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateVideoIdBatchById(List<String> ids, String videoId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("videoId", videoId);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return videoValueMapper.updateVideoIdBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频属性值关联的视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID、属性值ID、ID获取视频属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoValueEntity findByVideoValueId(String videoId, String valueId, String id) {
		try {
			VideoValueEntity entity = new VideoValueEntity();
			entity.setVideo(new VideoEntity(videoId));
			entity.setValue(new ValueEntity(valueId));
			List<VideoValueEntity> list = videoValueMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频ID、属性值ID、ID获取视频属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	public int updateVideoIdByTargetForDif(String videoId, String targetId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("videoId", videoId);
		map.put("targetId", targetId);
		List<String> list = videoValueMapper.findIdByVideoForDif(map);
		int result = 0;
		if(list != null && !list.isEmpty()) {
			result = updateVideoIdBatchById(list, videoId, null, null);
		}

		return result;
	}

	/**
	 * 视频标签点赞
	 * @param id
	 * @return
	 * @author tianguifang
	 */
	@Override
	public Map<String, Object> updateUpVideoValue(String id) {
		Map<String, Object> resultMap = new HashMap<>();
		VideoValueEntity videoValue = videoValueMapper.findById(id);
		if (videoValue != null) {
			String userId = ShiroAuthenticationManager.getUserId();
			// 查询最新点赞或取消点赞记录
			List<VideoValueLog> valueLogs = videoValueLogService.queryUserUpVideoLastest(id, userId);
			
			VideoValueLog insertLog = new VideoValueLog();
			insertLog.setId(IdGen.uuid());
			insertLog.setCreateTime(System.currentTimeMillis());
			insertLog.setCreateId(userId);
			insertLog.setVideoValueId(id);
			insertLog.setStatus(1);
			// 点赞数量
			Integer upNum = videoValue.getUpNum();
			if (upNum == null) { upNum = 0; }
			if (CollectionUtils.isEmpty(valueLogs)) {
				// 用户第一次为该视频点赞
				insertLog.setOperateType(GlobalConstant.VIDEO_TAG_OPERATE_TYPE_1);// 点赞
				upNum = upNum + 1;
				resultMap.put("operateType", 1);
			} else {
				VideoValueLog log = valueLogs.get(0);
				if (log.getOperateType() == 1) {// 点赞,则取消点赞
					insertLog.setOperateType(GlobalConstant.VIDEO_TAG_OPERATE_TYPE_2);// 取消点赞
					if (upNum > 0) {
						upNum = upNum - 1;
					}
					resultMap.put("operateType", 2);
				} else if (log.getOperateType() == 2) {// 取消点赞，则点赞
					insertLog.setOperateType(GlobalConstant.VIDEO_TAG_OPERATE_TYPE_1);// 点赞
					upNum = upNum + 1;
					resultMap.put("operateType", 1);
				}

			}
			// 新增视频标签操作记录
			int result = videoValueLogService.insert(insertLog);
			if (result > 0) { // 更新点赞数量
				VideoValueEntity value = new VideoValueEntity();
				value.setId(videoValue.getId());
				value.setUpNum(upNum);
				value.setUpdateTime(System.currentTimeMillis() + "");
				value.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
				videoValueMapper.updateById(value);
				
				resultMap.put("success", true);
				resultMap.put("upNum", upNum);
			} else {
				resultMap.put("success", false);
				resultMap.put("message", "操作失败，请稍候再试");
			}
			
		} else {
			resultMap.put("success", false);
			resultMap.put("message", "未找到相应标签");
		}
		return resultMap;
	}
	/**
	 * 新增视频标签
	 * @param userId
	 * @param videoId
	 * @param tagName
	 * @return
	 * @author tianguifang
	 */
	@Override
	public Map<String, Object> insertVideoValue(String userId, String videoId, String tagName) {
		Map<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId) || StringUtils.isBlank(tagName)) {
			resultMap.put("success", false);
			resultMap.put("message", "参数错误");
			return resultMap;
		}
		ValueEntity valueEntity = saveSysValue(tagName);
		VideoValueEntity videoValueEntity = this.findByVideoValueId(videoId, valueEntity.getId(), null);
		if (videoValueEntity == null) {
			videoValueEntity = new VideoValueEntity();
			videoValueEntity.setId(IdGen.uuid());
			videoValueEntity.setValue(valueEntity);
			videoValueEntity.setVideo(new VideoEntity(videoId));
			videoValueEntity.setStatus(status);
			videoValueEntity.setInformStatus(0);
			videoValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
			UserEntity user = new UserEntity();
			user.setId(userId);
			videoValueEntity.setCreateUser(user);
			int re = this.insert(videoValueEntity);
			if (re > 0) {
				VideoValueLog valueLog = new VideoValueLog();
				valueLog.setId(IdGen.uuid());
				valueLog.setVideoValueId(videoValueEntity.getId());
				valueLog.setCreateId(userId);
				valueLog.setCreateUser(user);
				valueLog.setCreateTime(System.currentTimeMillis());
				valueLog.setOperateType(GlobalConstant.VIDEO_TAG_OPERATE_TYPE_0);
				valueLog.setStatus(1);
				videoValueLogService.insert(valueLog);
				
				resultMap.put("success", true);
				resultMap.put("message", "保存成功");
				resultMap.put("videoValueId", videoValueEntity.getId());
				resultMap.put("createId", userId);
				return resultMap;
			}
		} else {
			resultMap.put("success", false);
			resultMap.put("message", "该视频标签已经存在");
			return resultMap;
		}
		return resultMap;
	}
	
	protected ValueEntity saveSysValue(String tagName) {
		ValueEntity valueEntity = valueService.findByNameId(tagName, null);
		if (valueEntity == null) {
			valueEntity = new ValueEntity();
			valueEntity.setName(tagName);
			valueEntity.setStatus(status);
			valueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
			valueEntity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			valueService.insert(valueEntity);
		}

		AttributeValueEntity attributeValueEntity = attributeValueService.findByAttributeValueId(attributeEntity, valueEntity, null);
		if (attributeValueEntity == null) {
			attributeValueEntity = new AttributeValueEntity();
			attributeValueEntity.setAttribute(attributeEntity);
			attributeValueEntity.setValue(valueEntity);
			attributeValueEntity.setStatus(status);
			attributeValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
			attributeValueService.insert(attributeValueEntity);
		}

		return valueEntity;
	}
}
