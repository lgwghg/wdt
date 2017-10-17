/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.submit.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.submit.model.SubmitKeywordEntity;
import com.webside.submit.model.SubmitUrlEntity;
import com.webside.submit.service.SubmitKeywordService;
import com.webside.user.model.UserEntity;
import com.webside.util.StringUtils;

/**
 * 提交搜索关键字控制管理层
 *
 * @author zengxn
 * @date 2017-06-13 17:49:37
 */

@Controller
@RequestMapping("/submitKeyword/")
public class SubmitKeywordController extends BaseController {
	/**
	 * 提交搜索关键字 Service定义
	 */
	@Autowired
	private SubmitKeywordService submitKeywordService;

	/**
	 * 提交搜索关键字
	 *
	 * @author zengxn
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(String keyword, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(keyword)) {
				map.put("success", Boolean.FALSE);
				map.put("message", "请填写关键词！");
			} else if (!request.getParameterMap().containsKey("submitUrls[]") || request.getParameterValues("submitUrls[]").length < 1) {
				map.put("success", Boolean.FALSE);
				map.put("message", "请填写关键词url！");
			} else {
				UserEntity createUser = ShiroAuthenticationManager.getUserEntity();
				SubmitKeywordEntity submitKeyword = new SubmitKeywordEntity();
				submitKeyword.setKeyword(keyword);
				submitKeyword.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
				if (createUser != null && StringUtils.isNotBlank(createUser.getId())) {
					submitKeyword.setCreateUser(createUser);
				}
				List<SubmitUrlEntity> submitUrlList = new ArrayList<SubmitUrlEntity>();
				String[] submitUrls = request.getParameterValues("submitUrls[]");
				for (String url : submitUrls) {
					SubmitUrlEntity urlEntity = new SubmitUrlEntity();
					urlEntity.setUrl(url.trim());
					submitUrlList.add(urlEntity);
				}
				submitKeyword.setSubmitUrlList(submitUrlList);
				int result = submitKeywordService.insert(submitKeyword);

				if (result > 0) {
					map.put("success", Boolean.TRUE);
					map.put("message", "提交成功");
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "提交失败");
				}
			}
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", GlobalConstant.RETURN_ERROR_MESSAGE);
		}
		return map;
	}
}
