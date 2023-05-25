package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class TC011_createFileWithBlock {
    private static Logger logger = LoggerFactory.getLogger(TC011_createFileWithBlock.class);


    //编号NO.11
    //测试API :create(Path f,boolean overwrite,int bufferSize,short replication,long blockSize)
    //测试场景
    //1.文件不存在，overwrite 为false,则创建该文件
    //2.文件不存在，overwrite 为true ,则创建该文件
    //3.文件存在，overwrite 为true, 则重写该文件
    //4.文件存在，overwrite 为false,则抛出异常
    //备注：传入的参数为文件夹目录
    public static void test(String path){
        String APIName = "create(Path f,boolean overwrite,int bufferSize,short replication,long blockSize)";
        logger.info("测试创建文件API: "+ APIName+" 开始......");
        int bufferSize = 1024;
        short replication = 3;
        long blockSize = 1048576;
        String content = "Hello";
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
            FSDataOutputStream outputStream =   dfs.create(filepath1, overwrite1, bufferSize,replication,blockSize);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查4点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确
        int rep1 = Tool.getFileReplication(filepath1);
        long bs1 = Tool.getFileBlockSize(filepath1);
        String realContent= Tool.getContent(testfile1);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))&&(rep1 == replication)&&(bs1 == blockSize)){
            logger.info("测试用例1 执行成功，测试通过");
            logger.info("文件内容为 : "+realContent);
            logger.info("副本数为: "+rep1);
            logger.info("blockSize: "+bs1);
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件不存在，且overwrite 为true");
        boolean overwrite2 = true;
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2, overwrite2, bufferSize,replication,blockSize);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查4点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确
        int rep2 = Tool.getFileReplication(filepath2);
        long bs2 = Tool.getFileBlockSize(filepath2);
        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))&&(rep2 == replication)&&(bs2 == blockSize)){
            logger.info("测试用例2  执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
            logger.info("副本数为: "+rep2);
            logger.info("blockSize: "+bs2);
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
            FSDataOutputStream outputStream =  dfs.create(filepath3, overwrite3, bufferSize,replication,blockSize);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查4点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确
        int rep3 = Tool.getFileReplication(filepath3);
        long bs3 = Tool.getFileBlockSize(filepath3);
        String realContent3= Tool.getContent(testfile1);
        if((realContent2!=null)&&(realContent3.equals(content2))&&(rep3 == replication)&&(bs3 == blockSize)){
            logger.info("测试用例3  执行成功，测试通过");
            logger.info("文件内容为: "+realContent3);
            logger.info("副本数为: "+rep3);
            logger.info("blockSize: "+bs3);
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
            FSDataOutputStream outputStream =  dfs.create(filepath4, overwrite4, bufferSize,replication,blockSize);
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
