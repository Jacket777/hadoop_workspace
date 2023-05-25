package com.kafka.monitor.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 18061263
 * @create 2019-03-29
 * @since v1.0.0
 */
public class CookieHelper {
    private static Log LOG = LogFactory.getLog(CookieHelper.class);

    public static Map<String, String> retrieveCookies(List<Cookie> cookieList) {
        Map<String, String> cookies = new HashMap<>();
        for (int i = 0; i < cookieList.size(); i++) {
            Cookie item = cookieList.get(i);
            cookies.put(item.getName(), item.getValue());
        }
        return cookies;
    }

    public static void printCookie(BasicCookieStore cookieStore) {
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies.isEmpty()) {
            LOG.info("Cookies is empty");
        } else {
            LOG.info("Cookies信息");
            for (int i = 0; i < cookies.size(); i++) {
                Cookie item = cookies.get(i);
                LOG.info(item.getName() + "- " + item.getValue());
            }
        }
    }
}
