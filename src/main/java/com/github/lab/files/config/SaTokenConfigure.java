package com.github.lab.files.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import com.github.lab.files.common.Enums;
import com.github.lab.files.service.ConfigService;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Libs
 * @date 2024/4/12
 */
@Configuration
@Data
public class SaTokenConfigure implements WebMvcConfigurer
{

	@Resource
	private ConfigService configService;

	@Bean
	@Primary
	public SaTokenConfig getSaTokenConfigPrimary()
	{
		SaTokenConfig config = new SaTokenConfig();
		String key = configService.getConfigStrWithDefault(Enums.ConfigEnum.JWT_SECRET_KEY.getKey(), Enums.ConfigEnum.JWT_SECRET_KEY.getDefVal());
		String tokenName = configService.getConfigStrWithDefault(Enums.ConfigEnum.TOKEN_NAME.getKey(), Enums.ConfigEnum.TOKEN_NAME.getDefVal());
		config.setJwtSecretKey(key);
		config.setTokenName(tokenName);
		config.setIsPrint(false);
		return config;
	}

	@Bean
	public StpLogic getStpLogicJwt()
	{
		return new StpLogicJwtForSimple();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new SaInterceptor()).addPathPatterns("/api/admin/**");
	}

}
