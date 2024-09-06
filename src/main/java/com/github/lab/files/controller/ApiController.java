package com.github.lab.files.controller;

import com.github.lab.files.common.RespBody;
import com.github.lab.files.service.FilesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController
{
	@Resource
	private FilesService filesService;

	@RequestMapping("/file/upload")
	public RespBody<List<String>> upload(
			@RequestParam(name = "files", required = false) MultipartFile[] files,
			@RequestParam(name = "url", required = false) String url)
	{
		return RespBody.ok(filesService.uploadFile(files, url));
	}

}
