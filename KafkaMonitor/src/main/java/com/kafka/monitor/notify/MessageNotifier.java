package com.kafka.monitor.notify;

import com.kafka.monitor.model.Person;
import com.kafka.monitor.utils.AlarmUtil;
import com.kafka.monitor.utils.Constants;
import com.kafka.monitor.utils.IpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @author 18061263
 * @create 2019-03-29
 * @since v1.0.0
 */
public class MessageNotifier implements INotifier {

    private Log LOG = LogFactory.getLog(MessageNotifier.class);

    private final List<Person> personList;

    public MessageNotifier(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public boolean notify(String message) {
        int flag;
        if (!personList.isEmpty()) {
            String ip = IpUtils.getLocalIP();
            flag = AlarmUtil.sendAlarm(Constants.ALARM_URL, Constants.ALARM_SYSTEM_KEY, personList, message, ip, "PRD");
        } else {
            flag = -1;
        }
        return flag == 1;
    }
}
