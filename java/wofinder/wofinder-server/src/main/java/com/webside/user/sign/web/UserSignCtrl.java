package com.webside.user.sign.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.sign.entities.UserSign;
import com.webside.user.sign.service.IUserSignService;

/**
 * 用户签到Controller
 * 
 * @author tianguifang
 * @date 2016-12-06 15:04:37
 */
@Controller
@RequestMapping("/my/")
public class UserSignCtrl {

	@Autowired
	private IUserSignService userSignService;

	/**
	 * 用户今天是否签到
	 * @return
	 */
	@RequestMapping(value = "isSign", method = RequestMethod.POST)
	@ResponseBody
	public Object todaySign() {
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return 0;
		}
		UserSign userSign = userSignService.querySignInToday(userId);
		if (userSign != null) {
			return userSign.getIntegral();
		} else {
			return -1;
		}
	}

	/**
	 * 签到
	 * @return
	 */
	@RequestMapping(value = "sign", method = RequestMethod.POST)
	@ResponseBody
	public Object sign() {
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return 0;
		}
		UserSign userSign = new UserSign();
		userSign.setUserId(userId);
		int insertState = userSignService.insert(userSign);
		if (insertState > 0) {
			return insertState;
		} else {
			return 0;
		}
	}

}
