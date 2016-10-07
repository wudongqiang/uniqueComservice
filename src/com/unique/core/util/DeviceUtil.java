package com.unique.core.util;

public class DeviceUtil {
	public static String getDeviceNameById(int id){
		switch (id) {
		case 1:
			return "WEB";
		case 2:
			return "ANDROID";
		case 3:
			return "IOS";
		default:
			return "UNKNOW";
		}
	}
}
