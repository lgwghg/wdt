/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.up.service.impl;

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
import com.webside.up.service.IUpSecondLevelService;
import com.webside.up.mapper.IUpPhotoMapper;
import com.webside.up.mapper.IUpSecondLevelMapper;
import com.webside.up.model.UpPhoto;
import com.webside.up.model.UpSecondLevel;

/**
 * 人物二级信息服务实现类
 *
 * @author tianguifang
 * @date 2017-06-06 11:44:01
 */
@Service("upSecondLevelService")
public class UpSecondLevelService extends AbstractService<UpSecondLevel, String> implements IUpSecondLevelService 
{
	//这句必须要加上。不然会报空指针异常，因为在实际调用的时候不是BaseMapper调用，而是具体的mapper，这里为userMapper
	@Autowired
	public void setBaseMapper() 
	{
		super.setBaseMapper(upSecondLevelMapper);
	}

	/**
	 * 人物二级信息 DAO定义
	 */
	@Autowired
	private IUpSecondLevelMapper upSecondLevelMapper;
	@Autowired
	private DictService dictService;
	@Autowired
	private IUpPhotoMapper upPhotoMapper;
	@Override
	public List<UpSecondLevel> queryListByPage(Map<String, Object> parameter) {
		List<UpSecondLevel> levelList = upSecondLevelMapper.queryListByPage(parameter);
		List<DictEntity> dictList = dictService.queryListByType(GlobalConstant.UP_SECOND_TITLE_TYPE);
		Map<String, String> dictMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(dictList)) {
			for (DictEntity dict : dictList) {
				dictMap.put(dict.getValue(), dict.getLabel());
			}
		}
		for (UpSecondLevel level : levelList) {
			level.setTitle(dictMap.get(level.getTitleType().toString()));
			/*Map<String, Object> photoParam = new HashMap<>();
			photoParam.put("upId", level.getUpId());
			photoParam.put("upSecondId", level.getId());
			List<UpPhoto> photoList = upPhotoMapper.queryPhotoByUpIdAndSecondUpId(photoParam);
			if (!CollectionUtils.isEmpty(photoList)) {
				level.setMainPhoto(photoList.get(0).getPhoto());
				level.setUpPhotoList(photoList);
			}*/
		}
		return levelList;
	}
	@Override
	public List<UpSecondLevel> queryUpSecondLevelListByUpId(String upId) {
		List<UpSecondLevel> levelList = upSecondLevelMapper.queryUpSecondLevelByUpId(upId);
		List<DictEntity> dictList = dictService.queryListByType(GlobalConstant.UP_SECOND_TITLE_TYPE);
		Map<String, String> dictMap = new HashMap<>();
		if (!CollectionUtils.isEmpty(dictList)) {
			for (DictEntity dict : dictList) {
				dictMap.put(dict.getValue(), dict.getLabel());
			}
		}
		for (UpSecondLevel level : levelList) {
			level.setTitle(dictMap.get(level.getTitleType().toString()));
			Map<String, Object> photoParam = new HashMap<>();
			photoParam.put("upId", level.getUpId());
			photoParam.put("upSecondId", level.getId());
			List<UpPhoto> photoList = upPhotoMapper.queryPhotoByUpIdAndSecondUpId(photoParam);
			if (!CollectionUtils.isEmpty(photoList)) {
				level.setMainPhoto(photoList.get(0).getPhoto());
				level.setUpPhotoList(photoList);
			}
		}
		return levelList;
	}
}
