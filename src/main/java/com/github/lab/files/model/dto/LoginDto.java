package com.github.lab.files.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录参数
 *
 * @author Libs
 * @date 2023/9/25
 */
@Data
public class LoginDto
{

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String userName;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;
}
