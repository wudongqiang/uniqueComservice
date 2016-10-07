package com.unique.core.util;

public class StringUtil {
	public static boolean isEmpty(String txt){
		if(txt==null || txt.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean equals(String source,String desc){
		if(source==null || desc==null){
			return false;
		}
		if(source.equals(desc)){
			return true;
		}else{
			return false;
		}
	}
}
