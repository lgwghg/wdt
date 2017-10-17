/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.up.mapper.UpNameMapper;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpNameEntity;
import com.webside.up.service.UpNameService;
import com.webside.up.service.UpService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 视频作者名称服务实现类
 *
 * @author zengxn
 * @date 2017-04-15 18:29:36
 */
@Service("upNameService")
public class UpNameServiceImpl extends AbstractService<UpNameEntity, String> implements UpNameService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(upNameMapper);
	}

	/**
	 * 视频作者名称 DAO定义
	 */
	@Autowired
	private UpNameMapper upNameMapper;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 视频作者 Service定义
	 */
	@Autowired
	private UpService upService;

	/**
	 * 新增视频作者名称
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(UpNameEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增视频作者名称出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频作者名称
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(UpNameEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}

			if (GlobalConstant.UP_NAME_TYPE_1.equals(entity.getType().getValue())) {
				// 修改视频作者名称
				String updateUserId = entity.getUpdateUser() == null || StringUtils.isBlank(entity.getUpdateUser().getId()) ? null : entity.getUpdateUser().getId();
				upService.updateNameById(entity.getUp().getId(), entity.getName(), updateUserId, entity.getUpdateTime());
			}

			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频作者名称出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频作者名称
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpNameEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<UpNameEntity> list = super.queryListByPage(parameter);
			for (UpNameEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getType() != null && StringUtils.isNotBlank(entity.getType().getValue())) {
					entity.setType(dictService.findByTypeValue(GlobalConstant.UP_NAME_TYPE, String.valueOf(entity.getType().getValue())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频作者名称出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据名称、类型、ID获取视频作者名称
	 * 
	 * @author zengxn
	 */
	@Override
	public UpNameEntity findByNameTypeId(String name, DictEntity type, String id) {
		try {
			UpNameEntity entity = new UpNameEntity(id);
			entity.setType(type);
			entity.setName(name);
			List<UpNameEntity> list = upNameMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据名称、类型、ID获取视频作者名称出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者ID删除视频作者名称
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByUpId(String upId) {
		try {
			UpNameEntity entity = new UpNameEntity();
			entity.setUp(new UpEntity(upId));
			return upNameMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频作者ID删除视频作者名称出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者ID、类型修改视频作者名称
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateNameByUpIdType(String name, String upId, String typeValue, String updateUserId, String updateTime) {
		try {
			UpNameEntity entity = new UpNameEntity();
			entity.setName(name);
			entity.setUp(new UpEntity(upId));
			entity.setType(new DictEntity(typeValue));
			if (StringUtils.isBlank(updateTime)) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			} else {
				entity.setUpdateTime(updateTime);
			}
			return upNameMapper.updateByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频作者ID、类型修改视频作者名称出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频作者名称的视频作者并且类别为次名
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateUpIdBatchById(List<String> ids, String upId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("upId", upId);
			map.put("ids", ids);
			map.put("typeValue", GlobalConstant.UP_NAME_TYPE_2);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return upNameMapper.updateUpIdBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频作者名称的视频作者并且类别为次名出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者id获取视频作者名称列表
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpNameEntity> queryListByUpId(String upId) {
		try {
			UpNameEntity upNameEntity = new UpNameEntity();
			upNameEntity.setUp(new UpEntity(upId));
			return upNameMapper.queryListByEntity(upNameEntity);
		} catch (Exception e) {
			logger.error("根据视频作者id获取视频作者名称列表出错：", e);
			throw new ServiceException(e);
		}
	}
}
