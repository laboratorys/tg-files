package com.github.lab.files.common;

import cn.hutool.core.io.unit.DataSize;

/**
 * @author Libs
 * @date 2024/9/12
 */
public class Constant
{
	public static final String fileSizeFmt = "10 MB";
	public static final Long fileSize = DataSize.parse(Constant.fileSizeFmt).toBytes();
}
