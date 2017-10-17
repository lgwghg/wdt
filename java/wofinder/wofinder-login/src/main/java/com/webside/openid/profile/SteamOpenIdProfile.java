package com.webside.openid.profile;

import org.pac4j.core.profile.AttributesDefinition;
import org.pac4j.openid.profile.OpenIdProfile;

/**
 * steam用户信息
 * 
 * @author zrk
 * @date 2016年4月15日 下午5:44:10
 */
public class SteamOpenIdProfile extends OpenIdProfile {

	private transient final static AttributesDefinition ATTRIBUTES_DEFINITION = new SteamOpenIdAttributesDefinition();

	@Override
    protected AttributesDefinition getAttributesDefinition() {
		return ATTRIBUTES_DEFINITION;
    }

	@Override
	public String getId() {
		return (String) getAttribute(SteamOpenIdAttributesDefinition.ID);
	}

	@Override
	public String getDisplayName() {
		return (String) getAttribute(SteamOpenIdAttributesDefinition.NICK_NAME);
	}
}
