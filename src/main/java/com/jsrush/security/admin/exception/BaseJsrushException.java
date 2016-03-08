package com.jsrush.security.admin.exception;

/**
 * 基础异常定义
 * @author sunburst
 *
 */
public class BaseJsrushException extends Exception {

	private static final long serialVersionUID = -1468755724942098713L;

	private Integer errorCode;
	
	private String errorMsg;
	
	 
	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public BaseJsrushException() {
		super();
	}
	
	public BaseJsrushException(Integer erorCode) {
		super();
		this.errorCode=erorCode;
	}
	
	public BaseJsrushException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
	
	public BaseJsrushException(String errorMsg, Integer erorCode) {
		super(errorMsg);
		this.errorCode=erorCode;
		this.errorMsg = errorMsg;
	}
	

	public BaseJsrushException(String errorMsg, Integer erorCode, Throwable e) {
		super(errorMsg, e);
		this.errorCode=erorCode;
		this.errorMsg = errorMsg;
	}
	
	
}
