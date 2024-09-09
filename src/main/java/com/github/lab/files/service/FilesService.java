package com.github.lab.files.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.github.lab.files.common.BizException;
import com.github.lab.files.config.MyFileBot;
import com.github.lab.files.model.FileInfo;
import com.github.lab.files.repository.FileInfoRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class FilesService
{
	@Resource
	private MyFileBot bot;

	@Resource
	private FileInfoRepository fileInfoRepository;

	public List<String> uploadFile(MultipartFile[] files, String url)
	{
		List<String> idList = new ArrayList<>();
		if (StrUtil.isNotBlank(url))
		{
			try
			{
				byte[] content = HttpUtil.downloadBytes(url);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
				FileInfo info = new FileInfo();
				String name = FileUtil.getName(url);
				if (StrUtil.isBlank(name))
				{
					name = String.valueOf(System.currentTimeMillis());
				}
				info.setName(name);
				FileInfo record = bot.uploadFile(inputStream, info);
				idList.add(record.getShortId());
			}
			catch (Exception e)
			{
				log.warn("上传失败：{}", e.getMessage());
			}
		}
		else
		{
			if (files != null && files.length > 0)
			{
				Arrays.stream(files).forEach(f -> {
					FileInfo info = new FileInfo();
					info.setName(f.getOriginalFilename());
					try
					{
						FileInfo record = bot.uploadFile(f.getInputStream(), info);
						idList.add(record.getShortId());
					}
					catch (IOException e)
					{
						log.error(e.getMessage(), e);
						throw new BizException("文件上传失败");
					}
				});
			}
		}
		return idList;
	}

	public InputStream downloadFile(String fileId)
	{
		return bot.downLoadFile(fileId);
	}

	public FileInfo getFileInfo(String id)
	{
		FileInfo record = new FileInfo();
		record.setShortId(id);
		Example<FileInfo> example = Example.of(record);
		return fileInfoRepository.findOne(example).orElse(null);
	}

}
