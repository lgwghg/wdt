package com.webside.user.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.config.service.ConfigService;
import com.webside.exception.AjaxException;
import com.webside.forget.model.UserForgetPassword;
import com.webside.forget.service.IUserForgetPasswordService;
import com.webside.redis.RedisManager;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.EmailUtil;
import com.webside.util.IdGen;
import com.webside.util.crypto.EndecryptUtils;

/**
 * 登录，注册相关
 * 
 * @author suyan
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/fp")
public class ForgetPasswordController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private Producer captchaProducer;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private ConfigService configService;
	@Autowired
	private RedisManager redisManager;
	@Autowired
	private IUserForgetPasswordService userForgetPasswordService;
	/**
	 * 忘记密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String forgetPassword(Model model) {
		return Common.BACKGROUND_PATH_WEB + "/forgetpwd";
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public Object password(UserEntity userEntity) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String password = userEntity.getPassword();
			if (StringUtils.isBlank(password)) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "密码不能为空");
				return result;
			}
			if (StringUtils.isBlank(userEntity.getMobile())) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "手机号不能为空");
				return result;
			}
			UserEntity userQ = userService.findByMobile(userEntity.getMobile());
			if (userQ == null) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "该手机号的用户不存在");
				return result;
			}
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity user = EndecryptUtils.md5Password(userQ.getId(), userEntity.getPassword(), 2);
			// 设置添加用户的密码和加密盐
			userEntity.setId(user.getId());
			userEntity.setPassword(user.getPassword());
			userEntity.setCredentialsSalt(user.getCredentialsSalt());
			int cnt = userService.updateUserOnly(userEntity, false);
			if (cnt > 0) {
				result.put("state", true);
				result.put("data", null);
				result.put("message", "密码修改成功");
				result.put("userId", user.getId());
			} else {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "密码修改失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return result;
	}

	@RequestMapping(value = "/feedback", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public Object saveForgetReason(String feedback,String userId,Integer type) {
		UserForgetPassword forget = new UserForgetPassword();
		if (StringUtils.isBlank(userId)) {
			userId = ShiroAuthenticationManager.getUserId();
		}
		forget.setId(IdGen.uuid());
		forget.setForgetReason(feedback);
		forget.setUserId(userId);
		forget.setStatus(1);
		forget.setType(type);
		forget.setCreateTime(System.currentTimeMillis());
		int result = userForgetPasswordService.insert(forget);
		return result;
	}
}
