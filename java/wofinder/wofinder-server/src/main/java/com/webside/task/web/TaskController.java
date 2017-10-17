/*******************************************************************************
 * All rights reserved. 
 * 
 * @author aning
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.dtgrid.model.Pager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.task.model.TaskEntity;
import com.webside.task.model.TaskLikeEntity;
import com.webside.task.service.TaskLikeService;
import com.webside.task.service.TaskService;
import com.webside.task.vo.TaskVo;
import com.webside.user.service.UserCacheService;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;

/**
 * 人物事件控制管理层
 * 
 * @author aning
 * @date 2017-04-20 21:15:55
 */

@Controller
@Scope("prototype")
@RequestMapping("/task/")
public class TaskController extends BaseController {

	/**
	 * 人物事件 Service定义
	 */
	@Autowired
	private TaskService taskService;

	/**
	 * 人物事件点赞 Service定义
	 */
	@Autowired
	private TaskLikeService taskLikeService;
	
	/**
	 * 用户缓存 Service定义
	 */
	@Autowired
	private UserCacheService userCacheService;

	/**
	 * 进入人物事件页面
	 */
	@RequestMapping("{taskId}")
	public String taskDetails(@PathVariable String taskId, Model model) {
		TaskEntity taskEntity = taskService.findById(taskId);
		if (taskEntity == null) {
			return Common.BACKGROUND_PATH + "/error/error";
		}
		// 访问量+1
		taskService.updateViewCountById(taskId);
		// 转换创建时间
		taskEntity.setCreateTime(DateUtils.longToString(StringUtils.toLong(taskEntity.getCreateTime())));
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isNotBlank(userId)) {
			TaskLikeEntity taskLikeEntity = taskLikeService.findByTaskAndUser(taskId, userId);
			if (taskLikeEntity != null && StringUtils.isNotBlank(taskLikeEntity.getId())) {
				taskEntity.setLikeStatus(StringUtils.toInteger(taskLikeEntity.getStatus().getValue()));
			}
			// 头像
			model.addAttribute("userPhoto_65", userCacheService.getUserEntityByUserId(userId).getPhoto_70());
		}
		model.addAttribute("task", taskEntity);
		return Common.BACKGROUND_PATH_WEB + "/task/taskDetail";
	}

	/**
	 * 查找相关事件
	 */
	@ResponseBody
	@RequestMapping(value = "getRelatedTask", method = RequestMethod.POST)
	public Object getRelatedTask(String gridPager, HttpServletResponse response) {
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		Map<String, Object> parameters = pager.getParameters();
		if (!parameters.containsKey("upId") || !parameters.containsKey("id")) {
			parameters.clear();
			parameters.put("isSuccess", Boolean.FALSE);
		} else {
			String userId = ShiroAuthenticationManager.getUserId();
			parameters.put("userId", userId);
			// 设置分页，page里面包含了分页信息
			List<TaskVo> list = taskService.queryListByIdForRelated(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			// 列表展示数据
			parameters.put("exhibitDatas", list);
		}
		return parameters;

	}

	/**
	 * 点赞
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "addOrUpdateLike", method = RequestMethod.POST)
	@ResponseBody
	public Object addOrUpdateLike(String taskId, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = ShiroAuthenticationManager.getUserId();
			taskLikeService.addOrUpdate(taskId, userId, status);
			map.put("success", Boolean.TRUE);
			map.put("message", "操作成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "操作失败" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}
}