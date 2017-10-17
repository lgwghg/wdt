/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.vo;

import java.io.Serializable;

/**
 * 视频作者站点VO类
 *
 * @author zengxn
 * @date 2017年6月13日 14:40:47
 */
public class VideoUpStationVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoUpStationVo() {
	}

	public VideoUpStationVo(String upAvatar) {
		this.upAvatar = upAvatar;
	}

	/**
	 * 头像
	 */
	private String upAvatar;

	public String getUpAvatar() {
		return upAvatar;
	}

	public void setUpAvatar(String upAvatar) {
		this.upAvatar = upAvatar;
	}
}
