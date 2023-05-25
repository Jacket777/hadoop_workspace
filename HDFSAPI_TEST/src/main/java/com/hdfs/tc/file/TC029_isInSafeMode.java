package com.hdfs.tc.file;

import com.hdfs.tools.Tool;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC029_isInSafeMode {
    private static Logger logger = LoggerFactory.getLogger(TC029_isInSafeMode.class);

    //编号NO.29
    //测试API：isInSafeMode()
    //功能说明：检查是否处于安全模式，默认为不是安全模式
    public static void test(){
        String apiName =  "isInSafeMode()";
        boolean result = true;
        logger.info("测试API: " + apiName + "开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }

        try{
            result = dfs.isInSafeMode();
        }catch(Exception e){
            e.printStackTrace();
            logger.info("获取安全模式失败，请检查");
        }
        logger.info("测试结果如下：");
        if(result){
            logger.info("测试用例失败，节点处于安全模式中");
        }else{
            logger.info("测试用例成功，节点不处于安全模式中");
        }
        logger.info("测试API: " + apiName + "结束......");
    }

}
