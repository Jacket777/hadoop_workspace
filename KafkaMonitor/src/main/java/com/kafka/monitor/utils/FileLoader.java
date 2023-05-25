package com.kafka.monitor.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 18061263
 * @create 2019-04-26
 * @since v1.0.0
 */
public class FileLoader {
    public List<String> loadWhiteList() throws IOException {
        List<String> list = new ArrayList<>();
        File file = new File(getClass().getResource("while-list.txt").getFile());
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bf = new BufferedReader(inputReader);
        // 按行读取字符串
        String str;
        while ((str = bf.readLine()) != null) {
            list.add(str);
        }
        bf.close();
        inputReader.close();
        return list;
    }
}
