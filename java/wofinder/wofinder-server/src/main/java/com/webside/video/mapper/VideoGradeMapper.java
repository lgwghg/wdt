/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.video.model.VideoGradeEntity;

/**
 * 视频评分数据访问接口
 *
 * @author zfei
 * @date 2017-06-12 16:04:07
 */
@Repository
public interface VideoGradeMapper extends BaseMapper<VideoGradeEntity, String> 
{
	/**
	 * 根据用户id查询今天用户评分数量
	 * @param paramMap
	 * @return
	 * @author tianguifang
	 */
	Integer queryUserTodayGradeNumByUserId(Map<String, Object> paramMap);
	
}
