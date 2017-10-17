/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserPhoto;

/**
 * 用户数据访问接口
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity, String> {

	/**
	 * 根据entity查询用户
	 * 
	 * @param entity
	 * @return
	 */
	public List<UserEntity> findByEntity(UserEntity entity);

	/**
	 * 取消第三方绑定
	 * 
	 * @param userId
	 * @param thirdType
	 * @return
	 */
	public int updateCancelBind(Map<String, Object> param);
	/**
	 * 保存用户头像
	 * @param photo
	 * @return
	 */
	public int insertUserPhoto(UserPhoto photo);
	/**
	 * 根据用户id获取用户信息和用户增量信息
	 * @param entity
	 * @return
	 */
	public UserEntity findUserAndIncrementById(String userId);

}
