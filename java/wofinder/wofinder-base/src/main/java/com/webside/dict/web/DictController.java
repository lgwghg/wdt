/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.dict.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.AjaxException;

/**
 * 字典控制管理层
 *
 * @author zengxn
 * @date 2017-05-16 15:05:05
 */

@Controller
@RequestMapping("/dict/")
public class DictController extends BaseController {
	/**
	 * 字典 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 获取字典列表
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping("getDict/{type}")
	@ResponseBody
	public Map<String, Object> getDictType(@PathVariable("type") String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<DictEntity> list = dictService.queryListByType(type);
			map.put("list", list);
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}
}
