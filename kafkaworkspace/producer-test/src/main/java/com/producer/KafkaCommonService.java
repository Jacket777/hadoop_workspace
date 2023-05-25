package com.producer;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

import java.text.SimpleDateFormat;

/**
 * Created by 17081290 on 2021/4/30.
 */
@Service
public class KafkaCommonService {
    @Autowired
    private SnKafkaCommonProducer<String, String> producer;

    @PostConstruct
    public void sendMessage(){
        long number = 0l;
        while(true){
            String message = "this duo huo  message "+number;
            try {
                producer.send(TopicName.topicName,"key07",message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println("time : "+df.format(new Date()));// new Date()为获取当前系统时间
                System.out.println("-------->>>>生产数据-----------------"+message);
                Thread.sleep(1000*TopicName.time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            number += 1;
        }
    }
}