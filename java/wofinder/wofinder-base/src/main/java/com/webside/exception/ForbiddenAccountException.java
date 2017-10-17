package com.webside.exception;

import org.apache.shiro.authc.DisabledAccountException;

/**
 * 禁止访问exception
 * 
 * @author tianguifang
 * 
 */
public class ForbiddenAccountException extends DisabledAccountException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2131946294707278840L;

	/**
	 * Creates a new ForbiddenAccountException.
	 */
	public ForbiddenAccountException() {
		super();
	}

	/**
	 * Constructs a new ForbiddenAccountException.
	 * 
	 * @param message
	 *            the reason for the exception
	 */
	public ForbiddenAccountException(String message) {
		super(message);
	}

	/**
	 * Constructs a new ForbiddenAccountException.
	 * 
	 * @param cause
	 *            the underlying Throwable that caused this exception to be
	 *            thrown.
	 */
	public ForbiddenAccountException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new ForbiddenAccountException.
	 * 
	 * @param message
	 *            the reason for the exception
	 * @param cause
	 *            the underlying Throwable that caused this exception to be
	 *            thrown.
	 */
	public ForbiddenAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
