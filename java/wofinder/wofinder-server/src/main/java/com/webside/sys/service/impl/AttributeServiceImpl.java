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
import com.webside.sys.mapper.AttributeMapper;
import com.webside.sys.model.AttributeEntity;
import com.webside.sys.service.AttributeService;
import com.webside.sys.service.AttributeValueService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 属性服务实现类
 *
 * @author zengxn
 * @date 2017-04-16 14:36:14
 */
@Service("attributeService")
public class AttributeServiceImpl extends AbstractService<AttributeEntity, String> implements AttributeService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(attributeMapper);
	}

	/**
	 * 属性 DAO定义
	 */
	@Autowired
	private AttributeMapper attributeMapper;

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
	 * 属性值关联管理 Service定义
	 */
	@Autowired
	private AttributeValueService attributeValueService;

	/**
	 * 新增属性
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(AttributeEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增属性出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改属性
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(AttributeEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改属性出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询属性
	 * 
	 * @author zengxn
	 */
	@Override
	public List<AttributeEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<AttributeEntity> list = super.queryListByPage(parameter);
			for (AttributeEntity entity : list) {
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
			logger.error("按条件查询属性出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除属性，并且删除属性值关联记录
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteRelevantById(String id) {
		try {
			// 删除属性值关联记录
			attributeValueService.deleteByAttributeId(id);
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据ID删除属性以及属性值关联记录出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据name与id获取属性
	 * 
	 * @author zengxn
	 */
	@Override
	public AttributeEntity findByNameId(String name, String id) {
		try {
			AttributeEntity entity = new AttributeEntity(id);
			entity.setName(name);
			List<AttributeEntity> list = attributeMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("根据name与id获取属性出错：", e);
			throw new ServiceException(e);
		}
	}
}
