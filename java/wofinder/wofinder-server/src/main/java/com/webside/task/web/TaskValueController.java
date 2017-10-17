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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.task.model.TaskValueEntity;
import com.webside.task.service.TaskValueService;

/**
 * 事件属性值关联控制管理层
 * 
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */

@Controller
@RequestMapping("/taskValue")
public class TaskValueController extends BaseController {
	/**
	 * 事件属性值关联 Service定义
	 */
	@Autowired
	private TaskValueService taskValueService;

	/**
	 * 属性值 Service定义
	 */
	@Autowired
	private ValueService valueService;

	/**
	 * 事件详情页，查询标签
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Object queryTaskValue(String taskId) {
		Map<String, Object> result = new HashMap<>();
		List<TaskValueEntity> taskValueList = taskValueService.queryListByTaskId(taskId);
		if (!CollectionUtils.isEmpty(taskValueList)) {
			ValueEntity valueEntity = null;
			for (TaskValueEntity videoValue : taskValueList) {
				valueEntity = valueService.findById(videoValue.getValue().getId());
				if (valueEntity != null) {
					videoValue.setValue(valueEntity);
				}
			}
		}
		result.put("taskValueList", taskValueList);
		return result;
	}
}
