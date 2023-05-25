package com.hdfs.serial;

public class Test {
	public static void main(String[] args) throws Exception {
		Person person = new Person("zhangsan",27,"man");
		byte[]values = HadoopSerializationUtil.serialize(person);
		System.out.println(values);
		Person p = new Person();
		HadoopSerializationUtil.deserialize(p, values);
		System.out.println(p);
	}
}
