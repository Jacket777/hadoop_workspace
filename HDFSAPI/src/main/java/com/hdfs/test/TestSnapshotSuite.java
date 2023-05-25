package com.hdfs.test;

/**
 * Created by 17081290 on 2018/1/8.
 */
public class TestSnapshotSuite {
    public static void test(String testDir){
        System.out.println("============执行快速功能测试用例=============================");
        Tool.beginExe();
        System.out.println("测试用例编号：N037");
        API24Test.testAllowSnapshot(testDir+TestP.API37);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N038");
        API24Test.testCreateSnapshot(testDir+TestP.API38);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N039");
        API24Test.testRenameSnapshot(testDir+TestP.API39);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N040");
        API24Test.testDeleteSnapshot(testDir+TestP.API40);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N041");
        API24Test.testDisallowSnapshot(testDir+TestP.API41);
        System.out.println("============快照用例执行完成=============================");
    }
}
