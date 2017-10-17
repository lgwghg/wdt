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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.redis.RedisManager;
import com.webside.up.service.UpService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoStationMapper;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;
import com.webside.video.model.VideoValueEntity;
import com.webside.video.service.VideoService;
import com.webside.video.service.VideoStationService;
import com.webside.video.service.VideoValueService;
import com.webside.video.vo.VideoStationCacheVo;
import com.webside.video.vo.VideoStationVo;

/**
 * 视频站点服务实现类
 *
 * @author zengxn
 * @date 2017-04-20 21:15:55
 */
@Service("videoStationService")
public class VideoStationServiceImpl extends AbstractService<VideoStationEntity, String> implements VideoStationService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoStationMapper);
	}

	/**
	 * 视频站点 DAO定义
	 */
	@Autowired
	private VideoStationMapper videoStationMapper;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 视频作者管理 Service定义
	 */
	@Autowired
	private UpService upService;
	@Autowired
	private VideoValueService videoValueService;

	/**
	 * 视频管理 Service定义
	 */
	@Autowired
	private VideoService videoService;

	/**
	 * redis 定义
	 */
	@Autowired
	private RedisManager redisManager;

	/**
	 * 线程池定义
	 */
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	/**
	 * entity对象转换成缓存中的对象
	 * 
	 * @author zengxn
	 */
	private VideoStationCacheVo entityToRedisVo(VideoStationEntity entity, VideoStationCacheVo vo) {
		if (StringUtils.isNotBlank(entity.getId())) {
			vo.setId(entity.getId());
		}
		if (entity.getVideo() != null && StringUtils.isNotBlank(entity.getVideo().getId())) {
			vo.setVid(entity.getVideo().getId());
		}
		if (StringUtils.isNotBlank(entity.getUrl())) {
			vo.setU(entity.getUrl());
		}
		if (StringUtils.isNotBlank(entity.getFlashUrl())) {
			vo.setFu(entity.getFlashUrl());
		}
		if (StringUtils.isNotBlank(entity.getIntroduction())) {
			vo.setI(entity.getIntroduction());
		}
		if (entity.getViewCount() != null) {
			vo.setVc(entity.getViewCount());
		}
		if (entity.getCommentCount() != null) {
			vo.setCc(entity.getCommentCount());
		}
		if (entity.getUp() != null) {
			vo.setUp(entity.getUp());
		}
		if (entity.getStation() != null) {
			vo.setS(entity.getStation());
		}
		return vo;
	}

	/**
	 * 缓存中的对象转换成entity对象
	 * 
	 * @author zengxn
	 */
	private VideoStationEntity redisVoToEntity(VideoStationCacheVo vo, VideoStationEntity entity) {
		if (entity == null) {
			entity = new VideoStationEntity();
		}
		if (StringUtils.isNotBlank(vo.getId())) {
			entity.setId(vo.getId());
		}
		if (StringUtils.isNotBlank(vo.getVid())) {
			entity.setVid(vo.getVid());
		}
		if (StringUtils.isNotBlank(vo.getU())) {
			entity.setUrl(vo.getU());
		}
		if (StringUtils.isNotBlank(vo.getFu())) {
			entity.setFlashUrl(vo.getFu());
		}
		if (StringUtils.isNotBlank(vo.getI())) {
			entity.setIntroduction(vo.getI());
		}
		if (vo.getVc() != null) {
			entity.setViewCount(vo.getVc());
		}
		if (vo.getCc() != null) {
			entity.setCommentCount(vo.getCc());
		}
		if (vo.getUp() != null) {
			entity.setUp(vo.getUp());
		}
		if (vo.getS() != null) {
			entity.setStation(vo.getS());
		}

		return entity;
	}

	/**
	 * 更新视频缓存信息
	 * 
	 * @author zengxn
	 */
	private void updateVideoStationToRedis(VideoStationEntity entity) {
		if (entity != null && StringUtils.isNotBlank(entity.getId()) && entity.getVideo() != null && StringUtils.isNotBlank(entity.getVideo().getId())) {
			cachedThreadPool.execute(new UpdateRedisVideoStationRunner(entity));
		}
	}

	/**
	 * 更新redis中的video信息
	 * 
	 * @author zengxn
	 */
	class UpdateRedisVideoStationRunner implements Runnable {
		private VideoStationEntity entity;

		public UpdateRedisVideoStationRunner(VideoStationEntity entity) {
			this.entity = entity;
		}

		@Override
		public void run() {
			VideoStationCacheVo vo = redisManager.getVByMap(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + entity.getVideo().getId(), CacheConstant.VideoConstant.VIDEO_STATION_REDIS_CACHE_KEY + entity.getId(), VideoStationCacheVo.class);
			if (vo == null) {
				vo = new VideoStationCacheVo();
			}
			vo = entityToRedisVo(entity, vo);
			// 存缓存
			redisManager.setVByMap(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + entity.getVideo().getId(), CacheConstant.VideoConstant.VIDEO_STATION_REDIS_CACHE_KEY + entity.getId(), vo);
		}
	}

	/**
	 * 新增视频站点
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(VideoStationEntity entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			int result = super.insert(entity);
			if (result > 0) {
				updateVideoStationToRedis(entity);
			}
			return result;
		} catch (Exception e) {
			logger.error("新增视频站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频站点
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(VideoStationEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			int result = super.updateById(entity);
			if (result > 0) {
				updateVideoStationToRedis(entity);
			}
			return result;
		} catch (Exception e) {
			logger.error("根据ID修改视频站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频站点
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoStationEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<VideoStationEntity> list = super.queryListByPage(parameter);
			for (VideoStationEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getStation() != null && StringUtils.isNotBlank(entity.getStation().getValue())) {
					entity.setStation(dictService.findByTypeValue(GlobalConstant.STATION_TYPE, String.valueOf(entity.getStation().getValue())));
				}
				if (entity.getVideo() != null && StringUtils.isNotBlank(entity.getVideo().getId())) {
					entity.setVideo(videoService.findById(entity.getVideo().getId()));
				}
				if (entity.getUp() != null && StringUtils.isNotBlank(entity.getUp().getId())) {
					entity.setUp(upService.findById(entity.getUp().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID、站点值、ID获取视频站点
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoStationEntity findByVideoStationId(String videoId, String stationValue, String id, String vid) {
		try {
			VideoStationEntity entity = new VideoStationEntity(id);
			entity.setVideo(new VideoEntity(videoId));
			entity.setStation(new DictEntity(stationValue));
			entity.setVid(vid);
			List<VideoStationEntity> list = videoStationMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频ID、站点值、ID获取视频站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID删除视频站点
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByVideoId(String videoId) {
		try {
			VideoStationEntity entity = new VideoStationEntity();
			entity.setVideo(new VideoEntity(videoId));
			return videoStationMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频ID删除视频站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 重置符合要求的视频站点视频作者字段
	 * 
	 * @author zengxn
	 */
	@Override
	public int resetUpIdByUpId(String upId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("upId", upId);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return videoStationMapper.resetUpIdByUpId(map);
		} catch (Exception e) {
			logger.error("重置符合要求的视频站点视频作者字段出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者ids批量修改视频站点的所属作者
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateUpIdBatchByUpId(List<String> upIds, String upId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("upIds", upIds);
			map.put("upId", upId);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return videoStationMapper.updateUpIdBatchByUpId(map);
		} catch (Exception e) {
			logger.error("根据视频作者ids批量修改视频站点的所属作者出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频站点的视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateVideoIdBatchById(List<String> ids, String videoId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("videoId", videoId);
			map.put("ids", ids);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return videoStationMapper.updateVideoIdBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频站点的视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据videoIds查询视频站点
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoStationEntity> queryListByVideoIds(List<String> videoIds, String stationValue) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("videoIds", videoIds);
			map.put("stationValue", stationValue);
			List<VideoStationEntity> list = videoStationMapper.queryListByVideoIds(map);
			for (VideoStationEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getStation() != null && StringUtils.isNotBlank(entity.getStation().getValue())) {
					entity.setStation(dictService.findByTypeValue(GlobalConstant.STATION_TYPE, String.valueOf(entity.getStation().getValue())));
				}
				if (entity.getVideo() != null && StringUtils.isNotBlank(entity.getVideo().getId())) {
					entity.setVideo(videoService.findById(entity.getVideo().getId()));
				}
				if (entity.getUp() != null && StringUtils.isNotBlank(entity.getUp().getId())) {
					entity.setUp(upService.findById(entity.getUp().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("根据videoIds查询视频站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频站点状态
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateStatusBatchById(List<String> ids, String statusValue, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("statusValue", statusValue);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return videoStationMapper.updateStatusBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID获取视频站点列表
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoStationEntity> queryListByVideoId(String videoId) {
		try {
			List<VideoStationEntity> list = null;
			if (redisManager.exists(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + videoId)) {
				List<VideoStationCacheVo> voList = redisManager.getListVByMap(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + videoId, CacheConstant.VideoConstant.VIDEO_STATION_REDIS_CACHE_KEY + "*", VideoStationCacheVo.class);
				if (!CollectionUtils.isEmpty(voList)) {
					list = new ArrayList<VideoStationEntity>();
					for (VideoStationCacheVo videoStationCacheVo : voList) {
						list.add(redisVoToEntity(videoStationCacheVo, null));
					}
				}
			}
			if (CollectionUtils.isEmpty(list)) {
				VideoStationEntity entity = new VideoStationEntity();
				entity.setVideo(new VideoEntity(videoId));
				PageHelper.startPage(0, 0, "viewCount desc");
				list = videoStationMapper.queryListByEntity(entity);
				for (VideoStationEntity videoStationEntity : list) {
					if (videoStationEntity.getStation() != null && StringUtils.isNotBlank(videoStationEntity.getStation().getValue())) {
						videoStationEntity.setStation(dictService.findByTypeValue(GlobalConstant.STATION_TYPE, String.valueOf(videoStationEntity.getStation().getValue())));
					}
					updateVideoStationToRedis(videoStationEntity);
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("根据视频ID获取视频站点列表出错：", e);
			throw new ServiceException(e);
		}
	}

	public List<VideoStationVo> queryListByIdForRelated(Map<String, Object> map) {
		String videoId = MapUtils.getString(map, "videoId");
		if (StringUtils.isBlank(videoId)) {
			throw new ServiceException("站点视频主键ID不能为空");
		}
		
		List<VideoValueEntity> valueList = videoValueService.queryListByVideoId(videoId);
		if(valueList != null && !valueList.isEmpty()) {
			List<String> valueIdList = new ArrayList<String>();
			for (VideoValueEntity videoValueEntity : valueList) {
				valueIdList.add(videoValueEntity.getValue().getId());
			}
			map.put("valueIdList", valueIdList);
		}
		
		return videoStationMapper.queryListByIdForRelated(map);
	}

	@Override
	public VideoStationEntity findByVideoStationTitle(String title) {
		try {
			VideoStationEntity entity = new VideoStationEntity();
			entity.setTitle(title);
			List<VideoStationEntity> list = videoStationMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频标题获取站点出错：", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public long queryCountByStationValue(String station, String category,String status) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("station", station);
			map.put("status", status);
			map.put("category", category);
			return videoStationMapper.queryCountByStationValue(map);
		} catch (Exception e) {
			logger.error("根据视频ID获取视频站点列表出错：", e);
			throw new ServiceException(e);
		}
	}
	
	public long queryCountByReInvalid(String station,String category){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("station", station);
			map.put("category", category);
			long countStatus = videoStationMapper.queryCountByStationValue(map);
			return countStatus;
		} catch (Exception e) {
			logger.error("根据视频ID获取视频站点列表出错：", e);
			throw new ServiceException(e);
		}
		
	}

	public Long queryTotalViewCountByVideoId(String videoId) {

		return videoStationMapper.queryTotalViewCountByVideoId(videoId);
	}
	
	public Long queryTotalViewCountByUpId(String upId) {
		
		return videoStationMapper.queryTotalViewCountByUpId(upId);
	}
	
	

}
