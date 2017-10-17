/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.video.model.VideoCommentLikeEntity;

/**
 * 评论点赞数据访问接口
 *
 * @author zfei
 * @date 2017-06-12 16:03:14
 */
@Repository
public interface VideoCommentLikeMapper extends BaseMapper<VideoCommentLikeEntity, String> 
{
	
}
