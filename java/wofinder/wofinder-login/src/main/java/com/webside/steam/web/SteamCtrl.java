/*******************************************************************************
 * 武汉沃达文化传媒有限公司。
 * All rights reserved. 
 * 
 * @author sunhw
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.steam.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.pac4j.core.context.J2EContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.koraktor.steamcondenser.steam.community.SteamId;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.frame.annotation.Token;
import com.webside.oauth.client.shiro.support.ShiroSessionStore;
import com.webside.openid.client.SteamOpenIdClient;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.SpringBeanUtil;
import com.webside.util.StringUtils;

/**
 * 用户管理 控制类
 * 
 * @author sunhw
 */

@Controller
@RequestMapping("/steam")
public class SteamCtrl extends BaseController {
	@Autowired
	private UserService userService;
	/**
	 * 初始化
	 * Common.BACKGROUND_PATH_WEB + "/login/third/thirdRegister"
	 * @return
	 */
	@RequestMapping("/bind")
	public ModelAndView bind(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = null;
		String redirect = null;

		try {
			mv = new ModelAndView();
			SteamOpenIdClient steamClient = SpringBeanUtil.getBean(SteamOpenIdClient.class);
			final J2EContext context = new J2EContext(WebUtils.toHttp(request), WebUtils.toHttp(response), new ShiroSessionStore());

			redirect = steamClient.getRedirectAction(context, true).getLocation();

			if (StringUtils.isNotEmpty(redirect)) {
				mv.setViewName("redirect:" + redirect);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("redirect:/login");
		}

		return mv;
	}
}
