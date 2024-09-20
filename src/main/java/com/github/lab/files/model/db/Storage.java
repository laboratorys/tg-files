package com.github.lab.files.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 上传来源
 *
 * @author Libs
 * @date 2024/8/29
 */
@Data
@Entity
@Table(name = "storage")
public class Storage
{
	@Id
	private String id;
	private String code;
	private String configUrl;
}
