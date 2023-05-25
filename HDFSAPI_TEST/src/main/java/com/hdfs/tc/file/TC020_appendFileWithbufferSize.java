package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC020_appendFileWithbufferSize {
    private static Logger logger = LoggerFactory.getLogger(TC020_appendFileWithbufferSize.class);

    //编号NO.20
    //测试API: append(Path f, int bufferSize)
    //API说明:追加文件时，以buffSize为限制，
    /**
     Append to an existing file (optional operation).
     Same as append(f, bufferSize, null).
     f the existing file to be appended.
     bufferSize the size of the buffer to be used.
     IOException
     */
    public static void test(String path){
        int bufferSize = 4;
        String append = "test append";
        Path filePath = new Path(path);
        String apiName = "append(Path f, int bufferSize)";

        logger.info("测试API: "+apiName+" 开始......");
        logger.info("测试准备1：创建测试文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查！");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取DFS失败，请检查........");
            return;
        }
        try{
            FSDataOutputStream outputStream = dfs.append(filePath,bufferSize);
            outputStream.write(append.getBytes());
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            logger.info("append 文件失败，请检查......");
        }

        //检查测试结果
        boolean result = Tool.checkAppendFile(path,append);
        if(result){
            logger.info("该用例测试通过......");
        }else{
            logger.info("该用例测试失败......");
        }
        logger.info("测试API: "+apiName+" 结束......");
    }


}
