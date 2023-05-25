package com.demo.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author 17081290
 *
 */
public class MyClassicParent extends TagSupport{
	private String name;

	public String getName() {return name;}
		
	public void setName(String name) {this.name = name;}
		
	public int doStartTag() throws JspException{

		return Tag.EVAL_BODY_INCLUDE;
	}
}