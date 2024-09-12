package com.github.lab.files.service.upload;

import com.github.lab.files.model.db.FileInfo;

import java.io.InputStream;

public interface BaseUploadService
{
	FileInfo uploadFile(InputStream inputStream, FileInfo info);
}
