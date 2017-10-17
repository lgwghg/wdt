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
import com.webside.dict.model.DictEntity;
import com.webside.dtgrid.model.Pager;
import com.webside.exception.AjaxException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserCacheService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.service.VideoCommentLikeService;
import com.webside.video.service.VideoCommentService;
import com.webside.video.vo.VideoCommentVo;

/**
 * 视频评论控制管理层
 * 
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */

@Controller
@RequestMapping("/videoComment/")
public class VideoCommentController extends BaseController {
	/**
	 * 视频评论 Service定义
	 */
	@Autowired
	private VideoCommentService videoCommentService;
	@Autowired
	private VideoCommentLikeService videoCommentLikeService;

	/**
	 * 消息队列 Service定义
	 */
	@Autowired
	private SendMessage sendMessage;
	@Autowired
	private UserCacheService userCacheService;

	/**
	 * 新增视频评论
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(VideoCommentEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setId(IdGen.uuid());
			entity.setCommentId(entity.getId());
			entity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_0));// 默认本站点
			entity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));// 默认有效
			String loginUserId = ShiroAuthenticationManager.getUserId();
			if (StringUtils.isNotBlank(entity.getCommentUserId())) {
				loginUserId = entity.getCommentUserId();
			}
			UserEntity userEntity = userCacheService.getUserEntityByUserId(loginUserId);
			if (StringUtils.isBlank(entity.getCommentUserId())) {// 评论者
				entity.setCommentUserId(userEntity.getId());
			}
			if (StringUtils.isBlank(entity.getCommentUserName())) {// 评论者
				entity.setCommentUserName(userEntity.getNickName());
			}
			entity.setCreateUser(userEntity);
			entity.setCommentCreatetime(StringUtils.toString(System.currentTimeMillis()));
			int result = videoCommentService.insert(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "添加成功");

				// 发送消息，评论
				VideoCommentVo videoCommentVo = new VideoCommentVo();
				videoCommentVo.setId(entity.getId());
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getPhoto_70())) {
					videoCommentVo.setAvatar(entity.getCreateUser().getPhoto_40());
				}
				videoCommentVo.setCommentUserName(entity.getCommentUserName());
				videoCommentVo.setCommentCreatetime(entity.getCreateTime());
				videoCommentVo.setCommentId(entity.getCommentId());
				videoCommentVo.setCommentContent(entity.getCommentContent());
				if (entity.getCommentParent() != null && StringUtils.isNotBlank(entity.getCommentParent().getCommentUserName())) {
					videoCommentVo.setParentUserName(entity.getCommentParent().getCommentUserName());
				}
				if (entity.getStation() != null) {
					videoCommentVo.setStation(entity.getStation().getValue());
					videoCommentVo.setStationClass(entity.getStation().getDescription());
				}
				if (entity.getVideo() != null && StringUtils.isNotBlank(entity.getVideo().getId())) {
					videoCommentVo.setVideoId(entity.getVideo().getId());
				}
				sendMessage.sendMessage(GlobalConstant.SEND_MESSAGE_PARAM, videoCommentVo);
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
	 * @author zengxn
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
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "c_createTime desc");
		List<VideoCommentEntity> list = videoCommentService.queryListForLike(parameters);
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
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "addOrUpdateLike", method = RequestMethod.POST)
	@ResponseBody
	public Object addOrUpdateLike(String commentId, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = ShiroAuthenticationManager.getUserId();
			videoCommentLikeService.addOrUpdate(commentId, userId, status);
			map.put("success", Boolean.TRUE);
			map.put("message", "点赞成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "点赞失败" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}
}
