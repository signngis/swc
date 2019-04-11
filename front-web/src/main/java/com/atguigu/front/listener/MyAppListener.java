package com.atguigu.front.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 配置监听器
 * @author lfy
 *
 */
public class MyAppListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 项目启动
		ServletContext servletContext = sce.getServletContext();
		
		//项目名以/开始但不以/结束
		String path = servletContext.getContextPath();
		servletContext.setAttribute("ctp", path);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
