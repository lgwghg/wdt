package com.webside.openid.profile;

import org.pac4j.core.profile.AttributesDefinition;
import org.pac4j.core.profile.converter.Converters;

/**
 * 返回数据转化器
 * @author zrk  
 * @date 2016年4月15日 下午5:43:38
 */
public class SteamOpenIdAttributesDefinition extends AttributesDefinition {

	public static final String ID = "id"; 									//用户UID 后被绑定为数据库业务id
	public static final String OPEN_ID = "openid"; // 字符串型的用户UID
	public static final String NICK_NAME = "nick_name"; // 用户昵称
	public static final String NAME  = "name";								//友好显示名称 
	public static final String GENDER = "gender";							//性别，m：男、f：女、n：未知 
	public static final String LOCATION = "location";						//地理未知 
	public static final String PROFILE_IMAGE_URL  = "profile_image_url";	//用户头像地址（中图），50×50像素 
	public static final String AVATAR_LARGE   = "avatar_large";				//用户头像地址（大图），180×180像素 
	public static final String AVATAR_HD    = "avatar_hd";					//用户头像地址（高清），高清头像原图 

	public SteamOpenIdAttributesDefinition() {
		addAttribute(ID, Converters.longConverter);
		addAttribute(OPEN_ID, Converters.stringConverter);
		addAttribute(NICK_NAME, Converters.stringConverter);
		addAttribute(NAME, Converters.stringConverter);
		addAttribute(GENDER, Converters.stringConverter);
		addAttribute(LOCATION, Converters.stringConverter);
		addAttribute(PROFILE_IMAGE_URL, Converters.stringConverter);
		addAttribute(AVATAR_LARGE, Converters.stringConverter);
		addAttribute(AVATAR_HD, Converters.stringConverter);
	}
}
