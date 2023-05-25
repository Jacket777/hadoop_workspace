package com.ch05.io;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.StringUtils;



import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

public class TestWritable {

	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		IntWritable w1 = new IntWritable(163);
		IntWritable w2 = new IntWritable(63);
		
		RawComparator<IntWritable>comparator = WritableComparator.get(IntWritable.class);
		int result = comparator.compare(w1, w2);
		System.out.println(result);
		byte[] bytes = serialize(w1);
		System.out.println("the byte is "+bytes.length);
		IntWritable neWritable = new IntWritable();
		
		deserialize(neWritable, bytes);
		System.out.println(neWritable.get());
		
		byte[]data = serialize(new VIntWritable(163));
		System.out.println(StringUtils.byteToHexString(data));
		
		
		Text text = new Text("hadoop");
		System.out.println("the text length "+text.getLength());
		System.out.println("the text bytes length "+text.getBytes().length);
		System.out.println("the text charAt(2) "+text.charAt(2));
		System.out.println("find a substring in "+text.find("do"));
		System.out.println("find first 'o' in "+text.find("o"));
		System.out.println("find first 'o' in positioin 4 or later "+text.find("o",4));
		System.out.println("find the no match string "+text.find("pig"));
		
		 
	}
	
	
	public static byte[]serialize(Writable writable)throws IOException{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		DataOutputStream dataOut= new DataOutputStream(out);
		writable.write(dataOut);
		dataOut.close();
		return out.toByteArray();
	}
	
	
	
	public static byte[]deserialize(Writable writable,byte[]bytes)throws IOException{
		ByteArrayInputStream in= new ByteArrayInputStream(bytes);
		DataInputStream dataIn= new DataInputStream(in);
		writable.readFields(dataIn);
		dataIn.close();
		return bytes;
	}

}