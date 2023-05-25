package com.test.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.Header;

import java.util.*;
//import java.util.concurrent.ExecutionException;

public class Producer extends Thread {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public Producer(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafkasitoltp01broker01.cnsuning.com:9092,kafkasitoltp01broker02.cnsuning.com:9092,kafkasitoltp01broker03.cnsuning.com:9092");
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(props);
        this.topic = topic;
    }

    public void run() {
        Integer messageNo = 1;
        while (true) {
            String messageStr = "Message_" + new Date().toString();
            long startTime = System.currentTimeMillis();
            List<Header> headers = new ArrayList<Header>();
            headers.add(new Header() {
				public String key() {return "I am a key";}	
				public byte[] value() {return "I am a value".getBytes();}
            });

            Boolean withHeaders = false;

            if (withHeaders) {
                // send with headers
                producer.send(new ProducerRecord<String, String>(topic, null,
                        messageNo.toString(),
                        messageStr, headers), new DemoCallBack(startTime, messageNo, messageStr));
            } else {
                // send without headers
                producer.send(new ProducerRecord<String, String>(topic,
                        messageNo.toString(),
                        messageStr), new DemoCallBack(startTime, messageNo, messageStr));
            }

            System.out.println(messageNo);
            ++messageNo;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class DemoCallBack implements Callback {
    private final long startTime;
    private final int key;
    private final String message;

    public DemoCallBack(long startTime, int key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }

    /**
     * A callback method the user can implement to provide asynchronous handling of request completion. This method will
     * be called when the record sent to the server has been acknowledged. Exactly one of the arguments will be
     * non-null.
     *
     * @param metadata  The metadata for the record that was sent (i.e. the partition and offset). Null if an error
     *                  occurred.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println(
                "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                    "), " +
                    "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
        } else {
            exception.printStackTrace();
        }
    }
}
