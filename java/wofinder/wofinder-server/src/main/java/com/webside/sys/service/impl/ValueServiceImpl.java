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
import com.webside.sys.mapper.ValueMapper;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.AttributeValueService;
import com.webside.sys.service.GameValueService;
import com.webside.sys.service.ValueService;
import com.webside.task.service.TaskValueService;
import com.webside.up.service.UpValueService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.service.VideoAlbumValueService;
import com.webside.video.service.VideoValueService;

/**
 * 属性值服务实现类
 *
 * @author zengxn
 * @date 2017-04-16 14:38:03
 */
@Service("valueService")
public class ValueServiceImpl extends AbstractService<ValueEntity, String> implements ValueService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(valueMapper);
	}

	/**
	 * 属性值 DAO定义
	 */
	@Autowired
	private ValueMapper valueMapper;

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
	 * 游戏属性值关联 Service定义
	 */
	@Autowired
	private GameValueService gameValueService;

	/**
	 * 视频作者属性值关联 Service定义
	 */
	@Autowired
	private UpValueService upValueService;

	/**
	 * 视频属性值关联 Service定义
	 */
	@Autowired
	private VideoValueService videoValueService;

	/**
	 * 视频专辑属性值关联 Service定义
	 */
	@Autowired
	private VideoAlbumValueService videoAlbumValueService;
	
	/**
	 * 事件属性值关联 Service定义
	 */
	@Autowired
	private TaskValueService taskValueService;

	/**
	 * 新增属性值
	 * 
	 * @param ValueEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int insert(ValueEntity entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增属性值出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改属性值
	 * 
	 * @param ValueEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int updateById(ValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改属性值出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询属性值
	 * 
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public List<ValueEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<ValueEntity> list = super.queryListByPage(parameter);
			for (ValueEntity entity : list) {
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
			logger.error("按条件查询属性值出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除属性值，并且删除属性值关联记录
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteRelevantById(String id) {
		try {
			// 删除属性值关联记录
			attributeValueService.deleteByValueId(id);
			// 删除游戏关联值记录
			gameValueService.deleteByValueId(id);
			// 删除视频作者属性值关联记录
			upValueService.deleteByValueId(id);
			// 删除视频属性值关联记录
			videoValueService.deleteByValueId(id);
			// 删除视频专辑属性值关联记录
			videoAlbumValueService.deleteByValueId(id);
			//删除事件属性值关联记录
			taskValueService.deleteByValueId(id);
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据ID删除属性值，并且删除属性值关联记录出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据名称、ID获取属性值
	 * 
	 * @author zengxn
	 */
	@Override
	public ValueEntity findByNameId(String name, String id) {
		try {
			ValueEntity entity = new ValueEntity(id);
			entity.setName(name);
			List<ValueEntity> list = valueMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据名称、ID获取属性值出错：", e);
			throw new ServiceException(e);
		}
	}
}
