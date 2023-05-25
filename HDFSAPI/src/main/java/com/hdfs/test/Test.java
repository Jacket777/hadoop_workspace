package com.hdfs.test;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;

import java.net.URL;

/**
 * Created by 17081290 on 2017/12/20.
 */
public class Test {
    static{
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) {
        TestFileSuite.test(args[0]);
        System.out.println("====================NEXT TEST SUITE======================");
        TestFileSuite2.test(args[0]);
        System.out.println("====================NEXT TEST SUITE======================");
        TestFileSuite3.test(args[0]);
        System.out.println("====================NEXT TEST SUITE======================");
        TestSnapshotSuite.test(args[0]);
        System.out.println("====================NEXT TEST SUITE======================");
        TestACLSuite.test(args[0]);

        //API24Test.testAddCachePool(args[0]);
        //API24Test.testRemoveCachePool(args[0]);
        //API24Test.testListCachePools(args[0]);
        //API24Test.testModifyCachePool(args[0]);
        //API24Test.testAddCacheDirWithFlage(args[0],args[1]);
        //API24Test.testModifyCacheDir(args[0],args[1]);
        //API24Test.testAddCacheDirective(args[0],args[1]);
        //API24Test.testListCacheDirectives(args[0],args[1]);
        //API24Test.testModifyCacheDir(args[0],args[1]);
         //API24Test.testAllowSnapshot(args[0]);
        //API24Test.testCreateSnapshot(args[0]);
         //API24Test.testRenameSnapshot(args[0]);
        //API24Test.testDeleteSnapshot(args[0]);
        //API24Test.testDisallowSnapshot(args[0]);
        //String Dir = "/demo/B/";
        //TestSnapshotSuite.test(Dir);
        //API24Test.testGlobStatus(args[0]);
        //API24Test.testGlobalStatusWithFilter(args[0]);
        //API24Test.testMakeQualified(args[0]);
        //API24Test.testResolvePath(args[0]);
         //API24Test.testDelete(args[0]);
        //API24Test.testCompleteLocalOutput(args[0]);
         //API24Test.testMoveLocalFile(args[0]);
        //API24Test.testIsSafeMode();
        // API24Test.testListStatusWithFSF(args[0]);
        //API24Test.testListStatusWithFS(args[0]);
        //API24Test.testListStatusWithFilter(args[0]);
        //API24Test.testListStatus(args[0]);
        //API24Test.testlistLocatedStatus(args[0]);
        //API24Test.testlistFile(args[0]);
        //API24Test.testIsExists(args[0]);
        //API24Test.testAppendFileWithBP(args[0]);
        //API24Test.testAppendwithSize(args[0]);
        //API24Test.testAppend(args[0]);
        //API24Test.testOpenFileWithSize(args[0]);
       // API24Test.testOpenFile(args[0]);
        //API24Test.testIsFile(args[0]);
        //API24Test.testCreatNewFile(args[0]);
        //API24Test.testCreateFileWithOutput(args[0]);
        //API24Test.testCreateFileWithPP(args[0]);
        //API24Test.testCreateWithB(args[0]);
        //API24Test.testCreateWithBlock(args[0]);
        //API24Test.testCreateWithSizeP(args[0]);
       // API24Test.testCreateFileWithSize(args[0]);
        //API24Test.testCreatFileWithRP(args[0]);
        //API24Test.testCreateFileReplic(args[0]);
        //API24Test.testCreateFileWithProgress(args[0]);
        //API24Test.testCreateFileWithProgress(args[0]);
        //API24Test.createFile1(args[0]);
        //API24Test.testCreateFile(args[0]);
        //API24Test.testMKdirWithP(args[0]);
        //API24Test.testMKdirsWithP(args[0]);
        //API24Test.testMKdirs(args[0]);
        //API24Test.testIsDirectory(args[0]);
        System.out.println("=================================>");
//        String testDir = "demo";
//        TestACLSuite.testACL(testDir);
    }


}

