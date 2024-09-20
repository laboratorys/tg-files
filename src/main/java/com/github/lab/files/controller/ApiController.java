package com.github.lab.files.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.lab.files.common.RespBody;
import com.github.lab.files.model.vo.PageVO;
import com.github.lab.files.model.vo.UploadFileVO;
import com.github.lab.files.service.FilesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@SaCheckLogin
public class ApiController
{
	@Resource
	private FilesService filesService;

	@GetMapping("/file/list")
	public RespBody<PageVO<UploadFileVO>> getAdminFileList(
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize)
	{
		return RespBody.ok(filesService.getAdminFileList(pageNo, pageSize));
	}

}
