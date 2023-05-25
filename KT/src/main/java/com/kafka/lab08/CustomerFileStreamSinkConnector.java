package com.kafka.lab08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

public class CustomerFileStreamSinkConnector extends SinkConnector {
	//声明文件配置变量
	public static final String FILE_CONFIG="file";
	//实例化一个配置对象
	private static final ConfigDef CONFIG_DEF= new ConfigDef().
			define(FILE_CONFIG, Type.STRING, Importance.HIGH, "Destination filename.");
	//声明一个文件名变量
	private String filename;
	
	/*获取版本信息*/
	@Override
	public String version() {
		return AppInfoParser.getVersion();
	}
	
	/*执行初始化*/
	@Override
	public void start(Map<String, String> props) {
		filename = props.get(FILE_CONFIG);	
	}
	
	/*实例化输出类*/
	@Override
	public Class<? extends Task> taskClass() {
		return CustomerFileStreamSinkTask.class;
	}
	
	/*获取配置信息*/
	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		ArrayList<Map<String, String>>configs = new ArrayList();
		for(int i =0;i<maxTasks;i++) {
			Map<String,String>config = new HashMap();
			if(filename!=null) {
				config.put(FILE_CONFIG, filename);
			}
			configs.add(config);
		}																											
		return configs;
	}
	
	
	@Override
	public void stop() {}
	

	/*获取配置对象*/
	@Override
	public ConfigDef config() {
		return CONFIG_DEF;
	}




}
