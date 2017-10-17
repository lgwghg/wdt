/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zfei
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.message.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.user.message.mapper.UserMessageMapper;
import com.webside.user.message.model.MessageVo;
import com.webside.user.message.model.UserMessageEntity;
import com.webside.user.message.service.UserMessageService;
import com.webside.util.IdGen;
import com.webside.video.service.VideoCommentService;

/**
 * 用户消息服务实现类
 * 
 * @author zfei
 * @date 2017-06-23 18:02:58
 */
@Service("userMessageService")
public class UserMessageServiceImpl extends AbstractService<UserMessageEntity, String> implements UserMessageService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userMessageMapper);
	}

	/**
	 * 用户消息 DAO定义
	 */
	@Autowired
	private UserMessageMapper userMessageMapper;
	@Autowired
	private  VideoCommentService videoCommentService;
	// 通过线程池获取线程
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();// 启动线程池

	/**
	 * 新增用户消息
	 * 
	 * @author zfei
	 */
	@Override
	public int insert(UserMessageEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增用户消息出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改用户消息
	 * 
	 * @author zfei
	 */
	@Override
	public int updateById(UserMessageEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改用户消息出错：", e);
			throw new ServiceException(e);
		}
	}

	public void addMessageForComment(String userId, String nickName, String businessId, String content, String videoId, String videoTitle) {
		String message = "<a href=\"/video/" + videoId + "\">" + nickName + " 在 " + videoTitle + " 中回复我：" + content + "</a>";
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_1, userId, businessId, message));
	}
	/**
	 * 任务完成消息
	 * @param userId
	 * @param businessId
	 * @param taskType
	 */
	public void addMessageForTask(String userId, String businessId, Integer taskType) {
		String message = "";
		if (taskType == GlobalConstant.INTEGRAL_TYPE_COMPLETE_USERINFO_6) {
			message = "恭喜，您完成了个人资料设置,成功获得10个积分。";
		} else if (taskType == GlobalConstant.INTEGRAL_TYPE_COMPLETE_GRADE_7) {
			message = "恭喜，您完成了每日10条评分,成功获得10个积分。";
		} else if (taskType == GlobalConstant.INTEGRAL_TYPE_COMPLETE_COMMENT_8) {
			message = "恭喜，您完成了每日10条评论,成功获得10个积分。";
		}
		cachedThreadPool.execute(new AddUserMessageRunner(GlobalConstant.MESSAGE_BUSINESS_TYPE_TASK_2, userId, businessId, message));
	}
	/**
	 * 异步执行消息插入
	 * 
	 * @author zengxn
	 * 
	 */
	class AddUserMessageRunner implements Runnable {
		private String userId;
		private String businessType;
		private String businessId;
		private String message;

		public AddUserMessageRunner(String businessType, String userId, String businessId, String message) {
			this.userId = userId;
			this.businessType = businessType;
			this.businessId = businessId;
			this.message = message;
		}

		public void run() {
			addMessage(businessType, userId, businessId, message);
		}
	}

	/**
	 * 新增用户消息
	 * 
	 * @param userId
	 * @param businessType
	 * @param businessId
	 * @param message
	 * @return
	 */
	private int addMessage(String businessType, String userId, String businessId, String message) {
		int result = 0;
		try {
			UserMessageEntity t = new UserMessageEntity();
			t.setId(IdGen.uuid());
			t.setUserId(userId);
			t.setBusinessType(businessType);
			t.setBusinessId(businessId);
			t.setCreateTime(System.currentTimeMillis() + "");
			t.setState(GlobalConstant.MESSAGE_STATE_0);
			t.setIsDeleted(GlobalConstant.DICTTYPE_IS_DELETE_0);
			t.setContent(message);
			result = super.insert(t);
		} catch (Exception e) {
			logger.error("新增用户消息出错：", e);
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public List<MessageVo> queryUserMessageListByPg(Map<String, Object> param) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) -1));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		param.put("createTime", calendar.getTimeInMillis());
		return userMessageMapper.queryUserMessageListByPg(param);
	}

	@Override
	public int updateMessageState(String userId, Integer type, String businessId) {
		if (type == 0) {// 系统公告，消息表插入一条数据
			UserMessageEntity message = new UserMessageEntity();
			message.setId(IdGen.uuid());
			message.setBusinessId(businessId);
			message.setBusinessType(GlobalConstant.MESSAGE_BUSINESS_TYPE_SYS_3);
			message.setState(GlobalConstant.MESSAGE_STATE_1);
			message.setCreateTime(System.currentTimeMillis() + "");
			message.setUpdateTime(System.currentTimeMillis() + "");
			message.setUserId(userId);
			int result = userMessageMapper.insert(message);
			return result;
		} else if (type == 1) {// 任务消息，直接修改状态
			UserMessageEntity message = new UserMessageEntity();
			message.setId(businessId);
			message.setState(GlobalConstant.MESSAGE_STATE_1);
			message.setUpdateTime(System.currentTimeMillis() + "");
			int result = userMessageMapper.updateById(message);
			return result;
		} else if (type == 2) {// 视频回复消息
			Map<String, Object> param = new HashMap<>();
			param.put("videoId", businessId);
			param.put("userId", userId);
			List<String> idList = userMessageMapper.queryMessageIdListByVidAndUId(param);
			if (!CollectionUtils.isEmpty(idList)) {
				Map<String, Object> map = new HashMap<>();
				map.put("idList", idList);
				map.put("updateTime", System.currentTimeMillis());
				int result = userMessageMapper.updateMessageStateByBusinessId(map);
				return result;
				
			}
		}
		return 0;
	}

	@Override
	public int getUnreadMsgNum(String userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) -1));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		param.put("createTime", calendar.getTimeInMillis());
		int result = userMessageMapper.queryUserMessageListByPgCount(param);
		return result;
	}
}
