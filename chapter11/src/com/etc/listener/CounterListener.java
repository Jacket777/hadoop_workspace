package com.etc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.etc.service.CounterService;

public class CounterListener implements ServletContextListener {
	// 关闭容器时，上下文销毁时，调用下面的方法
	public void contextDestroyed(ServletContextEvent event) {
		        //contextDestroyed
		System.out.println("=====close=====");
		ServletContext context = event.getServletContext();
		Integer number = (Integer) context.getAttribute("count");
		CounterService cs = new CounterService();
		cs.save(number);
	}

	// 启动容器
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("=====open=====");
		CounterService cs = new CounterService();
		Integer number = cs.getNumber();
		ServletContext context = event.getServletContext();
		context.setAttribute("count", number);
	}

}
