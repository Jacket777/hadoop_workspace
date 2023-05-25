package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class TC003_mkdir {
    private static Logger logger = LoggerFactory.getLogger(TC003_mkdir.class);
    //编号NO.3
    //测试API: mkdir(Path path, FsPermission fsPermission) /demox 根文件夹
    public static void test(String path) {
        String APIName = "mkdir(Path path, FsPermission fsPermission)";
        logger.info("测试创建文件API "+APIName+"开始.........");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }

        logger.info("测试准备 2:检查目录设置是否正确");
        if (Tool.checkIsPath(path)) {
            logger.info("该目录已经存在，请输入其他目录");
            return;
        }else{
            logger.info("该目录不存在，API将新文件夹");
        }

        try {
            FsPermission fsPermisson = FsPermission.getDefault();
            Path dir = new Path(path);
            dfs.mkdir(dir, fsPermisson);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("创建文件夹时 出现异常");
        } finally {
            logger.info("创建文件夹结束");
        }
        //判断文件夹是否创建成功
        Tool.checkDirResult(path);
        logger.info("测试创建文件API "+APIName+"结束.........");
    }
}
