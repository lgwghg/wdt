/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.controller;

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
import com.webside.up.model.UpStationEntity;
import com.webside.up.service.UpStationService;
import com.webside.util.ExhibitDatasUtil;
import com.webside.util.PageUtil;
import com.webside.util.StringUtils;

/**
 * 视频作者站点控制管理层
 *
 * @author zengxn
 * @date 2017-04-15 18:29:58
 */

@Controller
@RequestMapping("/upStationCtrl/")
public class UpStationCtrl extends BaseController {
	/**
	 * 视频作者站点 Service定义
	 */
	@Autowired
	private UpStationService upStationService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 跳转查询视频作者站点页面
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
			model.addAttribute("thirdPartyList", dictService.queryListByType(GlobalConstant.THIRD_PARTY_TYPE));
			if (request.getParameterMap().containsKey("upId")) {
				model.addAttribute("upId", request.getParameter("upId"));
			}
			return Common.BACKGROUND_PATH + "/business/up/station/list";
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
				List<UpStationEntity> list = upStationService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "station");
			List<UpStationEntity> list = upStationService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());

			// 权限校验。判断是否包含权限。
			Subject subject = SecurityUtils.getSubject();
			boolean edit = subject.isPermitted("upStation:editUI");
			boolean del = subject.isPermitted("upStation:del");
			// 列表展示数据
			parameters.put("exhibitDatas", ExhibitDatasUtil.addpenOperation(list, edit ? "edit" : "", del ? "del" : ""));
			return parameters;
		}
	}

	/**
	 * 跳转新增视频作者站点页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model, HttpServletRequest request) {
		try {
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("stationList", dictService.queryListByType(GlobalConstant.STATION_TYPE));
			model.addAttribute("thirdPartyList", dictService.queryListByType(GlobalConstant.THIRD_PARTY_TYPE));
			if (request.getParameterMap().containsKey("upId")) {
				model.addAttribute("upId", request.getParameter("upId"));
			}
			return Common.BACKGROUND_PATH + "/business/up/station/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 新增视频作者站点
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@ResponseBody
	public Object add(UpStationEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UpStationEntity findResult = upStationService.findByUpStationThirdPartyId(entity.getUp(), entity.getStation(), entity.getThirdParty(), null);
			if (findResult != null) {
				map.put("success", Boolean.FALSE);
				map.put("message", "添加失败，已存在该站点记录");
			} else {
				if (entity.getThirdParty() != null && StringUtils.isBlank(entity.getThirdParty().getValue())) {
					entity.setThirdParty(null);
				}
				if (entity.getStation() != null && StringUtils.isBlank(entity.getStation().getValue())) {
					entity.setStation(null);
				}
				if (entity.getThirdParty() == null && entity.getStation() == null) {
					map.put("success", Boolean.FALSE);
					map.put("message", "添加失败，请在所属站点与所属第三方中选择其一");
				} else if (entity.getThirdParty() != null && entity.getStation() != null) {
					map.put("success", Boolean.FALSE);
					map.put("message", "添加失败，所属站点与所属第三方只允许选择其中一个");
				} else {
					entity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
					int result = upStationService.insert(entity);

					if (result > 0) {
						map.put("success", Boolean.TRUE);
						map.put("message", "添加成功");
					} else {
						map.put("success", Boolean.FALSE);
						map.put("message", "添加失败");
					}
				}
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 跳转编辑视频作者站点页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		UpStationEntity entity = null;
		PageUtil page = null;

		try {
			entity = upStationService.findById(id);
			page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", entity);

			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("stationList", dictService.queryListByType(GlobalConstant.STATION_TYPE));
			model.addAttribute("thirdPartyList", dictService.queryListByType(GlobalConstant.THIRD_PARTY_TYPE));
			return Common.BACKGROUND_PATH + "/business/up/station/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改视频作者站点
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "edit.html", method = RequestMethod.POST)
	@ResponseBody
	public Object update(UpStationEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			UpStationEntity findResult = upStationService.findByUpStationThirdPartyId(entity.getUp(), entity.getStation(), entity.getThirdParty(), entity.getId());
			if (findResult != null) {
				map.put("success", Boolean.FALSE);
				map.put("message", "编辑失败，已存在该站点记录");
			} else {
				if (entity.getThirdParty() != null && StringUtils.isBlank(entity.getThirdParty().getValue())) {
					entity.setThirdParty(null);
				}
				if (entity.getStation() != null && StringUtils.isBlank(entity.getStation().getValue())) {
					entity.setStation(null);
				}
				if (entity.getThirdParty() == null && entity.getStation() == null) {
					map.put("success", Boolean.FALSE);
					map.put("message", "编辑失败，请在所属站点与所属第三方中选择其一");
				} else if (entity.getThirdParty() != null && entity.getStation() != null) {
					map.put("success", Boolean.FALSE);
					map.put("message", "编辑失败，所属站点与所属第三方只允许选择其中一个");
				} else {
					entity.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
					int result = upStationService.updateById(entity);

					if (result > 0) {
						map.put("success", Boolean.TRUE);
						map.put("message", "编辑成功");
					} else {
						map.put("success", Boolean.FALSE);
						map.put("message", "编辑失败");
					}
				}
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}

	/**
	 * 根据ID删除视频作者站点
	 * 
	 * @author zengxn
	 */
	@RequestMapping("delete.html")
	@ResponseBody
	public Object delete(String id) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			int result = upStationService.deleteById(id);

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
