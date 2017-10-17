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

import com.webside.up.model.UpSecondLevel;

/**
 * 人物二级信息服务接口
 *
 * @author tianguifang
 * @date 2017-06-06 11:44:01
 */
public interface IUpSecondLevelService 
{
	/*
	 * 按条件查询人物二级信息
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UpSecondLevel> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增人物二级信息
	 * @param UpSecondLevel
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UpSecondLevel entity);
	
	/*
	 * 修改人物二级信息
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UpSecondLevel entity);
	
	/*
	 * 根据ID获取人物二级信息
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UpSecondLevel findById(String id);
	
	/*
	 * 根据对象删除人物二级信息
	 * @param UpSecondLevel
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
	
	/**
	 * 根据人物id，查询人物二级信息
	 * @param upId
	 * @return
	 * @author tianguifang
	 */
	List<UpSecondLevel> queryUpSecondLevelListByUpId(String upId);
}
