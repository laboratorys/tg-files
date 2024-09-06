package com.github.lab.files.controller;

import com.github.lab.files.common.BizException;
import com.github.lab.files.model.FileInfo;
import com.github.lab.files.service.FilesService;
import jakarta.annotation.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController
{

	@Resource
	private FilesService filesService;

	@RequestMapping("/")
	public String index()
	{
		return "index.html";
	}

	@RequestMapping("/admin")
	public String admin()
	{
		return "admin.html";
	}

	@GetMapping("/f/{id}")
	public ResponseEntity<InputStreamResource> download(@PathVariable String id)
	{
		HttpHeaders headers = new HttpHeaders();
		FileInfo fileInfo = filesService.getFileInfo(id);
		if (fileInfo == null)
		{
			throw new BizException("文件不存在哦~");
		}
		headers.setContentType(MediaType.parseMediaType(fileInfo.getMimeType()));
		headers.setContentDisposition(ContentDisposition.inline().filename(fileInfo.getName()).build());
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(filesService.downloadFile(fileInfo.getTgFileId())));
	}
}
