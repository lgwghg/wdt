/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.task.mapper;

import org.springframework.stereotype.Repository;
import com.webside.base.basemapper.BaseMapper;
import com.webside.task.model.TaskLikeEntity;

/**
 * 评论点赞数据访问接口
 *
 * @author aning
 * @date 2017-06-12 16:03:14
 */
@Repository
public interface TaskLikeMapper extends BaseMapper<TaskLikeEntity, String> {

}
