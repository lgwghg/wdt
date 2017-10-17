package com.webside.openid.client;

import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.openid.client.BaseOpenIdClient;
import org.pac4j.openid.credentials.OpenIdCredentials;

import com.webside.openid.profile.SteamOpenIdAttributesDefinition;
import com.webside.openid.profile.SteamOpenIdProfile;

/**
 * steam登录client
 * @author zrk  
 * @date 2016年4月15日 下午5:42:53
 */
public class SteamOpenIdClient extends BaseOpenIdClient<SteamOpenIdProfile> {

	public static final String STEAM_GENERIC_USER_IDENTIFIER = "http://steamcommunity.com/openid";

	public SteamOpenIdClient() {
		setName("SteamOpenIdClient");
	}

	@Override
	protected void internalInit(final WebContext context) {
		super.internalInit(context);
	}

	@Override
	protected BaseClient<OpenIdCredentials, SteamOpenIdProfile> newClient() {
		return new SteamOpenIdClient();
	}

	@Override
	protected String getUser(final WebContext context) {
		return STEAM_GENERIC_USER_IDENTIFIER;
	}

	@Override
	protected FetchRequest getFetchRequest() throws MessageException {
		final FetchRequest fetchRequest = FetchRequest.createFetchRequest();
		fetchRequest.addAttribute(SteamOpenIdAttributesDefinition.ID, "http://steamcommunity.com/openid", true);
		fetchRequest.addAttribute(SteamOpenIdAttributesDefinition.NICK_NAME, "http://steamcommunity.com/openid", true);

		logger.debug("fetchRequest: {}", fetchRequest);
		return fetchRequest;
	}

	@Override
	protected SteamOpenIdProfile createProfile(final AuthSuccess authSuccess) throws MessageException {
		final SteamOpenIdProfile profile = new SteamOpenIdProfile();

		if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
			final FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
			for (final String name : new SteamOpenIdAttributesDefinition().getAllAttributes()) {
				profile.addAttribute(name, fetchResp.getAttributeValue(name));
			}
		}
		return profile;
	}

	@Override
	public String toString() {
		return CommonHelper.toString(this.getClass(), "callbackUrl", this.callbackUrl, "name", getName());
	}
}
