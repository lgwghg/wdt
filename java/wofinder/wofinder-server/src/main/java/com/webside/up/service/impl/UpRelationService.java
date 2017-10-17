/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.up.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.up.service.IUpRelationService;
import com.webside.up.mapper.IUpRelationMapper;
import com.webside.up.model.UpRelation;

/**
 * 人物关系服务实现类
 *
 * @author tianguifang
 * @date 2017-06-06 11:45:26
 */
@Service("upRelationService")
public class UpRelationService extends AbstractService<UpRelation, String> implements IUpRelationService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(upRelationMapper);
	}

	/**
	 * 人物关系 DAO定义
	 */
	@Autowired
	private IUpRelationMapper upRelationMapper;

	@Override
	public List<UpRelation> queryUpRelationListByUpIdAndRelationId(String upId, String relationUpId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("upId", upId);
		paramMap.put("relationUpId", relationUpId);
		return upRelationMapper.queryRelationListByUpId(paramMap);
	}
}
