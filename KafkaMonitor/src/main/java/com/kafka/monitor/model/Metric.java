package com.kafka.monitor.model;

/**
 * 穆加系统性能指标度量类
 *
 * @author 18061263
 * @create 2018-07-26
 * @link http://wiki.cnsuning.com/pages/viewpage.action?pageId=28007852
 * @since v1.0.0
 */
public class Metric {
    private String metric_key;//指标名称
    private String metric_unit;//指标单位
    private String metric_value;//指标值
    private String metric_threshold;//指标阈值
    private String metric_time;//指标采集时间
    private Integer aggregate_type;//指标聚合参数

    public String getMetric_key() {
        return metric_key;
    }

    public void setMetric_key(String metric_key) {
        this.metric_key = metric_key;
    }

    public String getMetric_unit() {
        return metric_unit;
    }

    public void setMetric_unit(String metric_unit) {
        this.metric_unit = metric_unit;
    }

    public String getMetric_value() {
        return metric_value;
    }

    public void setMetric_value(String metric_value) {
        this.metric_value = metric_value;
    }

    public String getMetric_threshold() {
        return metric_threshold;
    }

    public void setMetric_threshold(String metric_threshold) {
        this.metric_threshold = metric_threshold;
    }

    public String getMetric_time() {
        return metric_time;
    }

    public void setMetric_time(String metric_time) {
        this.metric_time = metric_time;
    }

    public Integer getAggregate_type() {
        return aggregate_type;
    }

    public void setAggregate_type(Integer aggregate_type) {
        this.aggregate_type = aggregate_type;
    }
}
