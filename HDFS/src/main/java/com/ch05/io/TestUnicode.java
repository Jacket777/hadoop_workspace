package com.ch05.io;

public class TestUnicode {

	public static void main(String[] args) {
		String s="\u0041\u00DF\\u6771\uD801\uDC00";
		System.out.println("======="+unicodeToString(s));
	}
	
	public static String unicodeToString(String unicode) {
		StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // ת����ÿһ�������
            int data = Integer.parseInt(hex[i], 16);
            // ׷�ӳ�string
            System.out.println("the char is "+(char) data);
            string.append((char) data);
        }
        return string.toString();
	}

}
