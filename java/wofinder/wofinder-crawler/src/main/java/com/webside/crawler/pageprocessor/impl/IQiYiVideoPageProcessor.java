package com.webside.crawler.pageprocessor.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

import com.alibaba.fastjson.JSONObject;
import com.webside.common.GlobalConstant;
import com.webside.crawler.pageprocessor.VideoPageProcessor;
import com.webside.dict.model.DictEntity;
import com.webside.sys.model.GameEntity;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpStationEntity;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;

@SuppressWarnings({ "unchecked" })
@Component
public class IQiYiVideoPageProcessor extends VideoPageProcessor {

	public static final Logger logger = Logger.getLogger(IQiYiVideoPageProcessor.class);

	// 查询视频列表
	private final String q_list_url = "http://so.iqiyi.com/so/q_.*_ctg_游戏_t_0_page_\\d+_p_1_qc_0_rd__site__m_4_bitrate_\\?maxVid=\\w*&minVid=\\w*&_=\\d+";
	// 视频详情
	private final String video_url = "http://www.iqiyi.com/(v|w)_\\w+.html";
	private final String data_url = "http://mixer.video.iqiyi.com/jp/mixin/videos/\\d+\\?albumId=\\w*&category=.*";
	// 评论列表
	private final String comment_url = "http://api-t.iqiyi.com/qx_api/comment/get_video_comments\\?sort=add_time&page_size_reply=30&reply_sort=add_time&need_reply=true&need_total=1&page_size=10&page=\\d+&tvid=\\d+&vid=\\w+";
	private final String first_comment_url = "http://api-t.iqiyi.com/qx_api/comment/get_video_comments\\?sort=add_time&page_size_reply=30&reply_sort=add_time&need_reply=true&need_total=1&page_size=10&isFirst=true&page=1&tvid=\\d+&vid=\\w+";

	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
	private DictEntity station = new DictEntity(GlobalConstant.STATION_TYPE_5);
	private String stationValue = GlobalConstant.STATION_TYPE_5;
	int size = 10;// 评论数量

	public void processData(Page page) {
		if (page.getUrl().regex(q_list_url).match()) {// 获取视频列表
			String url = page.getUrl().get();
			logger.info("爬虫info查看信息=========进入视频列表：" + url);
			Html html = page.getHtml();
			List<String> list = html.$(".list_item a.figure", "href").all();
			if (list != null && !list.isEmpty()) {
				String maxVid = url.substring(url.indexOf("maxVid=") + 7, url.indexOf("&minVid="));
				String minVid = url.substring(url.indexOf("minVid=") + 7, url.indexOf("&_="));
				String nowPage = url.substring(url.indexOf("page_") + 5, url.indexOf("_p_"));
				int pageCount = StringUtils.toInteger(nowPage);
				List<String> vidList = html.$(".list_item", "data-widget-searchlist-tvid").all();
				String category = url.substring(url.indexOf("q_") + 2, url.indexOf("_ctg_"));
				list = checkSource(list);
				if (list != null && !list.isEmpty()) {
					// 本页最小值（第一个插入的值）
					String thisMinVid = list.get(list.size() - 1);
					thisMinVid = thisMinVid.substring(thisMinVid.lastIndexOf("/") + 1, thisMinVid.indexOf(".html"));
					// 本页最大值（最后插入的值）
					String thisMaxVid = list.get(0);
					thisMaxVid = thisMaxVid.substring(thisMaxVid.lastIndexOf("/") + 1, thisMaxVid.indexOf(".html"));

					String nextPage = html.$(".mod-page a[data-key=\"down\"]", "href").get();
					if (StringUtils.isBlank(nextPage)) {// 没有下一页，就是最后一页
						pageCount = pageCount - 1;
						maxVid = thisMaxVid;

						addUrlToSpider(vidList, category, page);
					} else {
						// 最小值为空， 强制跳转上一页
						if (StringUtils.isBlank(minVid)) {
							pageCount = pageCount + 1;
							minVid = thisMinVid;
						} else {
							boolean flag = isExistsVid(thisMinVid);
							if (flag) {// 最小值已保存
								pageCount = pageCount - 1;
								maxVid = thisMaxVid;
								// 上一页最大值 不一致，爬取数据
								if (!thisMaxVid.equals(maxVid)) {
									addUrlToSpider(vidList, category, page);
								}
							} else {
								// 判断 最小值 和 上一页传入过来的最小值是否一致
								if (thisMinVid.equals(minVid)) {
									pageCount = pageCount - 1;
									maxVid = thisMaxVid;

									addUrlToSpider(vidList, category, page);
								} else {
									pageCount = pageCount + 1;
									minVid = thisMinVid;
								}
							}
						}
					}
				} else {
					if (vidList != null && !vidList.isEmpty()) {
						addUrlToSpider(vidList, category, page);
					}
					pageCount = pageCount - 1;
				}

				if (pageCount > 0) {
					String str = "maxVid=" + maxVid + "&minVid=" + minVid + "&_=" + System.currentTimeMillis();
					String pageUrl = url.replaceAll("maxVid=\\w*&minVid=\\w*&_=\\d+", str).replaceAll("page_\\d+_p", "page_" + pageCount + "_p");
					page.addTargetRequest(pageUrl);
				}
			}
		} else if (page.getUrl().regex(video_url).match()) {// 获取视频详情页
			Html html = page.getHtml();
			String str = html.regex("Q.PageInfo.playPageInfo.*?\\}").get();
			str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
			Map<String, String> map = JSONObject.parseObject(str, Map.class);

			// 查询数据
			String vid = MapUtils.getString(map, "tvId");
			String pageUrl = "http://mixer.video.iqiyi.com/jp/mixin/videos/" + vid + "?albumId=&category=";
			page.addTargetRequest(pageUrl);
		} else if (page.getUrl().regex(data_url).match()) {// 获取视频数据
			String url = page.getUrl().get();
			String category = url.substring(url.lastIndexOf("category=") + 9);
			String albumId = url.substring(url.lastIndexOf("albumId=") + 8, url.indexOf("&category="));
			String str = page.getRawText();
			str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
			Map<String, String> map = JSONObject.parseObject(str, Map.class);

			String title = MapUtils.getString(map, "name");
			String tvId = MapUtils.getString(map, "tvId");
			String vidHtml = MapUtils.getString(map, "vid");
			String homeUrl = MapUtils.getString(map, "url");
			String vid = homeUrl.substring(homeUrl.lastIndexOf("/") + 1, homeUrl.indexOf(".html"));
			String upStr = MapUtils.getString(map, "user");
			Map<String, String> upMap = JSONObject.parseObject(upStr, Map.class);
			String tagStr = MapUtils.getString(map, "categories");
			List<Map<String, String>> tagMap = JSONObject.parseObject(tagStr, List.class);
			List<String> tagsList = new ArrayList<String>();
			if (tagMap != null && tagMap.size() > 0) {
				for (Map<String, String> tag : tagMap) {
					tagsList.add(MapUtils.getString(tag, "name"));
				}
			}

			map.put("albumId", albumId);
			map.put("vid", vid);
			map.put("title", title);
			map.put("vidHtml", vidHtml);
			map.put("category", category);

			// 爬取数据
			crawlForVideo(upMap, map, tagsList);

			// 视频评论
			String pageUrl = "http://api-t.iqiyi.com/qx_api/comment/get_video_comments?sort=add_time&page_size_reply=30&reply_sort=add_time&need_reply=true&need_total=1&page_size=10";
			pageUrl += "&isFirst=true&page=1&tvid=" + tvId + "&vid=" + vid;
			page.addTargetRequest(pageUrl);
		} else if (page.getUrl().regex(first_comment_url).match()) {// 第一次进入评论列表
			String dataJson = page.getRawText();
			Map<String, String> map = getDataMap(dataJson);
			String str = page.getUrl().toString();
			String vid = str.substring(str.indexOf("&vid=") + 5);
			String tvid = str.substring(str.indexOf("&tvid=") + 6, str.indexOf("&vid="));

			VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
			long commentnum = videoCommentService.queryCountByReInvalid(stationValue, videoStationEntity.getVideo().getId(), null, null);
			long total = MapUtils.getLong(map, "count");

			int pageCount = StringUtils.toInteger((total - commentnum) / size);
			if (StringUtils.toInteger((total - commentnum) % size) != 0) {// 不能整除
				pageCount = pageCount + 1;
			}
			if (pageCount > 0) {
				String pageUrl = "http://api-t.iqiyi.com/qx_api/comment/get_video_comments?sort=add_time&page_size_reply=30&reply_sort=add_time&need_reply=true&need_total=1&page_size=10";
				pageUrl += "&page=" + pageCount + "&tvid=" + tvid + "&vid=" + vid;
				page.addTargetRequest(pageUrl);
			}
		} else if (page.getUrl().regex(comment_url).match()) {// 评论列表
			String dataJson = page.getRawText();
			Map<String, String> dataMap = getDataMap(dataJson);
			String str = page.getUrl().toString();
			String vid = str.substring(str.indexOf("&vid=") + 5);
			String tvid = str.substring(str.indexOf("&tvid=") + 6, str.indexOf("&vid="));
			dataMap.put("vid", vid);
			dataMap.put("tvid", tvid);
			// 单个视频数据
			saveComment(dataMap, page);
		}

	}

	// 验证来源，去掉不是爱奇艺的视频
	private List<String> checkSource(List<String> list) {
		List<String> _list = new ArrayList<String>();
		for (String str : list) {
			if (str.contains("www.iqiyi.com") && !str.contains("/a_")) {
				_list.add(str);
			}
		}

		return _list;
	}

	// 解析爬下来的数据，获取data，转换成map格式
	private Map<String, String> getDataMap(String dataJson) {
		Map<String, String> map = JSONObject.parseObject(dataJson, Map.class);
		String data = MapUtils.getString(map, "data");
		Map<String, String> dataMap = JSONObject.parseObject(data, Map.class);

		return dataMap;
	}

	private boolean isExistsVid(String vid) {
		boolean flag = false;
		String[] array = vid.split("_");
		String type = array[0];
		if ("a".equals(type)) {// 视频合辑
			VideoAlbumEntity entity = videoAlbumSerivce.findByHome("", vid);
			if (entity != null && StringUtils.isNotBlank(entity.getId())) {
				flag = true;
			}
		} else {// 视频
			VideoStationEntity entity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
			if (entity != null && StringUtils.isNotBlank(entity.getId())) {
				flag = true;
			}
		}

		return flag;
	}

	private void addUrlToSpider(List<String> vidList, String category, Page page) {
		int num = vidList.size();
		for (int i = num - 1; i >= 0; i--) {
			String vid = vidList.get(i);
			if (!"0".equals(vid)) {
				String url = "http://mixer.video.iqiyi.com/jp/mixin/videos/" + vid + "?albumId=&category=" + category;
				page.addTargetRequest(url);
			}
		}
	}

	// 单个视频
	private void crawlForVideo(Map<String, String> upMap, Map<String, String> videoMap, List<String> tagsList) {
		String homeId = MapUtils.getString(upMap, "id");// 用户ID
		UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
		// 保存或更新up主
		UpEntity upEntity = saveOrUpdateUp(upMap, upStationDataEntity);
		// 保存视频
		VideoEntity videoEntity = saveVideo(videoMap, upEntity, stationValue);
		// 视频标签
		saveVideoTags(tagsList, videoEntity);
	}

	// 先新增UP主，再新增站点UP主，这里暂时不做UP主的合并逻辑判断。
	private UpEntity saveOrUpdateUp(Map<String, String> map, UpStationEntity upStationDataEntity) {
		String createTime = StringUtils.toString((System.currentTimeMillis()));
		String name = quoteReplacement(MapUtils.getString(map, "name"));
		String introduction = quoteReplacement(MapUtils.getString(map, "verifiedReason"));
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
			String homeId = MapUtils.getString(map, "id");
			String homeUrl = MapUtils.getString(map, "profileUrl");
			Long upVideoCount = MapUtils.getLong(map, "videoCount");
			Long upFansCount = MapUtils.getLong(map, "followerCount");

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
			upStationEntity.setUpFansCount(upFansCount);
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
		String title = MapUtils.getString(map, "title");
		String coverImage = MapUtils.getString(map, "imageUrl");
		Double duration = MapUtils.getDouble(map, "duration");
		String category = MapUtils.getString(map, "category");
		String albumId = MapUtils.getString(map, "albumId");
		Long viewCount = MapUtils.getLong(map, "playCount");
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
		String description = quoteReplacement(MapUtils.getString(map, "description"));
		String coverImage = MapUtils.getString(map, "imageUrl");
		Double duration = MapUtils.getDouble(map, "duration");
		String vid = MapUtils.getString(map, "vid");
		String url = MapUtils.getString(map, "url");
		String tvId = MapUtils.getString(map, "tvId");
		String vidHtml = MapUtils.getString(map, "vidHtml");
		String flashUrl = "http://player.video.qiyi.com/" + vidHtml + "/0/0/" + vid + ".swf-albumId=" + tvId + "-tvId=" + tvId + "-isPurchase=0-cnId=8";
		String category = MapUtils.getString(map, "category");
		String published = MapUtils.getString(map, "issueTime");
		Long viewCount = MapUtils.getLong(map, "playCount");
		Long commentCount = MapUtils.getLong(map, "commentCount");

		try {
			if (StringUtils.isNotBlank(published) && !published.equals("null")) {
				entity.setPublished(published);
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
			entity.setCommentCount(commentCount);
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

	private void saveComment(Map<String, String> map, Page page) {
		String commentContentArr = MapUtils.getString(map, "comments");
		String vid = MapUtils.getString(map, "vid");
		if (StringUtils.isNotBlank(commentContentArr) && StringUtils.isNotBlank(vid)) {
			VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
			if (videoStationEntity == null) {
				return;
			}

			List<Map<String, String>> list = JSONObject.parseObject(commentContentArr, List.class);
			VideoCommentEntity videoCommentEntity = null;
			int num = list.size();
			for (int i = num - 1; i >= 0; i--) {
				Map<String, String> paramMap = list.get(i);
				String commentId = StringUtils.toString(MapUtils.getLong(paramMap, "contentId"));// 防止科学计数法
				String commentContent = MapUtils.getString(paramMap, "content");
				String postDate = MapUtils.getString(paramMap, "addTime");
				Map<String, String> userMap = JSONObject.parseObject(MapUtils.getString(paramMap, "userInfo"), Map.class);
				String commentUserId = StringUtils.toString(MapUtils.getLong(userMap, "uid"));
				String commentUserName = quoteReplacement(MapUtils.getString(userMap, "uname"));

				videoCommentEntity = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),stationValue, commentId);
				if (videoCommentEntity == null) {
					videoCommentEntity = new VideoCommentEntity();
					if (StringUtils.isBlank(commentContent)) {
						commentContent = "空值";
					} else {
						commentContent = quoteReplacement(commentContent.replaceAll("[\\x{10000}-\\x{10FFFF}]", "*"));
					}
					videoCommentEntity.setCommentId(commentId);
					videoCommentEntity.setCommentContent(commentContent);
					videoCommentEntity.setCommentUserId(commentUserId);
					videoCommentEntity.setCommentUserName(commentUserName);

					videoCommentEntity.setCommentCreatetime(postDate);
					videoCommentEntity.setVideo(videoStationEntity.getVideo());
					videoCommentEntity.setStation(station);
					videoCommentEntity.setStatus(status);
					videoCommentEntity.setLikeNum(0L);
					
					try {
						videoCommentService.insert(videoCommentEntity);

						String replyList = MapUtils.getString(paramMap, "replyList");
						saveReComment(replyList, videoStationEntity, videoCommentEntity);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 查找下一页
			long commentnum = videoCommentService.queryCountByReInvalid(stationValue, videoStationEntity.getVideo().getId(), null, null);
			long total = MapUtils.getLong(map, "count");

			int pageCount = StringUtils.toInteger((total - commentnum) / size);
			if (StringUtils.toInteger((total - commentnum) % size) != 0) {// 不能整除
				pageCount = pageCount + 1;
			}
			if (pageCount > 0) {
				String tvid = MapUtils.getString(map, "tvid");
				String pageUrl = "http://api-t.iqiyi.com/qx_api/comment/get_video_comments?sort=add_time&page_size_reply=30&reply_sort=add_time&need_reply=true&need_total=1&page_size=10";
				pageUrl += "&page=" + pageCount + "&tvid=" + tvid + "&vid=" + vid;
				page.addTargetRequest(pageUrl);
			}
		}
	}

	// 回复评论的评论
	private void saveReComment(String replyList, VideoStationEntity videoStationEntity, VideoCommentEntity commentParent) {
		if (StringUtils.isNotBlank(replyList)) {
			List<Map<String, String>> list = JSONObject.parseObject(replyList, List.class);
			if (list != null && !list.isEmpty()) {
				VideoCommentEntity videoCommentEntity = null;
				int num = list.size();
				for (int i = num - 1; i >= 0; i--) {
					Map<String, String> paramMap = list.get(i);
					String commentId = StringUtils.toString(MapUtils.getLong(paramMap, "id"));// 防止科学计数法
					String commentContent = MapUtils.getString(paramMap, "content");
					String postDate = MapUtils.getString(paramMap, "addTime");
					Map<String, String> userMap = JSONObject.parseObject(MapUtils.getString(paramMap, "userInfo"), Map.class);
					String commentUserId = StringUtils.toString(MapUtils.getLong(userMap, "uid"));
					String commentUserName = quoteReplacement(MapUtils.getString(userMap, "uname"));

					videoCommentEntity = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),stationValue, commentId);
					if (videoCommentEntity == null) {
						videoCommentEntity = new VideoCommentEntity();
						if (StringUtils.isBlank(commentContent)) {
							commentContent = "空值";
						} else {
							commentContent = quoteReplacement(commentContent.replaceAll("[\\x{10000}-\\x{10FFFF}]", "*"));
						}
						videoCommentEntity.setCommentId(commentId);
						videoCommentEntity.setCommentContent(commentContent);
						videoCommentEntity.setCommentUserId(commentUserId);
						videoCommentEntity.setCommentUserName(commentUserName);

						videoCommentEntity.setCommentCreatetime(postDate);
						videoCommentEntity.setCommentParent(commentParent);
						videoCommentEntity.setVideo(videoStationEntity.getVideo());
						videoCommentEntity.setStation(station);
						videoCommentEntity.setStatus(status);
						videoCommentEntity.setLikeNum(0L);
						videoCommentService.insert(videoCommentEntity);
					}
				}
			}
		}
	}

	@Override
	public void crawl(Map<String, Object> paramMap) {
		String id = MapUtils.getString(paramMap, "id");
		String q = MapUtils.getString(paramMap, "q");
		String isList = MapUtils.getString(paramMap, "isList");
		if (StringUtils.isNotBlank(id)) {
			String url = "http://www.iqiyi.com/" + id + ".html";
			Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
		}
		if (StringUtils.isNotBlank(q)) {
			String url = "http://so.iqiyi.com/so/q_" + q + "_ctg_游戏_t_0_page_2_p_1_qc_0_rd__site__m_4_bitrate_?maxVid=&minVid=&_=" + System.currentTimeMillis();
			Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
		}

		if ("true".equals(isList)) {
			List<String> list = new ArrayList<String>();
			list.add("http://so.iqiyi.com/so/q_王者荣耀_ctg_游戏_t_0_page_2_p_1_qc_0_rd__site__m_4_bitrate_?maxVid=&minVid=");
			list.add("http://so.iqiyi.com/so/q_英雄联盟_ctg_游戏_t_0_page_2_p_1_qc_0_rd__site__m_4_bitrate_?maxVid=&minVid=");
			list.add("http://so.iqiyi.com/so/q_我的世界_ctg_游戏_t_0_page_2_p_1_qc_0_rd__site__m_4_bitrate_?maxVid=&minVid=");
			list.add("http://so.iqiyi.com/so/q_dota2_ctg_游戏_t_0_page_2_p_1_qc_0_rd__site__m_4_bitrate_?maxVid=&minVid=");
			list.add("http://so.iqiyi.com/so/q_守望先锋_ctg_游戏_t_0_page_2_p_1_qc_0_rd__site__m_4_bitrate_?maxVid=&minVid=");
			List<String> urlList = null;
			for (String url : list) {
				urlList = new ArrayList<String>();
				urlList.add(url);
				Spider.create(this).setModeType(GlobalConstant.MODE_TYPE_ALWAYS).setInitUrls(urlList).addUrl(url + "&_=" + new Date().getTime()).thread(5).start();
			}
		}

	}

}
