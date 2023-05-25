package com.kafka.monitor.job;

import com.kafka.monitor.model.WhiteTopic;
import com.kafka.monitor.utils.Constants;
import com.kafka.monitor.utils.SSOClient;
import com.kafka.monitor.model.AdminUser;
import com.kafka.monitor.model.Person;
import com.kafka.monitor.model.TopicInfo;
import com.kafka.monitor.notify.INotifier;
import com.kafka.monitor.notify.MessageNotifier;
import com.kafka.monitor.spider.ConsumerSpider;
import com.kafka.monitor.spider.TopicManager;
import com.kafka.monitor.utils.DBUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 校验Kafka Owner是否丢失、Halt Consumer等场景的定时任务
 */
public class ConsumerCheckJob extends QuartzJob {
    private Log LOG = LogFactory.getLog(ConsumerCheckJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOG.info("开始执行ConsumerCheckJob定时检测...");
        final long execTimeInMill = System.currentTimeMillis();
        Date execTime = new Date(execTimeInMill);
        process(execTime, execTime, execTime);
        LOG.info("结束执行ConsumerCheckJob定时检测...");
    }

    @Override
    void process(final Date startTime, final Date endTime, final Date execTime) {
        DBUtils dbUtils = new DBUtils(Constants.DRIVER, Constants.DB_URL, Constants.DB_USER_NAME, Constants.DB_PASSWORD);
        if (!dbUtils.test()) {
            LOG.info("数据库连接不通");
        } else {
            try {
                List<Person> list = dbUtils.queryNotifyPerson();
                AdminUser adminUser = dbUtils.queryLoginUser();
                dbUtils.closeConnection();
                final INotifier notifier = new MessageNotifier(list);
                final ConsumerSpider consumerSpider = new ConsumerSpider(7, notifier);
                SSOClient client = new SSOClient(adminUser);
                client.doSSOLogin(new SSOClient.LoginCallback() {
                    @Override
                    public void onSuccess(BasicCookieStore cookieStore, List<Cookie> cookies) {
                        try {
                            List<TopicInfo> allTopicList = TopicManager.getInstance().getTopicList(cookies, true);
                            if (!allTopicList.isEmpty()) {
                                allTopicList = filterWhiteList(allTopicList);
                                consumerSpider.crawler(cookieStore, execTime.getTime(), execTime.getTime(), execTime.getTime(), cookies, allTopicList);
                            } else {
                                LOG.info("当前系统下没有Topic");
                            }
                        } catch (IOException e) {
                            LOG.error(e);
                        } catch (InterruptedException e) {
                            LOG.error(e);
                        } catch (SQLException e) {
                            LOG.error(e);
                        }
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        notifier.notify("kafka monitor " + message);
                    }
                });
            } catch (SQLException exp) {
                LOG.error(exp);
            }
        }
    }
}
