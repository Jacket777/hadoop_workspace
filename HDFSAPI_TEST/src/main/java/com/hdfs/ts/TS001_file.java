package com.hdfs.ts;

import com.hdfs.conf.TestParameter;
import com.hdfs.tc.file.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 17081290 on 2018/1/12
 * 文件相关API
 */
public class TS001_file {
    public static void test(String Dir){
        Logger logger = LoggerFactory.getLogger(TS001_file.class);
        logger.info("测试用例编号：N01");
        TC001_mkdirs.test(Dir+ TestParameter.API01);
        logger.info("测试用例编号：N02");
        TC002_mkdirsWithPermission.test(Dir+TestParameter.API02);
        logger.info("测试用例编号：N03");
        TC003_mkdir.test(Dir+TestParameter.API03);
        logger.info("测试用例编号：N04");
        TC004_createFile.test(Dir+TestParameter.API04);
        logger.info("测试用例编号：N05");
        TC005_createFileWithBoolean.test(Dir+TestParameter.API05);
        logger.info("测试用例编号：N06");
        TC006_createFileWithProgressable.test(Dir+TestParameter.API06);
        logger.info("测试用例编号：N07");
        TC007_createFileWithReplication.test(Dir+TestParameter.API07);
        logger.info("测试用例编号：N08");
        TC008_createFileWithReplicationAndProgressable.test(Dir+TestParameter.API08);
        logger.info("测试用例编号：N09");
        TC009_createFileWithBuffersize.test(Dir+TestParameter.API09);
        logger.info("测试用例编号：N10");
        TC010_createFileWithBuffsizeAndProgressable.test(Dir+TestParameter.API10);
        logger.info("测试用例编号：N11");
        TC011_createFileWithBlock.test(Dir+TestParameter.API11);
        logger.info("测试用例编号：N12");
        TC012_createFileWithMore.test(Dir+TestParameter.API12);
        logger.info("测试用例编号：N13");
        TC013_createFileWithFlag.test(Dir+TestParameter.API13);
        logger.info("测试用例编号：N14");
        TC014_createFileWithFlagAndBuffersize.test(Dir+TestParameter.API14);
        logger.info("测试用例编号：N15");
        TC015_createNewFile.test(Dir+TestParameter.API15);
        logger.info("测试用例编号：N16");
        TC016_isFile.test(Dir+TestParameter.API16);
        logger.info("测试用例编号：N17");
        TC017_openFile.test(Dir+TestParameter.API17);
        logger.info("测试用例编号：N18");
        TC018_openFileWithBufferSize.test(Dir+TestParameter.API18);
        logger.info("测试用例编号：N19");
        TC019_appendFile.test(Dir+TestParameter.API19);
        logger.info("测试用例编号：N20");
        TC020_appendFileWithbufferSize.test(Dir+TestParameter.API20);
        logger.info("测试用例编号：N21");
        TC021_appendFileWithProgress.test(Dir+TestParameter.API21);
        logger.info("测试用例编号：N22");
        TC022_exists.test(Dir+TestParameter.API22);
        logger.info("测试用例编号：N23");
        TC023_listFiles.test(Dir+TestParameter.API23);
        logger.info("测试用例编号：N24");
        TC024_listLocatedStatus.test(Dir+TestParameter.API24);
        logger.info("测试用例编号：N25");
        TC025_listStatus.test(Dir+TestParameter.API25);
        logger.info("测试用例编号：N26");
        TC026_listStatusWithFilter.test(Dir+TestParameter.API26);
        logger.info("测试用例编号：N27");
        TC027_listStatusWithFiles.test(Dir+TestParameter.API27);
        logger.info("测试用例编号：N28");
        TC028_listStatusWithFilesAndFilter.test(Dir+TestParameter.API28);
        logger.info("测试用例编号：N29");
        TC029_isInSafeMode.test();
        logger.info("测试用例编号：N30");
        TC030_moveToLocalFile.test(Dir+TestParameter.API30);
        logger.info("测试用例编号：N31");
        TC031_completeLocalOutput.test(Dir+TestParameter.API31);
        logger.info("测试用例编号：N32");
        TC032_deleteFile.test(Dir+TestParameter.API32);
        logger.info("测试用例编号：N33");
        TC033_resolvePath.test(Dir+TestParameter.API33);
        TC034_makeQualified.test(Dir+TestParameter.API34);
        logger.info("测试用例编号：N035");
        TC035_globStatus.test(Dir+TestParameter.API35);
        logger.info("测试用例编号：N036");
        TC036_globStatusWithFilter.test(Dir+TestParameter.API36);
        logger.info("测试用例编号：N057");
        TC037_isDirectory.test(Dir+TestParameter.API57);
    }
}
