package com.hdfs.tc;

import com.hdfs.tools.Tool;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.*;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by 17081290 on 2021/4/13.
 */
public class APITest {
    private static Logger logger = LoggerFactory.getLogger(APITest .class);

    //编号NO.1
    //测试API:mkdirs(Path path)  path为文件夹全路径
    public static void testMKdirs(String path) {
        String APIName = "mkdirs(Path path)";
        logger.info("测试创建文件API "+APIName+"开始.........");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查目录设置是否正确");
        if (Tool.checkIsPath(path)) {
            logger.info("该目录已经存在，请输入其他目录");
            return;
        }else{
            logger.info("该目录不存在，API将新文件夹");
        }

        try {
            dfs.mkdirs(new Path(path));
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("创建文件时出现异常");
        } finally {
            logger.info("创建文件夹结束");
        }
        //判断文件夹是否创建成功
        Tool.checkDirResult(path);
        logger.info("测试创建文件API "+APIName+"结束.........");
    }



    //编号NO.2
    //测试API:mkdirs(Path path, FsPermission fsPermission)
    public static void testMKdirsWithP(String path) {
        String APIName = "mkdirs(Path path,FsPermission fsPermission)";
        logger.info("测试创建文件API "+APIName+"开始.........");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查目录设置是否正确");
        if (Tool.checkIsPath(path)) {
            logger.info("该目录已经存在，请输入其他目录");
            return;
        }else{
            logger.info("该目录不存在，API将新文件夹");
        }

        try {
            FsPermission fsPermisson = FsPermission.getDefault();
            Path dir = new Path(path);
            dfs.mkdirs(dir, fsPermisson);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("创建文件夹出现异常，请检查");
        } finally {
            logger.info("创建文件夹结束");
        }

        //判断文件夹是否创建成功
        Tool.checkDirResult(path);
        logger.info("测试创建文件API "+APIName+"结束.........");
    }

    //编号NO.3
    //测试API: mkdir(Path path, FsPermission fsPermission) /demox 根文件夹
    public static void testMKdirWithP(String path) {
        String APIName = "mkdir(Path path, FsPermission fsPermission)";
        logger.info("测试创建文件API "+APIName+"开始.........");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }

        logger.info("测试准备 2:检查目录设置是否正确");
        if (Tool.checkIsPath(path)) {
            logger.info("该目录已经存在，请输入其他目录");
            return;
        }else{
            logger.info("该目录不存在，API将新文件夹");
        }

        try {
            FsPermission fsPermisson = FsPermission.getDefault();
            Path dir = new Path(path);
            dfs.mkdir(dir, fsPermisson);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("创建文件夹时 出现异常");
        } finally {
            logger.info("创建文件夹结束");
        }
        //判断文件夹是否创建成功
        Tool.checkDirResult(path);
        logger.info("测试创建文件API "+APIName+"结束.........");
    }

    //编号NO.4
    //测试API: create(Path path)
    //1.原文件不存在，则新建文件
    //2.原文件已存在，则重写该文件
    public static void testCreateFile(String path) {
        String APIName = "create(Path path)";
        logger.info("测试创建文件API "+APIName+"开始.........");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        if (Tool.checkFile(path)) {
            logger.info("该文件已经存在，请重新创建文件........");
            return;
        }
        Path filepath = new Path(path);
        logger.info("测试案例 1：文件不存在，创建新文件");
        String content = "Hello";
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("创建DFS对象或创建文件时出现异常");
        } finally {
            logger.info("创建文件结束");
        }
        //检查创建文件结果
        logger.info("检查测试案例执行结果");
        String realContent= Tool.getContent(path);
        logger.info("文件内容为 : "+realContent);
        if(Tool.checkFile(path)&&(realContent!=null)&&(realContent.equals(content))){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例 1 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例 2：文件存在，重写新文件");
        String content2 = "World";
        if (!Tool.checkFile(path)) {
            logger.info("该文件不存在，不满足测试条件，请检查测试代码");
            return;
        }

        try {
            FSDataOutputStream outputStream =  dfs.create(filepath);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (IOException e) {
            logger.info("重写文件时出现异常");
            e.printStackTrace();
        } finally {
            logger.info("创建文件结束");
        }
        //检查创建文件结果
        logger.info("检查测试案例执行结果");
        String realContent2= Tool.getContent(path);
        logger.info("文件内容为 : "+realContent2);
        if(Tool.checkFile(path)&&(realContent2!=null)&&(realContent2.equals(content2))){
            logger.info("测试用例 2 执行成功，测试通过");
        }else{
            logger.info("测试用例 2 执行失败，测试不通过");
        }
        logger.info("测试创建文件API "+APIName+"结束.........");
    }

    //编号NO.5
    //测试API: create(Path path Boolean istrue)
    //测试场景
    //1.创建的文件不存在时，则是否重写布尔值设置成true，false 则效果一致，都是创建新文件
    //2.创建的文件已经存在时，设置布尔值为true,则该文件被重写
    //3.创建的文件存在时，设置布尔值为false,则抛出异常
    public static void testCreateFile1(String path) {
        String APIName = "create(Path path,Boolean )";
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
        String testfile2 = path+"/test2.txt";
        logger.info("测试创建文件API: "+APIName+"开始......");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }
        logger.info("测试案例1 ：文件不存在，且overwrite 为false");
        boolean overwrite1 = false;
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, overwrite1);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件 出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在,2.内容正确
        String realContent= Tool.getContent(testfile1);
        logger.info("文件内容为 : "+realContent);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件不存在，且overwrite 为true");
        boolean overwrite2 = true;
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2, overwrite2);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时 出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))){
            logger.info("测试用例2  执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例3 ：文件存在，且overwrite 为true");
        boolean overwrite3 = true;
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath3, overwrite3);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent3= Tool.getContent(testfile1);
        if((realContent3!=null)&&(realContent3.equals(content2))){
            logger.info("测试用例3  执行成功，测试通过");
            logger.info("文件内容为: "+realContent3);
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例4 ：文件存在，且overwrite 为false,将抛出异常");
        boolean overwrite4 = false;
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, overwrite4);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            logger.info("测试用例4 执行成功");
            e.printStackTrace();
            return;
        }
        logger.info("测试用例4 执行失败");
        logger.info("测试创建文件"+APIName+"结束......");
    }

    //编号NO.6
    //测试API: create(Path f,Progressable prgress) //测试创建文件，且展示进度条
    //测试场景：
    //(1).如果文件不存在，则创建新文件
    //(2).如果文件存在，则重写该文件
    //备注： Progressable 方法在创建文件后写入数据时调用
    public static void testCreateFileWithProgress(String path) {
        String APIName = "create(Path f,Progressable progress)";
        logger.info("测试创建文件 API:"+APIName+" 开始......");
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
        String testfile2 = path+"/test2.txt";
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }

        logger.info("测试准备 3:获得Progressable 对象");
        Progressable progressable = Tool.getProgressable();
        if(progressable == null){
            logger.info("获取progreesable失败，请检查");
            return;
        }

        logger.info("测试案例 1：该文件不存在，且创建文件时不写入数据，则无进度条显示，请注意进度条");
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, progressable);
        } catch (Exception e) {
            logger.info("创建文件 出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在
        if(Tool.checkFile(testfile1)){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例 2：该文件不存在，且创建文件时写入数据，则进度条显示，请注意进度条");
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))){
            logger.info("测试用例2  执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例 3：该文件存在，且重写文件时不写入数据，则无进度条显示，请注意进度条");
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            dfs.create(filepath3,progressable);
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent3= Tool.getContent(testfile1);
        logger.info("文件内容为: "+realContent3);
        if((realContent2==null)||(realContent3.equals(""))){
            logger.info("测试用例3  执行成功，测试通过");
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例 4：该文件存在，且重写文件时写入数据，则进度条显示，请注意进度条");
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            e.printStackTrace();
        }
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent4= Tool.getContent(testfile2);
        logger.info("文件内容为: "+realContent4);
        if((realContent4!=null)&&(realContent4.equals(""))){
            logger.info("测试用例4  执行成功，测试通过");
        }else{
            logger.info("测试用例4 执行失败，测试不通过");
        }

        logger.info("测试创建文件 API:"+APIName+" 结束......");
    }

    //编号NO.7
    //测试API:create(Path f,Short repliaction) //创建文件，同时设置副本数的API
    //测试场景：
    //(1).如果文件不存在，则创建新文件
    //(2).如果文件存在，则重写该文件，默认为重写文件
    //备注：副本数类型为short类型
    public static void testCreateFileReplic(String path) {
        String APIName = "create(Path f,Short repliaction)";
        logger.info("测试创建文件API :"+ APIName+"开始......");
        short repliaction = 4;
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }

        logger.info("测试案例1 ：文件不存在，设置的副本数为4");
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, repliaction);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查3点：1.文件是否存在,2.内容正确,3.副本数正确
        String realContent= Tool.getContent(testfile1);
        int  realReplication = Tool.getFileReplication(filepath1);
        logger.info("文件内容为 : "+realContent);
        logger.info("副本数 : "+realReplication);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))&&(realReplication==4)){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件存在，则重写该文件");
        String content2 = "World";
        boolean isFileExist = Tool.checkFile(testfile1);
        if(!isFileExist){
            logger.info("该文件不存在，测试条件不满足，请检查测试代码");
            return;
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath1, repliaction);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常！请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent2= Tool.getContent(testfile1);
        int  realReplication2 = Tool.getFileReplication(filepath1);
        logger.info("文件内容为: "+realContent2);
        logger.info("副本数 : "+realReplication2);
        if(Tool.checkFile(testfile1)&&(realContent2!=null)&&(realContent2.equals(content2))&&(realReplication2==4)){
            logger.info("测试用例2  执行成功，测试通过");
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("测试创建文件API:create(Path f,Short repliaction) 结束......");
    }

    //编号NO.8
    //测试API: create(Path f, short replication,Progressable progress)
    //测试场景：
    //(1).如果文件不存在，则创建新文件
    //(2).如果文件存在，则重写该文件，默认为重写文件
    public static void testCreatFileWithRP(String path) {
        String APIName = "create(Path f, short replication,Progressable progress)";
        logger.info("测试创建文件API :"+APIName+"开始......");
        logger.info("请注意执行进度条，进度条为... ");
        short repliaction = 4;
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }
        logger.info("测试准备 3:获得Progressable 对象");
        Progressable progressable = Tool.getProgressable();
        if(progressable == null){
            logger.info("获取progreesable失败，请检查");
            return;
        }

        logger.info("测试案例1 ：文件不存在，设置的副本数为4");
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, repliaction, progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查3点：1.文件是否存在,2.内容正确,3.副本数正确
        String realContent= Tool.getContent(testfile1);
        int  realReplication = Tool.getFileReplication(filepath1);
        logger.info("文件内容为 : "+realContent);
        logger.info("副本数 : "+realReplication);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))&&(realReplication==4)){
            logger.info("测试用例 1 执行成功，测试通过");
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件存在，则重写该文件");
        String content2 = "World";
        boolean isFileExist = Tool.checkFile(testfile1);
        if(!isFileExist){
            logger.info("该文件不存在，测试条件不满足，请检查测试代码");
            return;
        }

        try {
            FSDataOutputStream outputStream =  dfs.create(filepath1, repliaction, progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常！请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent2= Tool.getContent(testfile1);
        int  realReplication2 = Tool.getFileReplication(filepath1);
        logger.info("文件内容为: "+realContent2);
        logger.info("副本数 : "+realReplication2);
        if(Tool.checkFile(testfile1)&&(realContent2!=null)&&(realContent2.equals(content2))&&(realReplication2==4)){
            logger.info("测试用例2  执行成功，测试通过");
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("测试创建文件API:"+APIName+" 结束......");
    }

    //编号NO.9
    //测试API：public FSDataOutputStream create(Path f,boolean overwrite,int bufferSize)
    //API 参数说明
    //1.f:the file name to create
    //2. overwrite  if a file with this name already exists, then if true,the file will be overwritten, and if false an error will be thrown.
    //3.bufferSize the size of the buffer to be used.
    //测试场景：
    //1.文件不存在，则创建文件，则overwrite 为true，创建文件
    //2.文件不存在，则创建文件，则overwrite 为false，创建文件
    //3.文件存在，overwrite 为true，则重写文件
    //4.文件存在，overwrite 为false，则抛出异常错误
    //备注：传入的参数为文件夹目录
    public static void testCreateFileWithSize(String path) {
        int bufferSize = 1024;
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
        String testfile2 = path+"/test2.txt";
        String APIName = "create(Path f,boolean overwrite,int bufferSize)";
        logger.info("测试创建文件API: "+APIName+"开始......");
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }

        logger.info("测试案例1 ：文件不存在，且overwrite 为false");
        boolean overwrite1 = false;
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, overwrite1, bufferSize);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件 出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在,2.内容正确
        String realContent= Tool.getContent(testfile1);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))){
            logger.info("测试用例 1 执行成功，测试通过");
            logger.info("文件内容为 : "+realContent);
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件不存在，且overwrite 为true");
        boolean overwrite2 = true;
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2, overwrite2, bufferSize);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确

        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))){
            logger.info("测试用例2  执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例3 ：文件存在，且overwrite 为true");
        boolean overwrite3 = true;
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath3, overwrite3, bufferSize);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent3= Tool.getContent(testfile1);
        if((realContent2!=null)&&(realContent3.equals(content2))){
            logger.info("测试用例3  执行成功，测试通过");
            logger.info("文件内容为: "+realContent3);
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例4 ：文件存在，且overwrite 为false,将抛出异常");
        boolean overwrite4 = false;
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, overwrite4, bufferSize);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            logger.info("测试用例4 执行成功");
            e.printStackTrace();
            return;
        }
        logger.info("测试用例4 执行失败");
        logger.info("测试创建文件"+APIName+"结束......");
    }


    //编号NO.10
    //测试API: create(Path f,boolean overwrite,int bufferSize,Progressable progress)
    //参数说明
    //1. f,文件路径, overwrite 是否重写， buffsizes
    //测试场景
    //1.文件不存在，则创建文件，则overwrite 为true，创建文件
    //2.文件不存在，则创建文件，则overwrite 为false，创建文件
    //3.文件存在，overwrite 为true，则重写文件
    //4.文件存在，overwrite 为false，则抛出异常错误
    //备注：传入的参数为文件夹目录
    public static void testCreateWithSizeP(String path) {
        String APIName = "create(Path f,boolean overwrite,int bufferSize,Progressable progress)";
        logger.info("测试创建文件API: "+APIName+"开始......");
        int bufferSize = 1024;
        String content = "Hello";
        Progressable progressable = Tool.getProgressable();
        String testfile1 = path+"/test1.txt";
        String testfile2 = path+"/test2.txt";
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }

        logger.info("测试案例1 ：文件不存在，且overwrite 为false");
        boolean overwrite1 = false;
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, overwrite1, bufferSize,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在,2.内容正确
        String realContent= Tool.getContent(testfile1);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))){
            logger.info("测试用例 1 执行成功，测试通过");
            logger.info("文件内容为 : "+realContent);
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件不存在，且overwrite 为true");
        boolean overwrite2 = true;
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2, overwrite2, bufferSize,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确

        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))){
            logger.info("测试用例2  执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例3 ：文件存在，且overwrite 为true");
        boolean overwrite3 = true;
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath3, overwrite3, bufferSize,progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查2点：1.文件是否存在，2.内容正确
        String realContent3= Tool.getContent(testfile1);
        if((realContent2!=null)&&(realContent3.equals(content2))){
            logger.info("测试用例3  执行成功，测试通过");
            logger.info("文件内容为: "+realContent3);
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例4 ：文件存在，且overwrite 为false,将抛出异常");
        boolean overwrite4 = false;
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, overwrite4, bufferSize,progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            logger.info("测试用例4 执行成功");
            e.printStackTrace();
            return;
        }
        logger.info("测试用例4 执行失败");
        logger.info("测试创建文件"+APIName+"结束......");
    }

    //编号NO.11
    //测试API :create(Path f,boolean overwrite,int bufferSize,short replication,long blockSize)
    //测试场景
    //1.文件不存在，overwrite 为false,则创建该文件
    //2.文件不存在，overwrite 为true ,则创建该文件
    //3.文件存在，overwrite 为true, 则重写该文件
    //4.文件存在，overwrite 为false,则抛出异常
    //备注：传入的参数为文件夹目录
    public static void testCreateWithBlock(String path){
        String APIName = "create(Path f,boolean overwrite,int bufferSize,short replication,long blockSize)";
        logger.info("测试创建文件API: "+ APIName+" 开始......");
        int bufferSize = 1024;
        short replication = 3;
        long blockSize = 1048576;
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
        String testfile2 = path+"/test2.txt";
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }

        logger.info("测试案例1 ：文件不存在，且overwrite 为false");
        boolean overwrite1 = false;
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =   dfs.create(filepath1, overwrite1, bufferSize,replication,blockSize);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查4点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确
        int rep1 = Tool.getFileReplication(filepath1);
        long bs1 = Tool.getFileBlockSize(filepath1);
        String realContent= Tool.getContent(testfile1);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))&&(rep1 == replication)&&(bs1 == blockSize)){
            logger.info("测试用例1 执行成功，测试通过");
            logger.info("文件内容为 : "+realContent);
            logger.info("副本数为: "+rep1);
            logger.info("blockSize: "+bs1);
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件不存在，且overwrite 为true");
        boolean overwrite2 = true;
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2, overwrite2, bufferSize,replication,blockSize);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查4点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确
        int rep2 = Tool.getFileReplication(filepath2);
        long bs2 = Tool.getFileBlockSize(filepath2);
        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))&&(rep2 == replication)&&(bs2 == blockSize)){
            logger.info("测试用例2  执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
            logger.info("副本数为: "+rep2);
            logger.info("blockSize: "+bs2);
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例3 ：文件存在，且overwrite 为true");
        boolean overwrite3 = true;
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath3, overwrite3, bufferSize,replication,blockSize);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查4点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确
        int rep3 = Tool.getFileReplication(filepath3);
        long bs3 = Tool.getFileBlockSize(filepath3);
        String realContent3= Tool.getContent(testfile1);
        if((realContent2!=null)&&(realContent3.equals(content2))&&(rep3 == replication)&&(bs3 == blockSize)){
            logger.info("测试用例3  执行成功，测试通过");
            logger.info("文件内容为: "+realContent3);
            logger.info("副本数为: "+rep3);
            logger.info("blockSize: "+bs3);
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例4 ：文件存在，且overwrite 为false,将抛出异常");
        boolean overwrite4 = false;
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, overwrite4, bufferSize,replication,blockSize);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            logger.info("测试用例4 执行成功");
            e.printStackTrace();
            return;
        }
        logger.info("测试用例4 执行失败");
        logger.info("测试创建文件"+APIName+"结束......");
    }

    //编号NO.12
    //测试API :create(Path f,FsPermission permission,boolean overwrite,int bufferSize,short replication,long blockSize,Progressable progress)
    //测试场景
    //1.文件不存在，overwrite 为false,则创建该文件
    //2.文件不存在，overwrite 为true ,则创建该文件
    //3.文件存在，overwrite 为true, 则重写该文件
    //4.文件存在，overwrite 为false,则抛出异常
    //备注：传入的参数为文件夹目录
    public static void testCreateWithB(String path){
        String APIName = "create(Path f,FsPermission permission,boolean overwrite,int bufferSize,short replication,long blockSize,Progressable progress)";
        int bufferSize = 1024;
        short replication = 3;
        long blockSize = 1048576;
        Progressable progressable = Tool.getProgressable();
        FsPermission permission =FsPermission.getDefault();
        logger.info("测试创建文件"+APIName+"开始......");
        String content = "Hello";
        String testfile1 = path+"/test1.txt";
        String testfile2 = path+"/test2.txt";
        logger.info("测试准备 1:获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试准备 2:检查用户设置的文件夹目录是否存在");
        boolean isExist = Tool.checkDir(path);
        if(isExist){
            logger.info("该文件夹已经存在，请选择其他文件夹");
            return;
        }

        logger.info("测试案例1 ：文件不存在，且overwrite 为false");
        boolean overwrite1 = false;
        Path filepath1 = new Path(testfile1);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath1, permission,overwrite1, bufferSize,replication,blockSize,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查5点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确，4.进度条是否正常显示，
        int rep1 = Tool.getFileReplication(filepath1);
        long bs1 = Tool.getFileBlockSize(filepath1);
        String realContent= Tool.getContent(testfile1);
        if(Tool.checkFile(testfile1)&&(realContent!=null)&&(realContent.equals(content))&&(rep1 == replication)&&(bs1 == blockSize)){
            logger.info("测试用例1 执行成功，测试通过");
            logger.info("文件内容为: "+realContent);
            logger.info("副本数为: "+rep1);
            logger.info("blockSize: "+bs1);
        }else{
            logger.info("测试用例1 执行失败，测试不通过");
        }

        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例2 ：文件不存在，且overwrite 为true");
        boolean overwrite2 = true;
        Path filepath2 = new Path(testfile2);
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath2, permission,overwrite2, bufferSize,replication,blockSize,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("创建文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查5点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确，4.进度条是否正常显示，
        int rep2 = Tool.getFileReplication(filepath2);
        long bs2 = Tool.getFileBlockSize(filepath2);
        String realContent2= Tool.getContent(testfile2);
        if(Tool.checkFile(testfile2)&&(realContent2!=null)&&(realContent2.equals(content))&&(rep2 == replication)&&(bs2 == blockSize)){
            logger.info("测试用例2 执行成功，测试通过");
            logger.info("文件内容为: "+realContent2);
            logger.info("副本数为: "+rep2);
            logger.info("blockSize: "+bs2);
        }else{
            logger.info("测试用例2 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例3 ：文件存在，且overwrite 为true");
        boolean overwrite3 = true;
        String content2 = "World";
        Path filepath3 = new Path(testfile1);
        if(!Tool.checkFile(testfile1)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath3, permission,overwrite3, bufferSize,replication,blockSize,progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，请检查");
            e.printStackTrace();
        }
        logger.info("检查测试案例执行结果");
        //检查测试是否通过
        //检查5点：1.文件是否存在，2.副本数是否正确，3.blockSize是否正确，4.内容正确，4.进度条是否正常显示，
        int rep3 = Tool.getFileReplication(filepath3);
        long bs3 = Tool.getFileBlockSize(filepath3);
        String realContent3= Tool.getContent(testfile1);
        if((realContent2!=null)&&(realContent3.equals(content2))&&(rep3 == replication)&&(bs3 == blockSize)){
            logger.info("测试用例3 执行成功，测试通过");
            logger.info("文件内容为: "+realContent3);
            logger.info("副本数为: "+rep3);
            logger.info("blockSize: "+bs3);
        }else{
            logger.info("测试用例3 执行失败，测试不通过");
        }
        logger.info("===================>>>>>>>>>>>>>==============================");
        logger.info("测试案例4 ：文件存在，且overwrite 为false,将抛出异常");
        boolean overwrite4 = false;
        Path filepath4= new Path(testfile2);
        if(!Tool.checkFile(testfile2)){
            logger.info("文件不存在，不满足测试条件,请检查测试代码");
        }
        try {
            FSDataOutputStream outputStream =  dfs.create(filepath4, permission,overwrite4, bufferSize,replication,blockSize,progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        } catch (Exception e) {
            logger.info("重写文件时出现异常，异常为正常显示");
            logger.info("测试用例4 执行成功");
            e.printStackTrace();
            return;
        }
        logger.info("测试用例4 执行失败");
        logger.info("测试创建文件"+APIName+"结束......");
    }



    //编号NO.13
    //测试API：create(Path f,FsPermission permission,EnumSet<CreateFlag> flags,bufferSize, replication,blockSize,
    //Progressable)
    //测试场景：
    //1.创建新文件，新文件创建成功，内容正确
    //2.重写旧文件，旧文件重写成功，内容正确
    public static void testCreateFileWithPP(String path){
        String APIName = "create(Path,permission,flags,bufferSize,replication, blockSize, Progressable )";
        logger.info("测试API："+APIName+"开始.......");
        int bufferSize = 1024;
        short replication = 3;
        long blockSize = 1048576;
        Path f = new Path(path);
        FsPermission permission = FsPermission.getDefault( );
        EnumSet<CreateFlag> flags =  EnumSet.of(CreateFlag.CREATE, CreateFlag.OVERWRITE);
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        Progressable progressable = Tool.getProgressable();
        logger.info("测试准备：获取DFS对象");
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试案例1：文件不存在，API将创建该文件");
        boolean isFileExist = Tool.checkFile(path);
        if(isFileExist){
            logger.info("文件已存在，请选择一个将新建的文件目录");
            return;
        }

        String content = "Hello";
        try{
            FSDataOutputStream outputStream = dfs.create(f,permission,flags,bufferSize,replication,blockSize,progressable);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
            logger.info("文件创建失败，请检查");
        }

        //检查文件
        Tool.checkFileIsRight(isFileExist,path,content);

        logger.info("测试案例2：文件已存在，API将重写该文件");
        String content2= "World";
        boolean isFileExist2 = Tool.checkFile(path);
        try{
            FSDataOutputStream outputStream = dfs.create(f,permission,flags,bufferSize,replication,blockSize,progressable);
            outputStream.write(content2.getBytes());
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
            logger.info("文件重写失败，请检查");
        }
        //检查文件
        Tool.checkFileIsRight(isFileExist2,path,content2);
        logger.info("测试API："+APIName+"开始.......");
    }


    //编号NO.14
    //测试API:FSDataOutputStream create(Path f, final FsPermission permission, final EnumSet<CreateFlag> cflags, final int bufferSize, final short replication, final long blockSize, final Progressable progress, final ChecksumOpt checksumOpt)
    //测试场景：
    //1.创建新文件，新文件创建成功，内容正确
    //2.重写旧文件，旧文件重写成功，内容正确
    public static void testCreateFileWithOutput(String path){
        String apiName = "create(Path f, permission, EnumSet<CreateFlag> cflags,  bufferSize, replication, blockSize, progress, checksumOpt)";
        logger.info("测试 "+apiName+" 开始......");
        short replication = 3;
        int bufferSize = 1024;
        long blockSize = 1048576;
        Path f = new Path(path);
        FsPermission permission = FsPermission.getDefault( );
        EnumSet<CreateFlag> flags =  EnumSet.of(CreateFlag.CREATE, CreateFlag.OVERWRITE);
        Progressable progressable = Tool.getProgressable();
        Options.ChecksumOpt checksumOpt = new Options.ChecksumOpt();
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        logger.info("测试准备：获取DFS对象");
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }
        logger.info("测试案例1：文件不存在，API将创建该文件");
        boolean isFileExist = Tool.checkFile(path);
        if(isFileExist){
            logger.info("文件已存在，请选择一个将新建的文件目录");
            return;
        }
        String content = "Hello";


        try{
            FSDataOutputStream outputStream = dfs.create(f, permission,flags, bufferSize, replication, blockSize, progressable, checksumOpt);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
            logger.info("文件创建失败，请检查");
        }

        //检查文件
        Tool.checkFileIsRight(isFileExist,path,content);

        logger.info("测试案例2：文件已存在，API将重写该文件");
        String content2= "World";
        boolean isFileExist2 = Tool.checkFile(path);
        try{
            FSDataOutputStream outputStream = dfs.create(f, permission,flags, bufferSize, replication, blockSize, progressable, checksumOpt);
            outputStream.write(content2.getBytes());
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
            logger.info("文件重写失败，请检查");
        }
        Tool.checkFileIsRight(isFileExist2,path,content2);

        logger.info("测试 "+apiName+" 结束......");
    }



    //编号NO.15
    //测试API:createNewFile(Path f)
    //API 说明：
    //Creates the given Path as a brand-new zero-length file.  If create fails, or if it already existed, return false.
    //@param f path to use for create
    //测试场景：
    //1.文件不存在，创建文件，返回值为true
    //2.文件存在，创建文件，返回值为false
    public static void testCreatNewFile(String path){
        String apiName = "createNewFile(Path f)";
        logger.info("测试API: "+apiName+"开始......");
        logger.info("测试准备1：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }

        logger.info("测试用例1: 文件不存在，返回为true");
        logger.info("步骤1：检查用户输入的文件是否存在");
        boolean isFileExist = Tool.checkFile(path);
        if(isFileExist){
            logger.info("该文件已存在，请重新选择一个不存在的文件目录");
            return;
        }

        boolean result1 = false;
        Path filePath = new Path(path);
        try{
            result1 = dfs.createNewFile(filePath);
        }catch(Exception e){
            logger.info("创建文件失败，请检查");
        }
        if(result1){
            logger.info("测试用例1: 测试通过");
        }else{
            logger.info("测试用例1: 测试失败");
        }

        logger.info("测试用例2: 文件存在，返回为false");
        boolean result2 = true;
        try{
            result2 = dfs.createNewFile(filePath);
        }catch(Exception e){
            logger.info("创建文件失败，请检查");
        }
        if(!result2){
            logger.info("测试用例2: 测试通过");
        }else{
            logger.info("测试用例2: 测试失败");
        }
    }

    //编号NO.16
    //测试API：isFile(Path f)
    //测试场景
    //1.f 为文件夹目录
    //2.f 为文件目录
    //3.f 文件不存在
    //说明：传入参数为文件夹目录
    public static void testIsFile(String path){
        String apiName = "isFile(Path f)";
        logger.info("测试 "+apiName+"开始......");
        logger.info("测试准备1：创建文件夹与文件");
        String DirPath = path;
        String FilePath1 = "/test01.txt";
        String FilePath2 = "/test02.txt";
        String FilePath3 =DirPath + "/test03.txt";
        boolean result = Tool.createDirAndFile(DirPath,FilePath1,FilePath2);
        if(!result){
            logger.info("创建文件夹与创建文件失败，请检查");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs ==null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }

        logger.info("测试案例1：f 为文件夹目录path");

        boolean result1 = false;
        try{
            Path Dir = new Path(path);
            result1 = dfs.isFile(Dir);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件失败，请检查......");
        }

        if(!result1){
            logger.info("测试案例1：f 为文件夹目录 测试通过.........");
        }else{
            logger.info("测试案例1：f 为文件夹目录 测试不通过.........");
        }


        logger.info("测试案例2：f 为文件目录path");
        boolean result2 = false;
        try{
            String testfilePath = path+FilePath2;
            Path testPath = new Path(testfilePath);
            result2 = dfs.isFile(testPath);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件失败，请检查......");
        }

        if(result2){
            logger.info("测试案例2：f 为文件目录 测试通过.........");
        }else{
            logger.info("测试案例2：f 为文件目录 测试不通过.........");
        }

        logger.info("测试案例3：f 为不存在文件目录path");
        boolean result3 = false;
        try{
            String wrongPath = path+FilePath3;
            Path testPath = new Path(wrongPath);
            result3 = dfs.isFile(testPath);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件是否存在失败，请检查......");
        }

        if(result3){
            logger.info("测试案例3：f 为不存在文件目录 测试不通过.........");
        }else{
            logger.info("测试案例3：f 为不存在文件目录 测试通过.........");
        }
    }




    //编号NO.17
    //测试API: open(Path f)
    //说明:传入参数为HDFS文件目录
    public static void testOpenFile(String path){
        String apiName = "open(Path f)";
        logger.info("测试API: "+apiName+" 开始......");
        logger.info("测试准备1：创建测试文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查！");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取DFS失败，请检查........");
            return;
        }

        Path filePath = new Path(path);
        FSDataInputStream in = null;
        String content ="";
        String expect ="helloWorld";

        try{
            in = dfs.open(filePath);
            content = in.readLine();

            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("文件内容为: "+content);
        if(content.equals(expect)){
            logger.info("该用例测试通过...............");
        }else{
            logger.info("该用例测试不通过...............");
        }
        logger.info("测试API: "+apiName+" 结束......");
    }


    //编号NO.18
    //测试API: open(Path f, final int bufferSize)
    //说明:传入参数为HDFS文件目录
    public static void testOpenFileWithSize(String path){
        String apiName = "open(Path f, final int bufferSize)";
        logger.info("测试API: "+apiName+" 开始......");
        logger.info("测试准备1：创建测试文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查！");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取DFS失败，请检查........");
            return;
        }
        Path filePath = new Path(path);
        FSDataInputStream in = null;
        String content ="";
        String expect ="helloWorld";
        try{
            in = dfs.open(filePath,5);
            content = in.readLine();
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("文件内容为: "+content);
        if(content.equals(expect)){
            logger.info("该用例测试通过...............");
        }else{
            logger.info("该用例测试不通过...............");
        }
        logger.info("测试API: "+apiName+" 结束......");
    }


    //编号NO.19
    //测试API:append(Path f)
    //API说明：
   /*
      Append to an existing file (optional operation).
     Same as append(f, getConf().getInt("io.file.buffer.size", 4096), null)
     @param f the existing file to be appended.
     @throws IOException
 */
    public static void testAppend(String path)  {
        String apiName = "append(Path f)";
        String append = "test append";
        Path filePath = new Path(path);
        logger.info("测试API: "+apiName+" 开始......");
        logger.info("测试准备1：创建测试文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查！");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取DFS失败，请检查........");
            return;
        }

        try{
            FSDataOutputStream outputStream = dfs.append(filePath);
            outputStream.write(append.getBytes());
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            logger.info("append 文件失败，请检查......");
        }

        //检查测试结果
        boolean result = Tool.checkAppendFile(path,append);
        if(result){
            logger.info("该用例测试通过......");
        }else{
            logger.info("该用例测试通过......");
        }

        logger.info("测试API: "+apiName+" 结束......");
    }



    //编号NO.20
    //测试API: append(Path f, int bufferSize)
    //API说明:追加文件时，以buffSize为限制，
    /**
     Append to an existing file (optional operation).
     Same as append(f, bufferSize, null).
     f the existing file to be appended.
     bufferSize the size of the buffer to be used.
     IOException
     */
    public static void testAppendwithSize(String path){
        int bufferSize = 4;
        String append = "test append";
        Path filePath = new Path(path);
        String apiName = "append(Path f, int bufferSize)";

        logger.info("测试API: "+apiName+" 开始......");
        logger.info("测试准备1：创建测试文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查！");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取DFS失败，请检查........");
            return;
        }
        try{
            FSDataOutputStream outputStream = dfs.append(filePath,bufferSize);
            outputStream.write(append.getBytes());
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            logger.info("append 文件失败，请检查......");
        }

        //检查测试结果
        boolean result = Tool.checkAppendFile(path,append);
        if(result){
            logger.info("该用例测试通过......");
        }else{
            logger.info("该用例测试失败......");
        }
        logger.info("测试API: "+apiName+" 结束......");
    }


    //编号NO.21
    //测试API:append(Path f, final int bufferSize, final Progressable progress)
    public static void testAppendFileWithBP(String path){
        String apiName = "append(Path f, final int bufferSize, final Progressable progress)";
        logger.info("测试API: "+apiName+" 开始......");
        logger.info("请注意进度条.........");
        logger.info("测试准备1：创建测试文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查！");
            return;
        }
        logger.info("测试准备2：获取DFS对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取DFS失败，请检查........");
            return;
        }

        String append = "test append";
        int bufferSize = 100;
        Path filePath = new Path(path);
        Progressable progressabel = Tool.getProgressable();
        if(dfs!=null){
            try{
                FSDataOutputStream outputStream = dfs.append(filePath,bufferSize,progressabel);
                outputStream.write(append.getBytes());
                outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
                logger.info("append 文件失败，请检查......");
            }
        }else{
            logger.info("获取该文件信息失败，请检查........");
            return;
        }

        //检查测试结果
        boolean result = Tool.checkAppendFile(path,append);
        if(result){
            logger.info("该用例测试通过......");
        }else{
            logger.info("该用例测试失败......");
        }

        logger.info("测试API: "+apiName+" 结束......");
    }


    //编号NO.22
    //测试API:boolean exists(Path f)
    //测试场景
    //1.f为文件夹path
    //2.f为文件Path
    //3.f为不存在文件path
    public static void testIsExists(String path){
        String apiName = "boolean exists(Path f)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs ==null){
            logger.info("获取DFS对象失败，请检查");
            return;
        }

        String DirPath = path;
        String FilePath1 = "/test01.txt";
        String FilePath2 = "/test02.txt";
        String FilePath3 =DirPath + "/test03.txt";
        boolean result = Tool.createDirAndFile(DirPath,FilePath1,FilePath2);
        if(!result){
            logger.info("创建文件夹与创建文件失败，请检查");
            return;
        }
        logger.info("测试案例1：f 为文件夹目录path");

        boolean result1 = false;
        try{
            Path Dir = new Path(path);
            result1 = dfs.exists(Dir);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件失败，请检查......");
        }

        if(result1){
            logger.info("测试案例1：f 为文件夹目录 测试通过.........");
        }else{
            logger.info("测试案例1：f 为文件夹目录 测试不通过.........");
        }


        logger.info("测试案例2：f 为文件目录path");
        boolean result2 = false;
        try{
            String testfilePath = path+FilePath2;
            Path testPath = new Path(testfilePath);
            result2 = dfs.exists(testPath);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件失败，请检查......");
        }

        if(result2){
            logger.info("测试案例2：f 为文件目录 测试通过.........");
        }else{
            logger.info("测试案例2：f 为文件目录 测试不通过.........");
        }

        logger.info("测试案例3：f 为不存在文件目录path");
        boolean result3 = false;
        try{
            String wrongPath = path+FilePath3;
            Path testPath = new Path(wrongPath);
            result3 = dfs.exists(testPath);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("检查文件是否存在失败，请检查......");
        }

        if(result3){
            logger.info("测试案例3：f 为不存在文件目录 测试不通过.........");
        }else{
            logger.info("测试案例3：f 为不存在文件目录 测试通过.........");
        }

        logger.info("测试API: "+apiName+" 结束......");
    }


    //-------------------------------12.28------------------------------------------
    //编号NO.23
    //测试API：listFiles(final Path f, final boolean recursive)
    //API说明：
    /*
     功能：List the statuses and block locations of the files in the given path.
     应用场景：
     1.If the path is a directory,
         1.1.if recursive is false, returns files in the directory;
         1.2.if recursive is true, return files in the subtree rooted at the path.
     2.If the path is a file, return the file's status and block locations.
     参数说明:
     1. f is the path
     2. recursive if the subdirectories need to be traversed recursively
     返回值说明：
     return an iterator that traverses statuses of the files
      @throws FileNotFoundException when the path does not exist;
     IOException see specific implementation
     */
    //备注：传入的path应该为文件夹！！
    /*测试场景：
    1.f 为文件夹，recursive is false, 则返回文件夹中文件
    2.f 为文件夹，recursive is true,  则返回文件夹中所有文件，包括子文件夹中文件
    3.f 为文件，recursive is false
    4.f 为文件，recursive is true
     */
    /*测试目录
     1. 文件夹/test01.txt
     2. 文件夹/test/test02.txt
     */
    public static void testlistFile(String path){
        String apiName = "listFiles(final Path f, final boolean recursive)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        String DirPath = path;
        String FilePath1 = "/test01.txt";
        String FilePath2 = "/test/test02.txt";
        boolean result = Tool.createDirAndFile(DirPath,FilePath1,FilePath2);
        if(!result){
            logger.info("创建文件夹与创建文件失败，请检查");
            return;
        }

        int result1 = 0;
        int result2 = 0 ;
        int result3 = 0;
        int result4 = 0;
        Path directory = new Path(DirPath);
        Path filePath  = new Path(DirPath+FilePath1);


        try {
            logger.info("测试用例1：f 为文件夹，recursive 为false........");
            RemoteIterator<LocatedFileStatus> list1 = dfs.listFiles(directory, false);
            while (list1.hasNext()) {
                result1++;
                list1.next();
            }

            logger.info("测试用例2：f 为文件夹，recursive 为true........");
            RemoteIterator<LocatedFileStatus> list2 = dfs.listFiles(directory, true);
            while (list2.hasNext()) {
                result2++;
                list2.next();
            }


            logger.info("测试用例3：f 为文件，recursive 为false........");
            RemoteIterator<LocatedFileStatus> list3 = dfs.listFiles(filePath, false);
            while (list3.hasNext()) {
                result3++;
                list3.next();
            }

            logger.info("测试用例4：f 为文件，recursive 为true........");
            RemoteIterator<LocatedFileStatus> list4 = dfs.listFiles(filePath, true);
            while (list4.hasNext()) {
                result4++;
                list4.next();
            }

        }catch(Exception e){
            e.printStackTrace();
            logger.info("调用该API 出现异常，请检查测试代码");
            return;
        }

        logger.info("测试结果汇总如下:");
        if(result1==1){
            logger.info("测试用例1：f 为文件夹，recursive 为false====通过 ");
        }else{
            logger.info("测试用例1：f 为文件夹，recursive 为false====失败");
            logger.info("实际结果为 "+result1);
        }

        if(result2==2){
            logger.info("测试用例2：f 为文件夹，recursive 为true====通过 ");
        }else{
            logger.info("测试用例2：f 为文件夹，recursive 为true====失败");
            logger.info("实际结果为 "+result2);
        }

        if(result3==1){
            logger.info("测试用例3：f 为文件，recursive 为false====通过 ");
        }else{
            logger.info("测试用例3：f 为文件，recursive 为false====失败");
            logger.info("实际结果为 "+result3);
        }

        if(result4==1){
            logger.info("测试用例4：f 为文件，recursive 为true====通过 ");
        }else{
            logger.info("测试用例4：f 为文件，recursive 为true====失败");
            logger.info("实际结果为 "+result4);
        }

        logger.info("测试 "+apiName+"结束......");
    }

    //编号NO.24
    //测试API:listLocatedStatus(final Path f)
    //功能说明：
    //List the statuses of the files/directories in the given path
    //1.if the path is a directory.Return the file's status and block locations
    //2.If the path is a file.If a returned status is a file, it contains the file's block locations.
    //参数与异常说明：
    //参数：f is the path 本用例中传入参数为文件夹目录
    //@throws FileNotFoundException If <code>f</code> does not exist
    //@throws IOException If an I/O error occurred
    //返回值：an iterator that traverses statuses of the files/directories in the given path
    //测试场景
    //1.f 为文件夹
    //2.f 为文件
    public static void testlistLocatedStatus(String path){
        String apiName = "listLocatedStatus(final Path f)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        Path directory = new Path(path);
        boolean checkDir = false;
        //boolean checkCreateDir = false;
        try{
            checkDir = dfs.isDirectory(directory);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(checkDir){
            logger.info("该文件夹已存在，请选择一个不存在的文件夹，我们将自动帮你创建");
            return;
        }
        logger.info("程序自动帮你创建文件夹开始.......");
        try {
            dfs.mkdirs(directory);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件夹失败，请检查");
            return;
        }

        if(Tool.checkDir(path)){
            logger.info("程序自动帮你创建文件夹成功.......");
            logger.info("程序自动帮你创建文件,请稍等.............");
        }else{
            logger.info("程序创建文件夹失败，请检查.......");
            return;
        }

        String file01 = path+"/test01.txt";
        String file02 = path+"/test02.txt";
        Path filePath = new Path(file01);
        Path filePath2 = new Path(file02);
        String fileName1 = filePath.getName();
        String fileName2 = filePath2.getName();
        Tool.createFile(file01);
        Tool.createFile(file02);

        if(Tool.checkFile(file01)&&Tool.checkFile(file02)){
            logger.info("程序自动帮你创建文件夹成功.......");
            logger.info("测试准备工作完成.......");
        }else{
            logger.info("程序自动帮你创建文件失败，请检查.......");
            return;
        }

        boolean checkDir1 = false;
        boolean checkDir2 = false;
        boolean checkFil = false;

        try{
            RemoteIterator<LocatedFileStatus> dir = dfs.listLocatedStatus(directory);
            RemoteIterator<LocatedFileStatus> f = dfs.listLocatedStatus(filePath);
            while(dir.hasNext()){
                LocatedFileStatus dirStatus = dir.next();
                Path dirPath = dirStatus.getPath();
                String pathName = dirPath.getName();
                logger.info("文件夹名称: "+pathName);
                if(pathName.equals(fileName1)){
                    checkDir1 = true;
                }
                if(path.equals(fileName2)){
                    checkDir2 = true;
                }
            }
            while(f.hasNext()){
                LocatedFileStatus fileStatus = f.next();
                Path Path = fileStatus.getPath();
                String pathName = Path.getName();
                logger.info("文件名称: "+pathName);
                if(pathName.equals(fileName1)){
                    checkFil = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info("获取信息失败，请检查");
            return;
        }

        logger.info("测试案例1：参数为文件夹目录，获得结果为文件夹下文件信息");
        if(checkDir1&&checkDir2){
            logger.info("文件信息显示正确，测试案例1 测试成功");
        }else{
            logger.info("文件信息显示错误，测试案例1 测试失败");
        }
        logger.info("测试案例2：参数为文件目录，获得结果为文件信息");
        if(checkFil){
            logger.info("文件信息显示正确，测试案例2 测试成功");
        }else{
            logger.info("文件信息显示错误，测试案例2 测试失败");
        }
    }



    //编号NO.25
    //测试API:FileStatus[] listStatus(Path p)
    public static void testListStatus(String path){
        String apiName = "listStatus(Path p)";
        logger.info("测试 "+apiName+"开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查");
            return;
        }

        Path filepath = new Path(path);
        try{
            FileStatus[] f = dfs.listStatus(filepath);
            if(f.length==0){
                logger.info("该用例测试失败......");
            }else{
                logger.info("该用例测试成功......");
                logger.info("该文件信息: "+f[0].toString()) ;
            }
        }catch(Exception e ){
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ") ;
        }

        logger.info("测试 "+apiName+"结束......");
    }


    //编号NO.26
    //测试API: FileStatus[] listStatus(Path f, PathFilter filter)
    //功能说明：Filter files/directories in the given path using the user-supplied path filter.
    //参数说明：
    //1.f：a path name
    //2.filter the user-supplied path filter
    //返回值: an array of FileStatus objects for the files under the given path after applying the filter
    //异常说明：
    // 1.FileNotFoundException when the path does not exist;
    // 2.IOException see specific implementation
    public  static void testListStatusWithFilter(String path){
        String apiName = "listStatus(Path f, PathFilter filter)";
        logger.info("测试 "+apiName+"开始......");

        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("创建文件失败，请检查");
            return;
        }

        Path filepath = new Path(path);
        try{
            logger.info("测试用例1：文件存在，且PathFilter为true");
            PathFilter trueFilter =Tool.getTruePathFilter();
            FileStatus[] f = dfs.listStatus(filepath,trueFilter);
            if(f.length==0){
                logger.info("测试用例1 测试失败......");
            }else{
                logger.info("测试用例1 测试成功......");
                logger.info("该文件信息: "+f[0].toString()) ;
            }
            logger.info("测试用例2：文件存在，且PathFilter为false");
            PathFilter falseFilter =Tool.getFalsePathFilter();
            FileStatus[] f1 = dfs.listStatus(filepath,falseFilter);
            if(f1.length==0){
                logger.info("测试用例2 测试成功......");
            }else{
                logger.info("测试用例2 测试失败......");
                logger.info("该文件信息: "+f[0].toString()) ;
            }
        }catch(Exception e ){
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ") ;
        }

        logger.info("测试 "+apiName+"结束......");
    }


    //编号NO.27
    //测试API:listStatus(Path[] files)
    //功能说明：Filter files/directories in the given list of paths using defaultpath filter.
    //参数说明：
    //1.files :a list of paths
    //返回值：a list of statuses for the files under the given paths after applying the filter default Path filter
    //异常说明:
    //1. FileNotFoundException when the path does not exist;
    //2. IOException see specific implementation
    public static void testListStatusWithFS(String path) {
        String apiName = "listStatus(Path[] files)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        boolean precondition = Tool.createDirAndFile(path, file1, file2);
        if (!precondition) {
            logger.info("创建文件夹与文件失败，请检查......");
            return;
        }
        file1 = path + file1;
        file2 = path + file2;
        Path path1 = new Path(file1);
        Path path2 = new Path(file2);
        Path[] paths = new Path[]{path1, path2};
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }

        FileStatus[] f = null;
        try {
            f = dfs.listStatus(paths);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ");
        }
        logger.info("测试结果如下： ");
        if (f.length == 2) {
            logger.info("测试用例测试通过.........");
            logger.info("第一个文件信息如下:  " + f[0].toString());
            logger.info("第二个文件信息如下:  " + f[1].toString());
        } else {
            logger.info("测试用例测试失败.........");
        }
        logger.info("测试API: " + apiName + "开始......");
    }

    //编号NO.28
    //测试API：FileStatus[] listStatus(Path[] files, PathFilter filter)
    //功能说明：Filter files/directories in the given list of paths using user-supplied path filter.
    //参数说明:
    // 1.files:a list of paths
    // 2.filter:the user-supplied path filter
    //返回值说明：a list of statuses for the files under the given paths after applying the filter
    // 异常说明 FileNotFoundException when the path does not exist;IOException see specific implementation
    public static void testListStatusWithFSF(String path) {
        String apiName = "listStatus(Path[] files, PathFilter filter)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        boolean precondition = Tool.createDirAndFile(path, file1, file2);
        if (!precondition) {
            logger.info("创建文件夹与文件失败，请检查......");
            return;
        }
        file1 = path + file1;
        file2 = path + file2;
        Path path1 = new Path(file1);
        Path path2 = new Path(file2);
        Path[] paths = new Path[]{path1, path2};
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }

        FileStatus[] f1 = null;
        FileStatus[] f2 = null;

        try {
            logger.info("测试用例1： pathFilter 为 true");
            PathFilter truePathFilter = Tool.getTruePathFilter();
            f1 = dfs.listStatus(paths, truePathFilter);
            logger.info("测试用例1： pathFilter 为 false");
            PathFilter falsePathFilter = Tool.getFalsePathFilter();
            f2 = dfs.listStatus(paths, falsePathFilter);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取文件信息失败,请检查...... ");
            return;
        }

        logger.info("测试结果如下...... ");
        if (f1.length == 2) {
            logger.info("测试用例1： pathFilter 为 true===>测试通过");
        } else {
            logger.info("测试用例1： pathFilter 为 true===>测试不通过");
        }

        if (f2.length == 0) {
            logger.info("测试用例2： pathFilter 为 false===>测试通过");
        } else {
            logger.info("测试用例2： pathFilter 为 false===>测试不通过");
        }

        logger.info("测试API: " + apiName + "结束......");
    }


    //--------------------12.29-----------------------------------------------------
    //编号NO.29
    //测试API：isInSafeMode()
    //功能说明：检查是否处于安全模式，默认为不是安全模式
    public static void testIsSafeMode(){
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


    //编号NO.30
    //测试API: moveToLocalFile(Path src, Path dst)
    //功能说明：将HDFS上的文件移动到本地文件中
    //The src file is under FS, and the dst is on the local disk. Copy it from FS control to the local dst name.Remove the source afterwards
    public static void testMoveLocalFile(String path){
        String apiName = "moveToLocalFile(Path src, Path dst)";
        logger.info("测试API: " + apiName + "开始......");
        logger.info("测试步骤1：在HDFS上创建文件");
        boolean isSuccess = Tool.createFile(path);
        if(!isSuccess){
            logger.info("在HDFS上创建文件失败，请检查");
            return;
        }
        String fileInLocal = "/home/bigdata/moveToLocalFile.txt";
        logger.info("测试步骤2：移动文件到本地");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if (dfs == null) {
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path src = new Path(path);
        Path dst = new Path(fileInLocal);
        try{
            dfs.moveToLocalFile(src,dst);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("API调用失败，请检查");
            return;
        }
        logger.info("测试步骤3：检查移动后本地文件");
        String result = Tool.readLocalFile(fileInLocal);
        if(result.equals("helloWorld")) {
            logger.info("用例测试通过........");
        }else{
            logger.info("用例测试失败........");
            if(result==null){
                logger.info("读取本地文件失败");
            }else{
                logger.info("读取本地文件内容为"+result);
            }
        }

        logger.info("测试API: " + apiName + "结束......");
    }


    //编号NO.31
    //测试API:completeLocalOutput(Path fsOutputFile, Path tmpLocalFile)
    //功能描述：复制 本地文件内容至HDFS系统文件中
    // Called when we're all done writing to the target.  A local FS will
    //do nothing, because we've written to exactly the right place.  A remote
    // FS will copy the contents of tmpLocalFile to the correct target at fsOutputFile.
    //测试用例描述:该用例在bigdata用户目录下创建文件，根据实际情况，这一目录将改变，请检查系统用户目录
    public static void testCompleteLocalOutput(String path){
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


    //编号NO.32
    //测试API:delete(Path f, final boolean recursive)
    //功能描述：删除文件，设置是否递归删除
    //测试用例描述:1.先创建文件夹，后创建文件，再创建文件夹下的文件
    public static void testDelete(String path){
        String apiName = "delete(Path f, final boolean recursive)";
        logger.info("测试API: " + apiName + "开始......");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        logger.info("测试用例1：文件夹下无文件，递归删除为false，文件夹被成功删除");
        boolean IsSuccess = Tool.createDir(path);
        if(IsSuccess){
            try{
                Path path1 = new Path(path);
                dfs.delete(path1,false);
            }catch(Exception e){
                logger.info("删除出现异常，请检查");
                e.printStackTrace();
            }
        }else{
            return ;
        }

        //检查测试用例1结果
        boolean result1 = Tool.checkDir(path);
        if(result1){
            logger.info("测试用例1：执行失败");
        }else{
            logger.info("测试用例1：执行成功");
        }

        logger.info("测试用例2：文件夹下有文件，递归删除为false，删除出现异常");

        logger.info("测试步骤1：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }
        Path path1 = new Path(path);
        Path path2 = new Path(path+file1);
        Path path3 = new Path(path+file2);

        try{
            dfs.delete(path1,false);
        }catch(Exception e){
            logger.info("测试用例2：测试通过");
            e.printStackTrace();
        }
        if(Tool.checkDir(path)&&Tool.checkFile(path+file2)&&(Tool.checkFile(path+file1))){
            logger.info("测试用例2：递归删除为false====>通过");
        }else{
            logger.info("测试用例2：递归删除为false====>失败");
        }

        logger.info("测试用例3：文件夹下有文件，递归删除为true，文件夹与文件都将删除成功");

        String newPath = path+"TEST";
        logger.info("测试步骤1：创建文件和文件夹");
        boolean Iscreate = Tool.createDirAndFile(newPath,file1,file2);
        if(!Iscreate){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        DistributedFileSystem dfs1 = Tool.getDistributedFileSystem();
        if(dfs1==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path deletePath = new Path(newPath);
        try{
            dfs1.delete(deletePath,true);
        }catch(Exception e){
            logger.info("删除出现异常，请检查");
            e.printStackTrace();
        }
        if(!Tool.checkFile(newPath+file2)&&(!Tool.checkFile(newPath+file1))&&(!Tool.checkDir(newPath))){
            logger.info("测试用例3：递归删除为true====>通过");
        }else{
            logger.info("测试用例3：递归删除为true====>失败");
        }

        logger.info("测试API: " + apiName + "结束......");
    }

    //------------------------------------2018.1.2--------------------------------------------------
    //编号NO.33
    //测试API:resolvePath(final Path p)
    //功能描述：Return the fully-qualified path of path f resolving the path through any symlinks or mount point
    //参数说明:p path to be resolved
    //返回值：return fully qualified path(Path对象）
    //异常说明
    //FileNotFoundException
    //测试用例描述:传入文件夹参数
    public static void testResolvePath(String path){
        String apiName = "resolvePath(final Path p)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test/test2.txt";

        logger.info("测试步骤1：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试步骤2：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试步骤3：解析Path对象");
        logger.info("测试用例1：path为已经存在的路径，应解析为正确路径");
        Path Directory = new Path(path);
        Path result = null;
        try{
            result = dfs.resolvePath(Directory);
        }catch(Exception e){
            logger.info("解析路径失败，请检查");
        }
        logger.info("测试结果如下:");
        if(result==null){
            logger.info("测试用例1：解析失败，该用例测试失败，请检查");
        }else{
            logger.info("测试用例1：解析成功，该用例测试成功");
            logger.info("解析如下:"+result.toString());
        }

        Path wrong = new Path(Directory+"/aa");
        Path result1 = null;
        logger.info("测试用例2：path为不存在的路径，应抛出异常");
        try{
            result1 = dfs.resolvePath(wrong);
        }catch (Exception e){
            logger.info("测试用例2 执行成功，抛出异常");
            e.printStackTrace();
        }
        if(result1==null){
            logger.info("测试用例2：执行成功");
        }
        logger.info("测试API: " + apiName + "结束......");
    }



    //编号NO.34
    //测试API:Path makeQualified(Path path)
    //功能描述：Make sure that a path specifies a FileSystem
    //参数说明:param path to use
    //返回值：Path
    //测试用例描述:
    //1.文件存在，将解析为HDFS上路径
    //2.文件不存在，将解析为HDFS上路径
    //参数说明：传入一个并不存在的文件夹路径，用例将自动创建
    public static void testMakeQualified(String path){
        String apiName = "makeQualified(Path path)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test/test2.txt";

        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }

        String rightPath =  path+file1;
        Path path1 = new Path(rightPath);
        String wrongPath = path+"/AA";
        Path path2 = new Path(wrongPath);

        logger.info("测试用例1：参数为正确的Path,应返回正确的path");
        Path result1 = null;
        try{
            result1 = dfs.makeQualified(path1);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result1==null){
            logger.info("测试用例1失败，请检查");
        }else{
            logger.info("测试用例1成功,Path 信息如下：");
            logger.info(result1.toString());
        }
        logger.info("测试用例2：参数为错误的Path,解析为HDFS上路径");
        Path result2 = null;
        try{
            result2 = dfs.makeQualified(path2);
        }catch (Exception e){
            logger.info("测试用例2失败！,它将抛出异常");
            e.printStackTrace();
        }
        if(result2==null){
            logger.info("测试用例2失败");
        }else{
            logger.info("测试用例2成功,Path 信息如下：");
            logger.info(result2.toString());
        }
        logger.info("测试API: " + apiName + "结束......");
    }



    //编号NO.35
    //测试API:FileStatus[] globStatus(Path pathPattern)
    //功能描述：Return all the files that match filePattern and are not checksum
    //files. Results are sorted by their names.Results are sorted by their names.
    //参数说明:Path pathPattern
    //返回值：FileStatus[]
    //测试用例描述：传入文件夹参数
    public static void testGlobStatus(String path){
        String apiName = "globStatus(Path pathPattern)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";

        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }

        String PathPattern =  path+"/*";
        Path path1 =  new Path(PathPattern);

        logger.info("测试用例:传入的正则为*,返回该文件夹下文件信息");
        FileStatus[] result = null;

        try{
            result= dfs.globStatus(path1);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("测试用例失败，请检查");

        }
        if(result == null){
            logger.info("测试用例失败，请检查");
        }else{
            logger.info("测试用例成功,信息如下：");
            logger.info("文件信息汇总如下：");
            int i  = result.length;
            logger.info("共计："+i+" 个信息");
            for(int j = 0; j< i; j++){
                FileStatus fs = result[j];
                logger.info(fs.toString());
            }
        }
        logger.info("测试API: " + apiName + "结束......");
    }


    //编号NO.36
    //测试API:FileStatus[] globStatus(Path pathPattern, PathFilter filter)
    //功能描述：
    // Return an array of FileStatus objects whose path names match pathPattern
    //and is accepted by the user-supplied path filter. Results are sorted by their path names.
    // Return null if pathPattern has no glob and the path does not exist.
    // Return an empty array if pathPattern has a glob and no path matches it.
    //参数描述:
    //@param pathPattern a regular expression specifying the path pattern
    //@param filter a user-supplied path filter
    //返回值：
    //return an array of FileStatus objects
    //异常说明:
    //throws IOException if any I/O error occurs when fetching file status
    //测试用例描述：
    public static void testGlobalStatusWithFilter(String path){
        String apiName = "globStatus(Path pathPattern, PathFilter filter)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";

        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }

        logger.info("测试用例1:pathPattern匹配文件夹下的文件，pathFile 为true,返回值为 FileStatus数组");
        String PathPattern1 =  path+"/*";
        Path path1 =  new Path(PathPattern1);
        PathFilter pathFilter1 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return true;
            }
        };
        FileStatus[] result1 = null;
        try{
            result1= dfs.globStatus(path1,pathFilter1);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("测试用例1异常，请检查");
        }
        if(result1 == null){
            logger.info("测试用例1:失败，请检查");
        }else{
            logger.info("测试用例1:成功,信息如下：");
            logger.info("文件信息汇总如下：");
            int i  = result1.length;
            logger.info("共计："+i+" 个信息");
            for(int j = 0; j< i; j++){
                FileStatus fs = result1[j];
                logger.info(fs.toString());
            }
        }

        logger.info("测试用例2:pathPattern不能匹配文件夹下的文件，pathFile 为false,返回值为null");
        String PathPattern2 =  path+"/aa";
        Path path2 =  new Path(PathPattern2);
        PathFilter pathFilter2 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };
        FileStatus[] result2 = null;
        try{
            result2= dfs.globStatus(path2,pathFilter2);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("测试用例2异常，请检查");

        }
        if(result2 == null){
            logger.info("测试用例2:成功，请检查");
        }else{
            logger.info("测试用例2:失败,请检查");
        }


        logger.info("测试用例3:pathPattern能匹配文件夹下的文件，pathFile 为false,返回值为空FileStatus数组");
        String PathPattern3 =  path+"/*";
        Path path3 =  new Path(PathPattern3);
        PathFilter pathFilter3 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };
        FileStatus[] result3 = null;
        try{
            result3= dfs.globStatus(path3,pathFilter3);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("测试用例3异常，请检查");

        }
        if(result3 == null){
            logger.info("测试用例3:失败，请检查");
        }else{
            logger.info("测试用例3:成功,请检查");
        }
        logger.info("测试API: " + apiName + "结束......");
    }


    //编号NO.37
    //测试API:allowSnapshot(final Path path)
    //功能描述：开启快照
    //测试用例描述：
    public static void testAllowSnapshot(String path){
        String apiName = "allowSnapshot(final Path path)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path Directory = new Path(path);
        try{
            dfs.allowSnapshot(Directory);
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        String snapshot = path+"/.snapshot";

        if(Tool.checkDir(snapshot)){
            logger.info("快照文件夹创建成功，用例测试通过！");
        }else{
            logger.info("快照文件夹创建失败，用例测试不通过！");
        }
    }


    //编号NO.38
    //测试API:createSnapshot(final Path path, final String snapshotName)
    //功能描述：创建快照
    //测试用例描述：
    public static void testCreateSnapshot(String path){
        String apiName = "createSnapshot(final Path path, final String snapshotName)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path Directory = new Path(path);
        String name = "testSnap";
        try{
            logger.info("测试步骤1：开启snapshot");
            dfs.allowSnapshot(Directory);
            logger.info("测试步骤2：创建snapshot文件");
            dfs.createSnapshot(Directory,name);
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        String snapshot = path+"/.snapshot/"+name;


        if(Tool.checkDir(snapshot)){
            logger.info("快照记录文件夹创建成功，用例测试通过！");
        }else{
            logger.info("快照记录文件夹创建失败，用例测试不通过！");
        }

        logger.info("测试API: " + apiName + "结束......");
    }


    //编号NO.39
    //测试API:renameSnapshot(final Path path, final String snapshotOldName, final String snapshotNewName)
    //功能描述：重命名快照
    //测试用例描述：
    public static void testRenameSnapshot(String path){
        String apiName = "renameSnapshot(final Path path, final String snapshotOldName, final String snapshotNewName)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path Directory = new Path(path);
        String name = "testSnap";
        String newName ="newSnap";
        try{
            logger.info("测试步骤1：开启snapshot");
            dfs.allowSnapshot(Directory);
            logger.info("测试步骤2：创建snapshot文件");
            dfs.createSnapshot(Directory,name);
            logger.info("测试步骤3：重命名snapshot文件");
            dfs.renameSnapshot(Directory,name,newName);

        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        String snapshot = path+"/.snapshot/"+newName;

        if(Tool.checkDir(snapshot)){
            logger.info("快照记录文件重命名成功，用例测试通过！");
        }else{
            logger.info("快照记录文件重命名失败，用例测试不通过！");
        }

        logger.info("测试API: " + apiName + "结束......");
    }


    //编号NO.40
    //测试API:deleteSnapshot(final Path snapshotDir, final String snapshotName)
    //功能描述：删除快照
    //测试用例描述：
    public static void testDeleteSnapshot(String path){
        String apiName = "deleteSnapshot(final Path snapshotDir, final String snapshotName)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path Directory = new Path(path);
        String name = "testSnap";
        String snapshot = path+"/.snapshot/"+name;


        try{
            logger.info("测试步骤1：开启snapshot");
            dfs.allowSnapshot(Directory);
            logger.info("测试步骤2：创建snapshot文件");
            dfs.createSnapshot(Directory,name);
            logger.info("测试步骤3：删除snapshot文件");
            dfs.deleteSnapshot(Directory,name);

        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        if(Tool.checkDir(snapshot)){
            logger.info("删除快照失败，用例测试不通过！");
        }else{
            logger.info("删除快照成功，用例测试通过！");
        }
        logger.info("测试API: " + apiName + "结束......");
    }


    //编号NO.41
    //测试API:disallowSnapshot(final Path path)
    //功能描述：关闭快照
    //测试用例描述：
    //1.先开启快照，后关闭快照，再检查相应目录是否存在
    public static void testDisallowSnapshot(String path){
        String apiName = "disallowSnapshot(final Path path)";
        logger.info("测试API: " + apiName + "开始......");
        String file1 = "/test1.txt";
        String file2 = "/test2.txt";
        logger.info("测试准备：创建文件和文件夹");
        boolean create = Tool.createDirAndFile(path,file1,file2);
        if(!create){
            logger.info("创建文件夹和文件失败，请检查");
            return;
        }

        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        Path Directory = new Path(path);
        String snapshot = path+"/.snapshot";

        try{
            logger.info("测试步骤1：开启snapshot");
            dfs.allowSnapshot(Directory);
            logger.info("测试步骤2：关闭snapshot");
            dfs.disallowSnapshot(Directory);
        }catch(Exception e){
            e.printStackTrace();
        }

        logger.info("测试结果检查：");
        if(Tool.checkDir(snapshot)){
            logger.info("关闭快照失败，用例测试不通过！");
        }else{
            logger.info("关闭快照成功，用例测试通过！");
        }
        logger.info("测试API: " + apiName + "结束......");
    }


    //----------------------------------1.3---------------------------------------------------
    //编号NO.42
    //测试API:addCachePool(CachePoolInfo info)
    //功能描述：添加缓存池
    //测试用例描述：
    //1.添加缓存池前，先进行缓存池检查，如果该缓存池名称已经存在则给出提示
    //2.添加缓存池后，进行检查是否添加成功
    public static void testAddCachePool(String poolname ){
        String apiName = "addCachePool(CachePoolInfo info)";
        logger.info("测试API: " + apiName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool( poolname);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;
        }

        CachePoolInfo cachePoolInfo = new CachePoolInfo(poolname);
        try{
            dfs.addCachePool(cachePoolInfo);
        }catch(Exception e){
            logger.info("添加缓存池失败，请检查");
            e.printStackTrace();
        }

        //如何检查是否添加成功？
        boolean lastResult = Tool.checkCachePool(poolname);
        if(lastResult){
            logger.info("该缓存池添加成功，用例执行成功");
        }else{
            logger.info("该缓存池添加失败，用例执行失败");
        }
        logger.info("测试API: " + apiName + "结束......");
    }

    //编号:NO.43
    //测试API:removeCachePool(String poolName)
    //功能描述：移除缓存池
    //测试用例描述：
    //1.移除缓存池后，进行检查是否移除成功
    public static void testRemoveCachePool(String poolName){
        String APIName = "removeCachePool(String poolName)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;
        }

        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = new CachePoolInfo(poolName);
        try{
            dfs.addCachePool(cachePoolInfo);
        }catch(Exception e){
            logger.info("添加缓存池失败，请检查");
            e.printStackTrace();
            return;
        }

        logger.info("测试步骤2：移除缓存池，名称为 "+poolName);
        try{
            dfs.removeCachePool(poolName);
        }catch(Exception e){
            logger.info("移除缓存池失败，请检查");
            e.printStackTrace();
            return;
        }
        //如何检查
        boolean lastResult = Tool.checkCachePool(poolName);
        if(!lastResult){
            logger.info("该缓存池删除成功，用例执行成功");
        }else{
            logger.info("该缓存池删除失败，用例执行失败");
        }

        logger.info("测试API: " + APIName + "结束......");
    }

    //编号:NO.44
    //测试API:RemoteIterator<CachePoolEntry> listCachePools()
    //功能描述：列出缓存池
    //测试用例描述：
    //1.先添加两个缓存池，再检查列表
    public static void testListCachePools(String poolName){
        String APIName = "listCachePools()";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;}

        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo1 = new CachePoolInfo(poolName);
        CachePoolInfo cachePoolInfo2 = new CachePoolInfo(poolName+"2");
        try{
            dfs.addCachePool(cachePoolInfo1);
            dfs.addCachePool(cachePoolInfo2);
        }catch(Exception e){
            logger.info("添加缓存池失败，请检查");
            e.printStackTrace();
            return;}

        logger.info("测试步骤2：列出缓存池");
        RemoteIterator<CachePoolEntry> cachePoolEntryRemoteIterator = null;
        try{
            logger.info("测试步骤2-1：列出缓存池");
            cachePoolEntryRemoteIterator = dfs.listCachePools();
        }catch(Exception e){
            logger.info("获取缓存池列表失败，请检查");
            e.printStackTrace();
            return;}

        if(cachePoolEntryRemoteIterator == null){
            logger.info("该用例执行失败，请检查");
            return;}

        boolean checkPoolName1 = false;
        boolean checkPoolName2 = false;

        try {
            while(cachePoolEntryRemoteIterator.hasNext()){
                String name = cachePoolEntryRemoteIterator.next().getInfo().getPoolName();
                if(name.equals(poolName)){
                    checkPoolName1 = true;
                }
                if(name.equals(poolName+"2")){
                    checkPoolName2 = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(checkPoolName1&&checkPoolName2){
            logger.info("该用例执行成功.......PASS");
        }else{
            logger.info("该用例执行失败，请检查");
        }
        logger.info("测试API: " + APIName + "结束......");
    }

    //编号:NO.45
    //测试API:modifyCachePool(CachePoolInfo info)
    //功能描述：Modify an existing cache pool
    //测试用例描述：
    //1.先添加缓存池，后进行修改
    public static void testModifyCachePool(String poolName){
        String APIName  = "modifyCachePool(CachePoolInfo info)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;
        }
        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：修改缓存池owner属性为Jack，名称为 "+poolName);
        String ownerName = "Jack";
        cachePoolInfo.setOwnerName(ownerName);
        try{
            dfs.modifyCachePool(cachePoolInfo);
        }catch(Exception e){
            logger.info("修改缓存池失败，请检查");
            e.printStackTrace();
            return;}

        logger.info("测试步骤3：检查缓存池owner属性为Jack，名称为 "+poolName);
        boolean lastResult = false;
        RemoteIterator<CachePoolEntry> cachePoolEntryIterator = null;
        try{
            logger.info("测试步骤3-1：获取缓存池列表");
            cachePoolEntryIterator = dfs.listCachePools();
        }catch(Exception e){
            logger.info("获取缓存池列表失败，请检查");
            e.printStackTrace();
            return;
        }

        try {
            while(cachePoolEntryIterator.hasNext()){
                String name = cachePoolEntryIterator.next().getInfo().getPoolName();
                String owner = cachePoolEntryIterator.next().getInfo().getOwnerName();
                if(name.equals(poolName)&&(owner.equals(ownerName))){
                    lastResult = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;}
        if(lastResult){
            logger.info("该用例执行成功");
        }else{
            logger.info("该用例执行失败");
        }
        logger.info("测试API: " + APIName + "结束......");
    }

    //编号:NO.46
    //测试API:long addCacheDirective(CacheDirectiveInfo info)
    //功能描述：添加缓存文件
    //测试用例描述：
    //1.先添加缓存池，后添加缓存文件
    public static void testAddCacheDirective(String poolName,String fileName){
        String APIName = "addCacheDirective(CacheDirectiveInfo info)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;}

        logger.info("测试准备：检查文件是否存在");
        boolean isFileExist = Tool.checkFile(fileName);
        if(isFileExist){
            logger.info("文件已经存在，请重新选择文件");
            return;}



        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：添加文件，名称为 "+fileName);
        Tool.createFile(fileName);
        if(!Tool.checkFile(fileName)){
            logger.info("文件创建失败，请检查");
            return;
        }

        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        String cacheFileName = filePath.getName();
        logger.info("cache file name is "+cacheFileName);
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();

        try{
            dfs.addCacheDirective(info);
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

        //检查
        boolean lastResult =  false;
        try {
            RemoteIterator<CacheDirectiveEntry> remoteIterator = dfs.listCacheDirectives(null);
            while (remoteIterator.hasNext()) {
                CacheDirectiveEntry ca = remoteIterator.next();
                CacheDirectiveInfo temp = ca.getInfo();
                logger.info("=============>>>==>>>=================");
                String realCoachFile = temp.getPath().getName();
                logger.info(realCoachFile);
                if(realCoachFile.equals(cacheFileName)){
                    lastResult = true;
                    logger.info("Find the file name ,break!!");
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        if(lastResult){
            logger.info("该用例执行成功");
        }else{
            logger.info("该用例执行失败");
        }
        logger.info("测试API: " + APIName + "结束......");

    }

    //编号:NO.47
    //测试API:addCacheDirective(CacheDirectiveInfo info, EnumSet<CacheFlag> flags)
    //功能描述：添加缓存文件
    //测试用例描述：
    //1.先添加缓存池，后添加缓存文件
    public static void testAddCacheDirWithFlage(String poolName,String fileName){
        String APIName = "addCacheDirective(CacheDirectiveInfo info, EnumSet<CacheFlag> flags)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;}

        logger.info("测试准备：检查文件是否存在");
        boolean isFileExist = Tool.checkFile(fileName);
        if(isFileExist){
            logger.info("文件已经存在，请重新选择文件");
            return;}

        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：添加文件，名称为 "+fileName);
        Tool.createFile(fileName);
        if(!Tool.checkFile(fileName)){
            logger.info("文件创建失败，请检查");
            return;
        }

        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        String realName = filePath.getName();
        logger.info("添加缓存文件在缓存中的名称: "+realName);
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();
        EnumSet<CacheFlag> flags = EnumSet.of(CacheFlag.FORCE);


        try{
            dfs.addCacheDirective(info,flags);
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

        //检查
        boolean lastResult =  false;
        try {
            RemoteIterator<CacheDirectiveEntry> remoteIterator = dfs.listCacheDirectives(null);
            while(remoteIterator.hasNext()) {
                CacheDirectiveEntry ca = remoteIterator.next();
                CacheDirectiveInfo temp = ca.getInfo();
                String coachName =temp.getPath().getName();
                logger.info(coachName);
                logger.info("缓存中文件实际名称: "+coachName);
                logger.info("=============>>>===================");
                if(coachName.equals(realName)){
                    lastResult = true;
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
        if(lastResult){
            logger.info("该用例执行成功");
        }else{
            logger.info("该用例执行失败");
        }
        logger.info("测试API: " + APIName + "结束......");
    }

    //编号:NO.48
    //测试API:a RemoteIterator<CacheDirectiveEntry> listCacheDirectives(CacheDirectiveInfo filter)
    //功能描述： List cache directives.  Incrementally fetches results from the server
    //测试用例描述：
    //1.传入参数为null，列出所有文件
    //2.传入参数不为null,则抛出异常.......
    //测试参数：poolName：缓存池名称，fileName:添加缓存文件名称
    public static void testListCacheDirectives(String poolName,String fileName){
        String APIName = "listCacheDirectives(CacheDirectiveInfo filter)";
        String fileName2 = fileName+"2";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;}

        logger.info("测试准备：检查文件是否存在");
        boolean isFileExist = Tool.checkFile(fileName);
        if(isFileExist){
            logger.info("文件"+fileName+"已经存在，请重新选择文件");
            return;}

        boolean isFileExist2 = Tool.checkFile(fileName2);
        if(isFileExist2){
            logger.info("文件"+fileName2+"已经存在，请重新选择文件");
            return;}



        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：添加文件，名称为 "+fileName);
        Tool.createFile(fileName);
        if(!Tool.checkFile(fileName)){
            logger.info("文件创建失败，请检查");
            return;
        }


        logger.info("测试步骤3：添加文件，名称为 "+fileName2);
        Tool.createFile(fileName2);
        if(!Tool.checkFile(fileName2)){
            logger.info("文件创建失败，请检查");
            return;
        }


        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        String coachFile1 = filePath.getName();
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();

        Path filePath2 = new Path(fileName2);
        String coachFile2 = filePath2.getName();
        CacheDirectiveInfo info2 = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath2).
                setPool(poolName).setReplication((short) 1).build();

        try{
            dfs.addCacheDirective(info);
            dfs.addCacheDirective(info2);
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

//        logger.info("TestCase1：传入带参");
//        RemoteIterator<CacheDirectiveEntry> Iterator1 = null;
//        try{
//            Iterator1 = dfs.listCacheDirectives(info);
//        }catch(Exception e){
//            logger.info("获取RemoteIterator失败，请检查");
//            e.printStackTrace();
//            return;
//        }
//        if( Iterator1== null){
//            logger.info("获取RemoteIterator为空，请检查");
//            return;
//        }
//
//        boolean lastResult1 = false;
//        try {
//            while (Iterator1.hasNext()) {
//                CacheDirectiveEntry ca = Iterator1.next();
//                CacheDirectiveInfo temp = ca.getInfo();
//                String RealfilePath = temp.getPath().getName();
//                logger.info("PATH name"+RealfilePath);
//                if(RealfilePath.equals(coachFile1)){
//                    lastResult1 = true;
//                    break;
//                }
//            }
//        }catch(Exception e){
//            logger.info("RemoteIterator迭代失败，请检查");
//            e.printStackTrace();
//            return;
//        }
//        if(lastResult1){
//            logger.info("TestCase1：测试用例通过");
//        }else{
//            logger.info("TestCase1：测试用例失败");
//        }



        logger.info("TestCase：不传入带参");
        RemoteIterator<CacheDirectiveEntry> Iterator2 = null;
        try{
            Iterator2 = dfs.listCacheDirectives(null);
        }catch(Exception e){
            logger.info("获取RemoteIterator失败，请检查");
            e.printStackTrace();
            return;
        }

//        if( Iterator1== null){
//            logger.info("获取RemoteIterator为空，请检查");
//            return;
//        }

        boolean lastResult21 = false;
        boolean lastResult22 = false;
        try {
            while (Iterator2.hasNext()) {
                CacheDirectiveEntry ca = Iterator2.next();
                CacheDirectiveInfo temp = ca.getInfo();
                String RealfilePath = temp.getPath().getName();
                logger.info("PATH name："+RealfilePath);
                if(RealfilePath.equals(coachFile1)){
                    lastResult21 = true;
                }
                if(RealfilePath.equals(coachFile2)){
                    lastResult22 = true;
                }
            }
        }catch(Exception e){
            logger.info("RemoteIterator迭代失败，请检查");
            e.printStackTrace();
            return;
        }
        boolean lastResult2 = lastResult21&&lastResult22;
        if(lastResult2){
            logger.info("TestCase2：测试用例通过");
        }else{
            logger.info("TestCase2：测试用例失败");
        }
    }

    //编号:NO.49
    //测试API:modifyCacheDirective(CacheDirectiveInfo info)
    //功能描述：
    //测试用例描述：修改缓存文件
    public static void testModifyCacheDir(String poolName,String fileName){
        String APIName = "modifyCacheDirective(CacheDirectiveInfo info)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;}

        logger.info("测试准备：检查文件是否存在");
        boolean isFileExist = Tool.checkFile(fileName);
        if(isFileExist){
            logger.info("文件已经存在，请重新选择文件");
            return;}


        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：添加文件，名称为 "+fileName);
        Tool.createFile(fileName);
        if(!Tool.checkFile(fileName)){
            logger.info("文件创建失败，请检查");
            return;
        }

        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        String coachFileName = filePath.getName();
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 5).build();

        try{
            dfs.addCacheDirective(info);
            logger.info("=====================");
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }
        info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 3).build();
        CacheDirectiveInfo.Builder builder = new CacheDirectiveInfo.Builder();
        CacheDirectiveInfo.Expiration expiration =  CacheDirectiveInfo.Expiration.newAbsolute(10000);

        logger.info("测试步骤4：修改缓存文件");
        try {
            //dfs.modifyCacheDirective(info);
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        //检查
        boolean isRight = false;
        RemoteIterator<CacheDirectiveEntry> Iterator = Tool.getCoacheFileIterator();
        if(Iterator == null){
            logger.info("获取RemoteIterator失败，值为null,请检查");
            return;
        }
        Short repliaction = 0;
        try {
            while (Iterator.hasNext()) {
                CacheDirectiveEntry ca = Iterator.next();
                CacheDirectiveInfo temp = ca.getInfo();
                String coachName = temp.getPath().getName();
                // long id = temp.getId();
                repliaction = temp.getReplication();
                logger.info("缓存文件名："+coachName);
                logger.info("缓存文件副本数: "+repliaction);
                if(coachName.equals(coachFileName)){
                    logger.info("找到该文件，修改该文件");
                    // dfs.addCacheDirective(info);
                    dfs.modifyCacheDirective(temp);
                    isRight = true;
                }
            }
        }catch(Exception e){
            logger.info("RemoteIterator迭代失败，请检查");
            e.printStackTrace();
            return;
        }

        if(isRight){
            logger.info("修改成功，副本数修改正确，测试用例通过");
        }else{
            logger.info("修改失败，预期修改副本数为: "+3+"实际副本数为: "+repliaction);
        }

        logger.info("测试API: " + APIName + "结束......");
    }

    //编号:NO.50
    //测试API:modifyCacheDirective(CacheDirectiveInfo info, EnumSet<CacheFlag> flags)
    //功能描述：
    //测试用例描述：修改缓存文件
    public static void testModifyCachDirWithFlag(String poolName,String fileName){
        String APIName = "modifyCacheDirective(CacheDirectiveInfo info, EnumSet<CacheFlag> flags)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;}

        logger.info("测试准备：检查文件是否存在");
        boolean isFileExist = Tool.checkFile(fileName);
        if(isFileExist){
            logger.info("文件已经存在，请重新选择文件");
            return;}


        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：添加文件，名称为 "+fileName);
        Tool.createFile(fileName);
        if(!Tool.checkFile(fileName)){
            logger.info("文件创建失败，请检查");
            return;
        }

        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();

        try{
            dfs.addCacheDirective(info);
            logger.info("==========>>===========");
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

        logger.info("测试步骤4：修改缓存文件");
        EnumSet<CacheFlag> flags = EnumSet.of(CacheFlag.valueOf("TEST"));
        try {
            dfs.modifyCacheDirective(info,flags);

        }catch(Exception e){
            e.printStackTrace();
        }

        //?检查
        logger.info("测试API: " + APIName + "结束......");
    }

    //编号:NO.51
    //测试API:removeCacheDirective(long id)
    //功能描述：
    //测试用例描述：删除缓存文件
    public static void testRemoveCachDir(String poolName,String fileName){
        String APIName = "removeCacheDirective(long id)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            logger.info("获取dfs失败，请检查");
            return;
        }
        logger.info("测试准备：检查缓存池是否存在");
        boolean result  = Tool.checkCachePool(poolName);
        if(result){
            logger.info("该缓存池已经存在，请重新传入缓存池名称");
            return;}

        logger.info("测试准备：检查文件是否存在");
        boolean isFileExist = Tool.checkFile(fileName);
        if(isFileExist){
            logger.info("文件已经存在，请重新选择文件");
            return;}


        logger.info("测试步骤1：添加缓存池，名称为 "+poolName);
        CachePoolInfo cachePoolInfo = Tool.AddCoachPool(poolName);
        if(cachePoolInfo==null){
            logger.info("添加缓存池失败，请检查");
            return;}

        logger.info("测试步骤2：添加文件，名称为 "+fileName);
        Tool.createFile(fileName);
        if(!Tool.checkFile(fileName)){
            logger.info("文件创建失败，请检查");
            return;
        }

        logger.info("测试步骤3：添加缓存文件");
        Path filePath = new Path(fileName);
        String cacheFileName = filePath.getName();
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(filePath).
                setPool(poolName).setReplication((short) 1).build();

        try{
            dfs.addCacheDirective(info);
            logger.info("==========>>>===========");
        }catch(Exception e){
            logger.info("添加缓存文件失败，请检查");
            e.printStackTrace();
            return;
        }

        logger.info("测试步骤4：删除缓存文件");
        Long id = 0L;
        try {
            logger.info("获取缓存文件ID");
            RemoteIterator<CacheDirectiveEntry> It = dfs.listCacheDirectives(null);
            while(It.hasNext()) {
                CacheDirectiveEntry ca = It.next();
                CacheDirectiveInfo temp = ca.getInfo();
                CacheDirectiveStats cs = ca.getStats();

                logger.info("缓存池名称 " + temp.getPool());
                logger.info("缓存文件id为 " + temp.getId());
                logger.info("缓存文件名称 " + temp.getPath().getName());
                //dfs.removeCacheDirective(id);
                if (poolName.equals(temp.getPool()) && cacheFileName.equals(temp.getPath().getName())) {
                    id = temp.getId();
                    logger.info("文件id 为"+id);
                    break;
                }
            }
            if(id!=0L){
                logger.info("根据id删除文件！");
                dfs.removeCacheDirective(id);
            }else{
                logger.info("没找到删除文件！");
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }


        boolean lastResult = false;
        try {
            logger.info("测试步骤5：检查删除缓存文件");
            RemoteIterator<CacheDirectiveEntry> Its = dfs.listCacheDirectives(null);
            while(Its.hasNext()) {
                CacheDirectiveEntry ca = Its.next();
                CacheDirectiveInfo temp = ca.getInfo();
                logger.info("缓存池名称 " + temp.getPool());
                logger.info("缓存文件id为 " + temp.getId());
                logger.info("缓存文件名称 " + temp.getPath().getName());
                if (poolName.equals(temp.getPool()) && cacheFileName.equals(temp.getPath().getName())) {
                    lastResult=true;
                    logger.info("应该删除文件依然存在！");
                    break;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            return;
        }

        if(!lastResult){
            logger.info("用例执行成功");
        }else{
            logger.info("用例执行失败");
        }

        logger.info("测试API: " + APIName + "结束......");
    }

    //编号:NO.52
    //测试API:removeDefaultAcl(Path path)
    //功能描述：移除文件默认ACL状态
    //测试用例描述,传入参数应为文件夹，只有文件夹才有default ACL,可以不先设置ACL
    public static void testRemoveDefaultAcl(String DirPath){
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

    //编号:NO.53
    //测试API: removeAcl(Path path)
    //功能描述：移除文件ACL状态
    //测试用例描述
    public static void testRemoveACL(String path){
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

    //编号:NO.54
    //测试API：removeAclEntries(Path path, final List<AclEntry> aclSpec)
    //功能：移除ACL
    //测试用例描述
    public static void testRemoveAclEntries(String filePath){
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

    //编号:NO.55
    //测试API：modifyAclEntries(Path path, final List<AclEntry> aclSpec)
    //功能：更改ACL
    //测试用例描述
    //备注：传入的参数中，文件夹必须存在，否则报错
    public static void testModifyAclEntries(String filePath){
        String APIName = "modifyAclEntries(Path path, final List<AclEntry> aclSpec)";
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
            logger.info("=====================modify=============================");
            dfs.modifyAclEntries(path, aclList);
            aclStatus = dfs.getAclStatus(path);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("移除时发生异常，请检查");
            return;
        }

        logger.info("the owner is "+aclStatus.getOwner());
        logger.info("the group is "+aclStatus.getGroup());
        String aclEntryName="";
        String permissionName ="";
        String scopeName ="";
        String typeName ="";
        boolean testResult = false;

        List<AclEntry >aclEntries = aclStatus.getEntries();
        for(int i = 0;i <aclEntries.size();i++){
            AclEntry aclEntry = aclEntries.get(i);
            aclEntryName = aclEntry.getName();
            logger.info("aclEntry name "+ aclEntryName);
            FsAction permission = aclEntry.getPermission();
            permissionName  = permission.name();
            logger.info("permission name: "+permissionName);
            AclEntryScope scope = aclEntry.getScope();
            scopeName = scope.name();
            logger.info("scope name: "+ scopeName);
            AclEntryType type =  aclEntry.getType();
            typeName=type.name();
            logger.info("typeName: "+ typeName);
            if(aclEntryName.equals("test")&&permissionName.equals("READ")&&scopeName.equals("ACCESS")&&typeName.equals("USER")){
                testResult = true;
                break;
            }
            logger.info("=============================================");
        }

        if(testResult){
            logger.info("修改成功,测试用例执行成功");
        }else{
            logger.info("修改失败,测试用例执行失败");
        }

        logger.info("测试API: " + APIName + "结束......");
    }


    //编号:NO.56
    //测试API：getDelegationToken()
    //功能：获取空Token
    //测试用例描述
    public static void testDelegationToken(){
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

    //编号:NO.57
    //测试API：isDirectory(Path f)
    //测试场景
    //1.f 为文件夹存在
    //2.f 文件夹不存在
    //3.f 为文件目录
    public static void testIsDirectory(String path){
        String APIName = "isDirectory(Path f)";
        logger.info("测试API: " + APIName + "开始......");
        logger.info("测试准备1：获取DistributedFileSystem对象");
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs == null){
            logger.info("获取dfs失败，请检查");
            return;}
        String dir  = path;
        String file = dir+"/test.txt";
        logger.info("测试准备2：创建文件");
        if(Tool.checkFile(file)){
            logger.info("该文件已经存在，请输入其他文件夹，程序将自动帮你创建");
            return;
        }else{
            boolean isSuccess = Tool.createFile(file);
            if(isSuccess){
                logger.info("文件创建成功，测试准备完毕");
            }else{
                logger.info("文件创建失败，请检查");
                return;
            }
        }

        logger.info("测试案例1：f 为文件夹，期望结果为true");
        Path Dir = new Path(path);
        Path filePath = new Path(file);
        boolean result1 = false;
        boolean result2 = false;

        try{
            result1 = dfs.isDirectory(Dir);
            result2 = dfs.isDirectory(filePath);
        }catch(Exception e){
            logger.info("判断文件夹出现异常，请检查!");
            e.printStackTrace();
        }
        if(result1){
            logger.info("测试案例1 测试成功！");
        }else{
            logger.info("测试案例1 测试失败！");
        }

        logger.info("测试案例2：f 为文件，期望结果为false");
        if(!result2){
            logger.info("测试案例2 测试成功！");
        }else{
            logger.info("测试案例2 测试失败！");
        }
    }

}
