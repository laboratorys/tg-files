package com.github.lab.files.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Libs
 * @date 2024/9/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoVO
{
	private String id;
	private String name;
	private String url;
}
