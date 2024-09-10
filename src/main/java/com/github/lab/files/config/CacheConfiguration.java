package com.github.lab.files.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.lab.files.common.Enums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Libs
 * @date 2024/9/10
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfiguration
{

	@Bean
	public CacheManager caffeineCacheManager()
	{
		SimpleCacheManager cacheManager = new SimpleCacheManager();

		List<CaffeineCache> caches = new ArrayList<>();
		for (Enums.CacheEnum c : Enums.CacheEnum.values())
		{
			Caffeine<Object, Object> caffeine = Caffeine.newBuilder().recordStats().maximumSize(c.getMaxSize());
			if (c.getTtl().getSeconds() > 0)
			{
				caffeine.expireAfterWrite(c.getTtl());
			}
			caches.add(new CaffeineCache(c.getName(), caffeine.build()));
		}
		cacheManager.setCaches(caches);
		return cacheManager;
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext context)
	{
		return args -> {
			Map<String, CacheManager> beans = context.getBeansOfType(CacheManager.class);
			beans.forEach((k, v) -> {
				log.info("{}:{}:{}", k, v.getClass().getName(), v.getCacheNames());
			});
		};
	}
}
