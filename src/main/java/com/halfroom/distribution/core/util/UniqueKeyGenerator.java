package com.halfroom.distribution.core.util;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniqueKeyGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(UniqueKeyGenerator.class);

	/**
	 * 生成用户的tocken，统一调用工具类，方便后续修改生成策略
	 * 
	 * @return
	 */
	public static String generateToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String generateSalt() {
		Random RANDOM = new SecureRandom();
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		try {
			return new String(Base64.encodeBase64(salt), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("generateSalt error");
		}
		return "";
	}
	
	/**
	 * 生成返回给界面的唯一ID，防止数据库的自增ID被获取
	 * 
	 * @return
	 */
	public static Long generateViewId() {
		Long timestamp = System.currentTimeMillis();
		// 生成三位随机数
		StringBuilder result = new StringBuilder();
		Random _random = new Random();
		for (int i = 0; i <3; i++) {
			result.append(_random.nextInt(10));
		}
		return Long.valueOf(String.valueOf(timestamp) + result.toString());
	}

	/**
	 * random生成任意位数随机数
	 * 
	 * @Title: random
	 * @param @param n
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String random(int n) {
		String random = "";
		Random _random = new Random();
		for (int i = 0; i < n; i++) {
			random += _random.nextInt(10);
		}
		return random;
	}

	/**
	 * 生成[min,max]区间内的随机数
	 * 
	 * @param n
	 * @param start
	 * @param end
	 * @return
	 */
	public static String random(int n, int min, int max) {
		String random = "";
		Random _random = new Random();
		for (int i = 0; i < n; i++) {

			random += (_random.nextInt(max) % (max - min + 1) + min);
		}
		return random;
	}
}
