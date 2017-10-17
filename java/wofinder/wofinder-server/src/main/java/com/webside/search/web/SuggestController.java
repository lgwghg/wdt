package com.webside.search.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.data.solr.common.Page;
import com.webside.data.solr.video.model.VideoSuggest;
import com.webside.data.solr.video.service.VideoSuggestSolrSerivce;
import com.webside.util.StringUtils;

/**
 * 自动补全控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 18:58:30
 */

@Controller
@RequestMapping("/su")
public class SuggestController extends BaseController {

	/**
	 * 视频suggest搜索 service定义
	 */
	@Autowired
	private VideoSuggestSolrSerivce videoSuggestSolrSerivce;

	/**
	 * 自动补全
	 * 
	 * @author zengxn
	 */
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object suggest(String wd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", Boolean.TRUE);
		try {
			if (StringUtils.isBlank(wd)) {
				return map;
			}

			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("search", escapeQueryChars(wd));

			Page<VideoSuggest> videoSuggestPage = new Page<VideoSuggest>();
			videoSuggestPage.setConditions(conditions);
			videoSuggestPage.setCurrent(1);
			videoSuggestSolrSerivce.queryListByPage(videoSuggestPage);

			List<VideoSuggest> videoSuggestDatas = videoSuggestPage.getDatas();
			List<String> titleList = null;
			if (!CollectionUtils.isEmpty(videoSuggestDatas)) {
				titleList = new ArrayList<String>(videoSuggestDatas.size());
				for (VideoSuggest videoSuggest : videoSuggestDatas) {
					titleList.add(videoSuggest.getTitle());
				}
				map.put("titleList", titleList);
			}
		} catch (Exception e) {
			map.put("succes", Boolean.FALSE);
			logger.error("自动补全" + wd + "出现错误", e);
		}
		return map;
	}

	//搜索关键字剔除特殊字符
	private static String escapeQueryChars(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// These characters are part of the query syntax and must be escaped
			if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~' || c == '*' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/' || Character.isWhitespace(c)) {
				sb.append('\\');
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
