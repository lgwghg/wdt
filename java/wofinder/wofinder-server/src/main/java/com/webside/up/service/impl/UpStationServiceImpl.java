/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.up.mapper.UpStationMapper;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpStationEntity;
import com.webside.up.service.UpService;
import com.webside.up.service.UpStationService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 视频作者站点服务实现类
 *
 * @author zengxn
 * @date 2017-04-15 18:29:58
 */
@Service("upStationService")
public class UpStationServiceImpl extends AbstractService<UpStationEntity, String> implements UpStationService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(upStationMapper);
	}

	/**
	 * 视频作者站点 DAO定义
	 */
	@Autowired
	private UpStationMapper upStationMapper;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 视频作者 Service定义
	 */
	@Autowired
	private UpService upService;

	/**
	 * 新增视频作者站点
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(UpStationEntity entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频作者站点
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(UpStationEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频作者站点
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpStationEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<UpStationEntity> list = super.queryListByPage(parameter);
			for (UpStationEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getStation() != null && StringUtils.isNotBlank(entity.getStation().getValue())) {
					entity.setStation(dictService.findByTypeValue(GlobalConstant.STATION_TYPE, String.valueOf(entity.getStation().getValue())));
				}
				if (entity.getThirdParty() != null && StringUtils.isNotBlank(entity.getThirdParty().getValue())) {
					entity.setThirdParty(dictService.findByTypeValue(GlobalConstant.THIRD_PARTY_TYPE, String.valueOf(entity.getThirdParty().getValue())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者id、站点值、第三方值、id获取视频作者站点
	 * 
	 * @author zengxn
	 */
	@Override
	public UpStationEntity findByUpStationThirdPartyId(UpEntity up, DictEntity station, DictEntity thirdParty, String id) {
		try {
			UpStationEntity entity = new UpStationEntity(id);
			entity.setUp(up);
			entity.setStation(station);
			entity.setThirdParty(thirdParty);
			List<UpStationEntity> list = upStationMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据视频作者id、站点值、第三方值、id获取视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}
	
	public UpStationEntity findByStationAndHomeId(String stationValue, String homeId) {
		try {
			UpStationEntity entity = new UpStationEntity();
			entity.setStation(new DictEntity(stationValue));
			entity.setHomeId(homeId);
			List<UpStationEntity> list = upStationMapper.queryListByEntity(entity);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error("根据站点值、站点用户id获取视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据视频作者ID删除视频作者站点
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteByUpId(String upId) {
		try {
			UpStationEntity entity = new UpStationEntity();
			entity.setUp(new UpEntity(upId));
			return upStationMapper.deleteByEntity(entity);
		} catch (Exception e) {
			logger.error("根据视频作者ID删除视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据upIds查询视频作者站点
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpStationEntity> queryListByUpIds(List<String> upIds, String stationValue, String thirdPartyValue) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("upIds", upIds);
			map.put("stationValue", stationValue);
			map.put("thirdPartyValue", thirdPartyValue);
			List<UpStationEntity> list = upStationMapper.findByUpIds(map);
			for (UpStationEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getStation() != null && StringUtils.isNotBlank(entity.getStation().getValue())) {
					entity.setStation(dictService.findByTypeValue(GlobalConstant.STATION_TYPE, String.valueOf(entity.getStation().getValue())));
				}
				if (entity.getThirdParty() != null && StringUtils.isNotBlank(entity.getThirdParty().getValue())) {
					entity.setThirdParty(dictService.findByTypeValue(GlobalConstant.THIRD_PARTY_TYPE, String.valueOf(entity.getThirdParty().getValue())));
				}
				if (entity.getUp() != null && StringUtils.isNotBlank(entity.getUp().getId())) {
					entity.setUp(upService.findById(entity.getUp().getId()));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("根据upIds查询视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频作者站点的视频作者
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateUpIdBatchById(List<String> ids, String upId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("upId", upId);
			map.put("ids", ids);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}

			return upStationMapper.updateUpIdBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频作者站点的视频作者出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频作者站点的状态
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateStatusBatchById(List<String> ids, String statusValue, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("statusValue", statusValue);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return upStationMapper.updateStatusBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频作者站点的状态出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据upId查询视频作者站点
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpStationEntity> queryListByUpId(String upId) {
		try {
			UpStationEntity entity = new UpStationEntity();
			entity.setUp(new UpEntity(upId));
			return upStationMapper.queryListByEntity(entity);
		} catch (Exception e) {
			logger.error("根据upId查询视频作者站点出错：", e);
			throw new ServiceException(e);
		}
	}
}
