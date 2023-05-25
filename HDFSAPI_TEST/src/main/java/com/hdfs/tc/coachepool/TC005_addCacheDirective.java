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
public class TC005_addCacheDirective {
    private static Logger logger = LoggerFactory.getLogger(TC005_addCacheDirective.class);

    //编号:NO.46
    //测试API:long addCacheDirective(CacheDirectiveInfo info)
    //功能描述：添加缓存文件
    //测试用例描述：
    //1.先添加缓存池，后添加缓存文件
    public static void test(String poolName,String fileName){
        String APIName = "addCacheDirective(CacheDirectiveInfo info)";
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
        logger.info("cache file name is "+cacheFileName);
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();

        try{
            dfs.addCacheDirective(info);
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

        //检查
        boolean lastResult =  false;
        try {
            RemoteIterator<CacheDirectiveEntry> remoteIterator = dfs.listCacheDirectives(null);
            while (remoteIterator.hasNext()) {
                CacheDirectiveEntry ca = remoteIterator.next();
                CacheDirectiveInfo temp = ca.getInfo();
                logger.info("=============>>>==>>>=================");
                String realCoachFile = temp.getPath().getName();
                logger.info(realCoachFile);
                if(realCoachFile.equals(cacheFileName)){
                    lastResult = true;
                    logger.info("Find the file name ,break!!");
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        if(lastResult){
            logger.info("该用例执行成功");
        }else{
            logger.info("该用例执行失败");
        }
        logger.info("测试API: " + APIName + "结束......");

    }

}
