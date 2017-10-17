package com.webside.crawler.pageprocessor.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

import com.webside.common.GlobalConstant;
import com.webside.crawler.pageprocessor.VideoPageProcessor;
import com.webside.dict.model.DictEntity;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpStationEntity;
import com.webside.util.StringUtils;

@Component
public class WeiBoVideoPageProcessor extends VideoPageProcessor {

	public static final Logger logger = Logger.getLogger(WeiBoVideoPageProcessor.class);

	// UP主页
	private final String space_url = "https://weibo.com/.*";

	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
	private DictEntity station = new DictEntity(GlobalConstant.STATION_TYPE_7);
	private String stationValue = GlobalConstant.STATION_TYPE_7;

	@Override
	public void processData(Page page) {
		if (page.getUrl().regex(space_url).match()) {// 获取up主页
			Map<String, String> map = new HashMap<String, String>();
			Html html = page.getHtml();

			List<String> list = html.regex("<strong.*?/strong>").all();
			String followingCount = list.get(0);
			map.put("followingCount", followingCount.substring(followingCount.indexOf(">") + 1, followingCount.lastIndexOf("<")));// 关注
			String followedCount = list.get(1);
			map.put("followedCount", followedCount.substring(followedCount.indexOf(">") + 1, followedCount.lastIndexOf("<")));// 粉丝
			String contributeCount = list.get(2);
			map.put("contributeCount", contributeCount.substring(contributeCount.indexOf(">") + 1, contributeCount.lastIndexOf("<")));// 微博数量

			// 姓名
			String username = html.regex("<h1 class=\"username\">.*?/h1>").get();
			map.put("username", username.substring(username.indexOf(">") + 1, username.lastIndexOf("<")));
			// 头像
			String upAvatar = html.regex("<p class=\"photo_wrap\">.*?/p>").get();
			map.put("upAvatar", upAvatar.substring(upAvatar.indexOf("src=\"") + 5, upAvatar.lastIndexOf("alt=") - 2));
			// id
			String homeId = "";
			String url = page.getUrl().get();
			if (url.contains("?")) {
				homeId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("?"));
			} else {
				homeId = url.substring(url.lastIndexOf("/") + 1);
			}
			map.put("homeId", homeId);

			// 简介
			String introduction = html.regex("<div class=\"pf_intro\" title=\".*?\">").get();
			map.put("introduction", introduction.substring(introduction.indexOf("title=") + 7, introduction.lastIndexOf(">") - 1));

			// 更新up主
			saveUp(map);
		}
	}

	// 先新增UP主，再新增站点UP主，这里暂时不做UP主的合并逻辑判断。
	private UpEntity saveUp(Map<String, String> map) {
		boolean add = false;
		String homeId = MapUtils.getString(map, "homeId");
		UpStationEntity upStationEntity = upStationService.findByStationAndHomeId(stationValue, homeId);

		UpEntity upEntity = new UpEntity();
		if (upStationEntity == null) {// up站点为空
			add = true;
			upStationEntity = new UpStationEntity();
		} else {
			upEntity = upStationEntity.getUp();
		}
		String createTime = StringUtils.toString((System.currentTimeMillis()));
		String name = quoteReplacement(MapUtils.getString(map, "username"));
		String upAvatar = MapUtils.getString(map, "upAvatar");
		String homeUrl = "https://weibo.com/" + homeId;

		String introduction = MapUtils.getString(map, "introduction");// 简介
		Long upVideoCount = MapUtils.getLong(map, "contributeCount");// 视频数量
		Long upFansCount = MapUtils.getLong(map, "followedCount");// 粉丝数量
		Long upFriendCount = MapUtils.getLong(map, "followingCount");// 关注数量
		// UP主

		try {
			upEntity.setName(name);
			upEntity.setAvatar(upAvatar);
			upEntity.setIntroduction(introduction);
			upEntity.setStatus(status);
			upEntity.setCreateTime(createTime);
			if (add) {
				upService.insert(upEntity);
			} else {
				upService.updateById(upEntity);
			}

			// up主关联站点
			upStationEntity.setUp(upEntity);
			upStationEntity.setHomeId(homeId);
			upStationEntity.setHomeUrl(homeUrl);
			upStationEntity.setName(name);
			upStationEntity.setUpAvatar(upAvatar);
			upStationEntity.setUpIntroduction(introduction);
			upStationEntity.setUpAvatar(upAvatar);
			upStationEntity.setUpVideoCount(upVideoCount);
			upStationEntity.setUpFansCount(upFansCount);
			upStationEntity.setUpFriendCount(upFriendCount);
			upStationEntity.setStation(station);
			upStationEntity.setStatus(status);
			upStationEntity.setCreateTime(createTime);

			if (add) {
				upStationService.insert(upStationEntity);
			} else {
				upStationService.updateById(upStationEntity);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return upEntity;
	}

	private static Request createUpPostRequest(String id) {
		Request request = new Request("https://weibo.com/" + id);
		request.setMethod(HttpConstant.Method.GET);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			request.setExtras(paramMap);
			request.addHeader("Host", "weibo.com");
			request.addHeader("Referer", "https://weibo.com/339809996?is_hot=1");
			request.addHeader("User-Agent", "spider");
			request.addHeader("Content-Type", "text/html; charset=utf-8");
			request.setRequestBody(HttpRequestBody.form(paramMap, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

	public void crawl(Map<String, Object> paramMap) {
		try {
			String id = MapUtils.getString(paramMap, "id");
			String isList = MapUtils.getString(paramMap, "isList");
			if (StringUtils.isNotBlank(id)) {
				Spider.create(this).setModeType("false").addRequest(createUpPostRequest(id)).thread(5).start();
			}

			if ("true".equals(isList)) {

			}
		} catch (Exception e) {
			logger.error("爬虫出错===========================爬虫出错：", e);
		}
	}

}
