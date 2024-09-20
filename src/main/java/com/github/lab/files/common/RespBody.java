package com.github.lab.files.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBody<T>
{
	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 数据
	 */
	private T data;

	public static <T> RespBody<T> ok()
	{
		return ok(null);
	}

	public static <T> RespBody<T> ok(T data)
	{
		return new RespBody<>(RespCode.OK.code, RespCode.OK.message, data);
	}

	public static <T> RespBody<T> okMsg(String msg)
	{
		return new RespBody<>(RespCode.OK.code, msg, null);
	}

	public static <T> RespBody<T> bad(String msg)
	{
		return new RespBody<>(RespCode.BAD_REQUEST.code, msg, null);
	}

	public static <T> RespBody<T> error(String msg)
	{
		return new RespBody<>(RespCode.INTERNAL_ERROR.code, msg, null);
	}

	public static <T> RespBody<T> error(int code, String msg)
	{
		return new RespBody<>(code, msg, null);
	}
}
