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
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.up.mapper.UpValueMapper;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpValueEntity;
import com.webside.up.service.UpValueService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 视频作者属性值关联服务实现类
 *
 * @author zengxn
 * @date 2017-04-15 18:28:39
 */
@Service("upValueService")
public class UpValueServiceImpl extends AbstractService<UpValueEntity, String> implements UpValueService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(upValueMapper);
	}

	/**
	 * 视频作者属性值关联 DAO定义
	 */
	@Autowired
	private UpValueMapper upValueMapper;

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
	 * 属性值 Service定义
	 */
	@Autowired
	private ValueService valueService;

	/**
	 * 新增视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(UpValueEntity entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增视频作者属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(UpValueEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频作者属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpValueEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<UpValueEntity> list = super.queryListByPage(parameter);
			for (UpValueEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getValue() != null && StringUtils.isNotBlank(entity.getValue().getId())) {
					entity.setValue(valueService.findById(entity.getValue().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频作者属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者ID、属性值ID、ID获取视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public UpValueEntity findByUpValueId(UpEntity up, ValueEntity value, String id) {
		try {
			UpValueEntity entity = new UpValueEntity(id);
			entity.setUp(up);
			entity.setValue(value);
			List<UpValueEntity> list = upValueMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频作者ID、属性值ID、ID获取视频作者属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者ID删除视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByUpId(String upId) {
		try {
			UpValueEntity entity = new UpValueEntity();
			entity.setUp(new UpEntity(upId));
			return upValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频作者ID删除视频作者属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据属性值id删除视频作者属性值关联
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByValueId(String valueId) {
		try {
			UpValueEntity entity = new UpValueEntity();
			entity.setValue(new ValueEntity(valueId));
			return upValueMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据属性值id删除视频作者属性值关联出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频作者属性值关联的视频作者
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateUpIdBatchById(List<String> ids, String upId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("upId", upId);
			map.put("ids", ids);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return upValueMapper.updateUpIdBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频作者属性值关联的视频作者出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者ID获取视频作者属性值关联列表
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpValueEntity> queryListByUpId(String upId) {
		try {
			UpValueEntity entity = new UpValueEntity();
			entity.setUp(new UpEntity(upId));
			return upValueMapper.queryListByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频作者ID获取视频作者属性值关联列表出错：", e);
			throw new ServiceException(e);
		}
	}
}
