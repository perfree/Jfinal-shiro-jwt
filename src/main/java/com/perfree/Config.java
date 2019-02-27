package com.perfree;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import com.perfree.controller.TestController;
import com.perfree.shiro.ShiroInterceptor;

public class Config extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("config.properties");
		// 初始化读入config.properties，方便其他地方调用配置信息，省去了其他地方需要每次先use的步骤
		PropKit.use("config.properties");
		me.setDevMode(true);
		
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", TestController.class);
	}

	@Override
	public void configEngine(Engine me) {
		
	}

	@Override
	public void configPlugin(Plugins me) {
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		
	}
}
