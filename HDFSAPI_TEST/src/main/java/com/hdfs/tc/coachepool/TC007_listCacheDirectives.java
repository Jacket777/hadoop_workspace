package com.hdfs.tc.coachepool;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveEntry;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveInfo;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC007_listCacheDirectives {
    private static Logger logger = LoggerFactory.getLogger(TC007_listCacheDirectives.class);

    //编号:NO.48
    //测试API:a RemoteIterator<CacheDirectiveEntry> listCacheDirectives(CacheDirectiveInfo filter)
    //功能描述： List cache directives.  Incrementally fetches results from the server
    //测试用例描述：
    //1.传入参数为null，列出所有文件
    //2.传入参数不为null,则抛出异常.......
    //测试参数：poolName：缓存池名称，fileName:添加缓存文件名称
    public static void test(String poolName,String fileName){
        String APIName = "listCacheDirectives(CacheDirectiveInfo filter)";
        String fileName2 = fileName+"2";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;}

        logger.info("测试准备：检查文件是否存在");
        boolean isFileExist = Tool.checkFile(fileName);
        if(isFileExist){
            logger.info("文件"+fileName+"已经存在，请重新选择文件");
            return;}

        boolean isFileExist2 = Tool.checkFile(fileName2);
        if(isFileExist2){
            logger.info("文件"+fileName2+"已经存在，请重新选择文件");
            return;}



        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：添加文件，名称为 "+fileName);
        Tool.createFile(fileName);
        if(!Tool.checkFile(fileName)){
            logger.info("文件创建失败，请检查");
            return;
        }


        logger.info("测试步骤3：添加文件，名称为 "+fileName2);
        Tool.createFile(fileName2);
        if(!Tool.checkFile(fileName2)){
            logger.info("文件创建失败，请检查");
            return;
        }


        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        String coachFile1 = filePath.getName();
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();

        Path filePath2 = new Path(fileName2);
        String coachFile2 = filePath2.getName();
        CacheDirectiveInfo info2 = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath2).
                setPool(poolName).setReplication((short) 1).build();

        try{
            dfs.addCacheDirective(info);
            dfs.addCacheDirective(info2);
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

//        logger.info("TestCase1：传入带参");
//        RemoteIterator<CacheDirectiveEntry> Iterator1 = null;
//        try{
//            Iterator1 = dfs.listCacheDirectives(info);
//        }catch(Exception e){
//            logger.info("获取RemoteIterator失败，请检查");
//            e.printStackTrace();
//            return;
//        }
//        if( Iterator1== null){
//            logger.info("获取RemoteIterator为空，请检查");
//            return;
//        }
//
//        boolean lastResult1 = false;
//        try {
//            while (Iterator1.hasNext()) {
//                CacheDirectiveEntry ca = Iterator1.next();
//                CacheDirectiveInfo temp = ca.getInfo();
//                String RealfilePath = temp.getPath().getName();
//                logger.info("PATH name"+RealfilePath);
//                if(RealfilePath.equals(coachFile1)){
//                    lastResult1 = true;
//                    break;
//                }
//            }
//        }catch(Exception e){
//            logger.info("RemoteIterator迭代失败，请检查");
//            e.printStackTrace();
//            return;
//        }
//        if(lastResult1){
//            logger.info("TestCase1：测试用例通过");
//        }else{
//            logger.info("TestCase1：测试用例失败");
//        }



        logger.info("TestCase：不传入带参");
        RemoteIterator<CacheDirectiveEntry> Iterator2 = null;
        try{
            Iterator2 = dfs.listCacheDirectives(null);
        }catch(Exception e){
            logger.info("获取RemoteIterator失败，请检查");
            e.printStackTrace();
            return;
        }

//        if( Iterator1== null){
//            logger.info("获取RemoteIterator为空，请检查");
//            return;
//        }

        boolean lastResult21 = false;
        boolean lastResult22 = false;
        try {
            while (Iterator2.hasNext()) {
                CacheDirectiveEntry ca = Iterator2.next();
                CacheDirectiveInfo temp = ca.getInfo();
                String RealfilePath = temp.getPath().getName();
                logger.info("PATH name："+RealfilePath);
                if(RealfilePath.equals(coachFile1)){
                    lastResult21 = true;
                }
                if(RealfilePath.equals(coachFile2)){
                    lastResult22 = true;
                }
            }
        }catch(Exception e){
            logger.info("RemoteIterator迭代失败，请检查");
            e.printStackTrace();
            return;
        }
        boolean lastResult2 = lastResult21&&lastResult22;
        if(lastResult2){
            logger.info("TestCase2：测试用例通过");
        }else{
            logger.info("TestCase2：测试用例失败");
        }
    }
}
