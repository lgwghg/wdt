/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.sys.service.HelpService;
import com.webside.sys.mapper.HelpMapper;
import com.webside.sys.model.HelpEntity;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 系统帮助服务实现类
 *
 * @author zfei
 * @date 2017-06-30 13:51:40
 */
@Service("helpService")
public class HelpServiceImpl extends AbstractService<HelpEntity, String> implements HelpService 
{
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(helpMapper);
	}

	/**
	 * 系统帮助 DAO定义
	 */
	@Autowired
	private HelpMapper helpMapper;
	
	/**
	 * 新增系统帮助
	 * @author zfei
	 */
	@Override
	public int insert(HelpEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			if(StringUtils.isBlank(entity.getAddTime())) {
				entity.setAddTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if(entity.getSysUserId() == null) {
				entity.setSysUserId(ShiroAuthenticationManager.getUserId());
			}
			
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增系统帮助出错：", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 根据ID修改系统帮助
	 * @author zfei
	 */
	@Override
	public int updateById(HelpEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改系统帮助出错：", e);
			throw new ServiceException(e);
		}
	}
}
