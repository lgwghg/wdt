/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.integral.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.integral.service.IUserIntegralService;
import com.webside.integral.mapper.IUserIntegralMapper;
import com.webside.integral.model.UserIntegral;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserIncrementEntity;
import com.webside.user.service.UserIncrementService;
import com.webside.util.IdGen;

/**
 * 用户积分服务实现类
 *
 * @author tianguifang
 * @date 2017-06-15 17:46:59
 */
@Service("userIntegralService")
public class UserIntegralService extends AbstractService<UserIntegral, String> implements IUserIntegralService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userIntegralMapper);
	}

	/**
	 * 用户积分 DAO定义
	 */
	@Autowired
	private IUserIntegralMapper userIntegralMapper;
	@Autowired
	private UserIncrementService userIncrementService;

	@Override
	public String insertIntegral(UserIntegral entity) {
		String id = IdGen.uuid();
		entity.setId(id);
		entity.setCreateTime(System.currentTimeMillis());
		entity.setCreateId(entity.getUserId());
		entity.setStatus(1);
		int result = userIntegralMapper.insert(entity);
		if (result > 0 && entity.getType() != 0) {
			UserIncrementEntity userIncrementEntity = userIncrementService.findByUserId(entity.getUserId());
			UserIncrementEntity updateIncrement = new UserIncrementEntity();
			updateIncrement.setId(userIncrementEntity.getId());
			updateIncrement.setIntegral(entity.getIntegral());
			updateIncrement.setUser(ShiroAuthenticationManager.getUserEntity());
			userIncrementService.updateById(updateIncrement);
			
		}
		return id;
	}

	/**
	 * 根据用户和积分类型，查询积分
	 * @param id
	 * @param i
	 * @return
	 * @author tianguifang
	 */
	@Override
	public List<UserIntegral> findByUserIdAndType(String userId, int integralType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("integralType", integralType);
		// 每日任务
		if (integralType == GlobalConstant.INTEGRAL_TYPE_COMPLETE_COMMENT_8 
				|| integralType == GlobalConstant.INTEGRAL_TYPE_COMPLETE_GRADE_7) {
			
			param.put("time", getBeginToday());
		}
		return userIntegralMapper.findByUserIdAndType(param);
	}
	private long getBeginToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}
}
