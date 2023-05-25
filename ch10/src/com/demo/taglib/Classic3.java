package com.demo.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class Classic3 extends TagSupport{
	JspWriter out;
	public int doStartTag() throws JspException{
	out = pageContext.getOut();
	try {
		out.println("Before body");
	}catch(IOException ex) {
		throw new JspException("IOException--"+ex.toString());
	}	
    return EVAL_BODY_INCLUDE;//º∆À„ÃÂ
   }
	
	public int doEndTag()throws JspException{
	try {
		out.println("After body");
	}catch(IOException ex) {
		throw new JspException("IOException--"+ex.toString());
	}
		return Tag.EVAL_PAGE;
	}
}