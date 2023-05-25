package com.kafka.lab04.serial;
import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

/*
 * 7.自定义序列化实现
 */

public class JSalarySeralizer implements Serializer<JSalarySerial>{

	public void close() {	
	}

	public void configure(Map<String, ?> configs, boolean isKey) {	
	}

	public byte[] serialize(String topic, JSalarySerial data) {
		return SerializeUtils.serialize(data);
	}

}
