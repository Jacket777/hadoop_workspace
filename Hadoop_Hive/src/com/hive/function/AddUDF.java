package com.hive.function;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.Description;

/*
 * 1.指定加法函数
 */
//@Description(name ='myadd',
//value='add(int a,int b)==>return a+b',
//extended= "Example:\n"
//+"add(1,1)"
//		
//		)

public class AddUDF extends UDF{
	
	public int evaluate(int a,int b) {
		return a+b;
	}
	
	public int evaluate(int a,int b,int c) {
		return a+b+c;
	}
    
}