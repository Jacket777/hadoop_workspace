package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC036_globStatusWithFilter {
    private static Logger logger = LoggerFactory.getLogger(TC036_globStatusWithFilter.class);

    //编号NO.36
    //测试API:FileStatus[] globStatus(Path pathPattern, PathFilter filter)
    //功能描述：
    // Return an array of FileStatus objects whose path names match pathPattern
    //and is accepted by the user-supplied path filter. Results are sorted by their path names.
    // Return null if pathPattern has no glob and the path does not exist.
    // Return an empty array if pathPattern has a glob and no path matches it.
    //参数描述:
    //@param pathPattern a regular expression specifying the path pattern
    //@param filter a user-supplied path filter
    //返回值：
    //return an array of FileStatus objects
    //异常说明:
    //throws IOException if any I/O error occurs when fetching file status
    //测试用例描述：
    public static void test(String path){
        String apiName = "globStatus(Path pathPattern, PathFilter filter)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";

        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.warn("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.warn("获取dfs失败，请检查");
            return;
        }

        logger.info("测试用例1:pathPattern匹配文件夹下的文件，pathFile 为true,返回值为 FileStatus数组");
        String PathPattern1 =  path+"/*";
        Path path1 =  new Path(PathPattern1);
        PathFilter pathFilter1 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return true;
            }
        };
        FileStatus[] result1 = null;
        try{
            result1= dfs.globStatus(path1,pathFilter1);
        }catch (Exception e){
            e.printStackTrace();
            logger.warn("测试用例1异常，请检查");
        }
        if(result1 == null){
            logger.info("测试用例1:失败，请检查");
        }else{
            logger.info("测试用例1:成功,信息如下：");
            logger.info("文件信息汇总如下：");
            int i  = result1.length;
            logger.info("共计："+i+" 个信息");
            for(int j = 0; j< i; j++){
                FileStatus fs = result1[j];
                logger.info(fs.toString());
            }
        }

        logger.info("测试用例2:pathPattern不能匹配文件夹下的文件，pathFile 为false,返回值为null");
        String PathPattern2 =  path+"/aa";
        Path path2 =  new Path(PathPattern2);
        PathFilter pathFilter2 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };
        FileStatus[] result2 = null;
        try{
            result2= dfs.globStatus(path2,pathFilter2);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("测试用例2异常，请检查");

        }
        if(result2 == null){
            logger.info("测试用例2:成功，请检查");
        }else{
            logger.info("测试用例2:失败,请检查");
        }


        logger.info("测试用例3:pathPattern能匹配文件夹下的文件，pathFile 为false,返回值为空FileStatus数组");
        String PathPattern3 =  path+"/*";
        Path path3 =  new Path(PathPattern3);
        PathFilter pathFilter3 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };
        FileStatus[] result3 = null;
        try{
            result3= dfs.globStatus(path3,pathFilter3);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("测试用例3异常，请检查");

        }
        if(result3 == null){
            logger.info("测试用例3:失败，请检查");
        }else{
            logger.info("测试用例3:成功,请检查");
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
