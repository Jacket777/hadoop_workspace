package com.kafka.monitor.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 校验Success文件生成
 *
 * @author 18061263
 * @create 2019-04-18
 * @since v1.0.0
 */
public class SuccessCheckJob extends QuartzJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    @Override
    void process(Date startTime, Date endTime, Date execTime) {

    }
}
