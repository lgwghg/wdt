package com.webside.rights.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.exception.ServiceException;
import com.webside.rights.mapper.RoleMapper;
import com.webside.rights.model.RoleEntity;
import com.webside.rights.model.RoleResourceEntity;
import com.webside.rights.model.UserRoleEntity;
import com.webside.rights.service.RoleResourceService;
import com.webside.rights.service.RoleService;
import com.webside.rights.service.UserRoleService;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 角色服务实现类
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractService<RoleEntity, String> implements RoleService {

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(roleMapper);
	}

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleResourceService roleResourceService;

	@Autowired
	private UserRoleService userRoleService;

	@Override
	public int insert(RoleEntity entity) {
		try {
			entity.setId(IdGen.uuid());
			entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			return super.insert(entity);
		} catch (Exception e) {
			logger.error("新增角色出错：", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addRolePermBatch(String roleId, List<String> ids) {
		boolean flag = false;
		try {
			int permCount = roleResourceService.findCountByRoleId(roleId);
			boolean delFlag = true;
			if (permCount > 0) {
				int delResult = roleResourceService.deleteByRoleId(roleId);
				if (permCount != delResult) {
					delFlag = false;
				}
			}

			if (delFlag) {
				if (ids.size() > 0) {
					List<RoleResourceEntity> list = new ArrayList<RoleResourceEntity>();
					for (String resourceId : ids) {
						RoleResourceEntity roleResource = new RoleResourceEntity();
						roleResource.setId(IdGen.uuid());
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(resourceId);
						list.add(roleResource);
					}
					int addResult = roleResourceService.insertByBatch(list);
					if (addResult == ids.size()) {
						flag = true;
					}
				} else {
					flag = true;
				}
			}
			List<String> userIds = userRoleService.findUserIdByRoleId(roleId);
			ShiroAuthenticationManager.clearUserAuthByUserId(userIds);
			return flag;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean deleteRoleById(String id) {
		try {
			// 1、删除该角色的权限信息
			roleResourceService.deleteByRoleId(id);
			// 2、删除该角色
			if (roleMapper.deleteById(id) > 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据用户id查询用户角色
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public RoleEntity findByUserId(String userId) {
		UserRoleEntity userRole = userRoleService.findByUserId(userId);
		if(userRole!=null && StringUtils.isNotBlank(userRole.getRoleId())){
			return super.findById(userRole.getRoleId());
		}
		return null;
	}
}
