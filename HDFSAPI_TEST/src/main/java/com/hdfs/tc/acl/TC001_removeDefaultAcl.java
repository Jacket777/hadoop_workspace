package com.hdfs.tc.acl;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.AclStatus;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC001_removeDefaultAcl {
    private static Logger logger = LoggerFactory.getLogger(TC001_removeDefaultAcl.class);

    //编号:NO.52
    //测试API:removeDefaultAcl(Path path)
    //功能描述：移除文件默认ACL状态
    //测试用例描述,传入参数应为文件夹，只有文件夹才有default ACL,可以不先设置ACL
    public static void test(String DirPath){
        String APIName = "removeDefaultAcl(Path path)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备1：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;}

        logger.info("测试准备2：创建文件夹与文件");
        String fileName1 = "/test1.txt";
        String fileName2 ="/test2.txt";
        boolean result = Tool.createDirAndFile(DirPath,fileName1,fileName2);
        if(!result){
            logger.info("创建文件夹与文件失败，请检查");
            return;
        }
        Path Direct = new Path(DirPath);
        AclStatus aclStatus = null;
        List<AclEntry> aclList = Tool.getDefaultACLList();

        try{
            dfs.modifyAclEntries(Direct,aclList);
            AclStatus aclStatus0 = dfs.getAclStatus(Direct);
            Tool.getACLInfo(aclStatus0);
            dfs.removeDefaultAcl(Direct);
            aclStatus = dfs.getAclStatus(Direct);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("测试执行时发生异常，请检查");
            return;
        }

        if(aclStatus == null){
            logger.info("获取aclStatus 对象为空，请检查");
            return;
        }


        logger.info("The owner is "+aclStatus.getOwner());
        logger.info("The group is "+aclStatus.getGroup());

        String aclEntryName="";
        String permissionName ="";
        String scopeName ="";
        String typeName ="";
        boolean testResult = false;

        List<AclEntry >aclEntries = aclStatus.getEntries();
        int size = aclEntries.size();

        if(size == 0){
            logger.info("the aclEntries size 为 0");
            logger.info("移除默认ACL,测试用例执行成功");
            return;
        }else {
            for (int i = 0; i < aclEntries.size(); i++) {
                AclEntry aclEntry = aclEntries.get(i);
                aclEntryName = aclEntry.getName();
                logger.info("aclEntry name is " + aclEntryName);
                permissionName = aclEntry.getPermission().name();
                logger.info("permission name is : " + permissionName);
                scopeName = aclEntry.getScope().name();
                logger.info("scope name is: " + scopeName);
                typeName = aclEntry.getType().name();
                logger.info("typeName is: " + typeName);
                if ((aclEntryName != null) && aclEntryName.equals("test") && permissionName.equals("READ") && scopeName.equals("DEFAULT") && typeName.equals("USER")) {
                    testResult = true;
                    break;
                }
                logger.info("=============================================");
            }
        }

        if(!testResult){
            logger.info("移除默认ACL,测试用例执行成功");
        }else{
            logger.info("移除默认ACL,测试用例执行失败");
        }


        logger.info("测试API: " + APIName + "结束......");
    }
}
