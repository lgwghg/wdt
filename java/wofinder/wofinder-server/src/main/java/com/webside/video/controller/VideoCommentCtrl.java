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
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.service.VideoCommentService;

/**
 * 视频评论控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */

@Controller
@RequestMapping("/videoCommentCtrl/")
public class VideoCommentCtrl extends BaseController {
	/**
	 * 视频评论 Service定义
	 */
	@Autowired
	private VideoCommentService videoCommentService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 跳转查询视频评论页面
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
			model.addAttribute("stationList", dictService.queryListByType(GlobalConstant.STATION_TYPE));
			if (request.getParameterMap().containsKey("videoId")) {
				model.addAttribute("videoId", request.getParameter("videoId"));
			}
			return Common.BACKGROUND_PATH + "/business/video/comment/list";
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

		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<VideoCommentEntity> list = videoCommentService.queryListByPage(parameters);
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
			List<VideoCommentEntity> list = videoCommentService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());

			// 权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("videoComment:editUI");
			boolean del = subject.isPermitted("videoComment:del");
			// 列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list, edit ? "edit" : "", del ? "del" : ""));
			return parameters;
		}
	}

	/**
	 * 跳转新增视频评论页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model, HttpServletRequest request) {
		try {
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("stationList", dictService.queryListByType(GlobalConstant.STATION_TYPE));
			if (request.getParameterMap().containsKey("videoId")) {
				model.addAttribute("videoId", request.getParameter("videoId"));
			}
			return Common.BACKGROUND_PATH + "/business/video/comment/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增视频评论
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(VideoCommentEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			if (StringUtils.isNotBlank(entity.getCommentCreatetime())) {
				entity.setCommentCreatetime(StringUtils.toString(DateUtils.getStringDate(entity.getCommentCreatetime(), DateUtils._DEFAULT1).getTime()));
			} else {
				entity.setCommentCreatetime(null);
			}
			int result = videoCommentService.insert(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "添加成功");
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
	 * 跳转编辑视频评论页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		VideoCommentEntity entity = null;
		PageUtil page = null;

		try {
			entity = videoCommentService.findById(id);
			VideoCommentEntity cloneEntity = (VideoCommentEntity) BeanUtils.cloneBean(entity);
			if (StringUtils.isNotBlank(cloneEntity.getCommentCreatetime())) {
				cloneEntity.setCommentCreatetime(DateUtils.longToString(StringUtils.toLong(cloneEntity.getCommentCreatetime())));
			}
			/*if (cloneEntity.getCommentParent() != null && StringUtils.isNotBlank(cloneEntity.getCommentParent().getCommentId())) {
				cloneEntity.setCommentParent(videoCommentService.findById(cloneEntity.getCommentParent().getCommentId()));
			}*/
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", cloneEntity);
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("stationList", dictService.queryListByType(GlobalConstant.STATION_TYPE));
			return Common.BACKGROUND_PATH + "/business/video/comment/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改视频评论
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(VideoCommentEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			entity.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
			if (StringUtils.isNotBlank(entity.getCommentCreatetime())) {
				entity.setCommentCreatetime(DateUtils.getStringDate(entity.getCommentCreatetime(), DateUtils._DEFAULT1).getTime() + "");
			} else {
				entity.setCommentCreatetime(null);
			}
			int result = videoCommentService.updateById(entity);

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "编辑失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 根据ID删除视频评论
	 * 
	 * @author zengxn
	 */
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			int result = videoCommentService.deleteById(id);

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

	/**
	 * 跳转查询视频评论页面，选择评论使用
	 * 
	 * @author zengxn
	 */
	@RequestMapping("searchListUI.html")
	public String searchlistUI(Model model, HttpServletRequest request) {
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
			model.addAttribute("stationList", dictService.queryListByType(GlobalConstant.STATION_TYPE));
			if (request.getParameterMap().containsKey("videoId")) {
				model.addAttribute("videoId", request.getParameter("videoId"));
			}
			if (request.getParameterMap().containsKey("hidId")) {
				model.addAttribute("hidId", request.getParameter("hidId"));
			}
			if (request.getParameterMap().containsKey("showId")) {
				model.addAttribute("showId", request.getParameter("showId"));
			}
			return Common.BACKGROUND_PATH + "/business/video/comment/searchList";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "searchList.html", method = RequestMethod.POST)
	@ResponseBody
	public Object searchList(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();

		// 设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize());
		List<VideoCommentEntity> list = videoCommentService.queryListByPage(parameters);
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
}
