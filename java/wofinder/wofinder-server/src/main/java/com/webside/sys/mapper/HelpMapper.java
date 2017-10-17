/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.sys.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.sys.model.HelpEntity;

/**
 * 系统帮助数据访问接口
 * 
 * @author zfei
 * @date 2017-06-30 13:51:40
 */
@Repository
public interface HelpMapper extends BaseMapper<HelpEntity, String> {

}
