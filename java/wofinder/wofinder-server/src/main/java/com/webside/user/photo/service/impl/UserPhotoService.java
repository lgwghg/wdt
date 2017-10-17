/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.photo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.user.photo.mapper.IUserPhotoMapper;
import com.webside.user.photo.model.UserPhoto;
import com.webside.user.photo.service.IUserPhotoService;

/**
 * 用户头像服务实现类
 *
 * @author tianguifang
 * @date 2017-06-23 10:17:52
 */
@Service("userPhotoService")
public class UserPhotoService extends AbstractService<UserPhoto, String> implements IUserPhotoService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userPhotoMapper);
	}

	/**
	 * 用户头像 DAO定义
	 */
	@Autowired
	private IUserPhotoMapper userPhotoMapper;
	/**
	 * 获取用户的历史头像三张，主图  上传时间 倒叙排
	 * @param userId
	 * @return
	 * @author tianguifang
	 */
	@Override
	public List<UserPhoto> queryUserHistoryPhoto(String userId) {
		return userPhotoMapper.queryUserHistoryPhoto(userId);
	}
	/**
	 * 编辑用户当前头像
	 * @author tianguifang
	 */
	@Override
	public int updateCurrentPhoto(UserPhoto photo) {
		int cancelResult = userPhotoMapper.updateCurrentPhotoByUserId(photo.getUserId());
		if (cancelResult > 0) {
			photo.setIsCurrent(1);
			return userPhotoMapper.updateById(photo);
		}
		return 0;
	}
}
