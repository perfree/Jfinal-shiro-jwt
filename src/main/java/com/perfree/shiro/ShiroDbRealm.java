package com.perfree.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.perfree.jwt.JWTToken;
import com.perfree.jwt.JwtUtils;
import com.perfree.model.User;

public class ShiroDbRealm extends AuthorizingRealm{

	
	/**
	 * 重写shiro的token
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}

	/**
	 * 角色,权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//String username = JwtUtils.getUsername(principals.toString());
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//这里可以连接数据库根据用户账户进行查询用户角色权限等信息,为简便,直接set
		simpleAuthorizationInfo.addRole("admin");
		simpleAuthorizationInfo.addStringPermission("all");
		return simpleAuthorizationInfo;
	}
	
	/**
	 * 自定义认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		String token = (String) auth.getCredentials();
		 // 解密获得username，用于和数据库进行对比
        String userName = JwtUtils.getUsername(token);
        if (userName == null || userName == "") {
            throw new AuthenticationException("token 校验失败");
        }
		//根据解密的token得到用户名到数据库查询(为省事,直接设置)
        User user = new User();
        user.setName(userName);
        if(user.getName() == null) {
        	throw new AuthenticationException("用户不存在");
        }
        if(JwtUtils.verifyJwt(token, userName) == null) {
        	throw new AuthenticationException("用户名或者密码错误");
        }
		return new SimpleAuthenticationInfo(token, token, getName());
	}

}
