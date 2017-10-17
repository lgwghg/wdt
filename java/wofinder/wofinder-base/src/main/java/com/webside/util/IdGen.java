/**
 * Copyright &copy; 2012-2014 All rights reserved.
 */
package com.webside.util;

import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen {

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String genOrderNo() {
		int machineId = 1; // 最大支持到9
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {
			hashCodeV = -hashCodeV;
		}
		// %015d：0表示前面补0，15表示长度为15，d表示参数为正整数
		return machineId + String.format("%011d", hashCodeV);
	}

}
