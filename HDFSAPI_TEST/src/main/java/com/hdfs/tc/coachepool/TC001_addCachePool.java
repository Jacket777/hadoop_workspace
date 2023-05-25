package com.hdfs.tc.coachepool;

import com.hdfs.tools.Tool;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC001_addCachePool {
    private static Logger logger = LoggerFactory.getLogger(TC001_addCachePool.class);

    //编号NO.42
    //测试API:addCachePool(CachePoolInfo info)
    //功能描述：添加缓存池
    //测试用例描述：
    //1.添加缓存池前，先进行缓存池检查，如果该缓存池名称已经存在则给出提示
    //2.添加缓存池后，进行检查是否添加成功
    public static void test(String poolname ){
        String apiName = "addCachePool(CachePoolInfo info)";
        logger.info("测试API: " + apiName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool( poolname);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;
        }

        CachePoolInfo cachePoolInfo = new CachePoolInfo(poolname);
        try{
            dfs.addCachePool(cachePoolInfo);
        }catch(Exception e){
            logger.info("添加缓存池失败，请检查");
            e.printStackTrace();
        }

        //如何检查是否添加成功？
        boolean lastResult = Tool.checkCachePool(poolname);
        if(lastResult){
            logger.info("该缓存池添加成功，用例执行成功");
        }else{
            logger.info("该缓存池添加失败，用例执行失败");
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
