package com.unique.core.util;

import java.util.Random;

/**
 * 随机码工具类
 * @author Angel
 *
 */
public class RandomNum {
	/**
	 * 获取N位随机码
	 * @param count
	 * @return
	 */
	public static String getRandomNum(int count){
		StringBuffer result = new StringBuffer();
		Random r = new Random();
		for(int i=0;i<count;i++){
			result.append(r.nextInt(10));
		}
		return result.toString();
	}
}
