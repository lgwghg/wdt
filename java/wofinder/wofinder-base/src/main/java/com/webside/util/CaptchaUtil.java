package com.webside.util;

import java.util.Random;

public class CaptchaUtil {
	private static char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	/**
	 * 获得0-9范围的随机数 4位
	 * 
	 * @return String
	 */
	public static String getNumCaptcha() {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
	}

	
	/** 
	 * 获取随机字母数字组合 
	 *  
	 * @param length 
	 *            字符串长度 
	 * @return 
	 */  
	public static String getRandomCharAndNumr(Integer length) {  
	    String str = "";  
	    Random random = new Random();  
	    for (int i = 0; i < length; i++) {  
	        boolean b = random.nextBoolean();  
	        if (b) { // 字符串  
	            // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母  
	            str += (char) (65 + random.nextInt(26));// 取得大写字母  
	        } else { // 数字  
	            str += String.valueOf(random.nextInt(10));  
	        }  
	    }  
	    return str;  
	}
	
}
