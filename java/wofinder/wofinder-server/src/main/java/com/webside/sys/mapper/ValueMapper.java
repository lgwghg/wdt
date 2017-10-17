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
import com.webside.sys.model.ValueEntity;

/**
 * 属性值数据访问接口
 *
 * @author zengxn
 * @date 2017-04-16 14:38:03
 */
@Repository
public interface ValueMapper extends BaseMapper<ValueEntity, String> {

}
