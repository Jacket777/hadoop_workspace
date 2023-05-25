package com.demo.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;



public class SimpleTagTest6 extends SimpleTagSupport {
	public void doTag()throws JspException,IOException{
		getJspContext().getOut().print("Message from within doTag().<br>");
		getJspContext().getOut().print("About to throw a SkipPageException");
		boolean test = true;
		if(test) {
			throw new SkipPageException();
		}

	}

}
