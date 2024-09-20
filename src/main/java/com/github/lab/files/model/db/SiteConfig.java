package com.github.lab.files.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 站点配置
 *
 * @author Libs
 * @date 2024/8/29
 */
@Data
@Entity
@Table(name = "site_config")
public class SiteConfig
{
	@Id
	private String id;
	private String k;
	private String v;
	private String remark;
}
