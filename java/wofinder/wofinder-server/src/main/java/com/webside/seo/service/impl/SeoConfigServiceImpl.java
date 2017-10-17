/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.seo.service.impl;

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
import com.webside.seo.mapper.SeoConfigMapper;
import com.webside.seo.model.SeoConfigEntity;
import com.webside.seo.service.SeoConfigService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * SEO配置服务实现类
 *
 * @author zengxn
 * @date 2017-06-27 12:04:08
 */
@Service("seoConfigService")
public class SeoConfigServiceImpl extends AbstractService<SeoConfigEntity, String> implements SeoConfigService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(seoConfigMapper);
	}

	/**
	 * SEO配置 DAO定义
	 */
	@Autowired
	private SeoConfigMapper seoConfigMapper;

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
	 * 新增SEO配置
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(SeoConfigEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增SEO配置出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改SEO配置
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(SeoConfigEntity entity) {
		try {
			entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改SEO配置出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询SEO配置
	 * 
	 * @author zengxn
	 */
	@Override
	public List<SeoConfigEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<SeoConfigEntity> list = super.queryListByPage(parameter);
			for (SeoConfigEntity entity : list) {
				if (entity.getType() != null && StringUtils.isNotBlank(entity.getType().getValue())) {
					entity.setType(dictService.findByTypeValue(GlobalConstant.SEO_CONFIG_TYPE, String.valueOf(entity.getType().getValue())));
				}
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
			logger.error("按条件查询SEO配置出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 查询全部SEO配置
	 * 
	 * @author zengxn
	 */
	@Override
	public Map<String, Map<String, String>> queryList() {
		try {
			List<SeoConfigEntity> list = seoConfigMapper.queryListByPage(null);
			Map<String, Map<String, String>> dataMap = new HashMap<String, Map<String, String>>();
			Map<String, String> map = null;
			for (SeoConfigEntity seoConfig : list) {
				map = new HashMap<String, String>();
				map.put("keywords", seoConfig.getKeywords());
				map.put("description", seoConfig.getDescription());
				map.put("title", seoConfig.getTitle());
				dataMap.put(seoConfig.getType().getValue(), map);
			}
			return dataMap;
		} catch (Exception e) {
			logger.error("查询全部SEO配置出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据type、ID获取SEO配置
	 * 
	 * @author zengxn
	 */
	@Override
	public SeoConfigEntity findByTypeId(String type, String id) {
		try {
			SeoConfigEntity entity = new SeoConfigEntity(id);
			entity.setType(new DictEntity(type));
			List<SeoConfigEntity> list = seoConfigMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("根据type、ID获取SEO配置出错：", e);
			throw new ServiceException(e);
		}
	}
}
