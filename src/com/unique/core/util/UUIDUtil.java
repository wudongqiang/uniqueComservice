package com.unique.core.util;

import java.util.UUID;

/**
 * UUID 产生码
 * 
 * @author Sunny
 * @Created Time:Jan 4, 2009 9:30:29 PM
 */

public class UUIDUtil {
	
	public static String randomUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.toUpperCase().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 预约挂号专用唯一码
	 */
	public static String genRegUniqueId() {
		return "GH_"+randomUUID();
	}
	
	/**
	 * 预约体检专用唯一码
	 */
	public static String genTjUniqueId() {
		return "TJ_"+randomUUID();
	}
	
	/**
	 * 定制专用唯一码
	 */
	public static String genDzUniqueId() {
		return "DZ_"+randomUUID();
	}

	/**
	 * 慢病专用唯一码
	 */
	public static String genMbUniqueId() {
		return "MB_"+randomUUID();
	}

	public static void main(String[] agr) {
		System.out.println(genRegUniqueId());
		System.out.println(genTjUniqueId());
		System.out.println(genDzUniqueId());
		System.out.println(genRegUniqueId().length());
	}
}
