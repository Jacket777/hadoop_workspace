package com.hdfs.test;

/**
 * Created by 17081290 on 2018/1/8.
 */
public class TestACLSuite {
    public static void test(String Dir){
        System.out.println("============执行快速功能测试用例=============================");
        System.out.println("测试用例编号：N052");
        API24Test.testRemoveDefaultAcl(Dir+TestP.API52);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N053");
        API24Test.testRemoveACL(Dir+TestP.API53);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N054");
        API24Test. testRemoveAclEntries(Dir+TestP.API54);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N055");
        API24Test.testModifyAclEntries(Dir+TestP.API55);
        System.out.println("================NEXT API==============================");
        System.out.println("测试用例编号：N056");
        API24Test.testDelegationToken();
    }
}
