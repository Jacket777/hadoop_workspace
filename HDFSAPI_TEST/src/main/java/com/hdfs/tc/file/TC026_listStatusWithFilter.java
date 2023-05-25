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
public class TC026_listStatusWithFilter {
    private static Logger logger = LoggerFactory.getLogger(TC026_listStatusWithFilter.class);

    //编号NO.26
    //测试API: FileStatus[] listStatus(Path f, PathFilter filter)
    //功能说明：Filter files/directories in the given path using the user-supplied path filter.
    //参数说明：
    //1.f：a path name
    //2.filter the user-supplied path filter
    //返回值: an array of FileStatus objects for the files under the given path after applying the filter
    //异常说明：
    // 1.FileNotFoundException when the path does not exist;
    // 2.IOException see specific implementation
    public  static void test(String path){
        String apiName = "listStatus(Path f, PathFilter filter)";
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
            logger.info("测试用例1：文件存在，且PathFilter为true");
            PathFilter trueFilter =Tool.getTruePathFilter();
            FileStatus[] f = dfs.listStatus(filepath,trueFilter);
            if(f.length==0){
                logger.info("测试用例1 测试失败......");
            }else{
                logger.info("测试用例1 测试成功......");
                logger.info("该文件信息: "+f[0].toString()) ;
            }
            logger.info("测试用例2：文件存在，且PathFilter为false");
            PathFilter falseFilter =Tool.getFalsePathFilter();
            FileStatus[] f1 = dfs.listStatus(filepath,falseFilter);
            if(f1.length==0){
                logger.info("测试用例2 测试成功......");
            }else{
                logger.info("测试用例2 测试失败......");
                logger.info("该文件信息: "+f[0].toString()) ;
            }
        }catch(Exception e ){
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ") ;
        }

        logger.info("测试 "+apiName+"结束......");
    }
}
