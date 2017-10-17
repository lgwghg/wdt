/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.interest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.integral.model.UserIntegral;
import com.webside.integral.service.IUserIntegralService;
import com.webside.user.interest.service.IUserInterestService;
import com.webside.user.interest.mapper.IUserInterestMapper;
import com.webside.user.interest.model.UserInterest;
import com.webside.util.IdGen;

/**
 * 用户兴趣服务实现类
 *
 * @author tianguifang
 * @date 2017-06-23 15:26:40
 */
@Service("userInterestService")
public class UserInterestService extends AbstractService<UserInterest, String> implements IUserInterestService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userInterestMapper);
	}

	/**
	 * 用户兴趣 DAO定义
	 */
	@Autowired
	private IUserInterestMapper userInterestMapper;
	@Autowired
	private IUserIntegralService userIntegralService;
	/**
	 * 根据用户id查询用户兴趣
	 * @param userId
	 * @return
	 */
	@Override
	public List<UserInterest> queryUserInterestByUserId(String userId) {
		return userInterestMapper.queryUserInterestByUserId(userId);
	}
	/**
	 * 添加兴趣
	 */
	@Override
	public int insert(UserInterest entity) {
		int result = userInterestMapper.insert(entity);
		if (result > 0) {
			try {
				List<UserIntegral> integralList = userIntegralService.findByUserIdAndType(entity.getUserId(), GlobalConstant.INTEGRAL_TYPE_INTERSET_3);
				if (CollectionUtils.isEmpty(integralList)) {
					UserIntegral integral = new UserIntegral();
					integral.setId(IdGen.uuid());
					integral.setUserId(entity.getUserId());
					integral.setType(GlobalConstant.INTEGRAL_TYPE_INTERSET_3);
					integral.setIntegralSource("添加兴趣");
					integral.setStatus(1);
					integral.setIntegral(5);
					integral.setCreateId(entity.getUserId());
					integral.setCreateTime(System.currentTimeMillis());
					userIntegralService.insertIntegral(integral);
				}
			} catch (Exception e) {
				logger.error("添加兴趣，加积分出错", e);
			}
			
		}
		return result;
	}
}
