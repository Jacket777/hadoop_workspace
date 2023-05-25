package com.demo.bean;

public class Movie {
	private String name;
	private String genre;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Movie() {
		super();
	}
	
	public Movie(String name,String genre) {
		this.name = name;
		this.genre = genre;
	}

	

}
