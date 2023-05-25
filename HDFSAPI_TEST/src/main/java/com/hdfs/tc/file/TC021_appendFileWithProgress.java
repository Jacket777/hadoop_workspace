package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC021_appendFileWithProgress {
    private static Logger logger = LoggerFactory.getLogger(TC021_appendFileWithProgress.class);

    //编号NO.21
    //测试API:append(Path f, final int bufferSize, final Progressable progress)
    public static void test(String path){
        String apiName = "append(Path f, final int bufferSize, final Progressable progress)";
        logger.info("测试API: "+apiName+" 开始......");
        logger.info("请注意进度条.........");
        logger.info("测试准备1：创建测试文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查！");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取DFS失败，请检查........");
            return;
        }

        String append = "test append";
        int bufferSize = 100;
        Path filePath = new Path(path);
        Progressable progressabel = Tool.getProgressable();
        if(dfs!=null){
            try{
                FSDataOutputStream outputStream = dfs.append(filePath,bufferSize,progressabel);
                outputStream.write(append.getBytes());
                outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
                logger.info("append 文件失败，请检查......");
            }
        }else{
            logger.info("获取该文件信息失败，请检查........");
            return;
        }

        //检查测试结果
        boolean result = Tool.checkAppendFile(path,append);
        if(result){
            logger.info("该用例测试通过......");
        }else{
            logger.info("该用例测试失败......");
        }

        logger.info("测试API: "+apiName+" 结束......");
    }


}
