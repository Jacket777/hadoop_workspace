package com.etc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class PrintURLFilter3 implements Filter{
	@Override
	public void destroy() {

	}
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("doFilter方法调用前: PrintURLFilter3"
			+((HttpServletRequest)request).getRequestURL());
		//chain.doFilter(request, response);
		System.out.println("doFilter方法调用后: PrintURLFilter3");	
	}
	
	
	public void init(FilterConfig arg0)throws ServletException {
		
	}



}
