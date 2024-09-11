package com.github.lab.files.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import cn.hutool.core.collection.ListUtil;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Libs
 * @date 2024/4/12
 */
@Configuration
@Data
public class SaTokenConfigure implements WebMvcConfigurer
{

	private static final String JWT_SECRET_KEY = "LFSkByUAaBvzmfHI7IhEl1wey9XbB2H1";
	private static final String TOKEN_NAME = "Authoritarian";

	@Bean
	@Primary
	public SaTokenConfig getSaTokenConfigPrimary()
	{
		SaTokenConfig config = new SaTokenConfig();
		config.setJwtSecretKey(JWT_SECRET_KEY);
		config.setTokenName(TOKEN_NAME);
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
		/*List<String> whites = getWhiteList();
		registry.addInterceptor(new SaInterceptor((handler) -> {
			String path = SaHolder.getRequest().getRequestPath();
			CheckUtil.perm(path);
		})).addPathPatterns("/**");*/
	}

	private List<String> getWhiteList()
	{
		List<String> list = ListUtil.list(false,
				"/",
				"/favicon.ico",
				"/api/user/login");
		return list;
	}
}
