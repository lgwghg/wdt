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

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.model.UserIncrementEntity;

/**
 * 用户增量数据访问接口
 *
 * @author zengxn
 * @date 2017-05-05 11:09:24
 */
@Repository
public interface UserIncrementMapper extends BaseMapper<UserIncrementEntity, String> {
	/**
	 * 根据entity获取用户增量
	 * 
	 * @param entity
	 * @author zengxn
	 */
	public List<UserIncrementEntity> findByEntity(UserIncrementEntity entity);
}
