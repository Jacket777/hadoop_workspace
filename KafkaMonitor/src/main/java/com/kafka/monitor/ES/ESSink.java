package com.kafka.monitor.ES;

import java.util.List;
import java.util.Map;


public interface ESSink {

    //批量写入ES
    public boolean put(List<Map> result);
}
