package com.demo.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class NestedLevelTag extends TagSupport{
	private int nestLevel = 0;
	
	public int doStartTag() throws JspException{
		nestLevel = 0;
		Tag parent = getParent();
		while(parent!=null) {
			parent = parent.getParent();
			nestLevel++;
		}
		
	try {
		pageContext.getOut().println("<br>Tag nested level: "+nestLevel);
	}catch(IOException ex) {
		throw new JspException("IOException--"+ex.toString());
	}	
    return EVAL_BODY_INCLUDE;//º∆À„ÃÂ
   }
}