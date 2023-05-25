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
public class TC008_createFileWithReplicationAndProgressable {
    private static Logger logger = LoggerFactory.getLogger(TC008_createFileWithReplicationAndProgressable.class);


    //编号NO.8
    //测试API: create(Path f, short replication,Progressable progress)
    //测试场景：
    //(1).如果文件不存在，则创建新文件
    //(2).如果文件存在，则重写该文件，默认为重写文件
    public static void test(String path) {
        String APIName = "create(Path f, short replication,Progressable progress)";
        logger.info("测试创建文件API :"+APIName+"开始......");
        logger.info("请注意执行进度条，进度条为... ");
        short repliaction = 4;
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
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
        logger.info("测试准备 3:获得Progressable 对象");
        Progressable progressable = Tool.getProgressable();
        if(progressable == null){
            logger.info("获取progreesable失败，请检查");
            return;
        }

        logger.info("测试案例1 ：文件不存在，设置的副本数为4");
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, repliaction, progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查3点：1.文件是否存在,2.内容正确,3.副本数正确
        String realContent= Tool.getContent(testfile1);
        int  realReplication = Tool.getFileReplication(filepath1);
        logger.info("文件内容为 : "+realContent);
        logger.info("副本数 : "+realReplication);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))&&(realReplication==4)){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件存在，则重写该文件");
        String content2 = "World";
        boolean isFileExist = Tool.checkFile(testfile1);
        if(!isFileExist){
            logger.info("该文件不存在，测试条件不满足，请检查测试代码");
            return;
        }

        try {
            FSDataOutputStream outputStream =  dfs.create(filepath1, repliaction, progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常！请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent2= Tool.getContent(testfile1);
        int  realReplication2 = Tool.getFileReplication(filepath1);
        logger.info("文件内容为: "+realContent2);
        logger.info("副本数 : "+realReplication2);
        if(Tool.checkFile(testfile1)&&(realContent2!=null)&&(realContent2.equals(content2))&&(realReplication2==4)){
            logger.info("测试用例2  执行成功，测试通过");
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("测试创建文件API:"+APIName+" 结束......");
    }
}
