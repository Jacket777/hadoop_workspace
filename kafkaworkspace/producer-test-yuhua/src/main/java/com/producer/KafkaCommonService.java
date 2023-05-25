package com.producer;

import com.suning.kafka.client.producer.SnKafkaCommonProducer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 17081290 on 2021/4/30.
 */
@Service
public class KafkaCommonService {
    @Autowired
    private SnKafkaCommonProducer<String, String> producer;

    @PostConstruct
    public void sendMessage(){
        while(true){
            try {
                producer.send("kafka206_yuhua_backtopic01","xuzhuang","this message xuzhuang");
                System.out.println("-------->>>>雨花 生产数据-----------------");
                Thread.sleep(2000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
