package com.webside.third.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;

/**
 * 第三方绑定登录，注册
 * 
 * @author tianguifang
 * 
 */
@Controller
@RequestMapping("/bind/")
public class ThirdBindCtrl extends BaseController {
	/**
	 * 绑定登录页
	 * 
	 * @param model
	 * @param thirdKey
	 * @param thirdNickName
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String thirdLoginUI(Model model, String thirdKey, String thirdNick, String thirdType) {
		if (StringUtils.isBlank(thirdKey) || StringUtils.isBlank(thirdNick) || StringUtils.isBlank(thirdType)) {
			return "redirect:/index";
		}
		model.addAttribute("thirdKey", thirdKey);
		model.addAttribute("thirdNick", thirdNick);
		model.addAttribute("thirdType", thirdType);
		return Common.BACKGROUND_PATH_WEB + "/login/third/thirdLogin";
	}

	/**
	 * 绑定注册页
	 * 
	 * @param model
	 * @param thirdKey
	 * @param thirdNickName
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String thirdRegisterUI(Model model, String thirdKey, String thirdNick, String thirdType) {
		if (StringUtils.isBlank(thirdKey) || StringUtils.isBlank(thirdNick) || StringUtils.isBlank(thirdType)) {
			return "redirect:/index";
		}
		model.addAttribute("thirdKey", thirdKey);
		model.addAttribute("thirdNick", thirdNick);
		model.addAttribute("thirdType", thirdType);
		return Common.BACKGROUND_PATH_WEB + "/login/third/thirdRegister";
	}
}