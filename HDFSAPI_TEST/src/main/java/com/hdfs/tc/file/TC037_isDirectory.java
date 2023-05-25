package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC037_isDirectory {
    private static Logger logger = LoggerFactory.getLogger(TC037_isDirectory.class);

    //编号:NO.57
    //测试API：isDirectory(Path f)
    //测试场景
    //1.f 为文件夹存在
    //2.f 文件夹不存在
    //3.f 为文件目录
    public static void test(String path){
        String APIName = "isDirectory(Path f)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备1：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.warn("获取dfs失败，请检查");
            return;}
        String dir  = path;
        String file = dir+"/test.txt";
        logger.info("测试准备2：创建文件");
        if(Tool.checkFile(file)){
            logger.warn("该文件已经存在，请输入其他文件夹，程序将自动帮你创建");
            return;
        }else{
            boolean isSuccess = Tool.createFile(file);
            if(isSuccess){
                logger.info("文件创建成功，测试准备完毕");
            }else{
                logger.error("文件创建失败，请检查");
                return;
            }
        }

        logger.info("测试案例1：f 为文件夹，期望结果为true");
        Path Dir = new Path(path);
        Path filePath = new Path(file);
        boolean result1 = false;
        boolean result2 = false;

        try{
            result1 = dfs.isDirectory(Dir);
            result2 = dfs.isDirectory(filePath);
        }catch(Exception e){
            logger.warn("判断文件夹出现异常，请检查!");
            e.printStackTrace();
        }
        if(result1){
            logger.info("测试案例1 测试成功！");
        }else{
            logger.info("测试案例1 测试失败！");
        }

        logger.info("测试案例2：f 为文件，期望结果为false");
        if(!result2){
            logger.info("测试案例2 测试成功！");
        }else{
            logger.info("测试案例2 测试失败！");
        }
    }
}
