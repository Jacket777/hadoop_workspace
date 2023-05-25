package com.kafka.monitor.test;

import com.kafka.monitor.job.ConsumerCheckJob;
import com.kafka.monitor.spider.TopicManager;

/**
 * @author 18061263
 * @create 2019-07-11
 * @since v1.0.0
 */
public class ConsumerCheckJobTester {
    public static void main(String[] args) {
        TopicManager.getInstance().setMaxPage(1);
        TopicManager.getInstance().setPageSize(100);

        ConsumerCheckJob consumerCheckJob = new ConsumerCheckJob();
        consumerCheckJob.execute(null);
    }
}
