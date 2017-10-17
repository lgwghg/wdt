/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.service;

import java.util.List;
import java.util.Map;
import com.webside.sys.model.HelpEntity;

/**
 * 系统帮助服务接口
 *
 * @author zfei
 * @date 2017-06-30 13:51:40
 */
public interface HelpService 
{
	/**
	 * 按条件查询系统帮助
	 * @author zfei
	 */
	List<HelpEntity> queryListByPage(Map<String, Object> parameter);
	
	/**
	 * 新增系统帮助
	 * @author zfei
	 */
	int insert(final HelpEntity entity);
	
	/**
	 * 根据ID更新修改系统帮助
	 * @author zfei
	 */
	int updateById(final HelpEntity entity);
	
	/**
	 * 根据ID获取系统帮助
	 * @author zfei
	 */
	HelpEntity findById(String id);
	
	/**
	 * 根据ID删除系统帮助
	 * @author zfei
	 */
	int deleteById(String id);
}
