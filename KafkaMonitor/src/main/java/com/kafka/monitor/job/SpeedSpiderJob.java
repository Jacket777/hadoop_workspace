package com.kafka.monitor.job;

import com.kafka.monitor.ES.ESSink;
import com.kafka.monitor.ES.ESSinkImpl;
import com.kafka.monitor.model.AdminUser;
import com.kafka.monitor.model.Person;
import com.kafka.monitor.model.TopicInfo;
import com.kafka.monitor.notify.INotifier;
import com.kafka.monitor.notify.MessageNotifier;
import com.kafka.monitor.spider.ConsumeSpeedSpider;
import com.kafka.monitor.spider.TopicManager;
import com.kafka.monitor.utils.Constants;
import com.kafka.monitor.utils.DBUtils;
import com.kafka.monitor.utils.SSOClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 每个Topic瞬时TPS抓取的定时任务
 *
 * @author 18061263
 * @create 2019-04-04
 * @since v1.0.0
 */
public class SpeedSpiderJob extends QuartzJob {
    private Log LOG = LogFactory.getLog(SpeedSpiderJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOG.info("开始抓取TPS...");
        final long execTimeInMill = System.currentTimeMillis();
        Date spiderTime = new Date(execTimeInMill);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(spiderTime);
        calendar.add(Calendar.MINUTE, ConsumeSpeedSpider.delay);
        Date endTime = calendar.getTime();
        calendar.add(Calendar.MINUTE, ConsumeSpeedSpider.interval);
        Date startTime = calendar.getTime();
        process(startTime, endTime, spiderTime);
        LOG.info("结束抓取TPS...");
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
                ESSink sink = new ESSinkImpl(Constants.ES_SERVER_NAMES, Constants.ES_PORT, Constants.ES_CLUSTER_NAME, Constants.ES_INDEX, Constants.ES_INDEX_TYPE);
                final ConsumeSpeedSpider consumeSpeedSpider = new ConsumeSpeedSpider(sink);
                SSOClient client = new SSOClient(adminUser);
                client.doSSOLogin(new SSOClient.LoginCallback() {
                    @Override
                    public void onSuccess(BasicCookieStore cookieStore, List<Cookie> cookies) {
                        try {
                            List<TopicInfo> allTopicList = TopicManager.getInstance().getTopicList(cookies, false);
                            //mock
                            //List<TopicInfo> allTopicList = makeMockData();
                            if (!allTopicList.isEmpty()) {
                                consumeSpeedSpider.crawler(cookieStore, startTime.getTime(), endTime.getTime(), execTime.getTime(), cookies, allTopicList);
                            } else {
                                LOG.info("当前系统下没有Topic");
                            }
                        } catch (IOException e) {
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

    private List<TopicInfo> makeMockData() throws IOException, SQLException {
        List<TopicInfo> allTopicList = new ArrayList<>();
        String detailUrl = "http://kafka.cnsuning.com/consumer/2608/consumerDetail.htm";
        Integer id = 2608;
        Integer topicId = 1436;
        String groupId = "bigdata_ssa_mp_search_log";
        String topicName = "ssa_mp_search_log";
        TopicInfo topicInfo = new TopicInfo(detailUrl, id, groupId);
        topicInfo.setTopicName(topicName);
        topicInfo.setTopicId(topicId);
        allTopicList.add(topicInfo);
        return allTopicList;
    }
}
