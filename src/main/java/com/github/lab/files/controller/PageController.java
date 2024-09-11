package com.github.lab.files.controller;

import cn.hutool.core.io.IoUtil;
import com.github.lab.files.common.BizException;
import com.github.lab.files.model.db.FileInfo;
import com.github.lab.files.service.FilesService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class PageController
{

	@Resource
	private FilesService filesService;

	@RequestMapping("/")
	public String index()
	{
		return "index.html";
	}

	@GetMapping("/f/{id}")
	@Cacheable(value = "file", key = "#root.methodName+'_'+#id")
	public ResponseEntity<byte[]> download(@PathVariable String id)
	{
		HttpHeaders headers = new HttpHeaders();
		FileInfo fileInfo = filesService.getFileInfo(id);
		if (fileInfo == null)
		{
			throw new BizException("文件不存在哦~");
		}
		headers.setContentType(MediaType.parseMediaType(fileInfo.getMimeType()));
		headers.setContentDisposition(ContentDisposition.inline().filename(fileInfo.getName()).build());
		return ResponseEntity.ok().headers(headers).body(IoUtil.readBytes(filesService.downloadFile(fileInfo.getTgFileId())));
	}
}
