package com.github.lab.files.service;

import cn.hutool.core.util.IdUtil;
import com.github.lab.files.model.db.SiteConfig;
import com.github.lab.files.repository.SiteConfigRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Libs
 * @date 2024/9/13
 */
@Service
public class ConfigService
{
	@Resource
	private SiteConfigRepository configRepository;

	public Map<String, String> getAllConfig()
	{
		List<SiteConfig> configList = configRepository.findAll();
		return configList.isEmpty() ? new HashMap<>() : configList.stream().filter(Objects::nonNull).collect(Collectors.toMap(SiteConfig::getK, SiteConfig::getV, (k1, k2) -> k1));
	}

	public SiteConfig getConfig(String k)
	{
		SiteConfig query = new SiteConfig();
		query.setK(k);
		Example<SiteConfig> example = Example.of(query);
		SiteConfig config = configRepository.findOne(example).orElse(null);
		if (config != null)
		{
			return config;
		}
		return null;
	}

	public String getConfigStr(String k)
	{
		SiteConfig record = this.getConfig(k);
		return record != null ? record.getV() : null;
	}

	public String getConfigStrWithDefault(String k, String defaultValue)
	{
		SiteConfig record = this.getConfig(k);
		return record != null ? record.getV() : defaultValue;
	}

	public void setConfig(String k, String v)
	{
		SiteConfig record = this.getConfig(k);
		if (record == null)
		{
			record = new SiteConfig();
			record.setId(IdUtil.fastSimpleUUID());
		}
		record.setK(k);
		record.setV(v);
		configRepository.save(record);
	}

	public void setConfigMap(Map<String, String> map)
	{
		map.forEach(this::setConfig);
	}

}
