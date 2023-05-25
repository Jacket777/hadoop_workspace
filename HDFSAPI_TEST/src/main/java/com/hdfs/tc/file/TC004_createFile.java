package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class TC004_createFile {
    private static Logger logger = LoggerFactory.getLogger(TC004_createFile.class);
    //编号NO.4
    //测试API: create(Path path)
    //1.原文件不存在，则新建文件
    //2.原文件已存在，则重写该文件
    public static void test(String path) {
        String APIName = "create(Path path)";
        logger.info("测试创建文件API "+APIName+"开始.........");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        if (Tool.checkFile(path)) {
            logger.info("该文件已经存在，请重新创建文件........");
            return;
        }
        Path filepath = new Path(path);
        logger.info("测试案例 1：文件不存在，创建新文件");
        String content = "Hello";
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("创建DFS对象或创建文件时出现异常");
        } finally {
            logger.info("创建文件结束");
        }
        //检查创建文件结果
        logger.info("检查测试案例执行结果");
        String realContent= Tool.getContent(path);
        logger.info("文件内容为 : "+realContent);
        if(Tool.checkFile(path)&&(realContent!=null)&&(realContent.equals(content))){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例 1 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例 2：文件存在，重写新文件");
        String content2 = "World";
        if (!Tool.checkFile(path)) {
            logger.info("该文件不存在，不满足测试条件，请检查测试代码");
            return;
        }

        try {
            FSDataOutputStream outputStream =  dfs.create(filepath);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (IOException e) {
            logger.info("重写文件时出现异常");
            e.printStackTrace();
        } finally {
            logger.info("创建文件结束");
        }
        //检查创建文件结果
        logger.info("检查测试案例执行结果");
        String realContent2= Tool.getContent(path);
        logger.info("文件内容为 : "+realContent2);
        if(Tool.checkFile(path)&&(realContent2!=null)&&(realContent2.equals(content2))){
            logger.info("测试用例 2 执行成功，测试通过");
        }else{
            logger.info("测试用例 2 执行失败，测试不通过");
        }
        logger.info("测试创建文件API "+APIName+"结束.........");
    }

}
