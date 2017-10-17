package com.webside.common;

/**
 * @title : 缓存常量类
 * @author zengxn
 */
public interface CacheConstant {

	// 缓存时长配置
	public static final int ONE_MINITES = 60; // 有效期1分钟
	public static final int TWO_MINITES = 120; // 有效期2分钟
	public static final int FIVE_MINITES = 300; // 有效期5分钟
	public static final int TEN_MINITES = 600; // 有效期10分钟
	public static final int THIRTY_MINITES = 1800; // 有效期30分钟

	public static final int ONE_DAY = 86400;// 有效期1天
	public static final int THREE_DAY = 259200;// 有效期3天
	public static final int FIVE_DAY = 432000;// 有效期5天
	public static final int SEVEN_DAY = 604800;// 有效期7天
	public static final int THIRTY_DAY = 2592000;// 有效期30天

	// 缓存key
	public static final String CACHE_CODE_LIST = "system:code_list"; // 标准代码缓存key前缀

	// 重置热门视频列表任务标识
	public static final String QUARTZ_HOT_VIDEO_JOB_STATUS = "quartz_hv_job_status";

	/**
	 * 用户缓存常量
	 * 
	 * @author suyan
	 * 
	 */
	public interface UserConstant {
		String USER_REDIS_CACHE_KEY = "u:";
		String USER_ROLE_REDIS_CACHE_KEY = "u:r:";
	}

	/**
	 * 视频缓存常量
	 */
	public interface VideoConstant {
		String VIDEO_MAP_REDIS_CACHE_KEY = "v:";
		String VIDEO_REDIS_CACHE_KEY = "v";
		String VIDEO_STATION_REDIS_CACHE_KEY = "vs:";
		String VIDEO_VALUE_REDIS_CACHE_KEY = "vv:";
		String VIDEO_HOT_LIST = "hv";
		String VIDEO_ID_REDIS_CACHE_SET = "vid";
	}

	/**
	 * 视频评论缓存常量
	 */
	public interface VideoCommentConstant {
		String VIDEO_COMMENT_SORTSET_REDIS_CACHE_KEY = "v:vc:z:";
		String VIDEO_COMMENT_HASH_REDIS_CACHE_KEY = "v:vc:h:";
	}
	
	/**
	 * 视频评论缓存常量
	 */
	public interface VideoAlbumConstant {
		String VIDEO_ALBUM_REDIS_CACHE_KEY = "vb:";
	}
}
