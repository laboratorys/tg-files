package com.github.lab.files.common;

import org.springframework.http.HttpStatus;

public enum RespCode
{
	OK(HttpStatus.OK.value(), "成功"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "请求参数错误"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "未授权的访问"),
	FORBIDDEN(HttpStatus.FORBIDDEN.value(), "禁止访问"),
	INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");

	public final int code;
	public final String message;

	RespCode(int code, String message)
	{
		this.code = code;
		this.message = message;
	}
}
