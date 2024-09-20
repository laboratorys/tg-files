package com.github.lab.files.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.util.StrUtil;
import com.github.lab.files.common.RespBody;
import com.github.lab.files.model.dto.LoginDto;
import com.github.lab.files.model.vo.*;
import com.github.lab.files.service.FilesService;
import com.github.lab.files.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController
{
	@Resource
	private FilesService filesService;

	@Resource
	private UserService userService;

	/**
	 * 用户登录
	 */
	@PostMapping(value = "/user/login")
	@Validated
	public RespBody<SaTokenInfo> userLogin(@RequestBody @Valid LoginDto userLoginDto)
	{
		return RespBody.ok(userService.userLogin(userLoginDto));
	}

	/**
	 * 用户信息
	 */
	@GetMapping(value = "/user/getLoginInfo")
	@SaCheckLogin
	public RespBody<LoginInfoVO> getLoginInfo()
	{

		return RespBody.ok(userService.getLoginInfo());
	}

	/**
	 * 用户登出
	 */
	@GetMapping(value = "/user/logout")
	@Validated
	@SaCheckLogin
	public RespBody<?> userLogout()
	{
		userService.userLogout();
		return RespBody.okMsg("用户已退出登录");
	}

	@GetMapping("/file/info")
	public RespBody<InfoVO> info()
	{
		return RespBody.ok(filesService.info());
	}

	@RequestMapping("/file/upload")
	public RespBody<List<FileInfoVO>> upload(
			@RequestParam(name = "files", required = false) MultipartFile[] files,
			@RequestParam(name = "url", required = false) String url,
			@RequestHeader("Authorization") String tokenHeader,
			@RequestParam(name = "token", required = false) String tokenParam)
	{
		return RespBody.ok(filesService.uploadFile(files, url, StrUtil.blankToDefault(tokenParam, tokenHeader)));
	}

	@GetMapping("/file/delete")
	public RespBody<?> deleteFile(
			@RequestParam(name = "id") String id,
			@RequestParam(name = "token") String token)
	{
		filesService.deleteFile(id, token);
		return RespBody.ok();
	}

	@GetMapping("/file/list")
	public RespBody<PageVO<UploadFileVO>> getFileList(
			@RequestParam(name = "token") String token,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize)
	{
		return RespBody.ok(filesService.fileList(token, pageNo, pageSize));
	}

	@GetMapping("/token")
	public RespBody<String> getToken()
	{
		return RespBody.ok(SaFoxUtil.getRandomString(32));
	}

}
