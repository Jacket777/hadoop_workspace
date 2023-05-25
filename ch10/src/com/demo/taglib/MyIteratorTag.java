package com.demo.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class MyIteratorTag extends TagSupport{
	String[]movies = new String[] {"Spiderman","Saved!","Amelie"};
	int movieCounter;
	
	public int doStartTag() throws JspException{
		movieCounter = 0;
		pageContext.setAttribute("movie", movies[movieCounter]);
		movieCounter++;
		return Tag.EVAL_BODY_INCLUDE;
   }
	
	
	public int doAfterBody()throws JspException{
		if(movieCounter<movies.length) {
			pageContext.setAttribute("movie", movies[movieCounter]);
			movieCounter++;
			return EVAL_BODY_AGAIN;
		}else {
			return Tag.SKIP_BODY;
		}
	}
	
	public int doEndTag()throws JspException{
		return Tag.EVAL_PAGE;
	}

}