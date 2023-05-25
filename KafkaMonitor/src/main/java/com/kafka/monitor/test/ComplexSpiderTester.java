package com.kafka.monitor.test;

import com.kafka.monitor.job.ConsumerCheckJob;
import com.kafka.monitor.job.SpeedSpiderJob;
import com.kafka.monitor.spider.TopicManager;

/**
 * @author 18061263
 * @create 2019-08-06
 * @since v1.0.0
 */
public class ComplexSpiderTester {
    public static void main(String[] args) {
        TopicManager.getInstance().setMaxPage(20);
        TopicManager.getInstance().setPageSize(100);

        ConsumerCheckJob consumerCheckJob = new ConsumerCheckJob();
        consumerCheckJob.execute(null);

        SpeedSpiderJob speedSpiderJob = new SpeedSpiderJob();
        speedSpiderJob.execute(null);
    }
}
