package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC035_globStatus {
    private static Logger logger = LoggerFactory.getLogger(TC035_globStatus.class);


    //编号NO.35
    //测试API:FileStatus[] globStatus(Path pathPattern)
    //功能描述：Return all the files that match filePattern and are not checksum
    //files. Results are sorted by their names.Results are sorted by their names.
    //参数说明:Path pathPattern
    //返回值：FileStatus[]
    //测试用例描述：传入文件夹参数
    public static void test(String path){
        String apiName = "globStatus(Path pathPattern)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";

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

        String PathPattern =  path+"/*";
        Path path1 =  new Path(PathPattern);

        logger.info("测试用例:传入的正则为*,返回该文件夹下文件信息");
        FileStatus[] result = null;

        try{
            result= dfs.globStatus(path1);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("测试用例失败，请检查");

        }
        if(result == null){
            logger.info("测试用例失败，请检查");
        }else{
            logger.info("测试用例成功,信息如下：");
            logger.info("文件信息汇总如下：");
            int i  = result.length;
            logger.info("共计："+i+" 个信息");
            for(int j = 0; j< i; j++){
                FileStatus fs = result[j];
                logger.info(fs.toString());
            }
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
