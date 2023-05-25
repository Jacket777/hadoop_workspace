package com.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * Created by 17081290 on 2021/4/7.
 */
public class SendCallBack implements Callback {
    private long startTime;
    private int key;
    private String message;

    public SendCallBack(long startTime, int key, String message){
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }


    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long duringTime = System.currentTimeMillis() - startTime;
        if(null!=metadata){
            System.out.println(
                    "message[" + key + ", " + message + "] sent to partition[" + metadata.partition() +
                            "], " +"offset[" + metadata.offset() + "] in " + duringTime + " ms");
        }
    }
}
