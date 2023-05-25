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
public class TC027_listStatusWithFiles {
    private static Logger logger = LoggerFactory.getLogger(TC027_listStatusWithFiles.class);

    //编号NO.27
    //测试API:listStatus(Path[] files)
    //功能说明：Filter files/directories in the given list of paths using defaultpath filter.
    //参数说明：
    //1.files :a list of paths
    //返回值：a list of statuses for the files under the given paths after applying the filter default Path filter
    //异常说明:
    //1. FileNotFoundException when the path does not exist;
    //2. IOException see specific implementation
    public static void test(String path) {
        String apiName = "listStatus(Path[] files)";
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

        FileStatus[] f = null;
        try {
            f = dfs.listStatus(paths);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ");
        }
        logger.info("测试结果如下： ");
        if (f.length == 2) {
            logger.info("测试用例测试通过.........");
            logger.info("第一个文件信息如下:  " + f[0].toString());
            logger.info("第二个文件信息如下:  " + f[1].toString());
        } else {
            logger.info("测试用例测试失败.........");
        }
        logger.info("测试API: " + apiName + "开始......");
    }

}
