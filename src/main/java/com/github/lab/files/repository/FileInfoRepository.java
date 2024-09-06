package com.github.lab.files.repository;

import com.github.lab.files.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository
		extends JpaRepository<FileInfo, String>
{

}
