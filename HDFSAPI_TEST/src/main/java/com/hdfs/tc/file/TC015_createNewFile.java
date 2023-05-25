package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class TC015_createNewFile {
    private static Logger logger = LoggerFactory.getLogger(TC015_createNewFile.class);

    //编号NO.15
    //测试API:createNewFile(Path f)
    //API 说明：
    //Creates the given Path as a brand-new zero-length file.  If create fails, or if it already existed, return false.
    //@param f path to use for create
    //测试场景：
    //1.文件不存在，创建文件，返回值为true
    //2.文件存在，创建文件，返回值为false
    public static void test(String path){
        String apiName = "createNewFile(Path f)";
        logger.info("测试API: "+apiName+"开始......");
        logger.info("测试准备1：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }

        logger.info("测试用例1: 文件不存在，返回为true");
        logger.info("步骤1：检查用户输入的文件是否存在");
        boolean isFileExist = Tool.checkFile(path);
        if(isFileExist){
            logger.info("该文件已存在，请重新选择一个不存在的文件目录");
            return;
        }

        boolean result1 = false;
        Path filePath = new Path(path);
        try{
            result1 = dfs.createNewFile(filePath);
        }catch(Exception e){
            logger.info("创建文件失败，请检查");
        }
        if(result1){
            logger.info("测试用例1: 测试通过");
        }else{
            logger.info("测试用例1: 测试失败");
        }

        logger.info("测试用例2: 文件存在，返回为false");
        boolean result2 = true;
        try{
            result2 = dfs.createNewFile(filePath);
        }catch(Exception e){
            logger.info("创建文件失败，请检查");
        }
        if(!result2){
            logger.info("测试用例2: 测试通过");
        }else{
            logger.info("测试用例2: 测试失败");
        }
    }
}
