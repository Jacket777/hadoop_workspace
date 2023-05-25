package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class TC018_openFileWithBufferSize {
    private static Logger logger = LoggerFactory.getLogger(TC018_openFileWithBufferSize.class);

    //编号NO.18
    //测试API: open(Path f, final int bufferSize)
    //说明:传入参数为HDFS文件目录
    public static void test(String path){
        String apiName = "open(Path f, final int bufferSize)";
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
        Path filePath = new Path(path);
        FSDataInputStream in = null;
        String content ="";
        String expect ="helloWorld";
        try{
            in = dfs.open(filePath,5);
            content = in.readLine();
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("文件内容为: "+content);
        if(content.equals(expect)){
            logger.info("该用例测试通过...............");
        }else{
            logger.info("该用例测试不通过...............");
        }
        logger.info("测试API: "+apiName+" 结束......");
    }

}
