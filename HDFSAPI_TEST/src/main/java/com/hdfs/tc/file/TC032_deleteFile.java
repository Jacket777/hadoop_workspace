package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC032_deleteFile {
    private static Logger logger = LoggerFactory.getLogger(TC032_deleteFile.class);

    //编号NO.32
    //测试API:delete(Path f, final boolean recursive)
    //功能描述：删除文件，设置是否递归删除
    //测试用例描述:1.先创建文件夹，后创建文件，再创建文件夹下的文件
    public static void test(String path){
        String apiName = "delete(Path f, final boolean recursive)";
        logger.info("测试API: " + apiName + "开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        logger.info("测试用例1：文件夹下无文件，递归删除为false，文件夹被成功删除");
        boolean IsSuccess = Tool.createDir(path);
        if(IsSuccess){
            try{
                Path path1 = new Path(path);
                dfs.delete(path1,false);
            }catch(Exception e){
                logger.info("删除出现异常，请检查");
                e.printStackTrace();
            }
        }else{
            return ;
        }

        //检查测试用例1结果
        boolean result1 = Tool.checkDir(path);
        if(result1){
            logger.info("测试用例1：执行失败");
        }else{
            logger.info("测试用例1：执行成功");
        }

        logger.info("测试用例2：文件夹下有文件，递归删除为false，删除出现异常");

        logger.info("测试步骤1：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }
        Path path1 = new Path(path);
        Path path2 = new Path(path+file1);
        Path path3 = new Path(path+file2);

        try{
            dfs.delete(path1,false);
        }catch(Exception e){
            logger.info("测试用例2：测试通过");
            e.printStackTrace();
        }
        if(Tool.checkDir(path)&&Tool.checkFile(path+file2)&&(Tool.checkFile(path+file1))){
            logger.info("测试用例2：递归删除为false====>通过");
        }else{
            logger.info("测试用例2：递归删除为false====>失败");
        }

        logger.info("测试用例3：文件夹下有文件，递归删除为true，文件夹与文件都将删除成功");

        String newPath = path+"TEST";
        logger.info("测试步骤1：创建文件和文件夹");
        boolean Iscreate = Tool.createDirAndFile(newPath,file1,file2);
        if(!Iscreate){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        DistributedFileSystem dfs1 = Tool.getDistributedFileSystem();
        if(dfs1==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path deletePath = new Path(newPath);
        try{
            dfs1.delete(deletePath,true);
        }catch(Exception e){
            logger.info("删除出现异常，请检查");
            e.printStackTrace();
        }
        if(!Tool.checkFile(newPath+file2)&&(!Tool.checkFile(newPath+file1))&&(!Tool.checkDir(newPath))){
            logger.info("测试用例3：递归删除为true====>通过");
        }else{
            logger.info("测试用例3：递归删除为true====>失败");
        }

        logger.info("测试API: " + apiName + "结束......");
    }
}
