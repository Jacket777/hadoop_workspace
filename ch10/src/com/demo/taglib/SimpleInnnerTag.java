package com.demo.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SimpleInnnerTag extends SimpleTagSupport {
	public void doTag()throws JspException,IOException{
		MyClassicParent parent = (MyClassicParent)getParent();
		getJspContext().getOut().print("Parent attribute is : "+parent.getName());
	}

}
