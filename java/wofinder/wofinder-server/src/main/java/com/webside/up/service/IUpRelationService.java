/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.up.service;

import java.util.List;
import java.util.Map;

import com.webside.up.model.UpRelation;

/**
 * 人物关系服务接口
 *
 * @author tianguifang
 * @date 2017-06-06 11:45:26
 */
public interface IUpRelationService 
{
	/*
	 * 按条件查询人物关系
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UpRelation> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增人物关系
	 * @param UpRelation
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UpRelation entity);
	
	/*
	 * 修改人物关系
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UpRelation entity);
	
	/*
	 * 根据ID获取人物关系
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UpRelation findById(String id);
	
	/*
	 * 根据对象删除人物关系
	 * @param UpRelation
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	/**
	 * 根据人物id查询人物关系
	 * @param upId
	 * @param relationUpId
	 * @return
	 * @author tianguifang
	 */
	public List<UpRelation> queryUpRelationListByUpIdAndRelationId(String upId, String relationUpId);

}
