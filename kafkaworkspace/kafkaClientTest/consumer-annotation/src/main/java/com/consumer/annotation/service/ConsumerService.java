package com.consumer.annotation.service;

import com.suning.kafka.client.consumer.SnKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by 17081290 on 2021/4/8.
 */
@Service
public class ConsumerService {
    @Autowired
    SnKafkaConsumer<String,String>consumer;

    //@PostConstruct
    public void consumerMsg(){
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(1000);
            for (ConsumerRecord<String,String>record:records) {
                System.out.printf("topic = %s, partition = %d, key = %s value = %s\n",
                        record.topic(),record.partition(),record.key(),record.value());
            }
        }
    }

    public void setSleepMode(){
        consumer.setSleepingMode(true);
        consumerMsg();
    }

    public void getMetrics(){
        Map<MetricName, ? extends Metric> metrics = consumer.metrics();
        for (Map.Entry<MetricName, ? extends Metric> entry : metrics.entrySet()) {
            System.out.println(entry.getKey().name() + " : " + entry.getValue().metricValue());
        }
    }

    public void closeConsumer(){
        while(true){
            consumer.close();
            ConsumerRecords<String,String> records = consumer.poll(1000);
            for (ConsumerRecord<String,String>record:records) {
                System.out.printf("topic = %s, partition = %d, key = %s value = %s\n",
                        record.topic(),record.partition(),record.key(),record.value());
            }
        }
    }


}
