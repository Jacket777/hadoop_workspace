package com.mapreduce.partiontemperature;

import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableComparable;

public class MyGroup extends WritableComparator{
	public MyGroup() {
		super(MyKey.class,true);
	}
	
	@Override
	public int compare(WritableComparable a,WritableComparable b) {
		MyKey mykey1 = (MyKey)a;
		MyKey mykey2 = (MyKey)b;
		int r1 = Integer.compare(mykey1.getYear(), mykey2.getYear());
		if(r1 ==0) {
			int r2 = Integer.compare(mykey1.getMonth(), mykey2.getMonth());
			return r2;
		}
		return r1;
	}
	
}