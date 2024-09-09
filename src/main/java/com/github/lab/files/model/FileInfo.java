package com.github.lab.files.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * @author Libs
 * @date 2024/8/29
 */
@Data
@Entity
@Table(name = "file_info")
public class FileInfo
{
	@Id
	private String id;
	private String shortId;
	private String hash;
	private String name;
	private Long size;
	private String sizeFmt;
	private String tgFileId;
	private String tgFileUniqueId;
	private String mimeType;
	private Date uploadTime;
}
