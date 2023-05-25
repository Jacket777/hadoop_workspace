package com.kafka.monitor.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * @author 18061263
 * @create 2019-08-06
 * @since v1.0.0
 */
public class CookieManager {
    private Log LOG = LogFactory.getLog(CookieManager.class);
    private final BasicCookieStore cookieStore;
    private final static CookieManager COOKIE_MANAGER = new CookieManager();

    private CookieManager() {
        cookieStore = new BasicCookieStore();
    }

    public static CookieManager getInstance() {
        return COOKIE_MANAGER;
    }

    public BasicCookieStore getCookieStore() {
        return cookieStore;
    }

    public void clear() {
        if (null != cookieStore) {
            cookieStore.clear();
        }
    }
}
