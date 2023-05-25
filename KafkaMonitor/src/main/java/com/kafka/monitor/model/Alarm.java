package com.kafka.monitor.model;
/**
 * 穆加系统告警类
 *
 * @author 18061263
 * @create 2018-07-25
 * @link http://wiki.cnsuning.com/pages/viewpage.action?pageId=28007852
 * @since v1.0.0
 */

import java.util.List;

public class Alarm {
    private String source_system_key;
    private String system_en_name;
    /**
     * VM:虚机、PM:物理机、ND:网络设备、SV:服务
     */
    private String alert_target_type;
    private String alert_target;//IP地址或者实例名
    private String software;
    private List<Metric> metrics;
    private String alert_content;
    private Integer alert_level;
    private String system_env;
    private String alert_time;
    private String triggedId;
    private Integer source_type;
    private Integer event_type;
    private String alert_recover_time;
    private String custom_close_period;
    private Attachments attachments;

    public String getSource_system_key() {
        return source_system_key;
    }

    public void setSource_system_key(String source_system_key) {
        this.source_system_key = source_system_key;
    }

    public String getSystem_en_name() {
        return system_en_name;
    }

    public void setSystem_en_name(String system_en_name) {
        this.system_en_name = system_en_name;
    }

    public String getAlert_target_type() {
        return alert_target_type;
    }

    public void setAlert_target_type(String alert_target_type) {
        this.alert_target_type = alert_target_type;
    }

    public String getAlert_target() {
        return alert_target;
    }

    public void setAlert_target(String alert_target) {
        this.alert_target = alert_target;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public String getAlert_content() {
        return alert_content;
    }

    public void setAlert_content(String alert_content) {
        this.alert_content = alert_content;
    }

    public Integer getAlert_level() {
        return alert_level;
    }

    public void setAlert_level(Integer alert_level) {
        this.alert_level = alert_level;
    }

    public String getSystem_env() {
        return system_env;
    }

    public void setSystem_env(String system_env) {
        this.system_env = system_env;
    }

    public String getAlert_time() {
        return alert_time;
    }

    public void setAlert_time(String alert_time) {
        this.alert_time = alert_time;
    }

    public String getTriggedId() {
        return triggedId;
    }

    public void setTriggedId(String triggedId) {
        this.triggedId = triggedId;
    }

    public Integer getSource_type() {
        return source_type;
    }

    public void setSource_type(Integer source_type) {
        this.source_type = source_type;
    }

    public Integer getEvent_type() {
        return event_type;
    }

    public void setEvent_type(Integer event_type) {
        this.event_type = event_type;
    }

    public String getAlert_recover_time() {
        return alert_recover_time;
    }

    public void setAlert_recover_time(String alert_recover_time) {
        this.alert_recover_time = alert_recover_time;
    }

    public String getCustom_close_period() {
        return custom_close_period;
    }

    public void setCustom_close_period(String custom_close_period) {
        this.custom_close_period = custom_close_period;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }
}
