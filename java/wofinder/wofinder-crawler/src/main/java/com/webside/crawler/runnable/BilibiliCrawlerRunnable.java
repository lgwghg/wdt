package com.webside.crawler.runnable;

import com.webside.common.GlobalConstant;
import com.webside.crawler.pageprocessor.impl.BilibiliVideoPageProcessor;
import com.webside.util.SpringBeanUtil;

public class BilibiliCrawlerRunnable implements Runnable{
	
	private String mode;
	
	public BilibiliCrawlerRunnable(String mode){
		this.mode = mode;
	}

	@Override
	public void run() {
		if(mode.equals(GlobalConstant.MODE_TYPE_ALWAYS)){
			BilibiliAlwaysCrawlerRunnable alwaysRunnable = new BilibiliAlwaysCrawlerRunnable();
			Thread alwaysThread = new Thread(alwaysRunnable,"B站持续性爬所有游戏视频");
			alwaysThread.start();
		}
	}
	
	class BilibiliAlwaysCrawlerRunnable implements Runnable{

		@Override
		public void run() {
			final BilibiliVideoPageProcessor bilibiliVideoPageProcessor = SpringBeanUtil.getBean(BilibiliVideoPageProcessor.class);
			bilibiliVideoPageProcessor.crawl(null);
		}
	}

}
