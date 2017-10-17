/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.service;

import java.util.List;
import java.util.Map;

import com.webside.sys.model.GameEntity;
import com.webside.sys.model.GameValueEntity;
import com.webside.sys.model.ValueEntity;

/**
 * 游戏属性值关联服务接口
 *
 * @author zengxn
 * @date 2017-04-16 14:37:36
 */
public interface GameValueService {
	/**
	 * 按条件查询游戏属性值关联
	 * 
	 * @author zengxn
	 */
	List<GameValueEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增游戏属性值关联
	 * 
	 * @author zengxn
	 */
	int insert(GameValueEntity entity);

	/**
	 * 根据ID修改游戏属性值关联
	 * 
	 * @author zengxn
	 */
	int updateById(GameValueEntity entity);

	/**
	 * 根据ID获取游戏属性值关联
	 * 
	 * @author zengxn
	 */
	GameValueEntity findById(String id);

	/**
	 * 根据ID删除游戏属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据游戏id删除属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByGameId(String gameId);

	/**
	 * 根据属性值id删除游戏属性值关联
	 * 
	 * @author zengxn
	 */
	int deleteByValueId(String valueId);

	/**
	 * 根据游戏ID、属性值ID、ID获取游戏属性值关联
	 * 
	 * @author zengxn
	 */
	GameValueEntity findByGameValueId(GameEntity game, ValueEntity value, String id);
}
