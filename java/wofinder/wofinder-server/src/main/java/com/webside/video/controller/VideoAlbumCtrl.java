/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.controller;

import java.util.ArrayList;
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
import com.webside.dict.service.DictService;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.service.VideoAlbumService;
import com.webside.video.service.VideoService;

/**
 * 视频专辑控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */

@Controller
@RequestMapping("/videoAlbumCtrl/")
public class VideoAlbumCtrl extends BaseController {
	/**
	 * 视频专辑 Service定义
	 */
	@Autowired
	private VideoAlbumService videoAlbumService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 视频 Service定义
	 */
	@Autowired
	private VideoService videoService;

	/**
	 * 跳转查询视频专辑页面
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

			return Common.BACKGROUND_PATH + "/business/video/album/list";
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
				List<VideoAlbumEntity> list = videoAlbumService.queryListByPage(parameters);
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
			List<VideoAlbumEntity> list = videoAlbumService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());

			// 权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("videoAlbum:editUI");
			boolean del = subject.isPermitted("videoAlbum:del");
			boolean batchVideo = subject.isPermitted("videoAlbum:batchVideoUI");
			boolean videoAlbumValue = subject.isPermitted("video:albumValue");
			// 列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list, edit ? "edit" : "", del ? "del" : "", batchVideo ? "batchVideo" : "", videoAlbumValue ? "videoAlbumValue" : ""));
			return parameters;
		}
	}

	/**
	 * 跳转新增视频专辑页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));

			return Common.BACKGROUND_PATH + "/business/video/album/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增视频专辑
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(VideoAlbumEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			int result = videoAlbumService.insert(entity);

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
	 * 跳转编辑视频专辑页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		VideoAlbumEntity entity = null;
		PageUtil page = null;

		try {
			entity = videoAlbumService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", entity);

			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			return Common.BACKGROUND_PATH + "/business/video/album/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改视频专辑
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(VideoAlbumEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			entity.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
			int result = videoAlbumService.updateById(entity);

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
	 * 根据ID删除视频专辑
	 * 
	 * @author zengxn
	 */
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String updateUserId = ShiroAuthenticationManager.getUserId();
			int result = videoAlbumService.deleteRelevantById(updateUserId, id);

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
	 * 跳转查询视频专辑页面，选择专辑使用
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
			if (request.getParameterMap().containsKey("hidId")) {
				model.addAttribute("hidId", request.getParameter("hidId"));
			}
			if (request.getParameterMap().containsKey("showId")) {
				model.addAttribute("showId", request.getParameter("showId"));
			}
			return Common.BACKGROUND_PATH + "/business/video/album/searchList";
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
		List<VideoAlbumEntity> list = videoAlbumService.queryListByPage(parameters);
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
	 * 跳转批量操作专辑下的视频页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("batchVideoUI.html")
	public String addBatchVideoUI(Model model, HttpServletRequest request, String id) {
		try {
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);

			PageHelper.startPage(1, 0, "albumIndex");
			List<VideoEntity> videoList = videoService.queryListByAlbumId(id, null);
			model.addAttribute("albumId", id);
			model.addAttribute("videoList", videoList);
			return Common.BACKGROUND_PATH + "/business/video/album/batchVideo";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 批量操作专辑下的视频
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "batchVideo.html", method = RequestMethod.POST)
	@ResponseBody
	public Object batchVideo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			if (request.getParameterMap().containsKey("datas[]") && request.getParameterMap().containsKey("albumId")) {
				String[] datasArray = request.getParameterValues("datas[]");
				String albumId = request.getParameter("albumId");
				if (datasArray.length < 1 || StringUtils.isBlank(albumId)) {
					map.put("success", Boolean.FALSE);
					map.put("message", "批量操作专辑下的视频失败，参数有误");
				} else {
					// 修改的id集合
					List<String> updateIdList = new ArrayList<String>();
					for (String string : datasArray) {
						JSONObject jsonObject = JSONObject.parseObject(string);
						updateIdList.add(jsonObject.getString("id"));
					}

					UserEntity updateUser = ShiroAuthenticationManager.getUserEntity();
					List<VideoEntity> videoList = new ArrayList<VideoEntity>();
					VideoEntity videoEntity = null;

					/*if (request.getParameterMap().containsKey("delDatas[]")) {
						String[] delArray = request.getParameterValues("delDatas[]");
						if (delArray.length > 0) {
							// 删除的id集合
							List<String> delIdList = new ArrayList<String>();
							for (String string : delArray) {
								delIdList.add(string);
							}
					
							// 删除的id集合对修改的id集合去重
							delIdList.removeAll(updateIdList);
					
							// 清空专辑的部分
							for (String videoId : delIdList) {
								videoEntity = new VideoEntity(videoId);
								videoEntity.setAlbum(new VideoAlbumEntity(null));
								videoEntity.setAlbumIndex(null);
								videoEntity.setUpdateUser(updateUser);
								videoEntity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
								videoList.add(videoEntity);
							}
						}
					}*/

					// 查找出专辑下所有视频
					List<VideoEntity> list = videoService.queryListByAlbumId(albumId, null);
					List<String> allVideoIdList = new ArrayList<String>();
					for (VideoEntity video : list) {
						allVideoIdList.add(video.getId());
					}
					// 所有的id集合对修改的id集合去重
					allVideoIdList.removeAll(updateIdList);

					// 清空专辑的部分
					for (String videoId : allVideoIdList) {
						videoEntity = new VideoEntity(videoId);
						videoEntity.setAlbum(new VideoAlbumEntity(null));
						videoEntity.setAlbumIndex(null);
						videoEntity.setUpdateUser(updateUser);
						videoEntity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
						videoList.add(videoEntity);
					}

					// 修改专辑的部分
					VideoAlbumEntity album = new VideoAlbumEntity(albumId);
					for (String jsonStr : datasArray) {
						JSONObject jsonObject = JSONObject.parseObject(jsonStr);
						videoEntity = new VideoEntity(jsonObject.getString("id"));
						videoEntity.setAlbum(album);
						videoEntity.setAlbumIndex(jsonObject.getInteger("index"));
						videoEntity.setUpdateUser(updateUser);
						videoEntity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
						videoList.add(videoEntity);
					}

					int result = videoService.doBatchAlbum(videoList);
					if (result == videoList.size()) {
						map.put("success", Boolean.TRUE);
						map.put("message", "批量操作专辑下的视频成功");
					} else {
						map.put("success", Boolean.FALSE);
						map.put("message", "批量操作专辑下的视频失败");
					}
				}
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "批量操作专辑下的视频失败，参数有误");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

}