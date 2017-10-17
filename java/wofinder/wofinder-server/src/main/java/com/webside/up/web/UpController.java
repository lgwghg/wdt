package com.webside.up.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.dtgrid.model.Pager;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpRelation;
import com.webside.up.model.UpSecondLevel;
import com.webside.up.model.UpValueEntity;
import com.webside.up.model.UpVo;
import com.webside.up.service.IUpRelationService;
import com.webside.up.service.IUpSecondLevelService;
import com.webside.up.service.UpService;
import com.webside.up.service.UpValueService;
import com.webside.up.service.impl.UpSecondLevelService;
import com.webside.util.ExhibitDatasUtil;
import com.webside.video.model.VideoEntity;
import com.webside.video.service.VideoService;
/**
 * 人物详情controller
 * @author tianguifang
 *
 */
@Controller
@RequestMapping("/p/")
public class UpController extends BaseController{
	
	@Autowired
	private UpService upService;
	@Autowired
	private UpValueService upValueService;
	@Autowired
	private IUpSecondLevelService upSecondLevelService;
	@Autowired
	private IUpRelationService upRelationService;
	@Autowired
	private VideoService videoService;
	/**
	 * 人物详情
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("{id}")
	public String queryUpDetail(Model model, HttpServletRequest request, @PathVariable String id) {
		if (StringUtils.isBlank(id)) {
			return "redirect:/index";
		}
		try {
			UpVo upVo = upService.queryUpDetailByUpId(id);
			if (upVo == null) {
				return "redirect:/index";
			}
			model.addAttribute("upVo", upVo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("staticPrefix", staticPrefix);
		return Common.BACKGROUND_PATH_WEB + "/up/upDetail";
	}
	/**
	 * 人物标签
	 * @param id
	 * @return
	 */
	@RequestMapping("/t/{id}")
	@ResponseBody
	public Object queryUpValue(@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UpValueEntity> upValueList = upValueService.queryListByUpId(id);
		if (!CollectionUtils.isEmpty(upValueList)) {
			resultMap.put("upValueList", upValueList);
		}
		return resultMap;
	}
	/**
	 * 人物二级信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/l/{id}")
	@ResponseBody
	public Object queryUpSecondLevel(@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UpSecondLevel> upSecondLevelList = upSecondLevelService.queryUpSecondLevelListByUpId(id);
		if (!CollectionUtils.isEmpty(upSecondLevelList)) {
			resultMap.put("upSecondLevelList", upSecondLevelList);
		}
		return resultMap;
	}
	/**
	 * 人物相关关系
	 * @param id
	 * @return
	 */
	@RequestMapping("/r/{id}")
	@ResponseBody
	public Object queryUpRelation(@PathVariable String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<UpRelation> upRelationList = upRelationService.queryUpRelationListByUpIdAndRelationId(id, null);
		if (!CollectionUtils.isEmpty(upRelationList)) {
			resultMap.put("upRelationList", upRelationList);
		}
		return resultMap;
	}
	
	/**
	 * 相关视频
	 * @param id
	 * @return
	 */
	@RequestMapping("/v")
	@ResponseBody
	public Object queryRelationVideo(String gridPager, HttpServletResponse response) {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters != null) {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize());
			List<VideoEntity> videoList = videoService.queryUpRelateVideoPgByUp(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			parameters.put("list", ExhibitDatasUtil.addpenOperation(videoList, null));
		}
		return parameters;
	}
}
