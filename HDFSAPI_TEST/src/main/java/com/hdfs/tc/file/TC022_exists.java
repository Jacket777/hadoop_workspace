package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC022_exists {
    private static Logger logger = LoggerFactory.getLogger(TC022_exists.class);

    //编号NO.22
    //测试API:boolean exists(Path f)
    //测试场景
    //1.f为文件夹path
    //2.f为文件Path
    //3.f为不存在文件path
    public static void test(String path){
        String apiName = "boolean exists(Path f)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs ==null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }

        String DirPath = path;
        String FilePath1 = "/test01.txt";
        String FilePath2 = "/test02.txt";
        String FilePath3 =DirPath + "/test03.txt";
        boolean result = Tool.createDirAndFile(DirPath,FilePath1,FilePath2);
        if(!result){
            logger.info("创建文件夹与创建文件失败，请检查");
            return;
        }
        logger.info("测试案例1：f 为文件夹目录path");

        boolean result1 = false;
        try{
            Path Dir = new Path(path);
            result1 = dfs.exists(Dir);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件失败，请检查......");
        }

        if(result1){
            logger.info("测试案例1：f 为文件夹目录 测试通过.........");
        }else{
            logger.info("测试案例1：f 为文件夹目录 测试不通过.........");
        }


        logger.info("测试案例2：f 为文件目录path");
        boolean result2 = false;
        try{
            String testfilePath = path+FilePath2;
            Path testPath = new Path(testfilePath);
            result2 = dfs.exists(testPath);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件失败，请检查......");
        }

        if(result2){
            logger.info("测试案例2：f 为文件目录 测试通过.........");
        }else{
            logger.info("测试案例2：f 为文件目录 测试不通过.........");
        }

        logger.info("测试案例3：f 为不存在文件目录path");
        boolean result3 = false;
        try{
            String wrongPath = path+FilePath3;
            Path testPath = new Path(wrongPath);
            result3 = dfs.exists(testPath);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件是否存在失败，请检查......");
        }

        if(result3){
            logger.info("测试案例3：f 为不存在文件目录 测试不通过.........");
        }else{
            logger.info("测试案例3：f 为不存在文件目录 测试通过.........");
        }

        logger.info("测试API: "+apiName+" 结束......");
    }


}
