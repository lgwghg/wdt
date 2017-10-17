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

/**
 * 游戏服务接口
 *
 * @author zengxn
 * @date 2017-04-16 14:37:15
 */
public interface GameService {
	
	public static String BEANNAME = "gameService";
	/**
	 * 按条件查询游戏
	 * 
	 * @author zengxn
	 */
	List<GameEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增游戏
	 * 
	 * @author zengxn
	 */
	int insert(GameEntity entity);

	/**
	 * 根据ID修改游戏
	 * 
	 * @author zengxn
	 */
	int updateById(GameEntity entity);

	/**
	 * 根据ID获取游戏
	 * 
	 * @author zengxn
	 */
	GameEntity findById(String id);

	/**
	 * 根据ID删除游戏
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 根据ID删除游戏，并且删除游戏属性值关联记录
	 * 
	 * @author zengxn
	 */
	int deleteRelevantById(String updateUserId, String id);

	/**
	 * 根据名称、ID获取游戏
	 * 
	 * @author zengxn
	 */
	GameEntity findByNameId(String name, String id);
}
