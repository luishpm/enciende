package org.enciende.exception;

public class ServiceException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private String status;

	public ServiceException(Throwable e){
		super(e);
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(String msg, String status) {
		super(msg);
		this.status = status;
	}

	public ServiceException(String msg, Throwable t) {
		super(msg, t);
	}

	public ServiceException(String msg, String status, Throwable t) {
		super(msg, t);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
