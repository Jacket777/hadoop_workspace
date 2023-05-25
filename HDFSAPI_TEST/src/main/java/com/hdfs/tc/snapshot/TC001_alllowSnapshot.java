package com.hdfs.tc.snapshot;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC001_alllowSnapshot {
    private static Logger logger = LoggerFactory.getLogger(TC001_alllowSnapshot.class);

    //编号NO.37
    //测试API:allowSnapshot(final Path path)
    //功能描述：开启快照
    //测试用例描述：
    public static void test(String path){
        String apiName = "allowSnapshot(final Path path)";
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
        try{
            dfs.allowSnapshot(Directory);
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        String snapshot = path+"/.snapshot";

        if(Tool.checkDir(snapshot)){
            logger.info("快照文件夹创建成功，用例测试通过！");
        }else{
            logger.info("快照文件夹创建失败，用例测试不通过！");
        }
    }


}
