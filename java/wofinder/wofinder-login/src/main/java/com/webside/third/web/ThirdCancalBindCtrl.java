package com.webside.third.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.service.UserService;

/**
 * 第三方绑定登录，注册
 * 
 * @author tianguifang
 * 
 */
@Controller
@RequestMapping("/unbind")
public class ThirdCancalBindCtrl extends BaseController {
	@Autowired
	private UserService userService;

	/**
	 * 取消第三方绑定
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public int cancelBind(String thirdType) {
		String userId = ShiroAuthenticationManager.getUserId();
		int cancel = userService.updateCancelBind(userId, thirdType);
		return cancel;
	}
}
