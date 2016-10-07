package com.unique.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	/** 身份证正则验证 */
	public static final String ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
	/** 手机号 正则验证 */
	public static final String MOBILE = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$";
	/** 标题30个字符以内 */
	public static final String TITLE = "^[\\S]{0,30}$";
	/** 内容300个字符以内 */
	public static final String CONTENT = "^[\\S]{0,300}$";
	/** 登录账户 手机号码 or 身份证 正则验证 */
	public static final String MOBILE_AND_ID_CARED = "^((((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})))$";

	/**
	 * 删除Html标签
	 * 
	 * @param content 含有html标签的字符串
	 * @return
	 */
	public static String htmlRemoveTag(String content) {
		if (content == null)
			return null;
		String htmlStr = content; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;
		//过滤html转义符 如 &nbsp;
		Pattern p_html_s;
	    Matcher m_html_s;
		try {
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			
		    p_html_s = Pattern.compile("&[a-zA-B]+;", Pattern.CASE_INSENSITIVE);
            m_html_s = p_html_s.matcher(htmlStr);
            htmlStr = m_html_s.replaceAll(""); //过滤html转义符 如 &nbsp;
			
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}

	public static void main(String[] args) {
		long s = System.currentTimeMillis();
		boolean isReg = Pattern.matches(MOBILE_AND_ID_CARED, "13455554861");
		System.out.println(isReg);
		System.out.println(System.currentTimeMillis() - s);
	}
}
