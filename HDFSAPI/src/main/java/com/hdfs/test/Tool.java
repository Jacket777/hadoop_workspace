package com.hdfs.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.CacheDirectiveEntry;
import org.apache.hadoop.hdfs.protocol.CachePoolEntry;
import org.apache.hadoop.hdfs.protocol.CachePoolInfo;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17081290 on 2017/12/20.
 */
public class Tool {

    //编号:NO.1
    //功能：判断文件夹是否创建成功
    public static boolean checkDir(String path) {
        boolean result = false;
        try {
            Configuration conf = new Configuration();
            DistributedFileSystem dfs = (DistributedFileSystem) DistributedFileSystem.get(conf);
            Path dir = new Path(path);
            result = dfs.isDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("获取DFS失败，请检查");
        } finally {
            System.out.println("判断文件夹结束");
            return result;
        }
    }

    //2.文件夹创建后结果输出
    public static void checkResult(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("测试通过，创建文件夹成功");
        } else {
            System.out.println("测试失败，请检查");
        }
    }


    //3.合并1,2函数用于判断文件夹是否创建成功
    public static void checkDirResult(String path) {
        boolean result = checkDir(path);
        checkResult(result);
    }


    //4.判断文件是否存在
    public static boolean checkFile(String path) {
        boolean result = false;
        try {
            Configuration conf = new Configuration();
            DistributedFileSystem dfs = (DistributedFileSystem) DistributedFileSystem.get(conf);
            Path file = new Path(path);
            result = dfs.isFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("获取DFS失败，请检查");
        } finally {
            return result;
        }
    }


    //创建文件夹前检查该目录是否存在，即该文件夹是否存在
    public static boolean checkIsPath(String path){
        boolean isExist = false;
        if (checkDir(path)) {
            System.out.println("该目录为文件夹目录，且已经存在");
            isExist = true;
        }else if(Tool.checkFile(path)){
            System.out.println("该目录为文件目录，且已经存在");
            isExist = true;
        }
        return isExist;
    }


    //5.文件创建后根据返回值输出最终结果
    public static void checkFileResult(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("测试通过，创建文件成功");
        } else {
            System.out.println("测试失败，请检查");
        }
    }


    //6.合并4,5 方法，输出测试结果
    public static void checkCreateFile(String path) {
        boolean result = checkFile(path);
        checkFileResult(result);
    }

    //7.获取DistributedFileSystem 对象
    public static DistributedFileSystem getDistributedFileSystem() {
        DistributedFileSystem dfs;
        Configuration conf = new Configuration();
        try {
            dfs = (DistributedFileSystem) DistributedFileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建DFS对象出现异常");
            return null;
        }
        return dfs;
    }


    //8.获取文件的副本数
    public static int getFileReplication(Path filepath) {
        DistributedFileSystem dfs = getDistributedFileSystem();
        int number = 0;
        if (dfs != null) {
            try {
                number = dfs.getFileStatus(filepath).getReplication();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("获取文件信息失败，请检查");
            } finally {
                return number;
            }
        } else {
            return number;
        }
    }

    /*
    9.创建文件前先进行判断文件是否存在，存在则重写，不存在则新建，
      该方法用于在执行创建文件API(默认为重写)前提供提示
     */

    public static void checkFileIsExist(String path) {
        if (Tool.checkFile(path)) {
            System.out.println("该文件已经存在，你将重写该文件");
        } else {
            System.out.println("该文件不存在，你将创建新文件");
        }
    }


    //10.获得Progressable 对象 用于报告执行进度
    public static Progressable getProgressable() {
        Progressable prg = new Progressable() {
            @Override
            public void progress() {
                System.out.println("........");
            }
        };
        return prg;
    }

    //11.检查文件创建是否成功与副本数是否正确
    public static void checkCreateFileReplic(String path, short repliaction) {
        if (checkFile(path)) {
            try {
                Path filepath = new Path(path);
                int number = Tool.getFileReplication(filepath);
                System.out.println("你创建文件实际副本数： " + number);
                if (repliaction == number) {
                    System.out.println("创建文件成功，且副本数正确，用例执行成功");
                } else {
                    System.out.println("创建文件成功，但副本数错误，用例执行失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("获取文件信息");
            }
        } else {
            System.out.println("创建文件失败，用例执行失败");
        }
    }


    //12.检查是否设置为重写
    public static void checkIsOverWrite(boolean overwrite) {
        if (overwrite) {
            System.out.println("你设置的是否重写为:true,如果该文件已存在，则文件将被重写，如果文件不存在则无影响");
        } else {
            System.out.println("你设置的是否重写为:false,如果该文件已存在，则将报错，如果文件不存在则无影响");
        }
    }


    //13.获取文件的blocksize数
    public static long getFileBlockSize(Path filepath) {
        DistributedFileSystem dfs = getDistributedFileSystem();
        long blocksize = 0;
        if (dfs != null) {
            try {
                blocksize = dfs.getFileStatus(filepath).getBlockSize();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("获取文件blockSize失败，请检查");
            } finally {
                return blocksize;
            }
        } else {
            return blocksize;
        }
    }


    //14..获取HDFS 文件中内容
    public static String getContent(String path) {
        String url = "hdfs://10.242.21.114:9000" + path;
        InputStream input = null;
        try {
            input = new URL(url).openStream();
            if (input != null) {
                System.out.println("输入流不为空.......");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                StringBuffer stringBuffer = new StringBuffer();
                String templine = new String("");
                while ((templine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(templine);
                    System.out.println("stringBuffer 不为空");
                }
                return stringBuffer.toString();
            }
        } catch (Exception e) {
            System.err.println("Error");
        }
        return null;
    }


    //15.检查文件是否创建成功，且内容正确
    public static void checkFileIsRight(boolean firstCheck, String path, String content) {
        //检查文件
        boolean checkFile = Tool.checkFile(path);
        String result = Tool.getContent(path);
        System.out.println("文件内容为:");
        System.out.println(result);
        if (firstCheck && (result != null)) {
            //检查是否重写
            if (result.equals(content)) {
                System.out.println("重写成功，该用例测试通过！");
            } else {
                System.out.println("重写不成功，该用例测试不通过！");
            }
        } else {
            //原先文件不存在，则检查文件是否创建成功，检查文件内容
            if (checkFile) {
                System.out.println("该文件新建成功，检查文件内容......");
                if (result.equals(content)) {
                    System.out.println("文件内容正确，该用例测试通过！");
                } else {
                    System.out.println("文件内容错误，该用例测试不通过！");
                }
            }
        }

    }


    //16.检查append是否成功
    public static boolean checkAppendFile(String path, String expect) {
        boolean result = false;
        FSDataInputStream in = null;
        String content = "";
        Path filePath = new Path(path);
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        if(dfs==null){
            System.out.println("获取该文件信息失败，请检查........");
            return false;}

        try {
                in = dfs.open(filePath);
                content = in.readLine();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("读取该文件内容失败，请检查........");
            }
        System.out.println("文件内容为: "+content);
        if (content.equals("helloWorld"+expect)) {
            System.out.println("文件追加成功");
            result = true;
        } else {
            System.out.println("文件追加失败");
        }
        return result;
    }


    //17.创建文件，且设置文本为HelloWorld
    public static boolean createFile(String path) {
        boolean result = false;
        System.out.println("测试前提条件:创建新文件 " + path);
        if (checkFile(path)) {
            System.out.println("该文件已经存在，请重新创建文件........");
            return false;
        }
        try {
            DistributedFileSystem dfs = getDistributedFileSystem();
            Path filepath = new Path(path);
            FSDataOutputStream outputStream = dfs.create(filepath);
            outputStream.writeBytes("helloWorld");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建DFS对象或创建文件时出现异常");
        } finally {
            System.out.println("创建文件结束");
        }
        //检查创建文件结果
        result = checkFile(path);
        return result;
    }


    //18.获取false pathFilter
    public static PathFilter getFalsePathFilter() {
        PathFilter pathFilter;
        pathFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };
        return pathFilter;
    }


    //19.获取true pathFilter
    public static PathFilter getTruePathFilter() {
        PathFilter pathFilter;
        pathFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return true;
            }
        };
        return pathFilter;
    }


    //20.创建文件夹与文件，为后续测试作为准备，一个文件夹，两个文件
    public static boolean createDirAndFile(String DirPath, String FilePath1, String FilePath2) {
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        Path directory = new Path(DirPath);
        boolean checkDir = false;
        //boolean checkCreateDir = false;
        try {
            checkDir = dfs.isDirectory(directory);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (checkDir) {
            System.out.println("该文件夹已存在，请选择一个不存在的文件夹，我们将自动帮你创建");
            return false;
        }
        System.out.println("程序自动帮你创建文件夹开始.......");
        try {
            dfs.mkdirs(directory);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建文件夹失败，请检查");
            return false;
        }

        if (Tool.checkDir(DirPath)) {
            System.out.println("程序自动帮你创建文件夹成功.......");
            System.out.println("程序自动帮你创建文件,请稍等.............");
        } else {
            System.out.println("程序自动帮你创建文件夹失败，请检查.......");
            return false;
        }

        String file01 = DirPath + FilePath1;
        String file02 = DirPath + FilePath2;
        Tool.createFile(file01);
        Tool.createFile(file02);

        if (Tool.checkFile(file01) && Tool.checkFile(file02)) {
            System.out.println("程序自动帮你创建文件夹成功.......");
            System.out.println("测试准备工作完成.......");
            return true;
        } else {
            System.out.println("程序自动帮你创建文件失败，请检查.......");
            return false;
        }
    }

    //------------------12.29--------------------------------------------
    //21.读取本地文件（linux） 返回文件内容
    public static String readLocalFile(String fileName) {
        String output = null;
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            try {
                BufferedReader input = new BufferedReader(new FileReader(file));
                StringBuffer buffer = new StringBuffer();
                String text;
                while ((text = input.readLine()) != null)
                    buffer.append(text);
                output = buffer.toString();
            } catch (IOException ioException) {
                System.err.println("文件读取异常!");
            }
        } else {
            System.err.println("文件不存在或传入的是文件夹目录!");
        }
        return output;
    }


    //--------------------------1.3-------------------------------------------------
    //22.添加缓存池前进行检查，如果该缓存池存在，则提示该缓存池已经存在，
    public static boolean checkCachePool(String poolName) {
        boolean resu1t = false;
        DistributedFileSystem dfs = getDistributedFileSystem();
        if (dfs == null) {
            System.out.println("获取dfs失败，请检查");
            return false;
        }
        RemoteIterator<CachePoolEntry> cachePoolEntryIterator = null;
        try {
            cachePoolEntryIterator = dfs.listCachePools();
        } catch (Exception e) {
            System.out.println("获取缓存池列表失败，请检查");
            e.printStackTrace();
            return false;
        }

        if (cachePoolEntryIterator == null) {
            return true;
        }

        try {
            while (cachePoolEntryIterator.hasNext()) {
                String name = cachePoolEntryIterator.next().getInfo().getPoolName();
                if (name.equals(poolName)) {
                    resu1t = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return resu1t;
    }

    //23.添加缓存池
    public static CachePoolInfo AddCoachPool(String poolName) {
        DistributedFileSystem dfs = getDistributedFileSystem();
        if (dfs == null) {
            System.out.println("获取dfs失败，请检查");
            return null;
        }

        CachePoolInfo cachePoolInfo = new CachePoolInfo(poolName);
        try {
            dfs.addCachePool(cachePoolInfo);
            return cachePoolInfo;
        } catch (Exception e) {
            System.out.println("添加缓存池失败，请检查");
            e.printStackTrace();
            return null;
        }
    }

    //获取缓存文件迭达器
    public static RemoteIterator<CacheDirectiveEntry> getCoacheFileIterator(){
        RemoteIterator<CacheDirectiveEntry> Iterator = null;
        DistributedFileSystem dfs = getDistributedFileSystem();
        try{
            Iterator = dfs.listCacheDirectives(null);
        }catch(Exception e){
            System.out.println("获取RemoteIterator失败，请检查");
            e.printStackTrace();
            return null;
        }
        return Iterator;
    }

    //----------------------设置ACL------------------------------
    public static List<AclEntry> getACLlist1() {
        List<AclEntry> aclList = new ArrayList<AclEntry>();
        AclEntry aclEntry1 = new AclEntry.Builder().setName("user1").setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.USER).build();
        AclEntry aclEntry2 = new AclEntry.Builder().setName("group1").setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.GROUP).build();
        AclEntry aclEntry3 = new AclEntry.Builder().setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.MASK).build();
        AclEntry aclEntry4 = new AclEntry.Builder().setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.OTHER).build();
        aclList.add(aclEntry1);
        aclList.add(aclEntry2);
        aclList.add(aclEntry3);
        aclList.add(aclEntry4);
        return aclList;
    }

    public static List<AclEntry> getACLlist2() {
        List<AclEntry> aclList = new ArrayList<AclEntry>();
        AclEntry aclEntry5 = new AclEntry.Builder().setName("user2").setPermission(FsAction.READ).
                setScope(AclEntryScope.ACCESS).setType(AclEntryType.USER).build();
        AclEntry aclEntry6 = new AclEntry.Builder().setName("group2").setPermission(FsAction.READ).
                setScope(AclEntryScope.ACCESS).setType(AclEntryType.GROUP).build();
//        AclEntry aclEntry6 = new AclEntry.Builder().setPermission(FsAction.READ).
//                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.MASK).build();
        AclEntry aclEntry7 = new AclEntry.Builder().setPermission(FsAction.READ).
                setScope(AclEntryScope.ACCESS).setType(AclEntryType.OTHER).build();
        //aclList.add(aclEntry1);
        aclList.add(aclEntry5);
        aclList.add(aclEntry6);
        aclList.add(aclEntry7);
        return aclList;
    }


    public static List<AclEntry> getNewACLlist() {
        List<AclEntry> aclList = new ArrayList<AclEntry>();
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.setName("test");
        builder.setPermission(FsAction.READ);
        builder.setScope(AclEntryScope.ACCESS);
        builder.setType(AclEntryType.USER);
        AclEntry aclEntry = builder.build();
        aclList.add(aclEntry);
        return aclList;
    }


    public static List<AclEntry> getDefaultACLList() {
        List<AclEntry> aclList = new ArrayList<AclEntry>();
        AclEntry.Builder builder = new AclEntry.Builder();
        builder.setName("test");
        builder.setPermission(FsAction.READ);
        builder.setScope(AclEntryScope.DEFAULT);
        builder.setType(AclEntryType.USER);
        AclEntry aclEntry = builder.build();
        aclList.add(aclEntry);
        return aclList;
    }


    //获取acl信息
    public static void getACLInfo(AclStatus aclStatus) {
        if (aclStatus == null) {
            System.out.println("AclStatus 对象为空，请检查");
            return;
        }
        System.out.println("获取ACL成功");
        System.out.println("The owner is " + aclStatus.getOwner());
        System.out.println("The group is " + aclStatus.getGroup());
        List<AclEntry> aclEntries = aclStatus.getEntries();
        if (aclEntries.size() == 0) {
            System.out.println("ACL Entries 为空，请检查");
            return;
        }
        for (int i = 0; i < aclEntries.size(); i++) {
            AclEntry aclEntry = aclEntries.get(i);
            System.out.println("aclEntry name is " + aclEntry.getName());
            System.out.println("permission name is : " + aclEntry.getPermission().name());
            System.out.println("scope name is: " + aclEntry.getScope().name());
            System.out.println("type name is: " + aclEntry.getType().name());
            System.out.println("============>>>>>================");
        }
    }

    //用例开始执行时标志
    public static void beginExe(){
        System.out.println("============>>>>>>>>>>>>>=============================");
    }

   //创建文件夹，但不创建文件
    public static boolean createDir(String DirPath){
        DistributedFileSystem dfs = Tool.getDistributedFileSystem();
        Path directory = new Path(DirPath);
        boolean checkDir = false;
        //boolean checkCreateDir = false;
        try {
            checkDir = dfs.isDirectory(directory);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (checkDir) {
            System.out.println("该文件夹已存在，请选择一个不存在的文件夹，我们将自动帮你创建");
            return false;
        }
        System.out.println("程序自动帮你创建文件夹开始.......");
        try {
            dfs.mkdirs(directory);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建文件夹发现异常，请检查");
            return false;
        }

        if (Tool.checkDir(DirPath)) {
            System.out.println("程序自动帮你创建文件夹成功.......");
            return true;
        } else {
            System.out.println("程序自动帮你创建文件夹失败，请检查.......");
            return false;
        }
    }

    //在linux系统中创建文件
    public static boolean createLocalFile(String user,String fileName){
        String filepath ="/home/"+user+"/"+fileName;
        FileOutputStream outputStream = null;
        try {
            File file = new File(filepath);
            if (!file.exists()) {
            file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write("helloWorld".getBytes());
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try{
            outputStream.close();
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
    return true;
    }
    
}