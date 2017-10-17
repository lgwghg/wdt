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
import com.webside.sys.mapper.AttributeValueMapper;
import com.webside.sys.model.AttributeEntity;
import com.webside.sys.model.AttributeValueEntity;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.AttributeValueService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 属性值关联服务实现类
 *
 * @author zengxn
 * @date 2017-04-16 14:36:57
 */
@Service("attributeValueService")
public class AttributeValueServiceImpl extends AbstractService<AttributeValueEntity, String> implements AttributeValueService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(attributeValueMapper);
	}

	/**
	 * 属性值关联 DAO定义
	 */
	@Autowired
	private AttributeValueMapper attributeValueMapper;

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
	 * 新增属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(AttributeValueEntity entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(AttributeValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public List<AttributeValueEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<AttributeValueEntity> list = super.queryListByPage(parameter);
			for (AttributeValueEntity entity : list) {
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
			logger.error("按条件查询属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性ID删除属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByAttributeId(String attributeId) {
		try {
			AttributeValueEntity entity = new AttributeValueEntity();
			entity.setAttribute(new AttributeEntity(attributeId));
			return attributeValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据属性ID删除属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性值ID删除属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByValueId(String valueId) {
		try {
			AttributeValueEntity entity = new AttributeValueEntity();
			entity.setValue(new ValueEntity(valueId));
			return attributeValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据属性值ID删除属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性id、属性值id、id获取属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public AttributeValueEntity findByAttributeValueId(AttributeEntity attribute, ValueEntity value, String id) {
		try {
			AttributeValueEntity entity = new AttributeValueEntity(id);
			entity.setAttribute(attribute);
			entity.setValue(value);
			List<AttributeValueEntity> list = attributeValueMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("根据attributeId、valueId、id获取属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}
}
