package com.hive.demo01;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hive.ql.exec.UDF;

public class UDFDemo extends UDF{
	private SimpleDateFormat df;
	
	public UDFDemo() {
		df = new SimpleDateFormat("MM-dd-yyyy");	
	}
	
	public String evaluate(Date day) {
		return this.evaluate(day.getMonth()+1,day.getDate());	
	}
	
	public String evaluate(String day) {
		Date date = null;
		try {
			date = (Date)df.parse(day);
		}catch(Exception e) {
			return null;
		}
		return this.evaluate(date.getMonth()+1,date.getDate());	
	}
	
	public String evaluate(Integer month,Integer day) {
		if(month == 1) {
			if(day<20) {
				return "Ä¦ôÉ×ù";
			}else {
				return "Ë®Æ¿×ù";
			}
		}
		if(month ==2) {
			if(day<19) {
				return "Ë®Æ¿×ù";
			}else {
				return "Ë«Óã×ù";
			}	
		}
		
		if(month ==3) {
			if(day<21) {
				return "Ë«Óã×ù";
			}else {
				return "°×Ñò×ù";
			}	
		}
		
		if(month ==4) {
			if(day<20) {
				return "°×Ñò×ù";
			}else {
				return "½ðÅ£×ù";
			}	
		}
		
		if(month ==5) {
			if(day<21) {
				return "½ðÅ£×ù";
			}else {
				return "Ë«×Ó×ù";
			}	
		}
		
		if(month ==6) {
			if(day<22) {
				return "Ë«×Ó×ù";
			}else {
				return "¾ÞÐ·×ù";
			}	
		}
		
		if(month ==7) {
			if(day<23) {
				return "¾ÞÐ·×ù";
			}else {
				return "Ê¨×Ó×ù";
			}	
		}
		
		if(month ==8) {
			if(day<23) {
				return "Ê¨×Ó×ù";
			}else {
				return "´¦Å®×ù";
			}	
		}
		
		if(month ==9) {
			if(day<23) {
				return "´¦Å®×ù";
			}else {
				return "ÌìÆ½×ù";
			}	
		}
		
		if(month ==10) {
			if(day<24) {
				return "ÌìÆ½×ù";
			}else {
				return "ÌìÐ«×ù";
			}	
		}
		
		if(month ==11) {
			if(day<23) {
				return "ÌìÐ«×ù";
			}else {
				return "ÉäÊÖ×ù";
			}	
		}
		
		if(month ==12) {
			if(day<22) {
				return "ÉäÊÖ×ù";
			}else {
				return "Ä¦ôÉ×ù";
			}	
		}
		return null;	
	}	
}
