package com.github.lab.files.common;

import cn.hutool.crypto.SecureUtil;

import java.util.UUID;

/**
 * @author Libs
 * @date 2024/9/5
 */
public class ShortIdUtil
{
	/**
	 * 短ID生成方法
	 */
	public static String[] shortId(String id)
	{
		String key = UUID.randomUUID().toString();
		//混淆key,加上当前时间,并且取一个随机字符串
		key = System.currentTimeMillis() + key;
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z"
		};

		// 对传入网址进行 MD5 加密

		String hex = SecureUtil.md5(key + id);

		String[] resUrl = new String[4];

		for (int i = 0; i < 4; i++)
		{

			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算

			String sTempSubString = hex.substring(i * 8, i * 8 + 8);

			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
			// long ，则会越界

			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);

			StringBuilder outChars = new StringBuilder();

			for (int j = 0; j < 6; j++)
			{

				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引

				long index = 0x0000003D & lHexLong;

				// 把取得的字符相加

				outChars.append(chars[(int) index]);

				// 每次循环按位右移 5 位

				lHexLong = lHexLong >> 5;

			}

			// 把字符串存入对应索引的输出数组

			resUrl[i] = outChars.toString();

		}

		return resUrl;
	}

	/**
	 * 获取我想要的字符串,将生成的两个相加,得到我想要的12位字符
	 */
	public static String getShortId(String id)
	{
		String[] aResult = shortId(id);

		return aResult[0] + aResult[1];
	}

}
