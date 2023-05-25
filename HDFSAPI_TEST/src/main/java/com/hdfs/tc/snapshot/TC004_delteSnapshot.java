package com.hdfs.tc.snapshot;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC004_delteSnapshot {
    private static Logger logger = LoggerFactory.getLogger(TC004_delteSnapshot.class);
    //编号NO.40
    //测试API:deleteSnapshot(final Path snapshotDir, final String snapshotName)
    //功能描述：删除快照
    //测试用例描述：
    public static void test(String path){
        String apiName = "deleteSnapshot(final Path snapshotDir, final String snapshotName)";
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
        String snapshot = path+"/.snapshot/"+name;


        try{
            logger.info("测试步骤1：开启snapshot");
            dfs.allowSnapshot(Directory);
            logger.info("测试步骤2：创建snapshot文件");
            dfs.createSnapshot(Directory,name);
            logger.info("测试步骤3：删除snapshot文件");
            dfs.deleteSnapshot(Directory,name);

        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        if(Tool.checkDir(snapshot)){
            logger.info("删除快照失败，用例测试不通过！");
        }else{
            logger.info("删除快照成功，用例测试通过！");
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
