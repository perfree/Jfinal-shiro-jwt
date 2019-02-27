package com.perfree.jwt;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

/**
 * 自定义Shiro的过滤器
 * @author Perfree
 *
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

	 /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含authc字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("authc");
        return authorization != null;
    }

    /**
     * 如果携带token进行登录
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("authc");

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    	HttpServletResponse resp = (HttpServletResponse)response;
    	Boolean flag = true;
    	//判断用户是否携带了token
        if (isLoginAttempt(request, response)) {
            try {
				executeLogin(request, response);
			} catch (Exception e) {
				flag = false;
			}
            if(!flag) {
            	try {
					resp.sendRedirect("/login");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            return flag;
        }else {
        	//未携带token,重定向至登录页面
			try {
				resp.sendRedirect("/login");
			} catch (IOException e1) {
			}
        	return false;
        }
        
    }
}
