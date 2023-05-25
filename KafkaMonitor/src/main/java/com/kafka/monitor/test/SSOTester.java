package com.kafka.monitor.test;


import com.kafka.monitor.model.AdminUser;

import com.kafka.monitor.utils.Constants;
import com.kafka.monitor.utils.DBUtils;
import com.kafka.monitor.utils.SSOClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;


import java.sql.SQLException;
import java.util.List;

/**
 * @author 18061263
 * @create 2019-07-11
 * @since v1.0.0
 */
public class SSOTester {
    private static Log LOG = LogFactory.getLog(SSOTester.class);

    public static void main(String[] args) {
       // final long execTime = System.currentTimeMillis();
        DBUtils dbUtils = new DBUtils(Constants.DRIVER, Constants.DB_URL, Constants.DB_USER_NAME, Constants.DB_PASSWORD);
        if (!dbUtils.test()) {
            LOG.info("数据库连接不通");
        } else {
            try {
                AdminUser adminUser = dbUtils.queryLoginUser();
                dbUtils.closeConnection();
                SSOClient client = new SSOClient(adminUser);
                client.doSSOLogin(new SSOClient.LoginCallback() {
                    @Override
                    public void onSuccess(BasicCookieStore cookieStore, List<Cookie> cookies) {

                    }

                    @Override
                    public void onFailed(int code, String message) {

                    }
                });
            } catch (SQLException exp) {
                LOG.error(exp);
            }
        }
    }
}
