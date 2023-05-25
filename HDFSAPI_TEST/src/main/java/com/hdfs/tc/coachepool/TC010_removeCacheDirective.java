package com.hdfs.tc.coachepool;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveEntry;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveInfo;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveStats;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC010_removeCacheDirective {
    private static Logger logger = LoggerFactory.getLogger(TC010_removeCacheDirective .class);

    //编号:NO.51
    //测试API:removeCacheDirective(long id)
    //功能描述：
    //测试用例描述：删除缓存文件
    public static void test(String poolName,String fileName){
        String APIName = "removeCacheDirective(long id)";
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
            logger.info("文件已经存在，请重新选择文件");
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

        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        String cacheFileName = filePath.getName();
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();

        try{
            dfs.addCacheDirective(info);
            logger.info("==========>>>===========");
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

        logger.info("测试步骤4：删除缓存文件");
        Long id = 0L;
        try {
            logger.info("获取缓存文件ID");
            RemoteIterator<CacheDirectiveEntry> It = dfs.listCacheDirectives(null);
            while(It.hasNext()) {
                CacheDirectiveEntry ca = It.next();
                CacheDirectiveInfo temp = ca.getInfo();
                CacheDirectiveStats cs = ca.getStats();

                logger.info("缓存池名称 " + temp.getPool());
                logger.info("缓存文件id为 " + temp.getId());
                logger.info("缓存文件名称 " + temp.getPath().getName());
                //dfs.removeCacheDirective(id);
                if (poolName.equals(temp.getPool()) && cacheFileName.equals(temp.getPath().getName())) {
                    id = temp.getId();
                    logger.info("文件id 为"+id);
                    break;
                }
            }
            if(id!=0L){
                logger.info("根据id删除文件！");
                dfs.removeCacheDirective(id);
            }else{
                logger.info("没找到删除文件！");
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }


        boolean lastResult = false;
        try {
            logger.info("测试步骤5：检查删除缓存文件");
            RemoteIterator<CacheDirectiveEntry> Its = dfs.listCacheDirectives(null);
            while(Its.hasNext()) {
                CacheDirectiveEntry ca = Its.next();
                CacheDirectiveInfo temp = ca.getInfo();
                logger.info("缓存池名称 " + temp.getPool());
                logger.info("缓存文件id为 " + temp.getId());
                logger.info("缓存文件名称 " + temp.getPath().getName());
                if (poolName.equals(temp.getPool()) && cacheFileName.equals(temp.getPath().getName())) {
                    lastResult=true;
                    logger.info("应该删除文件依然存在！");
                    break;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            return;
        }

        if(!lastResult){
            logger.info("用例执行成功");
        }else{
            logger.info("用例执行失败");
        }

        logger.info("测试API: " + APIName + "结束......");
    }
}
