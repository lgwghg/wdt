/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.user.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.webside.dict.model.DictEntity;
import com.webside.rights.model.RoleEntity;

/**
 * 用户实体类
 *
 * @author zengxn
 * @date 2017-05-04 10:13:48
 */
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private String id;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 是否锁定：0：正常；1：锁定
	 */
	private DictEntity locked;

	/**
	 * 加密盐
	 */
	private String credentialsSalt;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 头像
	 */
	private String photo;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * steam KEY
	 */
	private String steamKey;

	/**
	 * steam 账号昵称
	 */
	private String steamNick;

	/**
	 * qq KEY
	 */
	private String qqKey;

	/**
	 * qq账号昵称
	 */
	private String qqNick;

	/**
	 * 微信 KEY
	 */
	private String wechatKey;

	/**
	 * 微信 账号昵称
	 */
	private String wechatNick;

	/**
	 * 微博 KEY
	 */
	private String weiboKey;

	/**
	 * 微博 账号昵称
	 */
	private String weiboNick;

	/**
	 * 状态
	 */
	private DictEntity status;

	/**
	 * 创建者
	 */
	private UserEntity createUser;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改者
	 */
	private UserEntity updateUser;

	/**
	 * 修改时间
	 */
	private String updateTime;
	
	private String accountUpdateTime;
	/**
	 * 所属角色
	 */
	private RoleEntity role;

	/**
	 * 个人增量信息
	 */
	private UserIncrementEntity userIncrement;

	/**
	 * 新密码
	 */
	private String newPassword;

	public UserEntity() {

	}

	public UserEntity(String id) {
		this.id = id;
	}

	public UserEntity(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.password = userEntity.getPassword();
		this.credentialsSalt = userEntity.getCredentialsSalt();
		this.createUser = userEntity.getCreateUser();
		this.createTime = userEntity.getCreateTime();
		this.updateTime = userEntity.getUpdateTime();
		this.role = userEntity.getRole();
		this.userIncrement = userEntity.getUserIncrement();
	}

	public String getAccountUpdateTime() {
		return accountUpdateTime;
	}

	public void setAccountUpdateTime(String accountUpdateTime) {
		this.accountUpdateTime = accountUpdateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DictEntity getLocked() {
		return locked;
	}

	public void setLocked(DictEntity locked) {
		this.locked = locked;
	}

	public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSteamKey() {
		return steamKey;
	}

	public void setSteamKey(String steamKey) {
		this.steamKey = steamKey;
	}

	public String getSteamNick() {
		return steamNick;
	}

	public void setSteamNick(String steamNick) {
		this.steamNick = steamNick;
	}

	public String getQqKey() {
		return qqKey;
	}

	public void setQqKey(String qqKey) {
		this.qqKey = qqKey;
	}

	public String getQqNick() {
		return qqNick;
	}

	public void setQqNick(String qqNick) {
		this.qqNick = qqNick;
	}

	public String getWechatKey() {
		return wechatKey;
	}

	public void setWechatKey(String wechatKey) {
		this.wechatKey = wechatKey;
	}

	public String getWechatNick() {
		return wechatNick;
	}

	public void setWechatNick(String wechatNick) {
		this.wechatNick = wechatNick;
	}

	public String getWeiboKey() {
		return weiboKey;
	}

	public void setWeiboKey(String weiboKey) {
		this.weiboKey = weiboKey;
	}

	public String getWeiboNick() {
		return weiboNick;
	}

	public void setWeiboNick(String weiboNick) {
		this.weiboNick = weiboNick;
	}

	public DictEntity getStatus() {
		return status;
	}

	public void setStatus(DictEntity status) {
		this.status = status;
	}

	public UserEntity getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserEntity createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public UserEntity getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(UserEntity updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public UserIncrementEntity getUserIncrement() {
		return userIncrement;
	}

	public void setUserIncrement(UserIncrementEntity userIncrement) {
		this.userIncrement = userIncrement;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPhoto_24() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(getPhoto())) {
			if (getPhoto().lastIndexOf(".") < 0)
				return null;
			String name = getPhoto().substring(0, getPhoto().lastIndexOf("."));
			String fix = getPhoto().substring(getPhoto().lastIndexOf(".") + 1, getPhoto().length());
			newPhoto = name + "_24." + fix;
		}
		return newPhoto;
	}

	public String getPhoto_40() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(getPhoto())) {
			if (getPhoto().lastIndexOf(".") < 0)
				return null;
			String name = getPhoto().substring(0, getPhoto().lastIndexOf("."));
			String fix = getPhoto().substring(getPhoto().lastIndexOf(".") + 1, getPhoto().length());
			newPhoto = name + "_40." + fix;
		}
		return newPhoto;
	}

	public String getPhoto_70() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(getPhoto())) {
			if (getPhoto().lastIndexOf(".") < 0)
				return null;
			String name = getPhoto().substring(0, getPhoto().lastIndexOf("."));
			String fix = getPhoto().substring(getPhoto().lastIndexOf(".") + 1, getPhoto().length());
			newPhoto = name + "_70." + fix;
		}
		return newPhoto;
	}

	public String getPhoto_133() {
		String newPhoto = "";
		if (StringUtils.isNotBlank(getPhoto())) {
			if (getPhoto().lastIndexOf(".") < 0)
				return null;
			String name = getPhoto().substring(0, getPhoto().lastIndexOf("."));
			String fix = getPhoto().substring(getPhoto().lastIndexOf(".") + 1, getPhoto().length());
			newPhoto = name + "_133." + fix;
		}
		return newPhoto;
	}
}
