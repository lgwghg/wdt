/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.sys.model.AttributeValueEntity;

/**
 * 属性值关联数据访问接口
 *
 * @author zengxn
 * @date 2017-04-16 14:36:57
 */
@Repository
public interface AttributeValueMapper extends BaseMapper<AttributeValueEntity, String> {

}
