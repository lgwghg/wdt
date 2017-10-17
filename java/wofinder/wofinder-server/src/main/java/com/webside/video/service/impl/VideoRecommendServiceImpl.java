/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoRecommendMapper;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoRecommendEntity;
import com.webside.video.service.VideoRecommendService;
import com.webside.video.service.VideoService;
import com.webside.video.vo.VideoRecommendVo;
import com.webside.video.vo.VideoVo;

/**
 * 首页推荐视频服务实现类
 *
 * @author zengxn
 * @date 2017-04-20 22:21:52
 */
@Service("videoRecommendService")
public class VideoRecommendServiceImpl extends AbstractService<VideoRecommendEntity, String> implements VideoRecommendService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoRecommendMapper);
	}

	/**
	 * 首页推荐视频 DAO定义
	 */
	@Autowired
	private VideoRecommendMapper videoRecommendMapper;

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
	 * 视频 Service定义
	 */
	@Autowired
	private VideoService videoService;

	/**
	 * 新增首页推荐视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(VideoRecommendEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增首页推荐视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改首页推荐视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(VideoRecommendEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改首页推荐视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询首页推荐视频
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoRecommendEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<VideoRecommendEntity> list = super.queryListByPage(parameter);
			for (VideoRecommendEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getVideo() != null && StringUtils.isNotBlank(entity.getVideo().getId())) {
					entity.setVideo(videoService.findById(entity.getVideo().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询首页推荐视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID、ID获取首页推荐视频
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoRecommendEntity findByVideoId(String videoId, String id) {
		try {
			VideoRecommendEntity entity = new VideoRecommendEntity(id);
			entity.setVideo(new VideoEntity(videoId));
			List<VideoRecommendEntity> list = videoRecommendMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频ID、ID获取首页推荐视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID删除首页推荐视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByVideoId(String videoId) {
		try {
			VideoRecommendEntity entity = new VideoRecommendEntity();
			entity.setVideo(new VideoEntity(videoId));
			return videoRecommendMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频ID删除首页推荐视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ids批量清空首页推荐视频的视频关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int resetVideoIdBatchByVideoId(List<String> videoIds, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("videoIds", videoIds);
			map.put("updateUserId", updateUserId);
			map.put("statusValue", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_0);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return videoRecommendMapper.resetVideoIdBatchByVideoId(map);
		} catch (Exception e) {
			logger.error("根据视频ids批量清空首页推荐视频的视频关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询首页推荐视频9个视频
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoRecommendVo> queryListByParam(Map<String, Object> map) {
		try {
			List<VideoRecommendEntity> list = videoRecommendMapper.queryListByParam(map);
			List<VideoRecommendVo> recommendVos = new ArrayList<VideoRecommendVo>(list.size());
			for (VideoRecommendEntity videoRecommend : list) {
				VideoVo videoVo = videoService.findVideoVOByVideoId(videoRecommend.getVideo().getId());
				if (videoVo != null) {
					VideoRecommendVo recommendVo = new VideoRecommendVo(videoVo.getShortId());
					recommendVo.setCover(videoVo.getCover());
					recommendVo.setTitle(videoVo.getTitle());
					recommendVo.setIntroduction(videoVo.getVideoStationList().get(0).getIntroduction());
					recommendVo.setVideoCommentCount(videoVo.getVideoCommentCount());
					recommendVo.setViewCount(videoVo.getViewCount());
					recommendVos.add(recommendVo);
				}
			}
			return recommendVos;
		} catch (Exception e) {
			logger.error("查询首页推荐视频9个视频出错：", e);
			throw new ServiceException(e);
		}
	}
}
