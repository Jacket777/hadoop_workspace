package com.kafka.monitor.utils;

/**
 * 告警工具类
 *
 * @author 18061263
 * @create 2018-07-25
 * @since v1.0.0
 */

import com.kafka.monitor.model.Alarm;
import com.kafka.monitor.model.AlarmResponse;
import com.kafka.monitor.model.Attachments;
import com.kafka.monitor.model.Person;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlarmUtil {
    private static Log LOG = LogFactory.getLog(AlarmUtil.class);

    /**
     * Master HA切换告警方法，该方法目前包含弊端是：每次版本发布的时候和Master HA一样均会发送告警信息
     * 发送步骤:
     * 1.发送信息到穆加系统，然后由穆加发送短信和邮件到干系人
     * 2.将告警信息存储到alarm相关表
     *
     * @param url     穆加REST服务的URL
     * @param key     穆加认证的key
     * @param list    干系人列表
     * @param content 告警内容
     * @param ip      当前服务器IP
     * @param env     运行环境(SIT,PRE,PRD)
     */
    public static int sendAlarm(String url, String key, List<Person> list, String content, String ip, String env) {
        StringBuffer userBuffer = new StringBuffer();
        for (Person person : list) {
            //拼接员工ID列表
            userBuffer.append(person.getId() + ",");
        }
        String userList = userBuffer.toString();
        if (userList.endsWith(",")) {
            userList = userList.substring(0, userList.length() - 1);
        }
        Alarm alarmBean = new Alarm();
        alarmBean.setSource_system_key(key);
        alarmBean.setSystem_en_name("DI");
        alarmBean.setAlert_target_type("SV");
        alarmBean.setAlert_target(ip);
        alarmBean.setSoftware("DI Master HA");
        alarmBean.setAlert_content(content);
        alarmBean.setAlert_level(2);
        alarmBean.setSystem_env(env);
        Date current = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        alarmBean.setAlert_time(format.format(current));
        alarmBean.setTriggedId("-1");
        alarmBean.setSource_type(1);
        alarmBean.setEvent_type(0);
        Attachments attachments = new Attachments();
        attachments.setUser_list(userList);
        attachments.setOnly_attachments("Y");
        alarmBean.setAttachments(attachments);
        String request = JsonUtil.bean2Json(alarmBean);
        int actionState = 0;
        LOG.info("send alarm message,url:" + url + ",key:" + key + ",request:" + request);
        LOG.info("send alarm message,user list: " + userList);
        String responseStr = HttpUtils.doPostJson(url, request);
        LOG.info("send alarm message,response: " + responseStr);
        if (!StringUtil.isBlank(responseStr)) {
            AlarmResponse response = JsonUtil.json2Bean(responseStr, AlarmResponse.class);
            if (response.getSuccess() == 1) {
                actionState = 1;
            } else {
                actionState = 2;
            }
        } else {
            actionState = 2;
        }
        return actionState;
    }
}
