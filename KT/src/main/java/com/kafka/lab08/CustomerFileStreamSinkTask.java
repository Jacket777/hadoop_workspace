package com.kafka.lab08;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CustomerFileStreamSinkTask extends SinkTask{
	//1.声明一个日志对象
	private static final Logger LOG = LoggerFactory.getLogger(CustomerFileStreamSinkTask.class);
	//2.声明一个文件名变量
	private String filename;
	//3.声明一个输入流对象
	private PrintStream outputStream;
	
	
	public CustomerFileStreamSinkTask() {
	}
	
	
	public CustomerFileStreamSinkTask(PrintStream outputStream) {
		filename = null;
		this.outputStream = outputStream;
	}

	/*获取版本号*/
	public String version() {
		return new CustomerFileStreamSinkConnector().version();
	}
	
	/*开始执行任务*/
	@Override
	public void start(Map<String, String> props) {
		filename = props.get(CustomerFileStreamSinkConnector.FILE_CONFIG);
		if(filename == null) {
			outputStream = System.out;
		}else {
				try {
					outputStream = new PrintStream(new FileOutputStream(filename,true),
							false,StandardCharsets.UTF_8.name());
				} catch (UnsupportedEncodingException  e) {	
					e.printStackTrace();
					throw new ConnectException("Couldn't find or create "
							+ "file for FileStream sinkTask",e);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					throw new ConnectException("Couldn't find or create "
							+ "file for FileStream sinkTask",e);
				}
		}
	}

	/*发送记录给sink并输出*/
	@Override
	public void put(Collection<SinkRecord> sinkRecords) {
		for(SinkRecord record:sinkRecords) {
			LOG.trace("writing line to {}：{}",logFileName(),record.value());
			outputStream.println(record.value());
		}
	}
	
	/*持久化数据*/
	public void flush(Map<TopicPartition,OffsetAndMetadata>offsets) {
		LOG.trace("Flushing output stream for {}",logFileName());
		outputStream.flush();
	}
	


	/*停止任务*/
	@Override
	public void stop() {
		if(outputStream!=null && outputStream!=System.out) {
			outputStream.close();
		}
	}
	
	
	/*判断标准输出还是文件写入*/
	private String logFileName() {
		return filename==null?"stdout":filename;
	}

}
