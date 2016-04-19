package org.enciende.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String status;

	public BusinessException(Throwable e){
		super(e);
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, String status) {
		super(msg);
		this.status = status;
	}

	public BusinessException(String msg, Throwable t) {
		super(msg, t);
	}

	public BusinessException(String msg, String status, Throwable t) {
		super(msg, t);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
