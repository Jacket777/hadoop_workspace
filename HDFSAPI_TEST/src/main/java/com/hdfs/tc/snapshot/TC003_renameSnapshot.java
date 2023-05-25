package com.hdfs.tc.snapshot;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC003_renameSnapshot {
    private static Logger logger = LoggerFactory.getLogger(TC003_renameSnapshot.class);

    //编号NO.39
    //测试API:renameSnapshot(final Path path, final String snapshotOldName, final String snapshotNewName)
    //功能描述：重命名快照
    //测试用例描述：
    public static void test(String path){
        String apiName = "renameSnapshot(final Path path, final String snapshotOldName, final String snapshotNewName)";
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
        String name = "testSnap";
        String newName ="newSnap";
        try{
            logger.info("测试步骤1：开启snapshot");
            dfs.allowSnapshot(Directory);
            logger.info("测试步骤2：创建snapshot文件");
            dfs.createSnapshot(Directory,name);
            logger.info("测试步骤3：重命名snapshot文件");
            dfs.renameSnapshot(Directory,name,newName);

        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        String snapshot = path+"/.snapshot/"+newName;

        if(Tool.checkDir(snapshot)){
            logger.info("快照记录文件重命名成功，用例测试通过！");
        }else{
            logger.info("快照记录文件重命名失败，用例测试不通过！");
        }

        logger.info("测试API: " + apiName + "结束......");
    }

}
