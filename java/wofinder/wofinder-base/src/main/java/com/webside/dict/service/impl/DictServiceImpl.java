/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.dict.mapper.DictMapper;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.redis.RedisManager;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 字典服务实现类
 *
 * @author zengxn
 * @date 2017-05-16 15:05:05
 */
@Service("dictService")
public class DictServiceImpl extends AbstractService<DictEntity, String> implements DictService {

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(dictMapper);
	}

	/**
	 * 字典 DAO定义
	 */
	@Autowired
	private DictMapper dictMapper;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * redis 定义
	 */
	@Autowired
	private RedisManager redisManager;

	/**
	 * 新增字典
	 * 
	 * @param DictEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int insert(DictEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			int result = super.insert(entity);
			if(result > 0) {
				// 删除缓存
				String key = CacheConstant.CACHE_CODE_LIST;
				redisManager.delByKey(key);
			}
			return result;
		} catch (Exception e) {
			logger.error("新增字典出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改字典
	 * 
	 * @param DictEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int updateById(DictEntity entity) {
		try {
			entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			int result = super.updateById(entity);
			if(result > 0) {
				// 删除缓存
				String key = CacheConstant.CACHE_CODE_LIST;
				redisManager.delByKey(key);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改字典出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询字典
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public List<DictEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<DictEntity> list = super.queryListByPage(parameter);
			for (DictEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询字典出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据字典分类查询字典
	 * 
	 * @param type
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public List<DictEntity> queryListByType(String type) {
		try {
			List<DictEntity> listDict = queryList();
			List<DictEntity> list = new ArrayList<DictEntity>();
			if (listDict != null && listDict.size() > 0) {
				for (DictEntity dict : listDict) {
					if (type.equals(dict.getType())) {
						list.add(dict);
					}
				}
			}
			return list;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据type 和 value 查询字典对象
	 * 
	 * @param type
	 * @param value
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public DictEntity findByTypeValue(String type, String value) {
		try {
			DictEntity dictEntity = null;
			// 从缓存中查询所有字典
			List<DictEntity> list = queryList();
			if (list != null && list.size() > 0) {
				for (DictEntity dict : list) {
					if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
						dictEntity = dict;
						break;
					}
				}
			}
			if (dictEntity == null) {
				dictEntity = new DictEntity();
			}
			return dictEntity;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public DictEntity findByTypeValueNoRedis(String type, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("value", value);
		map.put("statusValue", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
		List<DictEntity> list = dictMapper.queryListByPage(map);
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询所有字典
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	@SuppressWarnings("unchecked")
	private List<DictEntity> queryList() {
		try {
			List<DictEntity> list = null;
			String key = CacheConstant.CACHE_CODE_LIST;
			// 查询缓存是否存在，若不存在查询数据库
			if (redisManager.exists(key)) {
				list = redisManager.get(key, List.class);
			}
			if (list == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("statusValue", GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);
				list = dictMapper.queryList(map);
				if (list != null && list.size() > 0) {
					// 添加缓存
					redisManager.set(key, list);
				}
			}
			return list;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
