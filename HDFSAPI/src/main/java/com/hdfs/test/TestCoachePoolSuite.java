package com.hdfs.test;

/**
 * Created by 17081290 on 2018/1/8.
 */
public class TestCoachePoolSuite {
    public static void test(){
        //42
        System.out.println("测试用例编号：N042");
        API24Test.testAddCachePool(TestP.API42);
        //43
        System.out.println("测试用例编号：N053");
        API24Test.testRemoveCachePool(TestP.API43);
        //44
        System.out.println("测试用例编号：N044");
        API24Test. testListCachePools(TestP.API44);
        //45
        System.out.println("测试用例编号：N045");
        API24Test.testModifyCachePool(TestP.API45);
    }





}
