package com.kafka.monitor.utils;

import com.kafka.monitor.model.AdminUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
//import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * @author 18061263
 * @create 2019-03-29
 * @since v1.0.0
 */
public class SSOClient {
    private Log LOG = LogFactory.getLog(SSOClient.class);
    private final String account;
    private final String password2;
    //private BasicCookieStore cookieStore;

    public SSOClient(AdminUser adminUser) {
        this.account = adminUser.getId();
        this.password2 = adminUser.getPassword2();
        //cookieStore = new BasicCookieStore();
    }

    public SSOClient(String account, String password2) {
        this.account = account;
        this.password2 = password2;
        //cookieStore = new BasicCookieStore();
    }

    public void doSSOLogin(LoginCallback callback) {
        LOG.info("获取Cookie...");
        CookieManager.getInstance().clear();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(CookieManager.getInstance().getCookieStore()).build();
        try {
            HttpUriRequest loginRequest = RequestBuilder.post()
                    .setUri(new URI(Constants.CAS_URI))
                    .addParameter("service", Constants.REF_SERVICE_URL)
                    .addParameter("loginTheme", "defaultTheme")
                    //.addParameter("oauth_redirect", "")
                    .addParameter("uuid", UUID.randomUUID().toString())
//                    .addParameter("password2", password2)
                    .addParameter("password", password2)
                    //.addParameter("token", "")
                    .addParameter("username", account).build();
            CloseableHttpResponse loginResponse = httpclient.execute(loginRequest);
            String content = consumeResponse(loginResponse);
            CookieHelper.printCookie(CookieManager.getInstance().getCookieStore());
            if (LOG.isDebugEnabled()) {
                LOG.debug("sso login response:" + content);
            }
            if (loginResponse.getStatusLine().getStatusCode() == 302) {
                Header[] headers = loginResponse.getHeaders("Location");
                if (headers.length == 1) {
                    String jumpUrl = headers[0].getValue();
                    HttpUriRequest jumpRequest = RequestBuilder.get()
                            .setUri(new URI(jumpUrl))
                            .build();
                    httpclient = HttpClients.custom()
                            .setDefaultCookieStore(CookieManager.getInstance().getCookieStore()).build();
                    CloseableHttpResponse jumpResponse = httpclient.execute(jumpRequest);
                    content = consumeResponse(jumpResponse);
                    CookieHelper.printCookie(CookieManager.getInstance().getCookieStore());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("jump response:" + content);
                    }
                    List<Cookie> cookies = CookieManager.getInstance().getCookieStore().getCookies();
                    if (cookies.isEmpty()) {
                        LOG.info("未获取指定Cookies");
                        callback.onFailed(0x12, "未获取指定Cookies");
                    } else {
                        callback.onSuccess(CookieManager.getInstance().getCookieStore(), cookies);
                    }
                }
            } else if (loginResponse.getStatusLine().getStatusCode() == 200) {
                if (content.contains("您的密码有效期已超期")) {
                    LOG.info(account + ":登录密码错误或过期");
                    callback.onFailed(0x12, account + ":登录密码错误或过期");
                } else {
                    LOG.info("sso回调链接验证失败，需要验证豆芽码");
                    callback.onFailed(0x11, account + "sso回调链接验证失败，需要验证豆芽码");
                }
            } else {
                LOG.info("Kafka Monitor SSO使用密码爬取有问题");
                callback.onFailed(0x12, "Kafka Monitor SSO使用密码爬取有问题");
            }
        } catch (IOException e) {
            LOG.error(e);
            callback.onFailed(0x13, e.getMessage());
        } catch (URISyntaxException e) {
            LOG.error(e);
            callback.onFailed(0x13, e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String consumeResponse(CloseableHttpResponse response) throws IOException {
        String responseValue = null;
  //      HttpEntity entity = response.getEntity();
        LOG.info("response status: "
                + response.getStatusLine());
        responseValue = EntityUtils.toString(response.getEntity(),
                StandardCharsets.UTF_8);
        //EntityUtils.consume(entity);
        return responseValue;
    }

    public interface LoginCallback {
        public void onSuccess(BasicCookieStore cookieStore, List<Cookie> cookies);

        public void onFailed(int code, String message);
    }
}
