package com.github.lab.files.service.upload;

import com.github.lab.files.model.db.FileInfo;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author Libs
 * @date 2024/9/13
 */
@Service
public class S3FileService implements BaseUploadStrategy
{
	@Override
	public FileInfo uploadFile(InputStream inputStream, FileInfo info)
	{
		return null;
	}
}
