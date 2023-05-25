package com.cnsuning.hdfs;

//public class Main {
////    /**
////     * @param args args[0]  root path for test in hdfs
////     * @throws IOException
////     */
//
//
////    //test api
////    public static void main(String[] args) throws IOException, URISyntaxException {
////        //创建文件的根目录
////        testDfsApi(args[0]);
////    }
//
//    public static void testDfsApi(String path)
//            throws IOException, URISyntaxException {
//        Configuration conf = new Configuration();
//        DistributedFileSystem dfs =   (DistributedFileSystem) new Path(path).getFileSystem(conf);
//        //执行 aptTest下 test（）方法
////        new DfsNewApiTest(dfs, new Path(path)).test();
//        //new DfsApiTest(dfs, new Path(path)).test();
//        DfsApiTest dfsTest = new DfsApiTest(dfs, new Path(path));
//        dfsTest.test();
//    }
//
//}
