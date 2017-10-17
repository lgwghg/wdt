/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.submit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.submit.service.SubmitUrlService;
import com.webside.submit.mapper.SubmitUrlMapper;
import com.webside.submit.model.SubmitUrlEntity;
import com.webside.util.IdGen;

/**
 * 提交搜索关键字url服务实现类
 *
 * @author zengxn
 * @date 2017-06-13 17:49:15
 */
@Service("submitUrlService")
public class SubmitUrlServiceImpl extends AbstractService<SubmitUrlEntity, String> implements SubmitUrlService 
{
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(submitUrlMapper);
	}

	/**
	 * 提交搜索关键字url DAO定义
	 */
	@Autowired
	private SubmitUrlMapper submitUrlMapper;
	
	/**
	 * 新增提交搜索关键字url
	 * @author zengxn
	 */
	@Override
	public int insert(SubmitUrlEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增提交搜索关键字url出错：", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据ID修改提交搜索关键字url
	 * @author zengxn
	 */
	@Override
	public int updateById(SubmitUrlEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改提交搜索关键字url出错：", e);
			throw new ServiceException(e);
		}
	}
}
