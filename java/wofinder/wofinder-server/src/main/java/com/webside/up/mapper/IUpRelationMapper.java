/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.up.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.up.model.UpRelation;

/**
 * 人物关系数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-06 11:45:26
 */
@Repository
public interface IUpRelationMapper extends BaseMapper<UpRelation, String> 
{

	List<UpRelation> queryRelationListByUpId(Map<String, Object> paramMap);
	
}
