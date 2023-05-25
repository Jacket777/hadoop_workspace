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
public class TC006_createFileWithProgressable {
    private static Logger logger = LoggerFactory.getLogger(TC006_createFileWithProgressable.class);
    //编号NO.6
    //测试API: create(Path f,Progressable prgress) //测试创建文件，且展示进度条
    //测试场景：
    //(1).如果文件不存在，则创建新文件
    //(2).如果文件存在，则重写该文件
    //备注： Progressable 方法在创建文件后写入数据时调用
    public static void test(String path) {
        String APIName = "create(Path f,Progressable progress)";
        logger.info("测试创建文件 API:"+APIName+" 开始......");
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

        logger.info("测试准备 3:获得Progressable 对象");
        Progressable progressable = Tool.getProgressable();
        if(progressable == null){
            logger.info("获取progreesable失败，请检查");
            return;
        }

        logger.info("测试案例 1：该文件不存在，且创建文件时不写入数据，则无进度条显示，请注意进度条");
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, progressable);
        } catch (Exception e) {
            logger.info("创建文件 出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在
        if(Tool.checkFile(testfile1)){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例 2：该文件不存在，且创建文件时写入数据，则进度条显示，请注意进度条");
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2,progressable);
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
        logger.info("测试案例 3：该文件存在，且重写文件时不写入数据，则无进度条显示，请注意进度条");
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            dfs.create(filepath3,progressable);
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent3= Tool.getContent(testfile1);
        logger.info("文件内容为: "+realContent3);
        if((realContent2==null)||(realContent3.equals(""))){
            logger.info("测试用例3  执行成功，测试通过");
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例 4：该文件存在，且重写文件时写入数据，则进度条显示，请注意进度条");
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            e.printStackTrace();
        }
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent4= Tool.getContent(testfile2);
        logger.info("文件内容为: "+realContent4);
        if((realContent4!=null)&&(realContent4.equals(""))){
            logger.info("测试用例4  执行成功，测试通过");
        }else{
            logger.info("测试用例4 执行失败，测试不通过");
        }

        logger.info("测试创建文件 API:"+APIName+" 结束......");
    }

}
