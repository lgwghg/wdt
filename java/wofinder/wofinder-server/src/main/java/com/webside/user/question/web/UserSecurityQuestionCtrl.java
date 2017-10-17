package com.webside.user.question.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.exception.AjaxException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.question.model.UserSecurityQuestion;
import com.webside.user.question.service.IUserSecurityQuestionService;
import com.webside.util.IdGen;

/**
 * 个人中心安全问题
 * 
 * @author tianguifang
 * @date 2017-06-27
 */
@Controller
@Scope("prototype")
@RequestMapping("/my")
public class UserSecurityQuestionCtrl {
	@Autowired
	private IUserSecurityQuestionService userSecurityQuestionService;
	@Autowired
	private DictService dictService;

	@RequestMapping(value = "/showQuestion", method = RequestMethod.POST)
	@ResponseBody
	public Object showQuestion() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserSecurityQuestion> questionList = userSecurityQuestionService.queryQuestionByUserId(ShiroAuthenticationManager.getUserId());
		if (!CollectionUtils.isEmpty(questionList)) {
			map.put("question", questionList.get(0));
		}
		map.put("questionList", dictService.queryListByType(GlobalConstant.USER_SECURITY_QUESTION));

		return map;
	}

	/**
	 * 新增编辑安全问题
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping(value = "/editQuestion", method = RequestMethod.POST)
	@ResponseBody
	public Object editQuestion(UserSecurityQuestion question) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = 0;
			if (question != null) {
				if (StringUtils.isNotBlank(question.getId())) {
					question.setUpdateTime(System.currentTimeMillis());
					result = userSecurityQuestionService.updateById(question);
				} else {
					question.setId(IdGen.uuid());// 设置ID 生成 UUID
					question.setCreateTime(System.currentTimeMillis());
					question.setUserId(ShiroAuthenticationManager.getUserId());
					result = userSecurityQuestionService.insert(question);
				}
			}

			if (result > 0) {
				map.put("success", Boolean.TRUE);
				map.put("data", null);
				map.put("message", "添加成功");
			} else {
				map.put("success", Boolean.FALSE);
				map.put("data", null);
				map.put("message", "添加失败");
			}
		} catch (Exception e) {
			throw new AjaxException(e);
		}

		return map;
	}
}
