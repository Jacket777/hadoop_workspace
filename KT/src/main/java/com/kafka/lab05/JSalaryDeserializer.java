package com.kafka.lab05;

import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import com.kafka.lab04.serial.SerializeUtils;

public class JSalaryDeserializer implements Deserializer<Object>{
	public void close() {}
	
	public void configure(Map<String, ?> config, boolean isKey) {		
	}

	//自定义反序列逻辑
	public Object deserialize(String topic, byte[] data) {
		return SerializeUtils.deserialize(data);
	}
}