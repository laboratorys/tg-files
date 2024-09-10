package com.github.lab.files.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

/**
 * @author Libs
 * @date 2024/9/10
 */
public class Enums
{
	@Getter
	@AllArgsConstructor
	public enum CacheEnum
	{
		FILE("file", 50L, Duration.ofDays(1));
		private String name;
		private Long maxSize;
		private Duration ttl;
	}
}
