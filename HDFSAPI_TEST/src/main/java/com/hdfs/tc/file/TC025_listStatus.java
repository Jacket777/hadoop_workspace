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
public class TC025_listStatus {
    private static Logger logger = LoggerFactory.getLogger(TC025_listStatus.class);

    //编号NO.25
    //测试API:FileStatus[] listStatus(Path p)
    public static void test(String path){
        String apiName = "listStatus(Path p)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查");
            return;
        }

        Path filepath = new Path(path);
        try{
            FileStatus[] f = dfs.listStatus(filepath);
            if(f.length==0){
                logger.info("该用例测试失败......");
            }else{
                logger.info("该用例测试成功......");
                logger.info("该文件信息: "+f[0].toString()) ;
            }
        }catch(Exception e ){
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ") ;
        }

        logger.info("测试 "+apiName+"结束......");
    }


}
