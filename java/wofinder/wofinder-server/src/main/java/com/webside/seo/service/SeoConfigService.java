/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.seo.service;

import java.util.List;
import java.util.Map;

import com.webside.seo.model.SeoConfigEntity;

/**
 * SEO配置服务接口
 *
 * @author zengxn
 * @date 2017-06-27 12:04:08
 */
public interface SeoConfigService {
	/**
	 * 按条件查询SEO配置
	 * 
	 * @author zengxn
	 */
	List<SeoConfigEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增SEO配置
	 * 
	 * @author zengxn
	 */
	int insert(final SeoConfigEntity entity);

	/**
	 * 根据ID更新修改SEO配置
	 * 
	 * @author zengxn
	 */
	int updateById(final SeoConfigEntity entity);

	/**
	 * 根据ID获取SEO配置
	 * 
	 * @author zengxn
	 */
	SeoConfigEntity findById(String id);

	/**
	 * 根据ID删除SEO配置
	 * 
	 * @author zengxn
	 */
	int deleteById(String id);

	/**
	 * 查询全部SEO配置
	 * 
	 * @author zengxn
	 */
	Map<String, Map<String, String>> queryList();

	/**
	 * 根据type、ID获取SEO配置
	 * 
	 * @author zengxn
	 */
	SeoConfigEntity findByTypeId(String type, String id);
}
