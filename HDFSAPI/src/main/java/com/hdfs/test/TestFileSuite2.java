package com.hdfs.test;

/**
 * Created by 17081290 on 2018/1/12.
 */
public class TestFileSuite2 {
    public static void test(String Dir){
        System.out.println("测试用例编号：N11");
        API24Test.testCreateWithBlock(Dir+TestP.API11);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N12");
        API24Test.testCreateWithB(Dir+TestP.API12);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N13");
        API24Test.testCreateFileWithPP(Dir+TestP.API13);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N14");
        API24Test. testCreateFileWithOutput(Dir+TestP.API14);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N15");
        API24Test.testCreatNewFile(Dir+TestP.API15);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N16");
        API24Test.testIsFile(Dir+TestP.API16);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N17");
        API24Test.testOpenFile(Dir+TestP.API17);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N18");
        API24Test.testOpenFileWithSize(Dir+TestP.API18);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N19");
        API24Test.testAppend(Dir+TestP.API19);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N20");
        API24Test.testAppendwithSize(Dir+TestP.API20);
    }



}
