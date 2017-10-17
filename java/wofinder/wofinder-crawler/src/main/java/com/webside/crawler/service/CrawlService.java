/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.crawler.service;

/**
 * 属性值服务接口
 * 
 * @author zengxn
 * @date 2017-04-16 14:38:03
 */
public interface CrawlService {

	public static String BEANNAME = "crawlService";

	/**
	 * 通过ID 和 站点 爬数据
	 * 
	 * @param id
	 * @param stationId
	 */
	public void crawlById(String id, String stationId, String q);
	
	/**
	 * 通过 站点 爬数据
	 * @param stationId
	 */
	public void crawlByStation(String stationId);
	
	/**
	 * 往Redis里面放入视频ID，如 “1/123001” 代表A站下的123001视频
	 * @param ids
	 */
	public void setIdToRedis(String ids);
	
	/**
	 * 爬取Redis里面的id
	 */
	public void crawlForRedis();
}
