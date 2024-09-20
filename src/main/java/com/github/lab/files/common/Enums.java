package com.github.lab.files.common;

import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.crypto.digest.BCrypt;
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
		FILE("file", 100L, Duration.ofDays(7));
		private String name;
		private Long maxSize;
		private Duration ttl;
	}

	@Getter
	@AllArgsConstructor
	public enum StorageEnum
	{
		LOCAL("local", "local"),
		TELEGRAM("telegram", "Telegram"),
		TELEGRAPH("telegraph", "Telegraph"),
		S3("s3", "S3"),
		IPFS("ipfs", "IPFS");
		private String code;
		private String name;
	}

	@Getter
	@AllArgsConstructor
	public enum ConfigEnum
	{

		ADMIN_PASSWORD("admin_password", BCrypt.hashpw("1314520")),
		ADMIN_USER("admin_user", "admin"),
		JWT_SECRET_KEY("jwt_secret_key", SaFoxUtil.getRandomString(32)),
		TOKEN_NAME("token_name", "Authoritarian");
		private String key;
		private String defVal;
	}
}