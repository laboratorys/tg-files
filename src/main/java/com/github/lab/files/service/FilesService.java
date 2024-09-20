package com.github.lab.files.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.github.lab.files.common.BizException;
import com.github.lab.files.common.Constant;
import com.github.lab.files.model.db.FileInfo;
import com.github.lab.files.model.vo.FileInfoVO;
import com.github.lab.files.model.vo.InfoVO;
import com.github.lab.files.model.vo.PageVO;
import com.github.lab.files.model.vo.UploadFileVO;
import com.github.lab.files.repository.FileInfoRepository;
import com.github.lab.files.service.upload.TelegramFileBot;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class FilesService
{
	@Resource
	private TelegramFileBot bot;

	@Resource
	private FileInfoRepository fileInfoRepository;

	@Value("${upload_token:}")
	private String uploadToken;

	@Value("${url:}")
	private String baseUrl;

	@Value("${project.version}")
	private String version;

	public List<FileInfoVO> uploadFile(MultipartFile[] files, String url, String token)
	{
		this.validPublicToken(token);
		List<FileInfoVO> fileList = new ArrayList<>();
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
				fileList.add(new FileInfoVO(record.getShortId(), record.getName(), StrUtil.format("{}/f/{}", baseUrl, record.getShortId())));
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
						fileList.add(new FileInfoVO(record.getShortId(), record.getName(), StrUtil.format("{}/f/{}", baseUrl, record.getShortId())));
					}
					catch (Exception e)
					{
						log.error(e.getMessage(), e);
						throw new BizException("文件上传失败");
					}
				});
			}
		}
		return fileList;
	}

	private void validPublicToken(String token)
	{
		if (StrUtil.isNotBlank(uploadToken) && !uploadToken.equals(token))
		{
			throw new BizException("上传接口暂未公开，请见谅！");
		}
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
		Sort sort = Sort.by(Sort.Direction.ASC, "uploadTime");
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		FileInfo record = new FileInfo();
		record.setToken(token);
		Example<FileInfo> example = Example.of(record);
		Page<FileInfo> page = fileInfoRepository.findAll(example, pageable);
		Page<UploadFileVO> data = page.map(f -> new UploadFileVO(f.getShortId(), f.getName(), StrUtil.format("{}/f/{}", baseUrl, f.getShortId()), "finished"));
		return PageVO.<UploadFileVO> builder().
				pageNo(pageNo).
				pageSize(pageSize).
				totalPages(data.getTotalPages()).
				totalCount(data.getTotalElements()).
				content(data.getContent()).build();
	}

	public InfoVO info()
	{
		InfoVO info = new InfoVO();
		long count = fileInfoRepository.count(Example.of(new FileInfo()));
		info.setTotalCount(count);
		info.setVersion(version);
		info.setMaxSize(Constant.fileSize);
		info.setMaxSizeFmt(Constant.fileSizeFmt);
		return info;
	}

	public PageVO<UploadFileVO> getAdminFileList(Integer pageNo, Integer pageSize)
	{
		Sort sort = Sort.by(Sort.Direction.ASC, "uploadTime");
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		Page<FileInfo> page = fileInfoRepository.findAll(Example.of(new FileInfo()), pageable);
		Page<UploadFileVO> data = page.map(f -> new UploadFileVO(f.getShortId(), f.getName(), StrUtil.format("{}/f/{}", baseUrl, f.getShortId()), "finished"));
		return PageVO.<UploadFileVO> builder().
				pageNo(pageNo).
				pageSize(pageSize).
				totalPages(data.getTotalPages()).
				totalCount(data.getTotalElements()).
				content(data.getContent()).build();
	}
}
