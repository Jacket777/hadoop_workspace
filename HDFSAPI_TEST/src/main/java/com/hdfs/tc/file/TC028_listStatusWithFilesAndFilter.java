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
public class TC028_listStatusWithFilesAndFilter {
    private static Logger logger = LoggerFactory.getLogger(TC028_listStatusWithFilesAndFilter.class);

    //编号NO.28
    //测试API：FileStatus[] listStatus(Path[] files, PathFilter filter)
    //功能说明：Filter files/directories in the given list of paths using user-supplied path filter.
    //参数说明:
    // 1.files:a list of paths
    // 2.filter:the user-supplied path filter
    //返回值说明：a list of statuses for the files under the given paths after applying the filter
    // 异常说明 FileNotFoundException when the path does not exist;IOException see specific implementation
    public static void test(String path) {
        String apiName = "listStatus(Path[] files, PathFilter filter)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        boolean precondition = Tool.createDirAndFile(path, file1, file2);
        if (!precondition) {
            logger.info("创建文件夹与文件失败，请检查......");
            return;
        }
        file1 = path + file1;
        file2 = path + file2;
        Path path1 = new Path(file1);
        Path path2 = new Path(file2);
        Path[] paths = new Path[]{path1, path2};
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }

        FileStatus[] f1 = null;
        FileStatus[] f2 = null;

        try {
            logger.info("测试用例1： pathFilter 为 true");
            PathFilter truePathFilter = Tool.getTruePathFilter();
            f1 = dfs.listStatus(paths, truePathFilter);
            logger.info("测试用例1： pathFilter 为 false");
            PathFilter falsePathFilter = Tool.getFalsePathFilter();
            f2 = dfs.listStatus(paths, falsePathFilter);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ");
            return;
        }

        logger.info("测试结果如下...... ");
        if (f1.length == 2) {
            logger.info("测试用例1： pathFilter 为 true===>测试通过");
        } else {
            logger.info("测试用例1： pathFilter 为 true===>测试不通过");
        }

        if (f2.length == 0) {
            logger.info("测试用例2： pathFilter 为 false===>测试通过");
        } else {
            logger.info("测试用例2： pathFilter 为 false===>测试不通过");
        }

        logger.info("测试API: " + apiName + "结束......");
    }

}
