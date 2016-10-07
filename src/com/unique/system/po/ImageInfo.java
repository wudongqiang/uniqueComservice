/**
 * 2015年9月16日
 */
package com.unique.system.po;

import com.unique.core.util.FileUtil;
import com.unique.core.util.StringUtil;

/**
 * <br/>
 * 创建人 wdq<br/>
 * 创建时间 2015年9月16日 下午4:09:52<br/>
 * @author Administrator
 * @date 2015年9月16日
 * @description 
 */
public class ImageInfo {

	//图片服务器
  	private static final String PIC_SERVER = FileUtil.readProperties("comservice.properties", "picServerOut");
  	
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
