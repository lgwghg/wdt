/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.photo.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.photo.model.UserPhoto;

/**
 * 用户头像数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-23 10:17:52
 */
@Repository
public interface IUserPhotoMapper extends BaseMapper<UserPhoto, String> 
{
	/**
	 * 获取用户的历史头像三张，主图  上传时间 倒叙排
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	List<UserPhoto> queryUserHistoryPhoto(String userId);
	/**
	 * 更新用户的当前头像未非当前头像
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	int updateCurrentPhotoByUserId(String userId);
	
}
