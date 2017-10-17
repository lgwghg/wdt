/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.util.DateUtils;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoRecommendEntity;
import com.webside.video.service.VideoRecommendService;
import com.webside.video.service.VideoService;

/**
 * 首页推荐视频控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 22:21:52
 */

@Controller
@RequestMapping("/videoRecommendCtrl/")
public class VideoRecommendCtrl extends BaseController {
	/**
	 * 首页推荐视频 Service定义
	 */
	@Autowired
	private VideoRecommendService videoRecommendService;

	/**
	 * 视频 Service定义
	 */
	@Autowired
	private VideoService videoService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 跳转查询首页推荐视频页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		PageUtil page = null;

		try {
			page = new PageUtil();

			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}

			model.addAttribute("page", page);

			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));

			return Common.BACKGROUND_PATH + "/business/video/recommend/list";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters.get("startTime") != null && StringUtils.isNotBlank(parameters.get("startTime").toString())) {
			parameters.put("startTime", DateUtils.parseDate(parameters.get("startTime")).getTime());
		}
		if (parameters.get("endTime") != null && StringUtils.isNotBlank(parameters.get("endTime").toString())) {
			parameters.put("endTime", DateUtils.parseDate(parameters.get("endTime")).getTime());
		}
		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<VideoRecommendEntity> list = videoRecommendService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize());
			List<VideoRecommendEntity> list = videoRecommendService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());

			// 权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("videoRecommend:editUI");
			boolean del = subject.isPermitted("videoRecommend:del");
			// 列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list, edit ? "edit" : "", del ? "del" : ""));
			return parameters;
		}
	}

	/**
	 * 跳转新增首页推荐视频页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));

			return Common.BACKGROUND_PATH + "/business/video/recommend/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增首页推荐视频
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(VideoRecommendEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			VideoRecommendEntity findResult = videoRecommendService.findByVideoId(entity.getVideo().getId(), entity.getId());
			if (findResult != null) {
				map.put("success", Boolean.FALSE);
				map.put("message", "添加失败，已存在同视频的首页推荐");
			} else {
				entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
				if (StringUtils.isNotBlank(entity.getStartTime())) {
					entity.setStartTime(StringUtils.toString(DateUtils.getStringDate(entity.getStartTime(), DateUtils._DEFAULT1).getTime()));
				} else {
					entity.setStartTime(null);
				}
				if (StringUtils.isNotBlank(entity.getEndTime())) {
					entity.setEndTime(StringUtils.toString(DateUtils.getStringDate(entity.getEndTime(), DateUtils._DEFAULT1).getTime()));
				} else {
					entity.setEndTime(null);
				}
				int result = videoRecommendService.insert(entity);

				if (result > 0) {
					map.put("success", Boolean.TRUE);
					map.put("message", "添加成功");
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "添加失败");
				}
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 跳转编辑首页推荐视频页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		VideoRecommendEntity entity = null;
		PageUtil page = null;

		try {
			entity = videoRecommendService.findById(id);
			VideoRecommendEntity cloneEntity = (VideoRecommendEntity) BeanUtils.cloneBean(entity);
			cloneEntity.setVideo(videoService.findById(cloneEntity.getVideo().getId()));
			if (StringUtils.isNotBlank(cloneEntity.getStartTime())) {
				cloneEntity.setStartTime(DateUtils.longToString(StringUtils.toLong(cloneEntity.getStartTime())));
			}
			if (StringUtils.isNotBlank(cloneEntity.getEndTime())) {
				cloneEntity.setEndTime(DateUtils.longToString(StringUtils.toLong(cloneEntity.getEndTime())));
			}
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", cloneEntity);
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			return Common.BACKGROUND_PATH + "/business/video/recommend/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改首页推荐视频
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(VideoRecommendEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			VideoRecommendEntity findResult = videoRecommendService.findByVideoId(entity.getVideo().getId(), entity.getId());
			if (findResult != null) {
				map.put("success", Boolean.FALSE);
				map.put("message", "编辑失败，已存在同视频的首页推荐");
			} else {
				entity.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
				if (StringUtils.isNotBlank(entity.getStartTime())) {
					entity.setStartTime(DateUtils.getStringDate(entity.getStartTime(), DateUtils._DEFAULT1).getTime() + "");
				} else {
					entity.setStartTime(null);
				}
				if (StringUtils.isNotBlank(entity.getEndTime())) {
					entity.setEndTime(DateUtils.getStringDate(entity.getEndTime(), DateUtils._DEFAULT1).getTime() + "");
				} else {
					entity.setEndTime(null);
				}
				int result = videoRecommendService.updateById(entity);

				if (result > 0) {
					map.put("success", Boolean.TRUE);
					map.put("message", "编辑成功");
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "编辑失败");
				}
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 根据ID删除首页推荐视频
	 * 
	 * @author zengxn
	 */
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			int result = videoRecommendService.deleteById(id);

			if (result > 0) {
				map.put("success", true);
				map.put("message", "删除成功");
			} else {
				map.put("success", false);
				map.put("message", "删除失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}
}
