/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.submit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.common.GlobalConstant;
import com.webside.dict.service.DictService;
import com.webside.exception.ServiceException;
import com.webside.submit.mapper.SubmitKeywordMapper;
import com.webside.submit.model.SubmitKeywordEntity;
import com.webside.submit.model.SubmitUrlEntity;
import com.webside.submit.service.SubmitKeywordService;
import com.webside.submit.service.SubmitUrlService;
import com.webside.user.service.UserService;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 提交搜索关键字服务实现类
 *
 * @author zengxn
 * @date 2017-06-13 17:49:37
 */
@Service("submitKeywordService")
public class SubmitKeywordServiceImpl extends AbstractService<SubmitKeywordEntity, String> implements SubmitKeywordService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(submitKeywordMapper);
	}

	/**
	 * 提交搜索关键字 DAO定义
	 */
	@Autowired
	private SubmitKeywordMapper submitKeywordMapper;

	/**
	 * 提交搜索关键字url Service定义
	 */
	@Autowired
	private SubmitUrlService submitUrlService;

	/**
	 * 用户 Service定义
	 */
	@Autowired
	private UserService userService;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 新增提交搜索关键字
	 * 
	 * @author zengxn
	 */
	@Override
	public int insert(SubmitKeywordEntity entity) {
		try {
			entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			entity.setId(IdGen.uuid());
			int result = super.insert(entity);
			if (result > 0) {
				if (entity.getSubmitUrlList() != null && !entity.getSubmitUrlList().isEmpty()) {
					for (SubmitUrlEntity submitUrlEntity : entity.getSubmitUrlList()) {
						submitUrlEntity.setSubmitKeyword(entity);
						submitUrlService.insert(submitUrlEntity);
					}
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("新增提交搜索关键字出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID修改提交搜索关键字
	 * 
	 * @author zengxn
	 */
	@Override
	public int updateById(SubmitKeywordEntity entity) {
		try {
			return super.updateById(entity);
		} catch (Exception e) {
			logger.error("修改提交搜索关键字出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 按条件查询提交搜索关键字
	 * 
	 * @author zengxn
	 */
	@Override
	public List<SubmitKeywordEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<SubmitKeywordEntity> list = super.queryListByPage(parameter);
			for (SubmitKeywordEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询视频出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID获取提交搜索关键字
	 * 
	 * @author zengxn
	 */
	@Override
	public SubmitKeywordEntity findById(String id) {
		SubmitKeywordEntity entity = super.findById(id);
		if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
			entity.setCreateUser(userService.findById(entity.getCreateUser().getId()));
		}
		return entity;
	}
}
