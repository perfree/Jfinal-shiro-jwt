package com.perfree.shiro;

import java.lang.reflect.Method;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AnnotationsAuthorizingMethodInterceptor;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;

public class ShiroInterceptor extends AnnotationsAuthorizingMethodInterceptor implements Interceptor {
	 
    public ShiroInterceptor() {
        getMethodInterceptors();
    }
 
    public void intercept(final Invocation inv) {
        try {
            invoke(new MethodInvocation() {
                public Object proceed() throws Throwable {
                    inv.invoke();
                    return inv.getReturnValue();
                }
                public Method getMethod() {
                    return inv.getMethod();
                }
 
                public Object[] getArguments() {
                    return inv.getArgs();
                }
 
                public Object getThis() {
                    return inv.getController();
                }
            });
        } catch (Throwable e) {
            if (e instanceof AuthorizationException) {
                doProcessuUnauthorization(inv.getController());
            }
            LogKit.warn("权限错误:", e);
        }
    }
 
    /**
     * 未授权处理
     *
     * @param controller controller
     */
    private void doProcessuUnauthorization(Controller controller) {
        controller.redirect("/login");
    }
}
