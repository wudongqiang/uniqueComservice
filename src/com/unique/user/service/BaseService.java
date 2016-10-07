package com.unique.user.service;


public interface BaseService {
	public void regDevice(String deviceToken,String userId);
	
	public void interfaceLog(String module,String method,int souce,String userId,String version, String deviceToken);
}
