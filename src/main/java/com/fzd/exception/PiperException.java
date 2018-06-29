package com.fzd.exception;

public class PiperException extends RuntimeException{

	/**
	 * 说明：传递了code，
	 */
	private static final long serialVersionUID = -1708941570344350043L;

	private Long stateId;

	//各业务所对应的错误code
	private Integer code;


	public PiperException() {
		super();
	}

	public PiperException(String message) {
		super(message);
	}

	public PiperException(Integer code) {
		this.code = code;
	}
	
	public PiperException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public PiperException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public PiperException(Long stateId, Integer code, String message) {
		super(message);
		this.stateId = stateId;
		this.code = code;
	}

	public PiperException(Long stateId, Integer code, String message, Throwable cause) {
		super(message,cause);
		this.stateId = stateId;
		this.code = code;
	}

	public PiperException(String message, Throwable cause) {
		super(message, cause);
	}

	public PiperException(Throwable cause) {
		super(cause);
	}

	public Long getStateId() {
		return stateId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
}
