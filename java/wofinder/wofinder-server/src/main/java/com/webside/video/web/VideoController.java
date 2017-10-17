/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.CacheConstant;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.redis.RedisManager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.up.model.UpEntity;
import com.webside.up.service.UpService;
import com.webside.user.service.UserCacheService;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;
import com.webside.video.model.VideoValueEntity;
import com.webside.video.service.VideoGradeService;
import com.webside.video.service.VideoService;
import com.webside.video.service.VideoStationService;
import com.webside.video.service.VideoValueService;
import com.webside.video.vo.VideoStationVo;
import com.webside.video.vo.VideoVo;

/**
 * 视频站点控制管理层
 * 
 * @author zengxn
 * @date 2017-04-20 21:15:55
 */

@Controller
@Scope("prototype")
@RequestMapping("/video/")
public class VideoController extends BaseController {
	public static final Logger logger = Logger.getLogger(VideoController.class);

	@Autowired
	private UserCacheService userCacheService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private VideoGradeService videoGradeService;
	@Autowired
	private VideoStationService videoStationService;
	@Autowired
	private UpService upService;
	@Autowired
	private VideoValueService videoValueService;
	@Autowired
	private ValueService valueService;
	@Autowired
	private RedisManager redisManager;

	@RequestMapping("{shortId}")
	public String videoDetails(@PathVariable String shortId, String gto, Model model) {
		VideoEntity videoEntity = videoService.findByShortId(shortId);
		if (videoEntity == null) {
			return Common.BACKGROUND_PATH + "/error/error";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("videoId", videoEntity.getId());
		map.put("shortId", videoEntity.getShortId());
		map.put("title", videoEntity.getTitle());
		map.put("cover", videoEntity.getCover());
		map.put("score", videoEntity.getScore());
		map.put("createTime", DateUtils.longToString(StringUtils.toLong(videoEntity.getCreateTime()), DateUtils._DEFAULT5));
		if (videoEntity.getAlbum() != null) {
			map.put("albumId", videoEntity.getAlbum().getId());
		}
		List<VideoStationEntity> list = videoStationService.queryListByVideoId(videoEntity.getId());
		if (list != null && !list.isEmpty()) {
			VideoStationEntity stationEntity = list.get(0);
			map.put("introduction", stationEntity.getIntroduction());
			map.put("station", stationEntity.getStation().getValue());
			map.put("flashUrl", stationEntity.getFlashUrl());
			map.put("url", stationEntity.getUrl());
			map.put("category", stationEntity.getCategory());
			map.put("videoStatinId", stationEntity.getId());
			map.put("homeId", stationEntity.getVid());
			if (stationEntity.getStation() != null) {
				map.put("stationName", stationEntity.getStation().getLabel());
			}
			if (stationEntity.getUp() != null && StringUtils.isNotBlank(stationEntity.getUp().getId())) {
				UpEntity upEntity = upService.findById(stationEntity.getUp().getId());
				map.put("upName", upEntity.getName());
			}
		}
		List<VideoValueEntity> videoValueList = videoValueService.queryListByVideoId(videoEntity.getId());
		StringBuffer tag = null;
		if (!CollectionUtils.isEmpty(videoValueList)) {
			tag = new StringBuffer();
			ValueEntity valueEntity = null;
			for (int i = 0; i < videoValueList.size(); i++) {
				valueEntity = valueService.findById(videoValueList.get(i).getValue().getId());
				if (valueEntity != null) {
					tag.append(valueEntity.getName());
					if (i < videoValueList.size() - 1) {
						tag.append(",");
					}
				}
			}
			map.put("videoTag", tag.toString());
		}
		model.addAttribute("video", map);
		model.addAttribute("gto", gto);

		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isNotBlank(userId)) {// 是否参与
			// 头像
			model.addAttribute("userPhoto_65", userCacheService.getUserEntityByUserId(userId).getPhoto_70());
		}

		String key = CacheConstant.VideoConstant.VIDEO_ID_REDIS_CACHE_SET;
		String value = MapUtils.getString(map, "station") + "/" + MapUtils.getString(map, "homeId");
		if (redisManager.exists(key)) {
			Set<String> set = redisManager.getSetByKey(key);
			if (set != null && set.contains(value)) {// 已经保存到缓存了
				model.addAttribute("videoIsRedisCache", true);
			}
		}
		model.addAttribute("videoIdsToRedis", value);

		return Common.BACKGROUND_PATH_WEB + "/video/videoDetails";
	}

	/**
	 * 查找相关视频
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRelatedVideo")
	public Object getRelatedVideo(String gridPager, HttpServletResponse response) {
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		Map<String, Object> parameters = pager.getParameters();

		// 设置分页，page里面包含了分页信息
		// String orderBy = MapUtils.getString(parameters, "orderBy");
		// Page<Object> page = PageHelper.startPage(pager.getNowPage(),
		// pager.getPageSize(), orderBy);
		List<VideoStationVo> list = videoStationService.queryListByIdForRelated(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		// parameters.put("pageCount", page.getPages());
		// parameters.put("recordCount", page.getTotal());
		// parameters.put("startRecord", page.getStartRow());
		// 列表展示数据
		parameters.put("exhibitDatas", list);

		return parameters;
	}

	/**
	 * 新增或修改评分
	 * 
	 * @param videoId
	 *            视频ID
	 * @param score
	 *            个人评分
	 * @return
	 */
	@RequestMapping(value = "addOrUpdateGrade", method = RequestMethod.POST)
	@ResponseBody
	public Object addOrUpdateGrade(String videoId, double score) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			videoGradeService.addOrUpdate(videoId, score);
			map.put("success", Boolean.TRUE);
			map.put("message", "打分成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "打分失败" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "getVideoStationList", method = RequestMethod.POST)
	@ResponseBody
	public Object getVideoStationList(String videoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			VideoVo videoVo = videoService.findVideoVOByVideoId(videoId);
			map.put("data", videoVo);
			map.put("success", Boolean.TRUE);
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "获取站点失败：" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 根据视频合集id查找视频列表
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "getAlbumVideoList", method = RequestMethod.POST)
	@ResponseBody
	public Object getAlbumVideoList(HttpServletResponse response, String albumId, Boolean all) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询最新合集前size个视频
			if (all != null && all.booleanValue()) {
				PageHelper.startPage(1, 0, "albumIndex desc");
			} else {
				PageHelper.startPage(1, 5, "albumIndex desc");
			}
			List<VideoEntity> videoList = videoService.queryListByAlbumId(albumId, null);
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
			result.put("list", videoVoList);
			result.put("success", Boolean.TRUE);
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("message", GlobalConstant.RETURN_ERROR_MESSAGE);
		}
		return result;
	}
}
