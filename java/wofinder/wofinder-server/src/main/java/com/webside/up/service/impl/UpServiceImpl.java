/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.up.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.sys.service.GameService;
import com.webside.sys.vo.ValueVo;
import com.webside.up.mapper.IUpPhotoMapper;
import com.webside.up.mapper.UpMapper;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpNameEntity;
import com.webside.up.model.UpPhoto;
import com.webside.up.model.UpStationEntity;
import com.webside.up.model.UpValueEntity;
import com.webside.up.model.UpVo;
import com.webside.up.service.UpNameService;
import com.webside.up.service.UpService;
import com.webside.up.service.UpStationService;
import com.webside.up.service.UpValueService;
import com.webside.up.vo.VideoUpStationVo;
import com.webside.up.vo.VideoUpVo;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.service.VideoStationService;

/**
 * 视频作者服务实现类
 *
 * @author zengxn
 * @date 2017-04-15 18:28:19
 */
@Service("upService")
public class UpServiceImpl extends AbstractService<UpEntity, String> implements UpService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(upMapper);
	}

	/**
	 * 视频作者 DAO定义
	 */
	@Autowired
	private UpMapper upMapper;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 游戏 Service定义
	 */
	@Autowired
	private GameService gameService;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 视频作者名称 Service定义
	 */
	@Autowired
	private UpNameService upNameService;

	/**
	 * 视频站点 Service定义
	 */
	@Autowired
	private VideoStationService videoStationService;

	/**
	 * 视频作者属性值关联 Service定义
	 */
	@Autowired
	private UpValueService upValueService;

	/**
	 * 视频作者站点 Service定义
	 */
	@Autowired
	private UpStationService upStationService;
	@Autowired
	private IUpPhotoMapper upPhotoMapper;

	/**
	 * 新增视频作者
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(UpEntity entity) {
		try {
			if(StringUtils.isBlank(entity.getId())){
				entity.setId(IdGen.uuid());
			}
			if (StringUtils.isBlank(entity.getCreateTime())) {
				entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			if (StringUtils.isNotBlank(entity.getName()) && entity.getName().length() > 50) {
				entity.setName(entity.getName().substring(0, 50));
			}
			if(entity.getIsSearch() == null || StringUtils.isBlank(entity.getIsSearch().getValue())){
				entity.setIsSearch(new DictEntity(GlobalConstant.DICTTYPE_YES_NO_0));
			}
			int result = super.insert(entity);
			if (result > 0) {
				UpNameEntity upNameEntity = new UpNameEntity();
				upNameEntity.setCreateTime(entity.getCreateTime());
				upNameEntity.setCreateUser(entity.getCreateUser());
				upNameEntity.setName(entity.getName());
				upNameEntity.setStatus(entity.getStatus());
				upNameEntity.setUp(entity);
				upNameEntity.setType(new DictEntity(GlobalConstant.UP_NAME_TYPE_1));
				result = upNameService.insert(upNameEntity);
			}
			return result;
		} catch (Exception e) {
			logger.error("新增视频作者出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频作者
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(UpEntity entity) {
		try {
			if (StringUtils.isBlank(entity.getUpdateTime())) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}

			// 修改主名名称
			String updateUserId = entity.getUpdateUser() == null || StringUtils.isBlank(entity.getUpdateUser().getId()) ? null : entity.getUpdateUser().getId();
			upNameService.updateNameByUpIdType(entity.getName(), entity.getId(), GlobalConstant.UP_NAME_TYPE_1, updateUserId, entity.getUpdateTime());

			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频作者出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询视频作者
	 * 
	 * @author zengxn
	 */
	@Override
	public List<UpEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<UpEntity> list = super.queryListByPage(parameter);
			for (UpEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userService.findById(entity.getUpdateUser().getId()));
				}
				if (entity.getGame() != null && StringUtils.isNotBlank(entity.getGame().getId())) {
					entity.setGame(gameService.findById(entity.getGame().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getIsSearch() != null && StringUtils.isNotBlank(entity.getIsSearch().getValue())) {
					entity.setIsSearch(dictService.findByTypeValue(GlobalConstant.DICTTYPE_YES_NO, String.valueOf(entity.getIsSearch().getValue())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频作者出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 批量修改视频作者状态
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateStatusBatchById(List<String> upIds, String statusValue, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("upIds", upIds);
			map.put("statusValue", statusValue);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return upMapper.updateStatusBatchById(map);
		} catch (Exception e) {
			logger.error("批量修改视频作者状态出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除视频作者，并且删除相关子表记录
	 * 
	 * @author zengxn
	 */
	@Override
	public int deleteRelevantById(String id, String updateUserId) {
		try {
			String updateTime = StringUtils.toString(System.currentTimeMillis());
			// 删除视频作者名称记录
			upNameService.deleteByUpId(id);
			// 删除视频作者属性值关联记录
			upValueService.deleteByUpId(id);
			// 重置符合要求的视频站点视频作者字段
			videoStationService.resetUpIdByUpId(id, updateUserId, updateTime);
			return super.deleteById(id);
		} catch (Exception e) {
			logger.error("根据ID删除视频作者，并且删除相关子表记录出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频作者名称
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateNameById(String id, String name, String updateUserId, String updateTime) {
		try {
			UpEntity entity = new UpEntity(id);
			entity.setName(name);
			entity.setUpdateUser(new UserEntity(updateUserId));
			if (StringUtils.isBlank(updateTime)) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			} else {
				entity.setUpdateTime(updateTime);
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频作者名称出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 重置符合要求的视频作者游戏字段
	 * 
	 * @author zengxn
	 */
	@Override
	public int resetGameIdByGameId(String gameId, String updateUserId, String updateTime) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gameId", gameId);
			map.put("updateUserId", updateUserId);
			if (StringUtils.isBlank(updateTime)) {
				map.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			} else {
				map.put("updateTime", updateTime);
			}
			return upMapper.resetGameIdByGameId(map);
		} catch (Exception e) {
			logger.error("重置符合要求的视频作者游戏字段出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 合并视频作者
	 * 
	 * @author zengxn
	 */
	@Override
	public Map<String, Object> doMerge(String updateUserId, List<String> upIdList, List<String> checkStationIdList) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 主视频作者id
			String upId = upIdList.get(0);
			// 除主视频作者外的id集合
			List<String> outherUpIdList = new ArrayList<String>();
			for (String id : upIdList) {
				if (!id.equals(upId)) {
					outherUpIdList.add(id);
				}
			}
			// 存在重复记录的站点数量
			int sameCount = 0;
			// 所有站点id集合
			List<String> upStationIdList = new ArrayList<String>();
			// 单条数据，没有重复记录的站点id集合
			List<String> upStationNotSameIdList = new ArrayList<String>();
			// 站点列表
			List<DictEntity> stationList = dictService.queryListByType(GlobalConstant.STATION_TYPE);
			// 第三方列表
			List<DictEntity> thirdPartyList = dictService.queryListByType(GlobalConstant.THIRD_PARTY_TYPE);
			// 重复站点数组对象
			JSONArray jsonArray = new JSONArray();
			// 循环所有站点数据
			for (DictEntity station : stationList) {
				List<UpStationEntity> upStationList = upStationService.queryListByUpIds(upIdList, station.getValue(), null);
				// 该站点有重复站点记录
				if (upStationList != null) {
					if (upStationList.size() > 1) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("name", station.getLabel());
						jsonObject.put("list", upStationList);
						jsonArray.add(jsonObject);
						sameCount++;
					} else if (upStationList.size() == 1) {
						upStationNotSameIdList.add(upStationList.get(0).getId());
					}
				}
				for (UpStationEntity upStationEntity : upStationList) {
					upStationIdList.add(upStationEntity.getId());
				}
			}
			// 循环所有第三方数据
			for (DictEntity thirdParty : thirdPartyList) {
				List<UpStationEntity> upStationList = upStationService.queryListByUpIds(upIdList, null, thirdParty.getValue());
				// 该站点有重复站点记录
				if (upStationList != null && upStationList.size() > 1) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("name", thirdParty.getLabel());
					jsonObject.put("list", upStationList);
					jsonArray.add(jsonObject);
					sameCount++;
				}
				for (UpStationEntity upStationEntity : upStationList) {
					upStationIdList.add(upStationEntity.getId());
				}
			}

			String updateTime = StringUtils.toString(System.currentTimeMillis());

			// 1、处理站点数据
			// 存在重复的视频作者站点记录
			if (sameCount > 0) {
				if (checkStationIdList != null && checkStationIdList.size() > 0) {
					// 执行删除重复的站点信息，选中的站点信息归到主视频作者下
					if (checkStationIdList.size() == sameCount) {
						// 把没有重复记录的站点id集合追加进来
						checkStationIdList.addAll(upStationNotSameIdList);
						// 将选中的以及没有重复的站点归到主视频作者下
						upStationService.updateUpIdBatchById(checkStationIdList, upId, updateUserId, updateTime);
						// 去除选中的记录
						upStationIdList.removeAll(checkStationIdList);
						// 软删其余的记录,目前只是设置状态为无效
						upStationService.updateStatusBatchById(upStationIdList, GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_0, updateUserId, updateTime);
					} else {
						map.put("success", Boolean.FALSE);
						map.put("message", "合并失败，还有未选择的重复记录的保留项");
						return map;
					}
				} else {
					map.put("success", Boolean.FALSE);
					map.put("message", "合并失败，存在重复站点记录，请选择保留项");
					map.put("code", -1);
					map.put("sameList", jsonArray);
					return map;
				}
			} else {
				// 将站点数据归到主视频作者下
				upStationService.updateUpIdBatchById(upStationIdList, upId, updateUserId, updateTime);
			}

			// 2、视频作者名称
			// 名做key,id做value，达到去重名效果
			Map<String, String> upNameMap = new HashMap<String, String>();
			// 所有的名称id集合
			List<String> upNameIdList = new ArrayList<String>();
			// 不重名的名称id集合
			List<String> upNameNotSameIdList = new ArrayList<String>();
			// 与主视频作者主名同名的id集合
			List<String> upNameSameNameIdList = new ArrayList<String>();
			// 主视频作者对象
			UpEntity mainUp = findById(upId);
			// 遍历合并的视频作者id集合
			for (String id : upIdList) {
				List<UpNameEntity> upNameList = upNameService.queryListByUpId(id);
				for (UpNameEntity upName : upNameList) {
					// 排除主视频作者的主名
					if (GlobalConstant.UP_NAME_TYPE_1.equals(upName.getType().getValue()) && upId.equals(upName.getUp().getId())) {
						continue;
					}
					if (upName.getName().equals(mainUp.getName())) {
						upNameSameNameIdList.add(upName.getId());
					}
					upNameMap.put(upName.getName(), upName.getId());
					upNameIdList.add(upName.getId());
				}
			}
			for (String string : upNameMap.values()) {
				upNameNotSameIdList.add(string);
			}
			// 修改不重名的视频作者名称
			if (upNameNotSameIdList != null && upNameNotSameIdList.size() > 0) {
				upNameService.updateUpIdBatchById(upNameNotSameIdList, upId, updateUserId, updateTime);
			}
			// 去除不重名的记录
			upNameIdList.removeAll(upNameNotSameIdList);
			// 追加与主视频作者主名同名的id集合
			upNameIdList.addAll(upNameSameNameIdList);
			// 删除重名的名称
			if (upNameIdList != null && upNameIdList.size() > 0) {
				upNameService.deleteBatchById(upNameIdList);
			}

			// 3、属性值关联
			// 名做key,id做value，达到去重名效果
			Map<String, String> upValueMap = new HashMap<String, String>();
			// 所有的属性值关联id集合
			List<String> upValueIdList = new ArrayList<String>();
			// 不重属性关联的id集合
			List<String> upValueIdNotSameList = new ArrayList<String>();
			// 遍历合并的视频作者id集合
			for (String id : upIdList) {
				List<UpValueEntity> upValueList = upValueService.queryListByUpId(id);
				for (UpValueEntity upValue : upValueList) {
					upValueMap.put(upValue.getValue().getId(), upValue.getId());
					upValueIdList.add(upValue.getId());
				}
			}
			for (String string : upValueMap.values()) {
				upValueIdNotSameList.add(string);
			}
			// 修改不重属性的属性值关联
			if (upValueIdNotSameList != null && upValueIdNotSameList.size() > 0) {
				upValueService.updateUpIdBatchById(upValueIdNotSameList, upId, updateUserId, updateTime);
			}
			// 去除不重属性的记录
			upValueIdList.removeAll(upValueIdNotSameList);
			// 删除重属性的视频作者属性值关联
			if (upValueIdList != null && upValueIdList.size() > 0) {
				upValueService.deleteBatchById(upValueIdList);
			}

			// 4、视频站点
			videoStationService.updateUpIdBatchByUpId(outherUpIdList, upId, updateUserId, updateTime);

			// 5、视频作者基本信息修改
			// 指数最高值
			long popularityIndex = 0;
			for (String id : upIdList) {
				UpEntity up = findById(id);
				if (up.getPopularityIndex() != null && up.getPopularityIndex().longValue() > popularityIndex) {
					popularityIndex = up.getPopularityIndex().longValue();
				}
			}
			// 修改主视频作者人气指数
			updatePopularityIndexById(upId, popularityIndex, updateUserId, updateTime);
			// 软删除其它视频作者，改状态
			if (outherUpIdList != null && outherUpIdList.size() > 0) {
				updateStatusBatchById(outherUpIdList, GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_0, updateUserId, updateTime);
			}

			map.put("success", Boolean.TRUE);
			map.put("message", "合并成功");
			return map;
		} catch (Exception e) {
			logger.error("合并视频作者出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改视频作者人气指数
	 * 
	 * @author zengxn
	 */
	@Override
	public int updatePopularityIndexById(String id, Long popularityIndex, String updateUserId, String updateTime) {
		try {
			UpEntity entity = new UpEntity(id);
			entity.setPopularityIndex(popularityIndex);
			entity.setUpdateUser(new UserEntity(updateUserId));
			if (StringUtils.isBlank(updateTime)) {
				entity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			} else {
				entity.setUpdateTime(updateTime);
			}
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("根据ID修改视频作者人气指数出错：", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public UpVo queryUpDetailByUpId(String id) {
		UpVo upVo = upMapper.findUpDetailById(id);
		Map<String, Object> photoParam = new HashMap<>();
		photoParam.put("upId", id);
		List<UpPhoto> photoList = upPhotoMapper.queryPhotoByUpIdAndSecondUpId(photoParam);
		if (upVo != null && !CollectionUtils.isEmpty(photoList)) {
			upVo.setMainPhoto(photoList.get(0).getPhoto());
			upVo.setUpPhotoList(photoList);
		}
		return upVo;
	}

	/**
	 * 根据人物名称id批量查询视频作者VO数据集合
	 * 
	 * @author zengxn
	 */
	@Override
	public List<VideoUpVo> queryListVideoUpVOBathchByUpNameId(List<String> upNameIds) {
		PageHelper.startPage(0, 0, "type");
		// 根据人物名称id集合查询出人物名称集合
		List<UpNameEntity> upNameEntityList = upNameService.queryListBatchById(upNameIds);
		if (upNameEntityList == null || upNameEntityList.isEmpty()) {
			return null;
		}
		Set<String> upIdsSet = new HashSet<String>();
		for (UpNameEntity upNameEntity : upNameEntityList) {
			upIdsSet.add(upNameEntity.getUp().getId());
		}
		List<String> upIds = new ArrayList<String>(upIdsSet.size());
		upIds.addAll(upIdsSet);
		// 根据去重后的人物id集合查询出人物集合
		List<UpEntity> upList = queryListBatchById(upIds);
		if (upList == null || upList.isEmpty()) {
			return null;
		}
		List<VideoUpVo> videoUpVoList = new ArrayList<VideoUpVo>(upList.size());
		// 根据各个人物id查询相应的站点数据
		for (UpEntity upEntity : upList) {
			List<UpStationEntity> upStationList = upStationService.queryListByUpId(upEntity.getId());
			List<UpValueEntity> upValueList = upValueService.queryListByUpId(upEntity.getId());
			VideoUpVo videoUpVo = new VideoUpVo(upEntity.getId());
			videoUpVo.setIntroduction(upEntity.getIntroduction());
			videoUpVo.setName(upEntity.getName());
			if (upStationList == null || upStationList.isEmpty()) {
				videoUpVo.setAvatar(upEntity.getAvatar());
			} else {
				List<VideoUpStationVo> videoUpStationVos = new ArrayList<VideoUpStationVo>();
				for (UpStationEntity upStationEntity : upStationList) {
					videoUpStationVos.add(new VideoUpStationVo(upStationEntity.getUpAvatar()));
				}
				videoUpVo.setUpStationList(videoUpStationVos);
			}
			if (upValueList != null && !upValueList.isEmpty()) {
				List<ValueVo> valueVos = new ArrayList<ValueVo>();
				for (UpValueEntity upValueEntity : upValueList) {
					valueVos.add(new ValueVo(upValueEntity.getValue().getId(), upValueEntity.getValue().getName()));
				}
				videoUpVo.setUpValueList(valueVos);
			}
			videoUpVoList.add(videoUpVo);
		}
		return videoUpVoList;
	}

}
