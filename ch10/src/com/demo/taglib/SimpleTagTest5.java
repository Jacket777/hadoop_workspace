package com.demo.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.demo.bean.Movie;


public class SimpleTagTest5 extends SimpleTagSupport {
	private List<Movie> movieList;
	
	public void setMovieList(List<Movie> movieList) {
		this.movieList = movieList;
	}
	
	
	public void doTag()throws JspException,IOException{
		Iterator<Movie> i =  movieList.iterator();
		while(i.hasNext()) {
			Movie movie = (Movie)i.next();
			getJspContext().setAttribute("movie", movie);
			getJspBody().invoke(null);
		}
	}

}
