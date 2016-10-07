package com.unique.reg.po;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

public class AppointImages{
	
	///图片服务器
  	private final static String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
	
	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		if(!StringUtil.isEmpty(imageUrl)){
			this.imageUrl = PIC_SERVER+imageUrl;
		}
	}
}
