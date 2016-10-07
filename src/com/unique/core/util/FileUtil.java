package com.unique.core.util;

import java.io.InputStreamReader;
import java.util.Properties;

public class FileUtil {
	public static String readProperties(String path,String key){
		Properties prop=new Properties();         
		try {
			prop.load(new InputStreamReader(FileUtil.class.getClassLoader().getResourceAsStream(path), "UTF-8"));
			return prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
}
