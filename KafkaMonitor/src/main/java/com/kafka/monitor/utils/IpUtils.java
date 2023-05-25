package com.kafka.monitor.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP相关的工具类
 *
 * @author Administrator
 */
public class IpUtils {

    /**
     * 获取本机的IP
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }

    /**
     * 获取本机的HostName
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return null;
        }
    }

}
