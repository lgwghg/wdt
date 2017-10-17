/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.up.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.up.service.IUpPhotoService;
import com.webside.up.mapper.IUpPhotoMapper;
import com.webside.up.model.UpPhoto;

/**
 * 人物图册服务实现类
 *
 * @author tianguifang
 * @date 2017-06-06 11:42:41
 */
@Service("upPhotoService")
public class UpPhotoService extends AbstractService<UpPhoto, String> implements IUpPhotoService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(upPhotoMapper);
	}

	/**
	 * 人物图册 DAO定义
	 */
	@Autowired
	private IUpPhotoMapper upPhotoMapper;
}
