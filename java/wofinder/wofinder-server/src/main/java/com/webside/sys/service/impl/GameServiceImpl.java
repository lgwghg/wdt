/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.sys.mapper.GameMapper;
import com.webside.sys.model.GameEntity;
import com.webside.sys.service.GameService;
import com.webside.sys.service.GameValueService;
import com.webside.up.service.UpService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.service.VideoService;

/**
 * 游戏服务实现类
 *
 * @author zengxn
 * @date 2017-04-16 14:37:15
 */
@Service("gameService")
public class GameServiceImpl extends AbstractService<GameEntity, String> implements GameService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(gameMapper);
	}

	/**
	 * 游戏 DAO定义
	 */
	@Autowired
	private GameMapper gameMapper;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 游戏属性值关联 Service定义
	 */
	@Autowired
	private GameValueService gameValueService;

	/**
	 * 视频作者 Service定义
	 */
	@Autowired
	private UpService upService;

	/**
	 * 视频 Service定义
	 */
	@Autowired
	private VideoService videoService;

	/**
	 * 新增游戏
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(GameEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增游戏出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改游戏
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(GameEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改游戏出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询游戏
	 * 
	 * @author zengxn
	 */
	@Override
	public List<GameEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<GameEntity> list = super.queryListByPage(parameter);
			for (GameEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询游戏出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除游戏，并且删除游戏属性值关联记录
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteRelevantById(String updateUserId, String id) {
		try {
			String updateTime = StringUtils.toString(System.currentTimeMillis());
			// 重置符合要求的视频作者游戏字段
			upService.resetGameIdByGameId(id, updateUserId, updateTime);
			// 重置符合要求的视频游戏字段
			videoService.resetGameIdByGameId(id, updateUserId, updateTime);
			// 删除游戏属性值关联记录
			gameValueService.deleteByGameId(id);
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据ID删除游戏，并且删除游戏属性值关联记录出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据名称、ID获取游戏
	 * 
	 * @author zengxn
	 */
	@Override
	public GameEntity findByNameId(String name, String id) {
		try {
			GameEntity entity = new GameEntity(id);
			entity.setName(name);
			List<GameEntity> list = gameMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("根据名称、ID获取游戏出错：", e);
			throw new ServiceException(e);
		}
	}

}
