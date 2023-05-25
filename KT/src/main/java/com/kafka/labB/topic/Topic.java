package com.kafka.labB.topic;


import java.util.Properties;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.JaasUtils;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;

public class Topic {
	//private static final String ZK_CONNECT="kafkasitoltp01broker01.cnsuning.com:2181,kafkasitoltp01broker02.cnsuning.com:2181,kafkasitoltp01broker03.cnsuning.com:2181";
	private static final String ZK_CONNECT="kafkasit02zk01.cnsuning.com:2181,kafkasit02zk02.cnsuning.com:2181,kafkasit02zk03.cnsuning.com:2181";
	
	private static final int SESSION_TIMEOUT = 120000;
	private static final int CONNECT_TIMEOUT = 120000;
	
	
	
	public static void createTopic(String topic,int partition,int replica,
			Properties properties) {
		ZkUtils zkUtils = null;
		try {
			zkUtils = ZkUtils.apply(ZK_CONNECT, SESSION_TIMEOUT, CONNECT_TIMEOUT,
					JaasUtils.isZkSecurityEnabled());
			
			if(!AdminUtils.topicExists(zkUtils, topic)) {
				AdminUtils.createTopic(zkUtils, topic,partition,replica,properties,
						AdminUtils.createTopic$default$6());
				System.out.println("====topic====");
			}else {
				System.out.println("======topic===========");
			}
			
		}catch(Exception e) {
			System.out.println("========");
			e.printStackTrace();
		}finally {
			zkUtils.close();
		}
	}

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("acks", "all");
		props.put("retries", 3);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
		props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
		props.put("sasl.jaas.config",
		"org.apache.kafka.common.security.plain.PlainLoginModule required username=\"maoxiangyi\" password=\"123321\";");
		Topic.createTopic("keywordTest01", 2, 2, props);
	}

}
