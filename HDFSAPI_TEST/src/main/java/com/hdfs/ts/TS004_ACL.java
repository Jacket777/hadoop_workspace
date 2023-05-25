package com.hdfs.ts;

import com.hdfs.conf.TestParameter;
import com.hdfs.tc.APITest;
import com.hdfs.tc.acl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/15.
 */
public class TS004_ACL {
    public static void test(String Dir){
        Logger logger = LoggerFactory.getLogger(TS004_ACL.class);
        logger.info("============执行权限功能测试用例=============================");
        logger.info("测试用例编号：N052");
        TC001_removeDefaultAcl.test(Dir+ TestParameter.API52);
        logger.info("测试用例编号：N053");
        TC002_removeAcl.test(Dir+TestParameter.API53);
        logger.info("测试用例编号：N054");
        TC003_removeAclEntries.test(Dir+TestParameter.API54);
        logger.info("测试用例编号：N055");
        TC004_modifyAclEntries.test(Dir+TestParameter.API55);
        logger.info("测试用例编号：N056");
        TC005_getDelegationToken.test();
    }
}
