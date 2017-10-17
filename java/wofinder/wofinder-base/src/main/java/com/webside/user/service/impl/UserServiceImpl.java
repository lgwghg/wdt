package com.webside.user.service.impl;

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
import com.webside.exception.ServiceException;
import com.webside.integral.model.UserIntegral;
import com.webside.integral.service.IUserIntegralService;
import com.webside.rights.model.RoleEntity;
import com.webside.rights.model.UserRoleEntity;
import com.webside.rights.service.RoleService;
import com.webside.rights.service.UserRoleService;
import com.webside.user.mapper.UserMapper;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserIncrementEntity;
import com.webside.user.model.UserPhoto;
import com.webside.user.service.UserCacheService;
import com.webside.user.service.UserIncrementService;
import com.webside.user.service.UserService;
import com.webside.util.EmailUtil;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;

/**
 * 用户服务实现类
 *
 * @author zengxn
 * @date 2017-04-20 19:00:41
 */
@Service("userService")
public class UserServiceImpl extends AbstractService<UserEntity, String> implements UserService {
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(userMapper);
	}

	/**
	 * 用户 DAO定义
	 */
	@Autowired
	private UserMapper userMapper;

	/**
	 * 字典管理 Service定义
	 */
	@Autowired
	private DictService dictService;

	/**
	 * 角色 Service定义
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 用户角色映射 Service定义
	 */
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * 用户增量 Service定义
	 */
	@Autowired
	private UserIncrementService userIncrementService;

	/**
	 * 用户缓存 Service定义
	 */
	@Autowired
	private UserCacheService userCacheService;

	/**
	 * 邮箱工具类定义
	 */
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private IUserIntegralService userIntegralService;
	/**
	 * 按条件查询用户
	 * 
	 * @param parameter
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public List<UserEntity> queryListByPage(Map<String, Object> parameter) {
		try {
			List<UserEntity> list = super.queryListByPage(parameter);
			for (UserEntity entity : list) {
				if (entity.getCreateUser() != null && StringUtils.isNotBlank(entity.getCreateUser().getId())) {
					entity.setCreateUser(userCacheService.getUserEntityByUserId(entity.getCreateUser().getId()));
				}
				if (entity.getUpdateUser() != null && StringUtils.isNotBlank(entity.getUpdateUser().getId())) {
					entity.setUpdateUser(userCacheService.getUserEntityByUserId(entity.getUpdateUser().getId()));
				}
				if (entity.getStatus() != null && StringUtils.isNotBlank(entity.getStatus().getValue())) {
					entity.setStatus(dictService.findByTypeValue(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS, String.valueOf(entity.getStatus().getValue())));
				}
				if (entity.getLocked() != null && StringUtils.isNotBlank(entity.getLocked().getValue())) {
					entity.setLocked(dictService.findByTypeValue(GlobalConstant.DICTTYPE_YES_NO, String.valueOf(entity.getLocked().getValue())));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("按条件查询用户列表出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 新增用户
	 * 
	 * @param UserEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	public int insert(UserEntity entity) {
		try {
			// 默认头像
			entity.setPhoto(GlobalConstant.DEFAULT_PHOTO_URL);
			entity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
			entity.setAccountUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			int result = super.insert(entity);
			if (result == 1) {
				// 用户增量数据
				UserIncrementEntity userIncrement = new UserIncrementEntity();
				userIncrement.setUser(entity);
				userIncrement.setCreateUser(entity.getCreateUser());
				userIncrement.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
				userIncrement.setIntegral(5);//注册积分10
				userIncrementService.insert(userIncrement);
				entity.setUserIncrement(userIncrement);
				// 增加用户缓存信息
				userCacheService.updateUserToRedis(entity);
			}
			return result;
		} catch (Exception e) {
			logger.error("新增用户出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改用户
	 * 
	 * @param UserEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	public int updateById(UserEntity userEntity, boolean isUpdateRedis) {
		try {
			return update(userEntity, isUpdateRedis, true);
		} catch (Exception e) {
			logger.error("修改用户出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改用户，底层,多一个参数是否需要修改用户角色相关操作
	 * 
	 * @param UserEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	private int update(UserEntity userEntity, boolean isUpdateRedis, boolean updateRole) {
		try {
			userEntity.setUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			if (StringUtils.isNotBlank(userEntity.getMobile())) {
				userEntity.setAccountUpdateTime(StringUtils.toString(System.currentTimeMillis()));
			}
			int result = super.updateById(userEntity);
			if (result == 1) {
				if (updateRole) {
					// 查询用户角色是否存在
					UserRoleEntity userRole = userRoleService.findByUserId(userEntity.getId());
					// 用户角色不为空
					if (userEntity.getRole() != null && StringUtils.isNotBlank(userEntity.getRole().getId())) {
						// 本身有角色
						if (userRole != null) {
							// 角色不同
							if (!userEntity.getRole().getId().equals(userRole.getRoleId())) {
								userRole.setRoleId(userEntity.getRole().getId());
								userRoleService.updateById(userRole);
							}
							// 本身无角色
						} else {
							userRole = new UserRoleEntity();
							userRole.setRoleId(userEntity.getRole().getId());
							userRole.setUserId(userEntity.getId());
							userRoleService.insert(userRole);
						}
						// 用户角色为空
					} else {
						// 本身有角色，需删除
						if (userRole != null) {
							userRoleService.deleteById(userRole.getId());
						}
					}
				}
				try {
					// 积分
					Integer integralType = null;
					String integralSource = "";
					if (StringUtils.isNotBlank(userEntity.getPhoto())) {
						integralType = GlobalConstant.INTEGRAL_TYPE_SETPHOTO_1;
						integralSource = "首次上传头像";
					}
					if (StringUtils.isNotBlank(userEntity.getNickName())) {
						integralType = GlobalConstant.INTEGRAL_TYPE_SETNICK_2;
						integralSource = "首次设置昵称";
					}
					if (integralType != null) {
						String userId = userEntity.getId();
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
					logger.error("设置积分", e);
				}
				if (isUpdateRedis) {
					// 更新缓存
					userCacheService.updateUserToRedis(userEntity);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("修改用户出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新用户表数据（数据库+缓存）
	 * 
	 * @param userEntity
	 * @throws ServiceException
	 * @author zengxn
	 */
	public int updateUserOnly(UserEntity userEntity, boolean isUpdateRedis) {
		try {
			return update(userEntity, isUpdateRedis, false);
		} catch (Exception e) {
			logger.error("更新用户表数据（数据库+缓存）出错：", e);
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 取消第三方绑定
	 * 
	 * @param userId
	 * @param thirdType
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int updateCancelBind(String userId, String thirdType) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			paramMap.put("thirdType", thirdType);
			paramMap.put("updateTime", StringUtils.toString(System.currentTimeMillis()));
			return userMapper.updateCancelBind(paramMap);
		} catch (Exception e) {
			logger.error("取消第三方绑定出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 修改密码，并且发送邮箱通知
	 * 
	 * @param userEntity
	 * @param password
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public int updatePassword(UserEntity userEntity, String password) {
		try {
			int result = updateUserOnly(userEntity,false);
			if (StringUtils.isNotBlank(userEntity.getEmail())) {
				emailUtil.send126Mail(userEntity.getEmail(), "G菠菜", "系统密码重置", "您好，您的密码已重置，新密码是:" + password);
			}
			return result;
		} catch (Exception e) {
			logger.error("修改密码，并且发送邮箱通知出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID删除用户 重写用户删除逻辑： 1、删除用户和角色的对应关系 2、删除用户
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	public int deleteById(String id) {
		try {
			userRoleService.deleteByUserId(id);
			return super.deleteById(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据手机获取用戶信息
	 * 
	 * @param mobile
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public UserEntity findByMobile(String mobile) {
		try {
			UserEntity entity = new UserEntity();
			entity.setMobile(mobile);
			List<UserEntity> list = userMapper.findByEntity(entity);
			if (list != null && list.size() > 0) {
				UserEntity user = new UserEntity();
				user = list.get(0);
				RoleEntity role = roleService.findByUserId(user.getId());
				if (role != null) {
					user.setRole(role);
				}
				return user;
			}
			return null;
		} catch (Exception e) {
			logger.error("根据手机获取用戶信息出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据邮箱获取用户信息
	 * 
	 * @param email
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public UserEntity findByEmail(String email) {
		try {
			UserEntity entity = new UserEntity();
			entity.setEmail(email);
			List<UserEntity> list = userMapper.findByEntity(entity);
			if (list != null && list.size() > 0) {
				UserEntity user = new UserEntity();
				user = list.get(0);
				RoleEntity role = roleService.findByUserId(user.getId());
				if (role != null) {
					user.setRole(role);
				}
				return user;
			}
			return null;
		} catch (Exception e) {
			logger.error("根据邮箱获取用户信息出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据用户昵称获取用户
	 * 
	 * @param nickName
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public UserEntity findByNickName(String nickName) {
		try {
			UserEntity entity = new UserEntity();
			entity.setNickName(nickName);
			List<UserEntity> list = userMapper.findByEntity(entity);
			if (list != null && list.size() > 0) {
				UserEntity user = new UserEntity();
				user = list.get(0);
				RoleEntity role = roleService.findByUserId(user.getId());
				if (role != null) {
					user.setRole(role);
				}
				return user;
			}
			return null;
		} catch (Exception e) {
			logger.error("根据邮箱获取用户信息出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据ID获取用户
	 * 
	 * @param ID
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public UserEntity findById(String id) {
		try {
			UserEntity user = super.findById(id);
			if (user != null) {
				RoleEntity role = roleService.findByUserId(user.getId());
				if (role != null) {
					user.setRole(role);
				}
			}
			return user;
		} catch (Exception e) {
			logger.error("根据ID获取用户出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 根据角色id 查询出该角色下的所有用户信息
	 * 
	 * @param roleId
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public List<UserEntity> findRoleUserByRoleId(String roleId) {
		try {
			List<String> userIds = userRoleService.findUserIdByRoleId(roleId);
			List<UserEntity> list = new ArrayList<UserEntity>();
			UserEntity entity = null;
			for (String id : userIds) {
				entity = userMapper.findById(id);
				list.add(entity);
			}
			return list;
		} catch (Exception e) {
			logger.error("根据角色id 查询出该角色下的所有用户信息出错：", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 该方法只用来登录获取用户信息
	 * 
	 * @param entity
	 * @throws ServiceException
	 * @author zengxn
	 */
	@Override
	public UserEntity login(UserEntity entity) {
		try {
			List<UserEntity> list = userMapper.findByEntity(entity);
			if (list != null && list.size() > 0) {
				UserEntity user = new UserEntity();
				user = list.get(0);
				RoleEntity role = roleService.findByUserId(user.getId());
				if (role != null) {
					user.setRole(role);
				}
				return user;
			}
			return null;
		} catch (Exception e) {
			logger.error("该方法只用来登录获取用户信息出错：", e);
			throw new ServiceException(e);
		}
	}

	@Override
	public int insertUserPhoto(UserPhoto photo) {
		return userMapper.insertUserPhoto(photo);
	}

	@Override
	public UserEntity findUserAndIncrementById(String userId) {
		return userMapper.findUserAndIncrementById(userId);
	}
}
