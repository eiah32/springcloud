package com.eiah.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 检测文件类型工具类 created by eiah on 2017-08-25
 */
public class CheckFileTypeUtil {
	// 缓存文件头信息-文件头信息
	public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();
	static {
		// images
		mFileTypes.put("FFD8FF", "jpg");
		mFileTypes.put("89504E47", "png");
		mFileTypes.put("47494638", "gif");
		mFileTypes.put("424D767E", "bmp");
	}

	/**
	 * 根据文件路径获取文件头信息
	 * 
	 * @param filePath 文件路径
	 * @return 文件头信息 
	 * created by eiah on 2017-08-25
	 */
	public static String getFileType(String filePath) {
		String realImgType = getFileHeader(filePath);
		for (Map.Entry<String, String> entry : mFileTypes.entrySet()) {
			if (realImgType.startsWith(entry.getKey()))
				return entry.getValue();
		}
		return null;
	}

	/**
	 * 根据文件路径获取文件头信息
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 文件头信息 created by eiah on 2017-08-25
	 */
	public static String getFileHeader(String filePath) {
		FileInputStream fis = null;
		String value = null;
		try {
			fis = new FileInputStream(filePath);
			byte[] b = new byte[4];
			/*
			 * int read() 从此输入流中读取一个数据字节。int read(byte[] b) 从此输入流中将最多 b.length
			 * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
			 * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
			 */
			fis.read(b, 0, b.length);
			value = bytesToHexString(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	 * 将要读取文件头信息的文件的byte数组转换成string类型表示
	 * 
	 * @param src
	 *            要读取文件头信息的文件的byte数组
	 * @return 文件头信息 created by eiah on 2017-08-25
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder builder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < src.length; i++) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}
		return builder.toString();
	}

}
