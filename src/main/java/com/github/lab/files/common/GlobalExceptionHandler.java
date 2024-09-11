package com.github.lab.files.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

	@ExceptionHandler(Exception.class)
	public RespBody<?> handleException(Exception e)
	{
		//log.error(e.getMessage(), e);
		return RespBody.error(RespCode.INTERNAL_ERROR.message);
	}

	@ExceptionHandler(BizException.class)
	public RespBody<?> handleBizException(BizException e)
	{
		log.error(e.getMessage(), e);
		return RespBody.error(e.getMessage());
	}

}
