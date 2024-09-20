package com.github.lab.files.config;

import com.github.lab.files.common.Enums;
import com.github.lab.files.service.ConfigService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Libs
 * @date 2024/9/14
 */
@Component
@Slf4j
public class StartedEventListener implements ApplicationListener<ApplicationStartedEvent>
{
	@Resource
	private ConfigService configService;

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event)
	{
		Map<String, String> configMap = configService.getAllConfig();
		if (configMap.isEmpty())
		{
			//初始化配置
			log.info("init site config!");
			configService.setConfigMap(
					Arrays.stream(Enums.ConfigEnum.values()).
							filter(Objects::nonNull).
							collect(Collectors.toMap(Enums.ConfigEnum::getKey,
									Enums.ConfigEnum::getDefVal,
									(k1, k2) -> k1)));
		}
	}
}
