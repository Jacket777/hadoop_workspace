package com.hdfs.tc.coachepool;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.CachePoolEntry;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC003_listCachePools {
    private static Logger logger = LoggerFactory.getLogger(TC003_listCachePools .class);
    //编号:NO.44
    //测试API:RemoteIterator<CachePoolEntry> listCachePools()
    //功能描述：列出缓存池
    //测试用例描述：
    //1.先添加两个缓存池，再检查列表
    public static void test(String poolName){
        String APIName = "listCachePools()";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;}

        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo1 = new CachePoolInfo(poolName);
        CachePoolInfo cachePoolInfo2 = new CachePoolInfo(poolName+"2");
        try{
            dfs.addCachePool(cachePoolInfo1);
            dfs.addCachePool(cachePoolInfo2);
        }catch(Exception e){
            logger.info("添加缓存池失败，请检查");
            e.printStackTrace();
            return;}

        logger.info("测试步骤2：列出缓存池");
        RemoteIterator<CachePoolEntry> cachePoolEntryRemoteIterator = null;
        try{
            logger.info("测试步骤2-1：列出缓存池");
            cachePoolEntryRemoteIterator = dfs.listCachePools();
        }catch(Exception e){
            logger.info("获取缓存池列表失败，请检查");
            e.printStackTrace();
            return;}

        if(cachePoolEntryRemoteIterator == null){
            logger.info("该用例执行失败，请检查");
            return;}

        boolean checkPoolName1 = false;
        boolean checkPoolName2 = false;

        try {
            while(cachePoolEntryRemoteIterator.hasNext()){
                String name = cachePoolEntryRemoteIterator.next().getInfo().getPoolName();
                if(name.equals(poolName)){
                    checkPoolName1 = true;
                }
                if(name.equals(poolName+"2")){
                    checkPoolName2 = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(checkPoolName1&&checkPoolName2){
            logger.info("该用例执行成功.......PASS");
        }else{
            logger.info("该用例执行失败，请检查");
        }
        logger.info("测试API: " + APIName + "结束......");
    }


}
