package com.webside.user.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;

/**
 * web用户控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */

@Controller
@Scope("prototype")
@RequestMapping("/user/")
public class UserController extends BaseController {

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 校验邮箱
	 * 
	 * @author zengxn
	 */
	@RequestMapping("withoutAuth/validateEmail")
	@ResponseBody
	public Object validateEmail(String email) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(email)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "邮箱不能为空");
			} else {
				UserEntity userEntity = userService.findByEmail(email);
				if (userEntity == null) {
					map.put("success", Boolean.TRUE);
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "该邮箱已被使用");
				}
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", GlobalConstant.RETURN_ERROR_MESSAGE);
		}
		return map;
	}

	/**
	 * 校验手机号码
	 * 
	 * @author zengxn
	 */
	@RequestMapping("withoutAuth/validateUserMobile")
	@ResponseBody
	public Object validateUserMobile(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(mobile)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "手机号码不能为空");
			} else {
				UserEntity userEntity = userService.findByMobile(mobile);
				if (userEntity == null) {
					map.put("success", Boolean.TRUE);
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "该手机号码已被使用");
				}
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", GlobalConstant.RETURN_ERROR_MESSAGE);
		}
		return map;
	}

	/**
	 * 校验用户昵称
	 * 
	 * @author zengxn
	 */
	@RequestMapping("withoutAuth/validateUserNick")
	@ResponseBody
	public Object validateUserNick(String nickName) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(nickName)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "用户昵称不能为空");
			} else {
				UserEntity userEntity = userService.findByNickName(nickName);
				if (userEntity == null) {
					map.put("success", Boolean.TRUE);
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "该用户昵称已被使用");
				}
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", GlobalConstant.RETURN_ERROR_MESSAGE);
		}
		return map;
	}

	/**
	 * 验证用户是否已经绑定某账号
	 * 
	 * @author zengxno
	 */
	@RequestMapping("withoutAuth/validateUserBindThird")
	@ResponseBody
	public Object validateUserBindThird(String mobile, String email, String thirdType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", Boolean.FALSE);
		try {
			if ((StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) || StringUtils.isBlank(thirdType)) {
				map.put("message", "帐号不能为空");
			} else if (!GlobalConstant.THIRD_LOGIN_TYPE_STEAM.equals(thirdType) && !GlobalConstant.THIRD_LOGIN_TYPE_QQ.equals(thirdType) && !GlobalConstant.THIRD_LOGIN_TYPE_WEIBO.equals(thirdType) && !GlobalConstant.THIRD_LOGIN_TYPE_WECHAT.equals(thirdType)) {
				map.put("message", "第三方类型有误");
			} else {
				UserEntity userEntity = null;
				if (StringUtils.isNotBlank(mobile)) {
					userEntity = userService.findByMobile(mobile);
				} else if (StringUtils.isNotBlank(email)) {
					userEntity = userService.findByEmail(email);
				}
				if (userEntity == null) {
					map.put("message", "帐号不存在");
				} else {
					if (GlobalConstant.THIRD_LOGIN_TYPE_STEAM.equals(thirdType) && StringUtils.isNotBlank(userEntity.getSteamKey())) {
						map.put("message", "该账号已绑定其他Steam账号");
					} else if (GlobalConstant.THIRD_LOGIN_TYPE_QQ.equals(thirdType) && StringUtils.isNotBlank(userEntity.getQqKey())) {
						map.put("message", "该账号已绑定其他QQ账号");
					} else if (GlobalConstant.THIRD_LOGIN_TYPE_WEIBO.equals(thirdType) && StringUtils.isNotBlank(userEntity.getWeiboKey())) {
						map.put("message", "该账号已绑定其他微博账号");
					} else if (GlobalConstant.THIRD_LOGIN_TYPE_WECHAT.equals(thirdType) && StringUtils.isNotBlank(userEntity.getWechatKey())) {
						map.put("message", "该账号已绑定其他微信账号");
					} else {
						map.put("success", Boolean.TRUE);
					}
				}
			}
		} catch (Exception e) {
			map.put("message", GlobalConstant.RETURN_ERROR_MESSAGE);
		}
		return map;
	}
}
