package com.webside.crawler.made;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.webside.redis.RedisManager;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

public class RedisScheduler extends DuplicateRemovedScheduler implements  
MonitorableScheduler, DuplicateRemover{

	protected RedisManager redisManager;

    private static final String QUEUE_PREFIX = "queue_";

    private static final String SET_PREFIX = "set_";

    private static final String ITEM_PREFIX = "item_";

    public RedisScheduler(RedisManager redisManager) {
        this.redisManager = redisManager;
        setDuplicateRemover(this);
    }

    @Override
    public void resetDuplicateCheck(Task task) {
    	redisManager.delByKey(getSetKey(task));
    }

    @Override
    public boolean isDuplicate(Request request, Task task) {
    	return redisManager.getJedis().sadd(getSetKey(task), request.getUrl()) == 0;
    }

    @Override
    protected void pushWhenNoDuplicate(Request request, Task task) {
    	redisManager.setVByList(getQueueKey(task), request.getUrl());
        if (request.getExtras() != null) {
            String field = DigestUtils.shaHex(request.getUrl());
            String value = JSON.toJSONString(request);
            redisManager.setVByMap((ITEM_PREFIX + task.getUUID()), field, value);
        }
    }

    @Override
    public synchronized Request poll(Task task) {
    	String url = redisManager.getJedis().lpop(getQueueKey(task));
        if (url == null) {
            return null;
        }
        String key = ITEM_PREFIX + task.getUUID();
        String field = DigestUtils.shaHex(url);
        byte[] bytes = redisManager.getJedis().hget(key.getBytes(), field.getBytes());
        if (bytes != null) {
            Request o = JSON.parseObject(new String(bytes), Request.class);
            return o;
        }
        Request request = new Request(url);
        return request;
    }

    protected String getSetKey(Task task) {
        return SET_PREFIX + task.getUUID();
    }

    protected String getQueueKey(Task task) {
        return QUEUE_PREFIX + task.getUUID();
    }

    protected String getItemKey(Task task)
    {
        return ITEM_PREFIX + task.getUUID();
    }

    @Override
    public int getLeftRequestsCount(Task task) {
    	 Long size = redisManager.getLenByList(getQueueKey(task));
         return size.intValue();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
    	Long size = redisManager.getLenBySet(getSetKey(task));
        return size.intValue();
    }

}
