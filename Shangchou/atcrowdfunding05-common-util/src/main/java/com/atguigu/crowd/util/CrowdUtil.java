package com.atguigu.crowd.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
	
	/**
	 * 判断当前请求是否为Ajax请求
	 * @param request 请求对象
	 * @return
	 * 		true：当前请求是Ajax请求
	 * 		false：当前请求不是Ajax请求
	 */
	public static boolean judgeRequestType(HttpServletRequest request) {
		// 1.获取请求消息头
		String acceptHeader = request.getHeader("Accept");
		String xRequestHeader = request.getHeader("X-Requested-With");
		// 2.判断
		return (acceptHeader != null && acceptHeader.contains("application/json"))
				
				||
				
				(xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
	}

	/**
	 * 对铭文字符串进行MD5加密
	 * @param source
	 * @return
	 */
	public  static String md5Encode(String source){
		//判断字符串是否有效
		if (source == null || source.length() == 0) {
			throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALID);
		}
		try {
			//获取MessageDigest对象, 传入算法
			String algorithm = "md5";
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			//获取source的字节数组
			byte[] bytes = source.getBytes();
			//执行加密
			byte[] digest = messageDigest.digest(bytes);
			//将加密后的数组, 按照16进制转为字符串输出
			int signum = 1;
			BigInteger bigInteger = new BigInteger(signum, digest);
			int radix = 16;
			String encoded = bigInteger.toString(radix);
			return  encoded;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
