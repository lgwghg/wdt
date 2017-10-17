package com.webside.openid.exception;

import org.apache.shiro.authc.AuthenticationException;

public class SteamUnbindExceptiom extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SteamUnbindExceptiom() {
		super();
	}

	public SteamUnbindExceptiom(String message) {
		super(message);
	}

	public SteamUnbindExceptiom(Throwable cause) {
		super(cause);
	}

	public SteamUnbindExceptiom(String message, Throwable cause) {
		super(message, cause);
	}

	public SteamUnbindExceptiom(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	}

}