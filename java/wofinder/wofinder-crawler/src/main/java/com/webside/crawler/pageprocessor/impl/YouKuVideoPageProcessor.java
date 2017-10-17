package com.webside.crawler.pageprocessor.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.common.GlobalConstant;
import com.webside.crawler.pageprocessor.VideoPageProcessor;
import com.webside.dict.model.DictEntity;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpStationEntity;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class YouKuVideoPageProcessor extends VideoPageProcessor {

	public static final Logger logger = Logger.getLogger(YouKuVideoPageProcessor.class);

	// 视频列表
	private final String list_url = "http://game.youku.com/index/(dota|lol|)/_page.*";
	// 查询视频列表                                                                      
	private final String q_list_url = "http://www.soku.com/search_video/q_.*_orderby_2_cateid_99_limitdate_0\\?maxVid=(\\w+==|\\w*)&minVid=(\\w+==|\\w*)&page=\\d+&_=\\d+";
	// 视频详情
	private final String video_url = "http://v.youku.com/v_show/id_(\\w+|\\w+==).html.*";
	// 视频data
	private final String data_url = "https://ups.youku.com/ups/get.json\\?category=.*&ccode=0401&client_ip=192.168.1.1&utid=\\w+&imageCode=.*&vid=(\\w+|\\w+==)&client_ts=\\d+";
	// 视频播放量
	private final String count_url = "http://v.youku.com/action/getVideoPlayInfo\\?callback=tuijsonp4&vid=(\\w+|\\w+==)";
	// up主页面
	private final String up_url = "http://i.youku.com/i/.*/videos";
	private final String up_json_url = "http://v.youku.com/action/sub\\?beta&callback=jQuery&vid=\\d+&ownerid=\\d+";
	// 评论列表
	private final String comment_url = "http://p.comments.youku.com/ycp/comment/pc/commentList\\?app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&currentPage=\\d+&vid=\\w+&objectId=\\d+&sign=\\w+&time=\\d+&_=\\d+";
	private final String first_comment_url = "http://p.comments.youku.com/ycp/comment/pc/commentList\\?isFirst=true&app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&currentPage=\\d+&vid=\\w+&objectId=\\d+&sign=\\w+&time=\\d+";
	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
	private DictEntity station = new DictEntity(GlobalConstant.STATION_TYPE_3);
	private String stationValue = GlobalConstant.STATION_TYPE_3;
	private String sign = "4b139762dc149db085cb788d82ea2e4a";
	private String time = "1502103155";
	private String imageSize = "05410";

	@Override
	public void processData(Page page) {
		if (page.getUrl().regex(q_list_url).match()) {// 获取视频列表
			String url = page.getUrl().get();
			logger.info("爬虫info查看信息=========进入视频列表：" + url);
			Html html = page.getHtml();
			List<String> list = html.$(".v-link a", "_log_vid").all();
			if (list != null && !list.isEmpty()) {
				List<String> imageList = html.$(".v-thumb img", "src").all();
				String nowPage = url.substring(url.indexOf("page=") + 5, url.indexOf("&_="));
				String category = url.substring(url.indexOf("q_") + 2, url.indexOf("_orderby"));
				String nextPage = html.$(".next a", "href").get();

				String maxVid = url.substring(url.indexOf("maxVid=") + 7, url.indexOf("&minVid"));
				String minVid = url.substring(url.indexOf("minVid=") + 7, url.indexOf("&page"));

				// 本页最小值（第一个插入的值）
				String thisMinVid = list.get(0);
				// 本页最大值（最后插入的值）
				String thisMaxVid = list.get(list.size() - 1);
				int pageCount = StringUtils.toInteger(nowPage);

				if (StringUtils.isBlank(nextPage)) {// 最后一页
					pageCount = pageCount - 1;
					maxVid = thisMaxVid;

					addUrlToSpider(list, imageList, category, page);
				} else {
					// 最小值为空， 强制跳转上一页
					if (StringUtils.isBlank(minVid)) {
						pageCount = pageCount + 1;
						minVid = thisMinVid;
					} else {
						VideoStationEntity entity = videoStationService.findByVideoStationId(null, stationValue, null, thisMinVid);
						// 最小值没数据
						if (entity == null || StringUtils.isBlank(entity.getId())) {
							// 判断 最小值 和 上一页传入过来的最小值是否一致
							if (thisMinVid.equals(minVid)) {
								pageCount = pageCount - 1;
								maxVid = thisMaxVid;

								addUrlToSpider(list, imageList, category, page);
							} else {
								pageCount = pageCount + 1;
								minVid = thisMinVid;
							}
						} else {
							pageCount = pageCount - 1;
							maxVid = thisMaxVid;
							// 上一页最大值 不一致，爬取数据
							if (!thisMaxVid.equals(maxVid)) {
								addUrlToSpider(list, imageList, category, page);
							}
						}
					}
				}

				if (pageCount > 0) {
					String str = "maxVid=" + maxVid + "&minVid=" + minVid + "&page=" + pageCount + "&_=" + System.currentTimeMillis();
					String pageUrl = url.replaceAll("maxVid=(\\w+==|\\w*)&minVid=(\\w+==|\\w*)&page=\\d+&_=\\d+", str);
					page.addTargetRequest(pageUrl);
				}
			}
		} else if (page.getUrl().regex(list_url).match()) {// 获取视频详情页
			String url = page.getUrl().get();
			logger.info("爬虫info查看信息=========进入视频列表：" + url);
			Html html = page.getHtml();
			List<String> list = html.css(".v-link a").all();
			if (list != null && !list.isEmpty()) {
				List<String> imageList = html.$(".v-thumb img", "src").all();
				String category = url.substring(0, url.lastIndexOf("/"));
				category = category.substring(category.lastIndexOf("/") + 1);

				String maxVid = url.substring(url.indexOf("maxVid=") + 7, url.indexOf("&minVid"));
				String minVid = url.substring(url.indexOf("minVid=") + 7, url.indexOf("&_"));
				url = url.substring(0, url.indexOf(".html") + 5);
				String nowPage = url.substring(url.lastIndexOf("_") + 1, url.indexOf(".html"));
				int pageCount = StringUtils.toInteger(nowPage);

				// 本页最小值（第一个插入的值）
				String thisMinVid = list.get(0);
				if (thisMinVid.contains("id_")) {
					thisMinVid = thisMinVid.substring(thisMinVid.indexOf("/id_") + 4, thisMinVid.indexOf(".html"));
				}
				// 本页最大值（最后插入的值）
				String thisMaxVid = list.get(list.size() - 1);
				if (thisMaxVid.contains("id_")) {
					thisMaxVid = thisMaxVid.substring(thisMaxVid.indexOf("/id_") + 4, thisMaxVid.indexOf(".html"));
				}

				String nextPage = html.$(".next a", "href").get();
				if (StringUtils.isBlank(nextPage)) {// 不存在下一页，就是最后一页
					pageCount = pageCount - 1;
					maxVid = thisMaxVid;

					addUrlToSpider(list, imageList, category, page);
				} else {
					// 最小值为空， 强制跳转上一页
					if (StringUtils.isBlank(minVid)) {
						pageCount = pageCount + 1;
						minVid = thisMinVid;
					} else {
						VideoStationEntity entity = videoStationService.findByVideoStationId(null, stationValue, null, thisMinVid);
						// 最小值没数据
						if (entity == null || StringUtils.isBlank(entity.getId())) {
							// 判断 最小值 和 上一页传入过来的最小值是否一致
							if (thisMinVid.equals(minVid)) {
								pageCount = pageCount - 1;
								maxVid = thisMaxVid;

								addUrlToSpider(list, imageList, category, page);
							} else {
								pageCount = pageCount + 1;
								minVid = thisMinVid;
							}
						} else {
							pageCount = pageCount - 1;
							maxVid = thisMaxVid;
							// 上一页最大值 不一致，爬取数据
							if (!thisMaxVid.equals(maxVid)) {
								addUrlToSpider(list, imageList, category, page);
							}
						}
					}
				}

				if (pageCount > 0) {
					String str = "?maxVid=" + maxVid + "&minVid=" + minVid + "&_=" + System.currentTimeMillis();
					url = url.replaceAll("_\\d+.html", "_" + pageCount + ".html");
					String pageUrl = url + str;
					page.addTargetRequest(pageUrl);
				}
			}
		} else if (page.getUrl().regex(video_url).match()) {// 获取视频详情页
			String url = page.getUrl().toString();
			String category = url.substring(url.indexOf("category=") + 9, url.indexOf("&imageCode"));
			String imageCode = url.substring(url.indexOf("imageCode=") + 10);
			String logo = "https://r1.ykimg.com/" + imageSize + imageCode;
			if (imageCode.contains("r1.ykimg.com")) {
				logo = "https:" + imageCode;
			}

			crawlVideo(page, logo, category);
		} else if (page.getUrl().regex(data_url).match()) {// 视频数据内容
			String dataJson = page.getRawText();
			Map<String, String> dataMap = getDataMap(dataJson);
			String url = page.getUrl().get();
			String vid = url.substring(url.indexOf("vid=") + 4, url.indexOf("&client_ts"));
			String category = url.substring(url.indexOf("category=") + 9, url.indexOf("&ccode"));
			String imageCode = url.substring(url.indexOf("imageCode=") + 10, url.indexOf("&vid"));
			dataMap.put("category", category);
			dataMap.put("vid", vid);
			dataMap.put("imageCode", imageCode);
			crawlForVideo(dataMap, page);
		} else if (page.getUrl().regex(count_url).match()) {// 视频播放量
			String dataJson = page.getRawText();
			dataJson = dataJson.substring(dataJson.indexOf("(") + 1, dataJson.indexOf(")"));
			// 单个视频数据
			Map<String, String> dataMap = JSONObject.parseObject(dataJson, Map.class);
			Map<String, String> stat = JSONObject.parseObject(MapUtils.getString(dataMap, "data"), Map.class);
			stat = JSONObject.parseObject(MapUtils.getString(stat, "stat"), Map.class);
			Long viewCount = MapUtils.getLong(stat, "vv");// 播放数量
			String str = page.getUrl().toString();
			String vid = str.substring(str.lastIndexOf("=") + 1, str.length());
			VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
			if (videoStationEntity != null) {
				videoStationEntity.setViewCount(viewCount);
				videoStationService.updateById(videoStationEntity);

				VideoEntity videoEntity = videoStationEntity.getVideo();
				if (viewCount > 100000) {
					videoEntity.setScore(8.4d);
				} else if (viewCount > 50000 && viewCount <= 100000) {
					videoEntity.setScore(7.8d);
				} else if (viewCount <= 50000 && viewCount > 10000) {
					videoEntity.setScore(7.3d);
				} else {
					videoEntity.setScore(6.9d);
				}
				videoService.updateById(videoEntity);
			}
		} else if (page.getUrl().regex(first_comment_url).match()) {// 第一次进入评论列表
			String dataJson = page.getRawText();
			Map<String, String> map = getDataMap(dataJson);
			String str = page.getUrl().toString();
			String vid = str.substring(str.indexOf("vid=") + 4, str.indexOf("&objectId="));
			String objectId = str.substring(str.indexOf("objectId=") + 9, str.indexOf("&sign="));

			VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
			long commentnum = videoCommentService.queryCountByReInvalid(stationValue, videoStationEntity.getVideo().getId(), null, null);
			int size = MapUtils.getInteger(map, "pageSize");
			long total = MapUtils.getLong(map, "totalSize");

			int pageCount = StringUtils.toInteger((total - commentnum) / size);
			if (StringUtils.toInteger((total - commentnum) % size) != 0) {// 不能整除
				pageCount = pageCount + 1;
			}
			if (pageCount > 0) {
				String pageUrl = "http://p.comments.youku.com/ycp/comment/pc/commentList?app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&";
				pageUrl += "currentPage=" + pageCount + "&vid=" + vid + "&objectId=" + objectId + "&sign=" + sign + "&time=" + time + "&_=" + System.currentTimeMillis();
				page.addTargetRequest(pageUrl);
			}
		} else if (page.getUrl().regex(comment_url).match()) {// 评论列表
			String dataJson = page.getRawText();
			Map<String, String> dataMap = getDataMap(dataJson);
			if (dataMap != null && !dataMap.isEmpty()) {
				String str = page.getUrl().toString();
				String vid = str.substring(str.indexOf("vid=") + 4, str.indexOf("&objectId="));
				String objectId = str.substring(str.indexOf("objectId=") + 9, str.indexOf("&sign="));
				dataMap.put("vid", vid);
				dataMap.put("objectId", objectId);
				// 单个视频数据
				saveOrUpdateComment(dataMap, page);
			}
		} else if (page.getUrl().regex(up_url).match()) {// up主
			Html html = page.getHtml();
			String userAvatar = html.$(".user-avatar img", "src").get();
			String playCount = html.$(".vnum", "title").get();
			if (StringUtils.isNotBlank(playCount)) {
				playCount = playCount.replaceAll(",", "");
			}
			String fansCount = html.$(".snum", "title").get();
			if (StringUtils.isNotBlank(fansCount)) {
				fansCount = fansCount.replaceAll(",", "");
			}
			String videoCount = html.$("h3[node-type=hdTitle] + span", "text").get();
			if (StringUtils.isNotBlank(videoCount)) {
				videoCount = videoCount.replaceAll(",", "").replace("(", "").replace(")", "");
			}
			String url = page.getUrl().get();
			String homeId = url.substring(url.lastIndexOf("/i/") + 3, url.indexOf("/videos"));
			UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
			if (upStationDataEntity != null) {
				upStationDataEntity.setUpFansCount(StringUtils.toLong(fansCount));
				upStationDataEntity.setUpPlayCount(StringUtils.toLong(playCount));
				upStationDataEntity.setUpVideoCount(StringUtils.toLong(videoCount));
				upStationDataEntity.setUpAvatar(userAvatar);
				upStationService.updateById(upStationDataEntity);

				UpEntity upEntity = upStationDataEntity.getUp();
				upEntity.setAvatar(userAvatar);
				upService.updateById(upEntity);
			}
		} else if (page.getUrl().regex(up_json_url).match()) {// up主
			String url = page.getUrl().get();
			String homeId = url.substring(url.lastIndexOf("=") + 1);
			UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
			if (upStationDataEntity != null) {
				String str = page.getRawText();
				str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
				Map<String, String> dataMap = JSONObject.parseObject(str, Map.class);
				Map<String, String> upMap = JSONObject.parseObject(MapUtils.getString(dataMap, "data"), Map.class);
				String homeUrl = MapUtils.getString(upMap, "url");
				if (StringUtils.isNotBlank(homeUrl)) {
					String title = quoteReplacement(MapUtils.getString(upMap, "title"));
					String description = quoteReplacement(MapUtils.getString(upMap, "description"));
					String homeId2 = homeUrl.substring(homeUrl.lastIndexOf("/") + 1);

					upStationDataEntity.setHomeUrl(homeUrl);
					upStationDataEntity.setName(title);
					upStationDataEntity.setUpIntroduction(description);
					upStationService.updateById(upStationDataEntity);

					UpEntity upEntity = upStationDataEntity.getUp();
					upEntity.setName(title);
					upEntity.setIntroduction(description);
					upService.updateById(upEntity);

					// up主页面
					String pageUrl = "http://i.youku.com/i/" + homeId2 + "/videos";
					page.addTargetRequest(pageUrl);
				}
			}

		}
	}

	private void crawlVideo(Page page, String logo, String category) {
		Html html = page.getHtml();
		String title = quoteReplacement(html.$("meta[name=\"description\"]", "content").get());
		String pageConfig = html.regex("var PageConfig.*};</script>").get();
		String str = pageConfig.substring(pageConfig.indexOf("{"), pageConfig.lastIndexOf("}") + 1);
		Map<String, String> configMap = JSONObject.parseObject(str, Map.class);
		String homeId = MapUtils.getString(configMap, "videoOwner");
		String vid = MapUtils.getString(configMap, "currentEncodeVid");
		String objectId = MapUtils.getString(configMap, "videoId");
		String duration = MapUtils.getString(configMap, "seconds");
		UpEntity upEntity = null;
		UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
		if (upStationDataEntity == null) {// up主站点为空
			upEntity = saveUp(title, homeId);
		} else {
			upEntity = upStationDataEntity.getUp();
		}
		Map<String, String> videoMap = new HashMap<String, String>();
		videoMap.put("vid", vid);
		videoMap.put("title", title);
		videoMap.put("duration", duration);
		videoMap.put("logo", logo);
		videoMap.put("category", category);

		VideoEntity videoEntity = saveVideo(videoMap, upEntity, stationValue);
		// 视频标签
		if (StringUtils.isNotBlank(category)) {
			List<String> taglist = new ArrayList<String>();
			taglist.add(category);
			saveVideoTags(taglist, videoEntity);
		}
		// 视频播放量
		String pageUrl = "http://v.youku.com/action/getVideoPlayInfo?callback=tuijsonp4&vid=" + vid;
		page.addTargetRequest(pageUrl);
		// up主
		pageUrl = "http://v.youku.com/action/sub?beta&callback=jQuery&vid=" + objectId + "&ownerid=" + homeId;
		page.addTargetRequest(pageUrl);
		// 视频评论
		pageUrl = "http://p.comments.youku.com/ycp/comment/pc/commentList?isFirst=true&app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&currentPage=1&vid=" + vid + "&objectId=" + objectId + "&sign=" + sign + "&time=" + time;
		page.addTargetRequest(pageUrl);
	}

	private UpEntity saveUp(String name, String homeId) {
		String createTime = StringUtils.toString((System.currentTimeMillis()));
		// UP主
		UpEntity upEntity = new UpEntity();
		upEntity.setName(name);
		upEntity.setStatus(status);
		upEntity.setCreateTime(createTime);
		try {
			upService.insert(upEntity);

			// up主关联站点
			UpStationEntity upStationEntity = new UpStationEntity();
			upStationEntity.setUp(upEntity);
			upStationEntity.setHomeId(homeId);
			upStationEntity.setName(name);
			upStationEntity.setStation(station);
			upStationEntity.setStatus(status);
			upStationEntity.setCreateTime(createTime);
			upStationService.insert(upStationEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return upEntity;
	}

	// 解析爬下来的数据，获取data，转换成map格式
	private Map<String, String> getDataMap(String dataJson) {
		Map<String, String> map = JSONObject.parseObject(dataJson, Map.class);
		String data = MapUtils.getString(map, "data");
		Map<String, String> dataMap = JSONObject.parseObject(data, Map.class);

		return dataMap;
	}

	private void addUrlToSpider(List<String> vidList, List<String> imageList, String category, Page page) {
		for (int i = 0; i < vidList.size(); i++) {
			String vid = vidList.get(i);
			if (vid.contains("id_")) {
				if(vid.contains("html")) {
					vid = vid.substring(vid.indexOf("/id_") + 4, vid.indexOf(".html"));
				} else {
					vid = vid.substring(vid.indexOf("/id_") + 4, vid.indexOf("?"));
				}
			}
			String imageCode = imageList.get(i);
			if (!imageCode.contains("r1.ykimg.com")) {
				imageCode = imageCode.substring(imageCode.lastIndexOf("/") + 6);
			}
			page.addTargetRequest(createUpPostRequest(vid, category, imageCode));
		}
	}

	private static Request createUpPostRequest(String vid, String category, String imageCode) {
		String pageUrl = "https://ups.youku.com/ups/get.json?category=" + category + "&ccode=0401&client_ip=192.168.1.1&utid=EK3TEfHEYFcCATusvFaIlXuu";
		pageUrl += "&imageCode=" + imageCode + "&vid=" + vid + "&client_ts=" + System.currentTimeMillis();
		Request request = new Request(pageUrl);
		request.setMethod(HttpConstant.Method.GET);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("category", category);
			paramMap.put("imageCode", imageCode);
			paramMap.put("ccode", "0401");
			paramMap.put("client_ip", "192.168.1.1");
			paramMap.put("utid", "EK3TEfHEYFcCATusvFaIlXuu");
			paramMap.put("vid", vid);
			paramMap.put("client_ts", System.currentTimeMillis());
			request.setExtras(paramMap);
			request.addHeader("referer", "http://v.youku.com/v_show/id_" + vid);
			request.addHeader("X-Requested-With", "ShockwaveFlash/26.0.0.137");
			request.addHeader("Content-Type", "text/plain;charset=UTF-8");
			request.setRequestBody(HttpRequestBody.form(paramMap, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

	// 单个视频
	private void crawlForVideo(Map<String, String> map, Page page) {
		try {
			String video = MapUtils.getString(map, "video");
			if (StringUtils.isBlank(video)) {
				throw new RuntimeException("video视频不能为空~");
			}
			Map<String, String> videoMap = JSONObject.parseObject(video, Map.class);
			Map<String, String> userMap = JSONObject.parseObject(MapUtils.getString(map, "uploader"), Map.class);
			Map<String, String> upsMap = JSONObject.parseObject(MapUtils.getString(map, "ups"), Map.class);

			String category = MapUtils.getString(map, "category");// 类型
			String vid = MapUtils.getString(videoMap, "id");// 视频ID
			String encodeid = MapUtils.getString(videoMap, "encodeid");// 视频ID
			String homeId = MapUtils.getString(videoMap, "userid");// 用户ID
																	// 2639117
			String userId = MapUtils.getString(userMap, "uid");// 用户ID
																// UMTA1NTY0Njg=
			String published = MapUtils.getString(upsMap, "ups_ts");// 用户ID

			UpEntity upEntity = null;
			UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
			if (upStationDataEntity == null) {// up主站点为空
				userMap.put("homeId", homeId);
				upEntity = saveUp(userMap);
			} else {
				upEntity = upStationDataEntity.getUp();
			}
			// 保存视频
			videoMap.put("category", category);
			videoMap.put("published", published);
			videoMap.put("vid", encodeid);
			videoMap.put("duration", MapUtils.getString(videoMap, "seconds"));
			VideoEntity videoEntity = saveVideo(videoMap, upEntity, stationValue);
			// 视频标签
			String tags = MapUtils.getString(videoMap, "tags");
			if (StringUtils.isNotBlank(tags)) {
				JSONArray jsonTagArray = JSONArray.parseArray(tags);// 把String转换为json
				List<String> taglist = jsonTagArray.toJavaList(String.class);
				saveVideoTags(taglist, videoEntity);
			}
			// 加入其它链接
			String pageUrl = "http://v.youku.com/action/getVideoPlayInfo?callback=tuijsonp4&vid=" + vid;
			page.addTargetRequest(pageUrl);
			pageUrl = "http://i.youku.com/i/" + userId + "/videos";
			page.addTargetRequest(pageUrl);
			pageUrl = "http://p.comments.youku.com/ycp/comment/pc/commentList?isFirst=true&app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&currentPage=1&vid=" + encodeid + "&objectId=" + vid + "&sign=" + sign + "&time=" + time;
			page.addTargetRequest(pageUrl);
		} catch (Exception e) {
			String error = MapUtils.getString(map, "error");
			if (StringUtils.isNotBlank(error)) {
				String vid = MapUtils.getString(map, "vid");
				String category = MapUtils.getString(map, "category");
				String imageCode = MapUtils.getString(map, "imageCode");
				String pageUrl = "http://v.youku.com/v_show/id_" + vid + ".html?category=" + category + "&imageCode=" + imageCode;
				page.addTargetRequest(pageUrl);
			} else {
				e.printStackTrace();
			}
		}
	}

	// 先新增UP主，再新增站点UP主，这里暂时不做UP主的合并逻辑判断。
	private UpEntity saveUp(Map<String, String> map) {
		String createTime = StringUtils.toString((System.currentTimeMillis()));
		String name = quoteReplacement(MapUtils.getString(map, "username"));
		String introduction = quoteReplacement(MapUtils.getString(map, "reason"));
		String homeId = MapUtils.getString(map, "homeId");
		String homeUrl = MapUtils.getString(map, "homepage");
		Map<String, String> avatarMap = JSONObject.parseObject(MapUtils.getString(map, "avatar"), Map.class);
		String upAvatar = MapUtils.getString(avatarMap, "big");

		UpEntity upEntity = new UpEntity();
		// UP主
		upEntity.setName(name);
		upEntity.setAvatar(upAvatar);
		upEntity.setIntroduction(introduction);
		upEntity.setStatus(status);
		upEntity.setCreateTime(createTime);
		try {
			upService.insert(upEntity);

			// up主关联站点
			UpStationEntity upStationEntity = new UpStationEntity();
			upStationEntity.setUp(upEntity);
			upStationEntity.setHomeId(homeId);
			upStationEntity.setHomeUrl(homeUrl);
			upStationEntity.setName(name);
			upStationEntity.setUpIntroduction(introduction);
			upStationEntity.setUpAvatar(upAvatar);
			upStationEntity.setStation(station);
			upStationEntity.setStatus(status);
			upStationEntity.setCreateTime(createTime);
			upStationService.insert(upStationEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return upEntity;
	}

	@Override
	protected VideoEntity saveOrUpdateVideo(Map<String, String> map, VideoEntity entity) {
		String title = quoteReplacement(MapUtils.getString(map, "title"));
		String coverImage = MapUtils.getString(map, "logo");
		Double duration = MapUtils.getDouble(map, "duration");

		if (entity == null) {
			entity = new VideoEntity();
		}
		try {
			entity.setTitle(title);
			entity.setCover(coverImage);
			entity.setDuration(duration);

			if (StringUtils.isBlank(entity.getId())) {
				entity.setStatus(status);
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
				videoService.insert(entity);
			} else {
				videoService.updateById(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	// 视频站点
	@Override
	protected VideoStationEntity saveVideoStation(Map<String, String> map, VideoStationEntity entity) {
		String title = quoteReplacement(MapUtils.getString(map, "title"));
		String published = MapUtils.getString(map, "published");
		String coverImage = MapUtils.getString(map, "logo");
		Double duration = MapUtils.getDouble(map, "duration");
		String videoId = MapUtils.getString(map, "vid");
		String url = "http://v.youku.com/v_show/id_" + videoId + ".html";
		String flashUrl = "http://player.youku.com/player.php/sid/" + videoId + "/v.swf";
		String category = MapUtils.getString(map, "category");
		if (StringUtils.isBlank(category)) {
			List<Map> list = JSON.parseArray(MapUtils.getString(map, "subcategories"), Map.class);
			if (list != null && !list.isEmpty()) {
				category = MapUtils.getString(list.get(0), "name");
			}
		}

		try {
			if (StringUtils.isBlank(published)) {
				long time = new Date().getTime();
				published = StringUtils.toString(time / 1000);
			}
			entity.setPublished(published);
			entity.setTitle(title);
			entity.setIntroduction(title);
			entity.setCover(coverImage);
			entity.setDuration(duration);
			entity.setVid(videoId);
			entity.setUrl(url);
			entity.setFlashUrl(flashUrl);
			entity.setCategory(category);
			if (StringUtils.isBlank(entity.getId())) {
				entity.setStation(station);
				entity.setStatus(status);
				entity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));

				videoStationService.insert(entity);
			} else {
				videoStationService.updateById(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entity;
	}

	private void saveOrUpdateComment(Map<String, String> map, Page page) {
		String commentContentArr = MapUtils.getString(map, "comment");
		String vid = MapUtils.getString(map, "vid");
		if (StringUtils.isNotBlank(commentContentArr) && StringUtils.isNotBlank(vid)) {
			VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);

			List<Map> list = JSON.parseArray(commentContentArr, Map.class);
			VideoCommentEntity parentComment = null;
			Collections.reverse(list);
			for (Map paramMap : list) {
				String commentId = StringUtils.toString(MapUtils.getLong(paramMap, "id"));// 防止科学计数法
				String quoteId = StringUtils.toString(MapUtils.getLong(paramMap, "parentCommentId"));// 父级ID
				String commentContent = MapUtils.getString(paramMap, "content");
				String postDate = MapUtils.getString(paramMap, "createTime");
				Map<String, String> userMap = JSONObject.parseObject(MapUtils.getString(paramMap, "user"), Map.class);
				String commentUserId = StringUtils.toString(MapUtils.getLong(userMap, "userId"));
				String commentUserName = quoteReplacement(MapUtils.getString(userMap, "userName"));

				VideoCommentEntity videoCommentEntity = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),stationValue, commentId);
				if (videoCommentEntity == null) {
					videoCommentEntity = new VideoCommentEntity();
				}
				if (StringUtils.isBlank(commentContent)) {
					commentContent = "空值";
				} else {
					commentContent = quoteReplacement(commentContent.replaceAll("[\\x{10000}-\\x{10FFFF}]", "*"));
				}
				videoCommentEntity.setCommentId(commentId);
				videoCommentEntity.setCommentContent(commentContent);
				videoCommentEntity.setCommentUserId(commentUserId);
				videoCommentEntity.setCommentUserName(commentUserName);
				if (StringUtils.toLong(quoteId) > 0) {
					parentComment = new VideoCommentEntity();
					parentComment.setCommentId(quoteId);
					videoCommentEntity.setCommentParent(parentComment);
				}

				try {
					if (StringUtils.isBlank(videoCommentEntity.getId())) {
						if (videoStationEntity != null) {// 视频不为空
							videoCommentEntity.setCommentCreatetime(postDate);
							videoCommentEntity.setVideo(videoStationEntity.getVideo());
							videoCommentEntity.setStation(station);
							videoCommentEntity.setStatus(status);
							videoCommentEntity.setLikeNum(0L);
							videoCommentService.insert(videoCommentEntity);
						}
					} else {
						videoCommentService.updateById(videoCommentEntity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 查找下一页
			long commentnum = videoCommentService.queryCountByReInvalid(stationValue, videoStationEntity.getVideo().getId(), null, null);
			int size = MapUtils.getInteger(map, "pageSize");
			long total = MapUtils.getLong(map, "totalSize");

			int pageCount = StringUtils.toInteger((total - commentnum) / size);
			if (StringUtils.toInteger((total - commentnum) % size) != 0) {// 不能整除
				pageCount = pageCount + 1;
			}
			if (pageCount > 0) {
				String objectId = MapUtils.getString(map, "objectId");
				String pageUrl = "http://p.comments.youku.com/ycp/comment/pc/commentList?app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&";
				pageUrl += "currentPage=" + pageCount + "&vid=" + vid + "&objectId=" + objectId + "&sign=" + sign + "&time=" + time + "&_=" + System.currentTimeMillis();
				page.addTargetRequest(pageUrl);
			}
		}
	}

	@Override
	public void crawl(Map<String, Object> paramMap) {
		String id = MapUtils.getString(paramMap, "id");
		String q = MapUtils.getString(paramMap, "q");
		String isList = MapUtils.getString(paramMap, "isList");
		if (StringUtils.isNotBlank(id)) {
			String url = "http://v.youku.com/v_show/id_" + id + ".html?category=&imageCode=";
			Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
		}
		if (StringUtils.isNotBlank(q)) {
			String url = "http://www.soku.com/search_video/q_" + q + "_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1&_=" + System.currentTimeMillis();
			Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
		}

		if ("true".equals(isList)) {
			List<String> list = new ArrayList<String>();
			list.add("http://game.youku.com/index/lol/_page93117_1.html?maxVid=&minVid=");// 英雄联盟
			list.add("http://game.youku.com/index/dota/_page93250_1.html?maxVid=&minVid=");// dota
			list.add("http://www.soku.com/search_video/q_王者荣耀_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			list.add("http://www.soku.com/search_video/q_英雄联盟_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			list.add("http://www.soku.com/search_video/q_dota2_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			list.add("http://www.soku.com/search_video/q_守望先锋_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			List<String> urlList = null;
			for (String url : list) {
				urlList = new ArrayList<String>();
				urlList.add(url);
				Spider.create(this).setModeType(GlobalConstant.MODE_TYPE_ALWAYS).setInitUrls(urlList).addUrl(url + "&_=" + new Date().getTime()).thread(5).start();
			}
		}

	}

}
