/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.seo.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.seo.model.SeoConfigEntity;

/**
 * SEO配置数据访问接口
 *
 * @author zengxn
 * @date 2017-06-27 12:04:08
 */
@Repository
public interface SeoConfigMapper extends BaseMapper<SeoConfigEntity, String> 
{
	
}
