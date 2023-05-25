package com.kafka.monitor.spider;

import com.kafka.monitor.model.TopicInfo;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.util.List;

/**
 * @author 18061263
 * @create 2019-04-08
 * @since v1.0.0
 */
public interface ISpider {
    public void crawler(BasicCookieStore cookieStore, long startTimeInMill, long endTimeInMill, long execTimeInMill, List<Cookie> cookieList, List<TopicInfo> list) throws Exception;
}
