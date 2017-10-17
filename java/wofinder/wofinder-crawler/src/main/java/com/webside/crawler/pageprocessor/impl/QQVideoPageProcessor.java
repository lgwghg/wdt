package com.webside.crawler.pageprocessor.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webside.common.GlobalConstant;
import com.webside.crawler.pageprocessor.VideoPageProcessor;
import com.webside.dict.model.DictEntity;
import com.webside.sys.model.GameEntity;
import com.webside.sys.model.ValueEntity;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpStationEntity;
import com.webside.user.model.UserEntity;
import com.webside.util.DateUtils;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.model.VideoAlbumValueEntity;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class QQVideoPageProcessor extends VideoPageProcessor {

	public static final Logger logger = Logger.getLogger(QQVideoPageProcessor.class);

	// 视频列表
	private final String list_url = "http://v.qq.com/x/list/games\\?sort=5&maxVid=(\\w+==|\\w*)&minVid=(\\w+==|\\w*)&offset=\\d+&itype=(974|975|976|977|980|981)&_=\\d+";
	private final String first_list_url = "http://v.qq.com/x/list/games\\?sort=5&offset=0&itype=(974|975|976|977|980|981)&_=\\d+";
	// 视频列表（查询）
	private final String q_list_url = "https://v.qq.com/x/search/\\?filter=tabid%3D17&cur=\\d+&q=.*";
	// 视频合集
	private final String a_list_url = "http://v.qq.com/detail/\\w+/\\w+.html\\?category=.*";
	// 视频合集初始页 只有 年
	private final String a_first_video_url = "http://s.video.qq.com/get_playsource\\?otype=json&type=4&id=\\w+&year=\\d+&albumId=\\w+";
	// 视频合集详情页 年月                                                              
	private final String a_video_url = "http://s.video.qq.com/get_playsource\\?otype=json&type=4&id=\\w+&year=\\d+&albumId=\\w+&month=\\d+&range=1-1000";
	// 视频详情
	private final String video_url = "http://v.qq.com/x/page/\\w+.html\\?category=.*";
	private final String video_url2 = "http://v.qq.com/x/cover/\\w+/\\w+.html\\?albumId=\\w+";
	// 获取评论ID
	private final String comment_id_url = "https://ncgi.video.qq.com/fcgi-bin/video_comment_id\\?otype=json&op=3&vid=\\w+&_=\\d+";
	// 评论数量
	private final String comment_count_url = "https://coral.qq.com/article/\\d+/commentnum\\?vid=\\w+&_=\\d+";
	// 评论列表
	private final String comment_url = "https://coral.qq.com/article/\\d+/comment\\?commentid=\\w+&reqnum=\\d+&vid=\\w+&_=\\d+";

	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
	private DictEntity station = new DictEntity(GlobalConstant.STATION_TYPE_6);
	private String stationValue = GlobalConstant.STATION_TYPE_6;
	// 重复的数量
	private int updateCount = 0;
	private int pageSize = 30;
	private int totalSize = 4980;

	@Override
	public void processData(Page page) {
		if (page.getUrl().regex(first_list_url).match()) {// 第一次进入视频详情页
			String url = page.getUrl().get();
			String itype = url.substring(url.indexOf("itype=") + 6, url.indexOf("&_="));
			String pageUrl = getNextPage(itype);
			page.addTargetRequest(pageUrl);
		} else if (page.getUrl().regex(list_url).match()) {// 获取视频详情页
			String url = page.getUrl().get();
			logger.info("爬虫info查看信息=========进入视频列表：" + url);
			if (updateCount >= pageSize) {
				return;
			}
			List<String> list = page.getHtml().$(".figures_list .list_item .figure", "href").all();
			if (list != null && !list.isEmpty()) {
				Collections.reverse(list);
				String offset = url.substring(url.indexOf("offset=") + 7, url.indexOf("&itype"));
				int _offset = StringUtils.toInteger(offset);
				String maxVid = url.substring(url.indexOf("maxVid=") + 7, url.indexOf("&minVid"));
				String minVid = url.substring(url.indexOf("minVid=") + 7, url.indexOf("&offset"));

				String firstUrl = list.get(0);
				// 本页最小值
				String thisMinVid = firstUrl.substring(firstUrl.lastIndexOf("/") + 1, firstUrl.indexOf(".html"));
				// 本页最大值
				String lastUrl = list.get(list.size() - 1);
				String thisMaxVid = lastUrl.substring(lastUrl.lastIndexOf("/") + 1, lastUrl.indexOf(".html"));

				String itype = url.substring(url.indexOf("itype=") + 6, url.indexOf("&_="));
				String category = getCategory(itype);

				if (_offset >= totalSize) {// 最后一页，直接转下一页
					addUrlToSpider(list, category, page);
					_offset = totalSize - pageSize;
					maxVid = thisMaxVid;
				} else {
					// 最小值为空， 强制跳转上一页
					if (StringUtils.isBlank(minVid)) {
						_offset = _offset + pageSize;
						minVid = thisMinVid;
					} else {
						VideoStationEntity entity = videoStationService.findByVideoStationId(null, stationValue, null, thisMinVid);
						// 最小值没数据
						if (entity == null || StringUtils.isBlank(entity.getId())) {
							// 判断 最小值 和 上一页传入过来的最小值是否一致
							if (thisMinVid.equals(minVid)) {
								addUrlToSpider(list, category, page);
								refreshDefault(itype, _offset);
								_offset = _offset - pageSize;
								maxVid = thisMaxVid;
							} else {
								_offset = _offset + pageSize;
								minVid = thisMinVid;
							}
						} else {
							// 上一页最大值 不一致，爬取数据
							if (!thisMaxVid.equals(maxVid)) {
								addUrlToSpider(list, category, page);
								refreshDefault(itype, _offset);
							}
							_offset = _offset - pageSize;
							maxVid = thisMaxVid;
						}
					}
				}

				if (_offset <= totalSize && _offset >= 0) {
					String pageUrl = getPageUrl(itype, maxVid, minVid, _offset);
					page.addTargetRequest(pageUrl);
				}
			}
		} else if (page.getUrl().regex(q_list_url).match()) {// 获取视频详情页
			logger.info("爬虫info查看信息=========进入视频列表：" + page.getUrl());
			if (updateCount >= 30) {
				return;
			}
			String url = page.getUrl().get();
			List<String> list = page.getHtml().$(".result_title a", "href").all();
			if (list != null && !list.isEmpty()) {
				Collections.reverse(list);
				String category = url.substring(url.lastIndexOf("&q=") + 3);
				for (String str : list) {
					str = str.replaceAll("https:", "http:");
					str = str + "?category=" + category + "&_=" + System.currentTimeMillis();;
					page.addTargetRequest(str);
				}

				String pageJson = page.getHtml().$(".search_container", "r-props").get();
				pageJson = pageJson.replaceAll(";", ",");
				Map<String, String> pageMap = JSONObject.parseObject(pageJson, Map.class);
				Integer pages = MapUtils.getInteger(pageMap, "pages");
				Integer cur = MapUtils.getInteger(pageMap, "cur");
				if (cur < pages) {// 不是最后一页
					cur = cur + 1;
					String pageUrl = url.replaceAll("cur=\\d+", "cur=" + cur);
					page.addTargetRequest(pageUrl);
				}
			}
		} else if (page.getUrl().regex(video_url).match() || page.getUrl().regex(video_url2).match()) {// 获取视频详情页
			// logger.info("爬虫info查看信息=========进入视频详情页：" + page.getUrl());
			Html html = page.getHtml();
			String upStr = html.regex("var VPP_INFO.*var VIDEO_INFO").get();
			String videoStr = html.regex("var VIDEO_INFO.*var LIST_INFO").get();
			if (StringUtils.isBlank(upStr)) {
				videoStr = html.regex("var VIDEO_INFO.*var VPP_INFO").get();
				upStr = html.regex("var VPP_INFO.*var LIST_INFO").get();
			}
			if (StringUtils.isBlank(upStr) || StringUtils.isBlank(videoStr)) {
				return;
			}
			List<String> tagsList = html.$(".video_tags .tag_item", "text").all();
			String published = html.$("meta[itemprop=\"datePublished\"]", "content").get();
			String viewCount = html.$("meta[itemprop=\"interactionCount\"]", "content").get();
			String url = page.getUrl().get();
			String category = "";
			String albumId = "";
			if (url.indexOf("albumId=") != -1) {// 合集
				albumId = url.substring(url.lastIndexOf("albumId=") + 8);
			} else {
				category = url.substring(url.lastIndexOf("category=") + 9, url.indexOf("&_="));
			}
			url = url.substring(0, url.lastIndexOf("?"));
			String str = upStr.substring(upStr.indexOf("{"), upStr.lastIndexOf("}") + 1);
			Map<String, String> upMap = JSONObject.parseObject(str, Map.class);
			str = videoStr.substring(videoStr.indexOf("{"), videoStr.lastIndexOf("}") + 1);
			str = str.replaceAll("\\!\\(.*\\)", "true");
			Map<String, String> videoMap = JSONObject.parseObject(str, Map.class);
			String vid = MapUtils.getString(videoMap, "vid");
			String coverImage = html.$("meta[itemprop=\"image\"]", "content").get();
			if (StringUtils.isBlank(coverImage) || coverImage.indexOf("https://") == -1) {// 没有图片
				coverImage = "https://puui.qpic.cn/qqvideo_ori/0/" + vid + "_228_128/0";
			}
			videoMap.put("coverImage", coverImage);
			videoMap.put("published", published);
			videoMap.put("category", category);
			videoMap.put("viewCount", viewCount);
			videoMap.put("albumId", albumId);
			videoMap.put("url", url);
			String title = MapUtils.getString(videoMap, "title");
			if (StringUtils.isBlank(title)) {
				title = html.$("meta[itemprop=\"description\"]", "content").get();
				videoMap.put("title", title);
			}

			crawlForVideo(upMap, videoMap, tagsList);
			String pageUrl = "https://ncgi.video.qq.com/fcgi-bin/video_comment_id?otype=json&op=3&vid=" + vid + "&_=" + System.currentTimeMillis();
			page.addTargetRequest(pageUrl);
		} else if (page.getUrl().regex(comment_id_url).match()) {// 获取评论ID
			// logger.info("爬虫info查看信息=========进入评论ID查询：" + page.getUrl());
			String str = page.getRawText();
			str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
			Map<String, String> map = JSONObject.parseObject(str, Map.class);
			String commentId = MapUtils.getString(map, "comment_id");
			String vid = MapUtils.getString(map, "srcid");
			String pageUrl = "https://coral.qq.com/article/" + commentId + "/commentnum?vid=" + vid + "&_=" + System.currentTimeMillis();
			page.addTargetRequest(pageUrl);
		} else if (page.getUrl().regex(comment_count_url).match()) {// 获取评论数量
			// logger.info("爬虫info查看信息=========进入评论数量：" + page.getUrl());
			String str = page.getRawText();
			Map<String, String> dataMap = getDataMap(str);
			Long commentCount = MapUtils.getLong(dataMap, "commentnum");
			if (commentCount != null && commentCount > 0) {
				Long commentId = MapUtils.getLong(dataMap, "targetid");
				String url = page.getUrl().get();
				String vid = url.substring(url.indexOf("vid=") + 4, url.lastIndexOf("&"));
				VideoStationEntity videoStationDataEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
				if (videoStationDataEntity != null) {
					videoStationDataEntity.setCommentCount(commentCount);
					videoStationService.updateById(videoStationDataEntity);
				}
				String pageUrl = "https://coral.qq.com/article/" + commentId + "/comment?commentid=0&reqnum=10&vid=" + vid + "&_=" + System.currentTimeMillis();
				page.addTargetRequest(pageUrl);
			}
		} else if (page.getUrl().regex(comment_url).match()) {// 获取评论列表
			// logger.info("爬虫info查看信息=========进入评论列表：" + page.getUrl());
			String str = page.getRawText();
			Map<String, String> dataMap = getDataMap(str);
			String url = page.getUrl().get();
			String vid = url.substring(url.indexOf("vid=") + 4, url.lastIndexOf("&"));
			dataMap.put("vid", vid);

			saveOrUpdateComment(dataMap, page);
		} else if (page.getUrl().regex(a_list_url).match()) {// 获取视频合集
			logger.info("爬虫info查看信息=========进入视频合集：" + page.getUrl());
			if (updateCount >= 30) {
				return;
			}
			crawForAlbum(page);
		} else if (page.getUrl().regex(a_video_url).match()) {// 数据列表
			// logger.info("爬虫info查看信息=========进入合集数据列表：" + page.getUrl());
			String url = page.getUrl().get();
			String albumId = url.substring(url.lastIndexOf("&albumId=") + 9, url.lastIndexOf("&month"));
			String str = page.getRawText();
			str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
			Map<String, String> map = JSONObject.parseObject(str, Map.class);
			Map<String, String> itemMap = JSONObject.parseObject(MapUtils.getString(map, "PlaylistItem"), Map.class);
			List<Map<String, String>> list = JSONObject.parseObject(MapUtils.getString(itemMap, "videoPlayList"), List.class);
			for (Map<String, String> dataMap : list) {
				String pageUrl = MapUtils.getString(dataMap, "playUrl");
				pageUrl = pageUrl + "?albumId=" + albumId;
				page.addTargetRequest(pageUrl);
			}
		} else if (page.getUrl().regex(a_first_video_url).match()) {// 初次进入
			// logger.info("爬虫info查看信息=========初次进入合集列表：" + page.getUrl());
			String str = page.getRawText();
			str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
			Map<String, String> map = JSONObject.parseObject(str, Map.class);
			Map<String, String> itemMap = JSONObject.parseObject(MapUtils.getString(map, "PlaylistItem"), Map.class);
			List<String> list = JSONObject.parseObject(MapUtils.getString(itemMap, "indexList2"), List.class);
			for (String month : list) {
				String pageUrl = page.getUrl().get() + "&month=" + month + "&range=1-1000";
				page.addTargetRequest(pageUrl);
			}
		}
	}
	
	private void addUrlToSpider(List<String> list, String category, Page page) {
		for (String str : list) {
			str = str.replace("https:", "http:");
			str = str + "?category=" + category + "&_=" + System.currentTimeMillis();
			page.addTargetRequest(str);
		}
	}
	
	// 重置默认数量,使其下次进来，还在这一页
	private void refreshDefault(String channelId, int offset) {
		DictEntity dictEntity = dictService.findByTypeValueNoRedis(GlobalConstant.Q_NOW_OFFSET, channelId);
		if (dictEntity != null && StringUtils.isNotBlank(dictEntity.getId())) {
			dictEntity.setDescription(StringUtils.toString(offset));
			dictService.updateById(dictEntity);
		} else {
			dictEntity = new DictEntity(channelId);
			dictEntity.setType(GlobalConstant.Q_NOW_OFFSET);
			dictEntity.setStatus(status);
			dictEntity.setLabel(getCategory(channelId));
			dictEntity.setCreateUser(new UserEntity("1"));
			dictEntity.setDescription(StringUtils.toString(offset));
			dictService.insert(dictEntity);
		}
	}
	
	private String getNextPage(String itype) {
		int offset = totalSize;
		DictEntity dictEntity = dictService.findByTypeValueNoRedis(GlobalConstant.Q_NOW_OFFSET, itype);
		if (dictEntity != null && StringUtils.isNotBlank(dictEntity.getId())) {
			offset = StringUtils.toInteger(dictEntity.getDescription());
		} else {
			dictEntity = new DictEntity(itype);
			dictEntity.setType(GlobalConstant.Q_NOW_OFFSET);
			dictEntity.setStatus(status);
			dictEntity.setLabel(getCategory(itype));
			dictEntity.setCreateUser(new UserEntity("1"));
			dictEntity.setDescription(StringUtils.toString(offset));
			dictService.insert(dictEntity);
		}

		String url = "http://v.qq.com/x/list/games?sort=5";
		url += "&maxVid=&minVid=";
		url += "&offset=" + offset;
		url += "&itype=" + itype;
		url += "&_=" + System.currentTimeMillis();

		return url;
	}

	// 解析爬下来的数据，获取data，转换成map格式
	private Map<String, String> getDataMap(String dataJson) {
		Map<String, String> map = JSONObject.parseObject(dataJson, Map.class);
		String data = MapUtils.getString(map, "data");
		Map<String, String> dataMap = JSONObject.parseObject(data, Map.class);

		return dataMap;
	}

	private String getPageUrl(String itype, String maxVid, String minVid, int offset) {
		String url = "http://v.qq.com/x/list/games?sort=5";
		url += "&maxVid=" + maxVid + "&minVid=" + minVid;
		url += "&offset=" + offset;
		url += "&itype=" + itype;
		url += "&_=" + System.currentTimeMillis();

		return url;
	}

	private String getCategory(String itype) {
		String category = "";
		if ("974".equals(itype)) {
			category = "主机单机";
		} else if ("975".equals(itype)) {
			category = "手机游戏";
		} else if ("976".equals(itype)) {
			category = "电子竞技";
		} else if ("977".equals(itype)) {
			category = "达人解说";
		} else if ("980".equals(itype)) {
			category = "游戏周边";
		} else if ("981".equals(itype)) {
			category = "网络游戏";
		}

		return category;
	}

	// 单个视频
	private void crawlForVideo(Map<String, String> upsMap, Map<String, String> videoMap, List<String> tagsList) {
		String homeId = MapUtils.getString(upsMap, "uin");// 用户ID
		UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
		// 更新或修改up主
		UpEntity upEntity = saveOrUpdateUp(upsMap, upStationDataEntity);
		// 保存视频
		VideoEntity videoEntity = saveVideo(videoMap, upEntity, stationValue);
		// 视频标签
		saveVideoTags(tagsList, videoEntity);
	}

	// 先新增UP主，再新增站点UP主，这里暂时不做UP主的合并逻辑判断。
	private UpEntity saveOrUpdateUp(Map<String, String> map, UpStationEntity upStationDataEntity) {
		String createTime = StringUtils.toString((System.currentTimeMillis()));
		String name = quoteReplacement(MapUtils.getString(map, "nick"));
		String introduction = quoteReplacement(MapUtils.getString(map, "info"));
		String upAvatar = MapUtils.getString(map, "avatar");

		UpEntity upEntity = new UpEntity();
		if(upStationDataEntity != null) {
			upEntity = upStationDataEntity.getUp();
		}
		// UP主
		upEntity.setName(name);
		upEntity.setAvatar(upAvatar);
		upEntity.setIntroduction(introduction);
		upEntity.setStatus(status);
		upEntity.setCreateTime(createTime);
		
		try {
			if(StringUtils.isNotBlank(upEntity.getId())) {
				upService.updateById(upEntity);
			} else {
				upService.insert(upEntity);
			}

			// up主关联站点
			String homeId = MapUtils.getString(map, "uin");
			String homeUrl = MapUtils.getString(map, "urlfull");
			Long upVideoCount = MapUtils.getLong(map, "videocount");

			UpStationEntity upStationEntity = new UpStationEntity();
			if(upStationDataEntity != null) {
				upStationEntity = upStationDataEntity;
			}
			upStationEntity.setUp(upEntity);
			upStationEntity.setHomeId(homeId);
			upStationEntity.setHomeUrl(homeUrl);
			upStationEntity.setName(name);
			upStationEntity.setUpIntroduction(introduction);
			upStationEntity.setUpAvatar(upAvatar);
			upStationEntity.setUpVideoCount(upVideoCount);
			upStationEntity.setStation(station);
			upStationEntity.setStatus(status);
			upStationEntity.setCreateTime(createTime);
			
			if(StringUtils.isNotBlank(upStationEntity.getId())) {
				upStationService.updateById(upStationEntity);
			} else {
				upStationService.insert(upStationEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return upEntity;
	}

	@Override
	protected VideoEntity saveOrUpdateVideo(Map<String, String> map, VideoEntity entity) {
		String title = quoteReplacement(MapUtils.getString(map, "title"));
		String coverImage = MapUtils.getString(map, "coverImage");
		Double duration = MapUtils.getDouble(map, "duration");
		String category = MapUtils.getString(map, "category");
		String albumId = MapUtils.getString(map, "albumId");
		Long viewCount = MapUtils.getLong(map, "viewCount");
		category = checkCategory(title, category);
		map.put("category", category);

		if (entity == null) {
			entity = new VideoEntity();
		}
		try {
			if (viewCount != null) {
				if (viewCount > 100000) {
					entity.setScore(8.4d);
				} else if (viewCount > 50000 && viewCount <= 100000) {
					entity.setScore(7.8d);
				} else if (viewCount <= 50000 && viewCount > 10000) {
					entity.setScore(7.3d);
				} else {
					entity.setScore(6.9d);
				}
			}
			GameEntity game = gameService.findByNameId(category, null);
			entity.setGame(game);
			entity.setTitle(title);
			entity.setCover(coverImage);
			entity.setDuration(duration);
			entity.setAlbum(new VideoAlbumEntity(albumId));

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

	private String checkCategory(String title, String category) {
		if (title.contains(GlobalConstant.SYS_GAME_1)) {
			category = GlobalConstant.SYS_GAME_1;
		} else if (title.contains(GlobalConstant.SYS_GAME_2)) {
			category = GlobalConstant.SYS_GAME_2;
		} else if (title.contains(GlobalConstant.SYS_GAME_3)) {
			category = GlobalConstant.SYS_GAME_3;
		} else if (title.contains(GlobalConstant.SYS_GAME_4) || title.toLowerCase().contains("lol")) {
			category = GlobalConstant.SYS_GAME_4;
		} else if (title.toLowerCase().contains(GlobalConstant.SYS_GAME_5)) {// 小写
			category = GlobalConstant.SYS_GAME_5;
		}

		return category;
	}

	// 视频站点
	@Override
	protected VideoStationEntity saveVideoStation(Map<String, String> map, VideoStationEntity entity) {
		String title = quoteReplacement(MapUtils.getString(map, "title"));
		String description = quoteReplacement(MapUtils.getString(map, "title"));
		String coverImage = MapUtils.getString(map, "coverImage");
		Double duration = MapUtils.getDouble(map, "duration");
		String vid = MapUtils.getString(map, "vid");
		String url = MapUtils.getString(map, "url");
		String flashUrl = "https://imgcache.qq.com/tencentvideo_v1/playerv3/TPout.swf?max_age=86400&v=20161117&vid=" + vid + "&auto=0";
		String category = MapUtils.getString(map, "category");
		String published = MapUtils.getString(map, "published");
		Long viewCount = MapUtils.getLong(map, "viewCount");

		try {
			if (StringUtils.isNotBlank(published) && !published.equals("null")) {
				entity.setPublished(String.valueOf(DateUtils.getStringDate(published, DateUtils._DEFAULT5).getTime()));
			} else {
				entity.setPublished(StringUtils.toString(System.currentTimeMillis()));
			}
			entity.setTitle(title);
			entity.setIntroduction(description);
			entity.setCover(coverImage);
			entity.setDuration(duration);
			entity.setVid(vid);
			entity.setUrl(url);
			entity.setFlashUrl(flashUrl);
			entity.setCategory(category);
			entity.setViewCount(viewCount);
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
		String commentContentArr = MapUtils.getString(map, "commentid");
		String vid = MapUtils.getString(map, "vid");
		if (StringUtils.isNotBlank(commentContentArr) && StringUtils.isNotBlank(vid)) {
			VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
			if (videoStationEntity == null) {// 视频不为空
				return;
			}

			List<Map> list = JSON.parseArray(commentContentArr, Map.class);
			VideoCommentEntity parentComment = null;
			for (Map paramMap : list) {
				String commentId = StringUtils.toString(MapUtils.getLong(paramMap, "id"));// 防止科学计数法
				String quoteId = StringUtils.toString(MapUtils.getLong(paramMap, "parent"));// 父级ID
				String commentContent = MapUtils.getString(paramMap, "content");
				String postDate = MapUtils.getString(paramMap, "time");
				Map<String, String> userMap = JSONObject.parseObject(MapUtils.getString(paramMap, "userinfo"), Map.class);
				String commentUserId = StringUtils.toString(MapUtils.getLong(userMap, "userid"));
				String commentUserName = quoteReplacement(MapUtils.getString(userMap, "nick"));

				if (StringUtils.isBlank(commentContent)) {
					commentContent = "空值";
				} else {
					commentContent = quoteReplacement(commentContent);
				}
				VideoCommentEntity videoCommentEntity = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),stationValue, commentId);
				if (videoCommentEntity == null) {
					videoCommentEntity = new VideoCommentEntity();
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
						videoCommentEntity.setCommentCreatetime(postDate + "000");
						videoCommentEntity.setVideo(videoStationEntity.getVideo());
						videoCommentEntity.setStation(station);
						videoCommentEntity.setStatus(status);
						videoCommentEntity.setLikeNum(0L);
						videoCommentService.insert(videoCommentEntity);
					} else {
						videoCommentService.updateById(videoCommentEntity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			int reqnum = MapUtils.getIntValue(map, "reqnum");
			int retnum = MapUtils.getIntValue(map, "retnum");
			if (reqnum == retnum) {// 返回值相同，进入下一页
				String commentid = MapUtils.getString(map, "last");
				String targetid = MapUtils.getString(map, "targetid");
				String pageUrl = "https://coral.qq.com/article/" + targetid + "/comment?commentid=" + commentid + "&reqnum=20&vid=" + vid + "&_=" + System.currentTimeMillis();
				page.addTargetRequest(pageUrl);
			}
		}
	}

	// 视频合集
	private void crawForAlbum(Page page) {
		Html html = page.getHtml();
		VideoAlbumEntity albumEntity = saveOrUpdate(page, html);
		String id = albumEntity.getHomeId();
		List<String> list = html.$(".year_slt_list .slt_list li", "text").all();
		if(list != null && !list.isEmpty()) {
			for (String year : list) {
				String pageUrl = "http://s.video.qq.com/get_playsource?otype=json&type=4&id=" + id + "&year=" + year + "&albumId=" + albumEntity.getId();
				page.addTargetRequest(pageUrl);
			}
		} else {
			String pageUrl = "http://s.video.qq.com/get_playsource?otype=json&type=4&id=" + id + "&year=2018&albumId=" + albumEntity.getId()+"&month=1&range=1-1000";
			page.addTargetRequest(pageUrl);
		}
	}

	private VideoAlbumEntity saveOrUpdate(Page page, Html html) {
		String url = page.getUrl().get();
		String homeUrl = url.substring(0, url.indexOf("?"));
		String homeId = url.substring(url.lastIndexOf("/") + 1, url.indexOf(".html"));
		String name = html.$(".video_title_cn a", "text").get();
		String introduction = html.$(".desc_txt span", "text").get();
		VideoAlbumEntity videoAlbum = videoAlbumSerivce.findByHome(homeUrl, "");
		if (videoAlbum == null) {
			videoAlbum = new VideoAlbumEntity();
		}

		videoAlbum.setHomeId(homeId);
		videoAlbum.setHomeUrl(homeUrl);
		videoAlbum.setName(name);
		videoAlbum.setIntroduction(introduction);

		try {
			if (StringUtils.isBlank(videoAlbum.getId())) {
				videoAlbum.setScore(6.9D);
				videoAlbum.setStatus(status);
				videoAlbumSerivce.insert(videoAlbum);
			} else {
				videoAlbumSerivce.updateById(videoAlbum);
			}

			String tagName = url.substring(url.lastIndexOf("=") + 1, url.length());
			ValueEntity valueEntity = saveSysValue(tagName);
			VideoAlbumValueEntity albumValueEntity = videoAlbumValueSerivce.findByVideoAlbumIdValueId(videoAlbum.getId(), valueEntity.getId(), null);
			if (albumValueEntity == null || StringUtils.isBlank(albumValueEntity.getId())) {
				albumValueEntity = new VideoAlbumValueEntity();

				albumValueEntity.setVideoAlbum(videoAlbum);
				albumValueEntity.setValue(valueEntity);
				albumValueEntity.setStatus(status);
				videoAlbumValueSerivce.insert(albumValueEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return videoAlbum;
	}

	public void crawl(Map<String, Object> paramMap) {
		try {
			String id = MapUtils.getString(paramMap, "id");
			String q = MapUtils.getString(paramMap, "q");
			String isList = MapUtils.getString(paramMap, "isList");
			if (StringUtils.isNotBlank(id)) {
				String url = "http://v.qq.com/x/page/" + id + ".html?category=&_=";
				Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
			}
			if (StringUtils.isNotBlank(q)) {
				String url = "https://v.qq.com/x/search/?filter=tabid%3D17&cur=1&q=" + q;
				Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
			}
			if ("true".equals(isList)) {
				List<String> list = new ArrayList<String>();
				list.add("http://v.qq.com/x/list/games?sort=5&offset=0&itype=974");// 主机单机
				list.add("http://v.qq.com/x/list/games?sort=5&offset=0&itype=975");// 手机游戏
				list.add("http://v.qq.com/x/list/games?sort=5&offset=0&itype=976");// 电子竞技
				list.add("http://v.qq.com/x/list/games?sort=5&offset=0&itype=977");// 达人解说
				list.add("http://v.qq.com/x/list/games?sort=5&offset=0&itype=980");// 游戏周边
				list.add("http://v.qq.com/x/list/games?sort=5&offset=0&itype=981");// 网络游戏

				List<String> urlList = null;
				for (String url : list) {
					urlList = new ArrayList<String>();
					urlList.add(url);
					Spider.create(this).setModeType(GlobalConstant.MODE_TYPE_ALWAYS).setInitUrls(urlList).addUrl(url + "&_=" + new Date().getTime()).thread(5).start();
				}
			}
		} catch (Exception e) {
			logger.error("爬虫出错===========================爬虫出错：", e);
		}
	}
}
