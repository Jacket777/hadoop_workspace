package com.hdfs.ts;

import com.hdfs.conf.TestParameter;
import com.hdfs.tc.coachepool.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2021/4/15.
 */
public class TS003_CoachPool {
    public static void test(){
        Logger logger = LoggerFactory.getLogger(TS003_CoachPool.class);
        logger.info("测试用例编号：N042");
        TC001_addCachePool.test(TestParameter.API42);
        logger.info("测试用例编号：N043");
        TC002_removeCachePool.test(TestParameter.API43);
        logger.info("测试用例编号：N044");
        TC003_listCachePools.test(TestParameter.API44);
        logger.info("测试用例编号：N045");
        TC004_modifyCachePool.test(TestParameter.API45);
    }
}