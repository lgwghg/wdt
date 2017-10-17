/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.exception.ServiceException;
import com.webside.integral.model.UserIntegral;
import com.webside.integral.service.IUserIntegralService;
import com.webside.user.mapper.UserIncrementMapper;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrementEntity;
import com.webside.user.service.UserCacheService;
import com.webside.user.service.UserIncrementService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 用户增量服务实现类
 *
 * @author zengxn
 * @date 2017-05-05 11:09:24
 */
@Service("userIncrementService")
public class UserIncrementServiceImpl extends AbstractService<UserIncrementEntity, String> implements UserIncrementService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userIncrementMapper);
	}

	/**
	 * 用户增量 DAO定义
	 */
	@Autowired
	private UserIncrementMapper userIncrementMapper;

	/**
	 * 用户缓存 Sevice定义
	 */
	@Autowired
	private UserCacheService userCacheService;
	@Autowired
	private IUserIntegralService userIntegralService;
	/**
	 * 
	 * @param UserIncrementEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int insert(UserIncrementEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增用户增量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改用户增量
	 * 
	 * @param UserIncrementEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int updateById(UserIncrementEntity entity) {
		try {
			if (entity == null || entity.getUser() == null || StringUtils.isBlank(entity.getUser().getId())) {
				return 0;
			}
			int result = super.updateById(entity);
			if (result == 1) {
				// 查询最新增量数据
				UserIncrementEntity increment = findByUserId(entity.getUser().getId());
				UserEntity user = new UserEntity();
				user.setId(increment.getUser().getId());
				user.setUserIncrement(increment);
				// 修改缓存用户数据
				// 暂时，不需要存缓存，后面需要时打开
				//userCacheService.updateUserToRedis(user);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改用户增量出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据userId获取用户增量
	 * 
	 * @param userId
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public UserIncrementEntity findByUserId(String userId) {
		try {
			UserIncrementEntity entity = new UserIncrementEntity();
			UserEntity userEntity = new UserEntity();
			userEntity.setId(userId);
			entity.setUser(userEntity);
			List<UserIncrementEntity> list = userIncrementMapper.findByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据userId查询用户角色映射出错：", e);
			throw new ServiceException(e);
		}
	}
	/**
	 * 根据用户id更新用户其他信息
	 */
	@Override
	public int updateByUserId(UserIncrementEntity userIncrement) {
		if (userIncrement == null || userIncrement.getUser() == null || StringUtils.isBlank(userIncrement.getUser().getId())) {
			return 0;
		}
		UserIncrementEntity increment = this.findByUserId(userIncrement.getUser().getId());
		userIncrement.setId(increment.getId());
		int result= this.updateById(userIncrement);
		if (result > 0) {
			try {
				Integer integralType = null;
				String integralSource = "";
				if (userIncrement.getBirthday() != null) {
					integralType = GlobalConstant.INTEGRAL_TYPE_BIRTHDAY_4;
					integralSource = "设置生日";
				}
				if (userIncrement.getSex() != null) {
					integralType = GlobalConstant.INTEGRAL_TYPE_SEX_5;
					integralSource = "设置性别";
				}
				if (integralType != null) {
					String userId = userIncrement.getUser().getId();
					List<UserIntegral> integralList = userIntegralService.findByUserIdAndType(userId, integralType);
					if (CollectionUtils.isEmpty(integralList)) {
						UserIntegral integral = new UserIntegral();
						integral.setId(IdGen.uuid());
						integral.setUserId(userId);
						integral.setType(integralType);
						integral.setIntegralSource(integralSource);
						integral.setStatus(1);
						integral.setIntegral(5);
						integral.setCreateId(userId);
						integral.setCreateTime(System.currentTimeMillis());
						userIntegralService.insertIntegral(integral);
					}
					
				}
			} catch (Exception e) {
				logger.error("加积分出错", e);
			}
		}
		return result;
	}
}
