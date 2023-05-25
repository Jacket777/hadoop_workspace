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
public class TC004_modifyCachePool {
    private static Logger logger = LoggerFactory.getLogger(TC004_modifyCachePool.class);

    //编号:NO.45
    //测试API:modifyCachePool(CachePoolInfo info)
    //功能描述：Modify an existing cache pool
    //测试用例描述：
    //1.先添加缓存池，后进行修改
    public static void test(String poolName){
        String APIName  = "modifyCachePool(CachePoolInfo info)";
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
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：修改缓存池owner属性为Jack，名称为 "+poolName);
        String ownerName = "Jack";
        cachePoolInfo.setOwnerName(ownerName);
        try{
            dfs.modifyCachePool(cachePoolInfo);
        }catch(Exception e){
            logger.info("修改缓存池失败，请检查");
            e.printStackTrace();
            return;}

        logger.info("测试步骤3：检查缓存池owner属性为Jack，名称为 "+poolName);
        boolean lastResult = false;
        RemoteIterator<CachePoolEntry> cachePoolEntryIterator = null;
        try{
            logger.info("测试步骤3-1：获取缓存池列表");
            cachePoolEntryIterator = dfs.listCachePools();
        }catch(Exception e){
            logger.info("获取缓存池列表失败，请检查");
            e.printStackTrace();
            return;
        }

        try {
            while(cachePoolEntryIterator.hasNext()){
                String name = cachePoolEntryIterator.next().getInfo().getPoolName();
                String owner = cachePoolEntryIterator.next().getInfo().getOwnerName();
                if(name.equals(poolName)&&(owner.equals(ownerName))){
                    lastResult = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;}
        if(lastResult){
            logger.info("该用例执行成功");
        }else{
            logger.info("该用例执行失败");
        }
        logger.info("测试API: " + APIName + "结束......");
    }
}
