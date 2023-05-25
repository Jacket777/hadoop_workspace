package com.demo.taglib;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class Menu extends TagSupport{
    private ArrayList items;
    
    public void addMenuItem(String item) {
    	items.add(item);
    }
       
       
	public int doStartTag() throws JspException{
		items = new ArrayList();
		return Tag.EVAL_BODY_INCLUDE;
   }
	
	
	public int doEndTag()throws JspException{
		try {
			pageContext.getOut().println("Menu items are: "+items);
		}catch(Exception ex) {
			throw new JspException("IOException--"+ex.toString());
		}
		return Tag.EVAL_PAGE;
	}

}