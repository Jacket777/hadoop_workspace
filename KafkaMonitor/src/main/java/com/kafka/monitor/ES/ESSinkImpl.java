package com.kafka.monitor.ES;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

public class ESSinkImpl implements ESSink {
    private Log LOG = LogFactory.getLog(ESSinkImpl.class);
    private String serverNames;//es服务器地址,为域名方式
    private int port;//es服务器端口
    private String clusterName;//es服务器名称
    private String index; //es index
    private String type; //es 数据type

    public ESSinkImpl(String serverNames, int port, String clusterName, String index, String type) {
        this.serverNames = serverNames;
        this.port = port;
        this.clusterName = clusterName;
        this.index = index;
        this.type = type;
    }

    @Override
    public synchronized boolean put(List<Map> result) {
        boolean isSuccessfully;
        Client client = getTransPortClient();
        if (null != client) {
            int count = result.size();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            for (int i = 0; i < count; i++) {
                Map itemMap = result.get(i);
                bulkRequest.add(client.prepareIndex(index, type).setSource(itemMap));
            }
            BulkResponse response = bulkRequest.execute().actionGet();
            client.close();
            if (response == null || response.hasFailures()) {
                isSuccessfully = false;
            } else {
                isSuccessfully = true;
            }
        } else {
            LOG.error("无法创建ES Client!");
            isSuccessfully = false;
        }

        if (isSuccessfully) {
            LOG.info("批量写入ES成功, 共计写入 " + result.size() + " 条数据");
        } else {
            LOG.info("批量写入ES失败");
        }
        return isSuccessfully;
    }

    /**
     * ES TransPortClient 客户端连接<br>
     * 在elasticsearch平台中,可以执行创建索引,获取索引,删除索引,搜索索引等操作
     *
     * @return
     */
    private Client getTransPortClient() {
        TransportClient transPort = null;
        try {
            if (transPort == null) {
                if (serverNames == null || "".equals(serverNames.trim())) {
                    return null;
                }
                Settings settings = Settings.builder()
                        .put("cluster.name", clusterName) //集群名
                        .put("client.transport.sniff", true)
                        .build(); //自动把集群下的机器添加到列表中
                transPort = new PreBuiltTransportClient(settings);
                String esHosts[] = serverNames.split(",");
                for (String esHost : esHosts) { //添加集群域名列表
                    InetAddress address = InetAddress.getByName(esHost);
                    TransportAddress transportAddress = new InetSocketTransportAddress(address, port);
                    transPort.addTransportAddresses(transportAddress);
                }
                return transPort;
            } else {
                return transPort;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transPort != null) {
                transPort.close();
            }
            return null;
        }
    }
}
