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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;
import com.webside.video.model.VideoValueEntity;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class AcfunVideoPageProcessor extends VideoPageProcessor {

	public static final Logger logger = Logger.getLogger(AcfunVideoPageProcessor.class);

	// 视频详情
	private final String video_url = "http://www.acfun.cn/v/ac[0-9]\\d*";
	// 视频列表
	private final String list_url = "http://www.acfun.cn/list/getlist\\?channelId=(84|83|145|85|170|165|72)&sort=(0|1|2|3)&pageSize=\\d+&maxVid=\\d*&minVid=\\d*&isSecond=\\w+&pageNo=[0-9]\\d*&_=\\d+";
	// 第一次进列表
	private final String first_list_url = "http://www.acfun.cn/list/getlist\\?channelId=(84|83|145|85|170|165|72)&sort=(0|1|2|3)&pageSize=[0-9]\\d+&isFirst=true&pageNo=1&_=\\d+";
	// 评论回复
	private final String comment_url = "http://www.acfun.cn/comment_list_json.aspx\\?isNeedAllCount=true&contentId=[0-9]\\d*&currentPage=[0-9]\\d*";
	// 视频数量
	private final String count_url = "http://www.acfun.cn/content_view.aspx\\?contentId=[0-9]\\d*";
	// 视频标签
	private final String tags_url = "http://www.acfun.cn/member/collect_up_exist.aspx\\?contentId=[0-9]\\d*";
	// UP主页
	private final String space_url = "http://www.acfun.cn/u/[0-9]\\d*.aspx#page=1";

	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
	private DictEntity station = new DictEntity(GlobalConstant.STATION_TYPE_1);
	private String stationValue = GlobalConstant.STATION_TYPE_1;
	// A站最多显示500页*20，多余的就不统计了
	private final int totalNum = 500 * 20;

	@Override
	public void processData(Page page) {
		if (page.getUrl().regex(first_list_url).match()) {// 如果是视频列表
			String url = page.getUrl().get();
			logger.info("爬虫info查看信息=========第一次进入视频列表页" + url);
			String datalist = page.getRawText();

			JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
			Map<String, String> paramMap = JSONObject.parseObject(json.getString("params"), Map.class);
			paramMap.put("url", url);
			int pageCount = getNextPage(paramMap);
			if (pageCount != 0) {
				String strUrl = "maxVid=&minVid=&isSecond=false&pageNo=" + pageCount + "&_=" + System.currentTimeMillis();
				String pageUrl = url.replaceAll("isFirst=true&pageNo=[0-9]\\d*&_=\\d+", strUrl);
				page.addTargetRequest(pageUrl);
			}
		} else if (page.getUrl().regex(list_url).match()) {// 如果是视频列表
			String url = page.getUrl().get();
			logger.info("爬虫info查看信息=========进入视频列表页" + url);
			String datalist = page.getRawText();

			JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
			JSONArray jsonArray = json.getJSONArray("data");
			List<Map> archivesList = JSON.parseArray(jsonArray.toJSONString(), Map.class);
			if (archivesList != null && !archivesList.isEmpty()) {
				Collections.reverse(archivesList);
				String maxVid = url.substring(url.indexOf("maxVid=") + 7, url.indexOf("&minVid"));
				String minVid = url.substring(url.indexOf("minVid=") + 7, url.indexOf("&isSecond"));
				int _minVid = StringUtils.toInteger(minVid);
				int _maxVid = StringUtils.toInteger(maxVid);

				Map map = archivesList.get(0);
				// 本页最小值
				String thisMinVid = MapUtils.getString(map, "id");
				int _thisMinVid = StringUtils.toInteger(thisMinVid);
				// 本页最大值
				map = archivesList.get(archivesList.size() - 1);
				String thisMaxVid = MapUtils.getString(map, "id");
				int _thisMaxVid = StringUtils.toInteger(thisMaxVid);

				int pageCount = StringUtils.toInteger(url.substring(url.indexOf("&pageNo=") + 8, url.indexOf("&_=")));
				String channelId = MapUtils.getString(map, "channelId");
				// 获取视频总量
				long num = videoStationService.queryCountByReInvalid(stationValue, getCategoryName(channelId));
				if (num == 0l || pageCount == 500) {// 没有下载 或者 最后一页
					pageCount = pageCount - 1;
					maxVid = thisMaxVid;
					for (Map dataMap : archivesList) {
						crawlForVideo(dataMap, page);
					}
				} else {
					// 最小值为空， 强制跳转上一页
					if (StringUtils.isBlank(minVid)) {
						pageCount = pageCount + 1;
						minVid = thisMinVid;
					} else {
						Map<String, Object> paramMap = JSONObject.parseObject(json.getString("params"), Map.class);
						paramMap.put("url", url);
						paramMap.put("videoNum", num);
						VideoStationEntity entity = videoStationService.findByVideoStationId(null, stationValue, null, thisMinVid);
						// 最小值没数据
						if (entity == null || StringUtils.isBlank(entity.getId())) {
							// 判断 最小值 和 上一页传入过来的最小值是否一致
							if (_thisMinVid == _minVid) {
								pageCount = pageCount - 1;
								maxVid = thisMaxVid;

								refreshDefault(paramMap);
								for (Map dataMap : archivesList) {
									crawlForVideo(dataMap, page);
								}
							} else {
								pageCount = pageCount + 1;
								minVid = thisMinVid;
							}
						} else {
							pageCount = pageCount - 1;
							maxVid = thisMaxVid;
							// 上一页最大值 不一致，爬取数据
							if (_maxVid != _thisMaxVid) {
								refreshDefault(paramMap);
								for (Map dataMap : archivesList) {
									crawlForVideo(dataMap, page);
								}
							}
						}
					}
				}

				if(pageCount > 0) {
					String strUrl = "maxVid=" + maxVid + "&minVid=" + minVid + "&isSecond=false&pageNo=" + pageCount + "&_=" + System.currentTimeMillis();
					String pageUrl = url.replaceAll("maxVid=\\d*&minVid=\\d*&isSecond=\\w+&pageNo=[0-9]\\d*&_=\\d+", strUrl);
					page.addTargetRequest(pageUrl);
				}
			}
		} else if (page.getUrl().regex(video_url).match()) {// 获取视频详情页
			// logger.info("爬虫info查看信息=========进入视频详情页" + page.getUrl());
			String html = page.getHtml().regex("<script>var pageInfo .*</script>\\s*<div id=\"pageInfo\"").get();
			String str = html.substring(html.indexOf("{"), html.lastIndexOf("}") + 1);
			Map<String, String> map = JSONObject.parseObject(str, Map.class);
			// 单个视频数据
			crawlForVideo(map, page);
		} else if (page.getUrl().regex(count_url).match()) {// 获取视频播放数量
			// logger.info("爬虫info查看信息=========进入播放数量" + page.getUrl());
			String datalist = page.getRawText();
			String str = page.getUrl().toString();
			String vid = str.substring(str.indexOf("contentId") + 10, str.length());
			// 修改视频播放量
			updateVideoCount(JSON.parseArray(datalist, String.class), vid);
		} else if (page.getUrl().regex(space_url).match()) {// 获取up主页
			// logger.info("爬虫info查看信息=========进入up主页" + page.getUrl());
			String html = page.getHtml().regex("var UPUser.*var pageList").get();
			String str = html.substring(html.indexOf("{"), html.lastIndexOf("}") + 1);
			Map<String, String> map = JSONObject.parseObject(str, Map.class);
			// 更新up主
			updateUpStation(map, page);
		} else if (page.getUrl().regex(comment_url).match()) {// 获取视频评论
			// logger.info("爬虫info查看信息=========进入视频评论" + page.getUrl());
			String dataJson = page.getRawText();
			Map<String, String> dataMap = getDataMap(dataJson);
			String str = page.getUrl().toString();
			String vid = str.substring(str.indexOf("contentId") + 10, str.lastIndexOf("&"));
			dataMap.put("vid", vid);
			// 保存评论
			saveOrUpdateComment(dataMap, page);
		} else if (page.getUrl().regex(tags_url).match()) {// 获取视频标签
			// logger.info("爬虫info查看信息=========进入视频标签" + page.getUrl());
			String dataJson = page.getRawText();
			Map<String, String> dataMap = getDataMap(dataJson);
			String tagListStr = MapUtils.getString(dataMap, "tagList");
			String str = page.getUrl().toString();
			String vid = str.substring(str.indexOf("contentId") + 10, str.length());
			saveVideoTags(tagListStr, vid);
		}
	}

	// 解析爬下来的数据，获取data，转换成map格式
	private Map<String, String> getDataMap(String dataJson) {
		Map<String, String> map = JSONObject.parseObject(dataJson, Map.class);
		String data = MapUtils.getString(map, "data");
		Map<String, String> dataMap = JSONObject.parseObject(data, Map.class);

		return dataMap;
	}

	// 重置默认数量,使其下次进来，还在这一页
	private void refreshDefault(Map<String, Object> map) {
		String channelId = MapUtils.getString(map, "channelId");
		// 获取视频总量
		long num = MapUtils.getLong(map, "videoNum");

		int size = MapUtils.getInteger(map, "pageSize");
		int totalCount = MapUtils.getInteger(map, "totalCount");// 本次下载的【总个数】

		int defaultValues = 0;// 缺失的页数
		DictEntity dictEntity = dictService.findByTypeValueNoRedis(GlobalConstant.A_DEFAULT_VALUES, channelId);
		if (dictEntity != null && StringUtils.isNotBlank(dictEntity.getId())) {
			defaultValues = StringUtils.toInteger(dictEntity.getDescription());
		} else {
			dictEntity = new DictEntity(channelId);
			dictEntity.setType(GlobalConstant.A_DEFAULT_VALUES);
			dictEntity.setStatus(status);
			dictEntity.setLabel(getCategoryName(channelId));
			dictEntity.setCreateUser(new UserEntity("1"));
			dictService.insert(dictEntity);
		}
		String url = MapUtils.getString(map, "url");
		String pageNo = url.substring(url.indexOf("&pageNo=") + 8, url.indexOf("&_="));

		int nextNum = totalCount - StringUtils.toInteger(num) - StringUtils.toInteger(pageNo) * size;
		if (nextNum != defaultValues) {
			dictEntity.setDescription(StringUtils.toString(nextNum));
			dictService.updateById(dictEntity);
		}
	}

	private int getNextPage(Map<String, String> map) {
		String channelId = MapUtils.getString(map, "channelId");
		// 获取视频总量
		long num = videoStationService.queryCountByReInvalid(stationValue, getCategoryName(channelId));

		int size = MapUtils.getInteger(map, "pageSize");
		int totalCount = MapUtils.getInteger(map, "totalCount");// 本次下载的【总个数】

		int defaultValues = 0;// 缺失的页数
		DictEntity dictEntity = dictService.findByTypeValueNoRedis(GlobalConstant.A_DEFAULT_VALUES, channelId);
		if (dictEntity != null && StringUtils.isNotBlank(dictEntity.getId())) {
			defaultValues = StringUtils.toInteger(dictEntity.getDescription());
		} else {
			dictEntity = new DictEntity(channelId);
			dictEntity.setType(GlobalConstant.A_DEFAULT_VALUES);
			dictEntity.setStatus(status);
			dictEntity.setLabel(getCategoryName(channelId));
			dictEntity.setCreateUser(new UserEntity("1"));
			dictService.insert(dictEntity);
		}

		// 第一次 初始化丢失的数量
		if (num == 0l) {
			if (totalCount > totalNum && (totalCount - totalNum) < defaultValues) {
				defaultValues = totalCount - totalNum;
			} else if (defaultValues < 0) {
				defaultValues = 0;
			}
			dictEntity.setDescription(StringUtils.toString(defaultValues));
			dictService.updateById(dictEntity);
		}

		// 本次下载页数 = 总页数 - 已下载数 - 缺失数
		int nextNum = totalCount - StringUtils.toInteger(num) - defaultValues;
		if (nextNum > totalNum) {
			int count = nextNum - totalNum;// 只能丢掉多余的
			defaultValues = defaultValues + count;// 多余的归属缺失的
			dictEntity.setDescription(StringUtils.toString(defaultValues));
			dictService.updateById(dictEntity);

			nextNum = totalNum;
		} else if (nextNum < 0) {
			defaultValues = defaultValues + nextNum;
			dictEntity.setDescription(StringUtils.toString(defaultValues));
			dictService.updateById(dictEntity);
		}

		int nextPage = nextNum / size;
		if (nextNum % size != 0) {
			nextPage = nextPage + 1;
		}

		String url = MapUtils.getString(map, "url");
		String isSecond = url.substring(url.indexOf("&isSecond=") + 10, url.indexOf("&pageNo="));
		String pageNo = url.substring(url.indexOf("&pageNo=") + 8, url.indexOf("&_="));
		if ("true".equals(isSecond) && pageNo.equals(nextPage + "")) {// 第二次页数还是一样，强制转下一页
			nextPage = nextPage - 1;
			if (totalCount > totalNum) {
				defaultValues = defaultValues + size;
				dictEntity.setDescription(StringUtils.toString(defaultValues));
				dictService.updateById(dictEntity);
			}
		}
		if (nextPage < 0) {
			nextPage = 1;
		}

		return nextPage;
	}

	// 单个视频
	private void crawlForVideo(Map<String, String> map, Page page) {
		String vid = MapUtils.getString(map, "id");
		String homeId = MapUtils.getString(map, "userId");

		UpEntity upEntity = new UpEntity();
		UpStationEntity upStationDataEntity = upStationService.findByStationAndHomeId(stationValue, homeId);
		if (upStationDataEntity == null) {// up主站点为空
			upEntity = saveUp(map);
		} else {
			upEntity = upStationDataEntity.getUp();
		}

		map.put("vid", vid);
		saveVideo(map, upEntity, stationValue);

		// 加入其它链接
		String strurl = "http://www.acfun.cn/member/collect_up_exist.aspx?contentId=" + vid;
		page.addTargetRequest(strurl);
		strurl = "http://www.acfun.cn/content_view.aspx?contentId=" + vid;
		page.addTargetRequest(strurl);
		strurl = "http://www.acfun.cn/u/" + homeId + ".aspx#page=1";
		page.addTargetRequest(strurl);
		strurl = "http://www.acfun.cn/comment_list_json.aspx?isNeedAllCount=true&contentId=" + vid + "&currentPage=1";
		page.addTargetRequest(strurl);
	}

	// 先新增UP主，再新增站点UP主，这里暂时不做UP主的合并逻辑判断。
	private UpEntity saveUp(Map<String, String> map) {
		String createTime = StringUtils.toString((System.currentTimeMillis()));
		String name = quoteReplacement(MapUtils.getString(map, "username"));
		String upAvatar = MapUtils.getString(map, "userAvatar");
		String homeId = MapUtils.getString(map, "userId");
		String homeUrl = "http://www.acfun.cn/u/" + homeId + ".aspx#page=1";

		// UP主
		UpEntity upEntity = new UpEntity();
		try {
			upEntity.setName(name);
			upEntity.setAvatar(upAvatar);
			upEntity.setStatus(status);
			upEntity.setCreateTime(createTime);
			upService.insert(upEntity);

			// up主关联站点
			UpStationEntity upStationEntity = new UpStationEntity();
			upStationEntity.setUp(upEntity);
			upStationEntity.setHomeId(homeId);
			upStationEntity.setHomeUrl(homeUrl);
			upStationEntity.setName(name);
			upStationEntity.setUpAvatar(upAvatar);
			upStationEntity.setStation(station);
			upStationEntity.setStatus(status);
			upStationEntity.setCreateTime(createTime);
			upStationService.insert(upStationEntity);
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return upEntity;
	}

	// 视频
	@Override
	protected VideoEntity saveOrUpdateVideo(Map<String, String> map, VideoEntity videoEntity) {
		String title = quoteReplacement(MapUtils.getString(map, "title"));
		String coverImage = MapUtils.getString(map, "coverImage");
		Double duration = MapUtils.getDouble(map, "duration");
		Long viewCount = MapUtils.getLong(map, "viewCount");
		String category = MapUtils.getString(map, "channelName");
		if (StringUtils.isBlank(category)) {
			String channelId = MapUtils.getString(map, "channelId");
			category = getCategoryName(channelId);
		}

		try {
			// 游戏
			GameEntity game = gameService.findByNameId(category, null);
			if (videoEntity == null) {
				videoEntity = new VideoEntity();
			}
			if (viewCount > 100000) {
				videoEntity.setScore(8.4d);
			} else if (viewCount > 50000 && viewCount <= 100000) {
				videoEntity.setScore(7.8d);
			} else if (viewCount <= 50000 && viewCount > 10000) {
				videoEntity.setScore(7.3d);
			} else {
				videoEntity.setScore(6.9d);
			}
			videoEntity.setGame(game);
			videoEntity.setTitle(title);
			videoEntity.setCover(coverImage);
			videoEntity.setDuration(duration);

			if (StringUtils.isBlank(videoEntity.getId())) {
				videoEntity.setStatus(status);
				videoEntity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
				videoService.insert(videoEntity);
			} else {
				videoService.updateById(videoEntity);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return videoEntity;
	}

	// 视频站点
	@Override
	protected VideoStationEntity saveVideoStation(Map<String, String> map, VideoStationEntity entity) {
		String title = quoteReplacement(MapUtils.getString(map, "title"));
		String description = quoteReplacement(MapUtils.getString(map, "description"));
		String coverImage = MapUtils.getString(map, "coverImage");
		Double duration = MapUtils.getDouble(map, "duration");
		Long viewCount = MapUtils.getLong(map, "viewCount");
		String vid = MapUtils.getString(map, "id");
		String videoId = MapUtils.getString(map, "videoId");
		String url = "http://www.acfun.cn/v/ac" + vid;
		String flashUrl = "http://cdn.aixifan.com/player/ACFlashPlayer.out.swf?vid=" + videoId + "&ref=http://www.acfun.cn/v/ac" + vid;
		String category = MapUtils.getString(map, "channelName");
		if (StringUtils.isBlank(category)) {
			String channelId = MapUtils.getString(map, "channelId");
			category = getCategoryName(channelId);
		}
		String contributeTime = MapUtils.getString(map, "contributeTime");
		Long commentCount = MapUtils.getLong(map, "commentCount");

		try {
			entity.setTitle(title);
			entity.setIntroduction(description);
			if (description.length() > 500) {
				entity.setIntroduction(description.substring(0, 500));
			}
			entity.setCover(coverImage);
			entity.setDuration(duration);
			entity.setVid(vid);
			entity.setUrl(url);
			entity.setFlashUrl(flashUrl);
			entity.setCategory(category);
			if (StringUtils.toLong(contributeTime) > 0) {// 数字格式化的时间
				entity.setPublished(contributeTime);
			} else {
				entity.setPublished(String.valueOf(DateUtils.getStringDate(contributeTime, DateUtils._DEFAULT1).getTime())); // 2017-06-06
																																// 15:48
			}
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
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return entity;
	}

	private String getCategoryName(String channelId) {
		String name = "";
		if ("84".equals(channelId)) {
			name = "主机单机";
		} else if ("83".equals(channelId)) {
			name = "游戏集锦";
		} else if ("145".equals(channelId)) {
			name = "电子竞技";
		} else if ("85".equals(channelId)) {
			name = "英雄联盟";
		} else if ("170".equals(channelId)) {
			name = "守望先锋";
		} else if ("165".equals(channelId)) {
			name = "桌游卡牌";
		} else if ("72".equals(channelId)) {
			name = "mugen";
		}
		return name;
	}

	// 保存视频标签
	private void saveVideoTags(String tagListStr, String vid) {
		if (StringUtils.isNotBlank(tagListStr)) {
			JSONArray jsonTagArray = JSONArray.parseArray(tagListStr);// 把String转换为json
			List<Map> taglist = jsonTagArray.toJavaList(Map.class);
			if (taglist != null && taglist.size() > 0) {
				for (Map map : taglist) {
					String tagName = MapUtils.getString(map, "tagName");
					try {
						ValueEntity valueEntity = saveSysValue(tagName);

						VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
						VideoValueEntity videoValueEntity = videoValueService.findByVideoValueId(videoStationEntity.getVideo().getId(), valueEntity.getId(), null);
						if (videoValueEntity == null) {
							videoValueEntity = new VideoValueEntity();
							videoValueEntity.setValue(valueEntity);
							videoValueEntity.setVideo(videoStationEntity.getVideo());
							videoValueEntity.setStatus(status);
							videoValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
							videoValueService.insert(videoValueEntity);
						}
					} catch (Exception e) {
						logger.info(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 修改视频播放量等数据
	private void updateVideoCount(List<String> list, String vid) {
		if (list != null && !list.isEmpty()) {
			Long viewCount = StringUtils.toLong(list.get(0));// 视频播放量
			Long commentCount = StringUtils.toLong(list.get(1));// 视频评论量
			Long favoriteCount = StringUtils.toLong(list.get(5));// 视频收藏量

			try {
				VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
				videoStationEntity.setViewCount(viewCount);
				videoStationEntity.setCommentCount(commentCount);
				videoStationEntity.setFavoriteCount(favoriteCount);

				videoStationService.updateById(videoStationEntity);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// 更新up主
	private void updateUpStation(Map<String, String> map, Page page) {
		String homeId = MapUtils.getString(map, "userId");
		UpStationEntity upStationEntity = upStationService.findByStationAndHomeId(stationValue, homeId);

		if (upStationEntity != null) {// up站点不为空
			String name = quoteReplacement(MapUtils.getString(map, "username"));
			String upAvatar = MapUtils.getString(map, "userImg");
			String introduction = MapUtils.getString(map, "signature");
			Long upVideoCount = MapUtils.getLong(map, "contributeCount");// 视频数量
			Long upFansCount = MapUtils.getLong(map, "followedCount");// 粉丝数量
			Long upFriendCount = MapUtils.getLong(map, "followingCount");// 关注数量

			try {
				UpEntity upEntity = upStationEntity.getUp();
				upEntity.setName(name);
				upEntity.setAvatar(upAvatar);
				upEntity.setIntroduction(introduction);
				upService.updateById(upEntity);

				// up主关联站点
				upStationEntity.setName(name);
				upStationEntity.setUpIntroduction(introduction);
				upStationEntity.setUpAvatar(upAvatar);
				upStationEntity.setUpVideoCount(upVideoCount);
				upStationEntity.setUpFansCount(upFansCount);
				upStationEntity.setUpFriendCount(upFriendCount);
				Long upPlayCount = videoStationService.queryTotalViewCountByUpId(upEntity.getId());
				upStationEntity.setUpPlayCount(upPlayCount); // 播放数量

				upStationService.updateById(upStationEntity);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void saveOrUpdateComment(Map<String, String> map, Page page) {
		String commentContentArr = MapUtils.getString(map, "commentContentArr");
		String vid = MapUtils.getString(map, "vid");
		if (StringUtils.isNotBlank(commentContentArr) && StringUtils.isNotBlank(vid)) {
			VideoStationEntity videoStationEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
			// 查找下一页
			long commentnum = videoCommentService.queryCountByReInvalid(stationValue, videoStationEntity.getVideo().getId(), null, null);
			int size = MapUtils.getInteger(map, "pageSize");
			long total = MapUtils.getLong(map, "totalCount");

			int pageCount = StringUtils.toInteger((total - commentnum) / size);
			if (StringUtils.toInteger((total - commentnum) % size) != 0) {// 不能整除
				pageCount = pageCount + 1;
			}
			if (pageCount != 0) {
				String pageUrl = "http://www.acfun.cn/comment_list_json.aspx?isNeedAllCount=true&contentId=" + vid + "&currentPage=" + pageCount;
				page.addTargetRequest(pageUrl);
			}
			Map<String, Object> dataMap = JSON.parseObject(commentContentArr);
			Map<String, String> paramMap = null;
			VideoCommentEntity parentComment = null;
			for (Object value : dataMap.values()) {
				paramMap = (Map<String, String>) JSON.toJSON(value);
				String commentId = StringUtils.toString(MapUtils.getLong(paramMap, "cid"));// 防止科学计数法
				String commentContent = MapUtils.getString(paramMap, "content");
				String postDate = MapUtils.getString(paramMap, "postDate");
				String commentUserId = StringUtils.toString(MapUtils.getLong(paramMap, "userID"));
				String commentUserName = quoteReplacement(MapUtils.getString(paramMap, "userName"));
				String quoteId = StringUtils.toString(MapUtils.getLong(paramMap, "quoteId"));// 父级ID

				try {
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
								videoCommentEntity.setCommentCreatetime(StringUtils.toString(DateUtils.getStringDate(postDate, DateUtils._DEFAULT2).getTime()));
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
				} catch (Exception e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	public void crawl(Map<String, Object> paramMap) {
		try {
			String id = MapUtils.getString(paramMap, "id");
			String isList = MapUtils.getString(paramMap, "isList");
			if (StringUtils.isNotBlank(id)) {
				String url = "http://www.acfun.cn/v/ac" + id;
				Spider.create(this).setModeType("false").addUrl(url).thread(5).start();
			}

			if ("true".equals(isList)) {
				List<String> list = new ArrayList<String>();
				list.add("http://www.acfun.cn/list/getlist?channelId=84&sort=0&pageSize=20&isFirst=true&pageNo=1");// 主机单机
				list.add("http://www.acfun.cn/list/getlist?channelId=83&sort=0&pageSize=20&isFirst=true&pageNo=1"); // 游戏集锦
				list.add("http://www.acfun.cn/list/getlist?channelId=145&sort=0&pageSize=20&isFirst=true&pageNo=1");// 电子竞技
				list.add("http://www.acfun.cn/list/getlist?channelId=85&sort=0&pageSize=20&isFirst=true&pageNo=1"); // 英雄联盟
				list.add("http://www.acfun.cn/list/getlist?channelId=170&sort=0&pageSize=20&isFirst=true&pageNo=1");// 守望先锋
				list.add("http://www.acfun.cn/list/getlist?channelId=165&sort=0&pageSize=20&isFirst=true&pageNo=1");// 桌游卡牌
				list.add("http://www.acfun.cn/list/getlist?channelId=72&sort=0&pageSize=20&isFirst=true&pageNo=1");// mugen
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
