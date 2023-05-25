package com.hdfs.test;

/**
 * Created by 17081290 on 2018/1/12.
 */
public class TestFileSuite3 {
    public static void test(String Dir){
        System.out.println("测试用例编号：N21");
        API24Test.testAppendFileWithBP(Dir+TestP.API21);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N22");
        API24Test.testIsExists(Dir+TestP.API22);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N23");
        API24Test.testlistFile(Dir+TestP.API23);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N24");
        API24Test.testlistLocatedStatus(Dir+TestP.API24);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N25");
        API24Test.testListStatus(Dir+TestP.API25);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N26");
        API24Test.testListStatusWithFilter(Dir+TestP.API26);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N27");
        API24Test.testListStatusWithFS(Dir+TestP.API27);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N28");
        API24Test.testListStatusWithFSF(Dir+TestP.API28);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N29");
        API24Test.testIsSafeMode();
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N30");
        API24Test.testMoveLocalFile(Dir+TestP.API30);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N31");
        API24Test.testCompleteLocalOutput(Dir+TestP.API31);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N32");
        API24Test.testDelete(Dir+TestP.API32);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N33");
        API24Test.testResolvePath(Dir+TestP.API33);
        System.out.println("================NEXT API==============================");
        API24Test.testMakeQualified(Dir+TestP.API34);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N035");
        API24Test.testGlobStatus(Dir+TestP.API35);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N036");
        API24Test.testGlobalStatusWithFilter(Dir+TestP.API36);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N057");
        API24Test.testIsDirectory(Dir+TestP.API57);


    }



}
