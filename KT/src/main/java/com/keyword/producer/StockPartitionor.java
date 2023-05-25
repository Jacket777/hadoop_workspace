package com.keyword.producer;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.log4j.Logger;

/**
 *  自定义Partitioner
 */
public class StockPartitionor implements Partitioner {
	private static final Logger LOG = Logger.getLogger(StockPartitionor.class);
	/** 分区数 */
	private static final Integer PARTITIONS = 3;

	public void configure(Map<String, ?> arg0) {}
	public void close() {}
	
	/**
	 * 根据股票代码最后一位与分区总长度取模来做为分区分配的策略
	 */
	public int partition(String topic, Object key, byte[] keyBytes,
			Object value, byte[] valueBytes, Cluster cluster) {
		if (null == key) {
			return 0;
		}
		String stockCode = String.valueOf(key);
		try {
			int partitionId = Integer.valueOf(stockCode.substring(stockCode
					.length() - 2)) % PARTITIONS;
			return partitionId;
		} catch (NumberFormatException e) {
			LOG.error("Parse message key occurs exception,key:" + stockCode, e);
			return 0;
		}	
	}



}
