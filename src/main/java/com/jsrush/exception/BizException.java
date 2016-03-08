package com.jsrush.exception;

/**
 * 	业务异常 
 * @author sunburst
 *
 */
public class BizException extends Exception {

	private static final long serialVersionUID = -4078868027054405453L;

	private Throwable cause;
	
	private int errorCode = 0;
	
	@Override
	public Throwable getCause() {
		return this.cause;
	}
	
	/**
	 * 	获取错误码
	 * @return
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
	 * @param msg 异常信息
	 */
	public BizException(String msg) {
		super(msg);
	}
	
	/**
	 * 
	 * @param msg 				异常信息
	 * @param errorCode		错误码
	 */
	public BizException(String msg, int errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	/**
	 * 
	 * @param t	异常对象
	 */
	public BizException(Throwable t) {
		super(t);
		this.cause = t;
	}
	
	/**
	 * 
	 * @param t				异常对象
	 * @param errorCode	错误码
	 */
	public BizException(Throwable t, int errorCode) {
		super(t);
		this.cause = t;
		this.errorCode = errorCode;
	}
	
	/**
	 * 
	 * @param msg	异常信息
	 * @param t		异常对象
	 */
	public BizException(String msg, Throwable t) {
		super(msg, t);
		this.cause = t;
	}
	
	/**
	 * 
	 * @param msg			异常信息
	 * @param t				异常对象
	 * @param errorCode 	错误码
	 */
	public BizException(String msg, Throwable t, int errorCode) {
		super(msg, t);
		this.cause = t;
		this.errorCode = errorCode;
	}
	
}
