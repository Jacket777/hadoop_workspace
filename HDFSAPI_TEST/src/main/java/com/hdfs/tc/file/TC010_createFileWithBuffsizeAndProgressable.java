package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class TC010_createFileWithBuffsizeAndProgressable {
    private static Logger logger = LoggerFactory.getLogger(TC010_createFileWithBuffsizeAndProgressable.class);


    //编号NO.10
    //测试API: create(Path f,boolean overwrite,int bufferSize,Progressable progress)
    //参数说明
    //1. f,文件路径, overwrite 是否重写， buffsizes
    //测试场景
    //1.文件不存在，则创建文件，则overwrite 为true，创建文件
    //2.文件不存在，则创建文件，则overwrite 为false，创建文件
    //3.文件存在，overwrite 为true，则重写文件
    //4.文件存在，overwrite 为false，则抛出异常错误
    //备注：传入的参数为文件夹目录
    public static void test(String path) {
        String APIName = "create(Path f,boolean overwrite,int bufferSize,Progressable progress)";
        logger.info("测试创建文件API: "+APIName+"开始......");
        int bufferSize = 1024;
        String content = "Hello";
        Progressable progressable = Tool.getProgressable();
        String testfile1 = path+"/test1.txt";
        String testfile2 = path+"/test2.txt";
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }

        logger.info("测试案例1 ：文件不存在，且overwrite 为false");
        boolean overwrite1 = false;
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, overwrite1, bufferSize,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在,2.内容正确
        String realContent= Tool.getContent(testfile1);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))){
            logger.info("测试用例 1 执行成功，测试通过");
            logger.info("文件内容为 : "+realContent);
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件不存在，且overwrite 为true");
        boolean overwrite2 = true;
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2, overwrite2, bufferSize,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确

        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))){
            logger.info("测试用例2  执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例3 ：文件存在，且overwrite 为true");
        boolean overwrite3 = true;
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath3, overwrite3, bufferSize,progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent3= Tool.getContent(testfile1);
        if((realContent2!=null)&&(realContent3.equals(content2))){
            logger.info("测试用例3  执行成功，测试通过");
            logger.info("文件内容为: "+realContent3);
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例4 ：文件存在，且overwrite 为false,将抛出异常");
        boolean overwrite4 = false;
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, overwrite4, bufferSize,progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            logger.info("测试用例4 执行成功");
            e.printStackTrace();
            return;
        }
        logger.info("测试用例4 执行失败");
        logger.info("测试创建文件"+APIName+"结束......");
    }
}
