package com.github.lab.files.common;

import org.springframework.http.HttpStatus;

/**
 * 主要异常类(运行时异常)
 */
public class BizException extends RuntimeException
{
	private static final long serialVersionUID = 3642149887998012492L;

	public BizException(String message, Throwable cause)
	{
		super(message, cause);
		this.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	public BizException(String message)
	{
		super(message);
		this.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	public BizException(int code, String message)
	{
		super(message);
		this.setCode(code);
	}

	public BizException(int code, String message, Throwable cause)
	{
		super(message, cause);
		this.setCode(code);
	}

	private int code; // 错误编码

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}
}
