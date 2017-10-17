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
import com.webside.up.model.UpPhoto;

/**
 * 人物图册服务接口
 *
 * @author tianguifang
 * @date 2017-06-06 11:42:41
 */
public interface IUpPhotoService 
{
	/*
	 * 按条件查询人物图册
	 * @throws Exception
	 * @author tianguifang
	 */
	public List<UpPhoto> queryListByPage(Map<String, Object> parameter);
	
	/*
	 * 新增人物图册
	 * @param UpPhoto
	 * @throws Exception
	 * @author tianguifang
	 */
	public int insert(final UpPhoto entity);
	
	/*
	 * 修改人物图册
	 * @param entity
	 * @throws Exception
	 * @author tianguifang
	 */
	public int updateById(final UpPhoto entity);
	
	/*
	 * 根据ID获取人物图册
	 * @param ID
	 * @throws Exception
	 * @author tianguifang
	 */
	public UpPhoto findById(String id);
	
	/*
	 * 根据对象删除人物图册
	 * @param UpPhoto
	 * @throws Exception
	 * @author tianguifang
	 */
	public int deleteBatchById(List<String> ids);
}
