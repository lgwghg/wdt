package com.webside.shiro;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.profile.CommonProfile;

import com.webside.common.GlobalConstant;
import com.webside.exception.ForbiddenAccountException;
import com.webside.openid.exception.SteamUnbindExceptiom;
import com.webside.rights.mapper.ResourceMapper;
import com.webside.rights.model.ResourceEntity;
import com.webside.shiro.filter.ShiroUtils;
import com.webside.shiro.support.UsernamePasswordAndClientRealm;
import com.webside.shiro.support.UsernamePasswordAndClientToken;
import com.webside.shiro.util.MyByteSource;
import com.webside.user.model.UserEntity;
import com.webside.user.service.UserService;


/**
 * 
 * @ClassName: MyRealm
 * @Description: 自定义jdbcRealm,认证&授权
 * @author gaogang
 * @date 2016年7月12日 下午4:30:16
 *
 */
public class MyDBRealm extends UsernamePasswordAndClientRealm {

	@Inject
	private ResourceMapper resourceMapper;

	@Inject
	private UserService userService;
	
	private final Pattern STEAM_REGEX = Pattern.compile("(\\d+)");

	@Override
	protected AuthenticationInfo internalClientGetAuthenticationInfo(CommonProfile profile, Credentials credentials,
			AuthenticationToken authenticationToken) {
		final UsernamePasswordAndClientToken clientToken = (UsernamePasswordAndClientToken) authenticationToken;
		SimpleAuthenticationInfo authenticationInfo = null;
		String typeId = profile.getTypedId();
		UserEntity userParam = null;
		if ("SteamOpenIdClient".equalsIgnoreCase(credentials.getClientName())) {
			Matcher matcher = STEAM_REGEX.matcher(typeId);
			if (matcher.find()) {
				String steamKey = matcher.group(1);
				if (StringUtils.isNotBlank(steamKey)) {
					userParam = new UserEntity();
					userParam.setSteamKey(steamKey);
					clientToken.setUserId(steamKey);
				}
			}
		}
		if (userParam != null) {
			UserEntity userEntity = userService.login(userParam);
			// clientToken.setPrincipal(userEntity);
			if (userEntity == null) {
				if ("SteamOpenIdClient".equalsIgnoreCase(credentials.getClientName())) {
					throw new SteamUnbindExceptiom();
				}
			}
			authenticationInfo = new SimpleAuthenticationInfo(userEntity, credentials, getName());
		}
		return authenticationInfo;
	}
	
	/**
	 * 授权信息
	 * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		UserEntity user = ShiroAuthenticationManager.getUserEntity();
		if (user != null) {
			List<ResourceEntity> resourceList = resourceMapper.findResourcesByUserId(user.getId());
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			//根据用户ID查询角色（role），放入到Authorization里。
			// 单角色用户情况
			info.addRole(user.getRole().getKey());
			// 多角色用户情况
			// info.setRoles(user.getRolesName());
			// 用户的角色对应的所有权限
			for (ResourceEntity resourceEntity : resourceList) {
				info.addStringPermission(resourceEntity.getSourceKey().trim());
				if(StringUtils.isNotBlank(resourceEntity.getSourceUrl().trim()))
				{
					info.addStringPermission(resourceEntity.getSourceUrl().trim());
					//可以正则匹配,也可以根据规则匹配，针对/*/add.html或/*/edit.html进行扩展，追加表单页面的权限/*/addUI.html或/*/editUI.html，这里暂不使用这种方式，而是改为手动添加资源的方式，比较灵活
					/*
					if(resourceEntity.getSourceUrl().endsWith("/add.html"))
					{
						info.addStringPermission(resourceEntity.getSourceUrl().replace("/add.html", "/addUI.html"));
					}
					if(resourceEntity.getSourceUrl().endsWith("/edit.html"))
					{
						info.addStringPermission(resourceEntity.getSourceUrl().replace("/edit.html", "/editUI.html"));
					}
					*/
				}
			}
			//或者直接查询出所有权限set集合
			//info.setStringPermissions(permissions);
			return info;
		}
		return null;
	}

	/**
	 * 认证信息,认证回调函数,登录时调用
	 * </br>首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
	 * </br>如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
	 * </br>交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
	 * </br>如果不匹配将抛出密码错误异常IncorrectCredentialsException；
	 * </br>另外如果密码重试次数太多将抛出超出重试次数异常ExcessiveAttemptsException；
	 * </br>在组装SimpleAuthenticationInfo信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、加密盐（username+salt），
	 * </br>CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
	 */
	@Override
	protected AuthenticationInfo internalUsernamePasswordGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
		SimpleAuthenticationInfo authenticationInfo = null;
		String loginName = (String)token.getPrincipal();
		
		UserEntity userEntity = null;
		if (StringUtils.isNotBlank(loginName)) {
			if (loginName.indexOf("@") > -1) { // 邮箱登录
				userEntity = userService.findByEmail(loginName);
			} else { // 手机登录
				userEntity = userService.findByMobile(loginName);
			}
		}
		if (userEntity != null) {
			// 用户角色为禁止访问
			if (userEntity.getRole() != null && "forbidden".equals(userEntity.getRole().getKey())) {
				throw new ForbiddenAccountException(); // 帐号被禁止访问
			}
			if (StringUtils.isNotBlank(userEntity.getLocked().getValue()) && GlobalConstant.DICTTYPE_YES_NO_1.equals(userEntity.getLocked().getValue())) {
				throw new LockedAccountException(); // 帐号被锁定
			}
			// 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
			// 当用户执行登录时,在方法处理上要实现subject.login(token);
			// 然后会自动进入这个类进行认证
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得shiro自带的不好可以自定义实现
            authenticationInfo = new SimpleAuthenticationInfo(
            		userEntity, // 用户对象
            		userEntity.getPassword(), // 密码
            		//new MyByteSource(username + userEntity.getCredentialsSalt()),// salt=username+salt
					new MyByteSource(userEntity.getId() + userEntity.getCredentialsSalt()),// salt = userID + salt
					getName() // realm name
			);
            //设置session属性
            ShiroAuthenticationManager.setSessionAttribute(ShiroUtils.USERSESSION, userEntity);
            return authenticationInfo;
		} else {
			throw new UnknownAccountException();// 没找到帐号
		}

	}
	
	/**
     * 清除当前用户权限信息
     */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
	/**
     * 清除当前用户认证信息
     */
	public void clearCachedAuthenticationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthenticationInfo(principals);
	}
	
	/**
	 * 清除指定 principalCollection 的权限信息
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	
	/**
     * 清除用户认证信息
     */
	public void clearCachedAuthenticationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthenticationInfo(principals);
	}


	/**
	 * 清除当前用户的认证和授权缓存信息
	 */
	public void clearAllCache() {
		clearCachedAuthorizationInfo();
		clearCachedAuthenticationInfo();
	}

}