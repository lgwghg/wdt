/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.message.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.user.message.model.MessageVo;
import com.webside.user.message.model.UserMessageEntity;

/**
 * 用户消息数据访问接口
 *
 * @author zfei
 * @date 2017-06-23 18:02:58
 */
@Repository
public interface UserMessageMapper extends BaseMapper<UserMessageEntity, String> 
{
	/**
	 * 根据用户id查询和时间用户未读消息
	 * @param param
	 * @return
	 * @author tianguifang
	 */
	public List<MessageVo> queryUserMessageListByPg(Map<String, Object> param);
	public int queryUserMessageListByPgCount(Map<String, Object> param);
	/**
	 * 根据业务id，更新消息已读状态
	 * @param commentIdList
	 * @return
	 * @author tianguifang
	 */
	public int updateMessageStateByBusinessId(Map<String, Object> map);
	
	public List<String> queryMessageIdListByVidAndUId(Map<String, Object> map);
	
}
