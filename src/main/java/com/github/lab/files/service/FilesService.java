package com.github.lab.files.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.github.lab.files.common.BizException;
import com.github.lab.files.config.MyFileBot;
import com.github.lab.files.model.db.FileInfo;
import com.github.lab.files.model.vo.PageVO;
import com.github.lab.files.model.vo.UploadFileVO;
import com.github.lab.files.repository.FileInfoRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public List<String> uploadFile(MultipartFile[] files, String url, String token)
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
				info.setToken(token);
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
					info.setToken(token);
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

	public void deleteFile(String shortId, String token)
	{
		if (StrUtil.hasBlank(shortId, token))
		{
			throw new BizException("删除失败，请求无效");
		}
		FileInfo record = new FileInfo();
		record.setShortId(shortId);
		record.setToken(token);
		Example<FileInfo> example = Example.of(record);
		FileInfo f = fileInfoRepository.findOne(example).orElse(null);
		if (f != null)
		{
			fileInfoRepository.deleteById(f.getId());
		}

	}

	public PageVO<UploadFileVO> fileList(String token, Integer pageNo, Integer pageSize)
	{
		if (StrUtil.isBlank(token))
		{
			return PageVO.<UploadFileVO> builder().
					pageNo(pageNo).
					pageSize(pageSize).
					totalPages(0).
					totalCount(0L).
					content(new ArrayList<>()).build();
		}
		Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo - 1);
		FileInfo record = new FileInfo();
		record.setToken(token);
		Example<FileInfo> example = Example.of(record);
		Page<FileInfo> page = fileInfoRepository.findAll(example, pageable);
		Page<UploadFileVO> data = page.map(f -> new UploadFileVO(f.getShortId(), f.getName(), null, "finished"));
		return PageVO.<UploadFileVO> builder().
				pageNo(pageNo).
				pageSize(pageSize).
				totalPages(data.getTotalPages()).
				totalCount(data.getTotalElements()).
				content(data.getContent()).build();
	}
}
