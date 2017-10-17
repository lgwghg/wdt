/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.controller;

import java.util.ArrayList;
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
import com.webside.sys.service.GameService;
import com.webside.up.model.UpEntity;
import com.webside.up.service.UpService;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 视频作者控制管理层
 *
 * @author zengxn
 * @date 2017-04-15 18:28:19
 */

@Controller
@RequestMapping("/upCtrl/")
public class UpCtrl extends BaseController {
	/**
	 * 视频作者 Service定义
	 */
	@Autowired
	private UpService upService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 游戏 Service定义
	 */
	@Autowired
	private GameService gameService;

	/**
	 * 跳转查询视频作者页面
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
			model.addAttribute("isSearchList", dictService.queryListByType(GlobalConstant.DICTTYPE_YES_NO));
			return Common.BACKGROUND_PATH + "/business/up/up/list";
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
				List<UpEntity> list = upService.queryListByPage(parameters);
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
			List<UpEntity> list = upService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());

			// 权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("up:editUI");
			boolean del = subject.isPermitted("up:del");
			boolean upName = subject.isPermitted("up:upName");
			boolean upStation = subject.isPermitted("up:upStation");
			boolean upValue = subject.isPermitted("up:upValue");
			boolean upSecondLevel = subject.isPermitted("up:upSecondLevel");
			boolean upRelation = subject.isPermitted("up:upRelation");
			boolean upPhoto = subject.isPermitted("up:upPhoto");
			// 列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list, edit ? "edit" : "", del ? "del" : "", upName ? "upName" : "", upStation ? "upStation" : "", upValue ? "upValue" : "", upSecondLevel ? "upSecondLevel" : "", upRelation ? "upRelation" : "", upPhoto ? "upPhoto" : ""));
			return parameters;
		}
	}

	/**
	 * 跳转新增视频作者页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("isSearchList", dictService.queryListByType(GlobalConstant.DICTTYPE_YES_NO));
			return Common.BACKGROUND_PATH + "/business/up/up/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增视频作者
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(UpEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
			int result = upService.insert(entity);

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
	 * 跳转编辑视频作者页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		UpEntity entity = null;
		PageUtil page = null;

		try {
			entity = upService.findById(id);
			UpEntity cloneEntity = (UpEntity) BeanUtils.cloneBean(entity);
			if (cloneEntity.getGame() != null && StringUtils.isNotBlank(cloneEntity.getGame().getId())) {
				cloneEntity.setGame(gameService.findById(cloneEntity.getGame().getId()));
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
			model.addAttribute("isSearchList", dictService.queryListByType(GlobalConstant.DICTTYPE_YES_NO));
			return Common.BACKGROUND_PATH + "/business/up/up/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改视频作者
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(UpEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			entity.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
			int result = upService.updateById(entity);

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
	 * 根据ID删除视频作者
	 * 
	 * @author zengxn
	 */
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id) {
		Map<String, Object> result = null;

		try {
			result = new HashMap<String, Object>();
			String updateUserId = ShiroAuthenticationManager.getUserId();
			int cnt = upService.deleteRelevantById(id, updateUserId);

			if (cnt > 0) {
				result.put("success", true);
				result.put("message", "删除成功");
			} else {
				result.put("success", false);
				result.put("message", "删除失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return result;
	}

	/**
	 * 跳转查询视频作者页面，选择作者使用
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
			model.addAttribute("gameList", gameService.queryListByPage(null));
			if (request.getParameterMap().containsKey("hidId")) {
				model.addAttribute("hidId", request.getParameter("hidId"));
			}
			if (request.getParameterMap().containsKey("showId")) {
				model.addAttribute("showId", request.getParameter("showId"));
			}
			return Common.BACKGROUND_PATH + "/business/up/up/searchList";
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
		List<UpEntity> list = upService.queryListByPage(parameters);
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
	 * 跳转合并视频作者页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("mergeUI.html")
	public String mergeUI(Model model) {
		try {
			return Common.BACKGROUND_PATH + "/business/up/up/merge";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 合并视频作者
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "merge.html", method = RequestMethod.POST)
	@ResponseBody
	public Object merge(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			if (request.getParameterMap().containsKey("upIds[]")) {
				String[] upIdArray = request.getParameterValues("upIds[]");
				if (upIdArray.length < 2) {
					map.put("success", Boolean.FALSE);
					map.put("message", "合并失败，参数有误");
				} else {
					List<String> checkStationIdList = new ArrayList<String>();
					if (request.getParameterMap().containsKey("checkStationIds[]")) {
						// 执行删除重复的站点信息，选中的站点信息归到主视频作者下
						String[] checkStationIds = request.getParameterValues("checkStationIds[]");
						for (String string : checkStationIds) {
							checkStationIdList.add(string);
						}
					}
					String userId = ShiroAuthenticationManager.getUserId();
					List<String> upIdList = new ArrayList<String>();
					for (String string : upIdArray) {
						upIdList.add(string);
					}
					map.clear();
					map = upService.doMerge(userId, upIdList, checkStationIdList);
				}
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "合并失败，参数有误");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}
	
	@RequestMapping(value = "selectRelationUp")
	@ResponseBody
	public Object selectRelationUp(String name, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = new HashMap<>();
		// 1、映射Pager对象
		Pager pager = new Pager();
		// 2、设置查询参数
		parameters.put("name", name);
		pager.setParameters(parameters);
		pager.setNowPage(1);
		pager.setPageSize(10);
		// 设置分页，page里面包含了分页信息
		PageHelper.startPage(pager.getNowPage(), pager.getPageSize());
		List<UpEntity> list = upService.queryListByPage(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
	
		// 列表展示数据
		parameters.put("list", ExhibitDatasUtil.addpenOperation(list, ""));
		return parameters;
	}
}
