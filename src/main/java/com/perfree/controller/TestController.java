package com.perfree.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.jfinal.core.Controller;
import com.perfree.common.AjaxResult;
import com.perfree.jwt.JwtUtils;

/**
 * 测试Controller
 * @author Perfree
 */
public class TestController extends Controller{

	/**
	 * 首页
	 */
	public void index() {
		renderText("这是首页");
	}
	
	/**
	 * 登录页
	 */
	public void login() {
		renderText("请登录");
	}
	
	/**
	 * 登录操作
	 */
	public void doLogin() {
		try {
			String name = getPara("name");
			String password = getPara("password");
			if(name.equals("perfree") && password.equals("123456")) {
				Map<String,String> map = new HashMap<>();
				map.put("name", name);
				renderJson(new AjaxResult(AjaxResult.SUCCESS, JwtUtils.createJwt(map, new Date(System.currentTimeMillis()+360000))));
			}else {
				renderJson(new AjaxResult(AjaxResult.ERROR,"用户名或密码错误"));
			}
		} catch (Exception e) {
			renderJson(new AjaxResult(AjaxResult.FAILD,"系统异常"));
		}
	}
}
