package com.kafka.monitor.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 18061263
 * @create 2019-03-30
 * @since v1.0.0
 */
public class HttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private static final int SOCKET_TIME_OUT = 15000;

    private static final int CONNECTION_TIME_OUT = 15000;

    private static CloseableHttpClient getHttpClient() {
        RequestConfig globalConfig = RequestConfig.custom()
                .setConnectTimeout(SOCKET_TIME_OUT)
                .setSocketTimeout(CONNECTION_TIME_OUT)
                .build();
        return HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .build();
    }

    private static void close(CloseableHttpResponse response,
                              CloseableHttpClient client) throws IOException {
        if (response != null) {
            response.close();
        }
        if (client != null) {
            client.close();
        }
    }

    public static String doPostJson(String url, String json) {
        // 创建 HttpClient 对象
        CloseableHttpClient client = getHttpClient();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建 HttpPost 请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行请求
            response = client.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            //LOGGER.error("Http post json throws exception: ", e);
        } finally {
            try {
                close(response, client);
            } catch (IOException e) {
                //LOGGER.error("Closing response/client failed: ", e);
            }
        }
        return resultString;
    }

    public static String doGet(String url, Map<String, String> params) {
        // 创建 HttpClient 对象
        CloseableHttpClient client = getHttpClient();
        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (null != params && params.size() >= 1) {
                Iterator<Map.Entry<String, String>> ite = params.entrySet().iterator();
                List<NameValuePair> paramPairList = new LinkedList<NameValuePair>();
                while (ite.hasNext()) {
                    Map.Entry<String, String> entry = ite.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    NameValuePair param = new BasicNameValuePair(key, value);
                    paramPairList.add(param);
                }
                uriBuilder.setParameters(paramPairList);
            }
            //根据带参数的URI对象构建GET请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(2000).setConnectionRequestTimeout(2000)
                    .setSocketTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            /*
             * 添加请求头信息
             */
            // 浏览器类型
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            // 传输的类型
            httpGet.addHeader("Content-Type", "application/json");
            response = client.execute(httpGet);
            resultString = EntityUtils.toString(response.getEntity(),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOG.error("Http get protocol throws exception: ", e);
        } finally {
            try {
                close(response, client);
            } catch (IOException e) {
                LOG.error("Closing response/client failed: ", e);
            }
        }
        return resultString;
    }
}
