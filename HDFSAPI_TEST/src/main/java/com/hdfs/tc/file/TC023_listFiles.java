package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC023_listFiles {
    private static Logger logger = LoggerFactory.getLogger(TC023_listFiles.class);

    //编号NO.23
    //测试API：listFiles(final Path f, final boolean recursive)
    //API说明：
    /*
     功能：List the statuses and block locations of the files in the given path.
     应用场景：
     1.If the path is a directory,
         1.1.if recursive is false, returns files in the directory;
         1.2.if recursive is true, return files in the subtree rooted at the path.
     2.If the path is a file, return the file's status and block locations.
     参数说明:
     1. f is the path
     2. recursive if the subdirectories need to be traversed recursively
     返回值说明：
     return an iterator that traverses statuses of the files
      @throws FileNotFoundException when the path does not exist;
     IOException see specific implementation
     */
    //备注：传入的path应该为文件夹！！
    /*测试场景：
    1.f 为文件夹，recursive is false, 则返回文件夹中文件
    2.f 为文件夹，recursive is true,  则返回文件夹中所有文件，包括子文件夹中文件
    3.f 为文件，recursive is false
    4.f 为文件，recursive is true
     */
    /*测试目录
     1. 文件夹/test01.txt
     2. 文件夹/test/test02.txt
     */
    public static void test(String path){
        String apiName = "listFiles(final Path f, final boolean recursive)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        String DirPath = path;
        String FilePath1 = "/test01.txt";
        String FilePath2 = "/test/test02.txt";
        boolean result = Tool.createDirAndFile(DirPath,FilePath1,FilePath2);
        if(!result){
            logger.info("创建文件夹与创建文件失败，请检查");
            return;
        }

        int result1 = 0;
        int result2 = 0 ;
        int result3 = 0;
        int result4 = 0;
        Path directory = new Path(DirPath);
        Path filePath  = new Path(DirPath+FilePath1);


        try {
            logger.info("测试用例1：f 为文件夹，recursive 为false........");
            RemoteIterator<LocatedFileStatus> list1 = dfs.listFiles(directory, false);
            while (list1.hasNext()) {
                result1++;
                list1.next();
            }

            logger.info("测试用例2：f 为文件夹，recursive 为true........");
            RemoteIterator<LocatedFileStatus> list2 = dfs.listFiles(directory, true);
            while (list2.hasNext()) {
                result2++;
                list2.next();
            }


            logger.info("测试用例3：f 为文件，recursive 为false........");
            RemoteIterator<LocatedFileStatus> list3 = dfs.listFiles(filePath, false);
            while (list3.hasNext()) {
                result3++;
                list3.next();
            }

            logger.info("测试用例4：f 为文件，recursive 为true........");
            RemoteIterator<LocatedFileStatus> list4 = dfs.listFiles(filePath, true);
            while (list4.hasNext()) {
                result4++;
                list4.next();
            }

        }catch(Exception e){
            e.printStackTrace();
            logger.info("调用该API 出现异常，请检查测试代码");
            return;
        }

        logger.info("测试结果汇总如下:");
        if(result1==1){
            logger.info("测试用例1：f 为文件夹，recursive 为false====通过 ");
        }else{
            logger.info("测试用例1：f 为文件夹，recursive 为false====失败");
            logger.info("实际结果为 "+result1);
        }

        if(result2==2){
            logger.info("测试用例2：f 为文件夹，recursive 为true====通过 ");
        }else{
            logger.info("测试用例2：f 为文件夹，recursive 为true====失败");
            logger.info("实际结果为 "+result2);
        }

        if(result3==1){
            logger.info("测试用例3：f 为文件，recursive 为false====通过 ");
        }else{
            logger.info("测试用例3：f 为文件，recursive 为false====失败");
            logger.info("实际结果为 "+result3);
        }

        if(result4==1){
            logger.info("测试用例4：f 为文件，recursive 为true====通过 ");
        }else{
            logger.info("测试用例4：f 为文件，recursive 为true====失败");
            logger.info("实际结果为 "+result4);
        }

        logger.info("测试 "+apiName+"结束......");
    }
}
