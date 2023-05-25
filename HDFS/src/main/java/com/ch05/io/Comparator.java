package com.ch05.io;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;

public class Comparator extends WritableComparator{
	private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
	
	static {
		WritableComparator.define(TextPair.class, new Comparator());
	}
	
	public Comparator() {
		super(TextPair.class);
	}
	
//	@Override
//	public int compare(byte[]b1,int s1,int l1,
//			byte[]b2,int s2,int l2) {
//		
//	}
	
	

}
