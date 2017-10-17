/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.sign.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.integral.model.UserIntegral;
import com.webside.integral.service.IUserIntegralService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.service.UserIncrementService;
import com.webside.user.sign.entities.UserSign;
import com.webside.user.sign.mapper.IUserSignMapper;
import com.webside.user.sign.service.IUserSignService;
import com.webside.util.IdGen;

/**
 * 用户签到服务实现类
 *
 * @author tianguifang
 * @date 2016-12-06 15:04:37
 */
@Service("userSignService")
public class UserSignService extends AbstractService<UserSign, String> implements IUserSignService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userSignMapper);
	}

	/**
	 * 用户签到 DAO定义
	 */
	@Autowired
	private IUserSignMapper userSignMapper;
	@Autowired
	private IUserIntegralService userIntegralService;
	/**
	 * 用户签到
	 */
	@Override
	public int insert(UserSign userSign) {
		if (userSign == null) {
			return 0;
		}
		UserSign todaySign = this.querySignInToday(userSign.getUserId());
		if (todaySign != null) { // 判断今天是否已经签到过，已经签到过，则返回，签到失败
			return -1;
		}
		String signId = IdGen.uuid();
		userSign.setId(signId);
		userSign.setAddTime(System.currentTimeMillis());
		//Long gold = Long.valueOf(getSignGiveGold());
		userSign.setIntegral(5);
		int result = userSignMapper.insert(userSign);// 签到
		if (result > 0) {
			try {
				// 签到成功加积分明细
				UserIntegral userIntegral = new UserIntegral();
				userIntegral.setId(IdGen.uuid());
				userIntegral.setIntegral(5);
				userIntegral.setStatus(1);
				userIntegral.setCreateTime(System.currentTimeMillis());
				userIntegral.setType(1);
				userIntegral.setIntegralSource("签到");
				userIntegral.setUserId(userSign.getUserId());
				userIntegral.setCreateId(userSign.getUserId());
				userIntegralService.insert(userIntegral);
				
			} catch (Exception e) {
				logger.error("签到加积分明细异常", e);
			}
		}
		return result;
	}

	/**
	 * 查询签到记录
	 * @param userId
	 * @return
	 */
	@Override
	public UserSign querySignInToday(String userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		param.put("addTime", calendar.getTimeInMillis());
		UserSign todaySign = userSignMapper.querySignInToday(param);
		return todaySign;
	}

	/**
	 * 获取1--30的随机数
	 * 
	 * @return
	 */
	private Integer getSignGiveGold() {
		int gold = (int) (Math.random() * 30);
		if (gold == 0) {
			gold = 1;
		}
		return gold;
	}

	/*public static void main(String[] args) {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			int gold = (int) (Math.random() * 30);
			if (gold == 0) {
				System.out.println(i + "_" + gold);
				break;
			}
		}
	}*/
}
