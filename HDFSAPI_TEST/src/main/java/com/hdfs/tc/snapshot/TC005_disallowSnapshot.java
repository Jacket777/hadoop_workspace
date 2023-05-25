package com.hdfs.tc.snapshot;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC005_disallowSnapshot {
    private static Logger logger = LoggerFactory.getLogger(TC005_disallowSnapshot.class);
    //编号NO.41
    //测试API:disallowSnapshot(final Path path)
    //功能描述：关闭快照
    //测试用例描述：
    //1.先开启快照，后关闭快照，再检查相应目录是否存在
    public static void test(String path){
        String apiName = "disallowSnapshot(final Path path)";
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
        Path Directory = new Path(path);
        String snapshot = path+"/.snapshot";

        try{
            logger.info("测试步骤1：开启snapshot");
            dfs.allowSnapshot(Directory);
            logger.info("测试步骤2：关闭snapshot");
            dfs.disallowSnapshot(Directory);
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        if(Tool.checkDir(snapshot)){
            logger.info("关闭快照失败，用例测试不通过！");
        }else{
            logger.info("关闭快照成功，用例测试通过！");
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
