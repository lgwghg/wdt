/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.video.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.video.model.VideoValueInform;

/**
 * 视频标签操作日志数据访问接口
 *
 * @author tianguifang
 * @date 2017-08-21 16:50:17
 */
@Repository
public interface IVideoValueInformMapper extends BaseMapper<VideoValueInform, String> 
{
	
}
