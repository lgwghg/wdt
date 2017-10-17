/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.user.uswitch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.user.uswitch.service.IUserSwitchService;
import com.webside.user.uswitch.mapper.IUserSwitchMapper;
import com.webside.user.uswitch.model.UserSwitch;

/**
 * 用户开关服务实现类
 *
 * @author tianguifang
 * @date 2017-06-27 15:04:58
 */
@Service("userSwitchService")
public class UserSwitchService extends AbstractService<UserSwitch, String> implements IUserSwitchService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(userSwitchMapper);
	}

	/**
	 * 用户开关 DAO定义
	 */
	@Autowired
	private IUserSwitchMapper userSwitchMapper;
	@Autowired
	private DictService dictService;
	/**
	 * 根据用户id查询用户开关设置
	 */
	@Override
	public List<UserSwitch> queryUserSwitchByUserId(String userId) {
		// 获取字典表中有哪些类型开关
		List<DictEntity> dictList = dictService.queryListByType(GlobalConstant.SWITCH_CONFIG_GROUP);
		if (CollectionUtils.isEmpty(dictList)) {
			return null;
		}
		// 查询用户设置了哪些开关
		List<UserSwitch> switchList = userSwitchMapper.queryUserSwitchByUserId(userId);
		Map<String, UserSwitch> switchMap = new HashMap<String, UserSwitch>();
		if (!CollectionUtils.isEmpty(switchList)) {
			for (UserSwitch uswitch : switchList) {
				switchMap.put(uswitch.getSwitchType(), uswitch);
			}
		}
		// 用户未设置的开关，默认打开
		List<UserSwitch> userSwitchList = new ArrayList<UserSwitch>();
		for (DictEntity dict : dictList) {
			UserSwitch userSwitch = new UserSwitch();
			userSwitch.setSwitchType(dict.getValue());
			userSwitch.setSwitchDesc(dict.getLabel());
			UserSwitch uswitch = switchMap.get(dict.getValue());
			Integer switchValue = 1;
			if (uswitch != null) {
				switchValue = uswitch.getValue();
				userSwitch.setId(uswitch.getId());
			}
			userSwitch.setValue(switchValue);
			
			userSwitchList.add(userSwitch);
		}
		return userSwitchList;
	}
}
