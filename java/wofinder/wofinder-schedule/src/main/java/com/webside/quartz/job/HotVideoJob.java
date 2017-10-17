package com.webside.quartz.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;

import com.webside.common.CacheConstant;
import com.webside.redis.RedisManager;
import com.webside.util.StringUtils;
import com.webside.video.service.VideoService;

/**
 * 重置热门视频列表任务
 * 
 * @author zengxn
 * @date 2017年7月14日 14:32:18
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HotVideoJob implements InterruptableJob {

	private static Logger logger = Logger.getLogger(HotVideoJob.class);

	/**
	 * 视频Service 定义
	 */
	@Autowired
	VideoService videoSerivce;

	/**
	 * redis 定义
	 */
	@Autowired
	private RedisManager redisManager;

	private boolean interrupted = false;

	private JobKey jobKey = null;

	public HotVideoJob() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 查询缓存状态
		String config = redisManager.get(CacheConstant.QUARTZ_HOT_VIDEO_JOB_STATUS, String.class);
		if (StringUtils.isNotBlank(config) && config.equals("false")) {// 还未执行
			// 设置为执行状态
			redisManager.set(CacheConstant.QUARTZ_HOT_VIDEO_JOB_STATUS, "true");
			jobKey = context.getJobDetail().getKey();
			if (context.isRecovering()) {
				logger.info("重置热门视频列表job 已恢复");
			} else {
				logger.info("重置热门视频列表job 开始执行");
				try {
					if (!interrupted) {
						redisManager.delByKey(CacheConstant.VideoConstant.VIDEO_HOT_LIST);
						videoSerivce.queryHotVideo(6);
						logger.info("重置热门视频列表成功");
					} else {
						logger.info("用户停止了重置热门视频列表任务job: " + jobKey);
						return;
					}
				} catch (Exception e) {
					logger.error("重置热门视频列表异常", e);
				} finally {
					redisManager.set(CacheConstant.QUARTZ_HOT_VIDEO_JOB_STATUS, "false");
					logger.info("重置热门视频列表job 执行完毕");
				}
			}
		}
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		interrupted = true;
		logger.info("用户停止了重置热门视频列表任务job: " + jobKey);
	}

}
