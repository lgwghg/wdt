/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.crawler.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;

import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.crawler.pageprocessor.VideoPageProcessor;
import com.webside.crawler.pageprocessor.impl.AcfunVideoPageProcessor;
import com.webside.crawler.pageprocessor.impl.BilibiliVideoPageProcessor;
import com.webside.crawler.pageprocessor.impl.IQiYiVideoPageProcessor;
import com.webside.crawler.pageprocessor.impl.QQVideoPageProcessor;
import com.webside.crawler.pageprocessor.impl.TuDouVideoPageProcessor;
import com.webside.crawler.pageprocessor.impl.WeiBoVideoPageProcessor;
import com.webside.crawler.pageprocessor.impl.YouKuVideoPageProcessor;
import com.webside.crawler.service.CrawlService;
import com.webside.redis.RedisManager;
import com.webside.util.StringUtils;

/**
 * 属性值服务实现类
 * 
 * @author zengxn
 * @date 2017-04-16 14:38:03
 */
@Service("crawlService")
public class CrawlServiceImpl implements CrawlService {
	@Autowired
	private RedisManager redisManager;

	public void crawlById(String stationId, String id, String q) {
		if (StringUtils.isBlank(stationId)) {
			throw new RuntimeException("stationId 站点不能为空~");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("q", q);

		crawl(stationId, paramMap);
	}

	public void crawlByStation(String stationId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isList", "true");

		crawl(stationId, paramMap);
	}

	private void crawl(String stationId, Map<String, Object> paramMap) {
		VideoPageProcessor videoPage = null;
		if (GlobalConstant.STATION_TYPE_1.equals(stationId)) {// A站
			videoPage = new AcfunVideoPageProcessor();
		} else if (GlobalConstant.STATION_TYPE_2.equals(stationId)) {// B站
			videoPage = new BilibiliVideoPageProcessor();
		} else if (GlobalConstant.STATION_TYPE_3.equals(stationId)) {// 优酷
			videoPage = new YouKuVideoPageProcessor();
		} else if (GlobalConstant.STATION_TYPE_4.equals(stationId)) {// 土豆
			videoPage = new TuDouVideoPageProcessor();
		} else if (GlobalConstant.STATION_TYPE_5.equals(stationId)) {// 爱奇艺
			videoPage = new IQiYiVideoPageProcessor();
		} else if (GlobalConstant.STATION_TYPE_6.equals(stationId)) {// QQ
			videoPage = new QQVideoPageProcessor();
		} else if (GlobalConstant.STATION_TYPE_7.equals(stationId)) {// 微博
			videoPage = new WeiBoVideoPageProcessor();
		}

		if (videoPage == null) {
			throw new RuntimeException("没有相匹配的站点");
		}

		final VideoPageProcessor _videoPage = videoPage;
		final Map<String, Object> _paramMap = paramMap;
		new Thread() {
			public void run() {
				_videoPage.crawl(_paramMap);
			}
		}.start();
	}

	public void setIdToRedis(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new RuntimeException("缓存的ids不能为空~");
		}
		String key = CacheConstant.VideoConstant.VIDEO_ID_REDIS_CACHE_SET;
		Set<String> set = null;
		if (redisManager.exists(key)) {
			set = redisManager.getSetByKey(key);
		}
		if (set == null || set.isEmpty()) {
			set = new HashSet<String>();
		}
		String[] array = ids.split(",");
		for (String id : array) {
			set.add(id);
		}
		redisManager.setVBySet(key, StringUtils.join(set.toArray(), ","));
	}

	public void crawlForRedis() {
		String key = CacheConstant.VideoConstant.VIDEO_ID_REDIS_CACHE_SET;
		if (redisManager.exists(key)) {
			Set<String> set = redisManager.getSetByKey(key);
			if (set != null && !set.isEmpty()) {
				Set<String> _set = new HashSet<String>();
				_set.addAll(set);
				for (String str : set) {
					String[] array = str.split("/");
					if (array.length == 2) {
						String stationId = array[0];
						String videoId = array[1];
						try {
							crawl(stationId, videoId);
							_set.remove(str);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				// 重定义Redis，清空再赋值
				redisManager.delSetByKey(key, "true");
				if (!_set.isEmpty()) {
					redisManager.setVBySet(key, StringUtils.join(_set.toArray(), ","));
				}
			}
		}
	}

	private void crawl(String stationId, String id) {
		VideoPageProcessor videoPage = null;
		String url = "";
		if (GlobalConstant.STATION_TYPE_1.equals(stationId)) {// A站
			videoPage = new AcfunVideoPageProcessor();
			url = "http://www.acfun.cn/v/ac" + id;
		} else if (GlobalConstant.STATION_TYPE_2.equals(stationId)) {// B站
			videoPage = new BilibiliVideoPageProcessor();
			// TODO url
		} else if (GlobalConstant.STATION_TYPE_3.equals(stationId)) {// 优酷
			videoPage = new YouKuVideoPageProcessor();
			url = "http://v.youku.com/v_show/id_" + id + ".html?category=&imageCode=";
		} else if (GlobalConstant.STATION_TYPE_4.equals(stationId)) {// 土豆 和 优酷
																		// 一样
			videoPage = new TuDouVideoPageProcessor();
			url = "http://v.youku.com/v_show/id_" + id + ".html?category=&imageCode=";
		} else if (GlobalConstant.STATION_TYPE_5.equals(stationId)) {// 爱奇艺
			videoPage = new IQiYiVideoPageProcessor();
			url = "http://www.iqiyi.com/" + id + ".html";
		} else if (GlobalConstant.STATION_TYPE_6.equals(stationId)) {// QQ
			videoPage = new QQVideoPageProcessor();
			url = "https://v.qq.com/x/page/" + id + ".html?category=";
		}

		if (videoPage == null) {
			throw new RuntimeException("没有相匹配的站点");
		}
		// run启动，跑完了才允许走下一步
		Spider.create(videoPage).setModeType("false").addUrl(url).thread(5).run();
	}

}
