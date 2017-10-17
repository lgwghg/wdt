/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.dtgrid.model.Pager;
import com.webside.dtgrid.util.ExportUtils;
import com.webside.exception.AjaxException;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.PageUtil;
import com.webside.util.RandomUtil;
import com.webside.util.crypto.EndecryptUtils;

/**
 * 用户控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */

@Controller
@Scope("prototype")
@RequestMapping("/userCtrl/")
public class UserCtrl extends BaseController {

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 跳转查询用户页面
	 * 
	 * @param Model
	 *            model
	 * @param HttpServletRequest
	 *            request
	 * @throws AjaxException
	 * @author zengxn
	 */
	@RequestMapping("listUI.html")
	public String listUI(Model model, HttpServletRequest request) {
		try {
			PageUtil page = new PageUtil();
			if (request.getParameterMap().containsKey("page")) {
				page.setPageNum(Integer.valueOf(request.getParameter("page")));
				page.setPageSize(Integer.valueOf(request.getParameter("rows")));
				page.setOrderByColumn(request.getParameter("sidx"));
				page.setOrderByType(request.getParameter("sord"));
			}
			model.addAttribute("page", page);

			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("lockedList", dictService.queryListByType(GlobalConstant.DICTTYPE_YES_NO));

			return Common.BACKGROUND_PATH + "/user/list";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * ajax分页动态加载模式
	 * 
	 * @param dtGridPager
	 *            Pager对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.html", method = RequestMethod.POST)
	@ResponseBody
	public Object list(String gridPager, HttpServletResponse response) throws Exception {
		Map<String, Object> parameters = null;
		// 1、映射Pager对象
		Pager pager = JSON.parseObject(gridPager, Pager.class);
		// 2、设置查询参数
		parameters = pager.getParameters();

		// 注册时间
		if (parameters.get("beginCreateTime") != null && StringUtils.isNotBlank(parameters.get("beginCreateTime").toString())) {
			parameters.put("beginCreateTime", DateUtils.parseDate(parameters.get("beginCreateTime")).getTime());
		}
		if (parameters.get("endCreateTime") != null && StringUtils.isNotBlank(parameters.get("endCreateTime").toString())) {
			parameters.put("endCreateTime", DateUtils.parseDate(parameters.get("endCreateTime")).getTime());
		}

		// 3、判断是否是导出操作
		if (pager.getIsExport()) {
			if (pager.getExportAllData()) {
				// 3.1、导出全部数据
				List<UserEntity> list = userService.queryListByPage(parameters);
				ExportUtils.exportAll(response, pager, list);
				return null;
			} else {
				// 3.2、导出当前页数据
				ExportUtils.export(response, pager);
				return null;
			}
		} else {
			// 设置分页，page里面包含了分页信息
			Page<Object> page = PageHelper.startPage(pager.getNowPage(), pager.getPageSize(), "createTime DESC");
			List<UserEntity> list = userService.queryListByPage(parameters);
			parameters.clear();
			parameters.put("isSuccess", Boolean.TRUE);
			parameters.put("nowPage", pager.getNowPage());
			parameters.put("pageSize", pager.getPageSize());
			parameters.put("pageCount", page.getPages());
			parameters.put("recordCount", page.getTotal());
			parameters.put("startRecord", page.getStartRow());
			// 列表展示数据
			parameters.put("exhibitDatas", list);
			return parameters;
		}
	}

	/**
	 * 跳转新增用户页面
	 * 
	 * @param Model
	 *            model
	 * @throws Exception
	 * @author zengxn
	 */
	@RequestMapping("addUI.html")
	public String addUI(Model model) {
		try {
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("lockedList", dictService.queryListByType(GlobalConstant.DICTTYPE_YES_NO));
			return Common.BACKGROUND_PATH + "/user/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}

	}

	/**
	 * 新增用户
	 * 
	 * @param AttributeEntity
	 * @throws AjaxException
	 * @author zengxn
	 */
	@RequestMapping("add.html")
	@ResponseBody
	public Object add(UserEntity userEntity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 检索用户昵称是否存在
			UserEntity userNick = userService.findByNickName(userEntity.getNickName());
			if (userNick == null) {
				// 检索手机号码是否存在
				UserEntity userMobile = userService.findByMobile(userEntity.getMobile());
				if (userMobile == null) {
					userEntity.setId(IdGen.uuid());
					// 加密用户输入的密码，得到密码和加密盐，保存到数据库
					UserEntity user = EndecryptUtils.md5Password(userEntity.getId(), userEntity.getPassword(), 2);
					// 设置添加用户的密码和加密盐
					userEntity.setPassword(user.getPassword());
					userEntity.setCredentialsSalt(user.getCredentialsSalt());
					// 设置创建者
					userEntity.setCreateUser(ShiroAuthenticationManager.getUserEntity());
					int result = userService.insert(userEntity);
					if (result == 1) {
						map.put("success", Boolean.TRUE);
						map.put("message", "添加成功");
					} else {
						map.put("success", Boolean.FALSE);
						map.put("message", "添加失败");
					}
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "添加失败,手机号已存在");
				}
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "添加失败,昵称已存在");
			}
		} catch (ServiceException e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 跳转编辑用户页面
	 * 
	 * @param Model
	 *            model
	 * @throws AjaxException
	 * @author zengxn
	 */
	@RequestMapping("editUI.html")
	public String editUI(Model model, HttpServletRequest request, String id) {
		try {
			UserEntity userEntity = userService.findById(id);
			PageUtil page = new PageUtil();
			page.setPageNum(Integer.valueOf(request.getParameter("page")));
			page.setPageSize(Integer.valueOf(request.getParameter("rows")));
			page.setOrderByColumn(request.getParameter("sidx"));
			page.setOrderByType(request.getParameter("sord"));

			model.addAttribute("page", page);
			model.addAttribute("entity", userEntity);
			// 查询选项数据
			model.addAttribute("statusList", dictService.queryListByType(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS));
			model.addAttribute("lockedList", dictService.queryListByType(GlobalConstant.DICTTYPE_YES_NO));
			return Common.BACKGROUND_PATH + "/user/form";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改用户
	 * 
	 * @param entity
	 * @throws AjaxException
	 * @author zengxn
	 */
	@RequestMapping("edit.html")
	@ResponseBody
	public Object update(UserEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			entity.setUpdateUser(ShiroAuthenticationManager.getUserEntity());
			int result = userService.updateById(entity, true);
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "编辑失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 锁定用户
	 * 
	 * @param entity
	 * @throws AjaxException
	 * @author zengxn
	 */
	@RequestMapping("lock.html")
	@ResponseBody
	public Object lock(UserEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DictEntity dict = new DictEntity();
			dict.setValue(GlobalConstant.DICTTYPE_YES_NO_1);
			entity.setLocked(dict);
			int result = userService.updateUserOnly(entity, false);
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("message", "账户已锁定");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "账户锁定失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 解锁用户
	 * 
	 * @param entity
	 * @throws AjaxException
	 * @author zengxn
	 */
	@RequestMapping("unlock.html")
	@ResponseBody
	public Object unlock(UserEntity entity) throws AjaxException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DictEntity dict = new DictEntity();
			dict.setValue(GlobalConstant.DICTTYPE_YES_NO_0);
			entity.setLocked(dict);
			int result = userService.updateUserOnly(entity, false);
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("message", "账户已解锁");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "账户解锁失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

	/**
	 * 重置密码
	 * 
	 * @param entity
	 * @throws AjaxException
	 * @author zengxn
	 */
	@RequestMapping("resetPassword.html")
	@ResponseBody
	public Object resetPassword(UserEntity entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 生成随机密码
			String password = RandomUtil.generateString(6);
			// 加密用户输入的密码，得到密码和加密盐，保存到数据库
			UserEntity user = EndecryptUtils.md5Password(entity.getId(), password, 2);
			// 设置添加用户的密码和加密盐
			entity.setPassword(user.getPassword());
			entity.setCredentialsSalt(user.getCredentialsSalt());

			int cnt = userService.updatePassword(entity, password);
			if (cnt > 0) {
				result.put("success", Boolean.TRUE);
				result.put("message", "密码重置成功");
			} else {
				result.put("success", Boolean.FALSE);
				result.put("message", "密码重置失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return result;
	}

	/**
	 * 跳转个人信息页面
	 * 
	 * @author zengxn
	 */
	@RequestMapping("infoUI.html")
	public String infoUI(Model model, String id) {
		try {
			UserEntity userEntity = userService.findById(id);
			model.addAttribute("userEntity", userEntity);
			return Common.BACKGROUND_PATH + "/user/info";
		} catch (Exception e) {
			throw new AjaxException(e);
		}
	}

	/**
	 * 修改个人信息
	 * 
	 * @author zengxn
	 */
	@RequestMapping("info.html")
	@ResponseBody
	public Object info(UserEntity userEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = userService.updateById(userEntity, true);
			if (result == 1) {
				map.put("success", Boolean.TRUE);
				map.put("message", "编辑成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("message", "编辑失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}
		return map;
	}

}
