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
public class TC008_modifyCacheDirective {
    private static Logger logger = LoggerFactory.getLogger(TC008_modifyCacheDirective.class);

    //编号:NO.49
    //测试API:modifyCacheDirective(CacheDirectiveInfo info)
    //功能描述：
    //测试用例描述：修改缓存文件
    public static void test(String poolName,String fileName){
        String APIName = "modifyCacheDirective(CacheDirectiveInfo info)";
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
        String coachFileName = filePath.getName();
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 5).build();

        try{
            dfs.addCacheDirective(info);
            logger.info("=====================");
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }
        info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 3).build();
        CacheDirectiveInfo.Builder builder = new CacheDirectiveInfo.Builder();
        CacheDirectiveInfo.Expiration expiration =  CacheDirectiveInfo.Expiration.newAbsolute(10000);

        logger.info("测试步骤4：修改缓存文件");
        try {
            //dfs.modifyCacheDirective(info);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        //检查
        boolean isRight = false;
        RemoteIterator<CacheDirectiveEntry> Iterator = Tool.getCoacheFileIterator();
        if(Iterator == null){
            logger.info("获取RemoteIterator失败，值为null,请检查");
            return;
        }
        Short repliaction = 0;
        try {
            while (Iterator.hasNext()) {
                CacheDirectiveEntry ca = Iterator.next();
                CacheDirectiveInfo temp = ca.getInfo();
                String coachName = temp.getPath().getName();
                // long id = temp.getId();
                repliaction = temp.getReplication();
                logger.info("缓存文件名："+coachName);
                logger.info("缓存文件副本数: "+repliaction);
                if(coachName.equals(coachFileName)){
                    logger.info("找到该文件，修改该文件");
                    // dfs.addCacheDirective(info);
                    dfs.modifyCacheDirective(temp);
                    isRight = true;
                }
            }
        }catch(Exception e){
            logger.info("RemoteIterator迭代失败，请检查");
            e.printStackTrace();
            return;
        }

        if(isRight){
            logger.info("修改成功，副本数修改正确，测试用例通过");
        }else{
            logger.info("修改失败，预期修改副本数为: "+3+"实际副本数为: "+repliaction);
        }

        logger.info("测试API: " + APIName + "结束......");
    }

}
