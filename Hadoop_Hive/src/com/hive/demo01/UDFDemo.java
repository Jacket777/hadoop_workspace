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
				return "Ħ����";
			}else {
				return "ˮƿ��";
			}
		}
		if(month ==2) {
			if(day<19) {
				return "ˮƿ��";
			}else {
				return "˫����";
			}	
		}
		
		if(month ==3) {
			if(day<21) {
				return "˫����";
			}else {
				return "������";
			}	
		}
		
		if(month ==4) {
			if(day<20) {
				return "������";
			}else {
				return "��ţ��";
			}	
		}
		
		if(month ==5) {
			if(day<21) {
				return "��ţ��";
			}else {
				return "˫����";
			}	
		}
		
		if(month ==6) {
			if(day<22) {
				return "˫����";
			}else {
				return "��з��";
			}	
		}
		
		if(month ==7) {
			if(day<23) {
				return "��з��";
			}else {
				return "ʨ����";
			}	
		}
		
		if(month ==8) {
			if(day<23) {
				return "ʨ����";
			}else {
				return "��Ů��";
			}	
		}
		
		if(month ==9) {
			if(day<23) {
				return "��Ů��";
			}else {
				return "��ƽ��";
			}	
		}
		
		if(month ==10) {
			if(day<24) {
				return "��ƽ��";
			}else {
				return "��Ы��";
			}	
		}
		
		if(month ==11) {
			if(day<23) {
				return "��Ы��";
			}else {
				return "������";
			}	
		}
		
		if(month ==12) {
			if(day<22) {
				return "������";
			}else {
				return "Ħ����";
			}	
		}
		return null;	
	}	
}
