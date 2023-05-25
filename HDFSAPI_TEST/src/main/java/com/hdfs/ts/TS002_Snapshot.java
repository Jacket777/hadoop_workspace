package com.hdfs.ts;

import com.hdfs.conf.TestParameter;
import com.hdfs.tc.snapshot.*;
import com.hdfs.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TS002_Snapshot {
    public static void test(String testDir){
        Logger logger = LoggerFactory.getLogger(TS002_Snapshot.class);
        logger.info("============执行快照功能测试用例======================");
        Tool.beginExe();
        logger.info("测试用例编号：N037");
        TC001_alllowSnapshot.test(testDir+ TestParameter.API37);
        logger.info("测试用例编号：N038");
        TC002_createSnapshot.test(testDir+TestParameter.API38);
        logger.info("测试用例编号：N039");
        TC003_renameSnapshot.test(testDir+TestParameter.API39);
        logger.info("测试用例编号：N040");
        TC004_delteSnapshot.test(testDir+TestParameter.API40);
        logger.info("测试用例编号：N041");
        TC005_disallowSnapshot.test(testDir+TestParameter.API41);
        logger.info("============快照用例执行完成=============================");
    }
}
