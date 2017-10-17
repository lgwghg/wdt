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
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.data.solr.video.service.VideoSolrSerivce;
import com.webside.data.solr.video.service.VideoSuggestSolrSerivce;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.redis.RedisManager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.sys.model.GameEntity;
import com.webside.sys.service.GameService;
import com.webside.up.model.UpStationEntity;
import com.webside.up.service.UpStationService;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.ShortCharUtil;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoMapper;
import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoGradeEntity;
import com.webside.video.model.VideoStationEntity;
import com.webside.video.model.VideoValueEntity;
import com.webside.video.service.VideoAlbumService;
import com.webside.video.service.VideoCommentService;
import com.webside.video.service.VideoGradeService;
import com.webside.video.service.VideoRecommendService;
import com.webside.video.service.VideoService;
import com.webside.video.service.VideoStationService;
import com.webside.video.service.VideoValueService;
import com.webside.video.vo.VideoCacheVo;
import com.webside.video.vo.VideoStationVo;
import com.webside.video.vo.VideoVo;

/**
 * 视频服务实现类
 * 
 * @author zengxn
 * @date 2017-04-20 18:58:30
 */
@Service("videoService")
public class VideoServiceImpl extends AbstractService<VideoEntity, String> implements VideoService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoMapper);
	}

	/**
	 * 视频 DAO定义
	 */
	@Autowired
	private VideoMapper videoMapper;

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
	 * 游戏管理 Service定义
	 */
	@Autowired
	private GameService gameService;

	/**
	 * 视频专辑管理 Service定义
	 */
	@Autowired
	private VideoAlbumService videoAlbumService;

	/**
	 * 视频评论管理 Service定义
	 */
	@Autowired
	private VideoCommentService videoCommentService;

	/**
	 * 视频站点管理 Service定义
	 */
	@Autowired
	private VideoStationService videoStationService;

	/**
	 * 视频属性值管理 Service定义
	 */
	@Autowired
	private VideoValueService videoValueService;

	/**
	 * 视频评分管理 Service定义
	 */
	@Autowired
	private VideoGradeService videoGradeService;

	/**
	 * 首页推荐视频管理 Service定义
	 */
	@Autowired
	private VideoRecommendService videoRecommendService;

	/**
	 * 视频作者站点 Service定义
	 */
	@Autowired
	private UpStationService upStationService;

	/**
	 * redis 定义
	 */
	@Autowired
	private RedisManager redisManager;

	/**
	 * 视频搜索 service定义
	 */
	@Autowired
	private VideoSolrSerivce videoSolrSerivce;

	/**
	 * 视频suggest搜索 service定义
	 */
	@Autowired
	private VideoSuggestSolrSerivce videoSuggestSolrSerivce;
	
	/**
	 * 线程池定义
	 */
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	/**
	 * entity对象转换成缓存中的对象
	 * 
	 * @author zengxn
	 */
	private VideoCacheVo entityToRedisVo(VideoEntity entity, VideoCacheVo vo) {
		if (vo == null) {
			vo = new VideoCacheVo();
		}
		if (StringUtils.isNotBlank(entity.getId())) {
			vo.setId(entity.getId());
		}
		if (StringUtils.isNotBlank(entity.getShortId())) {
			vo.setSid(entity.getShortId());
		}
		if (StringUtils.isNotBlank(entity.getTitle())) {
			vo.setT(entity.getTitle());
		}
		if (StringUtils.isNotBlank(entity.getCover())) {
			vo.setC(entity.getCover());
		}
		if (entity.getDuration() != null) {
			vo.setD(entity.getDuration());
		}
		if (entity.getScore() != null) {
			vo.setS(entity.getScore());
		}
		if (entity.getGame() != null && StringUtils.isNotBlank(entity.getGame().getId())) {
			vo.setGid(entity.getGame().getId());
		}
		if (entity.getVideoCommentCount() != null) {
			vo.setCc(entity.getVideoCommentCount());
		}
		return vo;
	}

	/**
	 * 缓存中的对象转换成entity对象
	 * 
	 * @author zengxn
	 */
	private VideoEntity redisVoToEntity(VideoCacheVo vo, VideoEntity entity) {
		if (entity == null) {
			entity = new VideoEntity();
		}
		if (StringUtils.isNotBlank(vo.getId())) {
			entity.setId(vo.getId());
		}
		if (StringUtils.isNotBlank(vo.getSid())) {
			entity.setShortId(vo.getSid());
		}
		if (StringUtils.isNotBlank(vo.getT())) {
			entity.setTitle(vo.getT());
		}
		if (StringUtils.isNotBlank(vo.getC())) {
			entity.setCover(vo.getC());
		}
		if (vo.getD() != null) {
			entity.setDuration(vo.getD());
		}
		if (vo.getS() != null) {
			entity.setScore(vo.getS());
		}
		if (StringUtils.isNotBlank(vo.getGid())) {
			entity.setGame(new GameEntity(vo.getGid()));
		}
		if (vo.getCc() != null) {
			entity.setVideoCommentCount(vo.getCc());
		}
		return entity;
	}

	/**
	 * 更新视频缓存信息
	 * 
	 * @author zengxn
	 */
	private void updateVideoToRedis(VideoEntity entity) {
		if (entity != null && StringUtils.isNotBlank(entity.getId())) {
			cachedThreadPool.execute(new UpdateRedisVideoRunner(entity));
		}
	}

	/**
	 * 更新redis中的video信息
	 * 
	 * @author zengxn
	 */
	class UpdateRedisVideoRunner implements Runnable {
		private VideoEntity entity;

		public UpdateRedisVideoRunner(VideoEntity entity) {
			this.entity = entity;
		}

		@Override
		public void run() {
			VideoCacheVo vo = redisManager.getVByMap(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + entity.getId(), CacheConstant.VideoConstant.VIDEO_REDIS_CACHE_KEY, VideoCacheVo.class);
			vo = entityToRedisVo(entity, vo);
			// 存缓存
			redisManager.setVByMap(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + entity.getId(), CacheConstant.VideoConstant.VIDEO_REDIS_CACHE_KEY, vo);
		}
	}

	/**
	 * 新增视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(VideoEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getId())) {
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (entity.getAlbum() == null || StringUtils.isBlank(entity.getAlbum().getId())) {
				entity.setAlbum(null);
				entity.setAlbumIndex(null);
			}
			if (StringUtils.isBlank(entity.getShortId())) {
				entity.setShortId(getShortId(entity));
			}

			int result = super.insert(entity);
			if (result > 0) {
				// 更新redis缓存
				updateVideoToRedis(entity);
				// 更新solr
				 videoSolrSerivce.insertOrUpdate(entity.getId(),
				 entity.getTitle());
				 videoSuggestSolrSerivce.insertOrUpdate(entity.getId(),
				 entity.getTitle());
			}
			return result;
		} catch (Exception e) {
			logger.error("新增视频出错：", e);
			throw new ServiceException(e);
		}
	}

	private String getShortId(VideoEntity videoEntity) {
		String[] shortText = ShortCharUtil.ShortText(videoEntity.getId() + System.currentTimeMillis());
		for (int j = 0; j < shortText.length; j++) {
			VideoEntity entity = this.findByShortId(shortText[j]);
			if (entity == null) {
				return shortText[j];
			}
		}
		return getShortId(videoEntity);
	}

	/**
	 * 根据ID修改视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(VideoEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (entity.getAlbum() == null || StringUtils.isBlank(entity.getAlbum().getId())) {
				entity.setAlbum(null);
				entity.setAlbumIndex(null);
			}
			int result = super.updateById(entity);
			if (result > 0) {
				// 更新redis缓存
				updateVideoToRedis(entity);
				// 更新solr
				videoSolrSerivce.insertOrUpdate(entity.getId(), entity.getTitle());
				videoSuggestSolrSerivce.insertOrUpdate(entity.getId(), entity.getTitle());
			}
			return result;
		} catch (Exception e) {
			logger.error("根据ID修改视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频状态
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateStatusBatchById(List<String> ids, String statusValue, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			map.put("updateUserId", updateUserId);
			map.put("statusValue", statusValue);
			return videoMapper.updateStatusBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频状态出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public List<VideoEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<VideoEntity> list = super.queryListByPage(parameter);
			for (VideoEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getGame() != null && StringUtils.isNotBlank(entity.getGame().getId())) {
					entity.setGame(gameService.findById(entity.getGame().getId()));
				}
				if (entity.getAlbum() != null && StringUtils.isNotBlank(entity.getAlbum().getId())) {
					entity.setAlbum(videoAlbumService.findById(entity.getAlbum().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID获取视频
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoEntity findById(String id) {
		VideoEntity entity = null;
		if (redisManager.exists(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + id)) {
			VideoCacheVo vo = redisManager.getVByMap(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + id, CacheConstant.VideoConstant.VIDEO_REDIS_CACHE_KEY, VideoCacheVo.class);
			if (vo != null) {
				entity = redisVoToEntity(vo, null);
			}
		}
		if (entity == null) {
			entity = super.findById(id);
			updateVideoToRedis(entity);
		}
		return entity;
	}

	/**
	 * 根据ID删除视频，并且删除视频相关评论、站点、属性、首页推荐视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteRelevantById(String id) {
		try {
			// 删除评论
			videoCommentService.deleteByVideoId(id);
			// 删除站点
			videoStationService.deleteByVideoId(id);
			// 删除属性值
			videoValueService.deleteByVideoId(id);
			// 首页推荐视频
			videoRecommendService.deleteByVideoId(id);
			int result = super.deleteById(id);
			if (result > 0) {
				// 删除redis缓存
				redisManager.delByKey(CacheConstant.VideoConstant.VIDEO_MAP_REDIS_CACHE_KEY + id);
				redisManager.delByKey(CacheConstant.VideoCommentConstant.VIDEO_COMMENT_HASH_REDIS_CACHE_KEY + id);
				redisManager.delByKey(CacheConstant.VideoCommentConstant.VIDEO_COMMENT_SORTSET_REDIS_CACHE_KEY + id);
				// 删除solr
				videoSolrSerivce.deleteById(id);
				videoSuggestSolrSerivce.deleteById(id);
			}
			return result;
		} catch (Exception e) {
			logger.error("根据ID删除视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 重置符合要求的视频专辑字段
	 * 
	 * @author zengxn
	 */
	@Override
	public int resetAlbumIdByAlbumId(String albumId, String updateUserId, String updateTime) {
		try {
			VideoEntity entity = new VideoEntity();
			entity.setAlbum(new VideoAlbumEntity(albumId));
			entity.setUpdateUser(new UserEntity(updateUserId));
			if (StringUtils.isBlank(updateTime)) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			} else {
				entity.setUpdateTime(updateTime);
			}
			return videoMapper.resetAlbumIdByAlbumId(entity);
		} catch (Exception e) {
			logger.error("重置符合要求的视频专辑字段出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 重置符合要求的视频游戏字段
	 * 
	 * @author zengxn
	 */
	@Override
	public int resetGameIdByGameId(String gameId, String updateUserId, String updateTime) {
		try {
			VideoEntity entity = new VideoEntity();
			entity.setGame(new GameEntity(gameId));
			entity.setUpdateUser(new UserEntity(updateUserId));
			if (StringUtils.isBlank(updateTime)) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			} else {
				entity.setUpdateTime(updateTime);
			}
			return videoMapper.resetGameIdByGameId(entity);
		} catch (Exception e) {
			logger.error("重置符合要求的视频游戏字段出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据专辑获取视频集合
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoEntity> queryListByAlbumId(String albumId, Integer index) {
		try {
			VideoEntity entity = new VideoEntity();
			entity.setAlbum(new VideoAlbumEntity(albumId));
			entity.setAlbumIndex(index);
			return videoMapper.queryListByEntity(entity);
		} catch (Exception e) {
			logger.error("根据专辑获取视频集合出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量归纳视频集合到一个专辑中与从专辑移除视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int doBatchAlbum(List<VideoEntity> videoList) {
		try {
			int result = 0;
			for (VideoEntity videoEntity : videoList) {
				result += videoMapper.updateAlbumById(videoEntity);
			}
			return result;
		} catch (Exception e) {
			logger.error("批量归纳视频集合到一个专辑中出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 合并视频
	 * 
	 * @author zengxn
	 */
	@Override
	public Map<String, Object> doMerge(String updateUserId, List<String> videoIdList, List<String> checkStationIdList) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 主视频id
			String videoId = videoIdList.get(0);
			// 除主视频外的id集合
			List<String> outherVideoIdList = new ArrayList<String>();
			for (String id : videoIdList) {
				if (!id.equals(videoId)) {
					outherVideoIdList.add(id);
				}
			}
			// 存在重复记录的站点数量
			int sameCount = 0;
			// 所有站点id集合
			List<String> videoStationIdList = new ArrayList<String>();
			// 单条数据，没有重复记录的站点id集合
			List<String> videoStationNotSameIdList = new ArrayList<String>();
			// 站点列表
			List<DictEntity> stationList = dictService.queryListByType(GlobalConstant.STATION_TYPE);
			// 重复站点数组对象
			JSONArray jsonArray = new JSONArray();
			// 循环所有站点数据
			for (DictEntity station : stationList) {
				List<VideoStationEntity> videoStationList = videoStationService.queryListByVideoIds(videoIdList, station.getValue());
				// 该站点有重复站点记录
				if (videoStationList != null) {
					if (videoStationList.size() > 1) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("name", station.getLabel());
						jsonObject.put("list", videoStationList);
						jsonArray.add(jsonObject);
						sameCount++;
					} else if (videoStationList.size() == 1) {
						videoStationNotSameIdList.add(videoStationList.get(0).getId());
					}
				}
				for (VideoStationEntity videoStationEntity : videoStationList) {
					videoStationIdList.add(videoStationEntity.getId());
				}
			}

			String updateTime = StringUtils.toString(System.currentTimeMillis());

			// 1、处理站点数据
			// 存在重复的视频站点记录
			if (sameCount > 0) {
				if (checkStationIdList != null && checkStationIdList.size() > 0) {
					// 执行删除重复的站点信息，选中的站点信息归到主视频下
					if (checkStationIdList.size() == sameCount) {
						// 把没有重复记录的站点id集合追加进来
						checkStationIdList.addAll(videoStationNotSameIdList);
						// 将选中的以及没有重复的站点归到主视频下
						videoStationService.updateVideoIdBatchById(checkStationIdList, videoId, updateUserId, updateTime);
						// 去除选中的记录
						videoStationIdList.removeAll(checkStationIdList);
						// 软删其余的记录,目前只是设置状态为无效
						videoStationService.updateStatusBatchById(videoStationIdList, GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_0, updateUserId, updateTime);
					} else {
						map.put("success", Boolean.FALSE);
						map.put("message", "合并失败，还有未选择的重复记录的保留项");
						return map;
					}
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "合并失败，存在重复站点记录，请选择保留项");
					map.put("code", -1);
					map.put("sameList", jsonArray);
					return map;
				}
			} else {
				// 将站点数据归到主视频下
				videoStationService.updateVideoIdBatchById(videoStationIdList, videoId, updateUserId, updateTime);
			}

			// 2、视频评论
			// 站点+评论id做key,id做value，达到去重名效果
			Map<String, String> videoCommentMap = new HashMap<String, String>();
			// 所有的评论id集合
			List<String> videoCommentIdList = new ArrayList<String>();
			// 不重名的评论id集合
			List<String> videoCommentNotSameIdList = new ArrayList<String>();
			// 遍历合并的视频作者id集合
			for (String id : videoIdList) {
				List<VideoCommentEntity> videoCommentList = videoCommentService.queryListByVideoId(id);
				for (VideoCommentEntity videoComment : videoCommentList) {
					videoCommentMap.put(videoComment.getStation().getId() + "-" + videoComment.getCommentId(), videoComment.getId());
					videoCommentIdList.add(videoComment.getId());
				}
			}
			for (String string : videoCommentMap.values()) {
				videoCommentNotSameIdList.add(string);
			}
			// 修改不重id的视频评论
			if (videoCommentNotSameIdList != null && videoCommentNotSameIdList.size() > 0) {
				videoCommentService.updateVideoIdByIds(videoCommentNotSameIdList, videoId, updateUserId, updateTime);
			}
			// 去除不重id的记录
			videoCommentIdList.removeAll(videoCommentNotSameIdList);
			// 删除重id的名称
			if (videoCommentIdList != null && videoCommentIdList.size() > 0) {
				videoCommentService.deleteBatchById(videoCommentIdList);
			}

			// 3、属性值关联
			// 值id做key,id做value，达到去重名效果
			Map<String, String> videoValueMap = new HashMap<String, String>();
			// 所有的属性值关联id集合
			List<String> videoValueIdList = new ArrayList<String>();
			// 不重属性关联的id集合
			List<String> videoValueIdNotSameList = new ArrayList<String>();
			// 遍历合并的视频id集合
			for (String id : videoIdList) {
				List<VideoValueEntity> videoValueList = videoValueService.queryListByVideoId(id);
				for (VideoValueEntity videoValue : videoValueList) {
					videoValueMap.put(videoValue.getValue().getId(), videoValue.getId());
					videoValueIdList.add(videoValue.getId());
				}
			}
			for (String string : videoValueMap.values()) {
				videoValueIdNotSameList.add(string);
			}
			// 修改不重属性的属性值关联
			if (videoValueIdNotSameList != null && videoValueIdNotSameList.size() > 0) {
				videoValueService.updateVideoIdBatchById(videoValueIdNotSameList, videoId, updateUserId, updateTime);
			}
			// 去除不重属性的记录
			videoValueIdList.removeAll(videoValueIdNotSameList);
			// 删除重属性的视频作者属性值关联
			if (videoValueIdList != null && videoValueIdList.size() > 0) {
				videoValueService.deleteBatchById(videoValueIdList);
			}

			// 4、首页推荐视频
			videoRecommendService.resetVideoIdBatchByVideoId(outherVideoIdList, updateUserId, updateTime);

			// 5、视频基本信息修改
			// 评分最高值
			double score = 0;
			for (String id : videoIdList) {
				VideoEntity video = findById(id);
				if (video.getScore() != null && video.getScore().doubleValue() > score) {
					score = video.getScore().doubleValue();
				}
			}
			VideoEntity entity = new VideoEntity();
			entity.setId(videoId);
			entity.setScore(score);
			entity.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
			// 修改主视频
			updateById(entity);
			// 软删除其它视频，改状态
			if (outherVideoIdList != null && outherVideoIdList.size() > 0) {
				updateStatusBatchById(outherVideoIdList, GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_0, updateUserId, updateTime);
			}

			map.put("success", Boolean.TRUE);
			map.put("message", "合并成功");
			return map;
		} catch (Exception e) {
			logger.error("合并视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询人物的相关视频
	 * 
	 * @param up
	 * @return
	 * @author tianguifang
	 * @date 2017-06-08
	 */
	@Override
	public List<VideoEntity> queryUpRelateVideoPgByUp(Map<String, Object> paramMap) {
		List<VideoEntity> videoList = videoMapper.queryUpRelateVideoPgByUp(paramMap);
		return videoList;
	}

	/**
	 * 根据视频id查询视频VO数据
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoVo findVideoVOByVideoId(String videoId) {
		VideoEntity video = videoMapper.findById(videoId);
		if (video == null) {
			return null;
		}
		VideoVo videoVo = getVideoVo(video);

		return videoVo;
	}

	/**
	 * 根据视频id批量查询视频VO数据集合
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoVo> queryListVideoVOBathchByVideoId(List<String> videoIds) {

		if (CollectionUtils.isEmpty(videoIds)) {
			return null;
		}
		List<VideoVo> videoVoList = new ArrayList<VideoVo>();
		for (String videoId : videoIds) {
			VideoEntity video = findById(videoId);
			if (video != null) {
				VideoVo videoVo = getVideoVo(video);
				if (videoVo != null) {
					videoVoList.add(videoVo);
				}
			}
		}
		return videoVoList;
	}

	/**
	 * 根据视频实体对象查询相关数据并封装到vo对象中
	 * 
	 * @author zengxn
	 */
	private VideoVo getVideoVo(VideoEntity video) {
		// 查询站点列表
		List<VideoStationEntity> list = videoStationService.queryListByVideoId(video.getId());
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		// 统计站点总播放量
		long viewCount = 0;
		// 统计站点总评论量
		long commentCount = 0;
		// 整理站点数据
		List<VideoStationVo> videoStationVoList = null;
		videoStationVoList = new ArrayList<VideoStationVo>(list.size());
		VideoStationVo stationVo = null;
		for (VideoStationEntity videoStationEntity : list) {
			String upName = "";
			UpStationEntity upStation = upStationService.findByUpStationThirdPartyId(videoStationEntity.getUp(), videoStationEntity.getStation(), null, null);
			if (upStation != null) {
				upName = upStation.getName();
			}
			stationVo = new VideoStationVo(videoStationEntity.getId(), videoStationEntity.getStation().getDescription(), videoStationEntity.getFlashUrl(), videoStationEntity.getUrl(), videoStationEntity.getIntroduction(), upName,videoStationEntity.getPublished());
			stationVo.setCategory(videoStationEntity.getCategory());
			stationVo.setStation(videoStationEntity.getStation().getValue());
			videoStationVoList.add(stationVo);
			if (videoStationEntity.getViewCount() != null) {
				viewCount += videoStationEntity.getViewCount();
			}
			if (videoStationEntity.getCommentCount() != null) {
				commentCount += videoStationEntity.getCommentCount();
			}
		}
		
		VideoVo videoVo = new VideoVo(video.getId());
		videoVo.setShortId(video.getShortId());
		videoVo.setCover(video.getCover());
		videoVo.setDuration(video.getDuration());
		videoVo.setScore(video.getScore());
		videoVo.setTitle(video.getTitle());
		videoVo.setAlbumIndex(video.getAlbumIndex());
		videoVo.setViewCount(viewCount);
		if (video.getVideoCommentCount() != null && video.getVideoCommentCount() >= commentCount) {
			videoVo.setVideoCommentCount(video.getVideoCommentCount());
		} else {
			// 查询评论总数
			long cc = videoCommentService.countByVideoId(video.getId());
			videoVo.setVideoCommentCount(cc);
			video.setVideoCommentCount(cc);
			updateVideoToRedis(video);
		}
		videoVo.setVideoStationList(videoStationVoList);

		// 个人评分
		VideoGradeEntity videoGradeEntity = videoGradeService.findByVideoAndUser(video.getId(), "");
		if (videoGradeEntity != null && videoGradeEntity.getScore() != null) {
			videoVo.setMyScore(videoGradeEntity.getScore());
		}
		return videoVo;
	}

	public int updateScoreById(String id) {
		Long viewCount = videoStationService.queryTotalViewCountByVideoId(id);
		String score = "6.9";
		if (viewCount > 100000) {
			score = "8.4";
		} else if (viewCount > 50000 && viewCount <= 100000) {
			score = "7.8";
		} else if (viewCount <= 50000 && viewCount > 10000) {
			score = "7.3";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("score", score);
		return videoMapper.updateScoreById(map);
	}

	@Override
	public VideoEntity findByTitle(String title) {
		try {
			VideoEntity entity = new VideoEntity();
			entity.setTitle(title);
			List<VideoEntity> list = videoMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频标题查询出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询搜索页右侧热门视频
	 * 
	 * @param object
	 * @return
	 * @author tianguifang
	 */
	@Override
	public List<VideoEntity> queryHotVideo(int count) {
		try {
			List<VideoEntity> list = null;
			if (redisManager.exists(CacheConstant.VideoConstant.VIDEO_HOT_LIST)) {
				Set<VideoEntity> cacheSet = redisManager.getVByList(CacheConstant.VideoConstant.VIDEO_HOT_LIST, VideoEntity.class);
				if (!CollectionUtils.isEmpty(cacheSet)) {
					list = new ArrayList<VideoEntity>(cacheSet);
				}
			}
			if (CollectionUtils.isEmpty(list)) {
				list = videoMapper.queryHotVideo(count);
				for (VideoEntity videoEntity : list) {
					redisManager.setVBySet(CacheConstant.VideoConstant.VIDEO_HOT_LIST, videoEntity);
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("查询搜索页右侧热门视频出错：", e);
			throw new ServiceException(e);
		}
	}

	public VideoEntity findByShortId(String shortId) {
		if (StringUtils.isBlank(shortId)) {
			throw new RuntimeException("视频ID不能为空~ ");
		}
		VideoEntity videoEntity = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("shortId", shortId);
		List<VideoEntity> list = super.queryListByPage(parameter);
		if (list != null && !list.isEmpty()) {
			videoEntity = list.get(0);
		}

		return videoEntity;
	}
}
