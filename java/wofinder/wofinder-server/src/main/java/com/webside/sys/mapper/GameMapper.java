/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.sys.model.GameEntity;

/**
 * 游戏数据访问接口
 *
 * @author zengxn
 * @date 2017-04-16 14:37:15
 */
@Repository
public interface GameMapper extends BaseMapper<GameEntity, String> {

}
