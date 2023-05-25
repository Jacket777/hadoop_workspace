package com.kafka.lab04.partition;

import java.util.Map;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.clients.producer.Partitioner;

/*
 * 1.自定义主分区算法
 */
public class JPartitioner implements Partitioner{
	public void configure(Map<String, ?> config) {	}
	
	public void close() {	}

	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		int partition = 0;
		String k =(String)key;
		partition = Math.abs(k.hashCode())%cluster.partitionCountForTopic(topic);
		return partition;
	}	
}