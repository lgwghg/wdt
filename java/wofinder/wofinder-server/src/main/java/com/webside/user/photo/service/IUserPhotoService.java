/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.photo.service;

import java.util.List;
import java.util.Map;

import com.webside.user.photo.model.UserPhoto;

/**
 * 用户头像服务接口
 *
 * @author tianguifang
 * @date 2017-06-23 10:17:52
 */
public interface IUserPhotoService 
{
	/*
	 * 按条件查询用户头像
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UserPhoto> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增用户头像
	 * @param UserPhoto
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UserPhoto entity);
	
	/*
	 * 修改用户头像
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UserPhoto entity);
	
	/*
	 * 根据ID获取用户头像
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UserPhoto findById(String id);
	
	/*
	 * 根据对象删除用户头像
	 * @param UserPhoto
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 获取用户的历史头像三张，主图  上传时间 倒叙排
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	public List<UserPhoto> queryUserHistoryPhoto(String userId);
	
	/**
	 * 编辑用户的当前头像
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	int updateCurrentPhoto(UserPhoto photo);
}
