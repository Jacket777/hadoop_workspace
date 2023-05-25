package com.kafka.monitor.v1;

import com.kafka.monitor.job.SpeedSpiderJob;
import com.kafka.monitor.spider.TopicManager;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Startup {
    public static void main(String[] args) throws SchedulerException {
        TopicManager.getInstance().setMaxPage(20);
        TopicManager.getInstance().setPageSize(100);

        //jobDetail
        //JobDetail checkOwnerJobDetail = JobBuilder.newJob(ConsumerCheckJob.class).withIdentity("checkOwnerCronJob").build();
        JobDetail getSpeedJobDetail = JobBuilder.newJob(SpeedSpiderJob.class).withIdentity("getSpeedCronJob").build();

        //每小时的15分，45分触发一次
        //CronTrigger checkOwnerCronTrigger = TriggerBuilder.newTrigger().withIdentity("checkOwnerCronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 15,45 * * * ?")).build();

        //每个小时的7,22,37,52分触发一次,对应采集的数据时间是每个小时的5,20,35,50
        CronTrigger getSpeedCronTrigger = TriggerBuilder.newTrigger().withIdentity("getSpeedCronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0 7,22,37,52 * * * ?")).build();

        //Scheduler实例
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.start();
        //scheduler.scheduleJob(checkOwnerJobDetail, checkOwnerCronTrigger);
        scheduler.scheduleJob(getSpeedJobDetail, getSpeedCronTrigger);
    }
}
