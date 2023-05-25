package com.etc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class PrintURLFilter1 implements Filter{
	@Override
	public void destroy() {

	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("doFilter方法调用前: PrintURLFilter1");
		chain.doFilter(request, response);
		System.out.println("doFilter方法调用后: PrintURLFilter1");
	}
	
	
	public void init(FilterConfig config)throws ServletException {
		System.out.println("start===="+config.getInitParameter("start"));
		System.out.println("end===="+config.getInitParameter("end"));
	}



}
