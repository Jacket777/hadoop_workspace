package com.demo.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Classic1 extends TagSupport{
	public int doStartTag() throws JspException{
		JspWriter out = pageContext.getOut();
		//传统标记从TagSupport继承了一个pageContext成员变量
		//SimpleTag要使用getJspContext()方法
		try {
		out.println("classic tag output");
	}catch(IOException ex) {
		throw new JspException("IOException--"+ex.toString());
	}
    return SKIP_BODY;
   }
}
