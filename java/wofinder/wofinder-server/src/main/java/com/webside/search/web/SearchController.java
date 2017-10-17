package com.webside.search.web;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.alibaba.fastjson.JSONObject;
import com.webside.base.basecontroller.BaseController;
import com.webside.common.CacheConstant;
import com.webside.common.Common;
import com.webside.common.GlobalConstant;
import com.webside.data.solr.common.Page;
import com.webside.data.solr.common.SolrUtil;
import com.webside.data.solr.video.model.Video;
import com.webside.data.solr.video.model.VideoAlbum;
import com.webside.data.solr.video.service.VideoAlbumSolrSerivce;
import com.webside.data.solr.video.service.VideoSolrSerivce;
import com.webside.util.CookieUtil;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoEntity;
import com.webside.video.service.VideoAlbumService;
import com.webside.video.service.VideoService;
import com.webside.video.vo.VideoAlbumVo;
import com.webside.video.vo.VideoVo;

/**
 * 搜索控制管理层
 *
 * @author zengxn
 * @date 2017-04-20 18:58:30
 */

@Controller
@RequestMapping("/s")
public class SearchController extends BaseController {

	/**
	 * 人物 Service定义
	 */
	/*@Autowired
	private UpService upService;*/

	/**
	 * 人物名称 Service定义
	 */
	/*@Autowired
	private UpNameSolrSerivce upNameService;*/

	/**
	 * 视频 Service定义
	 */
	@Autowired
	private VideoService videoService;

	/**
	 * 视频搜索 service定义
	 */
	@Autowired
	private VideoSolrSerivce videoSolrSerivce;

	/**
	 * 人物关系 Service定义
	 */
	/*@Autowired
	private IUpRelationService upRelationService;*/

	/**
	 * 视频专辑搜索 Service定义
	 */
	@Autowired
	private VideoAlbumSolrSerivce videoAlbumSolrSerivce;

	/**
	 * 视频专辑 Service定义
	 */
	@Autowired
	private VideoAlbumService videoAlbumService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(Model model, HttpServletRequest request, HttpServletResponse response, String current) {
		String wd = getWd(request);
		if (StringUtils.isBlank(wd)) {
			return "redirect:/";
		}
		return searchByForward(model, request, response, current);
	}

	/**
	 * 搜索，页面跳转方式
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String searchByForward(Model model, HttpServletRequest request, HttpServletResponse response, String current) {
		String wd = getWd(request);
		try {
			// 记录cookies
			setCookie(request, response, wd);
			model.addAttribute("wd", wd);
			model.addAttribute("current", current);
			JSONObject search = search(wd, 0, true);
			long count = search.getLongValue("count");
			model.addAttribute("count", count);
			return Common.BACKGROUND_PATH_WEB + "/search";
		} catch (Exception e) {
			logger.error("页面跳转方式搜索" + wd + "出现错误", e);
			return Common.BACKGROUND_PATH_WEB + "/developing";
		}
	}

	/**
	 * 获取wd参数
	 */
	private String getWd(HttpServletRequest request) {
		String wd = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (wd.startsWith("/s/")) {
			if (wd.endsWith("?current")) {
				wd = wd.substring("/s/".length(), wd.indexOf("?current"));
			} else {
				wd = wd.substring("/s/".length());
			}
			return wd;
		}
		return null;
	}

	/**
	 * 搜索，ajax请求方式
	 * 
	 * @author zengxn
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Object searchByAjax(String wd, String current) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 当前页码
			int currentNo = 1;
			if (StringUtils.isNotBlank(current) && StringUtils.isNumeric(current)) {
				currentNo = StringUtils.toDouble(current).intValue();
			}
			JSONObject search = search(wd, currentNo, false);

			if (search.containsKey("videoVoList")) {
				map.put("videoVoList", search.get("videoVoList"));
			}
			if (search.containsKey("videoAlbumVoList")) {
				map.put("videoAlbumVoList", search.get("videoAlbumVoList"));
			}
			long count = search.getLongValue("count");
			int cur = search.getIntValue("current");
			long totalPages = search.getLongValue("totalPages");
			// 计算分页
			long startPage = 1;
			long endPage = 7;
			if (cur > 3) {
				if ((totalPages - cur) >= 3) {
					startPage = cur - 3;
					endPage = cur + 3;
				} else {
					startPage = totalPages - 5;
					endPage = totalPages;
				}
			}
			if (endPage > totalPages) {
				endPage = totalPages;
			}
			if (startPage < 1) {
				startPage = 1;
			}
			map.put("count", count);
			map.put("current", cur);
			map.put("totalPages", totalPages);
			map.put("startPage", startPage);
			map.put("endPage", endPage);
			map.put("isSuccess", Boolean.TRUE);
		} catch (Exception e) {
			map.put("isSuccess", Boolean.FALSE);
			logger.error("ajax请求方式搜索" + wd + "出现错误", e);
		}
		return map;
	}

	private JSONObject search(String wd, int currentNo, boolean onlyCount) {

		JSONObject result = new JSONObject();

		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("search", escapeQueryChars(wd));

		// 总数
		long count = 0;

		// 1、搜索人物名称（人物暂时不需要）
		/*Page<UpName> upNamePage = new Page<UpName>();
		upNamePage.setConditions(conditions);
		UpNameSerivce.queryListByPage(upNamePage);
		// 累加总数
		count += upNamePage.getCount();*/

		// 2、搜索视频专辑
		Page<VideoAlbum> videoAlbumPage = new Page<VideoAlbum>();
		videoAlbumPage.setConditions(conditions);
		videoAlbumSolrSerivce.queryListByPage(videoAlbumPage);
		// 累加总数
		count += videoAlbumPage.getCount();

		// 3、搜索视频
		Page<Video> videoPage = new Page<Video>();
		videoPage.setConditions(conditions);
		if (!onlyCount) {
			videoPage.setCurrent(currentNo);
		}
		videoSolrSerivce.queryListByPage(videoPage);
		// 累加总数
		count += videoPage.getCount();

		if (!onlyCount) {
			// 1、取得solr中的视频集合
			List<Video> videoDatas = videoPage.getDatas();
			Map<String, String> videoMap = new HashMap<String, String>();
			if (videoDatas != null && videoDatas.size() > 0) {
				List<String> videoIds = new ArrayList<String>(videoDatas.size());
				for (Video video : videoDatas) {
					videoIds.add(video.getId());
					videoMap.put(video.getId(), video.getTitle());
				}
				List<VideoVo> videoVoList = videoService.queryListVideoVOBathchByVideoId(videoIds);
				for (VideoVo videoVo : videoVoList) {
					// 获取高亮标题
					if (videoMap.containsKey(videoVo.getId())) {
						List<String> list = getTokenStream(wd);
						StringBuffer title = new StringBuffer(videoMap.get(videoVo.getId()));
						for (String string : list) {
							title = new StringBuffer(enhance(title.toString(), string, SolrUtil.HIGHLIGHT_SIMPLE_PRE, SolrUtil.HIGHLIGHT_SIMPLE_POST));
						}
						videoVo.setSearchTitle(title.toString().replaceAll(SolrUtil.HIGHLIGHT_SIMPLE_PRE, "<font style='color:#db4437'>").replaceAll(SolrUtil.HIGHLIGHT_SIMPLE_POST, "</font>"));
					}
				}
				if (!CollectionUtils.isEmpty(videoVoList)) {
					result.put("videoVoList", videoVoList);
				}
			}

			// 如果是第一页，获取视频合集（人物暂时不需要）
			if (currentNo <= 1) {
				/*List<String> upNameIds = null;
				// 2、取得solr中的人物名称集合
				List<UpName> upNameDatas = upNamePage.getDatas();
				if (upNameDatas != null && upNameDatas.size() > 0) {
					upNameIds = new ArrayList<String>(upNameDatas.size());
					for (UpName upName : upNameDatas) {
						upNameIds.add(upName.getId());
					}
				}
				// 第一页需要进行人物、视频专辑的搜索（专辑暂时不需要）
				if (!CollectionUtils.isEmpty(upNameIds)) {
					model.addAttribute("videoUpList", upService.queryListVideoUpVOBathchByUpNameId(upNameIds));
				}
			
				// 优先显示相关人物，后显示热门视频
				// 1、相关人物（暂时不需要）
				List<UpRelation> upRelationList = null;
				if (!CollectionUtils.isEmpty(upNameIds)) {
					PageHelper.startPage(0, 0, "type");
					// 根据人物名称id集合查询出人物名称集合
					List<UpNameEntity> upNameEntityList = upNameSolrSerivce.queryListBatchById(upNameIds);
					if (upNameEntityList == null || upNameEntityList.isEmpty()) {
						return null;
					}
					Set<String> upIdsSet = new HashSet<String>();
					for (UpNameEntity upNameEntity : upNameEntityList) {
						upIdsSet.add(upNameEntity.getUp().getId());
					}
					List<String> upIds = new ArrayList<String>(upIdsSet.size());
					upIds.addAll(upIdsSet);
				
					upRelationList = new ArrayList<UpRelation>();
					for (String upId : upIds) {
						List<UpRelation> list = upRelationService.queryUpRelationListByUpIdAndRelationId(upId, null);
						if (!CollectionUtils.isEmpty(list)) {
							upRelationList.addAll(list);
						}
					}
					if (!CollectionUtils.isEmpty(upRelationList)) {
						model.addAttribute("rightUpRelationList", upRelationList);
					}
				}*/
				// 3、取得solr中的视频专辑集合
				List<VideoAlbum> videoAlbumDatas = videoAlbumPage.getDatas();
				Map<String, String> videoAlbumMap = new HashMap<String, String>();
				if (videoAlbumDatas != null && videoAlbumDatas.size() > 0) {
					List<String> videoAlbumIds = new ArrayList<String>(videoAlbumDatas.size());
					for (VideoAlbum videoAlbum : videoAlbumDatas) {
						videoAlbumIds.add(videoAlbum.getId());
						videoAlbumMap.put(videoAlbum.getId(), videoAlbum.getName());
					}
					List<VideoAlbumVo> videoAlbumVoList = videoAlbumService.queryListVideoAlbumVoBatchById(videoAlbumIds);
					for (VideoAlbumVo videoAlbumVo : videoAlbumVoList) {
						// 获取高亮标题
						if (videoAlbumMap.containsKey(videoAlbumVo.getId())) {
							List<String> list = getTokenStream(wd);
							StringBuffer name = new StringBuffer(videoAlbumMap.get(videoAlbumVo.getId()));
							for (String string : list) {
								name = new StringBuffer(enhance(name.toString(), string, SolrUtil.HIGHLIGHT_SIMPLE_PRE, SolrUtil.HIGHLIGHT_SIMPLE_POST));
							}
							videoAlbumVo.setSearchName(name.toString().replaceAll(SolrUtil.HIGHLIGHT_SIMPLE_PRE, "<font style='color:#db4437'>").replaceAll(SolrUtil.HIGHLIGHT_SIMPLE_POST, "</font>"));
						}
					}
					if (!CollectionUtils.isEmpty(videoAlbumVoList)) {
						result.put("videoAlbumVoList", videoAlbumVoList);
					}
				}
			}
			result.put("current", videoPage.getCurrent());
			result.put("totalPages", videoPage.getTotalPages());
		}

		result.put("count", count);

		return result;
	}

	/**
	 * 热门视频
	 */
	@RequestMapping(value = "/hotVideo", method = RequestMethod.POST)
	@ResponseBody
	public Object hotVideo() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<VideoEntity> videoList = videoService.queryHotVideo(6);
			if (!CollectionUtils.isEmpty(videoList)) {
				map.put("rightVideoList", videoList);
			}
			map.put("isSuccess", Boolean.TRUE);
		} catch (Exception e) {
			map.put("isSuccess", Boolean.FALSE);
			logger.error("ajax请求方式搜索热门视频出现错误", e);
		}
		return map;
	}

	/**
	 * 替换关键字
	 */
	public String enhance(String content, String keyword, String prefix, String suffix) {
		JSONObject checkValue = checkValue(content, keyword);
		if (!checkValue.getBooleanValue("checkValue"))
			return content;
		StringTokenizer tokenizer = new StringTokenizer(checkValue.getString("lowKeyword"));
		Set<String> tokens = new HashSet<String>();
		while (tokenizer.hasMoreTokens()) {
			tokens.add(tokenizer.nextToken());
		}
		StringBuffer result = null;
		for (String token : tokens) {
			result = new StringBuffer();
			String tmpResult = doEnhance(checkValue, result, 0, token);
			content = tmpResult;
			String lowContent = tmpResult.toLowerCase();
			checkValue.put("content", content);
			checkValue.put("lowContent", lowContent);
		}
		return result.toString().replace(SolrUtil.HIGHLIGHT_SIMPLE_PRE, prefix).replace(SolrUtil.HIGHLIGHT_SIMPLE_POST, suffix);
	}

	private JSONObject checkValue(String content, String keyword) {
		JSONObject jsonObject = new JSONObject();
		if (content != null && !"".equals(content.trim()) && keyword != null && !"".equals(keyword.trim())) {
			jsonObject.put("checkValue", true);
			jsonObject.put("content", content);
			jsonObject.put("lowContent", content.toLowerCase());
			jsonObject.put("lowKeyword", keyword.toLowerCase());
		} else {
			jsonObject.put("checkValue", false);
		}
		return jsonObject;
	}

	private String doEnhance(JSONObject jsonObject, StringBuffer result, int fromIndex, String token) {
		String lowContent = jsonObject.getString("lowContent");
		String content = jsonObject.getString("content");
		int index = lowContent.indexOf(token, fromIndex);

		if (index != -1) {
			result.append(content.substring(fromIndex, index));
			result.append(SolrUtil.HIGHLIGHT_SIMPLE_PRE).append(content.substring(index, index + token.length())).append(SolrUtil.HIGHLIGHT_SIMPLE_POST);
			return doEnhance(jsonObject, result, index + token.length(), token);
		}
		result.append(content.substring(fromIndex, content.length()));
		return result.toString();
	}

	/**
	 * 获取字符串的分词
	 */
	public List<String> getTokenStream(String text) {
		try {
			Set<String> set = new HashSet<String>();
			// 创建分词对象
			Analyzer anal = new IKAnalyzer(true);
			StringReader reader = new StringReader(text);
			// 分词
			TokenStream ts = anal.tokenStream("", reader);
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
			// 遍历分词数据
			while (ts.incrementToken()) {
				set.add(term.toString());
			}
			reader.close();
			return new ArrayList<String>(set);
		} catch (Exception e) {
			return null;
		}
	}

	// 搜索关键字剔除特殊字符
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

	// 设置cookies
	private void setCookie(HttpServletRequest request, HttpServletResponse response, String wd) throws UnsupportedEncodingException {
		String oldCookie = CookieUtil.findCookieByName(request, GlobalConstant.SEARCH_HISTORY_COOKIE);
		List<String> newCookieList = new ArrayList<String>();
		if (StringUtils.isNotBlank(oldCookie)) {
			String[] oldCookieArray = oldCookie.split("##--##");
			for (String cookie : oldCookieArray) {
				if (!URLDecoder.decode(cookie, "utf-8").equals(wd)) {
					newCookieList.add(cookie);
				}
			}
		}
		StringBuffer newCookieStr = new StringBuffer();
		String encode = URLEncoder.encode(wd, "utf-8");
		newCookieList.add(encode);
		int i = 0;
		if (newCookieList.size() > 10) {
			i = newCookieList.size() - 10;
		}

		for (int j = i; j < newCookieList.size(); j++) {
			newCookieStr.append(newCookieList.get(j));
			if (i < newCookieList.size() - 1) {
				newCookieStr.append("##--##");
			}
		}
		Cookie cookie = new Cookie(GlobalConstant.SEARCH_HISTORY_COOKIE, newCookieStr.toString());
		cookie.setSecure(false);
		CookieUtil.addCookie(response, cookie, CacheConstant.THIRTY_DAY);
	}
}