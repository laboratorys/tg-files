package com.github.lab.files.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Libs
 * @date 2024/9/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileVO
{
	private String id;
	private String name;
	private String url;
	private String status;
}
