package com.demo.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.demo.bean.*;
import com.sun.javafx.collections.MappingChange.Map;

import java.util.ArrayList;
import java.util.HashMap;




public class TestServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//ÇëÇó²ÎÊý
//		String name = request.getParameter("userName");
//		request.setAttribute("name", name);
		
//		Person person = new Person();
//		person.setName("Jack");
//		
//		request.setAttribute("person", person);
		
		People people = new People();
		people.setName("Tim");
		Dog dog = new Dog();
		dog.setName("Spike");
		people.setDog(dog);
		request.setAttribute("people", people);
		
		String []favoriteMusic = {"Zero 7","Tahiti 80","BT","Frou Frou"};
		request.setAttribute("musicList", favoriteMusic);
		
		ArrayList favoriteFood = new ArrayList();
		favoriteFood.add("chai ice cream");
		favoriteFood.add("thai pizza");
		favoriteFood.add("anything in dark chocolate");
		request.setAttribute("favoriteFood", favoriteFood);
		
		HashMap musicMap = new HashMap();
		musicMap.put("Ambient", "Zero 7");
		musicMap.put("Surf", "Tahiti 80");
		musicMap.put("DJ", "BT");
		musicMap.put("Indie", "Travis");
		request.setAttribute("musicMap", musicMap);
		request.setAttribute("Genre", "Ambient");
		
		String[]musicTypes = {"Ambient","Surf","DJ","Indie"};
		request.setAttribute("MusicType", musicTypes);
		
		
		String num = "2";
		request.setAttribute("num", num);
		Integer i= new Integer(3);
		request.setAttribute("integer", i);
		ArrayList list = new ArrayList();
		list.add("true");
		list.add("2");
		list.add("10");
		request.setAttribute("list", list);
		
	
		
		
	
		request.getRequestDispatcher("/result.jsp").forward(request,
					response);		
	}

}