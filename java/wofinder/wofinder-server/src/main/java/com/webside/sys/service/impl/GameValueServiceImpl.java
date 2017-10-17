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
import com.webside.sys.mapper.GameValueMapper;
import com.webside.sys.model.GameEntity;
import com.webside.sys.model.GameValueEntity;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.GameValueService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 游戏属性值关联服务实现类
 *
 * @author zengxn
 * @date 2017-04-16 14:37:36
 */
@Service("gameValueService")
public class GameValueServiceImpl extends AbstractService<GameValueEntity, String> implements GameValueService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(gameValueMapper);
	}

	/**
	 * 游戏属性值关联 DAO定义
	 */
	@Autowired
	private GameValueMapper gameValueMapper;

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
	 * 新增游戏属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(GameValueEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增游戏属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改游戏属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(GameValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改游戏属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询游戏属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public List<GameValueEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<GameValueEntity> list = super.queryListByPage(parameter);
			for (GameValueEntity entity : list) {
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
			logger.error("按条件查询游戏属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除游戏属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteById(String id) {
		try {
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据ID删除游戏属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据游戏id删除属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByGameId(String gameId) {
		try {
			GameValueEntity entity = new GameValueEntity();
			entity.setGame(new GameEntity(gameId));
			return gameValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据游戏id删除属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性值id删除游戏属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByValueId(String valueId) {
		try {
			GameValueEntity entity = new GameValueEntity();
			entity.setValue(new ValueEntity(valueId));
			return gameValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据属性值id删除游戏属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据游戏ID、属性值ID、ID获取游戏属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public GameValueEntity findByGameValueId(GameEntity game, ValueEntity value, String id) {
		try {
			GameValueEntity entity = new GameValueEntity(id);
			entity.setGame(game);
			entity.setValue(value);
			List<GameValueEntity> list = gameValueMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("根据游戏ID、属性值ID、ID获取游戏属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

}
