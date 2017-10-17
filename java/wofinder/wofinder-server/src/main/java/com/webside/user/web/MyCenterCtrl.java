package com.webside.user.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.common.CacheConstant;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dtgrid.model.Pager;
import com.webside.exception.AjaxException;
import com.webside.integral.model.UserIntegral;
import com.webside.integral.service.IUserIntegralService;
import com.webside.ip2region.DbSearcher;
import com.webside.logininfo.model.LoginInfoEntity;
import com.webside.logininfo.service.LoginInfoService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.interest.model.UserInterest;
import com.webside.user.interest.service.IUserInterestService;
import com.webside.user.message.model.MessageVo;
import com.webside.user.message.service.UserMessageService;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrementEntity;
import com.webside.user.photo.model.UserPhoto;
import com.webside.user.photo.service.IUserPhotoService;
import com.webside.user.service.UserCacheService;
import com.webside.user.service.UserIncrementService;
import com.webside.user.service.UserService;
import com.webside.user.uswitch.model.UserSwitch;
import com.webside.user.uswitch.service.IUserSwitchService;
import com.webside.util.CookieUtil;
import com.webside.util.IdGen;
import com.webside.util.IpUtil;
import com.webside.util.crypto.EndecryptUtils;
import com.webside.video.model.VideoGradeEntity;
import com.webside.video.service.VideoCommentService;
import com.webside.video.service.VideoGradeService;
/**
 * 个人中心
 * @author tianguifang
 * @date 2017-06-22
 */
@Controller
@Scope("prototype")
@RequestMapping("/my")
public class MyCenterCtrl {
	
	protected static final Logger logger = Logger.getLogger(MyCenterCtrl.class);
	@Autowired
	private LoginInfoService loginInfoService;
	/**
	 * ip库定义
	 */
	@Autowired
	private DbSearcher ipSearcher;
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserCacheService userCacheService;
	
	@Autowired
	private UserIncrementService userIncrementService;
	/** 用户头像service **/
	@Autowired
	private IUserPhotoService userPhotoService;
	/** 用户兴趣service **/
	@Autowired
	private IUserInterestService userInterestService;
	/** 视频评论service **/
	@Autowired
	private VideoCommentService videoCommentService;
	/** 视频点评（评分）service **/
	@Autowired
	private VideoGradeService videoGradeService;
	/** 用户消息service **/
	@Autowired
	private UserMessageService userMessageService;
	@Autowired
	private IUserSwitchService userSwitchService;
	@Autowired
	private IUserIntegralService userIntegralService;
	
	/**
	 * 个人中心首页
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("")
	public String myCenter(Model model, HttpServletRequest request, HttpServletResponse response) {
		return Common.BACKGROUND_PATH_WEB + "/my/myCenter";
	}
	
	@RequestMapping("/welcome")
	public String welcome(Model model,HttpServletRequest request, HttpServletResponse response) {
		LoginInfoEntity loginInfo = new LoginInfoEntity();
		String ip = IpUtil.getIpAddr(request);
		String region;
		try {
			region = ipSearcher.memorySearch(ip).getRegion();
			String[] regions = StringUtils.split(region, '|');
			System.out.println(regions.toString());
			loginInfo.setLoginIp(ip);
			loginInfo.setProvince(regions[2]);
			loginInfo.setCity(regions[3]);
			loginInfo.setNet(regions[4]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("loginInfo", loginInfo);
		model.addAttribute("userInfo", userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId()));
		return Common.BACKGROUND_PATH_WEB + "/my/welcome";
	}
	/**
	 * 登录与安全    &   设备活动与通知
	 * 只取最近的10条数据
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginAndSecurity")
	@ResponseBody
	public Object queryLoginInfo() {
		Map<String, Object> map = new HashMap<>();
		Pager pager = new Pager();
		pager.setPageSize(10);
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", ShiroAuthenticationManager.getUserId());
			pager.setParameters(parameters);
			PageHelper.startPage(pager.getNowPage(),pager.getPageSize(), "l_login_time DESC");
			List<LoginInfoEntity> loginInfoList = loginInfoService.queryListByPage(parameters);
			map.put("loginInfoList", loginInfoList);
			
		} catch (Exception e) {
			logger.error("获取登录日志出错", e);
		}
		return map;//Common.BACKGROUND_PATH_WEB + "/my/loginAndSecurity";
	}
	
	/**
	 * 个人信息与隐私页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/personalInfo")
	public String personalInfo(Model model) {
		String userId = ShiroAuthenticationManager.getUserId();
		UserEntity user = userService.findUserAndIncrementById(userId);
		model.addAttribute("userInfo", user);
		try {
			List<UserPhoto> usrePhotoList = userPhotoService.queryUserHistoryPhoto(userId);
			model.addAttribute("usrePhotoList", usrePhotoList);
			
		} catch (Exception e) {
			logger.error("", e);
		}
		List<UserInterest> interestList  = userInterestService.queryUserInterestByUserId(userId);
		model.addAttribute("interestList", interestList);
		return Common.BACKGROUND_PATH_WEB + "/my/personal";
	}
	
	/**
	 * 修改个人信息与隐私页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Object editPersonalInfo(HttpServletResponse response, UserEntity user) {
		Map<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isNotBlank(ShiroAuthenticationManager.getUserId())) {
			user.setId(ShiroAuthenticationManager.getUserId());
			int result = userService.updateById(user, true);
			if (result > 0 && StringUtils.isNotBlank(user.getNickName())) {
				try {
					CookieUtil.addCookie(response, GlobalConstant.COOKIE_NICK_NAME, URLEncoder.encode(user.getNickName(), "utf-8"), CacheConstant.SEVEN_DAY);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			resultMap.put("result", result);
		}
		return resultMap;
	}
	/**
	 * 修改个人信息的头像
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPhoto")
	@ResponseBody
	public Object editPhoto(HttpServletResponse response, String photoId, String deleteIds) {
		Map<String, Object> resultMap = new HashMap<>();
		// 删除的
		if (StringUtils.isNotBlank(deleteIds)) {
			String[] deleteIdArr = deleteIds.split(",");
			if (deleteIdArr != null && deleteIdArr.length > 0) {
				int deresult = userPhotoService.deleteBatchById(Arrays.asList(deleteIdArr));
				resultMap.put("deresult", deresult);
			}
		}
		if (StringUtils.isNotBlank(photoId)) {
			UserPhoto exchangePhoto = userPhotoService.findById(photoId);
			// 头像切换的
			UserEntity user = new UserEntity();
			user.setId(ShiroAuthenticationManager.getUserId());
			user.setPhoto(exchangePhoto.getPhoto());
			user.setUpdateTime(System.currentTimeMillis() + "");
			int result = userService.updateUserOnly(user, true);
			resultMap.put("result", result);
			exchangePhoto.setUserId(ShiroAuthenticationManager.getUserId());
			// 切换头像表中该用户的当前头像
			userPhotoService.updateCurrentPhoto(exchangePhoto);
			// 设置进cookie
			try {
				CookieUtil.addCookie(response, GlobalConstant.COOKIE_USER_PHOTO, URLEncoder.encode(user.getPhoto(), "utf-8").replaceAll("%2F", "/"), CacheConstant.SEVEN_DAY);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		List<UserPhoto> usrePhotoList = userPhotoService.queryUserHistoryPhoto(ShiroAuthenticationManager.getUserId());
		resultMap.put("usrePhotoList", usrePhotoList);
		return resultMap;
	}
	
	/**
	 * 修改个人信息的生日与性别
	 * @param model
	 * @return
	 */
	@RequestMapping("/editBirthdayAndSex")
	@ResponseBody
	public Object editBirthdayAndSex(Long birthday, Integer sex) {
		Map<String, Object> resultMap = new HashMap<>();
		UserIncrementEntity userIncrement = new UserIncrementEntity();
		userIncrement.setUser(ShiroAuthenticationManager.getUserEntity());
		if (birthday != null) {
			userIncrement.setBirthday(birthday);
		}
		if (sex != null) {
			userIncrement.setSex(sex);
		}
		int result = userIncrementService.updateByUserId(userIncrement);
		resultMap.put("result", result);
		return resultMap;
	}
	
	/**
	 * 修改个人信息的兴趣
	 * @param model
	 * @return
	 */
	@RequestMapping("/editInterest")
	@ResponseBody
	public Object editInterest(String interest, String deleteIds) {
		Map<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isNotBlank(deleteIds)) {
			String[] deleteIdArr = deleteIds.split(",");
			if (deleteIdArr != null && deleteIdArr.length > 0) {
				int deresult = userInterestService.deleteBatchById(Arrays.asList(deleteIdArr));
				resultMap.put("deresult", deresult);
				List<UserInterest> interestList  = userInterestService.queryUserInterestByUserId(ShiroAuthenticationManager.getUserId());
				resultMap.put("interestList", interestList);
			}
		}
		if (StringUtils.isNotBlank(interest)) {
			List<UserInterest> interestList  = userInterestService.queryUserInterestByUserId(ShiroAuthenticationManager.getUserId());
			if (interestList.size() < 5) {
				UserInterest userInterest = new UserInterest();
				String id = IdGen.uuid();
				userInterest.setId(id);
				userInterest.setUserId(ShiroAuthenticationManager.getUserId());
				userInterest.setCreateId(ShiroAuthenticationManager.getUserId());
				userInterest.setInterest(interest);
				userInterest.setStatus(1);
				userInterest.setCreateTime(System.currentTimeMillis());
				int result = userInterestService.insert(userInterest);
				resultMap.put("result", result);
				resultMap.put("id", id);
			} else {
				resultMap.put("result", 0);
				resultMap.put("msg", "每个用户最多添加5个标签哦");
			}
		}
		return resultMap;
	}
	
	@RequestMapping("/msg")
	public String showMessage() {
		return Common.BACKGROUND_PATH_WEB + "/my/message";
	}
	/**
	 * 消息中心
	 * @param gridPager
	 * @param response
	 * @return
	 */
	@RequestMapping("/msgData")
	@ResponseBody
	public Object showMessage(String gridPager, HttpServletResponse response) {
		String userId = ShiroAuthenticationManager.getUserId();
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		parameters.put("userId", userId);
		// 设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize());
		List<MessageVo> list = userMessageService.queryUserMessageListByPg(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		// 列表展示数据
		parameters.put("list", list);
		return parameters;
	}
	/**
	 * 消息读取
	 * @return
	 */
	@RequestMapping("/readMsg")
	@ResponseBody
	public Object readMsg(Integer type, String businessId) {
		String userId = ShiroAuthenticationManager.getUserId();
		int result = userMessageService.updateMessageState(userId, type, businessId);
		return result;
	}
	/**
	 * 未读消息数
	 * @return
	 */
	@RequestMapping("/unreadMsgNum")
	@ResponseBody
	public Object unreadMsgNum() {
		String userId = ShiroAuthenticationManager.getUserId();
		if (StringUtils.isBlank(userId)) {
			return 0;
		}
		int result = userMessageService.getUnreadMsgNum(userId);
		return result;
	}
	
	@RequestMapping("/grade")
 	public String showVideoGrade() {
		return Common.BACKGROUND_PATH_WEB + "/my/commentGrade";
 	}
	/**
	 * 点评记录
	 * @param gridPager
	 * @param response
	 * @return
	 */
	@RequestMapping("/gradeData")
	@ResponseBody
	public Object showVideoGrade(String gridPager, HttpServletResponse response) {
		String userId = ShiroAuthenticationManager.getUserId();
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		parameters.put("userId", userId);
		// 设置分页，page里面包含了分页信息
		Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "g.create_time desc");
		List<VideoGradeEntity> list = videoGradeService.queryListByPage(parameters);
		parameters.clear();
		parameters.put("isSuccess", Boolean.TRUE);
		parameters.put("nowPage", pager.getNowPage());
		parameters.put("pageSize", pager.getPageSize());
		parameters.put("pageCount", page.getPages());
		parameters.put("recordCount", page.getTotal());
		parameters.put("startRecord", page.getStartRow());
		// 列表展示数据
		parameters.put("list", list);
		return parameters;
	}
	
	@RequestMapping("/showPwdEdit")
 	public String showEditPwd() {
		return Common.BACKGROUND_PATH_WEB + "/my/pwdEdit";
 	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("/editPwd")
	@ResponseBody
	public Object editPassword(String oldPassword, String newPassword) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(oldPassword)) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "原密码不能为空");
				return result;
			}
			if (StringUtils.isBlank(newPassword)) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "新密码不能为空");
				return result;
			}
			UserEntity userEntity = ShiroAuthenticationManager.getUserEntity();
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			String pwd = EndecryptUtils.checkMd5Password(userEntity.getId(), oldPassword,userEntity.getCredentialsSalt(), 2);
			if (!userEntity.getPassword().equals(pwd)) {
				result.put("state", false);
				result.put("data", null);
				result.put("message", "原密码错误");
				return result;
			}
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity user = EndecryptUtils.md5Password(userEntity.getId(), newPassword, 2);
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
	
	@RequestMapping("/showAccountSet")
 	public String showAccountSet(Model model) {
		UserEntity user = userCacheService.getUserEntityByUserId(ShiroAuthenticationManager.getUserId());
		if (user != null) {
			model.addAttribute("mobile", user.getMobile());
			if (StringUtils.isNotBlank(user.getAccountUpdateTime())) {
				Long accountUpdateTime = Long.valueOf(user.getAccountUpdateTime());
				Calendar calendar = Calendar.getInstance();
				//System.out.println(calendar.getTime());
				int month = calendar.get(Calendar.MONTH);
				calendar.set(Calendar.MONTH, (month -1));
				//System.out.println(calendar.getTime());
				if (accountUpdateTime <= calendar.getTimeInMillis()) {
					model.addAttribute("monthEnough", 1);
				} else {
					model.addAttribute("monthEnough", 0);
				}
			} else {
				model.addAttribute("monthEnough", 1);
			}
		}
		return Common.BACKGROUND_PATH_WEB + "/my/accountSet";
 	}
	
	@RequestMapping("/editMobileAccount")
	@ResponseBody
	public Object editMobileAccount(String oldMobile, String mobile, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<>();
		UserEntity oldMobileUser = userService.findByMobile(oldMobile);
		if (oldMobileUser != null) {
			if (oldMobileUser.getId().equals(ShiroAuthenticationManager.getUserId())) {
				UserEntity newMobileUser = userService.findByMobile(mobile);
				if (newMobileUser == null) {
					UserEntity user = new UserEntity();
					user.setId(oldMobileUser.getId());	
					user.setMobile(mobile);
					user.setAccountUpdateTime(System.currentTimeMillis() + "");
					user.setUpdateTime(System.currentTimeMillis() + "");
					user.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
					int result = userService.updateUserOnly(user, true);
					if (result > 0) {
						resultMap.put("success", true);
						resultMap.put("message", "新账号设置成功");
					} else {
						if (StringUtils.isNotBlank(mobile)) {
							try {
								CookieUtil.addCookie(response, GlobalConstant.COOKIE_LOGIN_NAME, URLEncoder.encode(mobile, "utf-8"), CacheConstant.SEVEN_DAY);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
						resultMap.put("success", false);
						resultMap.put("message", "新账号设置失败，请稍候再试");
					}
					
				} else {
					resultMap.put("success", false);
					resultMap.put("message", "新手机对应的用户已经存在");
				}
			} else {
				resultMap.put("success", false);
				resultMap.put("message", "原手机对应的用户不是当前用户");
			}
		} else {
			resultMap.put("success", false);
			resultMap.put("message", "原手机对应的用户不存在");
		}
		return resultMap;
	}
	/**
	 * 个人中心浏览配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/showBrowseSet")
 	public String showBrowseSet(Model model) {
		List<UserSwitch> userSwitchList = userSwitchService.queryUserSwitchByUserId(ShiroAuthenticationManager.getUserId());
		model.addAttribute("userSwitchList", userSwitchList);
		return Common.BACKGROUND_PATH_WEB + "/my/browseSet";
 	}
	/**
	 * 头部个人下拉框中的浏览配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/showHeaderBrowseSet")
 	public String showHeaderBrowseSet(Model model) {
		List<UserSwitch> userSwitchList = userSwitchService.queryUserSwitchByUserId(ShiroAuthenticationManager.getUserId());
		model.addAttribute("userSwitchList", userSwitchList);
		return Common.BACKGROUND_PATH_WEB + "/include/headerBrowseSet";
 	}
	/**
	 * 积分与任务信息展示
	 * @param model
	 * @return
	 */
	@RequestMapping("/showIntegralTask")
 	public String showIntegralTask(Model model) {
		String userId = ShiroAuthenticationManager.getUserId();
		// 用户信息填写完成数量
		Integer userInfoCompleteNum = 0;
		List<UserIntegral> integralList = userIntegralService.findByUserIdAndType(userId, GlobalConstant.INTEGRAL_TYPE_COMPLETE_USERINFO_6);
		if (!CollectionUtils.isEmpty(integralList)) {
			userInfoCompleteNum = 6;
			model.addAttribute("completedUserIntegral", integralList.get(0));
		} else {
			List<String> completeUser = new ArrayList<String>();
			List<String> uncompleteUser = new ArrayList<String>();
			UserEntity user = userService.findUserAndIncrementById(userId);;
			if (user != null) {
				userInfoCompleteNum = userInfoCompleteNum + 1;
				completeUser.add("账号信息");
				if (StringUtils.isNotBlank(user.getNickName()) && !user.getNickName().startsWith("wf_")) {
					userInfoCompleteNum = userInfoCompleteNum + 1;
					completeUser.add("设置昵称");
				} else {
					uncompleteUser.add("设置昵称");
				}
				if (user.getPhoto() != null && user.getPhoto().indexOf("default.png") == -1) {
					userInfoCompleteNum = userInfoCompleteNum + 1;
					completeUser.add("设置头像");
				} else {
					uncompleteUser.add("设置头像");
				}
				if (user.getUserIncrement() != null) {
					UserIncrementEntity increment = user.getUserIncrement();
					if (increment.getBirthday() != null) {
						userInfoCompleteNum = userInfoCompleteNum + 1;
						completeUser.add("设置生日");
					} else {
						uncompleteUser.add("设置生日");
					}
					if (increment.getSex() != null) {
						userInfoCompleteNum = userInfoCompleteNum + 1;
						completeUser.add("设置性别");
					} else {
						uncompleteUser.add("设置性别");
					}
				}
			}
			
			List<UserInterest> interestList  = userInterestService.queryUserInterestByUserId(userId);
			if (!CollectionUtils.isEmpty(interestList)) {
				userInfoCompleteNum = userInfoCompleteNum + 1;
				completeUser.add("添加兴趣");
			} else {
				uncompleteUser.add("添加兴趣");
			}
			model.addAttribute("completeUser", completeUser);
			model.addAttribute("uncompleteUser", uncompleteUser);
		}
		model.addAttribute("userInfoCompleteNum", userInfoCompleteNum);
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		paramMap.put("time", calendar.getTimeInMillis());
		Integer commentNum = videoCommentService.queryUserTodayCommentNumByUserId(paramMap);
		if (commentNum == null) {
			commentNum = 0;
		} else {
			if (commentNum >= 10) {
				
				List<UserIntegral> commentIntegralList = userIntegralService.findByUserIdAndType(userId, GlobalConstant.INTEGRAL_TYPE_COMPLETE_COMMENT_8);
				if (!CollectionUtils.isEmpty(commentIntegralList)) {
					model.addAttribute("commentIntegral", commentIntegralList.get(0));
				}
			}
			
		}
		Integer gradeNum = videoGradeService.queryUserTodayGradeNumByUserId(paramMap);
		if (gradeNum == null) {
			gradeNum = 0;
		} else {
			if (gradeNum >= 10) {
				List<UserIntegral> gradeIntegralList = userIntegralService.findByUserIdAndType(userId, GlobalConstant.INTEGRAL_TYPE_COMPLETE_GRADE_7);
				if (!CollectionUtils.isEmpty(gradeIntegralList)) {
					model.addAttribute("gradeIntegral", gradeIntegralList.get(0));
				}
			}
		}
		model.addAttribute("commentNum", commentNum);
		model.addAttribute("gradeNum", gradeNum);
		return Common.BACKGROUND_PATH_WEB + "/my/integralTask";
 	}
	
	@RequestMapping("/completeTask")
	@ResponseBody
	public Object completeTaskAddIntgral(Integer type) {
		if (type != null) {
			UserIntegral integral = new UserIntegral();
			integral.setUserId(ShiroAuthenticationManager.getUserId());
			integral.setStatus(1);
			integral.setCreateId(ShiroAuthenticationManager.getUserId());
			integral.setIntegral(10);
			integral.setCreateTime(System.currentTimeMillis());
			if (type == 0) {// 用户信息
				integral.setType(GlobalConstant.INTEGRAL_TYPE_COMPLETE_USERINFO_6);
				integral.setIntegralSource("完成用户信息奖励");
			} else if (type == 1) {
				integral.setType(GlobalConstant.INTEGRAL_TYPE_COMPLETE_GRADE_7);
				integral.setIntegralSource("完成每日评分奖励");
			} else if (type == 2) {
				integral.setType(GlobalConstant.INTEGRAL_TYPE_COMPLETE_COMMENT_8);
				integral.setIntegralSource("完成每日评论奖励");
			}
			
			if (integral.getType() != null) {
				List<UserIntegral> integralList = userIntegralService.findByUserIdAndType(ShiroAuthenticationManager.getUserId(), integral.getType());
				if (!CollectionUtils.isEmpty(integralList)) {// 已完成，直接返回
					return 1;
				}
				
				String id = userIntegralService.insertIntegral(integral);
				if (StringUtils.isNotBlank(id)) {
					try {
						userMessageService.addMessageForTask(ShiroAuthenticationManager.getUserId(), id, integral.getType());
					} catch (Exception e) {
						logger.error("完成任务发消息出错", e);
					}
					return 1;
				}
			}
			
		}
		return null;
	}
	
	/**
	 * 展示安全问题
	 * @return
	 */
	@RequestMapping("/showSafeQuestion")
 	public String showSafeQuestion() {
		return Common.BACKGROUND_PATH_WEB + "/my/safeQuestion";
 	}
}
