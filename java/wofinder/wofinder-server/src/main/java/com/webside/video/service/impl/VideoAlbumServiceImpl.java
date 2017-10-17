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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.data.solr.video.service.VideoAlbumSolrSerivce;
import com.webside.data.solr.video.service.VideoSuggestSolrSerivce;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.redis.RedisManager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoAlbumMapper;
import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.service.VideoAlbumService;
import com.webside.video.service.VideoService;
import com.webside.video.vo.VideoAlbumCacheVo;
import com.webside.video.vo.VideoAlbumVo;
import com.webside.video.vo.VideoVo;

/**
 * 视频专辑服务实现类
 * 
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
@Service("videoAlbumService")
public class VideoAlbumServiceImpl extends AbstractService<VideoAlbumEntity, String> implements VideoAlbumService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoAlbumMapper);
	}

	/**
	 * 视频专辑 DAO定义
	 */
	@Autowired
	private VideoAlbumMapper videoAlbumMapper;

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
	 * 视频管理 Service定义
	 */
	@Autowired
	private VideoService videoService;

	/**
	 * 视频合集搜索 service定义
	 */
	@Autowired
	private VideoAlbumSolrSerivce videoAlbumSolrSerivce;

	/**
	 * 视频suggest搜索 service定义
	 */
	@Autowired
	private VideoSuggestSolrSerivce videoSuggestSolrSerivce;

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
	private VideoAlbumCacheVo entityToRedisVo(VideoAlbumEntity entity, VideoAlbumCacheVo vo) {
		if (vo == null) {
			vo = new VideoAlbumCacheVo();
		}
		if (StringUtils.isNotBlank(entity.getId())) {
			vo.setId(entity.getId());
		}
		if (StringUtils.isNotBlank(entity.getName())) {
			vo.setN(entity.getName());
		}
		if (StringUtils.isNotBlank(entity.getCover())) {
			vo.setC(entity.getCover());
		}
		if (StringUtils.isNotBlank(entity.getAuthor())) {
			vo.setA(entity.getAuthor());
		}
		if (StringUtils.isNotBlank(entity.getIntroduction())) {
			vo.setI(entity.getIntroduction());
		}
		if (StringUtils.isNotBlank(entity.getUpdateRemarks())) {
			vo.setU(entity.getUpdateRemarks());
		}
		if (entity.getInde() != null) {
			vo.setInde(entity.getInde());
		}
		if (entity.getParentAlbum() != null && StringUtils.isNotBlank(entity.getParentAlbum().getId())) {
			vo.setPid(entity.getParentAlbum().getId());
		}
		return vo;
	}

	/**
	 * 缓存中的对象转换成entity对象
	 * 
	 * @author zengxn
	 */
	private VideoAlbumEntity redisVoToEntity(VideoAlbumCacheVo vo, VideoAlbumEntity entity) {
		if (entity == null) {
			entity = new VideoAlbumEntity();
		}
		if (StringUtils.isNotBlank(vo.getId())) {
			entity.setId(vo.getId());
		}
		if (StringUtils.isNotBlank(vo.getN())) {
			entity.setName(vo.getN());
		}
		if (StringUtils.isNotBlank(vo.getC())) {
			entity.setCover(vo.getC());
		}
		if (StringUtils.isNotBlank(vo.getA())) {
			entity.setAuthor(vo.getA());
		}
		if (StringUtils.isNotBlank(vo.getI())) {
			entity.setIntroduction(vo.getI());
		}
		if (StringUtils.isNotBlank(vo.getU())) {
			entity.setUpdateRemarks(vo.getU());
		}
		if (vo.getInde() != null) {
			entity.setInde(vo.getInde());
		}
		if (StringUtils.isNotBlank(vo.getPid())) {
			entity.setParentAlbum(new VideoAlbumEntity(vo.getPid()));
		}
		return entity;
	}

	/**
	 * 更新视频缓存信息
	 * 
	 * @author zengxn
	 */
	private void updateVideoToRedis(VideoAlbumEntity entity) {
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
		private VideoAlbumEntity entity;

		public UpdateRedisVideoRunner(VideoAlbumEntity entity) {
			this.entity = entity;
		}

		@Override
		public void run() {
			VideoAlbumCacheVo vo = redisManager.get(CacheConstant.VideoAlbumConstant.VIDEO_ALBUM_REDIS_CACHE_KEY + entity.getId(), VideoAlbumCacheVo.class);
			vo = entityToRedisVo(entity, vo);
			// 存缓存
			redisManager.set(CacheConstant.VideoAlbumConstant.VIDEO_ALBUM_REDIS_CACHE_KEY + entity.getId(), vo);
		}
	}

	/**
	 * 新增视频专辑
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(VideoAlbumEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (entity.getCreateUser() == null || StringUtils.isBlank(entity.getCreateUser().getId())) {
				entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			}
			int result = super.insert(entity);
			if (result > 0) {
				// 更新redis缓存
				updateVideoToRedis(entity);
				// 更新solr
				if (entity.getParentAlbum() == null || StringUtils.isEmpty(entity.getParentAlbum().getId())) {
					videoAlbumSolrSerivce.insertOrUpdate(entity.getId(), entity.getName());
					videoSuggestSolrSerivce.insertOrUpdate("a_" + entity.getId(), entity.getName());
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("新增视频专辑出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频专辑
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(VideoAlbumEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (entity.getParentAlbum() != null) {
				String parentId = entity.getParentAlbum().getId();
				if (entity.getId().equals(parentId)) {
					entity.setParentAlbum(null);
				}
			}
			int result = super.updateById(entity);
			if (result > 0) {
				// 更新redis缓存
				updateVideoToRedis(entity);
				// 更新solr
				if (entity.getParentAlbum() == null || StringUtils.isEmpty(entity.getParentAlbum().getId())) {
					videoAlbumSolrSerivce.insertOrUpdate(entity.getId(), entity.getName());
					videoSuggestSolrSerivce.insertOrUpdate("a_" + entity.getId(), entity.getName());
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("根据ID修改视频专辑出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频专辑
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoAlbumEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<VideoAlbumEntity> list = super.queryListByPage(parameter);
			for (VideoAlbumEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getParentAlbum() != null && StringUtils.isNotBlank(entity.getParentAlbum().getId())) {
					entity.setParentAlbum(this.findById(entity.getParentAlbum().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频专辑出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID获取视频合集
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoAlbumEntity findById(String id) {
		VideoAlbumEntity entity = null;
		if (redisManager.exists(CacheConstant.VideoAlbumConstant.VIDEO_ALBUM_REDIS_CACHE_KEY + id)) {
			VideoAlbumCacheVo vo = redisManager.get(CacheConstant.VideoAlbumConstant.VIDEO_ALBUM_REDIS_CACHE_KEY + id, VideoAlbumCacheVo.class);
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
	 * 根据ID删除视频专辑，并且清空相关的视频专辑字段
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteRelevantById(String updateUserId, String id) {
		try {
			// 重置符合要求的视频专辑字段
			String updateTime = StringUtils.toString(System.currentTimeMillis());
			videoService.resetAlbumIdByAlbumId(id, updateUserId, updateTime);
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据ID删除视频专辑，并且重置相关的视频专辑字段出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 通过第三方链接查找
	 * 
	 * @param homeUrl
	 * @return
	 */
	public VideoAlbumEntity findByHome(String homeUrl, String homeId) {
		if (StringUtils.isNotBlank(homeUrl)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("homeUrl", homeUrl);
			map.put("homeId", homeId);
			List<VideoAlbumEntity> list = super.queryListByPage(map);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		}

		return null;
	}

	/**
	 * 根据视频合集id批量查询视频合集VO数据集合
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoAlbumVo> queryListVideoAlbumVoBatchById(List<String> videoAlbumIds) {
		if (CollectionUtils.isEmpty(videoAlbumIds)) {
			return null;
		}
		List<VideoAlbumVo> VideoAlbumVoList = new ArrayList<VideoAlbumVo>();
		for (String videoAlbumId : videoAlbumIds) {
			VideoAlbumEntity videoAlbum = findById(videoAlbumId);
			if (videoAlbum != null) {
				VideoAlbumVo videoAlbumVo = getVideoAlbumVo(videoAlbum, 5);
				if (videoAlbumVo != null) {
					VideoAlbumVoList.add(videoAlbumVo);
				}
			}
		}
		return VideoAlbumVoList;

	}

	/**
	 * 根据视频合集实体对象查询相关数据并封装到vo对象中
	 * 
	 * @author zengxn
	 */
	private VideoAlbumVo getVideoAlbumVo(VideoAlbumEntity videoAlbum, int videoSize) {
		List<VideoAlbumEntity> videoAlbumList = this.queryListByParentId(videoAlbum.getId());

		// 如果传入的是子合集对象，selectVideoAlbumVo表示该子合集对象，否则后续修改为最新子合集对象
		VideoAlbumVo selectVideoAlbumVo = null;
		// 封装vo对象
		List<VideoAlbumVo> videoAlbumVoList = null;

		if (CollectionUtils.isEmpty(videoAlbumList)) {
			// 查询不出对象，说明该合集没有子合集，只有本身一个合集
			selectVideoAlbumVo = new VideoAlbumVo(videoAlbum.getId(), videoAlbum.getName(), videoAlbum.getIntroduction(), videoAlbum.getCover(), videoAlbum.getAuthor(), videoAlbum.getUpdateRemarks());
		} else {
			// 封装vo对象
			videoAlbumVoList = new ArrayList<VideoAlbumVo>(videoAlbumList.size());
			for (VideoAlbumEntity videoAlbumEntity : videoAlbumList) {
				VideoAlbumVo videoAlbumVo = new VideoAlbumVo(videoAlbumEntity.getId(), videoAlbumEntity.getName());
				videoAlbumVoList.add(videoAlbumVo);
				if (selectVideoAlbumVo == null && videoAlbumVo.getId().equals(videoAlbum.getId())) {
					selectVideoAlbumVo = videoAlbumVo;
				}
			}
			// 最新合集对象
			if (selectVideoAlbumVo == null) {
				selectVideoAlbumVo = videoAlbumVoList.get(0);
			}
		}

		// 查询最新合集前size个视频
		PageHelper.startPage(1, videoSize, "albumIndex desc");
		List<VideoEntity> videoList = videoService.queryListByAlbumId(selectVideoAlbumVo.getId(), null);
		List<VideoVo> videoVoList = new ArrayList<VideoVo>(videoList.size());
		VideoVo videoVo = null;
		for (VideoEntity video : videoList) {
			videoVo = new VideoVo(video.getId());
			videoVo.setShortId(video.getShortId());
			videoVo.setCover(video.getCover());
			videoVo.setDuration(video.getDuration());
			videoVo.setScore(video.getScore());
			videoVo.setTitle(video.getTitle());
			videoVo.setAlbumIndex(video.getAlbumIndex());
			videoVoList.add(videoVo);
		}
		selectVideoAlbumVo.setVideoList(videoVoList);

		// 完成最终vo封装
		VideoAlbumVo videoAlbumVo = new VideoAlbumVo(videoAlbum.getId(), videoAlbum.getName(), videoAlbum.getIntroduction(), videoAlbum.getCover(), videoAlbum.getAuthor(), videoAlbum.getUpdateRemarks());
		videoAlbumVo.setVideoAlbumList(videoAlbumVoList);
		if (CollectionUtils.isEmpty(videoAlbumList)) {
			videoAlbumVo.setVideoList(videoVoList);
		}
		return videoAlbumVo;
	}

	/**
	 * 根据父级id查询视频合集集合
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoAlbumEntity> queryListByParentId(String parentId) {
		return videoAlbumMapper.queryListByParentId(parentId);
	}

	/**
	 * 通过当前合辑，查找一系列关联的合辑集合
	 * 
	 * @param albumId
	 * @return
	 */
	public List<VideoAlbumEntity> queryListByIdForAll(String albumId) {
		if(StringUtils.isBlank(albumId)) {
			throw new RuntimeException("请传入合辑albumId");
		}
		VideoAlbumEntity albumEntity = super.findById(albumId);
		List<VideoAlbumEntity> list = new ArrayList<VideoAlbumEntity>();
		// 判断是否有父级合辑
		if(albumEntity != null && albumEntity.getParentAlbum() != null) {
			list = this.queryListByParentId(albumEntity.getParentAlbum().getId());
		} else {
			// 判断自己是否就是父级合辑
			list = this.queryListByParentId(albumEntity.getId());
		}
		
		return list;
	}

}
