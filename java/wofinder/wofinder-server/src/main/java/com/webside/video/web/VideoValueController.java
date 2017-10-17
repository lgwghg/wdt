/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.redis.RedisManager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoValueEntity;
import com.webside.video.model.VideoValueInform;
import com.webside.video.model.VideoValueLog;
import com.webside.video.service.IVideoValueInformService;
import com.webside.video.service.IVideoValueLogService;
import com.webside.video.service.VideoService;
import com.webside.video.service.VideoValueService;
import com.webside.video.service.impl.VideoValueInformService;

/**
 * 视频属性值关联控制管理层
 *
 * @author tianguifang
 * @date 2017-08-21
 */

@Controller
@RequestMapping("/videoValue")
public class VideoValueController extends BaseController {
	/**
	 * 视频属性值关联 Service定义
	 */
	@Autowired
	private VideoValueService videoValueService;

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
	private IVideoValueInformService videoValueInformService;
	
	@Autowired
	private RedisManager redisManager;
	@Autowired
	private IVideoValueLogService videoValueLogService;
	@Autowired
	private VideoService videoService;
	
	/**
	 * 视频详情页，查询标签
	 * @param videoId
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Object queryVideoValue(String videoId) {
		Map<String, Object> result = new HashMap<>();
		result.put("videoId", videoId);
		List<VideoValueEntity> videoValueList = videoValueService.queryListByVideoId(videoId);
		if(!CollectionUtils.isEmpty(videoValueList)){
			ValueEntity valueEntity = null;
			for (VideoValueEntity videoValue : videoValueList) {
				valueEntity = valueService.findById(videoValue.getValue().getId());
				if(valueEntity!=null){
					videoValue.setValue(valueEntity);
				}
			}
		}
		result.put("videoValueList", videoValueList);
		return result;
	}
	@RequestMapping(value = "/u/queryUp", method = RequestMethod.POST)
	@ResponseBody
	public Object queryUserUpAndVideoUpNum(String videoValueId) {
		Map<String, Object> result = new HashMap<>();
		String userId = ShiroAuthenticationManager.getUserId();
		// 查询最新点赞或取消点赞记录
		List<VideoValueLog> valueLogs = videoValueLogService.queryUserUpVideoLastest(videoValueId, userId);
		VideoValueEntity videoValue = videoValueService.findById(videoValueId);
		if (videoValue != null) {
			result.put("upNum", videoValue.getUpNum());
		}
		if (!CollectionUtils.isEmpty(valueLogs)) {
			VideoValueLog valueLog = valueLogs.get(0);
			if (GlobalConstant.VIDEO_TAG_OPERATE_TYPE_1.equals(valueLog.getOperateType())) {
				result.put("isUp", 1);// 已经点赞
			} else {
				result.put("isUp", 0);// 取消了点赞
			}
		} else {
			result.put("isUp", 0);// 未点赞
		}
		
		return result;
	}
	/**
	 * 视频标签修改页面
	 * @param videoId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/log/{shortId}", method = RequestMethod.GET)
	public String videoValueHistory(@PathVariable String shortId, Model model) {
		VideoEntity video = videoService.findByShortId(shortId);
		if (video != null) {
			model.addAttribute("videoId", video.getId());
			model.addAttribute("videoTitle", video.getTitle());
			return Common.BACKGROUND_PATH_WEB + "/video/valueHistory";
		} else {
			return Common.BACKGROUND_PATH + "/error/404";
		}
	}
	
	/**
	 * 视频标签修改页面数据，滚动分页
	 * @param gridPager
	 * @return
	 */
	@RequestMapping(value = "/logData", method = RequestMethod.POST)
	@ResponseBody
	public Object queryVideoValueHistory(String gridPager) {
		Map<String, Object> parameters = null;
		//1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		//2、设置查询参数
		parameters = pager.getParameters();
		Page<Object> page = PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "");
		List<VideoValueLog> valueLogList = null;
		if (parameters != null && parameters.get("videoId") != null) {
			valueLogList = videoValueLogService.queryVideoValueLog((String)parameters.get("videoId"));
		}
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		//列表展示数据
		parameters.put("valueLogList", valueLogList);
		return parameters;
	}
	
	/*
	 * 视频标签举报
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "/u/inform", method = RequestMethod.POST)
	@ResponseBody
	public Object informVideoValue(VideoValueInform inform) throws AjaxException
	{
		Map<String, Object> map = null;
		
		try {
			map = new HashMap<String, Object>();
			int result = videoValueInformService.insert(inform);
			
			if(result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "举报成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "举报失败");
			}
		} catch(Exception e) {
			throw new AjaxException(e);
		}
		
		return map;
	}
	
	/*
	 * 视频标签点赞
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "/u/up", method = RequestMethod.POST)
	@ResponseBody
	public Object upVideoValue(String videoValueId) throws AjaxException {
		Map<String, Object> map = null;
		String key = "video_up_" + ShiroAuthenticationManager.getUserId();
		Integer ok = redisManager.get(key, Integer.class);
		if (ok == null) {
			try {
				redisManager.setex(key, 1, 3);// 3秒缓存
				map = videoValueService.updateUpVideoValue(videoValueId);
			} catch(Exception e) {
				throw new AjaxException(e);
			}
		} else {
			map = new HashMap<>();
			map.put("success", false);
			map.put("message", "操作太频繁，请稍候再试。");
		}
		
		return map;
	}
	
	/*
	 * 视频标签点赞
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "/u/add", method = RequestMethod.POST)
	@ResponseBody
	public Object addVideoValue(String videoId, String tagName) throws AjaxException {
		Map<String, Object> map = null;
		String userId = ShiroAuthenticationManager.getUserId();
		String key = "video_add_" + userId;
		Integer ok = redisManager.get(key, Integer.class);
		if (ok == null) {
			try {
				redisManager.setex(key, 1, 3);// 3秒缓存
				map = videoValueService.insertVideoValue(userId, videoId, tagName);
			} catch(Exception e) {
				throw new AjaxException(e);
			}
		} else {
			map = new HashMap<>();
			map.put("success", false);
			map.put("message", "操作太频繁，请稍候再试。");
		}
		
		return map;
	}
	/*
	 * 删除视频标签关联关系
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	@RequestMapping(value = "/u/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteVideoValue(String videoValueId) throws AjaxException {
		Map<String, Object> map = new HashMap<>();
		String userId = ShiroAuthenticationManager.getUserId();
		try {
			VideoValueEntity videoValue = videoValueService.findById(videoValueId);
			if (videoValue.getCreateUser() != null && StringUtils.isNotBlank(videoValue.getCreateUser().getId()) 
					&& videoValue.getCreateUser().getId().equals(userId)) {
				int re = videoValueService.deleteVideoValue(videoValueId);
				if (re > 0) {
					map.put("success", true);
					map.put("message", "删除成功");
				} else {
					map.put("success", false);
					map.put("message", "删除失败");
				}
			} else {
				map.put("success", false);
				map.put("message", "您没权限删除该视频标签");
			}
		} catch(Exception e) {
			throw new AjaxException(e);
		}
		
		return map;
	}
}
