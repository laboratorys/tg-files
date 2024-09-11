package com.github.lab.files.controller;

import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.util.StrUtil;
import com.github.lab.files.common.RespBody;
import com.github.lab.files.model.vo.FileInfoVO;
import com.github.lab.files.model.vo.InfoVO;
import com.github.lab.files.model.vo.PageVO;
import com.github.lab.files.model.vo.UploadFileVO;
import com.github.lab.files.service.FilesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController
{
	@Resource
	private FilesService filesService;

	@GetMapping("/public/file/info")
	public RespBody<InfoVO> info()
	{
		return RespBody.ok(filesService.info());
	}

	@RequestMapping("/public/file/upload")
	public RespBody<List<FileInfoVO>> upload(
			@RequestParam(name = "files", required = false) MultipartFile[] files,
			@RequestParam(name = "url", required = false) String url,
			@RequestHeader("Authorization") String tokenHeader,
			@RequestParam(name = "token", required = false) String tokenParam)
	{
		return RespBody.ok(filesService.uploadFile(files, url, StrUtil.blankToDefault(tokenParam, tokenHeader)));
	}

	@GetMapping("/public/file/delete")
	public RespBody<?> deleteFile(
			@RequestParam(name = "id") String id,
			@RequestParam(name = "token") String token)
	{
		filesService.deleteFile(id, token);
		return RespBody.ok();
	}

	@GetMapping("/public/file/list")
	public RespBody<PageVO<UploadFileVO>> getFileList(
			@RequestParam(name = "token") String token,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize)
	{
		return RespBody.ok(filesService.fileList(token, pageNo, pageSize));
	}

	@GetMapping("/public/token")
	public RespBody<String> getToken()
	{
		return RespBody.ok(SaFoxUtil.getRandomString(32));
	}

}
