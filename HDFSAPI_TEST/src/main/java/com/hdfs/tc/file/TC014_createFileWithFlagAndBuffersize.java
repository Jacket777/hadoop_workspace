package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.CreateFlag;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Options;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class TC014_createFileWithFlagAndBuffersize {
    private static Logger logger = LoggerFactory.getLogger(TC014_createFileWithFlagAndBuffersize.class);

    //编号NO.14
    //测试API:FSDataOutputStream create(Path f, final FsPermission permission, final EnumSet<CreateFlag> cflags, final int bufferSize, final short replication, final long blockSize, final Progressable progress, final ChecksumOpt checksumOpt)
    //测试场景：
    //1.创建新文件，新文件创建成功，内容正确
    //2.重写旧文件，旧文件重写成功，内容正确
    public static void test(String path){
        String apiName = "create(Path f, permission, EnumSet<CreateFlag> cflags,  bufferSize, replication, blockSize, progress, checksumOpt)";
        logger.info("测试 "+apiName+" 开始......");
        short replication = 3;
        int bufferSize = 1024;
        long blockSize = 1048576;
        Path f = new Path(path);
        FsPermission permission = FsPermission.getDefault( );
        EnumSet<CreateFlag> flags =  EnumSet.of(CreateFlag.CREATE, CreateFlag.OVERWRITE);
        Progressable progressable = Tool.getProgressable();
        Options.ChecksumOpt checksumOpt = new Options.ChecksumOpt();
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        logger.info("测试准备：获取DFS对象");
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试案例1：文件不存在，API将创建该文件");
        boolean isFileExist = Tool.checkFile(path);
        if(isFileExist){
            logger.info("文件已存在，请选择一个将新建的文件目录");
            return;
        }
        String content = "Hello";


        try{
            FSDataOutputStream outputStream = dfs.create(f, permission,flags, bufferSize, replication, blockSize, progressable, checksumOpt);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
            logger.info("文件创建失败，请检查");
        }

        //检查文件
        Tool.checkFileIsRight(isFileExist,path,content);

        logger.info("测试案例2：文件已存在，API将重写该文件");
        String content2= "World";
        boolean isFileExist2 = Tool.checkFile(path);
        try{
            FSDataOutputStream outputStream = dfs.create(f, permission,flags, bufferSize, replication, blockSize, progressable, checksumOpt);
            outputStream.write(content2.getBytes());
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
            logger.info("文件重写失败，请检查");
        }
        Tool.checkFileIsRight(isFileExist2,path,content2);

        logger.info("测试 "+apiName+" 结束......");
    }


}
