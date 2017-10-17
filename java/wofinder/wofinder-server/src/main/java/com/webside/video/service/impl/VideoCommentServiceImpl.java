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

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.message.service.UserMessageService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.mapper.VideoCommentMapper;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoCommentLikeEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.service.VideoCommentLikeService;
import com.webside.video.service.VideoCommentService;
import com.webside.video.service.VideoService;

/**
 * 视频评论服务实现类
 * 
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */
@Service("videoCommentService")
public class VideoCommentServiceImpl extends AbstractService<VideoCommentEntity, String> implements VideoCommentService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(videoCommentMapper);
	}

	/**
	 * 视频评论 DAO定义
	 */
	@Autowired
	private VideoCommentMapper videoCommentMapper;

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
	 * 用户消息 Service定义
	 */
	@Autowired
	private UserMessageService userMessageService;

	/**
	 * 视频 Service定义
	 */
	@Autowired
	private VideoService videoService;
	@Autowired
	private VideoCommentLikeService videoCommentLikeService;
	

	/**
	 * 新增视频评论
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(VideoCommentEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getId())) {
				entity.setId(IdGen.uuid());
			}
			if (entity.getVideo() == null || StringUtils.isBlank(entity.getVideo().getId())) {
				throw new RuntimeException("评论的视频ID不能为空");
			}
			if (StringUtils.isBlank(entity.getCommentContent())) {
				throw new RuntimeException("评论内容不能为空");
			}
			if (entity.getLikeNum() == null) {
				entity.setLikeNum(0L);
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			int result = super.insert(entity);
			if (result > 0) {
				// 给被回复的人发一条消息
				if (entity.getStation() != null && GlobalConstant.STATION_TYPE_0.equals(entity.getStation().getValue())) {// 本站点
					if (entity.getCommentParent() != null && StringUtils.isNotBlank(entity.getCommentParent().getCommentId())) {
						VideoCommentEntity parentComment = findById(entity.getCommentParent().getCommentId());
						// 回复他人（本站）评论
						if (parentComment != null && StringUtils.isNoneBlank(parentComment.getCommentUserId()) && parentComment.getStation() != null && GlobalConstant.STATION_TYPE_0.equals(parentComment.getStation().getValue())) {
							String userId = parentComment.getCommentUserId();
							String nickName = entity.getCommentUserName();// 发送人
							String businessId = entity.getId();
							String content = entity.getCommentContent();
							String videoId = entity.getVideo().getId();
							VideoEntity videoEntity = videoService.findById(videoId);
							String videoTitle = videoEntity.getTitle();
							String shortId = videoEntity.getShortId();
							userMessageService.addMessageForComment(userId, nickName, businessId, content, shortId, videoTitle);
						}
					}
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("新增视频评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频评论
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(VideoCommentEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频评论
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoCommentEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<VideoCommentEntity> list = super.queryListByPage(parameter);
			for (VideoCommentEntity entity : list) {
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
				if (entity.getCommentParent() != null && StringUtils.isNotBlank(entity.getCommentParent().getCommentId())) {
					entity.setCommentParent(findByParentId(entity.getVideo().getId(),entity.getCommentParent().getCommentId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据父评论ID获取视频评论
	 * 
	 * @author zengxn
	 */
	@Override
	public VideoCommentEntity findByParentId(String videoId,String parentId) {
		try {
			VideoCommentEntity entity = new VideoCommentEntity();
			VideoEntity videoEntity = new VideoEntity();
			VideoCommentEntity parent = new VideoCommentEntity();
			parent.setCommentId(parentId);
			entity.setCommentParent(parent);
			videoEntity.setId(videoId);
			entity.setVideo(videoEntity);
			List<VideoCommentEntity> list = videoCommentMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据父评论ID获取视频评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID删除视频评论
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByVideoId(String videoId) {
		try {
			VideoCommentEntity entity = new VideoCommentEntity();
			entity.setVideo(new VideoEntity(videoId));
			return videoCommentMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频ID删除视频评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频评论的视频
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateVideoIdByIds(List<String> ids, String videoId, String updateUserId, String updateTime) {
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
			return videoCommentMapper.updateVideoIdByIds(map);
		} catch (Exception e) {
			logger.error("批量修改视频评论的视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频ID获取视频评论
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoCommentEntity> queryListByVideoId(String videoId) {
		try {
			VideoCommentEntity entity = new VideoCommentEntity();
			entity.setVideo(new VideoEntity(videoId));
			return videoCommentMapper.queryListByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频ID获取视频评论出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频id查询视频评论数量
	 * 
	 * @author zengxn
	 */
	@Override
	public long countByVideoId(String videoId) {
		try {
			VideoCommentEntity entity = new VideoCommentEntity();
			entity.setVideo(new VideoEntity(videoId));
			return videoCommentMapper.countByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频id查询视频评论数量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询评论以及是否点赞
	 * 
	 * @author zengxn
	 */
	public List<VideoCommentEntity> queryListForLike(Map<String, Object> map) {
		List<VideoCommentEntity> list = videoCommentMapper.queryListByPage(map);
		String userId = MapUtils.getString(map, "userId");
		if (StringUtils.isBlank(userId)) {
			userId = ShiroAuthenticationManager.getUserId();
		}
		for (VideoCommentEntity entity : list) {
			if (StringUtils.isNotBlank(userId)) {
				VideoCommentLikeEntity commentLikeEntity = videoCommentLikeService.findByCommentAndUser(entity.getId(), userId);
				if (commentLikeEntity != null && StringUtils.isNotBlank(commentLikeEntity.getId())) {
					entity.setLikeStatus(commentLikeEntity.getStatus());
				}
			}
			if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
				entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
			}
			if (entity.getCommentParent() != null && StringUtils.isNotBlank(entity.getCommentParent().getCommentId())) {
				entity.setCommentParent(findByParentId(entity.getVideo().getId(),entity.getCommentParent().getCommentId()));
			}
			if (entity.getStation() != null && StringUtils.isNotBlank(entity.getStation().getValue())) {
				entity.setStation(dictService.findByTypeValue(GlobalConstant.STATION_TYPE, String.valueOf(entity.getStation().getValue())));
			}
		}
		return list;
	}

	@Override
	public int updateVideoIdByVid(String videoId, String oldVideoId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("videoId", videoId);
			map.put("oldVideoId", oldVideoId);
			map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			return videoCommentMapper.updateVideoIdByVid(map);
		} catch (Exception e) {
			logger.error("批量修改视频评论的视频出错：", e);
			throw new ServiceException(e);
		}
	}

	public VideoCommentEntity findByStationCid(String videoId,String station, String cid) { // 疑问？差父级却查的是cid
		try {
			VideoCommentEntity entity = new VideoCommentEntity();
			VideoEntity videoEntity = new VideoEntity();
			videoEntity.setId(videoId);
			entity.setCommentId(cid);
			entity.setStation(new DictEntity(station));
			entity.setVideo(videoEntity);
			List<VideoCommentEntity> list = videoCommentMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据评论ID获取视频评论出错：", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public long queryCountByStationVid(String station, String vid, String pid, String isempty,String status) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("station", station);
			map.put("vid", vid);
			map.put("status", status);
			map.put("pid", pid);
			map.put("isempty", isempty);
			return videoCommentMapper.queryCountByStationVid(map);
		} catch (Exception e) {
			logger.error("根据视频ID获取视频站点列表出错：", e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public long queryCountByReInvalid(String station, String vid, String pid, String isempty) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("station", station);
			map.put("vid", vid);
			map.put("status", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
			map.put("pid", pid);
			map.put("isempty", isempty);
			long countStatus1 = videoCommentMapper.queryCountByStationVid(map);
			map.put("status", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_9);
			long countStatus9 = videoCommentMapper.queryCountByStationVid(map);
			return countStatus1+countStatus9;
		} catch (Exception e) {
			logger.error("根据视频ID获取视频站点列表出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据用户id查询今天用户发表评论的数量
	 * 
	 * @param paramMap
	 * @return
	 * @author tianguifang
	 */
	@Override
	public Integer queryUserTodayCommentNumByUserId(Map<String, Object> paramMap) {
		return videoCommentMapper.queryUserTodayCommentNumByUserId(paramMap);
	}
}
