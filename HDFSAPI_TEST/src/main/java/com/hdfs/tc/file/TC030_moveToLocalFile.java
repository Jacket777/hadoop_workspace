package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC030_moveToLocalFile {
    private static Logger logger = LoggerFactory.getLogger(TC030_moveToLocalFile.class);

    //编号NO.30
    //测试API: moveToLocalFile(Path src, Path dst)
    //功能说明：将HDFS上的文件移动到本地文件中
    //The src file is under FS, and the dst is on the local disk. Copy it from FS control to the local dst name.Remove the source afterwards
    public static void test(String path){
        String apiName = "moveToLocalFile(Path src, Path dst)";
        logger.info("测试API: " + apiName + "开始......");
        logger.info("测试步骤1：在HDFS上创建文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("在HDFS上创建文件失败，请检查");
            return;
        }
        String fileInLocal = "/home/bigdata/moveToLocalFile.txt";
        logger.info("测试步骤2：移动文件到本地");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path src = new Path(path);
        Path dst = new Path(fileInLocal);
        try{
            dfs.moveToLocalFile(src,dst);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("API调用失败，请检查");
            return;
        }
        logger.info("测试步骤3：检查移动后本地文件");
        String result = Tool.readLocalFile(fileInLocal);
        if(result.equals("helloWorld")) {
            logger.info("用例测试通过........");
        }else{
            logger.info("用例测试失败........");
            if(result==null){
                logger.info("读取本地文件失败");
            }else{
                logger.info("读取本地文件内容为"+result);
            }
        }

        logger.info("测试API: " + apiName + "结束......");
    }

}
