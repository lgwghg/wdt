package com.webside.user.uswitch.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.uswitch.model.UserSwitch;
import com.webside.user.uswitch.service.IUserSwitchService;
import com.webside.util.IdGen;

/**
 * 个人中心浏览开关
 * @author tianguifang
 * @date 2017-06-27
 */
@Controller
@Scope("prototype")
@RequestMapping("/my")
public class UserSwitchCtrl {
	/** 用户开关service **/
	@Autowired
	private IUserSwitchService userSwitchService;
	
	/**
	 * 展示浏览开关，用户未设置之前，默认开关是开着的
	 * @return
	 */
	@RequestMapping("/switch/{type}")
	@ResponseBody
	public Object showSwitch(@PathVariable String type) {
		String userId = ShiroAuthenticationManager.getUserId();
		Map<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isBlank(type)) {
			return resultMap;
		} else {
			List<UserSwitch> userSwitchList = userSwitchService.queryUserSwitchByUserId(userId);
			for (UserSwitch userSwitch : userSwitchList) {
				if (type.equals(userSwitch.getSwitchType())) {
					if (userSwitch.getValue() == 1) {
						resultMap.put("switchValue", true);//开关打开
					} else {
						resultMap.put("switchValue", false);//开关关闭
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 设置开关，用户未设置之前，默认开关是开着的
	 * @return
	 */
	@RequestMapping("/editSwitch")
	@ResponseBody
	public Object editSwitch(UserSwitch userSwitch) {
		Map<String, Object> resultMap = new HashMap<>();
		String userId = ShiroAuthenticationManager.getUserId();
		int result = 0;
		if (StringUtils.isBlank(userSwitch.getId())) {
			userSwitch.setId(IdGen.uuid());
			userSwitch.setUserId(userId);
			userSwitch.setCreateTime(System.currentTimeMillis());
			result = userSwitchService.insert(userSwitch);
		} else {
			userSwitch.setUpdateTime(System.currentTimeMillis());
			result = userSwitchService.updateById(userSwitch);
		}
		if (result > 0) {
			resultMap.put("success", true);
			resultMap.put("message", "设置成功");
		} else {
			resultMap.put("success", false);
			resultMap.put("message", "设置失败");
		}
		return resultMap;
	}
	
}
