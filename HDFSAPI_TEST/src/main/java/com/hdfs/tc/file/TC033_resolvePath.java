package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC033_resolvePath {
    private static Logger logger = LoggerFactory.getLogger(TC033_resolvePath.class);

    //编号NO.33
    //测试API:resolvePath(final Path p)
    //功能描述：Return the fully-qualified path of path f resolving the path through any symlinks or mount point
    //参数说明:p path to be resolved
    //返回值：return fully qualified path(Path对象）
    //异常说明
    //FileNotFoundException
    //测试用例描述:传入文件夹参数
    public static void test(String path){
        String apiName = "resolvePath(final Path p)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test/test2.txt";

        logger.info("测试步骤1：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试步骤2：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试步骤3：解析Path对象");
        logger.info("测试用例1：path为已经存在的路径，应解析为正确路径");
        Path Directory = new Path(path);
        Path result = null;
        try{
            result = dfs.resolvePath(Directory);
        }catch(Exception e){
            logger.info("解析路径失败，请检查");
        }
        logger.info("测试结果如下:");
        if(result==null){
            logger.info("测试用例1：解析失败，该用例测试失败，请检查");
        }else{
            logger.info("测试用例1：解析成功，该用例测试成功");
            logger.info("解析如下:"+result.toString());
        }

        Path wrong = new Path(Directory+"/aa");
        Path result1 = null;
        logger.info("测试用例2：path为不存在的路径，应抛出异常");
        try{
            result1 = dfs.resolvePath(wrong);
        }catch (Exception e){
            logger.info("测试用例2 执行成功，抛出异常");
            e.printStackTrace();
        }
        if(result1==null){
            logger.info("测试用例2：执行成功");
        }
        logger.info("测试API: " + apiName + "结束......");
    }
}
