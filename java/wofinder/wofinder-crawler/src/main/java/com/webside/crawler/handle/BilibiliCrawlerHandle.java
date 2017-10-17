package com.webside.crawler.handle;

import com.webside.common.GlobalConstant;
import com.webside.crawler.runnable.BilibiliCrawlerRunnable;

public class BilibiliCrawlerHandle {
	
	public static boolean BilibiliAlwaysCrawler(){
		boolean flag = false;
		BilibiliCrawlerRunnable biliCrawlerRunnable = new BilibiliCrawlerRunnable(GlobalConstant.MODE_TYPE_ALWAYS);
		Thread taskThread = new Thread(biliCrawlerRunnable, "BilibiliCrawlerRunnable[新增B站持续抓所有游戏视频]");
		taskThread.start();
		flag = true;
		return flag;
	}
	
	
}
