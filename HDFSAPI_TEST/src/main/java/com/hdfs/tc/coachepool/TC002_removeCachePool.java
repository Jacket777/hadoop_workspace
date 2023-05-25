package com.hdfs.tc.coachepool;

import com.hdfs.tools.Tool;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC002_removeCachePool {
    private static Logger logger = LoggerFactory.getLogger(TC002_removeCachePool.class);
    //编号:NO.43
    //测试API:removeCachePool(String poolName)
    //功能描述：移除缓存池
    //测试用例描述：
    //1.移除缓存池后，进行检查是否移除成功
    public static void test(String poolName){
        String APIName = "removeCachePool(String poolName)";
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
            return;
        }

        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = new CachePoolInfo(poolName);
        try{
            dfs.addCachePool(cachePoolInfo);
        }catch(Exception e){
            logger.info("添加缓存池失败，请检查");
            e.printStackTrace();
            return;
        }

        logger.info("测试步骤2：移除缓存池，名称为 "+poolName);
        try{
            dfs.removeCachePool(poolName);
        }catch(Exception e){
            logger.info("移除缓存池失败，请检查");
            e.printStackTrace();
            return;
        }
        //如何检查
        boolean lastResult = Tool.checkCachePool(poolName);
        if(!lastResult){
            logger.info("该缓存池删除成功，用例执行成功");
        }else{
            logger.info("该缓存池删除失败，用例执行失败");
        }

        logger.info("测试API: " + APIName + "结束......");
    }

}
