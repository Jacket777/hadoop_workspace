package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC034_makeQualified {
    private static Logger logger = LoggerFactory.getLogger(TC034_makeQualified .class);

    //编号NO.34
    //测试API:Path makeQualified(Path path)
    //功能描述：Make sure that a path specifies a FileSystem
    //参数说明:param path to use
    //返回值：Path
    //测试用例描述:
    //1.文件存在，将解析为HDFS上路径
    //2.文件不存在，将解析为HDFS上路径
    //参数说明：传入一个并不存在的文件夹路径，用例将自动创建
    public static void test(String path){
        String apiName = "makeQualified(Path path)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test/test2.txt";

        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }

        String rightPath =  path+file1;
        Path path1 = new Path(rightPath);
        String wrongPath = path+"/AA";
        Path path2 = new Path(wrongPath);

        logger.info("测试用例1：参数为正确的Path,应返回正确的path");
        Path result1 = null;
        try{
            result1 = dfs.makeQualified(path1);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result1==null){
            logger.info("测试用例1失败，请检查");
        }else{
            logger.info("测试用例1成功,Path 信息如下：");
            logger.info(result1.toString());
        }
        logger.info("测试用例2：参数为错误的Path,解析为HDFS上路径");
        Path result2 = null;
        try{
            result2 = dfs.makeQualified(path2);
        }catch (Exception e){
            logger.info("测试用例2失败！,它将抛出异常");
            e.printStackTrace();
        }
        if(result2==null){
            logger.info("测试用例2失败");
        }else{
            logger.info("测试用例2成功,Path 信息如下：");
            logger.info(result2.toString());
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
