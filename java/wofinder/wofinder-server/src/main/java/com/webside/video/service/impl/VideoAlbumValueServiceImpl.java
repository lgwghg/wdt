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

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoAlbumValueMapper;
import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.model.VideoAlbumValueEntity;
import com.webside.video.service.VideoAlbumValueService;

/**
 * 视频属性值关联服务实现类
 *
 * @author zengxn
 * @date 2017-04-20 21:18:02
 */
@Service("videoAlbumValueService")
public class VideoAlbumValueServiceImpl extends AbstractService<VideoAlbumValueEntity, String> implements VideoAlbumValueService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoAlbumValueMapper);
	}

	/**
	 * 视频属性值关联 DAO定义
	 */
	@Autowired
	private VideoAlbumValueMapper videoAlbumValueMapper;

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

	/**
	 * 新增视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(VideoAlbumValueEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (entity.getCreateUser() == null || StringUtils.isBlank(entity.getCreateUser().getId())) {
				entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增视频专辑属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(VideoAlbumValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频专辑属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频专辑ID获取视频属性值关联集合
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoAlbumValueEntity> queryListByVideoAlbumId(String videoAlbumId) {
		try {
			VideoAlbumValueEntity entity = new VideoAlbumValueEntity();
			entity.setVideoAlbum(new VideoAlbumEntity(videoAlbumId));
			return videoAlbumValueMapper.queryListByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频专辑ID获取视频属性值关联集合出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoAlbumValueEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<VideoAlbumValueEntity> list = super.queryListByPage(parameter);
			for (VideoAlbumValueEntity entity : list) {
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
			logger.error("按条件查询视频专辑属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID删除视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByVideoAlbumId(String videoAlbumId) {
		try {
			VideoAlbumValueEntity entity = new VideoAlbumValueEntity();
			entity.setVideoAlbum(new VideoAlbumEntity(videoAlbumId));
			return videoAlbumValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频ID删除视频专辑属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性值ID删除视频专辑属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByValueId(String valueId) {
		try {
			VideoAlbumValueEntity entity = new VideoAlbumValueEntity();
			entity.setValue(new ValueEntity(valueId));
			return videoAlbumValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据属性值ID删除视频专辑属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频专辑属性值关联的视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateVideoAlbumIdBatchById(List<String> ids, String videoAlbumId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("videoAlbumId", videoAlbumId);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return videoAlbumValueMapper.updateVideoAlbumIdBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频专辑属性值关联的视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频专辑ID、属性值ID、ID获取视频属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoAlbumValueEntity findByVideoAlbumIdValueId(String videoAlbumId, String valueId, String id) {
		try {
			VideoAlbumValueEntity entity = new VideoAlbumValueEntity();
			entity.setVideoAlbum(new VideoAlbumEntity(videoAlbumId));
			entity.setValue(new ValueEntity(valueId));
			List<VideoAlbumValueEntity> list = videoAlbumValueMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频专辑ID、属性值ID、ID获取视频属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}
}
