package com.webside.index.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.CacheConstant;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.exception.ForbiddenAccountException;
import com.webside.exception.SystemException;
import com.webside.frame.annotation.Token;
import com.webside.integral.model.UserIntegral;
import com.webside.integral.service.IUserIntegralService;
import com.webside.ip2region.DbSearcher;
import com.webside.logininfo.model.LoginInfoEntity;
import com.webside.logininfo.service.LoginInfoService;
import com.webside.redis.RedisManager;
import com.webside.rights.model.ResourceEntity;
import com.webside.rights.service.ResourceService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.shiro.support.UsernamePasswordAndClientToken;
import com.webside.sms.SMS;
import com.webside.user.model.UserEntity;
import com.webside.user.model.vo.ThirdUserInfo;
import com.webside.user.service.UserCacheService;
import com.webside.user.service.UserService;
import com.webside.util.CaptchaUtil;
import com.webside.util.CookieUtil;
import com.webside.util.IdGen;
import com.webside.util.IpUtil;
import com.webside.util.RandomUtil;
import com.webside.util.TreeUtil;
import com.webside.util.crypto.EndecryptUtils;

/**
 * 
 * @ClassName: IndexController
 * @Description: 登录、注册、退出、验证码
 * @author zengxn
 * @date 2017年5月4日 17:53:03
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/")
public class IndexController extends BaseController {

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private UserCacheService userCacheService;
	/**
	 * 资源 Service定义
	 */
	@Autowired
	private ResourceService resourceService;

	/**
	 * 登陆信息 Service定义
	 */
	@Autowired
	private LoginInfoService loginInfoService;

	/**
	 * 验证码定义
	 */
	@Autowired
	private Producer captchaProducer;

	/**
	 * ip库定义
	 */
	@Autowired
	private DbSearcher ipSearcher;

	/**
	 * redis定义
	 */
	@Autowired
	private RedisManager redisManager;
	@Autowired
	private IUserIntegralService userIntegralService;

	/**
	 * 跳转登陆
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String login(String returnUrl, Model model, HttpServletRequest request) {
		try {
			if (ShiroAuthenticationManager.getUserEntity() != null) {
				return "redirect:/index";
			}
			request.removeAttribute("error");
			model.addAttribute("loginButtonHide", true);
			model.addAttribute("isLogin", true);
			model.addAttribute("returnUrl", returnUrl);
			return Common.BACKGROUND_PATH_WEB + "/loginAndRegister";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 用户登录 认证过程： 1、想要得到Subject对象,访问地址必须在shiro的拦截地址内,不然会报空指针
	 * 2、用户输入的账号和密码,存到UsernamePasswordToken对象中,然后由shiro内部认证对比
	 * 3、认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理 4、当以上认证成功后会向下执行,认证失败会抛出异常
	 * 
	 * @param userEntity
	 *            用户对象
	 * @param captcha
	 *            验证码
	 * @param rememberMe
	 *            是否记住
	 * @param thirdUser
	 *            第三方登陆信息
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public Object userLogin(UserEntity userEntity, String captcha, Boolean rememberMe, ThirdUserInfo thirdUser, String returnUrl, HttpServletRequest request, HttpServletResponse response) {
		UsernamePasswordAndClientToken token = null;
		Map<String, Object> map = new HashMap<String, Object>();
		String userCookieMsg = "";
		SavedRequest savedRequest = null;
		try {
			// 获取验证码
			String expected = ShiroAuthenticationManager.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
			if (!StringUtils.equalsIgnoreCase(expected, captcha)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "验证码错误！");
			} else {
				// 想要得到Subject对象,访问地址必须在shiro的拦截地址内,不然会报空指针
				Subject subject = SecurityUtils.getSubject();
				if (StringUtils.isNotBlank(userEntity.getMobile()) || StringUtils.isNotBlank(userEntity.getEmail())) {
					// 手机不为空，就用手机登录
					if (StringUtils.isNotBlank(userEntity.getMobile())) {
						token = new UsernamePasswordAndClientToken(userEntity.getMobile(), userEntity.getPassword());
						userCookieMsg = userCookieMsg + userEntity.getMobile();
					}
					// 邮箱不为空，就用邮箱登录
					else {
						token = new UsernamePasswordAndClientToken(userEntity.getEmail(), userEntity.getPassword());
						userCookieMsg = userCookieMsg + userEntity.getEmail();
					}
					// 记住用户登录状态
					token.setRememberMe(rememberMe);
					subject.login(token);

					if (subject.isAuthenticated()) {
						userEntity = (UserEntity) SecurityUtils.getSubject().getPrincipal();
						// 将用户存入session
						session.setAttribute(GlobalConstant.LOGIN_USER_SESSION, userEntity);
						// 存储昵称
						if (StringUtils.isNotBlank(userEntity.getNickName())) {
							CookieUtil.addCookie(response, GlobalConstant.COOKIE_NICK_NAME, URLEncoder.encode(userEntity.getNickName(), "utf-8"), CacheConstant.SEVEN_DAY);
						}
						// 存储头像
						if (StringUtils.isNotBlank(userEntity.getPhoto())) {
							CookieUtil.addCookie(response, GlobalConstant.COOKIE_USER_PHOTO, URLEncoder.encode(userEntity.getPhoto(), "utf-8").replaceAll("%2F", "/"), CacheConstant.SEVEN_DAY);
						} else {
							CookieUtil.clearCookie(request, response, GlobalConstant.COOKIE_USER_PHOTO);
						}
						// 存储账号
						if (StringUtils.isNotBlank(userCookieMsg)) {
							CookieUtil.addCookie(response, GlobalConstant.COOKIE_LOGIN_NAME, URLEncoder.encode(userCookieMsg, "utf-8"), CacheConstant.SEVEN_DAY);
						}

						// 保存登陆信息
						LoginInfoEntity loginInfo = new LoginInfoEntity();
						loginInfo.setUserId(userEntity.getId());
						loginInfo.setAccountName(userEntity.getNickName());
						String ip = IpUtil.getIpAddr(request);
						String userAgent = request.getHeader("User-Agent");
						if (StringUtils.isNotBlank(userAgent)) {
							loginInfo.setUserAgent(userAgent);
							// userAgent = userAgent.toLowerCase();
							if (userAgent.indexOf("Windows") != -1 || userAgent.indexOf("Mac") != -1) {
								loginInfo.setDevice("Windows");
							} else if (userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("Android") != -1) {
								loginInfo.setDevice("iPhone");
							} else if (userAgent.indexOf("iPad") != -1) {
								loginInfo.setDevice("iPad");
							} else {
								loginInfo.setDevice("Windows");
							}
						}
						//System.out.println("userAgent:" + userAgent);
						String region = ipSearcher.memorySearch(ip).getRegion();
						String[] regions = StringUtils.split(region, '|');
						loginInfo.setLoginIp(ip);
						loginInfo.setProvince(regions[2]);
						loginInfo.setCity(regions[3]);
						loginInfo.setRegion(region);
						loginInfoService.log(loginInfo);
						// 判断是否需要绑定第三方,如果通过第三方账户注册的，则加入第三方账号信息
						if (thirdUser != null && StringUtils.isNotBlank(thirdUser.getThirdKey())) {
							UserEntity userUpdate = new UserEntity();
							userUpdate.setId(userEntity.getId());
							if (GlobalConstant.THIRD_LOGIN_TYPE_STEAM.equals(thirdUser.getThirdType())) {
								userUpdate.setSteamKey(thirdUser.getThirdKey());
								userUpdate.setSteamNick(thirdUser.getThirdNick());
							}
							if (GlobalConstant.THIRD_LOGIN_TYPE_QQ.equals(thirdUser.getThirdType())) {
								userUpdate.setQqKey(thirdUser.getThirdKey());
								userUpdate.setQqNick(thirdUser.getThirdNick());
							}
							if (GlobalConstant.THIRD_LOGIN_TYPE_WEIBO.equals(thirdUser.getThirdType())) {
								userUpdate.setWeiboKey(thirdUser.getThirdKey());
								userUpdate.setWeiboNick(thirdUser.getThirdNick());
							}
							if (GlobalConstant.THIRD_LOGIN_TYPE_WECHAT.equals(thirdUser.getThirdType())) {
								userUpdate.setWechatKey(thirdUser.getThirdKey());
								userUpdate.setWechatNick(thirdUser.getThirdNick());
							}
							// 更新绑定第三方信息
							userService.updateUserOnly(userUpdate, true);
						}

						// 获取上一次请求路径
						savedRequest = WebUtils.getSavedRequest(request);

						map.put("success", Boolean.TRUE);
						map.put("message", "登陆成功！");
						map.put("code", 1);
						if (StringUtils.isBlank(returnUrl)) {
							returnUrl = "/index";
						}
						map.put("url", "/loginSuccess?returnUrl=" + URLEncoder.encode(savedRequest == null ? returnUrl : savedRequest.getRequestURI(), "utf-8"));
						CookieUtil.addCookie(response, "login", "1", 120);
					} else {
						token.clear();
						map.put("success", Boolean.FALSE);
						map.put("message", "用户名或密码错误！");
					}
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "账户不存在！");
				}
			}
		} catch (UnknownAccountException uae) {
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "账户不存在！");
		} catch (IncorrectCredentialsException ice) {
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "用户名或密码错误！");
		} catch (LockedAccountException e) {
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "您的账户已被锁定,请稍后再试！");
		} catch (ExcessiveAttemptsException e) {
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "您连续输错5次,帐号将被锁定10分钟!");
		} catch (ExpiredCredentialsException eca) {
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "用户名或密码错误！");
		} catch (ForbiddenAccountException e) {
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "您的账号已被禁止登录");
		} catch (AuthenticationException e) {
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "用户名或密码错误！");
		} catch (Exception e) {
			logger.error("登录异常", e);
			token.clear();
			map.put("success", Boolean.FALSE);
			map.put("message", "未知错误,请稍后！");
		}
		return map;
	}

	@RequestMapping(value = "loginSuccess", method = RequestMethod.GET)
	public String loginSuccess(Model model, HttpServletRequest request, HttpServletResponse response, String returnUrl) {
		String login = CookieUtil.findCookieByName(request, "login");
		if (StringUtils.isNotBlank(login) && login.equals("1")) {
			CookieUtil.clearCookie(request, response, "login");
			model.addAttribute("returnUrl", returnUrl);
			return Common.BACKGROUND_PATH_WEB + "/loginSuccess";
		} else {
			return "redirect:/";
		}
	}
	/**
	 * 网站首页
	 */
	@RequestMapping("index")
	@Token(save = true)
	public String index() {
		return "redirect:/";
	}

	/**
	 * 欢迎页面，登录会跳转到后台管理 未登录直接跳到登陆页。
	 * 
	 * @return
	 */
	@RequestMapping
	public String main() {
		return Common.BACKGROUND_PATH_WEB + "/index";
	}

	/**
	 * 后台首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String admin(Model model) {
		try {
			// 获取登录的bean;
			UserEntity userEntity = userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId());
			List<ResourceEntity> list = resourceService.findResourcesMenuByUserId(userEntity.getId());
			List<ResourceEntity> treeList = TreeUtil.getChildResourceEntitys(list, null);
			model.addAttribute("list", treeList);
			model.addAttribute("menu", JSON.toJSONString(treeList));
			// 登陆的信息回传页面
			model.addAttribute("userEntity", userEntity);
			return "/index";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 跳转注册页面
	 * 
	 * @param model
	 * @return
	 */
	@Token(save = true)
	@RequestMapping(value = "register", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public String registerUI(Model model) {
		try {
			if (ShiroAuthenticationManager.getUserEntity() != null) {
				return "redirect:/index";
			}
			model.addAttribute("loginButtonHide", true);
			return Common.BACKGROUND_PATH_WEB + "/loginAndRegister";
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param userEntity
	 * @return
	 */
	// @Token(remove = true)
	@RequestMapping(value = "register", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	@ResponseBody
	public Object register(UserEntity userEntity, ThirdUserInfo thirdUser, String fromuid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*// 检索用户昵称是否存在
			UserEntity userNick = userService.findByNickName(userEntity.getNickName());
			if (userNick == null) {*/
			// 检索手机号码是否存在
			UserEntity userMobile = userService.findByMobile(userEntity.getMobile());
			if (userMobile == null) {
				userEntity.setId(IdGen.uuid());
				userEntity.setNickName("wf_" + RandomUtil.generateString(9));
				// 加密用户输入的密码，得到密码和加密盐，保存到数据库
				UserEntity user = EndecryptUtils.md5Password(userEntity.getId(), userEntity.getPassword(), 2);
				// 设置添加用户的密码和加密盐
				userEntity.setPassword(user.getPassword());
				userEntity.setCredentialsSalt(user.getCredentialsSalt());
				// 设置锁定状态：未锁定；状态：正常
				userEntity.setLocked(new DictEntity(GlobalConstant.DICTTYPE_YES_NO_0));
				userEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
				// 如果通过第三方账户注册的，则加入第三方账号信息
				if (thirdUser != null) {
					if (GlobalConstant.THIRD_LOGIN_TYPE_STEAM.equals(thirdUser.getThirdType())) {
						userEntity.setSteamKey(thirdUser.getThirdKey());
						userEntity.setSteamNick(thirdUser.getThirdNick());
					}
					if (GlobalConstant.THIRD_LOGIN_TYPE_QQ.equals(thirdUser.getThirdType())) {
						userEntity.setQqKey(thirdUser.getThirdKey());
						userEntity.setQqNick(thirdUser.getThirdNick());
					}
					if (GlobalConstant.THIRD_LOGIN_TYPE_WEIBO.equals(thirdUser.getThirdType())) {
						userEntity.setWeiboKey(thirdUser.getThirdKey());
						userEntity.setWeiboNick(thirdUser.getThirdNick());
					}
					if (GlobalConstant.THIRD_LOGIN_TYPE_WECHAT.equals(thirdUser.getThirdType())) {
						userEntity.setWechatKey(thirdUser.getThirdKey());
						userEntity.setWechatNick(thirdUser.getThirdNick());
					}
				}
				// 保存用户注册信息
				int insert = userService.insert(userEntity);
				// 新增成功
				if (insert == 1) {
					// 加积分
					try {
						UserIntegral integral = new UserIntegral();
						integral.setUserId(userEntity.getId());
						integral.setIntegral(10);
						integral.setIntegralSource("注册");
						integral.setType(GlobalConstant.INTEGRAL_TYPE_REGISTER_0);
						integral.setStatus(1);
						userIntegralService.insertIntegral(integral);
					} catch (Exception e) {
						logger.error("注册加积分异常", e);
					}

					map.put("success", Boolean.TRUE);
					map.put("message", "注册成功");
					map.put("userId", userEntity.getId());
					map.put("nickName", userEntity.getNickName());
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "注册失败");
				}
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "手机号已存在");
			}
			/*} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "昵称已存在");
			}*/
		} catch (Exception e) {
			logger.error("注册用户失败：", e);
			map.put("success", Boolean.FALSE);
			map.put("message", "注册失败");
		}
		return map;
	}

	/**
	 * 用户退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// 注销登录
		session.removeAttribute(GlobalConstant.LOGIN_USER_SESSION);
		CookieUtil.deleteAllCookie(request, response);
		ShiroAuthenticationManager.logout();
		CookieUtil.addCookie(response, "logout", "1", 120);
		return "redirect:/logoutSuccess";
	}
	@RequestMapping(value = "logoutSuccess", method = RequestMethod.GET)
	public String logoutSuccess(HttpServletRequest request, HttpServletResponse response) {
		String logout = CookieUtil.findCookieByName(request, "logout");
		if (StringUtils.isNotBlank(logout) && logout.equals("1")) {
			CookieUtil.clearCookie(request, response, "logout");
			return Common.BACKGROUND_PATH_WEB + "/logout";
		} else {
			return "redirect:/";
		}
	}
	/**
	 * 获取验证码
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void kaptcha(HttpServletResponse response) {
		ServletOutputStream out = null;
		try {
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");

			String capText = CaptchaUtil.getNumCaptcha();
			// 将验证码存入shiro 登录用户的session
			ShiroAuthenticationManager.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

			BufferedImage image = captchaProducer.createImage(capText);
			out = response.getOutputStream();
			ImageIO.write(image, "jpg", out);
			out.flush();
		} catch (IOException e) {
			throw new SystemException(e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				throw new SystemException(e);
			}
		}
	}

	/**
	 * 检验验证码正确性
	 * 
	 * @param imgCaptcha
	 * @return
	 */
	@RequestMapping(value = "/captcha/check", method = RequestMethod.POST)
	@ResponseBody
	public Object checkImgCaptcha(String imgCaptcha) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(imgCaptcha)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "验证码不能为空");
				map.put("code", -2);
			}
			// 将验证码存入shiro 登录用户的session
			String sessionCa = (String) ShiroAuthenticationManager.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (StringUtils.isNotBlank(sessionCa) && sessionCa.equals(imgCaptcha)) {
				map.put("success", Boolean.TRUE);
				map.put("message", "验证码正确！");
				map.put("code", 1);
			} else {
				map.put("success", Boolean.TRUE);
				map.put("message", "验证码错误！");
				map.put("code", 0);
			}
		} catch (Exception e) {
			logger.error("验证验证码正确性出错：", e);
			map.put("success", Boolean.FALSE);
			map.put("message", "验证出错，请稍候再试！");
			map.put("code", -1);
		}
		return map;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 * @param imgCaptcha
	 * @return
	 */
	@RequestMapping(value = "withoutAuth/genCaptcha", method = RequestMethod.POST)
	@ResponseBody
	public Object mobileKaptcha(String mobile, String imgCaptcha) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*// 验证码为空
			if (StringUtils.isBlank(imgCaptcha)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "验证码不能为空！");
				map.put("code", -2);
			} else {
				// 验证码错误
				String textCa = (String) ShiroAuthenticationManager.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
				if (StringUtils.isBlank(textCa) || !textCa.equals(imgCaptcha)) {
					map.put("success", Boolean.FALSE);
					map.put("message", "验证码错误！");
					map.put("code", 0);
				} else {
				}
			}*/
			// 频繁发送
			//String countDownKey = GlobalConstant.CAPTCHA_COUNTDOWN + mobile;
			String kaptchaKey = GlobalConstant.CAPTCHA_MOBILE + mobile;
			if (redisManager.exists(kaptchaKey)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "操作频繁，五分钟内只允许发送一次，请稍候重试！");
				map.put("code", -3);
			} else {
				// 生成验证码
				String capText = CaptchaUtil.getNumCaptcha();
				// 1分钟倒计时
				//redisManager.setex(countDownKey, DateUtils.getDate("yyyy-MM-dd HH:mm:ss"), CacheConstant.ONE_MINITES);
				// 短信验证码缓存5分钟
				redisManager.setex(kaptchaKey, capText, CacheConstant.FIVE_MINITES);
				// 发送短信验证码
				SMS.sendKaptcha(capText, mobile, SMS.MOBILE_AUTHENTICATION_KEY); // 统一用手机身份验证
				ShiroAuthenticationManager.delSessionAttribute(Constants.KAPTCHA_SESSION_KEY);
				map.put("success", Boolean.TRUE);
				map.put("message", "发送成功！");
				map.put("code", 1);
			}
		} catch (Exception e) {
			logger.error("发送短信验证码出错", e);
			map.put("success", Boolean.FALSE);
			map.put("message", "发送失败，请稍候再试！");
			map.put("code", -1);
		}
		return map;
	}

	/**
	 * 检验短信验证码正确性
	 * 
	 * @param captcha
	 * @param mobile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/withoutAuth/checkCaptcha", method = RequestMethod.POST)
	@ResponseBody
	public Object checkCaptcha(String captcha, String mobile, Integer isClear) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 验证码为空 || 手机号码为空
			if (StringUtils.isBlank(captcha) || StringUtils.isBlank(mobile)) {
				if (StringUtils.isBlank(captcha)) {
					map.put("success", Boolean.FALSE);
					map.put("message", "验证码不能为空！");
					map.put("code", -2);
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "手机号码不能为空！");
					map.put("code", -3);
				}
			} else {
				String captchaKey = GlobalConstant.CAPTCHA_MOBILE + mobile;
				// 从redis中取出验证码
				String capText = redisManager.get(captchaKey);
				if (StringUtils.isBlank(capText)) {
					map.put("success", Boolean.FALSE);
					map.put("message", "验证码错误！");
					map.put("code", -4);
				} else {
					if (captcha.equals(capText)) {
						// 只在验证码正确的时候清空redis中的验证码，根据业务需求调整
						if (isClear != null && isClear == 1) {
							redisManager.delByKey(captchaKey);
						}
						map.put("success", Boolean.TRUE);
						map.put("message", "验证码正确！");
						map.put("code", 1);
					} else {
						map.put("success", Boolean.FALSE);
						map.put("message", "验证码错误！");
						map.put("code", 0);
					}
				}
			}
		} catch (Exception e) {
			logger.error("检验短信验证码正确性出错", e);
			map.put("success", Boolean.FALSE);
			map.put("message", "验证失败，请稍候再试！");
			map.put("code", -1);
		}
		return map;
	}

	/**
	 * 正在开发中页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "developing")
	public String developing() {
		return Common.BACKGROUND_PATH_WEB + "/developing";
	}
}
