package com.github.lab.files.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Libs
 * @date 2023/9/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoVO
{
	/**
	 * 昵称
	 */
	private String userName;
	/**
	 * 是否已登录
	 */
	private Boolean isLogin;

	/**
	 * token
	 */
	private String token;

}
