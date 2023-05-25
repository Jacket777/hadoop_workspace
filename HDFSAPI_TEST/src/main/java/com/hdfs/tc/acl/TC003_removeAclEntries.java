package com.hdfs.tc.acl;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by 17081290 on 2021/4/14.
 */
public class TC003_removeAclEntries {
    private static Logger logger = LoggerFactory.getLogger(TC003_removeAclEntries.class);

    //编号:NO.54
    //测试API：removeAclEntries(Path path, final List<AclEntry> aclSpec)
    //功能：移除ACL
    //测试用例描述
    public static void test(String filePath){
        String APIName = "removeAclEntries(Path path, final List<AclEntry> aclSpec)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备1：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;}

        logger.info("测试准备2：创建文件");
        boolean checkFile = Tool.checkFile(filePath);
        if(checkFile){
            logger.info("文件已经存在，请选择其他文件名");
            return;}

        boolean isSuccess = Tool.createFile(filePath);
        if(!isSuccess){
            logger.info("文件创建失败，请检查");
            return;}

        Path path = new Path(filePath);
        AclStatus aclStatus = null;
        List<AclEntry> aclList = Tool.getNewACLlist();
        try{
            dfs.modifyAclEntries(path, aclList);
            logger.info("=====================remove=============================");
            dfs.removeAclEntries(path, aclList);
            aclStatus = dfs.getAclStatus(path);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("移除时发生异常，请检查");
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
        for(int i = 0;i <aclEntries.size();i++){
            AclEntry aclEntry = aclEntries.get(i);
            aclEntryName = aclEntry.getName();
            logger.info("aclEntry name1 "+ aclEntryName);
            FsAction permission = aclEntry.getPermission();
            permissionName  = permission.name();
            logger.info("permission name1: "+permissionName);
            AclEntryScope scope = aclEntry.getScope();
            scopeName = scope.name();
            logger.info("scope name1: "+ scopeName);
            AclEntryType type =  aclEntry.getType();
            typeName=type.name();
            logger.info("typeName1: "+ typeName);
            if((aclEntryName!=null)&&aclEntryName.equals("test")&&permissionName.equals("READ")&&scopeName.equals("ACCESS")&&typeName.equals("USER")){
                testResult = true;
                break;
            }
            logger.info("=============================================");
        }

        if(!testResult){
            logger.info("移除成功,测试用例执行成功");
        }else{
            logger.info("移除失败,测试用例执行失败");
        }

        logger.info("测试API: " + APIName + "结束......");
    }
}
