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

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.up.model.UpSecondLevel;

/**
 * 人物二级信息数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-06 11:44:01
 */
@Repository
public interface IUpSecondLevelMapper extends BaseMapper<UpSecondLevel, String> 
{
	/**
	 * 根据人物id，查询人物二级信息
	 * @param upId
	 * @return
	 * @author tianguifang
	 */
	List<UpSecondLevel> queryUpSecondLevelByUpId(String upId);
}
