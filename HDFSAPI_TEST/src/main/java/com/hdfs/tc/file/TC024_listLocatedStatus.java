package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC024_listLocatedStatus {
    private static Logger logger = LoggerFactory.getLogger(TC024_listLocatedStatus.class);

    //编号NO.24
    //测试API:listLocatedStatus(final Path f)
    //功能说明：
    //List the statuses of the files/directories in the given path
    //1.if the path is a directory.Return the file's status and block locations
    //2.If the path is a file.If a returned status is a file, it contains the file's block locations.
    //参数与异常说明：
    //参数：f is the path 本用例中传入参数为文件夹目录
    //@throws FileNotFoundException If <code>f</code> does not exist
    //@throws IOException If an I/O error occurred
    //返回值：an iterator that traverses statuses of the files/directories in the given path
    //测试场景
    //1.f 为文件夹
    //2.f 为文件
    public static void test(String path){
        String apiName = "listLocatedStatus(final Path f)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        Path directory = new Path(path);
        boolean checkDir = false;
        //boolean checkCreateDir = false;
        try{
            checkDir = dfs.isDirectory(directory);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(checkDir){
            logger.info("该文件夹已存在，请选择一个不存在的文件夹，我们将自动帮你创建");
            return;
        }
        logger.info("程序自动帮你创建文件夹开始.......");
        try {
            dfs.mkdirs(directory);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件夹失败，请检查");
            return;
        }

        if(Tool.checkDir(path)){
            logger.info("程序自动帮你创建文件夹成功.......");
            logger.info("程序自动帮你创建文件,请稍等.............");
        }else{
            logger.info("程序创建文件夹失败，请检查.......");
            return;
        }

        String file01 = path+"/test01.txt";
        String file02 = path+"/test02.txt";
        Path filePath = new Path(file01);
        Path filePath2 = new Path(file02);
        String fileName1 = filePath.getName();
        String fileName2 = filePath2.getName();
        Tool.createFile(file01);
        Tool.createFile(file02);

        if(Tool.checkFile(file01)&&Tool.checkFile(file02)){
            logger.info("程序自动帮你创建文件夹成功.......");
            logger.info("测试准备工作完成.......");
        }else{
            logger.info("程序自动帮你创建文件失败，请检查.......");
            return;
        }

        boolean checkDir1 = false;
        boolean checkDir2 = false;
        boolean checkFil = false;

        try{
            RemoteIterator<LocatedFileStatus> dir = dfs.listLocatedStatus(directory);
            RemoteIterator<LocatedFileStatus> f = dfs.listLocatedStatus(filePath);
            while(dir.hasNext()){
                LocatedFileStatus dirStatus = dir.next();
                Path dirPath = dirStatus.getPath();
                String pathName = dirPath.getName();
                logger.info("文件夹名称: "+pathName);
                if(pathName.equals(fileName1)){
                    checkDir1 = true;
                }
                if(path.equals(fileName2)){
                    checkDir2 = true;
                }
            }
            while(f.hasNext()){
                LocatedFileStatus fileStatus = f.next();
                Path Path = fileStatus.getPath();
                String pathName = Path.getName();
                logger.info("文件名称: "+pathName);
                if(pathName.equals(fileName1)){
                    checkFil = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info("获取信息失败，请检查");
            return;
        }

        logger.info("测试案例1：参数为文件夹目录，获得结果为文件夹下文件信息");
        if(checkDir1&&checkDir2){
            logger.info("文件信息显示正确，测试案例1 测试成功");
        }else{
            logger.info("文件信息显示错误，测试案例1 测试失败");
        }
        logger.info("测试案例2：参数为文件目录，获得结果为文件信息");
        if(checkFil){
            logger.info("文件信息显示正确，测试案例2 测试成功");
        }else{
            logger.info("文件信息显示错误，测试案例2 测试失败");
        }
    }


}
