package com.demo.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class MenuItem extends TagSupport{
	private String  itemValue;
	
    public String getItemValue() {
		return itemValue;
	}


	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public int doStartTag() throws JspException{
		return Tag.EVAL_BODY_INCLUDE;
   }
	
	
	public int doEndTag()throws JspException{
		Menu parent =  (Menu)getParent();
		parent.addMenuItem(itemValue);
		return Tag.EVAL_PAGE;
	}

}