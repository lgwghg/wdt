package com.webside.common;

import com.webside.util.PropertyUtil;

/**
 * 全局常量/参数类
 * 
 * @author: Zengxn
 * @data: 2017年4月6日 18:36:25
 */
public class GlobalConstant {

	/**
	 * 错误信息提示
	 */
	public static final String RETURN_ERROR_MESSAGE = "服务器打盹了...";

	/**
	 * 发送消息通道值
	 */
	public static final String SEND_MESSAGE_PARAM = "console";

	/**
	 * 用户令牌属性
	 */
	public static final String USER_TOKEN = "token";

	public static final String USER_LOGIN_MSG_COOKIES = "wodota_user";

	public static final String COOKIE_FOLDER_NAME = "wodota";

	public static final String COOKIE_NICK_NAME = "cookie_nick_name";

	public static final String COOKIE_LOGIN_NAME = "cookie_login_name";

	public static final String COOKIE_USER_PHOTO = "cookie_user_photo";
	
	/**
	 * 搜索cookies
	 */
	public static final String SEARCH_HISTORY_COOKIE = "search_histroy";

	/**
	 * @title : 登陆用户seesion key
	 */
	public static final String LOGIN_USER_SESSION = "user";

	/**
	 * @title : 第三方用户绑定类型
	 */
	public static final String THIRD_LOGIN_TYPE_STEAM = "steam";
	public static final String THIRD_LOGIN_TYPE_QQ = "qq";
	public static final String THIRD_LOGIN_TYPE_WEIBO = "weibo";
	public static final String THIRD_LOGIN_TYPE_WECHAT = "wechat";

	/**
	 * @title : 是否删除分类
	 */
	public static final String DICTTYPE_IS_DELETE = "is_delete"; // 是否删除分类 0：正常
	public static final String DICTTYPE_IS_DELETE_0 = "0"; // 正常
	public static final String DICTTYPE_IS_DELETE_1 = "1"; // 删除

	/**
	 * @title : 有效状态
	 */
	public static final String DICTTYPE_EFFECTIVE_STATUS = "effective_status"; // 有效状态
	public static final String DICTTYPE_EFFECTIVE_STATUS_0 = "0"; // 0：无效
	public static final String DICTTYPE_EFFECTIVE_STATUS_1 = "1"; // 1 有效
	public static final String DICTTYPE_EFFECTIVE_STATUS_9 = "9"; // 9填充

	/**
	 * @title : 是/否
	 */
	public static final String DICTTYPE_YES_NO = "yes_no"; // 是/否 1 是 0 否
	public static final String DICTTYPE_YES_NO_0 = "0";
	public static final String DICTTYPE_YES_NO_1 = "1";

	/**
	 * @title : 角色类型
	 */
	public static final String ROLE_TYPE = "role_type"; // 角色类型，0：网站官方组 1：普通用户组
	public static final String ROLE_TYPE_0 = "0";
	public static final String ROLE_TYPE_1 = "1";

	/**
	 * @title : 作者名称类别
	 */
	public static final String UP_NAME_TYPE = "up_name_type"; // 作者名称类别
	public static final String UP_NAME_TYPE_1 = "1"; // 1：主名
	public static final String UP_NAME_TYPE_2 = "2"; // 2：次名

	/**
	 * @title : 所属站点
	 */
	public static final String STATION_TYPE = "station_type"; // 所属站点
	public static final String STATION_TYPE_0 = "0"; // 0：本系统
	public static final String STATION_TYPE_1 = "1"; // 1：A站
	public static final String STATION_TYPE_2 = "2"; // 2：B站
	public static final String STATION_TYPE_3 = "3"; // 3：优酷视频
	public static final String STATION_TYPE_4 = "4"; // 4：土豆视频
	public static final String STATION_TYPE_5 = "5"; // 5：爱奇艺视频
	public static final String STATION_TYPE_6 = "6"; // 6：腾讯视频
	public static final String STATION_TYPE_7 = "7"; // 7：微博

	public static final String MODE_TYPE_ALWAYS = "always";

	/**
	 * @title : 所属第三方
	 */
	public static final String THIRD_PARTY_TYPE = "third_party_type"; // 所属第三方
	public static final String THIRD_PARTY_TYPE_1 = "1"; // 1：微博
	public static final String THIRD_PARTY_TYPE_2 = "2"; // 2：斗鱼TV
	public static final String THIRD_PARTY_TYPE_3 = "3"; // 3：熊猫TV
	public static final String THIRD_PARTY_TYPE_4 = "4"; // 4：龙珠TV
	public static final String THIRD_PARTY_TYPE_5 = "5"; // 5：战旗TV
	public static final String THIRD_PARTY_TYPE_6 = "6"; // 6：全民TV

	/**
	 * @title : A站缺失的页数
	 */
	public static final String A_DEFAULT_VALUES = "A_default_values";
	public static final String Q_NOW_OFFSET = "Q_now_offset";

	/**
	 * @title: SEO类型
	 */
	public static final String SEO_CONFIG_TYPE = "seo_config_type";

	/**
	 * @title: 验证码相关key
	 */
	public static final String CAPTCHA_COUNTDOWN = "captcha_countdown_";
	public static final String CAPTCHA_MOBILE = "captcha_mobile_";
	public static final String CAPTCHA_EMAIL = "captcha_email_";

	/**
	 * @title:默认头像
	 */
	public static final String DEFAULT_PHOTO_URL = "/resources/wofinder/images/default.png";

	/**
	 * 环境标识：常量
	 */
	public interface EnvConstant {
		String DEV = "DEV";// 开发环境
		String TEST = "TEST";// 测试环境
		String PRD = "PRD";// 生产环境
	}

	/**
	 * socket地址
	 */
	public interface SocketAddressConstant {
		String DEV = "192.168.10.47:5678";// 测试环境
		String TEST = "192.168.10.47:5678";// 测试环境
		String PRD = "socket.wodota.com";// 生产环境
	}

	/**
	 * 环境标识
	 */
	public static final String ENV = PropertyUtil.getProInfo("webside.env");

	public static final String VERSION = PropertyUtil.getProInfo("webside.version");
	/**
	 * 人物二级信息标题类型，字典表
	 */
	public static final String UP_SECOND_TITLE_TYPE = "up_second_title_type";

	/**
	 * 阅读状态
	 */
	public static final String MESSAGE_STATE = "message_state"; // 阅读状态
	public static final String MESSAGE_STATE_1 = "1";// 已读
	public static final String MESSAGE_STATE_0 = "0";// 未读

	/**
	 * @title: 用户消息业务类型
	 */
	public static final String MESSAGE_BUSINESS_TYPE = "message_business_type"; // 1:评论
	public static final String MESSAGE_BUSINESS_TYPE_1 = "1";
	public static final String MESSAGE_BUSINESS_TYPE_TASK_2 = "2";
	public static final String MESSAGE_BUSINESS_TYPE_SYS_3 = "3";

	/**
	 * @title: 用户积分类型
	 */
	public static final String INTEGRAL_TYPE = "integral_type";
	public static final Integer INTEGRAL_TYPE_REGISTER_0 = 0;
	public static final Integer INTEGRAL_TYPE_SETPHOTO_1 = 1;
	public static final Integer INTEGRAL_TYPE_SETNICK_2 = 2;
	public static final Integer INTEGRAL_TYPE_INTERSET_3 = 3;
	public static final Integer INTEGRAL_TYPE_BIRTHDAY_4 = 4;
	public static final Integer INTEGRAL_TYPE_SEX_5 = 5;
	public static final Integer INTEGRAL_TYPE_COMPLETE_USERINFO_6 = 6;
	public static final Integer INTEGRAL_TYPE_COMPLETE_GRADE_7 = 7;
	public static final Integer INTEGRAL_TYPE_COMPLETE_COMMENT_8 = 8;

	/**
	 * 开关组 字典表
	 */
	public static final String SWITCH_CONFIG_GROUP = "sys_switch_group";
	public static final String SWITCH_CONFIG_BARRAGE = "1";// 弹幕开关

	public static final String SYS_HELP_TYPE = "sys_help_type";
	public static final String SYS_HELP_TYPE_1 = "1";// 系统帮助
	public static final String SYS_HELP_TYPE_2 = "2";// 用户反馈

	/**
	 * 游戏名称
	 */
	public static final String SYS_GAME_1 = "魔兽世界";
	public static final String SYS_GAME_2 = "王者荣耀";
	public static final String SYS_GAME_3 = "守望先锋";
	public static final String SYS_GAME_4 = "英雄联盟";
	public static final String SYS_GAME_5 = "dota2";

	/**
	 * 密保问题
	 */
	public static final String USER_SECURITY_QUESTION = "user_security_question";
	
	/**
	 * 视频标签举报类型
	 * 举报原因  0:内容不相关 1：敏感信息 2：恶意攻击 3：剧透内容 4：恶意删除
	 */
	public static final String VIDEO_TAG_INFORM_TYPE = "video_tag_inform_type";
	/** 举报原因  0:内容不相关 */
	public static final Integer VIDEO_TAG_INFORM_TYPE_0 = 0;
	/** 举报原因  1：敏感信息 */
	public static final Integer VIDEO_TAG_INFORM_TYPE_1 = 1;
	/** 举报原因  2：恶意攻击 */
	public static final Integer VIDEO_TAG_INFORM_TYPE_2 = 2;
	/** 举报原因  3：剧透内容 */
	public static final Integer VIDEO_TAG_INFORM_TYPE_3 = 3;
	/** 举报原因  4：恶意删除 */
	public static final Integer VIDEO_TAG_INFORM_TYPE_4 = 4;
	
	/**
	 * 视频标签举报状态
	 * 举报状态 0：正常   1：已举报  2：已处理
	 */
	public static final String VIDEO_TAG_INFORM_STATUS = "video_tag_inform_status";
	/** 举报状态 0：正常 */
	public static final Integer VIDEO_TAG_INFORM_STATUS_0 = 0;
	/** 举报状态 1：已举报 */
	public static final Integer VIDEO_TAG_INFORM_STATUS_1 = 1;
	/** 举报状态 2：已处理 */
	public static final Integer VIDEO_TAG_INFORM_STATUS_2 = 2;
	
	/**
	 * 视频标签操作类型
	 * 0：新增   1：点赞  2：取消点赞  4：删除
	 */
	public static final String VIDEO_TAG_OPERATE_TYPE = "video_tag_operate_type";
	/** 视频标签操作类型 0：新增 */
	public static final Integer VIDEO_TAG_OPERATE_TYPE_0 = 0;
	/** 视频标签操作类型 1：点赞 */
	public static final Integer VIDEO_TAG_OPERATE_TYPE_1 = 1;
	/** 视频标签操作类型 2：取消点赞 */
	public static final Integer VIDEO_TAG_OPERATE_TYPE_2 = 2;
	/** 视频标签操作类型 4：删除 */
	public static final Integer VIDEO_TAG_OPERATE_TYPE_4 = 4;
	

}
