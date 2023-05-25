
package com.test.kafka;

public class KafkaConsumerProducerDemo {
    public static void main(String[] args) {
        Producer producerThread = new Producer("test_tc001xz");
        producerThread.start();
    }
}
