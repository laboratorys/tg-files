package com.github.lab.files.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.github.lab.files.common.BizException;
import com.github.lab.files.common.Enums;
import com.github.lab.files.model.dto.LoginDto;
import com.github.lab.files.model.vo.LoginInfoVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Libs
 * @date 2024/9/13
 */
@Service
public class UserService
{
	@Resource
	private ConfigService configService;

	public SaTokenInfo userLogin(LoginDto loginDto)
	{
		Map<String, String> configMap = configService.getAllConfig();
		String userName = configMap.get(Enums.ConfigEnum.ADMIN_USER.getKey());
		String password = configMap.get(Enums.ConfigEnum.ADMIN_PASSWORD.getKey());
		boolean check = BCrypt.checkpw(StrUtil.cleanBlank(loginDto.getPassword()), password);
		if (check && userName.equals(loginDto.getUserName()))
		{
			StpUtil.login(userName, 4 * DateUnit.WEEK.getMillis());
			return StpUtil.getTokenInfo();
		}
		else
		{
			throw new BizException("用户名或密码错误");
		}
	}

	public void userLogout()
	{
		StpUtil.logout();
	}

	public LoginInfoVO getLoginInfo()
	{
		LoginInfoVO loginInfoVO = new LoginInfoVO();
		if (StpUtil.isLogin())
		{
			loginInfoVO.setUserName(configService.getConfigStr("admin_user"));
			loginInfoVO.setToken(StpUtil.getTokenValue());
			loginInfoVO.setIsLogin(true);
		}
		else
		{
			loginInfoVO.setIsLogin(false);
		}
		return loginInfoVO;
	}
}
