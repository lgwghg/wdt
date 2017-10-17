/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.dict.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.dict.model.DictEntity;

/**
 * 字典数据访问接口
 *
 * @author zengxn
 * @date 2017-05-16 15:05:05
 */
@Repository
public interface DictMapper extends BaseMapper<DictEntity, String> {

	/**
	 * 根据条件查询所有字典
	 * 
	 * @param map
	 * @author zengxn
	 */
	public List<DictEntity> queryList(Map<String, Object> map);
}
