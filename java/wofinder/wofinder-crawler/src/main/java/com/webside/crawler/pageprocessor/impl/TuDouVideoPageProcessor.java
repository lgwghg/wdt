package com.webside.crawler.pageprocessor.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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
public class TuDouVideoPageProcessor extends VideoPageProcessor {

	public static final Logger logger = Logger.getLogger(TuDouVideoPageProcessor.class);

	// 视频列表
	private final String q_list_url = "http://www.soku.com/nt/search/q_.*_orderby_2_cateid_99_limitdate_0\\?maxVid=(\\w+==|\\w*)&minVid=(\\w+==|\\w*)&page=\\d+&_=\\d+";
	// 视频详情
	private final String video_url = "http://video.tudou.com/v/(\\w+|\\w+==).html.*";
	// 视频data
	private final String data_url = "https://ups.youku.com/ups/get.json\\?category=.*&ccode=0402&client_ip=192.168.1.1&utid=\\w+&vid=(\\w+|\\w+==)&client_ts=\\d+";
	// 视频播放量
	private final String count_url = "http://v.youku.com/action/getVideoPlayInfo\\?callback=tuijsonp4&vid=\\d+";
	// up主页面
	private final String up_url = "http://id.tudou.com/i/(\\w+|\\w+==)/videos";
	// 评论列表
	private final String comment_url = "http://p.comments.youku.com/ycp/comment/pc/commentList\\?app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&currentPage=\\d+&vid=\\w+&objectId=\\d+&sign=\\w+&time=\\d+";
	private final String first_comment_url = "http://p.comments.youku.com/ycp/comment/pc/commentList\\?isFirst=true&app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&currentPage=\\d+&vid=\\w+&objectId=\\d+&sign=\\w+&time=\\d+";
	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
	private DictEntity station = new DictEntity(GlobalConstant.STATION_TYPE_3);
	private String stationValue = GlobalConstant.STATION_TYPE_3;
	private String sign = "4b139762dc149db085cb788d82ea2e4a";
	private String time = "1502103155";

	@Override
	public void processData(Page page) {
		if (page.getUrl().regex(q_list_url).match()) {// 获取视频列表
			String url = page.getUrl().get();
			logger.info("爬虫info查看信息=========进入视频列表：" + url);
			Html html = page.getHtml();
			List<String> list = html.$(".v-link a", "_log_vid").all();
			if (list != null && !list.isEmpty()) {
				Collections.reverse(list);
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

					addUrlToSpider(list, category, page);
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

								addUrlToSpider(list, category, page);
							} else {
								pageCount = pageCount + 1;
								minVid = thisMinVid;
							}
						} else {
							pageCount = pageCount - 1;
							maxVid = thisMaxVid;
							// 上一页最大值 不一致，爬取数据
							if (!thisMaxVid.equals(maxVid)) {
								addUrlToSpider(list, category, page);
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
		} else if (page.getUrl().regex(video_url).match()) {// 获取视频详情页
			// logger.info("爬虫info查看信息=========进入视频详情页：" + page.getUrl());
			String url = page.getUrl().toString();
			String vid = url.substring(url.lastIndexOf("/") + 1, url.indexOf(".html"));

			page.addTargetRequest(createUpPostRequest(vid, ""));
		} else if (page.getUrl().regex(data_url).match()) {// 视频数据内容
			// logger.info("爬虫info查看信息=========进入视频数据：" + page.getUrl());
			String dataJson = page.getRawText();
			Map<String, String> dataMap = getDataMap(dataJson);
			String url = page.getUrl().get();
			String category = url.substring(url.indexOf("category=") + 9, url.indexOf("&"));
			dataMap.put("category", category);
			// 单个视频数据
			crawlForVideo(dataMap, page);
		} else if (page.getUrl().regex(count_url).match()) {// 视频播放量
			// logger.info("爬虫info查看信息=========进入视频播放量：" + page.getUrl());
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
			// logger.info("爬虫info查看信息=========进入评论列表：" + page.getUrl());
			String dataJson = page.getRawText();
			Map<String, String> dataMap = getDataMap(dataJson);
			String str = page.getUrl().toString();
			String vid = str.substring(str.indexOf("vid=") + 4, str.indexOf("&objectId="));
			String objectId = str.substring(str.indexOf("objectId=") + 9, str.indexOf("&sign="));
			dataMap.put("vid", vid);
			dataMap.put("objectId", objectId);
			// 单个视频数据
			saveOrUpdateComment(dataMap, page);
		} else if (page.getUrl().regex(up_url).match()) {// up主
			// logger.info("爬虫info查看信息=========进入up主：" + page.getUrl());
			Html html = page.getHtml();
			String playCount = html.$(".vnum", "title").get();
			playCount = playCount.replaceAll(",", "");
			String fansCount = html.$(".snum", "title").get();
			fansCount = fansCount.replaceAll(",", "");
			String videoCount = html.$("h3[node-type=hdTitle] + span", "text").get();
			if (StringUtils.isNotBlank(videoCount)) {
				videoCount = videoCount.replaceAll(",", "").replace("(", "").replace(")", "");
			}
			String url = page.getUrl().get();
			String homeId = url.substring(url.lastIndexOf("/") + 1);
			UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
			upStationDataEntity.setUpFansCount(StringUtils.toLong(fansCount));
			upStationDataEntity.setUpPlayCount(StringUtils.toLong(playCount));
			upStationDataEntity.setUpVideoCount(StringUtils.toLong(videoCount));
			upStationService.updateById(upStationDataEntity);
		}
	}

	private void addUrlToSpider(List<String> list, String category, Page page) {
		for (String vid : list) {
			page.addTargetRequest(createUpPostRequest(vid, category));
		}
		try {
			Thread.sleep(1000 * 60 * 5);// 暂停5分钟
		} catch (InterruptedException e) {
			logger.error("Thread interrupted when sleep", e);
		}
	}

	private static Request createUpPostRequest(String vid, String category) {
		String pageUrl = "https://ups.youku.com/ups/get.json?category=" + category + "&ccode=0402&client_ip=192.168.1.1&utid=EK3TEfHEYFcCATusvFaIlXuu&vid=" + vid + "&client_ts=" + System.currentTimeMillis();
		Request request = new Request(pageUrl);
		request.setMethod(HttpConstant.Method.GET);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("category", "category");
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

	// 解析爬下来的数据，获取data，转换成map格式
	private Map<String, String> getDataMap(String dataJson) {
		Map<String, String> map = JSONObject.parseObject(dataJson, Map.class);
		String data = MapUtils.getString(map, "data");
		Map<String, String> dataMap = JSONObject.parseObject(data, Map.class);

		return dataMap;
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
			String homeId = MapUtils.getString(userMap, "uid");// 用户ID
			String published = MapUtils.getString(upsMap, "ups_ts");// 用户ID
			
			UpEntity upEntity = null;
			UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
			if (upStationDataEntity == null) {// up主站点为空
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
			pageUrl = "http://id.tudou.com/i/" + homeId + "/videos";
			page.addTargetRequest(pageUrl);
			pageUrl = "http://p.comments.youku.com/ycp/comment/pc/commentList?isFirst=true&app=100-DDwODVkv&objectType=1&listType=0&pageSize=30&currentPage=1&vid=" + encodeid + "&objectId=" + vid + "&sign=" + sign + "&time=" + time;
			page.addTargetRequest(pageUrl);
		} catch (Exception e) {
			String error = MapUtils.getString(map, "error");
			if (StringUtils.isNotBlank(error)) {
				try {
					Thread.sleep(1000 * 60 * 60 * 8);// 暂停8小时
				} catch (InterruptedException e1) {
					logger.error("Thread interrupted when sleep", e1);
				}
			}

			e.printStackTrace();
		}
	}

	// 先新增UP主，再新增站点UP主，这里暂时不做UP主的合并逻辑判断。
	private UpEntity saveUp(Map<String, String> map) {
		String createTime = StringUtils.toString((System.currentTimeMillis()));
		String name = quoteReplacement(MapUtils.getString(map, "username"));
		String introduction = quoteReplacement(MapUtils.getString(map, "reason"));
		String homeId = MapUtils.getString(map, "uid");
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
		String coverImage = MapUtils.getString(map, "logo");
		String published = MapUtils.getString(map, "published");
		Double duration = MapUtils.getDouble(map, "duration");
		String videoId = MapUtils.getString(map, "encodeid");
		String url = MapUtils.getString(map, "weburl");
		String flashUrl = "http://player.youku.com/player.php/sid/" + videoId + "/v.swf";
		String category = MapUtils.getString(map, "category");
		if (StringUtils.isBlank(category)) {
			List<Map> list = JSON.parseArray(MapUtils.getString(map, "subcategories"), Map.class);
			if (list != null && !list.isEmpty()) {
				category = MapUtils.getString(list.get(0), "name");
			}
		}

		try {
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
			String url = "http://video.tudou.com/v/" + id + ".html";
			Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
		}
		if (StringUtils.isNotBlank(q)) {
			String url = "http://www.soku.com/nt/search/q_" + q + "_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1&_=" + System.currentTimeMillis();
			Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
		}
		if ("true".equals(isList)) {
			List<String> list = new ArrayList<String>();
			list.add("http://www.soku.com/nt/search/q_王者荣耀_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			list.add("http://www.soku.com/nt/search/q_英雄联盟_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			list.add("http://www.soku.com/nt/search/q_dota2_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			list.add("http://www.soku.com/nt/search/q_守望先锋_orderby_2_cateid_99_limitdate_0?maxVid=&minVid=&page=1");
			List<String> urlList = null;
			for (String url : list) {
				urlList = new ArrayList<String>();
				urlList.add(url);
				Spider.create(this).setModeType(GlobalConstant.MODE_TYPE_ALWAYS).setInitUrls(urlList).addUrl(url + "&_=" + System.currentTimeMillis()).thread(5).start();
			}
		}

	}

	public static void main(String[] args) {
		String str = "http://www.soku.com/nt/search/q_王者荣耀_orderby_2_cateid_99_limitdate_0?page=100&_=1234567156";
		str = str.substring(str.indexOf("page=") + 5, str.indexOf("&_="));
		System.out.println(str);

	}

}
