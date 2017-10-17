package com.webside.crawler.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.crawler.service.CrawlService;

@Controller
@RequestMapping("/crawl/")
public class CrawlController extends BaseController {

	@Autowired
	private CrawlService crawlService;

	/**
	 * 单个视频爬取
	 * 
	 * @param id
	 * @param stationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crawlId")
	public Object crawlId(String stationId, String id, String q) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			crawlService.crawlById(stationId, id, q);
			map.put("success", Boolean.TRUE);
			map.put("message", "爬取成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "爬取失败：" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 整个站点爬取
	 * 
	 * @param stationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crawlStation", method = RequestMethod.POST)
	public Object crawlStation(String stationId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			crawlService.crawlByStation(stationId);
			map.put("success", Boolean.TRUE);
			map.put("message", "爬取成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "爬取失败：" + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}
	
	/**
	 * 往Redis里面放入视频ID，如 “1/123001” 代表A站下的123001视频
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "setIdToRedis")
	public Object setIdToRedis(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			crawlService.setIdToRedis(ids);
			map.put("success", Boolean.TRUE);
			map.put("message", "缓存成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "缓存失败：" + e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 爬取Redis里面的id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "crawlForRedis")
	public Object crawlForRedis() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			crawlService.crawlForRedis();
			map.put("success", Boolean.TRUE);
			map.put("message", "爬取缓存成功");
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "爬取缓存失败：" + e.getMessage());
			e.printStackTrace();
		}
		
		return map;
	}

}
