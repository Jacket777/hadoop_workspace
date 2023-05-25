package com.demo.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SimpleTagTest7 extends SimpleTagSupport {
	public void doTag()throws JspException,IOException{
		getJspContext().getOut().print("Before body");
		getJspBody().invoke(null);
		getJspContext().getOut().print("After body");
	}
}
