package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC031_completeLocalOutput {
    private static Logger logger = LoggerFactory.getLogger(TC031_completeLocalOutput.class);

    //编号NO.31
    //测试API:completeLocalOutput(Path fsOutputFile, Path tmpLocalFile)
    //功能描述：复制 本地文件内容至HDFS系统文件中
    // Called when we're all done writing to the target.  A local FS will
    //do nothing, because we've written to exactly the right place.  A remote
    // FS will copy the contents of tmpLocalFile to the correct target at fsOutputFile.
    //测试用例描述:该用例在bigdata用户目录下创建文件，根据实际情况，这一目录将改变，请检查系统用户目录
    public static void test(String path){
        String apiName = "completeLocalOutput(Path fsOutputFile, Path tmpLocalFile)";
        logger.info("测试API: " + apiName + "开始......");
        logger.info("测试步骤1：在linux上创建文件,目录为/home/bigdata");
        String fileName = new Path(path).getName();
        boolean isSuccess = Tool.createLocalFile("bigdata",fileName);
        if(!isSuccess){
            logger.info("创建本地文件失败，请检查目录及其权限是否正确");
            return;
        }
        logger.info("测试步骤2：在HDFS上创建文件,文件目录即为控制台输入的目录");
        boolean isRight = Tool.createFile(path);
        if(!isRight){
            logger.info("创建文件失败，请检查目录及其权限是否正确");
            return;
        }

        logger.info("测试步骤3：复制本地文件内容到HDFS文件上");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path fsOutputFile = new Path(path);
        String fileInLocal = "/home/bigdata/"+fileName;
        Path tmpLocalFile = new Path(fileInLocal);
        try{
            dfs.completeLocalOutput(fsOutputFile,tmpLocalFile);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("API调用失败，请检查");
            return;
        }
        logger.info("测试步骤3：检查复制后HDFS上文件");
        String result = Tool.getContent(path);
        logger.info("读取文件内容为"+result);
        if(result.equals("helloWorld")){
            logger.info("该用例测试通过");
        }else{
            logger.info("该用例测试失败");
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
