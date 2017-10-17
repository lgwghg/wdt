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
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.video.service.VideoRecommendService;

/**
 * 首页推荐视频控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 22:21:52
 */
@Controller
@RequestMapping("/videoRecommend/")
public class VideoRecommendController extends BaseController {

	/**
	 * 首页推荐视频 Service定义
	 */
	@Autowired
	private VideoRecommendService videoRecommendService;

	/**
	 * ajax分页动态加载模式
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "searchList", method = RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statusValue", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
			map.put("startTime", System.currentTimeMillis());
			map.put("endTime", System.currentTimeMillis());
			result.put("list", videoRecommendService.queryListByParam(map));
			result.put("success", Boolean.TRUE);
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("message", GlobalConstant.RETURN_ERROR_MESSAGE);
		}
		return result;
	}

}
