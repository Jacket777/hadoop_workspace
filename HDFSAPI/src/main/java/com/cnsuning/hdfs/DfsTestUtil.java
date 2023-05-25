package com.cnsuning.hdfs;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DfsTestUtil {
    private static final Logger logger = LoggerFactory.getLogger("dfsApiTestUtil");
    private final FileSystem dfs;
    private final Path rootPath;

    public DfsTestUtil(FileSystem dfs, Path rootPath) {
        this.dfs = dfs;
        this.rootPath = rootPath;
    }

    /**
     * 根据传入的参数做目录，在hdfs生成一个文件并返回文件路径
     *
     * @param str 传入的String作为一个目录，文件名为系统当前时间
     * @return 返回的是在hdfs上的绝对路径
     * @throws IOException
     */
    public Path createTmpFile(String str) throws IOException {
        Path dirPath = new Path(rootPath, str);
        if (!dfs.isDirectory(dirPath)) {
            dfs.mkdirs(dirPath);
        }
        String time = "" + System.currentTimeMillis();
        Path filePath = new Path(dirPath, time);
        FSDataOutputStream outputStream=null;
        try {
             outputStream = dfs.create(filePath);
            outputStream.write("for_test".getBytes());
        }finally {
            outputStream.close();
        }

        return filePath;
    }

    public Path createTmpFiles(String str,String s) throws IOException {
        Path dirPath = new Path(rootPath, str);
        if (!dfs.isDirectory(dirPath)) {
            dfs.mkdirs(dirPath);
        }
        String time = s + System.currentTimeMillis();
        Path filePath = new Path(dirPath, time);
        FSDataOutputStream outputStream = dfs.create(filePath);
        outputStream.write("for_test".getBytes());
        outputStream.close();
        return filePath;
    }

    //just for test testconcat
    public Path createTmpFiles(String str, String s, DistributedFileSystem dfs) throws IOException {
        Path dirPath = new Path(rootPath, str);
        if (!dfs.isDirectory(dirPath)) {
            dfs.mkdirs(dirPath);
        }
        String time = s + System.currentTimeMillis();
        Path filePath = new Path(dirPath, time);
        logger.info("testConcat: conf"+dfs.getConf().get("dfs.block.size"));
        FSDataOutputStream outputStream = dfs.create(filePath);
        outputStream.write("just_for_testContact".getBytes());
        outputStream.close();
        return filePath;
    }


    //在本地生成一个文件
    public Path createLocalTmpFile(String str) throws IOException {
        Path path = new Path(rootPath, str);
        File file = new File(path.toString());
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write("local_txt_for_test".getBytes());
        } finally {
            outputStream.close();
        }
        return path;
    }

    //在本地生成一个目录
    public Path createLocalTmpDir(String str) throws IOException {
        Path path = new Path(rootPath, str);
        File file = new File(path.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }


    public String readFile(Path path) throws IOException {
        FSDataInputStream inputStream = dfs.open(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    //在
    public Path createTmpDir(String str) throws IOException {
        if(dfs.exists(rootPath)){
            dfs.mkdirs(rootPath);
        }
        Path path = new Path(rootPath, str);
        if (!(!dfs.exists(path) && dfs.isFile(path))) {
            dfs.mkdirs(path);
        }
        return path;
    }

}
