/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.message.service;

import java.util.List;
import java.util.Map;

import com.webside.user.message.model.MessageVo;
import com.webside.user.message.model.UserMessageEntity;

/**
 * 用户消息服务接口
 * 
 * @author zfei
 * @date 2017-06-23 18:02:58
 */
public interface UserMessageService {
	/**
	 * 按条件查询用户消息
	 * 
	 * @author zfei
	 */
	List<UserMessageEntity> queryListByPage(Map<String, Object> parameter);

	/**
	 * 新增用户消息
	 * 
	 * @author zfei
	 */
	int insert(final UserMessageEntity entity);

	/**
	 * 根据ID更新修改用户消息
	 * 
	 * @author zfei
	 */
	int updateById(final UserMessageEntity entity);

	/**
	 * 根据ID获取用户消息
	 * 
	 * @author zfei
	 */
	UserMessageEntity findById(String id);

	/**
	 * 根据ID删除用户消息
	 * 
	 * @author zfei
	 */
	int deleteById(String id);
	
	/**
	 * 评论发送通知
	 * @param userId     用户ID（接收用户）
	 * @param nickName   用户昵称（发送用户）
	 * @param businessId 业务ID，如评论表(t_video_comment)
	 * @param content    通知内容
	 * @param videoId    视频ID
	 * @param videoName  视频名称
	 */
	public void addMessageForComment(String userId, String nickName, String businessId, String content, String videoId, String videoTitle);
	
	/**
	 * 任务完成消息
	 * @param userId
	 * @param businessId
	 * @param taskType
	 * @author tianguifang
	 */
	public void addMessageForTask(String userId, String businessId, Integer taskType);
	
	/**
	 * 根据用户id查询和时间用户未读消息
	 * @param param
	 * @return
	 * @author tianguifang
	 */
	public List<MessageVo> queryUserMessageListByPg(Map<String, Object> param);
	/**
	 * 更新消息已读状态
	 * @param type
	 * @param businessId
	 * @return
	 * @author tianguifang
	 */
	int updateMessageState(String userId, Integer type, String businessId);
	/**
	 * 获取用户未读消息数量
	 * @param userId
	 * @return
	 */
	int getUnreadMsgNum(String userId);
}
