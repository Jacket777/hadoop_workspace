package com.hdfs.tc.acl;

import com.hdfs.tools.Tool;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC005_getDelegationToken {
    private static Logger logger = LoggerFactory.getLogger(TC005_getDelegationToken.class);
    //编号:NO.56
    //测试API：getDelegationToken()
    //功能：获取空Token
    //测试用例描述
    public static void test(){
        String APIName = "getDelegationToken";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备1：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取dfs失败，请检查");
            return;}

        try {
            dfs.getDelegationToken("");
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("测试API: " + APIName + "结束......");
    }

}
