//package com.cnsuning.hdfs;
//
//
//import org.apache.hadoop.fs.BlockStoragePolicySpi;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
////import org.apache.hadoop.fs.XAttrSetFlag;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.*;
//
//public class DfsNewApiTest {
//    private static final Logger logger = LoggerFactory.getLogger("dfsNewApiTest");
//    private final FileSystem dfs;
//    private final Path rootPath;
//    private DfsTestUtil util;
////    EnumSet<XAttrSetFlag> flag;
//
//    public DfsNewApiTest(FileSystem dfs, Path path) {
//        this.dfs = dfs;
//        this.rootPath = path;
//        util = new DfsTestUtil(dfs, rootPath);
////        flag = EnumSet.of(XAttrSetFlag.CREATE);
//    }
//
//
//    public void test() throws IOException {
//        //storagePolicy
//        testAllStroageApi();
//        //
//        //truncate
//        testTruncate();
//        //xAttr
//        testAllXAttrs();
//        //
//        testStartLocalOutput();
//        testSupportSysmlinks();
//        //unsupport
//        //testCreateSymlink();
//    }
//
//    private void testCreateSymlink() throws IOException {
//        Path file = util.createTmpFile("testCreateSymlink");
//        Path file1= util.createTmpFile("testCreateSymlink1");
//    }
//
//    private void testSupportSysmlinks() {
//        logger.info("testSupportSysmlinks:" + dfs.supportsSymlinks());
//    }
//
//    private void testStartLocalOutput() throws IOException {
//        Path local = util.createLocalTmpFile("testStartLocalOutput_local");
//        Path file = util.createTmpFile("testStartLocalOutput_file");
//        logger.info("testStartLocalOutput:" + dfs.startLocalOutput(file, local).toString());
//    }
//
//    private void testAllXAttrs() throws IOException {
//        Path path = util.createTmpFile("testAllXAttrs");
//        Path path1 = util.createTmpFile("testAllXAttrs1");
//
//        String name = "user.x";
//        String name2 = "user.y";
////        if(dfs.getXAttr(path, name)!=null) {
////            logger.info("getXAttr_before:" + dfs.getXAttr(path, name).toString());
////        }
////        if(dfs.getXAttr(path1, name)!=null) {
////            logger.info("getXAttr_before1:" + dfs.getXAttr(path1, name).toString());
////        }
//        dfs.setXAttr(path, name, name.getBytes());
//        dfs.setXAttr(path1, name2, name2.getBytes());
//
//        List<String> list = new ArrayList<>();
//        list.add(name);
//        list.add(name2);
//        logger.info("getXAttr:" + dfs.getXAttr(path, name).toString());
//        dfs.setXAttr(path, name, name.getBytes());
//        logger.info("getXAttr_after_setXA:" + dfs.getXAttr(path, name).toString());
//        logger.info("getXAttr_after_setXA:" + dfs.getXAttr(path1, name2).toString());
//
//        Path path3 = util.createTmpFile("testAllXAttrs2");
//        dfs.setXAttr(path3, name, name.getBytes());
//        dfs.setXAttr(path3, name2, name2.getBytes());
//
//        logger.info("getXAttrs(path,list):" + dfs.getXAttrs(path3, list).toString());
//        logger.info("listXAttrs_size" + dfs.listXAttrs(path).size());
//        dfs.removeXAttr(path, name);
//        //移除了，下面取消注释胡报错
//        // logger.info("getXAttr_after_removeXAttr:" + dfs.getXAttr(path, name).toString());
//
//    }
//
//
//    private void testTruncate() throws IOException {
//        Path path = util.createTmpFile("testTruncate");
//        logger.info("path_exists:   "+dfs.exists(path));
//        boolean b = dfs.truncate(path, 1);
//        logger.info("testTruncate" + b);
//    }
//
//    private void testAllStroageApi() throws IOException {
//        Path path = util.createTmpFile("testAllStroageApi");
//
//        logger.info("getStorageStatistics(Path)_name" + dfs.getStoragePolicy(path).getName().toString());
//
//        Collection<? extends BlockStoragePolicySpi> l =  dfs.getAllStoragePolicies();
//        Iterator it = l.iterator();
//        while(it.hasNext()){
//            BlockStoragePolicySpi ll= (BlockStoragePolicySpi) it.next();
//            logger.info("getAllStoragePolicies:" + ll.getName().toString());
//        }
//
//        dfs.setStoragePolicy(path, "ALL_SSD");
//        logger.info("getStorageStatistics(Path)_name_after_setStoragePolicy:" +
//                dfs.getStoragePolicy(path).getName().toString());
//        dfs.unsetStoragePolicy(path);
//        logger.info("getStorageStatistics(Path)_name_after_unsetStoragePolicy:" +
//                dfs.getStoragePolicy(path).toString());
//    }
//}
