package com.demo.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SimpleTagTest1 extends SimpleTagSupport {
	public void doTag()throws JspException,IOException{
		getJspContext().getOut().println("This is the lamest use of a custom tag");
	}

}
