package com.demo.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class AdvisorTagHandler extends SimpleTagSupport{
	private String user;
	
	public void setUser(String user) {
		this.user= user;
	}
	
	public void doTag()throws JspException,IOException{
		getJspContext().getOut().write("Hello "+user+" <br>");
		getJspContext().getOut().write("Your advice is: "+getAdvice());
	}
	
	String getAdvice() {
		String[]adviceString = {
				"That color's not working for you.",
				"You should call in sick",
				"You might want to rethink that haircut"};
		
		int random = (int)(Math.random()*adviceString.length);
		return adviceString[random];	
	}
	

}
