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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.common.redis.listener.SendMessage;
import com.webside.dtgrid.model.Pager;
import com.webside.exception.AjaxException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.task.model.TaskCommentEntity;
import com.webside.task.service.TaskCommentLikeService;
import com.webside.task.service.TaskCommentService;
import com.webside.task.vo.TaskCommentVo;
import com.webside.util.StringUtils;

/**
 * 事件评论控制管理层
 * 
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */

@Controller
@RequestMapping("/taskComment/")
public class TaskCommentController extends BaseController {
	/**
	 * 事件评论 Service定义
	 */
	@Autowired
	private TaskCommentService taskCommentService;

	/**
	 * 事件评论点赞 Service定义
	 */
	@Autowired
	private TaskCommentLikeService taskCommentLikeService;

	/**
	 * 消息队列 Service定义
	 */
	@Autowired
	private SendMessage sendMessage;

	/**
	 * 新增事件评论
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(TaskCommentEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			int result = taskCommentService.insert(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "添加成功");

				// 发送消息，评论
				TaskCommentVo taskCommentVo = new TaskCommentVo();
				taskCommentVo.setId(entity.getId());
				taskCommentVo.setAvatar(entity.getCreateUser().getPhoto_40());
				taskCommentVo.setUserName(entity.getCreateUser().getNickName());
				taskCommentVo.setCreatetime(entity.getCreateTime());
				taskCommentVo.setContent(entity.getContent());
				if (entity.getParentComment() != null && entity.getParentComment().getCreateUser() != null && StringUtils.isNotBlank(entity.getParentComment().getCreateUser().getNickName())) {
					taskCommentVo.setParentUserName(entity.getParentComment().getCreateUser().getNickName());
				}
				if (entity.getTask() != null && StringUtils.isNotBlank(entity.getTask().getId())) {
					taskCommentVo.setTaskId(entity.getTask().getId());
				}
				sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, taskCommentVo);
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "添加失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @author aning
	 */
	@RequestMapping(value = "searchList", method = RequestMethod.POST)
	@ResponseBody
	public Object searchList(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		parameters.put("userId", ShiroAuthenticationManager.getUserId());

		// 设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "createTime desc");
		List<TaskCommentVo> list = taskCommentService.queryListForLike(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		// 列表展示数据
		parameters.put("exhibitDatas", list);
		return parameters;
	}

	/**
	 * 点赞
	 * 
	 * @author aning
	 */
	@RequestMapping(value = "addOrUpdateLike", method = RequestMethod.POST)
	@ResponseBody
	public Object addOrUpdateLike(String commentId, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = ShiroAuthenticationManager.getUserId();
			taskCommentLikeService.addOrUpdate(commentId, userId, status);
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
