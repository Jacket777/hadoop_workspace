package com.kafka.monitor.test;

import com.kafka.monitor.job.SpeedSpiderJob;
import com.kafka.monitor.spider.TopicManager;

/**
 * @author 18061263
 * @create 2019-07-11
 * @since v1.0.0
 */
public class SpeedSpiderJobTester {
    public static void main(String[] args) {
        TopicManager.getInstance().setMaxPage(1);
        TopicManager.getInstance().setPageSize(100);

        SpeedSpiderJob speedSpiderJob = new SpeedSpiderJob();
        speedSpiderJob.execute(null);
    }
}
