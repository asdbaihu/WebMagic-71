package com.fzd.util;

public class EsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1708941570344350043L;

	public EsException() {
		super();
	}

	public EsException(String message) {
		super(message);
	}

	public EsException(String message, Throwable cause) {
		super(message, cause);
	}

	public EsException(Throwable cause) {
		super(cause);
	}
}
