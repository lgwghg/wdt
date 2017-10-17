/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.vo;

import java.io.Serializable;

/**
 * 属性值VO类
 *
 * @author zengxn
 * @date 2017年6月13日 14:40:47
 */
public class ValueVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public ValueVo() {
	}

	public ValueVo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
