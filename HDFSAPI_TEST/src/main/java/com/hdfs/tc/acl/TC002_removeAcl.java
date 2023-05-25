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
public class TC002_removeAcl {
    private static Logger logger = LoggerFactory.getLogger(TC002_removeAcl.class);

    //编号:NO.53
    //测试API: removeAcl(Path path)
    //功能描述：移除文件ACL状态
    //测试用例描述
    public static void test(String path){
        String APIName = "removeAcl(Path path)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备1：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;}
        logger.info("测试准备2：创建文件夹与文件");
        String fileName1 = "/test1.txt";
        String fileName2 ="/test2.txt";
        boolean result = Tool.createDirAndFile(path,fileName1,fileName2);
        if(!result){
            logger.info("创建文件夹与文件失败，请检查");
            return;
        }
        Path Direct = new Path(path);
        List<AclEntry> aclList = Tool.getNewACLlist();
        AclStatus aclStatus = null;
        try{
            dfs.modifyAclEntries(Direct, aclList);
            dfs.removeAcl(Direct);
            aclStatus = dfs.getAclStatus(Direct);
        }catch(Exception e){
            e.printStackTrace();
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
            logger.info("aclEntry name2 "+ aclEntryName);
            FsAction permission = aclEntry.getPermission();
            permissionName  = permission.name();
            logger.info("permission name2: "+permissionName);
            AclEntryScope scope = aclEntry.getScope();
            scopeName = scope.name();
            logger.info("scope name2: "+ scopeName);
            AclEntryType type =  aclEntry.getType();
            typeName=type.name();
            logger.info("typeName2: "+ typeName);
            if((aclEntryName!=null)&&aclEntryName.equals("test")&&permissionName.equals("READ")&&scopeName.equals("ACCESS")&&typeName.equals("USER")){
                testResult = true;
                break;
            }
            logger.info("=============================================");
        }

        if(!testResult){
            logger.info("删除成功,测试用例执行成功");
        }else{
            logger.info("删除失败,测试用例执行失败");
        }

        logger.info("测试API: " + APIName + "结束......");
    }

}
