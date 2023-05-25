package com.hdfs.test;

/**
 * Created by 17081290 on 2018/1/12.
 */
public class TestFileSuite {
    public static void test(String Dir){
        System.out.println("测试用例编号：N01");
        API24Test.testMKdirs(Dir+TestP.API01);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N02");
        API24Test.testMKdirsWithP(Dir+TestP.API02);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N03");
        API24Test.testMKdirWithP(Dir+TestP.API03);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N04");
        API24Test.testCreateFile(Dir+TestP.API04);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N05");
        API24Test.testCreateFile1(Dir+TestP.API05);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N06");
        API24Test.testCreateFileWithProgress(Dir+TestP.API06);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N07");
        API24Test.testCreateFileReplic(Dir+TestP.API07);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N08");
        API24Test.testCreatFileWithRP(Dir+TestP.API08);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N09");
        API24Test.testCreateFileWithSize(Dir+TestP.API09);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N10");
        API24Test.testCreateWithSizeP(Dir+TestP.API10);
    }
}
